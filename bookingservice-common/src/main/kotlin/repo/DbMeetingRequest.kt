package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting

data class DbMeetingRequest(
    val meeting :BsMeeting
)
