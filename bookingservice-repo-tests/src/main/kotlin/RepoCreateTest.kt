package ru.otuskotlin.public.bookingservice.repo.tests

import io.kotest.core.spec.style.funSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otuskotlin.public.bookingservice.common.models.meeting.*
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus
import ru.otuskotlin.public.bookingservice.common.repo.DbMeetingRequest
import ru.otuskotlin.public.bookingservice.common.repo.IMeetingRepository
import ru.otuskotlin.public.bookingservice.stubs.SlotStub

@OptIn(ExperimentalCoroutinesApi::class)
fun repoCreateTest(repo :IMeetingRepository) = funSpec {
    repoTest("Create meeting."){
        val meeting = BsMeeting(
                employeeId = BsEmployeeId("444"),
                clientId = BsClientId("444"),
                description = "Тестирование создания встречи",
                slots = mutableSetOf(BsSlot(BsSlotId("123000111")),BsSlot(BsSlotId("123000222")))
                )
        val createResult = repo.createMeeting((DbMeetingRequest(meeting)))
        val expected = meeting.copy(createResult.data?.id?: BsMeetingId.NONE)

        createResult.isSuccess shouldBe true
        createResult.errors shouldBe emptyList()
        createResult.data?.id shouldNotBe  BsMeetingId.NONE
        createResult.data?.clientId shouldBe expected.clientId
        createResult.data?.employeeId shouldBe expected.employeeId
        createResult.data?.description shouldBe expected.description
        createResult.data?.meetingStatus shouldBe BsMeetingStatus.CREATED
        createResult.data?.slots shouldNotBe emptySet<BsSlot>()
        createResult.data?.slots?.forEach { resultSlot ->
            resultSlot.slotStatus shouldBe BsSlotStatus.RESERVED
            expected.slots.any { it.id == resultSlot.id } shouldBe true
        }
    }
}

object InitCreateObjects :InitObjects {
    override val initObjects: List<BsMeeting> = emptyList()
    override val initSlots: Set<BsSlot> = SlotStub.getSlots().toSet()
}
