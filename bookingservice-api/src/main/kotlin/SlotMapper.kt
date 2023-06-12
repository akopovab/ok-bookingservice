package ru.otuskotlin.public.bookingservice.api

import ru.otuskotlin.public.bookingservice.api.models.ISlotRequest
import ru.otuskotlin.public.bookingservice.api.models.ISlotResponse

fun apiSlotRequestSerialize(request: ISlotRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : ISlotRequest> apiSlotRequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, ISlotRequest::class.java) as T

fun apiSlotResponseSerialize(response: ISlotResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : ISlotResponse> apiSlotResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, ISlotResponse::class.java) as T
