package ru.otuskotlin.public.bookingservice.common.context

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.models.helpers.*
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs

data class BsSlotContext (
    var command : BsSlotCommand = BsSlotCommand.NONE,
    var state : BsState = BsState.NONE,
    var errors :MutableList<BsError> = mutableListOf(),

    var workMode : BsWorkMode = BsWorkMode.PROD,
    var stub: BsStubs = BsStubs.NONE,
    var requestId: BsRequestId = BsRequestId.NONE,
    var timeStart : Instant = Instant.NONE,

    var slotRequest :BsEmployeeId = BsEmployeeId.NONE,
    var slotResponse: MutableList<BsSlot> = mutableListOf()
    )