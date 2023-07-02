package ru.otuskotlin.public.bookingservice.business

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.business.processors.MeetingProcessor
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

class StubMeetingTest : FunSpec({

    test("STUB test: meeting create success") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
            meetingRequest = MeetingStub.getMeeting()
        }
        val stub = MeetingStub.getMeeting().copy()
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
        ctx.meetingResponse shouldBe stub
    }

    test("STUB test: meeting create bad clientId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.BAD_CLIENT_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong client id"
    }

    test("STUB test: meeting create bad slotId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.BAD_SLOT_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong slot id"
    }

    test("STUB test: meeting create bad EmployeeId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.BAD_EMPLOYEE_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }

    test("STUB test: meeting create bad description") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.BAD_DESCRIPTION
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong description"
    }

    test("STUB test: meeting create slot id reserved") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.CREATE
            stub = BsStubs.SLOT_ID_RESERVED
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Slot reserved"
    }

    test("STUB test: meeting read success") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.READ
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
            meetingRequest = MeetingStub.getMeeting()
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
        ctx.meetingResponse shouldBe MeetingStub.getMeeting()
    }

    test("STUB test: meeting read bad id") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.READ
            stub = BsStubs.BAD_MEETING_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong meeting id"
    }

    test("STUB test: meeting update success") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
            meetingRequest = MeetingStub.getMeeting()
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
        ctx.meetingResponse shouldBe MeetingStub.getMeeting()
    }

    test("STUB test: meeting update bad clientId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.BAD_CLIENT_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong client id"
    }

    test("STUB test: meeting update bad slotId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.BAD_SLOT_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong slot id"
    }

    test("STUB test: meeting update bad EmployeeId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.BAD_EMPLOYEE_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }

    test("STUB test: meeting update bad description") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.BAD_DESCRIPTION
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong description"
    }

    test("STUB test: meeting update slot id reserved") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.SLOT_ID_RESERVED
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Slot reserved"
    }

    test("STUB test: meeting delete success") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.DELETE
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
    }

    test("STUB test: meeting delete bad id") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.UPDATE
            stub = BsStubs.BAD_MEETING_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong meeting id"
    }

    test("STUB test: meeting search success") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.SEARCH
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
        ctx.meetingsResponse shouldBe MeetingStub.getMeetings()
    }

    test("STUB test: meeting search bad employeeId") {
        val ctx = BsMeetingContext().apply {
            command = BsMeetingCommand.SEARCH
            stub = BsStubs.BAD_EMPLOYEE_ID
            workMode = BsWorkMode.STUB
        }
        MeetingProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }




})