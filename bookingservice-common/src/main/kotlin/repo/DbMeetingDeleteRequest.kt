package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingLock

data class DbMeetingDeleteRequest(
    val id : BsMeetingId,
    val lock : BsMeetingLock = BsMeetingLock.NONE
){
    constructor(meeting : BsMeeting): this(meeting.id, meeting.lock)
}
