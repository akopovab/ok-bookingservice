package ru.otuskotlin.public.bookingservice.meeting.app.conf

import io.ktor.server.auth.jwt.*
import ru.otuskotlin.public.bookingservice.common.models.permission.BsPrincipal
import ru.otuskotlin.public.bookingservice.common.models.permission.BsUserGroup
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.F_NAME_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.GROUPS_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.ID_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.L_NAME_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.M_NAME_CLAIM

fun JWTPrincipal?.toModel() = this?.run {
    BsPrincipal(
        id = payload.getClaim(ID_CLAIM).asString(),
        firstName = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        middleName = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lastName = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> BsUserGroup.USER
                    "EMPLOYEE" -> BsUserGroup.EMPLOYEE
                    "TEST" -> BsUserGroup.TEST
                    "BANNED" -> BsUserGroup.BANNED
                    "ADMIN" -> BsUserGroup.ADMIN
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: BsPrincipal.NONE