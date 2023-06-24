package ru.otuskotlin.public.bookingservice.lib.cor.handlers

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.otuskotlin.public.bookingservice.lib.cor.ICorExec

suspend fun <T> executeSequential(context: T, execs: List<ICorExec<T>>): Unit =
    execs.forEach {
        it.exec(context)
    }

suspend fun <T> executeParallel(context: T, execs: List<ICorExec<T>>): Unit = coroutineScope {
    execs.forEach {
        launch { it.exec(context) }
    }
}
