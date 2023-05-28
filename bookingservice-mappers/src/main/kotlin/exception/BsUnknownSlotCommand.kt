package ru.otuskotlin.public.bookingservice.mappers.exception

import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotCommand

class BsUnknownSlotCommand(command: BsSlotCommand) : Throwable("Wrong command $command at mapping toTransport stage")
