package ru.otuskotlin.public.bookingservice.common.models.slot

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE

class BsSlot {
    var id = BsSlotId.NONE
    var startDate = Instant.NONE
    var endDate = Instant.NONE
    var slotStatus = BsSlotStatus.NONE
}