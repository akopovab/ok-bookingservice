package ru.otuskotlin.public.bookingservice.business.workers.repo

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker

fun CorChainDsl<BsMeetingContext>.repoMeetingPrepareSearch(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING }
    handle {
        meetingRepoPrepareSearch = meetingSearchRequest
    }
}