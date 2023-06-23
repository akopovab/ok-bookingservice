package ru.otuskotlin.public.bookingservice.business.workers.stubs

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError




fun CorChainDsl<BsMeetingContext>.stubMeetingBadEmployeeId(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING && stub == BsStubs.BAD_EMPLOYEE_ID }
    handle {
        addError(
            group = "validation",
            code = "validation-id",
            field = "employeeId",
            message = "Wrong employee id"
        )
        state = BsState.FAILING
    }
}