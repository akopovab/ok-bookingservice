package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingStatus
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.common.repo.DbEmployeeIdRequest
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@OptIn(ExperimentalCoroutinesApi::class)
fun repoSearchSlotsTest(repo : IMeetingRepository) = funSpec {
    repoTest("Search meeting."){
        val employeeId = BsEmployeeId("444")
        val result = repo.searchSlots((DbEmployeeIdRequest(employeeId)))

        result.isSuccess shouldBe true
        result.errors shouldBe emptyList()
        result.data shouldNotBe emptyList<BsSlot>()
    }
}
