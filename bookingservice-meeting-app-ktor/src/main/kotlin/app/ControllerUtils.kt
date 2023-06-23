package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import ru.otuskotlin.public.bookingservice.api.models.IMeetingRequest
import ru.otuskotlin.public.bookingservice.api.models.ISlotRequest
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.lib.log.common.IBsLogWrapper
import ru.otuskotlin.public.bookingservice.mappers.log.toLog
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportSlot
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings


suspend inline fun <reified Q : IMeetingRequest> ApplicationCall.meetingProcess(
    appSettings: BsAppSettings,
    logger: IBsLogWrapper,
    logId: String,
    command: BsMeetingCommand,
) {
    val ctx = BsMeetingContext(
        timeStart = Clock.System.now(),
    )
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromTransportMeeting(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            appSettings.meetingProcessor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toTransportMeeting())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
                e = e,
                data = ctx.toLog(logger.loggerId)
            )
        }
    }
}

suspend inline fun <reified Q : ISlotRequest> ApplicationCall.slotProcess(
    appSettings: BsAppSettings,
    logger: IBsLogWrapper,
    logId: String,
    command: BsSlotCommand,
) {
    val ctx = BsSlotContext(
        timeStart = Clock.System.now(),
    )
    try {
        logger.doWithLogging(id = logId) {
            val request = receive<Q>()
            ctx.fromTransportSlot(request)
            logger.info(
                msg = "$command request is got",
                data = ctx.toLog("${logId}-got")
            )
            appSettings.slotProcessor.exec(ctx)
            logger.info(
                msg = "$command request is handled",
                data = ctx.toLog("${logId}-handled")
            )
            respond(ctx.toTransportMeeting())
        }
    } catch (e: Throwable) {
        logger.doWithLogging(id = "${logId}-failure") {
            command.also { ctx.command = it }
            logger.error(
                msg = "$command handling failed",
                e = e,
                data = ctx.toLog(logger.loggerId)
            )
        }
    }
}
