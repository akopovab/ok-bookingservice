package ru.otuskotlin.public.bookingservice

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.test.Test
import ru.otuskotlin.public.bookingservice.api.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = MeetingCreateResponse(
        requestId = "requestIdId123id",
        result = ResponseResult.SUCCESS,
        meeting = MeetingObject(
            meetingId = "123",
            clientId = "456",
            employeeId = "789",
            slots = listOf("s123", "s345"),
            description = "Немного задержусь. Если что - позвоню!",
            status = MeetingStatus.CREATE,
            meetingPermissions = setOf(MeetingPermissions.READ)
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        assertContains(json, Regex("\"requestId\":\\s*\"requestIdId123id\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"meetingId\":\\s*\"123\""))
        assertContains(json, Regex("\"clientId\":\\s*\"456\""))
        assertContains(json, Regex("\"employeeId\":\\s*\"789\""))
        assertContains(json, Regex("\"slots\":\\W*\"s123\""))
        assertContains(json, Regex("\"description\":\\s*\"Немного задержусь. Если что - позвоню!\""))
        assertContains(json, Regex("\"status\":\\s*\"CREATE\""))
        assertContains(json, Regex("\"meetingPermissions\":\\W*\"READ\""))

    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IMeetingResponse::class.java) as MeetingCreateResponse
        assertEquals(response, obj)
    }

}