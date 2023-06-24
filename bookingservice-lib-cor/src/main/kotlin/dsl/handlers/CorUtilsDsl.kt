package ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers

import ru.otuskotlin.public.bookingservice.lib.cor.dsl.CorExecDsl
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeParallel
import ru.otuskotlin.public.bookingservice.lib.cor.handlers.executeSequential

fun <T> worker(block: CorWorkerDsl<T>.() -> Unit) = CorWorkerDsl<T>().apply(block)

fun <T> chain(block: CorChainDsl<T>.() -> Unit) = CorChainDsl<T>().apply(block)

fun <T> CorWorkerDsl<T>.handle(block: suspend T.() -> Unit) {
    this.blockHandle = block
}

fun <T> CorChainDsl<T>.worker(block: CorWorkerDsl<T>.() -> Unit) {
    val worker = CorWorkerDsl<T>().apply(block)
    add(worker)
}

 fun <T> CorChainDsl<T>.worker(title :String, block: suspend T.() -> Unit) {
    val worker = CorWorkerDsl<T>().apply{
        blockHandle = block
        this.title = title
    }
    add(worker)
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

fun <T> CorChainDsl<T>.chain(block: CorChainDsl<T>.() -> Unit) = add(CorChainDsl<T>().apply(block))
