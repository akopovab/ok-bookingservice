package ru.otuskotlin.public.bookingservice.lib.cor.handle

import ru.otuskotlin.public.bookingservice.lib.cor.CorExec

class CorWorker<T>(
    override val title: String,
    override val description: String = "",
    private val blockHandle: suspend T.() -> Unit = {},
    blockOn: suspend T.() -> Boolean = { true },
    blockException: suspend T.(Throwable) -> Unit = {throw it}
) : CorExec<T>(title, description, blockOn, blockException) {

    override suspend fun T.handle() = blockHandle()

}