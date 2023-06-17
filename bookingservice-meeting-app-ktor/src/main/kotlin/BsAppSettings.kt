package ru.otuskotlin.public.bookingservice.meeting

import ru.otuskotlin.public.bookingservice.common.BsCorSettings

data class BsAppSettings (
    val appUrls: List<String>,
    val corSettings: BsCorSettings,
)


