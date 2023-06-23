package ru.otuskotlin.public.bookingservice.stubs

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting

fun <T:BsContext> T.addError(group: String, code: String, field: String, message: String) {
    this.errors.add(BsError(group, code, field, message))
}


fun MeetingStub.createMeeting(block: BsMeeting.() -> Unit) = getMeeting().copy().apply { block() }

fun MeetingStub.updateMeeting(block: BsMeeting.() -> Unit) = getMeeting().copy().apply { block() }
