package ru.otuskotlin.public.bookingservice.common.models.meeting

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.NONE
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId

data class BsMeeting (
    var id: BsMeetingId = BsMeetingId.NONE,
    var employeeId: BsEmployeeId = BsEmployeeId.NONE,
    var clientId: BsClientId = BsClientId.NONE,
    var meetingStatus :BsMeetingStatus = BsMeetingStatus.NONE,
    var description :String = "",
    val slots :MutableSet<BsSlotId> = mutableSetOf(),
    val meetingPermissions: MutableSet<BsMeetingPermissions> = mutableSetOf(),
    var timeCreate :Instant = Instant.NONE,
    var timeModify :Instant = Instant.NONE

)