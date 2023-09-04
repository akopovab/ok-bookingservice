package ru.otuskotlin.public.bookingservice.business.workers.access

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun <T : BsMeetingContext> CorChainDsl<T>.validatePermission(title: String) = worker {
    this.title = title
    handle {
        addError(
            group = "validation",
            code = "validation-permitted",
            field = "permissions",
            message = "User is not allowed to perform this operation"
        )
        state = BsState.FAILING
    }
    on { !permitted  }

}