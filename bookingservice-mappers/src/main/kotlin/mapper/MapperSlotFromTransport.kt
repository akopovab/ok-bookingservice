package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsRequestId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.mappers.exception.UnknownRequestClass

fun BsSlotContext.fromTransportSlot(request: ISlotRequest) = when (request) {
    is SlotSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}
private fun String?.toEmployeeId() = this?.let { BsEmployeeId(it) } ?: BsEmployeeId.NONE

fun BsSlotContext.fromTransport(request: SlotSearchRequest) {
    command = BsSlotCommand.SEARCH
    requestId = request.requestId()
    slotRequest = request.employeeId.toEmployeeId()
    workMode = request.debug.transportToWorkMode()
    stub = request.debug.transportToStubCase()
}

private fun ISlotRequest.requestId() = this.requestId?.let { BsRequestId(it) } ?: BsRequestId.NONE

