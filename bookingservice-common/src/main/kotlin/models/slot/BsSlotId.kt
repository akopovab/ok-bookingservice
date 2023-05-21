package ru.otuskotlin.public.bookingservice.common.models.slot
@JvmInline
value class BsSlotId (private val id :String){
    fun asString() = id

    companion object {
        val NONE = BsSlotId("")
    }
}