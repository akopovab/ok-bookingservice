package ru.otuskotlin.public.bookingservice.stubs

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

object MeetingStub {

    private val bsMeetingCaseOne = BsMeeting(
        id = BsMeetingId("1234567890"),
        employeeId = BsEmployeeId("0987654321"),
        clientId = BsClientId("1230984567"),
        meetingStatus = BsMeetingStatus.CREATE,
        description = "Запись на стрижку",
        slots = mutableSetOf(
            BsSlot(
                id = BsSlotId("123"),
                startDate = Instant.parse("2023-05-20T12:00:00Z"),
                endDate = Instant.parse("2023-05-20T12:30:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            ), BsSlot(
                id = BsSlotId("123"),
                startDate = Instant.parse("2023-05-20T12:30:00Z"),
                endDate = Instant.parse("2023-05-20T13:00:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            )
        ),
        meetingPermissions = mutableSetOf(BsMeetingPermissions.READ)
    )

    private val bsMeetingCaseTwo = BsMeeting(
        id = BsMeetingId("000000000000"),
        employeeId = BsEmployeeId("1111111111111"),
        clientId = BsClientId("22222222222"),
        meetingStatus = BsMeetingStatus.CREATE,
        description = "Запись на стрижку",
        slots = mutableSetOf(
            BsSlot(
                id = BsSlotId("000000000"),
                startDate = Instant.parse("2023-06-20T12:00:00Z"),
                endDate = Instant.parse("2023-06-20T12:30:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            ), BsSlot(
                id = BsSlotId("9999999999"),
                startDate = Instant.parse("2023-06-20T12:30:00Z"),
                endDate = Instant.parse("2023-06-20T13:00:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            )
        ),
        meetingPermissions = mutableSetOf(BsMeetingPermissions.READ)
    )


    fun getMeeting() = bsMeetingCaseOne

    fun getMeetings() = mutableListOf(bsMeetingCaseOne, bsMeetingCaseTwo)


}