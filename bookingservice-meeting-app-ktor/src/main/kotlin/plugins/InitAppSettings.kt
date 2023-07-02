package ru.otuskotlin.public.bookingservice.meeting.plugins

import io.ktor.server.application.*
import ru.otuskotlin.public.bookingservice.business.processors.MeetingProcessor
import ru.otuskotlin.public.bookingservice.business.processors.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.lib.log.common.BsLoggerProvider
import ru.otuskotlin.public.bookingservice.lib.logback.mpLoggerLogback
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

fun Application.initAppSettings(): BsAppSettings {
    return BsAppSettings(
        corSettings = BsCorSettings(
            loggerProvider = BsLoggerProvider { mpLoggerLogback(it) },
            repoSettings = getRepository()
        ),
        slotProcessor = SlotProcessor(),
        meetingProcessor = MeetingProcessor()
    )

}
   