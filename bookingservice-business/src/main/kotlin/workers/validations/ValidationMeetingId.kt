package ru.otuskotlin.public.bookingservice.business.workers.validations

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.validationMeetingId(title: String) = worker {
    this.title = title
    on { meetingRequestValidation.id.asString().isNullOrEmpty() }
    handle {
        addError(
            group = "validation",
            code = "validation-id",
            field = "meetingId",
            message = "Wrong meeting id"
        )
        state = BsState.FAILING
    }
}