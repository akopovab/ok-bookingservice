package ru.otuskotlin.public.bookingservice.common.models
data class BsError(
    val code: String = "",
    val group: String = "BOOKING_SERVICE_ERROR",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null
)
