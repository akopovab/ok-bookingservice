package ru.otuskotlin.public.bookingservice.business.workers.repo

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.repo.DbMeetingRequest
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.repoMeetingUpdate(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING }
    handle {
        val result = meetingRepo.updateMeeting(DbMeetingRequest(meetingRequest.copy()))
        if (result.isSuccess && result.data != null) {
            meetingRepoDone = result.data!!
        } else if (result.errors.isNotEmpty()) {
            addError(result.errors)
            state = BsState.FAILING
        }
    }
}
