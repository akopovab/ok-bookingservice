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
fun repoSearchMeetingTest(repo : IMeetingRepository) = funSpec {
    repoTest("Search meeting."){
        val employeeId = BsEmployeeId("444")
        var expected = BsMeeting()
        val result = repo.searchMeeting((DbEmployeeIdRequest(employeeId)))

        MeetingStub.getMeetings().forEach {
            if (it.employeeId == employeeId) expected = it
        }


        result.isSuccess shouldBe true
        result.errors shouldBe emptyList()
        result.data[0].id shouldNotBe BsMeetingId.NONE
        result.data[0].clientId shouldBe expected.clientId
        result.data[0].employeeId shouldBe expected.employeeId
        result.data[0].description shouldBe expected.description
        result.data[0].meetingStatus shouldBe BsMeetingStatus.CREATED
        result.data[0].slots shouldNotBe emptySet<BsSlot>()
        result.data[0].slots.forEach { resultSlot ->
            resultSlot.slotStatus shouldBe BsSlotStatus.RESERVED
            expected.slots.any { it.id == resultSlot.id } shouldBe true
        }
    }
}

object InitSearchObjects :InitObjects {
    override val initObjects: List<BsMeeting> = MeetingStub.getMeetings()
    override val initSlots: Set<BsSlot> = emptySet()
}
