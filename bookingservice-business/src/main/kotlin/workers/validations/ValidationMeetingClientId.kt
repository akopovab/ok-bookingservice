package ru.otuskotlin.public.bookingservice.business.workers.validations

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.validationMeetingClientId(title: String) = worker {
    this.title = title
    on { meetingRequestValidation.clientId.asString().isEmpty() }
    handle {
        addError(
            group = "validation",
            code = "validation-id",
            field = "clientId",
            message = "Wrong client id"
        )
        state = BsState.FAILING
    }
}