package ru.otuskotlin.public.bookingservice.lib.cor

import ru.otuskotlin.public.bookingservice.lib.cor.handlers.throwException


abstract class CorExec<T>(
    override val title: String,
    override val description: String = "",
    private val blockOn: suspend T.() -> Boolean = { true },
    private val blockException: suspend T.(Throwable) -> Unit = { it ->
        println("test")
        throw it
        println("test2")

    }
) : ICorExec<T> {

    protected abstract suspend fun T.handle()

    override suspend fun exec(context: T) {
        if (context.blockOn()) {
            try {
                context.handle()
            } catch (e: Throwable) {
                println("test0")
                context.blockException(e)
                //throw e
            }
        }
    }
}