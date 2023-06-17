package ru.otuskotlin.public.bookingservice.lib.cor.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.CorExec
import ru.otuskotlin.public.bookingservice.lib.cor.ICorExec

class CorChain<T>(
    override val title: String,
    override val description: String = "",
    private val handles: List<ICorExec<T>>,
    blockOn: suspend T.() -> Boolean = { true },
    private val strategy: suspend (T, List<ICorExec<T>>) -> Unit = ::executeSequential,
    blockException: suspend T.(Throwable) -> Unit = {}
) : CorExec<T>(title, description, blockOn, blockException) {

    override suspend fun T.handle() = strategy(this, handles)

}





