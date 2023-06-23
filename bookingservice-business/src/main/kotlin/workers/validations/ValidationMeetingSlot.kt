package ru.otuskotlin.public.bookingservice.business.workers.validations

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.validationMeetingSlot(title: String) = worker {
    this.title = title
    on {
        meetingRequestValidation.slots.isNullOrEmpty()
                || meetingRequestValidation.slots.filter {
            it.id.asString().isNotEmpty()
        }.size != meetingRequestValidation.slots.size
    }
    handle {
        addError(
            group = "validation",
            code = "validation-id",
            field = "slots",
            message = "Wrong slots"
        )
        state = BsState.FAILING
    }
}