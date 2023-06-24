package ru.otuskotlin.public.bookingservice.business.workers.stubs

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

fun CorChainDsl<BsMeetingContext>.stubMeetingSearchSuccess(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING && stub == BsStubs.SUCCESS }
    handle {
        meetingsResponse = MeetingStub.getMeetings().map { it.copy().apply { employeeId = it.employeeId } }.toMutableList()
        state = BsState.FINISHING
    }
}