package ru.otuskotlin.public.bookingservice.meeting

import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.Test
import io.kotest.matchers.shouldBe
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@Test
class MeetingSuccessStubTest : FunSpec({

    test("Create request success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }

            val createRequest = MeetingCreateRequest(
                requestType = "create",
                requestId = "123",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                meeting = MeetingCreateObject(
                    clientId = "123",
                    employeeId = "123",
                    description = "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик вышел из строя",
                    slots = listOf(SlotIdListSlotsInner("123"), SlotIdListSlotsInner("456"))
                )
            )

            val response = client.post("/api/meeting/create") {
                contentType(ContentType.Application.Json)
                setBody(createRequest)
            }
            val meetingUpdateRequest = response.body<MeetingCreateResponse>()

            response shouldHaveStatus HttpStatusCode.OK
            meetingUpdateRequest.meeting?.meetingId shouldBe "1234567890"
            meetingUpdateRequest.meeting?.meetingId shouldBe "1234567890"
            meetingUpdateRequest.meeting?.description shouldBe "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик вышел из строя"
            meetingUpdateRequest.meeting?.slots?.get(0)?.slotId shouldBe "123"
            meetingUpdateRequest.meeting?.slots?.get(1)?.slotId shouldBe "456"
            meetingUpdateRequest.requestId shouldBe "123"
        }
    }

    test("update success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }
            val updateRequest = MeetingUpdateRequest(
                requestType = "update",
                requestId = "12",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                meeting = MeetingUpdateObject(
                    meetingId = "34",
                    clientId = "56",
                    employeeId = "78",
                    description = "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик выше из строя",
                    slots = listOf(SlotIdListSlotsInner("90"), SlotIdListSlotsInner("910"))
                )
            )
            val response = client.post("/api/meeting/update") {
                contentType(ContentType.Application.Json)
                setBody(updateRequest)
            }
            val meetingUpdateResponse = response.body<MeetingUpdateResponse>()

            response shouldHaveStatus HttpStatusCode.OK
            meetingUpdateResponse.meeting?.meetingId shouldBe "34"
            meetingUpdateResponse.meeting?.clientId shouldBe "56"
            meetingUpdateResponse.meeting?.employeeId shouldBe "78"
            meetingUpdateResponse.meeting?.description shouldBe "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик выше из строя"
            meetingUpdateResponse.meeting?.slots?.get(0)?.slotId shouldBe "90"
            meetingUpdateResponse.meeting?.slots?.get(1)?.slotId shouldBe "910"
        }
    }

    test("read success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }
            val readRequest = MeetingReadRequest(
                requestType = "read",
                requestId = "123",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                meeting = MeetingReadObject(
                    meetingId = "123",
                )
            )
            val response = client.post("/api/meeting/read") {
                contentType(ContentType.Application.Json)
                setBody(readRequest)
            }

            val meetingReadResponse = response.body<MeetingReadResponse>()

            response shouldHaveStatus HttpStatusCode.OK
            meetingReadResponse.meeting?.meetingId shouldBe "123"
            meetingReadResponse.meeting?.description shouldBe MeetingStub.getMeeting().description
            meetingReadResponse.meeting?.clientId shouldBe MeetingStub.getMeeting().clientId.asString()
        }
    }

    test("delete success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }
            val deleteRequest = MeetingDeleteRequest(
                requestType = "delete",
                requestId = "123",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                meeting = MeetingReadObject(
                    meetingId = "123",
                )
            )
            val response = client.post("/api/meeting/delete") {
                contentType(ContentType.Application.Json)
                setBody(deleteRequest)
            }

            response shouldHaveStatus HttpStatusCode.OK
        }
    }

    test("search success stub") {
        testApplication {
            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                        setConfig(apiV1Mapper.serializationConfig)
                        setConfig(apiV1Mapper.deserializationConfig)
                    }
                }
            }
            val searchRequest = MeetingSearchRequest(
                requestType = "search",
                requestId = "123",
                debug = Debug(
                    mode = RequestDebugMode.STUB,
                    stub = RequestDebugStubs.SUCCESS
                ),
                employeeId = "123"
            )
            val response = client.post("/api/meeting/search") {
                contentType(ContentType.Application.Json)
                setBody(searchRequest)
            }
            val meetingSearchResponse = response.body<MeetingSearchResponse>()
            val responseStub = MeetingStub.getMeetings()

            response shouldHaveStatus HttpStatusCode.OK
            meetingSearchResponse.meetings?.get(0)?.meetingId shouldBe responseStub[0].id.asString()
            meetingSearchResponse.meetings?.get(0)?.description shouldBe responseStub[0].description
            meetingSearchResponse.meetings?.get(0)?.clientId shouldBe responseStub[0].clientId.asString()
        }
    }

})