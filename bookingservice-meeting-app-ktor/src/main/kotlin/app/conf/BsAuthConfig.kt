package ru.otuskotlin.public.bookingservice.meeting.app.conf

import io.ktor.server.application.*

data class BsAuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val clientId: String,
    val certUrl: String? = null,
) {
    constructor(environment: ApplicationEnvironment) : this(
        secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString(),
        clientId = environment.config.property("jwt.clientId").getString(),
        certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
    )

    companion object {
        const val ID_CLAIM = "id"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "first_name"
        const val L_NAME_CLAIM = "last_name"
        const val M_NAME_CLAIM = "middle_name"

        val TEST = BsAuthConfig(
            secret = "bs_secret_code_1075_1",
            issuer = "BookingService",
            audience = "bookingservice-users",
            realm = "bs",
            clientId = "bookingservice_id",
        )
    }
}
