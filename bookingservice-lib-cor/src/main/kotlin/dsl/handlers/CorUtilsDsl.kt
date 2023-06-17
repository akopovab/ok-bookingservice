package ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.dsl.CorExecDsl
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeParallel
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeSequential

fun <T> worker(block: CorWorkerDsl<T>.() -> Unit) = CorWorkerDsl<T>().apply(block)

fun <T> chain(block: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(block)

fun <T> CorWorkerDsl<T>.handle(block: suspend T.() -> Unit) {
    this.blockHandle = block
}

fun <T> CorChainDsl<T>.worker(block: CorWorkerDsl<T>.() -> Unit) :CorWorkerDsl<T> {
    val worker = CorWorkerDsl<T>().apply(block)
    add(worker)
    return worker
}

fun <T> CorExecDsl<T>.on(block: suspend T.() -> Boolean) {
    this.blockOn = block
}

fun <T> CorChainDsl<T>.sequential(block: CorChainDsl<T>.() -> Unit) {
    this.apply(block).apply { strategy = ::executeSequential }
}

fun <T> CorChainDsl<T>.parallel(block: CorChainDsl<T>.() -> Unit) {
    this.apply(block).apply { strategy = ::executeParallel }
}

