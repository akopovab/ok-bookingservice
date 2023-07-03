package ru.otuskotlin.public.bookingservice.repo.tests

import ru.otuskotlin.public.bookingservice.common.models.meeting.BsMeeting
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot

interface InitObjects {
    val initObjects: List<BsMeeting>
    val initSlots: Set<BsSlot>
}