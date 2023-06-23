package ru.otuskotlin.public.bookingservice.meeting

import ru.otuskotlin.public.bookingservice.business.processors.impl.MeetingProcessor
import ru.otuskotlin.public.bookingservice.business.processors.impl.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.BsLogSettings

data class BsAppSettings(
    val logSettings: BsLogSettings,
    val meetingProcessor : MeetingProcessor = MeetingProcessor(),
    val slotProcessor : SlotProcessor = SlotProcessor()
    // TODO два процессора переделать
    )


