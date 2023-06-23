package ru.otuskotlin.public.bookingservice.business.workers

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker

fun <T: BsContext>CorChainDsl<T>.initStatus(title :String) = worker{
    this.title = title
    handle {state = BsState.RUNNING}
    on { state == BsState.NONE }
    //on { false }
}