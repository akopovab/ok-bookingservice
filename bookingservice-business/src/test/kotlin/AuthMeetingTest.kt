package ru.otuskotlin.public.bookingservice.business

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import ru.otuskotlin.public.bookingservice.business.processors.MeetingProcessor
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipal
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserGroup
import ru.otuskotlin.public.bookingservice.repo.inmemory.MeetingRepoInMemory
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub
import ru.otuskotlin.public.bookingservice.stubs.SlotStub
import java.util.*

class AuthMeetingTest : FunSpec({
    val processor by lazy { MeetingProcessor() }


    test("success create meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctx = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = clientId, id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctx)

            ctx.errors.size shouldBe 0
            ctx.state shouldBe BsState.FINISHING
            ctx.permitted shouldBe true
        }

    }

    test("failed create meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctx = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = BsClientId("NO_VALID_CLIENT_ID"), id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctx)

            ctx.errors.size shouldBe 1
            ctx.state shouldBe BsState.FAILING
            ctx.permitted shouldBe false
        }

    }


    test("success update meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctxCreate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = clientId, id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctxCreate)

            val ctxUpdate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.UPDATE,
                meetingRequest = MeetingStub.getMeeting().copy(
                    clientId = clientId,
                    id = ctxCreate.meetingResponse.id,
                    lock = ctxCreate.meetingResponse.lock,
                    description = "success update meeting test"
                ),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )

            processor.exec(ctxUpdate)

            ctxUpdate.errors.size shouldBe 0
            ctxUpdate.meetingResponse.description shouldBe "success update meeting test"
            ctxUpdate.state shouldBe BsState.FINISHING
            ctxUpdate.permitted shouldBe true
        }

    }

    test("failed update meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctxCreate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = clientId, id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctxCreate)

            val ctxUpdate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.UPDATE,
                meetingRequest = MeetingStub.getMeeting().copy(
                    clientId = clientId,
                    id = ctxCreate.meetingResponse.id,
                    lock = ctxCreate.meetingResponse.lock,
                    description = "failed update meeting test"
                ),
                principal = BsPrincipal(
                    id = clientId.asString()
                )
            )

            processor.exec(ctxUpdate)

            ctxUpdate.errors.size shouldBe 1
            ctxUpdate.state shouldBe BsState.FAILING
            ctxUpdate.permitted shouldBe false
        }
    }

    test("success read meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctxCreate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = clientId, id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctxCreate)

            val ctxRead = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.READ,
                meetingRequest = BsMeeting(id = ctxCreate.meetingResponse.id),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )

            processor.exec(ctxRead)
            println(ctxRead.errors)
            ctxRead.errors.size shouldBe 0
            ctxRead.state shouldBe BsState.FINISHING
            ctxRead.permitted shouldBe true
        }

    }

    test("failed read meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val repo = MeetingRepoInMemory()

            val ctxCreate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(clientId = clientId, id = BsMeetingId.NONE),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctxCreate)

            val ctxRead = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.READ,
                meetingRequest = BsMeeting(id = ctxCreate.meetingResponse.id),
                principal = BsPrincipal(
                    id = "NO_VALID_CLIENT_ID",
                    groups = setOf(BsUserGroup.USER)
                )
            )

            processor.exec(ctxRead)

            ctxRead.errors.size shouldBe 1
            ctxRead.state shouldBe BsState.FAILING
            ctxRead.permitted shouldBe false
        }

    }

    test("success search meeting test") {
        runTest {
            val clientId = BsClientId(UUID.randomUUID().toString())
            val slot = SlotStub.getSlots().first()
            val repo = MeetingRepoInMemory()

            val ctxCreate = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.CREATE,
                meetingRequest = MeetingStub.getMeeting().copy(
                    clientId = clientId,
                    id = BsMeetingId.NONE,
                    employeeId = slot.employeeId,
                    slots = mutableSetOf(slot)
                ),
                principal = BsPrincipal(
                    id = clientId.asString(),
                    groups = setOf(BsUserGroup.USER)
                )
            )
            processor.exec(ctxCreate)
            println(ctxCreate.errors)

            val ctxSearch = BsMeetingContext(
                workMode = BsWorkMode.TEST,
                settings = BsCorSettings(repoSettings = mapOf(Pair(BsWorkMode.TEST, repo))),
                command = BsMeetingCommand.SEARCH,
                meetingSearchRequest = slot.employeeId,
                principal = BsPrincipal(
                    id = slot.employeeId.asString(),
                    groups = setOf(BsUserGroup.EMPLOYEE)
                )
            )

            processor.exec(ctxSearch)
            println(ctxSearch.errors)
            ctxSearch.errors.size shouldBe 0
            ctxSearch.state shouldBe BsState.FINISHING
            ctxSearch.permitted shouldBe true
            ctxSearch.meetingsResponse.size shouldBe 1
        }

    }
}
)