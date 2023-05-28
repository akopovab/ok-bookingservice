package ru.otuskotlin.public.bookingservice.mappers

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.context.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import kotlin.test.Test
import kotlin.test.assertEquals

class ToTransportMapperTest {

    @Test
    fun toTransportMeeting() {
        val context = BsMeetingContext(
            requestId = BsRequestId("123"),
            command = BsMeetingCommand.CREATE,
            meetingResponse = BsMeeting(
                id = BsMeetingId("111"),
                employeeId = BsEmployeeId("321"),
                clientId = BsClientId("456"),
                meetingStatus = BsMeetingStatus.CREATE,
                description = "Запись на  мастеркласс",
                meetingPermissions = mutableSetOf(BsMeetingPermissions.READ),
                slots = mutableSetOf(
                    BsSlot(
                        id = BsSlotId("slotId123"),
                        startDate = Instant.parse("2023-05-20T12:00:00Z"),
                        endDate = Instant.parse("2023-05-20T12:30:00Z"),
                        slotStatus = BsSlotStatus.RESERVED
                    )
                )
            )
        )

        val resp = context.toTransportMeeting() as MeetingCreateResponse

        assertEquals("123", resp.requestId)
        assertEquals(resp.meeting?.meetingId, "111")
        assertEquals(resp.meeting?.employeeId, "321")
        assertEquals(resp.meeting?.clientId, "456")
        assertEquals(resp.meeting?.status, MeetingStatus.CREATE)
        assertEquals(resp.meeting?.description, "Запись на  мастеркласс")
        assertEquals(resp.meeting?.meetingPermissions?.firstOrNull(), MeetingPermissions.READ)
        assertEquals(resp.meeting?.slots?.firstOrNull()?.slotId, "slotId123")
        assertEquals(resp.meeting?.slots?.firstOrNull()?.startDate, "2023-05-20T12:00:00Z")
        assertEquals(resp.meeting?.slots?.firstOrNull()?.endDate, "2023-05-20T12:30:00Z")
        assertEquals(resp.meeting?.slots?.firstOrNull()?.slotStatus, SlotStatus.RESERVED)
    }

    @Test
    fun toTransportSlots() {
        val context = BsSlotContext(
            requestId = BsRequestId("123"),
            command = BsSlotCommand.SEARCH,
            slotResponse = mutableSetOf(
                BsSlot(
                    id = BsSlotId("slotId123"),
                    startDate = Instant.parse("2023-05-20T12:00:00Z"),
                    endDate = Instant.parse("2023-05-20T12:30:00Z"),
                    slotStatus = BsSlotStatus.RESERVED
                )
            )

        )
        val resp = context.toTransportMeeting() as SlotSearchResponse

        assertEquals("123", resp.requestId)
        assertEquals(resp.slots?.firstOrNull()?.slotId, "slotId123")
        assertEquals(resp.slots?.firstOrNull()?.startDate, "2023-05-20T12:00:00Z")
        assertEquals(resp.slots?.firstOrNull()?.endDate, "2023-05-20T12:30:00Z")
        assertEquals(resp.slots?.firstOrNull()?.slotStatus, SlotStatus.RESERVED)
    }


}