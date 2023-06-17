package ru.otuskotlin.public.bookingservice.common

import ru.otuskotlin.public.bookingservice.lib.log.common.BsLoggerProvider

data class BsCorSettings( val loggerProvider: BsLoggerProvider = BsLoggerProvider()){
    companion object {
        val NONE = BsCorSettings()
    }
}
