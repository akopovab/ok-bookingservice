package ru.otuskotlin.public.bookingservice.common.context

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.BsCorSettings
import ru.otuskotlin.public.bookingservice.common.models.*
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository

interface BsContext {
    var command: BsCommand
    var state: BsState
    var errors: MutableList<BsError>
    var workMode: BsWorkMode
    var stub: BsStubs
    var requestId: BsRequestId
    var timeStart: Instant
    var meetingRepo: IMeetingRepository
    var settings :BsCorSettings
}