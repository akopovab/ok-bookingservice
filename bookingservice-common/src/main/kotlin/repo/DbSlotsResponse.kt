package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.BsError
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot

data class DbSlotsResponse(
    override val data: Set<BsSlot> = emptySet(),
    override val isSuccess: Boolean,
    override val errors: List<BsError> = emptyList()
) : IDbResponse<Set<BsSlot>> {
    companion object {
        fun errors(errors: List<BsError>) = DbSlotsResponse(emptySet(), false, errors)
        fun error(errors: BsError) = DbSlotsResponse(emptySet(), false, listOf(errors))
        fun success(data: Set<BsSlot>) = DbSlotsResponse(data, true)
    }
}