package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import ru.otuskotlin.public.bookingservice.api.models.IMeetingRequest
import ru.otuskotlin.public.bookingservice.api.models.IMeetingResponse
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import ru.otuskotlin.public.bookingservice.mappers.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.log.toLog
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub


suspend inline fun <reified Q : IMeetingRequest, @Suppress("unused") reified R : IMeetingResponse> ApplicationCall.meetingProcess(
    appSettings: BsAppSettings,
    logger: IBsLogWrapper,
    logId: String,
    command: BsMeetingCommand? = null,
) {
    val ctx = BsMeetingContext(
        timeStart = Clock.System.now(),
    )
    //val processor = appSettings.processor
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromTransportMeeting(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            ctx.meetingResponse = MeetingStub.getMeeting()
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toTransportMeeting())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command?.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
                e = e,
                data = ctx.toLog(logger.loggerId)
            )
            //ctx.state = BsState.FAILING
        }
    }
}
