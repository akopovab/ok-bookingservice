package ru.otuskotlin.public.bookingservice.common.context.Impl

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.*
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipal
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserPermission
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository

data class BsMeetingContext  (
    override var command : BsCommand = BsMeetingCommand.NONE,
    override var state : BsState = BsState.NONE,
    override var errors :MutableList<BsError> = mutableListOf(),

    override var workMode : BsWorkMode = BsWorkMode.PROD,
    override var stub: BsStubs = BsStubs.NONE,
    override var requestId: BsRequestId = BsRequestId.NONE,
    override var timeStart :Instant = Instant.NONE,

    override var meetingRepo :IMeetingRepository = IMeetingRepository.NONE,
    override var settings :BsCorSettings = BsCorSettings.NONE,

    var principal: BsPrincipal = BsPrincipal.NONE,
    var permissions: MutableSet<BsUserPermission> = mutableSetOf(),
    var permitted: Boolean = false,

    var meetingSearchRequest :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRequest :BsMeeting = BsMeeting(),

    var meetingResponse :BsMeeting = BsMeeting(),
    var meetingsResponse :MutableList<BsMeeting> = mutableListOf(),

    var meetingSearchValidation :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRequestValidation :BsMeeting = BsMeeting(),


    var meetingRepoRead :BsMeeting = BsMeeting(),
    var meetingRepoPrepare :BsMeeting = BsMeeting(),
    var meetingRepoPrepareSearch :BsEmployeeId = BsEmployeeId.NONE,
    var meetingRepoDone :BsMeeting = BsMeeting(),
    var meetingsRepoDone :MutableList<BsMeeting> = mutableListOf(),

    ) :BsContext