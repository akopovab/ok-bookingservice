package ru.otuskotlin.public.bookingservice.business.chains

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsCommand
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.chain
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on

 fun <T: BsContext> CorChainDsl<T>.operation(
     title: String,
     command: BsCommand,
     block: CorChainDsl<T>.() -> Unit
) = chain {
    this.title = title
    on { this.command == command && this.state == BsState.RUNNING }
    //on { true }
    block()
}