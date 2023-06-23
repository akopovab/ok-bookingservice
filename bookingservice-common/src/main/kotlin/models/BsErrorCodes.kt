package ru.otuskotlin.public.bookingservice.common.models

enum class BsErrorCodes {
    NONE,
    NOT_FOUND,
    BAD_SLOT_DATE,
    BAD_ID,
    BAD_DESCRIPTION,
    SLOT_ID_RESERVED
}