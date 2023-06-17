package ru.otuskotlin.public.bookingservice.lib.cor.handle

import ru.otuskotlin.public.bookingservice.lib.cor.CorExec
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeSequential

class CorChain<T>(
    override val title: String,
    override val description: String = "",
    private val handles: List<CorWorker<T>>,
    blockOn: suspend T.() -> Boolean = {true},
    private val strategy: suspend (T, List<CorWorker<T>>) -> Unit = ::executeSequential,
    blockException: suspend T.(Throwable) -> Unit = {}
) : CorExec<T>(title, description, blockOn, blockException) {

    override suspend fun T.handle() = strategy(this, handles)

}