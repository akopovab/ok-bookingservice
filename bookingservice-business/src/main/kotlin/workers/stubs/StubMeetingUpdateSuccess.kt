package ru.otuskotlin.public.bookingservice.business.workers.stubs

import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.common.models.BsState
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingPermissions
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingStatus
import ru.otuskotlin.public.bookingservice.common.models.stubs.BsStubs
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.CorChainDsl
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.handle
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.on
import ru.otuskotlin.public.bookingservice.lib.cor.dsl.handlers.worker
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub
import ru.otuskotlin.public.bookingservice.stubs.updateMeeting


fun CorChainDsl<BsMeetingContext>.stubMeetingUpdateSuccess(title: String) = worker {
    this.title = title
    on { state == BsState.RUNNING && stub == BsStubs.SUCCESS }
    handle {
        meetingResponse = MeetingStub.updateMeeting {
            id = meetingRequest.id
            clientId = meetingRequest.clientId
            employeeId = meetingRequest.employeeId
            description = meetingRequest.description
            meetingStatus = BsMeetingStatus.CREATE
            meetingPermissions = mutableSetOf(BsMeetingPermissions.READ)
            slots = meetingRequest.slots
        }
        state = BsState.FINISHING
    }
}