package ru.otuskotlin.public.bookingservice.business

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.business.processors.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand

class ValidationSlotTest : FunSpec({

    test("Validation test: search slot, bad slot") {
        val ctx = BsSlotContext().apply {
            command = BsMeetingCommand.CREATE
            workMode = BsWorkMode.PROD
            slotRequest = BsEmployeeId.NONE
        }
        SlotProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"
    }
})