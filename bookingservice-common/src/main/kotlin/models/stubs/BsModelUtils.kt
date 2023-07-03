package ru.otuskotlin.public.bookingservice.common.models.stubs

import ru.otuskotlin.public.bookingservice.common.models.BsError

fun Throwable.asBsError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = BsError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)