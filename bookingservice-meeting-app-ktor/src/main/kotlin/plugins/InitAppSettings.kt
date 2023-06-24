package ru.otuskotlin.public.bookingservice.meeting.plugins

import ru.otuskotlin.public.bookingservice.business.processors.impl.MeetingProcessor
import ru.otuskotlin.public.bookingservice.business.processors.impl.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.BsLogSettings
import ru.otuskotlin.public.bookingservice.lib.log.common.BsLoggerProvider
import ru.otuskotlin.public.bookingservice.lib.logback.mpLoggerLogback
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

fun initAppSettings(): BsAppSettings {
    return BsAppSettings(
        logSettings = BsLogSettings(loggerProvider = BsLoggerProvider { mpLoggerLogback(it) }),
        slotProcessor = SlotProcessor(),
        meetingProcessor = MeetingProcessor()
    )

}
   