package ru.otuskotlin.public.bookingservice.mappers

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportSlot
import kotlin.test.Test
import kotlin.test.assertEquals

class FromTransportMapperTest {

    @Test
    fun fromTransportMeeting() {
        val req = MeetingCreateRequest(
            requestId = "123",
            debug = Debug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            ),
            meeting = MeetingCreateObject(
                clientId = "client12",
                employeeId = "empl123",
                description = "На семинар",
                slots = mutableListOf(SlotIdListSlotsInner(slotId = "123"))
            )
        )

        val context = BsMeetingContext()
        context.fromTransportMeeting(req)

        assertEquals("123", context.requestId.asString())
        assertEquals(BsStubs.SUCCESS, context.stub)
        assertEquals(BsWorkMode.STUB, context.workMode)
        assertEquals("client12", context.meetingRequest.clientId.asString())
        assertEquals("empl123", context.meetingRequest.employeeId.asString())
        assertEquals("На семинар", context.meetingRequest.description)
        assertEquals("123", context.meetingRequest.slots.firstOrNull()?.id?.asString()  )
    }
    @Test

    fun fromTransportSlots() {
        val req = SlotSearchRequest(
            requestId = "123",
            debug = Debug(
                mode = RequestDebugMode.STUB,
                stub = RequestDebugStubs.SUCCESS
            ),
            employeeId = "empl123"

        )

        val context = BsSlotContext()
        context.fromTransportSlot(req)

        assertEquals("123", context.requestId.asString())
        assertEquals(BsStubs.SUCCESS, context.stub)
        assertEquals(BsWorkMode.STUB, context.workMode)
        assertEquals("empl123", context.slotRequest.asString())
    }

}