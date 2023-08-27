package ru.otuskotlin.public.bookingservice.repo.postgresql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingLock
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingStatus
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.stubs.asBsError
import ru.otuskotlin.public.bookingservice.common.repo.*
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_ID_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_ID_ERROR
import ru.otuskotlin.public.bookingservice.repo.postgresql.entities.Meeting
import ru.otuskotlin.public.bookingservice.repo.postgresql.entities.Meeting.id
import ru.otuskotlin.public.bookingservice.repo.postgresql.entities.MeetingSlot
import ru.otuskotlin.public.bookingservice.repo.postgresql.entities.Slot
import ru.otuskotlin.public.bookingservice.stubs.SlotStub
import java.util.*

class MeetingRepoSql(
    props: SqlProperties = SqlProperties(),
    initObjects: List<BsMeeting> = emptyList(),
    initSlots: Set<BsSlot> = emptySet(),
) : IMeetingRepository {

    init {
        val driver = when {
            props.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unknown driver for url ${props.url}")
        }

        Database.connect(
            props.url, driver, props.user, props.password
        )

        transaction {
            props.takeIf { it.dropTable }?.let { SchemaUtils.drop(Meeting, Slot, MeetingSlot) }
            SchemaUtils.create(Meeting, Slot, MeetingSlot)

            if (initSlots.isEmpty()) {
                SlotStub.getSlots().forEach { slot ->
                    saveSlot(slot)
                }
            }

            initSlots.forEach {
                saveSlot(it)
            }

            initObjects.forEach { meeting ->
                val resMeeting = saveMeeting(meeting)
                meeting.slots.forEach { slot ->
                    saveRelation(resMeeting, slot)
                    Slot.update({
                        Slot.id eq slot.id.asString()
                    }) {
                        reserved(it)
                    }

                }
            }
        }
    }

    private fun getUuid() = UUID.randomUUID().toString()
    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbMeetingResponse): DbMeetingResponse =
        transactionWrapper(block) { DbMeetingResponse.error(it.asBsError()) }


    private fun saveSlot(slot: BsSlot): BsSlot {
        val res = Slot.insert { builder ->
            to(
                it = builder,
                slot = slot,
                uuidId = { slot.id.asString() }
            )
        }
        return Slot.from(res)
    }

    private fun addSlot(meeting: BsMeeting) {
        Slot
            .rightJoin(MeetingSlot)
            .select { MeetingSlot.meetingId eq meeting.id.asString() }.forEach {
                meeting.slots.add(Slot.from(it))
            }
    }


    private fun saveMeeting(meeting: BsMeeting): BsMeeting {
        val res = Meeting.insert { builder ->
            to(
                it = builder,
                meeting = meeting,
                uuidId = { meeting.id.takeIf { it != BsMeetingId.NONE }?.asString() ?: getUuid() }
            )
        }
        return Meeting.from(res)
    }


    private fun saveRelation(meeting: BsMeeting, slot: BsSlot) {
        MeetingSlot.insert {
            it[id] = getUuid()
            it[meetingId] = meeting.id.asString()
            it[slotId] = slot.id.asString()
        }
    }

    private fun readMeeting(meetingId: BsMeetingId): DbMeetingResponse {
        val resultMeetingRow = meetingId.takeIf { it != BsMeetingId.NONE }?.let {
            Meeting.select {
                id eq it.asString()
            }.singleOrNull() ?: return NO_FOUND_ID_ERROR
        } ?: return EMPTY_ID_ERROR
        val meeting = Meeting.from(resultMeetingRow).apply {
            addSlot(this)
        }
        return DbMeetingResponse.success(meeting)
    }

    override suspend fun createMeeting(request: DbMeetingRequest): DbMeetingResponse =
        transactionWrapper {
            val result = saveMeeting(request.meeting.apply {
                meetingStatus = BsMeetingStatus.CREATED
                lock = BsMeetingLock(getUuid())
            })
            request.meeting.slots.forEach { slot ->
                saveRelation(result, slot)
                Slot.update({
                    Slot.id eq slot.id.asString()
                }) {
                    reserved(it)
                }
            }
            addSlot(result)
            DbMeetingResponse.success(result)
        }


    override suspend fun readMeeting(request: DbMeetingIdRequest) =
        transactionWrapper { readMeeting(request.id) }

    override suspend fun updateMeeting(request: DbMeetingRequest) =
        transactionWrapper {
            val newStatus = when (val current = readMeeting(request.meeting.id).data?.meetingStatus) {
                BsMeetingStatus.NONE, BsMeetingStatus.CREATED, null -> BsMeetingStatus.UPDATED
                else -> current
            }
            Meeting.update({ id eq request.meeting.id.asString() }) {
                to(
                    it,
                    request.meeting.copy(lock = BsMeetingLock(UUID.randomUUID().toString()), meetingStatus = newStatus),
                    ::getUuid
                )
            }
            MeetingSlot.deleteWhere { meetingId eq request.meeting.id.asString() }
            request.meeting.slots.forEach {
                saveRelation(request.meeting, it)
                Slot.update({
                    Slot.id eq it.id.asString()
                }) { statement ->
                    reserved(statement)
                }
            }
            readMeeting(request.meeting.id)
        }


    override suspend fun deleteMeeting(request: DbMeetingIdRequest) =
        transactionWrapper {
            Meeting.deleteWhere { id eq request.id.asString() }
            DbMeetingResponse.success()
        }


    override suspend fun searchMeeting(request: DbEmployeeIdRequest) =
        transactionWrapper({
            val meetings = Meeting
                .select { Meeting.employeeId eq request.id.asString() }
                .map { Meeting.from(it) }
            meetings.forEach {
                addSlot(it)
            }
            DbMeetingsResponse.success(meetings)
        }) {
            DbMeetingsResponse.error(it.asBsError())
        }

    override suspend fun searchSlots(request: DbEmployeeIdRequest) =
        transactionWrapper({
            val slots = Slot
                .select { Slot.employeeId eq request.id.asString() }.map {
                    Slot.from(it)
                }.toMutableSet()
            DbSlotsResponse.success(slots)
        }) {
            DbSlotsResponse.error(it.asBsError())
        }

}