package ru.otuskotlin.public.bookingservice.business.workers.repo

import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker


fun <T : BsContext> CorChainDsl<T>.initRepo(title: String) = worker {
    this.title = title
    handle {
        meetingRepo = settings.repoSettings[workMode] ?: IMeetingRepository.NONE
    }
    on { state == BsState.RUNNING }

}