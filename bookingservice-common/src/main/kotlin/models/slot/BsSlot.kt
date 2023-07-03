package ru.otuskotlin.public.bookingservice.common.models.slot

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId

data class BsSlot (
    var id :BsSlotId = BsSlotId.NONE,
    var startDate :Instant = Instant.NONE,
    var endDate :Instant = Instant.NONE,
    var slotStatus :BsSlotStatus = BsSlotStatus.NONE,
    var employeeId :BsEmployeeId = BsEmployeeId.NONE
)