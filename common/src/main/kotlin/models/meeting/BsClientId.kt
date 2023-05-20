package ru.otuskotlin.public.bookingservice.common.models.meeting
@JvmInline
value class BsClientId (private val id :String){
    fun asString() = id

    companion object {
        val NONE = BsClientId("")
    }
}