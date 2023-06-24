package ru.otuskotlin.public.bookingservice.business.workers.validations


import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsClientId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker


fun CorChainDsl<BsMeetingContext>.prepareMeetingValidation(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING }
    handle {
        meetingSearchValidation = BsEmployeeId(meetingSearchRequest.asString().trim())
        meetingRequestValidation = meetingRequest.copy().apply {
            id = BsMeetingId(this.id.asString().trim())
            employeeId = BsEmployeeId(this.employeeId.asString().trim())
            clientId = BsClientId(this.clientId.asString().trim())
            description = this.description.trim()
            slots =
                this.slots.map {
                    BsSlot(
                        BsSlotId(it.id.asString().trim()))
                }.toMutableSet()
        }
    }
}

