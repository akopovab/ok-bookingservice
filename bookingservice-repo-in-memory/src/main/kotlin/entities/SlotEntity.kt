package ru.otuskotlin.public.bookingservice.repo.inmemory.entities

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

data class SlotEntity(
    val id: String,
    val startDate: String,
    val endDate :String,
    val status :String,
    val employeeId :String
){
    constructor(slot :BsSlot): this(
        id = slot.id.asString(),
        startDate = slot.startDate.toString(),
        endDate = slot.endDate.toString(),
        status = slot.slotStatus.name,
        employeeId = slot.employeeId.asString()
    )

    fun toInternal() = BsSlot(
        id = BsSlotId(id),
        startDate = Instant.parse(startDate),
        endDate = Instant.parse(endDate),
        slotStatus = status.let { BsSlotStatus.valueOf(it)},
        employeeId = BsEmployeeId(employeeId)
    )
}
