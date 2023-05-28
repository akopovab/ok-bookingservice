package ru.otuskotlin.public.bookingservice.mappers.exception

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingCommand


 class BsUnknownMeetingCommand(command: BsMeetingCommand) : Throwable("Wrong command $command at mapping toTransport stage")
