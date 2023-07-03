package ru.otuskotlin.public.bookingservice.common.models.meeting

@JvmInline
value class BsMeetingLock(private val id :String){
    fun asString() = id

    companion object {
        val NONE = BsMeetingLock("")
    }

}
