package ru.otuskotlin.public.bookingservice.common.context

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.*
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs

interface BsContext {
    var command: BsCommand
    var state: BsState
    var errors: MutableList<BsError>
    var workMode: BsWorkMode
    var stub: BsStubs
    var requestId: BsRequestId
    var timeStart: Instant
}