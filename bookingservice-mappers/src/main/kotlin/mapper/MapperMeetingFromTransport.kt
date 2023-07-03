package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.mappers.exception.UnknownRequestClass

fun BsMeetingContext.fromTransportMeeting(request: IMeetingRequest) = when (request) {
    is MeetingCreateRequest -> fromTransport(request)
    is MeetingReadRequest -> fromTransport(request)
    is MeetingUpdateRequest -> fromTransport(request)
    is MeetingDeleteRequest -> fromTransport(request)
    is MeetingSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun BsMeetingContext.fromTransport(request: MeetingCreateRequest) {
    command = BsMeetingCommand.CREATE
    requestId = request.requestId()
    meetingRequest = request.meeting?.toInternal() ?: BsMeeting()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun BsMeetingContext.fromTransport(request: MeetingReadRequest) {
    command = BsMeetingCommand.READ
    requestId = request.requestId()
    meetingRequest = request.meeting?.toInternal() ?: BsMeeting()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun BsMeetingContext.fromTransport(request: MeetingUpdateRequest) {
    command = BsMeetingCommand.UPDATE
    requestId = request.requestId()
    meetingRequest = request.meeting?.toInternal() ?: BsMeeting()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun BsMeetingContext.fromTransport(request: MeetingDeleteRequest) {
    command = BsMeetingCommand.DELETE
    requestId = request.requestId()
    meetingRequest = request.meeting?.toInternal() ?: BsMeeting()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun BsMeetingContext.fromTransport(request: MeetingSearchRequest) {
    command = BsMeetingCommand.SEARCH
    requestId = request.requestId()
    meetingSearchRequest = request.employeeId.toEmployeeId()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}


private fun String?.toEmployeeId() = this?.let { BsEmployeeId(it) } ?: BsEmployeeId.NONE

private fun String?.toClientId() = this?.let { BsClientId(it) } ?: BsClientId.NONE

private fun IMeetingRequest.requestId() = this.requestId?.let { BsRequestId(it) } ?: BsRequestId.NONE

private fun String?.toMeetingId() = this?.let { BsMeetingId(it) } ?: BsMeetingId.NONE

private fun String?.toLockId() = this?.let { BsMeetingLock(it) } ?: BsMeetingLock.NONE

private fun String?.toSlotId() = this?.let { BsSlotId(it) } ?: BsSlotId.NONE

private fun List<SlotIdListSlotsInner>?.fromTransport() = this?.map { BsSlot(id = it.slotId.toSlotId()) }?.toMutableSet() ?: mutableSetOf()

private fun MeetingStatus?.transportToStatus() = when (this) {
    MeetingStatus.MEETING_UNDONE -> BsMeetingStatus.MEETING_UNDONE
    MeetingStatus.CREATE -> BsMeetingStatus.CREATE
    MeetingStatus.TOOK_PLACE -> BsMeetingStatus.TOOK_PLACE
    MeetingStatus.WITHDRAWN -> BsMeetingStatus.WITHDRAWN
    null -> BsMeetingStatus.NONE
}

private fun MeetingReadObject.toInternal(): BsMeeting = BsMeeting(
    id = this.meetingId.toMeetingId()
)

private fun MeetingDeleteObject.toInternal(): BsMeeting = BsMeeting(
    id = this.meetingId.toMeetingId(),
    lock = this.meetingLock.toLockId()
)


private fun MeetingCreateObject.toInternal(): BsMeeting = BsMeeting(
    employeeId = this.employeeId.toEmployeeId(),
    clientId = this.clientId.toClientId(),
    description = this.description ?: "",
    slots = this.slots.fromTransport()
)

private fun MeetingUpdateObject.toInternal(): BsMeeting = BsMeeting(
    id = this.meetingId.toMeetingId(),
    employeeId = this.employeeId.toEmployeeId(),
    clientId = this.clientId.toClientId(),
    description = this.description ?: "",
    slots = this.slots.fromTransport(),
    meetingStatus = this.status.transportToStatus(),
    lock = this.meetingLock.toLockId()
)







