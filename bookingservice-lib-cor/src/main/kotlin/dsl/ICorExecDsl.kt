package ru.otuskotlin.public.bookingservice.lib.cor.dsl

import ru.otuskotlin.public.bookingservice.lib.cor.ICorExec

interface ICorExecDsl<T> {
    var title :String
    var description :String
    fun build(): ICorExec<T>
}
