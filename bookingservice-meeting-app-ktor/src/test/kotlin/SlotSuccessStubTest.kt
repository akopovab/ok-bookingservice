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
import ru.otuskotlin.public.bookingservice.stubs.SlotStub

class SlotSuccessStubTest : FunSpec({

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
            val searchRequest = SlotSearchRequest(
                requestType = "search",
                requestId = "123",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                employeeId = "123"
            )
            val response = client.post("/api/slot/search") {
                contentType(ContentType.Application.Json)
                setBody(searchRequest)
            }
            val searchResponse =  response.body<SlotSearchResponse>()
            val responseStub = SlotStub.getSlots()
            response shouldHaveStatus HttpStatusCode.OK
            searchResponse.requestId shouldBe "123"
            searchResponse.slots?.get(0)?.slotId shouldBe responseStub.toList()[0].id.asString()
            searchResponse.slots?.get(1)?.slotId shouldBe responseStub.toList()[1].id.asString()
        }
    }
})