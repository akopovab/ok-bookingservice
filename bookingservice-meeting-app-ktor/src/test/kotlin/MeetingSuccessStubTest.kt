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
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@Test
class MeetingSuccessStubTest : FunSpec({

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
            description = "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик выше из строя",
            slots = listOf(SlotIdListSlotsInner("123"), SlotIdListSlotsInner("456"))
        )
    )

    val createResponse = BsMeetingContext().apply {
        requestId = BsRequestId("123")
        meetingResponse = MeetingStub.getMeeting()
        command = BsMeetingCommand.CREATE
    }.toTransportMeeting()

    val updateRequest = MeetingUpdateRequest(
        requestType = "update",
        requestId = "123",
        debug = Debug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.SUCCESS
        ),
        meeting = MeetingUpdateObject(
            meetingId = "123",
            clientId = "123",
            employeeId = "123",
            description = "Ремонт коробки передач, дёргается на светофорах, скорее всего маховик выше из строя",
            slots = listOf(SlotIdListSlotsInner("123"), SlotIdListSlotsInner("456"))
        )
    )

    val updateResponse = BsMeetingContext().apply {
        requestId = BsRequestId("123")
        meetingResponse = MeetingStub.getMeeting()
        command = BsMeetingCommand.UPDATE
    }.toTransportMeeting()

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

    val readResponse = BsMeetingContext().apply {
        requestId = BsRequestId("123")
        meetingResponse = MeetingStub.getMeeting()
        command = BsMeetingCommand.READ
    }.toTransportMeeting()


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

    val deleteResponse = BsMeetingContext().apply {
        requestId = BsRequestId("123")
        command = BsMeetingCommand.DELETE
    }.toTransportMeeting()

    val searchRequest = MeetingSearchRequest(
        requestType = "search",
        requestId = "123",
        debug = Debug(
            mode = RequestDebugMode.STUB,
            stub = RequestDebugStubs.SUCCESS
        ),
        employeeId = "123"
    )

    val searchResponse = BsMeetingContext().apply {
        requestId = BsRequestId("123")
        meetingsResponse = MeetingStub.getMeetings()
        command = BsMeetingCommand.SEARCH
    }.toTransportMeeting()


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
            val response = client.post("/api/meeting/create") {
                contentType(ContentType.Application.Json)
                setBody(createRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as MeetingCreateResponse shouldBe createResponse
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
            val response = client.post("/api/meeting/update") {
                contentType(ContentType.Application.Json)
                setBody(updateRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as MeetingUpdateResponse shouldBe updateResponse
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
            val response = client.post("/api/meeting/read") {
                contentType(ContentType.Application.Json)
                setBody(readRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as MeetingReadResponse shouldBe readResponse
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
            val response = client.post("/api/meeting/delete") {
                contentType(ContentType.Application.Json)
                setBody(deleteRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as MeetingDeleteResponse shouldBe deleteResponse
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
            val response = client.post("/api/meeting/search") {
                contentType(ContentType.Application.Json)
                setBody(searchRequest)
            }
            response shouldHaveStatus HttpStatusCode.OK
            response.body() as MeetingSearchResponse shouldBe searchResponse
        }
    }

})