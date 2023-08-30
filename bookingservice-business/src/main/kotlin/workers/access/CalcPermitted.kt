package ru.otuskotlin.public.bookingservice.business.workers.access

import ru.otuskotlin.public.bookingservice.auth.checkPermitted
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker

fun <T : BsMeetingContext> CorChainDsl<T>.calcPermitted(title: String) = worker {
    this.title = title
    handle {
        permitted = checkPermitted(command, meetingRepoPrepare.principalRelation, permissions)
    }
    on { state == BsState.RUNNING }

}