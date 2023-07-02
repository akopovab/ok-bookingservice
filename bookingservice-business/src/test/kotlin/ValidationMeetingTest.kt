package ru.otuskotlin.public.bookingservice.business

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.business.processors.MeetingProcessor
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsClientId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

class ValidationMeetingTest : FunSpec({

    test ("Validation test: create meeting, bad clientId"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { clientId = BsClientId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong client id"
    }

    test ("Validation test: create meeting, bad employeeId"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { employeeId = BsEmployeeId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }

    test ("Validation test: create meeting, bad description"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { (0..1000).forEach { description = description.plus("a")} }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong description, length must not exceed 1000 characters"
    }

    test ("Validation test: create meeting, bad slots"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { slots = mutableSetOf() }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong slots"
    }

    test ("Validation test: read meeting, bad id"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.READ
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { id = BsMeetingId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong meeting id"
    }

    test ("Validation test: update meeting, bad id"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { id = BsMeetingId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong meeting id"
    }

    test ("Validation test: update meeting, bad clientId"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { clientId = BsClientId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong client id"
    }

    test ("Validation test: update meeting, bad employeeId"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { employeeId = BsEmployeeId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }

    test ("Validation test: update meeting, bad description"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { (0..1000).forEach { description = description.plus("a")} }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong description, length must not exceed 1000 characters"
    }

    test ("Validation test: update meeting validation, bad slots"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { slots = mutableSetOf() }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong slots"
    }

    test ("Validation test: delete meeting, bad id"){
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.DELETE
            workMode = BsWorkMode.PROD
            meetingRequest = MeetingStub.getMeeting().copy().apply { id = BsMeetingId.NONE }
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong meeting id"
    }

    test ("Validation test: search meeting, bad employeeId"){
        val ctx = BsMeetingContext().copy().apply {
            command = BsMeetingCommand.SEARCH
            workMode = BsWorkMode.PROD
            meetingSearchRequest = BsEmployeeId.NONE
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }


})