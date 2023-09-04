package ru.otuskotlin.public.bookingservice.common.models.permission


data class BsPrincipal(
    val id :String = "",
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val groups: Set<BsUserGroup> = emptySet()
) {
    companion object {
        val NONE = BsPrincipal()
    }
}
