package ru.otuskotlin.public.bookingservice.common.models
@JvmInline
value class BsRequestId (private val id :String){
    fun asString() = id

    companion object {
        val NONE = BsRequestId("")
    }
}