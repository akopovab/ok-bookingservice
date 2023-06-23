package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

suspend fun ApplicationCall.createMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) =
    meetingProcess<MeetingCreateRequest>(appSettings, log, "meeting-create", BsMeetingCommand.CREATE)

suspend fun ApplicationCall.readMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) =
    meetingProcess<MeetingReadRequest>(appSettings, log, "meeting-read", BsMeetingCommand.READ)

suspend fun ApplicationCall.updateMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) =
    meetingProcess<MeetingUpdateRequest>(appSettings, log, "meeting-update", BsMeetingCommand.UPDATE)

suspend fun ApplicationCall.deleteMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) =
    meetingProcess<MeetingDeleteRequest>(appSettings, log, "meeting-delete", BsMeetingCommand.DELETE)

suspend fun ApplicationCall.searchMeetings(log :IBsLogWrapper, appSettings: BsAppSettings) =
    meetingProcess<MeetingSearchRequest>(appSettings, log, "meeting-search", BsMeetingCommand.SEARCH)

