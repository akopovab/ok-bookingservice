package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingId
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeetingLock
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.repo.DbMeetingIdRequest
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

@OptIn(ExperimentalCoroutinesApi::class)
fun repoDeleteTest(repo : IMeetingRepository) = funSpec {
    repoTest("Delete meeting."){
           val meeting = BsMeeting(
            id = BsMeetingId("000000000000"),
            lock = BsMeetingLock("333444")
        )
        val result = repo.deleteMeeting((DbMeetingIdRequest(meeting)))
        val searchResult = repo.readMeeting(DbMeetingIdRequest(meeting))

        result.isSuccess shouldBe true
        searchResult.isSuccess shouldBe false
        result.errors shouldBe emptyList()
        searchResult.errors[0].message shouldBe "Object by meetingId not found."

    }
}

object InitDeleteObjects :InitObjects {
    override val initObjects: List<BsMeeting> = MeetingStub.getMeetings()
    override val initSlots: Set<BsSlot> = emptySet()
}
