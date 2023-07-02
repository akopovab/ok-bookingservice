package ru.otuskotlin.public.bookingservice.business

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.business.processors.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.stubs.SlotStub

class StubSlotTest : FunSpec({

    test("STUB test: slot search success") {
        val ctx = BsSlotContext().apply {
            command = BsSlotCommand.SEARCH
            stub = BsStubs.SUCCESS
            workMode = BsWorkMode.STUB
        }
        SlotProcessor().exec(ctx)
        ctx.state shouldBe BsState.FINISHING
        ctx.slotResponse shouldBe SlotStub.getSlots()
    }

    test("STUB test: slot search bad employeeId") {
        val ctx = BsSlotContext().apply {
            command = BsSlotCommand.SEARCH
            stub = BsStubs.BAD_EMPLOYEE_ID
            workMode = BsWorkMode.STUB
        }
        SlotProcessor().exec(ctx)
        ctx.state shouldBe BsState.FAILING
        ctx.errors[0].message shouldBe "Wrong employee id"

    }

})