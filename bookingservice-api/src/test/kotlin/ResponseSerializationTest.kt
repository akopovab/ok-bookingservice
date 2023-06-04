package ru.otuskotlin.public.bookingservice.api

import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import kotlin.test.Test
import ru.otuskotlin.public.bookingservice.api.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = MeetingCreateResponse(
        requestId = "requestIdId123id",
        result = ResponseResult.SUCCESS,
        meeting = MeetingResponseObject(
            meetingId = "123",
            clientId = "456",
            employeeId = "789",
            slots = listOf(BaseSlots("123", "25.05.2023 12-00", "25.05.2023 12-30", SlotStatus.RESERVED)),
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
        assertContains(json, Regex("\"slotId\":\\s*\"123\""))
        assertContains(json, Regex("\"startDate\":\\s*\"25.05.2023 12-00\""))
        assertContains(json, Regex("\"endDate\":\\s*\"25.05.2023 12-30\""))
        assertContains(json, Regex("\"slotStatus\":\\s*\"RESERVED\""))
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