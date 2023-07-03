package ru.otuskotlin.public.bookingservice.common.repo

import ru.otuskotlin.public.bookingservice.common.models.BsError

object DbRepoErrors {

    val CONCURRENT_MODIFICATION = DbMeetingResponse.error(
        BsError(
            code = "CONCURRENT_MODIFY",
            field = "lock",
            message = "This object has already been modified. Refresh the object and try again."
        )
    )
    //meeting errors:
    val EMPTY_ID_ERROR = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_MEETING",
            field = "meetingId",
            message = "Empty meetingId."
        )
    )

    val NO_FOUND_ID_ERROR = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_MEETING",
            field = "meetingId",
            message = "Object by meetingId not found."
        )
    )

    val SLOT_NO_FOUND = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_SLOT",
            field = "id",
            message = "one or more of the slots was not found."
        )
    )

    val EMPTY_EMPLOYEE_ID_FOR_MEETINGS = DbMeetingsResponse.error(
        BsError(
            code = "VALIDATE_MEETING",
            field = "employeeId",
            message = "EmployeeId is empty."
        )
    )
    val NO_FOUND_MEETINGS_BY_EMPLOYEE_ID = DbMeetingsResponse.error(
        BsError(
            code = "VALIDATE_MEETING",
            field = "employeeId",
            message = "No meetings found by employeeId."
        )
    )

    val SLOT_OF_ANOTHER_EMPLOYEE = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_MEETING",
            field = "slots",
            message = "Specified slots do not match employeeId."
        )
    )
    //slots error
    val NO_FOUND_SLOTS_BY_EMPLOYEE_ID = DbSlotsResponse.error(
        BsError(
            code = "VALIDATE_SLOT",
            field = "employeeId",
            message = "No slots found by employeeId."
        )
    )

    val EMPTY_EMPLOYEE_ID_FOR_SLOTS = DbSlotsResponse.error(
        BsError(
            code = "VALIDATE_SLOT",
            field = "employeeId",
            message = "EmployeeId is empty."
        )
    )
    val EMPTY_SLOT_ERROR = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_SLOT",
            field = "slotId",
            message = "Slot array is empty."
        )
    )

    val SLOT_RESERVED_ERROR = DbMeetingResponse.error(
        BsError(
            code = "VALIDATE_SLOT",
            field = "slots",
            message = "Slot is reserved, please try again"
        )
    )
}
