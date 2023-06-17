package ru.otuskotlin.public.bookingservice.common.models.meeting

import ru.otuskotlin.public.bookingservice.common.models.BsCommand

enum class BsMeetingCommand : BsCommand {
    NONE,
    CREATE,
    DELETE,
    UPDATE,
    READ,
    SEARCH
}