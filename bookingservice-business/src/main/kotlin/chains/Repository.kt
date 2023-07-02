package chains

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.chain
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on

fun <T : BsContext> CorChainDsl<T>.repository(title: String, block: CorChainDsl<T>.() -> Unit) = chain {
    this.title = title
    on { state == BsState.RUNNING }
    block()
}