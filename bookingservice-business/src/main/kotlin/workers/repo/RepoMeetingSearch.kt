package workers.repo

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.repo.DbEmployeeIdRequest
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.repoMeetingSearch(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING }
    handle {
        val result = meetingRepo.searchMeeting(DbEmployeeIdRequest(meetingSearchRequest))
        if (result.isSuccess && result.data.isNotEmpty()) {
            meetingsRepoDone = result.data.toMutableList()
        } else if (result.errors.isNotEmpty()) {
            addError(result.errors)
            state = BsState.FAILING
        }
    }
}
