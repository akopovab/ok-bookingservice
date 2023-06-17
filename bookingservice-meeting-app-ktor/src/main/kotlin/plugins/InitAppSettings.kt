package ru.otuskotlin.public.bookingservice.meeting.plugins

import io.ktor.server.application.*
import mpLoggerLogback
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.lib.log.common.BsLoggerProvider
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

fun Application.initAppSettings(): BsAppSettings {
    val corSettings = BsCorSettings(
        loggerProvider = BsLoggerProvider{mpLoggerLogback(it)}
    )
    return BsAppSettings(

        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings
    )
}