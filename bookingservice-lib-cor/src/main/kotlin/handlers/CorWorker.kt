package ru.otuskotlin.public.bookingservice.lib.cor.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.CorExec

class CorWorker<T>(
    title: String,
    description: String = "",
    private val blockHandle: suspend T.() -> Unit = {},
    blockOn: suspend T.() -> Boolean = { true },
    blockException: suspend T.(Throwable) -> Unit = {throw it}
) : CorExec<T>(title, description, blockOn, blockException) {

    override suspend fun T.handle() = blockHandle()

}





