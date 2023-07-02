package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting

data class DbMeetingResponse(
    override val data: BsMeeting?,
    override val isSuccess: Boolean,
    override val errors: List<BsError> = emptyList()
) :IDbResponse<BsMeeting> {


    companion object {
        fun errors(errors: List<BsError>) = DbMeetingResponse(null, false, errors)
        fun error(errors: BsError) = DbMeetingResponse(null, false, listOf(errors))
        fun success(res :BsMeeting) = DbMeetingResponse(res, true)
        fun success() = DbMeetingResponse(null, true)
    }





}