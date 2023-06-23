package ru.otuskotlin.public.bookingservice.business.workers.stubs



import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.addError

fun CorChainDsl<BsMeetingContext>.stubMeetingSlotIdReserved(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING && stub == BsStubs.SLOT_ID_RESERVED }
    handle {
        addError(
            group = "validation",
            code = "validation-slotId",
            field = "slotId",
            message = "Slot reserved"
        )
        state = BsState.FAILING
    }
}