package ru.otuskotlin.public.bookingservice

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import ru.otuskotlin.public.bookingservice.MeetRequest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// let's try TDD

class MeetRequestTest : FunSpec({

    test("test status new MeetRequest") {
        val newMeetRequestStatus = MeetRequest().status
        newMeetRequestStatus shouldBe "CREATE"
    }

    test("test end date MeetRequest") {
        val meetRequest = MeetRequest().apply { setOneSlotToTomorrow("15:30") }

        meetRequest.endDate shouldBe LocalDate.now().plusDays(1).let {
            it.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")).plus(" 16:00")
        }
    }


})