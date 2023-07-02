package ru.otuskotlin.public.bookingservice.repo.inmemory.entities

import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot

data class MeetingEntity(
    val id: String,
    val clientId: String,
    val employeeId: String,
    val status: String,
    val lock: String?,
    val slots :Set<BsSlot>,
    val description: String?
){
    constructor(meeting: BsMeeting): this(
        id = meeting.id.asString(),
        clientId = meeting.clientId.asString(),
        employeeId = meeting.employeeId.asString(),
        status = meeting.meetingStatus.name,
        lock = meeting.lock.asString().takeIf { it.isNotBlank() },
        slots = meeting.slots,
        description = meeting.description.takeIf { it.isNotBlank() }
    )

    fun toInternal() = BsMeeting(
        id = BsMeetingId(id),
        clientId = BsClientId(clientId),
        employeeId = BsEmployeeId(employeeId),
        meetingStatus = BsMeetingStatus.valueOf(status),
        lock = lock?.let { BsMeetingLock(it) }?: BsMeetingLock.NONE,
        slots = slots.toMutableSet(),
        description = description?:""
    )

}
