package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.common.context.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.helpers.BsError
import ru.otuskotlin.public.bookingservice.common.models.helpers.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId

//
//fun BsMeetingContext.ToTransportMeeting(): IMeetingResponse = when (val cmd = command) {
//    BsMeetingCommand.CREATE -> toTransportCreate()
//    BsMeetingCommand.READ -> toTransportRead()
//    BsMeetingCommand.UPDATE -> toTransportUpdate()
//    BsMeetingCommand.DELETE -> toTransportDelete()
//    BsMeetingCommand.SEARCH -> toTransportSearch()
//    BsMeetingCommand.NONE -> throw UnknownBsCommand(cmd)
//}


fun BsMeetingContext.toTransportCreate() = MeetingCreateResponse(
    responseType = "create",
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if(state == BsState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    meeting = meetingResponse.toTransportMeeting()
)


fun BsMeeting.toTransportMeeting() :MeetingObject = MeetingObject(
    meetingId = id.takeIf { it != BsMeetingId.NONE }?.asString(),
    clientId = clientId.takeIf { it != BsClientId.NONE }?.asString(),
    employeeId = employeeId.takeIf { it != BsEmployeeId.NONE}?.asString(),
    slots = slots.toTransportSlot(),
    description = description.takeIf { it.isNotBlank() }
   // permission =

//    /* примечание к встрече */
//    @field:JsonProperty("description")
//    val description: kotlin.String? = null,
//
//    @field:JsonProperty("status")
//    val status: MeetingStatus? = null,
//
//    @field:JsonProperty("meetingPermissions")
//    val meetingPermissions: kotlin.collections.Set<MeetingPermissions>? = null
//)
)



private fun MutableSet<BsSlotId>.toTransportSlot() = this.map { it.asString() }.toList().takeIf { it.isNotEmpty() }

private fun MutableList<BsError>.toTransportErrors() = this.map { it.toTransportMeeting()}.toList().takeIf { it.isNotEmpty() }

private fun BsError.toTransportMeeting() = Error(
    code = code.takeIf { it.isNotBlank() } ,
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)