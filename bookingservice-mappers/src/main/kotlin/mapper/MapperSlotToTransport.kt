package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand
import ru.otuskotlin.public.bookingservice.mappers.exception.BsUnknownSlotCommand

fun BsSlotContext.toTransportSlot(): ISlotResponse = when (val cmd = command as BsSlotCommand) {
    BsSlotCommand.SEARCH -> toTransportSearch()
    BsSlotCommand.NONE -> throw BsUnknownSlotCommand(cmd)
}

private fun BsSlotContext.toTransportSearch() = SlotSearchResponse(
    //responseType = "search",
    requestId = requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == BsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    slots = slotResponse.toTransportSlots()

)

private fun MutableList<BsError>.toTransportErrors() =  this.map { it.toTransportError() }.toList().takeIf { it.isNotEmpty() }

private fun BsError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)


