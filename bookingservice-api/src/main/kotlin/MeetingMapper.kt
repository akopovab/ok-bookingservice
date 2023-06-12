package ru.otuskotlin.public.bookingservice.api

import ru.otuskotlin.public.bookingservice.api.models.IMeetingRequest
import ru.otuskotlin.public.bookingservice.api.models.IMeetingResponse

fun apiMeetingRequestSerialize(request: IMeetingRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IMeetingRequest> apiMeetingRequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IMeetingRequest::class.java) as T

fun apiMeetingResponseSerialize(response: IMeetingResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IMeetingResponse> apiMeetingResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IMeetingResponse::class.java) as T
