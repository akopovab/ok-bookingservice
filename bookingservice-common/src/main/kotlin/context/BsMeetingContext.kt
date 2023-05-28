package ru.otuskotlin.public.bookingservice.common.context

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs

data class BsMeetingContext (
    var command : BsMeetingCommand = BsMeetingCommand.NONE,
    var state : BsState = BsState.NONE,
    var errors :MutableList<BsError> = mutableListOf(),

    var workMode : BsWorkMode = BsWorkMode.PROD,
    var stub: BsStubs = BsStubs.NONE,
    var requestId: BsRequestId = BsRequestId.NONE,
    var timeStart :Instant = Instant.NONE,

    var meetingSearchRequest :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRequest :BsMeeting = BsMeeting(),

    var meetingResponse :BsMeeting = BsMeeting(),
    var meetingsResponse :MutableList<BsMeeting> = mutableListOf()
)