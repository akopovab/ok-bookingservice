package ru.otuskotlin.public.bookingservice.meeting

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportSlot
import ru.otuskotlin.public.bookingservice.stubs.SlotStub

class SlotSuccessStubTest : FunSpec({

    val searchRequest = SlotSearchRequest(
        requestType = "search",
        requestId = "123",
        debug = Debug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.SUCCESS
        ),
        employeeId = "123"
    )

    val searchResponse = BsSlotContext().apply {
        requestId = BsRequestId("123")
        slotResponse = SlotStub.getSlots()
        command = BsSlotCommand.SEARCH
    }.toTransportSlot()

    test("search slot success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }
            val response = client.post("/api/slot/search") {
                contentType(ContentType.Application.Json)
                setBody(searchRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as SlotSearchResponse shouldBe searchResponse
        }
    }
})