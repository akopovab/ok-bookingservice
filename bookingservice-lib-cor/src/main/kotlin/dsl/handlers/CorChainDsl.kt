package ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.ICorExec
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.CorDslMarker
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.CorExecDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.ICorExecDsl
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.CorChain
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeSequential
@CorDslMarker
class CorChainDsl<T>() : CorExecDsl<T>() {
    override var title: String = ""
    override var description: String = ""
    private var handlers: MutableList<ICorExecDsl<T>> = mutableListOf()
    var strategy: suspend (T, List<ICorExec<T>>) -> Unit = ::executeSequential
    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        handles = handlers.map { it.build() },
        blockOn = blockOn,
        strategy = strategy,
        blockException = blockException
    )

    fun add(worker: ICorExecDsl<T>) {
        handlers.add(worker)
    }
}