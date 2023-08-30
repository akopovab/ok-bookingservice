package ru.otuskotlin.public.bookingservice.business.workers.access

import ru.otuskotlin.public.bookingservice.auth.resolvePermissions
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker

fun <T : BsMeetingContext> CorChainDsl<T>.resolvePermission(title: String) = worker {
    this.title = title
    handle {
        permissions.addAll(resolvePermissions(principal.groups))
    }
    on { state == BsState.RUNNING }

}