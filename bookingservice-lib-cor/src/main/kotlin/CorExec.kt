package ru.otuskotlin.public.bookingservice.lib.cor

import ru.otuskotlin.public.bookingservice.lib.cor.dsl.ICorExecDsl

abstract class CorExec<T>(
    override val title: String,
    override val description: String = "",
    private val blockOn: suspend T.() -> Boolean = {true},
    private val blockException: suspend T.(Throwable) -> Unit = {throw it}
) : ICorExec<T> {

    protected abstract suspend fun T.handle()

    override suspend fun exec(context: T) {
        if (context.blockOn()) {
            try {
                context.handle()
            } catch (e: Throwable) {
                context.blockException(e)
            }
        }
    }
}


