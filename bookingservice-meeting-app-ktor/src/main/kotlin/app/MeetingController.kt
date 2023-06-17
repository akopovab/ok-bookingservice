package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import ru.otuskotlin.public.bookingservice.mappers.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.log.toLog
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

suspend fun ApplicationCall.createMeeting(log :IBsLogWrapper, appSettings: BsAppSettings){
    log.doWithLogging("read") {
        val request = receive<MeetingCreateRequest>()
        val context = BsMeetingContext()
        log.info(
            msg = "${context.command} request was received",
            data = context.toLog("read-request")
        )
        context.fromTransportMeeting(request)
        context.meetingResponse = MeetingStub.getMeeting()
        respond(context.toTransportMeeting())
        log.info(
            msg = "${context.command} response was received",
            data = context.toLog("read-response")
        )
    }
}
suspend fun ApplicationCall.readMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) {
    log.doWithLogging("read") {
        val request = receive<MeetingReadRequest>()
        val context = BsMeetingContext()
        context.fromTransportMeeting(request)
        context.meetingResponse = MeetingStub.getMeeting()
        respond(context.toTransportMeeting())
    }
}

suspend fun ApplicationCall.updateMeeting(log :IBsLogWrapper, appSettings: BsAppSettings) {
    val request = receive<MeetingUpdateRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingResponse = MeetingStub.getMeeting()
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.deleteMeeting(log :IBsLogWrapper, appSettings: BsAppSettings){
    val request = receive<MeetingDeleteRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.searchMeetings(log :IBsLogWrapper, appSettings: BsAppSettings){
    val request = receive<MeetingSearchRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingsResponse = MeetingStub.getMeetings()
    respond(context.toTransportMeeting())
}