package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.mappers.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

suspend fun ApplicationCall.createMeeting(){
    val request = receive<MeetingCreateRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingResponse = MeetingStub.getMeeting()
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.readMeeting(){
    val request = receive<MeetingReadRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingResponse = MeetingStub.getMeeting()
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.updateMeeting(){
    val request = receive<MeetingUpdateRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingResponse = MeetingStub.getMeeting()
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.deleteMeeting(){
    val request = receive<MeetingDeleteRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    respond(context.toTransportMeeting())
}

suspend fun ApplicationCall.searchMeetings(){
    val request = receive<MeetingSearchRequest>()
    val context = BsMeetingContext()
    context.fromTransportMeeting(request)
    context.meetingsResponse = MeetingStub.getMeetings()
    respond(context.toTransportMeeting())
}