package ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.ICorExec
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.CorExecDsl
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.CorWorker

class CorWorkerDsl<T>(
    var blockHandle: suspend T.() -> Unit = {},
) : CorExecDsl<T>() {
    override fun build(): ICorExec<T> = CorWorker(
        title = title,
        description = description,
        blockHandle = blockHandle,
        blockOn = blockOn,
        blockException = blockException
    )
}