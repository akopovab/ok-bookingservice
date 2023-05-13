package ru.otuskotlin.public.bookingservice

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MeetRequest {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    var status = "CREATE"
    var beginDate = ""
    var endDate = ""


    private fun dateToStr(date :LocalDate, time :String) = date.format(dateFormatter).plus(" $time")
    private fun strToDate(date :String) = LocalDateTime.parse(date, dateTimeFormatter)

    fun setOneSlotToTomorrow(time :String) {
        beginDate = LocalDate.now().plusDays(1).let {dateToStr(it,time)}
        endDate = strToDate(beginDate).plusMinutes(30).format(dateTimeFormatter)
    }
}