package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting

data class DbMeetingsResponse(
    override val data: List<BsMeeting> = emptyList(),
    override val isSuccess: Boolean,
    override val errors: List<BsError> = emptyList()
) :IDbResponse<List<BsMeeting>> {
    companion object {
        fun errors(errors: List<BsError>) = DbMeetingsResponse(emptyList(), false, errors)
        fun error(errors: BsError) = DbMeetingsResponse(emptyList(), false, listOf(errors))
        fun success(data: List<BsMeeting>) = DbMeetingsResponse(data, true)
    }
}
