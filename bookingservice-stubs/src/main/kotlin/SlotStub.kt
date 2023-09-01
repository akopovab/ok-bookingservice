package ru.otuskotlin.public.bookingservice.stubs

import kotlinx.datetime.Instant
import ru.otuskotlin.public.bookingservice.common.models.meeting.BsEmployeeId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlot
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotId
import ru.otuskotlin.public.bookingservice.common.models.slot.BsSlotStatus

object SlotStub {

    fun getSlots() = mutableSetOf(
        BsSlot(
            id = BsSlotId("123000111"),
            startDate = Instant.parse("2023-07-20T12:30:00Z"),
            endDate = Instant.parse("2023-07-20T13:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000222"),
            startDate = Instant.parse("2023-07-20T13:00:00Z"),
            endDate = Instant.parse("2023-07-20T13:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000333"),
            startDate = Instant.parse("2023-07-20T13:30:00Z"),
            endDate = Instant.parse("2023-07-20T14:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000444"),
            startDate = Instant.parse("2023-06-20T15:00:00Z"),
            endDate = Instant.parse("2023-07-20T15:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("444"),
        ),
        BsSlot(
            id = BsSlotId("123000555"),
            startDate = Instant.parse("2023-06-20T15:30:00Z"),
            endDate = Instant.parse("2023-07-20T16:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000666"),
            startDate = Instant.parse("2023-06-20T16:00:00Z"),
            endDate = Instant.parse("2023-07-20T16:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000777"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000888"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000999"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        BsSlot(
            id = BsSlotId("123000000"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("555"),
        ),
        //Пользователь 2: employee_1 / employee_1 (группа employee и слоты) 094a5fbe-4e61-4b4c-8dc7-3572c662111e
        BsSlot(
            id = BsSlotId("2C502FCECFC144E79E5342E9577B40E4"),
            startDate = Instant.parse("2023-07-20T12:30:00Z"),
            endDate = Instant.parse("2023-07-20T13:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("953F4C063B7743A1B7A2AB4A48DE4987"),
            startDate = Instant.parse("2023-07-20T13:00:00Z"),
            endDate = Instant.parse("2023-07-20T13:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("9287B1F44A0245B7A13D92952D8A8CEA"),
            startDate = Instant.parse("2023-07-20T13:30:00Z"),
            endDate = Instant.parse("2023-07-20T14:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("A567AB14EAA645ADADC07E59201A768F"),
            startDate = Instant.parse("2023-06-20T15:00:00Z"),
            endDate = Instant.parse("2023-07-20T15:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("FCC9B175F9BA47A288F351DD347BAEEC"),
            startDate = Instant.parse("2023-06-20T15:30:00Z"),
            endDate = Instant.parse("2023-07-20T16:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("B4DC3B68CF9344A2A69830D13CDC8D67"),
            startDate = Instant.parse("2023-06-20T16:00:00Z"),
            endDate = Instant.parse("2023-07-20T16:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("A23A8E93AC374090904C296B9DFCDEF5"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("364E55FC20EA4C4582CD6D8387D12FB6"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("4BC55F7FCA2D4B93A2ABF8544780982E"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        BsSlot(
            id = BsSlotId("0A56EAB118324B2C97FAC1A885D22B9A"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("094a5fbe-4e61-4b4c-8dc7-3572c662111e"),
        ),
        //Пользователь: employee_1 / employee_1 (группа employee и слоты) 770cda7b-7baa-4027-a3b5-6fd2416d310f
        BsSlot(
            id = BsSlotId("9BD647B5EA9A41679E43A829CC22837F"),
            startDate = Instant.parse("2023-07-20T12:30:00Z"),
            endDate = Instant.parse("2023-07-20T13:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("2D1A30AAAAEB455492A62E50E0AFF541"),
            startDate = Instant.parse("2023-07-20T13:00:00Z"),
            endDate = Instant.parse("2023-07-20T13:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("1E2B1599DA7948239C7217D6F3A4CD9E"),
            startDate = Instant.parse("2023-07-20T13:30:00Z"),
            endDate = Instant.parse("2023-07-20T14:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("710F4F6C3B474502B47C9A7A596B603E"),
            startDate = Instant.parse("2023-06-20T15:00:00Z"),
            endDate = Instant.parse("2023-07-20T15:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("B44C93D29B324284B7F608F4485B7396"),
            startDate = Instant.parse("2023-06-20T15:30:00Z"),
            endDate = Instant.parse("2023-07-20T16:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("E141424664D34992BBB162C9C0672B9B"),
            startDate = Instant.parse("2023-06-20T16:00:00Z"),
            endDate = Instant.parse("2023-07-20T16:30:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("5D4ECA610DD848EA910489314E7D7768"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("79F4042F12E845B294ECB3E4C8FEBDD9"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("48E40E6FAF6E40D9A601DEBD03730624"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        ),
        BsSlot(
            id = BsSlotId("220F19BDAE76427D9A5ED3EB9447013B"),
            startDate = Instant.parse("2023-06-20T16:30:00Z"),
            endDate = Instant.parse("2023-07-20T17:00:00Z"),
            slotStatus = BsSlotStatus.FREE,
            employeeId = BsEmployeeId("770cda7b-7baa-4027-a3b5-6fd2416d310f"),
        )



    )

}