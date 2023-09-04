package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingStatus
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.common.repo.DbMeetingRequest
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@OptIn(ExperimentalCoroutinesApi::class)
fun repoUpdateTest(repo : IMeetingRepository) = funSpec {
    repoTest("Update meeting."){

        val meeting = MeetingStub.getMeetingForUpdate()
        val result = repo.updateMeeting((DbMeetingRequest(meeting)))

        result.isSuccess shouldBe true
        result.errors shouldBe emptyList()
        result.data?.id shouldNotBe  BsMeetingId.NONE
        result.data?.clientId shouldBe meeting.clientId
        result.data?.employeeId shouldBe meeting.employeeId
        result.data?.description shouldBe meeting.description
        result.data?.meetingStatus shouldBe BsMeetingStatus.UPDATED
        result.data?.slots shouldNotBe emptySet<BsSlot>()
        result.data?.slots?.forEach { resultSlot ->
            resultSlot.slotStatus shouldBe BsSlotStatus.RESERVED
            meeting.slots.any { it.id == resultSlot.id } shouldBe true
        }
    }
}

object InitUpdateObjects :InitObjects {
    override val initObjects: List<BsMeeting> = listOf(MeetingStub.getMeetingForUpdate())
    override val initSlots: Set<BsSlot> = emptySet()
}
