package ru.otuskotlin.public.bookingservice.mappers.mapper

import ru.otuskotlin.public.bookingservice.api.models.Debug
import ru.otuskotlin.public.bookingservice.api.models.RequestDebugMode
import ru.otuskotlin.public.bookingservice.api.models.RequestDebugStubs
import ru.otuskotlin.public.bookingservice.common.models.BsWorkMode
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs

fun Debug?.transportToWorkMode(): BsWorkMode = when (this?.mode) {
    RequestDebugMode.STUB -> BsWorkMode.STUB
    RequestDebugMode.TEST -> BsWorkMode.TEST
    RequestDebugMode.PROD -> BsWorkMode.PROD
    null -> BsWorkMode.PROD
}

fun Debug?.transportToStubCase(): BsStubs = when (this?.stub) {
    RequestDebugStubs.BAD_EMPLOYEE_ID -> BsStubs.BAD_EMPLOYEE_ID
    RequestDebugStubs.BAD_DESCRIPTION -> BsStubs.BAD_DESCRIPTION
    RequestDebugStubs.BAD_CLIENT_ID -> BsStubs.BAD_CLIENT_ID
    RequestDebugStubs.BAD_MEETING_ID -> BsStubs.BAD_MEETING_ID
    RequestDebugStubs.BAD_SLOT_ID -> BsStubs.BAD_SLOT_ID
    RequestDebugStubs.SUCCESS -> BsStubs.SUCCESS
    RequestDebugStubs.NOT_FOUND -> BsStubs.NOT_FOUND
    RequestDebugStubs.BAD_SLOT_DATE -> BsStubs.BAD_SLOT_DATE
    RequestDebugStubs.SLOT_ID_RESERVED -> BsStubs.SLOT_ID_RESERVED
    else -> BsStubs.NONE
}