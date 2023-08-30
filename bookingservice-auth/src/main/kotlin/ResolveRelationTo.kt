package ru.otuskotlin.public.bookingservice.auth

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipal
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipalRelations
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserGroup

fun BsMeeting.resolveRelationTo(principal: BsPrincipal): Set<BsPrincipalRelations> =
setOfNotNull(
    BsPrincipalRelations.NONE,
    BsPrincipalRelations.OWN_EMPLOYEE.takeIf { principal.id == this.employeeId.asString()},
    BsPrincipalRelations.OWN_CLIENT.takeIf { principal.id == this.clientId.asString()},
)