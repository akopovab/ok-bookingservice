package ru.otuskotlin.public.bookingservice.meeting

import ru.otuskotlin.public.bookingservice.business.processors.MeetingProcessor
import ru.otuskotlin.public.bookingservice.business.processors.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.BsCorSettings

data class BsAppSettings(
    val corSettings: BsCorSettings,
    val meetingProcessor : MeetingProcessor = MeetingProcessor(),
    val slotProcessor : SlotProcessor = SlotProcessor()
    // TODO два процессора переделать
    )


