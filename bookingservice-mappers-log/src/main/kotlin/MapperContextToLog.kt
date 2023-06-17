package ru.otuskotlin.public.bookingservice.mappers.log

import kotlinx.datetime.Clock
import ru.otuskotlin.public.bookingservice.api.log.models.*
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.context.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand


fun BsMeetingContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    serviceName = "bookingservice-meeting",
    meeting = toMeetingLog(),
)


fun BsSlotContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    serviceName = "bookingservice-slot",
    slot = toSlotLog(),
)

private fun BsMeetingContext.toMeetingLog() = BsMeetingLogModel(
    requestId = requestId.takeIf { it != BsRequestId.NONE }?.asString(),
    operation = command.toLog(),
    requestMeeting = meetingRequest.toLog(),
    responseMeeting = meetingResponse.toLog(),
    requestMeetingSearch = meetingSearchRequest.takeIf { it != BsEmployeeId.NONE }
        ?.let { LogEmployeeId(it.asString()) },
    responseMeetings = meetingsResponse.map { it.toLog() }.toList()
)

private fun BsMeeting.toLog() = MeetingLogModel(
    meetingId = id.takeIf { it != BsMeetingId.NONE }?.asString(),
    employeeId = employeeId.takeIf { it != BsEmployeeId.NONE }?.asString(),
    clientId = clientId.takeIf { it != BsClientId.NONE }?.asString(),
    description = description.takeIf { it.isNotBlank() },
    meetingStatus = meetingStatus.takeIf { it != BsMeetingStatus.NONE }?.toString(),
    meetingPermissions = meetingPermissions.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet()
)

private fun BsSlotContext.toSlotLog() = BsSlotLogModel(
    requestId = requestId.takeIf { it != BsRequestId.NONE }?.asString(),
    operation = command.toLog(),
    requestSlot = slotRequest.takeIf { it != BsEmployeeId.NONE }?.let { LogEmployeeId(it.asString()) },
    responseSlot = slotResponse.toLog()
)

private fun MutableSet<BsSlot>.toLog() = this.map {
    SlotLogModel(
        it.id.asString(),
        it.startDate.toString(),
        it.endDate.toString(),
        it.slotStatus.toString()
    )
}.toList()

private fun BsSlotCommand.toLog() = when (this) {
    BsSlotCommand.SEARCH -> BsSlotLogModel.Operation.SEARCH
    else -> null
}


private fun BsMeetingCommand.toLog() = when (this) {
    BsMeetingCommand.CREATE -> BsMeetingLogModel.Operation.CREATE
    BsMeetingCommand.READ -> BsMeetingLogModel.Operation.READ
    BsMeetingCommand.UPDATE -> BsMeetingLogModel.Operation.UPDATE
    BsMeetingCommand.DELETE -> BsMeetingLogModel.Operation.DELETE
    BsMeetingCommand.SEARCH -> BsMeetingLogModel.Operation.SEARCH
    else -> null
}






