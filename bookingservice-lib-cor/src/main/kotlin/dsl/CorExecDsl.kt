package ru.otuskotlin.public.bookingservice.lib.cor.dsl


abstract class CorExecDsl<T>: ICorExecDsl<T> {
    override var title: String = ""
    override var description: String = ""
    var blockOn: suspend T.() -> Boolean = {true}
    var blockException: suspend T.(Throwable) -> Unit = {throw it}
}