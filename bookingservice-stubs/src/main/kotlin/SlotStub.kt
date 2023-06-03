package ru.otuskotlin.public.bookingservice.stubs

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

object SlotStub {

    fun getSlots() = mutableSetOf(
        BsSlot(
            id = BsSlotId("123000111"),
            startDate = Instant.parse("2023-06-20T12:30:00Z"),
            endDate = Instant.parse("2023-06-20T13:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
        ),
        BsSlot(
            id = BsSlotId("1230002222"),
            startDate = Instant.parse("2023-06-20T13:00:00Z"),
            endDate = Instant.parse("2023-06-20T13:30:00Z"),
            slotStatus = BsSlotStatus.RESERVED,
        )
    )

}