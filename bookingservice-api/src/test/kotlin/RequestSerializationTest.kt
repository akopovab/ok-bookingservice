package ru.otuskotlin.public.bookingservice.api

import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import ru.otuskotlin.public.bookingservice.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = MeetingCreateRequest(
        requestType = "create",
        requestId = "requestId123id",
        debug = Debug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.BAD_CLIENT_ID
        ),
        meeting = MeetingCreateObject(
            clientId = "clientId123id",
            employeeId = "employeeId123id",
            slots = listOf(SlotIdListSlotsInner("123")),
            description = "Стрижка на свадьбу, очень переживаю. До встречи!"
        )
    )

    @Test
    fun serialize() {

        val json = apiV1Mapper.writeValueAsString(request)
        assertContains(json, Regex("\"requestId\":\\s*\"requestId123id\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badClientId\""))
        assertContains(json, Regex("\"clientId\":\\s*\"clientId123id\""))
        assertContains(json, Regex("\"clientId\":\\s*\"clientId123id\""))
        assertContains(json, Regex("\"employeeId\":\\s*\"employeeId123id\""))
        assertContains(json, Regex("\"slotId\":\\s*\"123\""))
        assertContains(json, Regex("\"description\":\\s*\"Стрижка на свадьбу, очень переживаю. До встречи!\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IMeetingRequest::class.java) as MeetingCreateRequest
        assertEquals(request, obj)
    }
}