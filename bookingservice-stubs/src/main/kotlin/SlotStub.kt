package ru.otuskotlin.public.bookingservice.stubs

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

object SlotStub {

    fun getSlots() = mutableSetOf(
        BsSlot(
            id = BsSlotId("123000111"),
            startDate = Instant.parse("2023-07-20T12:30:00Z"),
            endDate = Instant.parse("2023-07-20T13:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000222"),
            startDate = Instant.parse("2023-07-20T13:00:00Z"),
            endDate = Instant.parse("2023-07-20T13:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000333"),
            startDate = Instant.parse("2023-07-20T13:30:00Z"),
            endDate = Instant.parse("2023-07-20T14:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000444"),
            startDate = Instant.parse("2023-06-20T15:00:00Z"),
            endDate = Instant.parse("2023-07-20T15:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000555"),
            startDate = Instant.parse("2023-06-20T15:30:00Z"),
            endDate = Instant.parse("2023-07-20T16:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000666"),
            startDate = Instant.parse("2023-06-20T16:00:00Z"),
            endDate = Instant.parse("2023-07-20T16:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000777"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000888"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000999"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000000"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        )
    )

}