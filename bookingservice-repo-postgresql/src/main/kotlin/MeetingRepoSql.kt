package ru.otuskotlin.public.bookingservice.repo.postgresql

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingLock
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.stubs.asBsError
import ru.otuskotlin.public.bookingservice.common.repo.*
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_ID_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_ID_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_MEETINGS_BY_EMPLOYEE_ID
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
                uuidId = { meeting.id.asString() }
            )
        }
        return Meeting.from(res)
    }


    private fun saveRelation(meeting: BsMeeting, slot: BsSlot) {
        MeetingSlot.insert {
            it[meetingId] = meeting.id.asString()
            it[slotId] = slot.id.asString()
        }
    }

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

            initObjects.forEach { meeting ->
                saveMeeting(meeting)
                meeting.slots.forEach { slot ->
                    saveSlot(slot)
                    saveRelation(meeting, slot)
                }
            }

            if (initSlots.isEmpty()) {
                SlotStub.getSlots().forEach { slot ->
                    saveSlot(slot)
                }
            }
        }
    }

    override suspend fun createMeeting(request: DbMeetingRequest): DbMeetingResponse =
        transactionWrapper {
            request.meeting.slots.forEach {
                saveRelation(request.meeting, it)
            }
            val res = saveMeeting(request.meeting)
            DbMeetingResponse.success(res)
        }


    override suspend fun readMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
        val resultMeetingRow = request.id.takeIf { it != BsMeetingId.NONE }?.let {
            Meeting.select {
                Meeting.id eq it.asString()
            }.singleOrNull() ?: return NO_FOUND_ID_ERROR
        } ?: return EMPTY_ID_ERROR
        val meeting = Meeting.from(resultMeetingRow).apply {
            addSlot(this)
        }
        return DbMeetingResponse.success(meeting)
    }

    override suspend fun updateMeeting(request: DbMeetingRequest): DbMeetingResponse {
        Meeting.update({ Meeting.id eq request.meeting.id.asString() }) {
            to(
                it, request.meeting.copy(lock = BsMeetingLock(UUID.randomUUID().toString())), ::getUuid
            )
        }
        MeetingSlot.deleteWhere { meetingId eq request.meeting.id.asString() }
        request.meeting.slots.forEach {
            saveRelation(request.meeting, it)
        }
        return readMeeting(DbMeetingIdRequest(request.meeting))
    }


    override suspend fun deleteMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
        Meeting.deleteWhere { id eq request.id.asString() }
        return DbMeetingResponse.success()
    }

    override suspend fun searchMeeting(request: DbEmployeeIdRequest): DbMeetingsResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchSlots(request: DbEmployeeIdRequest): DbSlotsResponse {
        TODO("Not yet implemented")
    }
}