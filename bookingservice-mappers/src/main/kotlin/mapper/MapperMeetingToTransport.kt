package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.mappers.exception.BsUnknownMeetingCommand

fun BsMeetingContext.toTransportMeeting(): IMeetingResponse = when (val cmd :BsMeetingCommand = command as BsMeetingCommand) {
    BsMeetingCommand.CREATE -> toTransportCreate()
    BsMeetingCommand.READ -> toTransportRead()
    BsMeetingCommand.UPDATE -> toTransportUpdate()
    BsMeetingCommand.DELETE -> toTransportDelete()
    BsMeetingCommand.SEARCH -> toTransportSearch()
    BsMeetingCommand.NONE -> throw BsUnknownMeetingCommand(cmd)

}

fun BsMeetingContext.toTransportCreate() = MeetingCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    meeting = meetingResponse.toTransportMeeting()
)

fun BsMeetingContext.toTransportRead() = MeetingReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    meeting = meetingResponse.toTransportMeeting()
)

fun BsMeetingContext.toTransportUpdate() = MeetingUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    meeting = meetingResponse.toTransportMeeting()
)

fun BsMeetingContext.toTransportDelete() = MeetingDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)


fun BsMeetingContext.toTransportSearch() = MeetingSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.FINISHING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    meetings = meetingsResponse.toTransportMeetings()
)

fun BsMeeting.toTransportMeeting(): MeetingResponseObject = MeetingResponseObject(
    meetingId = id.takeIf { it != BsMeetingId.NONE }?.asString(),
    clientId = clientId.takeIf { it != BsClientId.NONE }?.asString(),
    employeeId = employeeId.takeIf { it != BsEmployeeId.NONE }?.asString(),
    slots = slots.toTransportSlots(),
    description = description.takeIf { it.isNotBlank() },
    meetingPermissions = meetingPermissions.toTransportPermissions(),
    status = meetingStatus.toTransportStatus(),
    meetingLock = lock.takeIf { it != BsMeetingLock.NONE}?.asString()
)

private fun MutableSet<BsMeetingPermissions>.toTransportPermissions() = this.map { it.toTransportPermission() }.toSet()

fun MutableSet<BsSlot>.toTransportSlots() = this.map { it.toTransportMeeting() }.toList().takeIf { it.isNotEmpty() }

private fun MutableList<BsError>.toTransportErrors() = this.map { it.toTransportError() }.toList().takeIf { it.isNotEmpty() }

private fun MutableList<BsMeeting>.toTransportMeetings() = this.map { it.toTransportMeeting() }.toList().takeIf { it.isNotEmpty() }

private fun BsSlot.toTransportMeeting() = BaseSlots(
    slotId = id.asString(),
    startDate = startDate.toString(),
    endDate = endDate.toString(),
    slotStatus = slotStatus.toTransportSlotStatus()
)

private fun BsError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)

private fun BsMeetingStatus.toTransportStatus() = when (this) {
    BsMeetingStatus.NONE -> null
    BsMeetingStatus.WITHDRAWN -> MeetingStatus.WITHDRAWN
    BsMeetingStatus.MEETING_UNDONE -> MeetingStatus.MEETING_UNDONE
    BsMeetingStatus.CREATED -> MeetingStatus.CREATED
    BsMeetingStatus.TOOK_PLACE -> MeetingStatus.TOOK_PLACE
    BsMeetingStatus.UPDATED -> MeetingStatus.UPDATED
}

private fun BsMeetingPermissions.toTransportPermission() = when (this) {
    BsMeetingPermissions.READ -> MeetingPermissions.READ
    BsMeetingPermissions.DELETE -> MeetingPermissions.DELETE
    BsMeetingPermissions.UPDATE -> MeetingPermissions.UPDATE
}

private fun BsSlotStatus.toTransportSlotStatus() = when (this){
    BsSlotStatus.NONE -> null
    BsSlotStatus.FREE -> SlotStatus.FREE
    BsSlotStatus.RESERVED -> SlotStatus.RESERVED
}