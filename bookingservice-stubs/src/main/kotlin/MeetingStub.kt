package ru.otuskotlin.public.bookingservice.stubs

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

object MeetingStub {

    private val bsMeetingCaseOne = BsMeeting(
        id = BsMeetingId("1234567890"),
        employeeId = BsEmployeeId("444"),
        clientId = BsClientId("1230984567"),
        meetingStatus = BsMeetingStatus.CREATED,
        description = "Запись на стрижку",
        slots = mutableSetOf(
            BsSlot(
                id = BsSlotId("123000111"),
                startDate = Instant.parse("2023-05-20T12:00:00Z"),
                endDate = Instant.parse("2023-05-20T12:30:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            ), BsSlot(
                id = BsSlotId("123000222"),
                startDate = Instant.parse("2023-05-20T12:30:00Z"),
                endDate = Instant.parse("2023-05-20T13:00:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            )
        ),
        meetingPermissions = mutableSetOf(BsMeetingPermissions.READ),
        lock = BsMeetingLock("44444444")
    )

    private val bsMeetingCaseTwo = BsMeeting(
        id = BsMeetingId("000000000000"),
        employeeId = BsEmployeeId("555"),
        clientId = BsClientId("22222222222"),
        meetingStatus = BsMeetingStatus.CREATED,
        description = "Запись на стрижку",
        slots = mutableSetOf(
            BsSlot(
                id = BsSlotId("123000666"),
                startDate = Instant.parse("2023-06-20T12:00:00Z"),
                endDate = Instant.parse("2023-06-20T12:30:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            ), BsSlot(
                id = BsSlotId("123000777"),
                startDate = Instant.parse("2023-06-20T12:30:00Z"),
                endDate = Instant.parse("2023-06-20T13:00:00Z"),
                slotStatus = BsSlotStatus.RESERVED
            )
        ),
        meetingPermissions = mutableSetOf(BsMeetingPermissions.READ),
        lock = BsMeetingLock("333444")
    )

    private val bsMeetingForUpdate = BsMeeting(
        id = BsMeetingId("000001111111"),
        employeeId = BsEmployeeId("444"),
        clientId = BsClientId("22222222222"),
        description = "Запись на стрижку",
        slots = mutableSetOf(
            BsSlot(
                id = BsSlotId("123000111"),
            ), BsSlot(
                id = BsSlotId("123000222"),
            )
        ),
        lock = BsMeetingLock("test123test")
    )


    fun getMeeting() = bsMeetingCaseOne

    fun getMeetings() = mutableListOf(bsMeetingCaseOne, bsMeetingCaseTwo)

    fun getMeetingForUpdate() = bsMeetingForUpdate



}