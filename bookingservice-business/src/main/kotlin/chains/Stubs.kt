package ru.otuskotlin.public.bookingservice.business.chains

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.*

fun <T : BsContext> CorChainDsl<T>.stubs(title: String, block: CorChainDsl<T>.() -> Unit) = chain {
    this.title = title
    on { state == BsState.RUNNING && workMode == BsWorkMode.STUB }
    block()
}