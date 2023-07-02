package ru.otuskotlin.public.bookingservice.common.context.Impl

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.*
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository

data class BsSlotContext (
    override var command : BsCommand = BsSlotCommand.NONE,
    override var state : BsState = BsState.NONE,
    override var errors :MutableList<BsError> = mutableListOf(),

    override var meetingRepo : IMeetingRepository = IMeetingRepository.NONE,
    override var settings : BsCorSettings = BsCorSettings.NONE,

    override var workMode : BsWorkMode = BsWorkMode.PROD,
    override var stub: BsStubs = BsStubs.NONE,
    override var requestId: BsRequestId = BsRequestId.NONE,
    override var timeStart : Instant = Instant.NONE,

    var slotRequest :BsEmployeeId = BsEmployeeId.NONE,
    var slotResponse: MutableSet<BsSlot> = mutableSetOf(),

    var slotRepoDone : MutableSet<BsSlot> = mutableSetOf()
    ) : BsContext