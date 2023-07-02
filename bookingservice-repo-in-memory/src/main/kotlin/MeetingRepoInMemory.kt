package ru.otuskotlin.public.bookingservice.repo.inmemory

import io.github.reactivecircus.cache4k.Cache
import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.common.repo.*
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.CONCURRENT_MODIFICATION
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_EMPLOYEE_ID_FOR_MEETINGS
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_EMPLOYEE_ID_FOR_SLOTS
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_ID_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.EMPTY_SLOT_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_ID_ERROR
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_MEETINGS_BY_EMPLOYEE_ID
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.NO_FOUND_SLOTS_BY_EMPLOYEE_ID
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.SLOT_NO_FOUND
import ru.otuskotlin.public.bookingservice.common.repo.DbRepoErrors.SLOT_OF_ANOTHER_EMPLOYEE
import ru.otuskotlin.public.bookingservice.repo.inmemory.entities.MeetingEntity
import ru.otuskotlin.public.bookingservice.repo.inmemory.entities.SlotEntity
import ru.otuskotlin.public.bookingservice.stubs.SlotStub
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class MeetingRepoInMemory(
    initObjects: List<BsMeeting> = emptyList(),
    initSlots: Set<BsSlot> = emptySet(),
    ttl: Duration = 10.minutes,
    val uuid: () -> String = { UUID.randomUUID().toString() }
) : IMeetingRepository {

    private val meetingCache = Cache.Builder<String, MeetingEntity>().expireAfterWrite(ttl).build()
    private val slotsCache = Cache.Builder<String, SlotEntity>().expireAfterWrite(ttl).build()

    init {
        initObjects.forEach {
            save(it)
        }
        initSlots.forEach {
            save(it)
        }
        if (initSlots.isEmpty()) {
            SlotStub.getSlots().forEach {
                slotsCache.get(it.id.asString()) ?: slotsCache.put(it.id.asString(), SlotEntity(it))
            }
        }
    }

    private fun getUUID() = UUID.randomUUID().toString()

    private fun save(meeting: BsMeeting) {
        MeetingEntity(meeting).apply {
            meetingCache.put(this.id, this)
        }
    }

    private fun save(slot: BsSlot) {
        SlotEntity(slot).apply {
            slotsCache.put(this.id, this)
        }
    }

    private fun updateAndPutSlot(slot: BsSlot) {
        slot.startDate = Instant.parse(slotsCache.get(slot.id.asString())!!.startDate)
        slot.endDate = Instant.parse(slotsCache.get(slot.id.asString())!!.endDate)
        slot.slotStatus = BsSlotStatus.RESERVED
        slot.employeeId = BsEmployeeId(slotsCache.get(slot.id.asString())!!.employeeId)
        slotsCache.put(slot.id.asString(), SlotEntity(slot))
    }

    override suspend fun createMeeting(request: DbMeetingRequest): DbMeetingResponse {
        val resultSlot = request.meeting.slots.map { slot ->
            val slotFromCache = slotsCache.get(slot.id.asString()) ?: return SLOT_NO_FOUND
            if (slotFromCache.employeeId != request.meeting.employeeId.asString()) return SLOT_OF_ANOTHER_EMPLOYEE
            slot.takeIf { slotFromCache.status == BsSlotStatus.FREE.toString() }
                ?: return DbRepoErrors.SLOT_RESERVED_ERROR
        }.onEach {
            updateAndPutSlot(it)
        }.toMutableSet()

        val key = getUUID()
        val newMeeting = request.meeting.copy(
            id = BsMeetingId(key),
            lock = BsMeetingLock(getUUID()),
            meetingStatus = BsMeetingStatus.CREATE,
            meetingPermissions = mutableSetOf(BsMeetingPermissions.READ),
            slots = resultSlot
        ).apply {
            save(this)
        }
        return DbMeetingResponse.success(newMeeting)
    }

    override suspend fun readMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
        val key = request.id.takeIf { it != BsMeetingId.NONE }?.asString() ?: return EMPTY_ID_ERROR
        val meeting = meetingCache.get(key) ?: return NO_FOUND_ID_ERROR
        return DbMeetingResponse.success(meeting.toInternal())
    }

    override suspend fun updateMeeting(request: DbMeetingRequest): DbMeetingResponse {
        val key = request.meeting.id.takeIf { it != BsMeetingId.NONE }?.asString() ?: return EMPTY_ID_ERROR
        val oldMeeting = meetingCache.get(key) ?: return NO_FOUND_ID_ERROR
        if (oldMeeting.lock != request.meeting.lock.asString()) return CONCURRENT_MODIFICATION
        if (request.meeting.slots.isEmpty()) return EMPTY_SLOT_ERROR
        val resultSlot = request.meeting.slots.map { slot ->
            val slotFromCache = slotsCache.get(slot.id.asString()) ?: return SLOT_NO_FOUND
            if (slotFromCache.employeeId != request.meeting.employeeId.asString()) return SLOT_OF_ANOTHER_EMPLOYEE
            slot.takeIf {
                // если слот свободен или уже был записан в этой встрече, то ок, иначе слот уже в резерве:
                slotFromCache.status == BsSlotStatus.FREE.toString() || meetingCache.get(key)?.slots?.any { it.id == slot.id } ?: false
            } ?: return DbRepoErrors.SLOT_RESERVED_ERROR
        }.onEach {
            updateAndPutSlot(it)
        }.toMutableSet()
        //освободим слоты
        oldMeeting.slots.forEach {
            if (!resultSlot.any { result -> it.id.asString() == result.id.asString() }) {
                val updSlot = slotsCache.get(it.id.asString())?.copy(status = BsSlotStatus.FREE.toString())
                if (updSlot != null) slotsCache.put(updSlot.id, updSlot)
            }
        }
        val result = request.meeting.copy(
            slots = resultSlot,
            lock = BsMeetingLock(getUUID()),
            meetingStatus = BsMeetingStatus.CREATE,
            meetingPermissions = mutableSetOf(BsMeetingPermissions.READ),
        ).apply { save(this) }
        return DbMeetingResponse.success(result)
    }

    override suspend fun deleteMeeting(request: DbMeetingIdRequest): DbMeetingResponse {
        val key = request.id.takeIf { it != BsMeetingId.NONE }?.asString() ?: return EMPTY_ID_ERROR
        val oldMeeting = meetingCache.get(key) ?: return NO_FOUND_ID_ERROR
        if (oldMeeting.lock != request.lock.asString()) return CONCURRENT_MODIFICATION
        meetingCache.invalidate(key)
        return DbMeetingResponse.success()
    }

    override suspend fun searchMeeting(request: DbEmployeeIdRequest): DbMeetingsResponse {
        val key = request.id.takeIf { it != BsEmployeeId.NONE }?.asString() ?: return EMPTY_EMPLOYEE_ID_FOR_MEETINGS
        val result = meetingCache.asMap().asSequence().filter { entry ->
            entry.value.employeeId == key
        }.map { it.value.toInternal() }.toList()
        if (result.isEmpty()) return NO_FOUND_MEETINGS_BY_EMPLOYEE_ID
        return DbMeetingsResponse.success(result)
    }

    override suspend fun searchSlots(request: DbEmployeeIdRequest): DbSlotsResponse {
        val key = request.id.takeIf { it != BsEmployeeId.NONE }?.asString() ?: return EMPTY_EMPLOYEE_ID_FOR_SLOTS
        val result = slotsCache.asMap().asSequence().filter { entry ->
            entry.value.employeeId == key
        }.map { it.value.toInternal() }.toSet()
        if (result.isEmpty()) return NO_FOUND_SLOTS_BY_EMPLOYEE_ID
        return DbSlotsResponse.success(result)
    }
}