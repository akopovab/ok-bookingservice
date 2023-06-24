package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import ru.otuskotlin.public.bookingservice.api.models.SlotSearchRequest
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

suspend fun ApplicationCall.searchSlot(log : IBsLogWrapper, appSettings: BsAppSettings) =
    slotProcess<SlotSearchRequest>(appSettings, log, "meeting-search", BsSlotCommand.SEARCH)
