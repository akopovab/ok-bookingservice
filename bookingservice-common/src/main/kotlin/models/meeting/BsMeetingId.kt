package ru.otuskotlin.public.bookingservice.common.models.meeting
@JvmInline
value class BsMeetingId (private val id :String){
    fun asString() = id

    companion object {
        val NONE = BsMeetingId("")
    }
}