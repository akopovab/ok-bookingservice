package ru.otuskotlin.public.bookingservice.common.models.slot

import ru.otuskotlin.public.bookingservice.common.models.BsCommand

enum class BsSlotCommand : BsCommand {
    NONE, SEARCH
}