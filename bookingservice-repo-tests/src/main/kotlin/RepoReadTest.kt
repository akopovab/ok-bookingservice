package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.common.repo.DbMeetingIdRequest
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@OptIn(ExperimentalCoroutinesApi::class)
fun repoReadTest(repo : IMeetingRepository) = funSpec {
    repoTest("Read meeting."){
        val meetingId = "000000000000"
        val meeting = BsMeeting(
            id = BsMeetingId(meetingId)
        )
        var expected :BsMeeting = BsMeeting()
        val result = repo.readMeeting((DbMeetingIdRequest(meeting)))

        MeetingStub.getMeetings().forEach {
            if (it.id.asString() == meetingId) expected = it
        }

        result.isSuccess shouldBe true
        result.errors shouldBe emptyList()
        result.data?.id shouldNotBe  BsMeetingId.NONE
        result.data?.clientId shouldBe expected.clientId
        result.data?.employeeId shouldBe expected.employeeId
        result.data?.description shouldBe expected.description
        result.data?.meetingStatus shouldBe BsMeetingStatus.CREATE
        result.data?.slots shouldNotBe emptySet<BsSlot>()
        result.data?.slots?.forEach { resultSlot ->
            resultSlot.slotStatus shouldBe BsSlotStatus.RESERVED
            expected.slots.any { it.id == resultSlot.id } shouldBe true
        }
    }
}

object InitReadObjects :InitObjects {
    override val initObjects: List<BsMeeting> = MeetingStub.getMeetings()
    override val initSlots: Set<BsSlot> = emptySet()
}
