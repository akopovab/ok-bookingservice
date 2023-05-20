package ru.otuskotlin.public.bookingservice

import ru.otuskotlin.public.bookingservice.api.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = MeetingCreateRequest(
        requestId = "requestId123id",
        debug = Debug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.BAD_CLIENT_ID
        ),
        meeting = BaseMeeting(
            clientId = "clientId123id",
            employeeId = "employeeId123id",
            slots = listOf("abc", "123", "456"),
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
        assertContains(json, Regex("\"slots\":\\W*\"abc\""))
        assertContains(json, Regex("\"description\":\\s*\"Стрижка на свадьбу, очень переживаю. До встречи!\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IMeetingRequest::class.java) as MeetingCreateRequest
        assertEquals(request, obj)
    }
}