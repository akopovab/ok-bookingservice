package ru.otuskotlin.public.bookingservice.common.context.Impl

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.*
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs

data class BsMeetingContext  (
    override var command : BsCommand = BsMeetingCommand.NONE,
    override var state : BsState = BsState.NONE,
    override var errors :MutableList<BsError> = mutableListOf(),

    override var workMode : BsWorkMode = BsWorkMode.PROD,
    override var stub: BsStubs = BsStubs.NONE,
    override var requestId: BsRequestId = BsRequestId.NONE,
    override var timeStart :Instant = Instant.NONE,

    var meetingSearchRequest :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRequest :BsMeeting = BsMeeting(),

    var meetingResponse :BsMeeting = BsMeeting(),
    var meetingsResponse :MutableList<BsMeeting> = mutableListOf(),

    var meetingSearchValidation :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRequestValidation :BsMeeting = BsMeeting(),




) :BsContext