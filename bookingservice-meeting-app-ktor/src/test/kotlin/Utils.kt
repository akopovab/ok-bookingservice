package ru.otuskotlin.public.bookingservice.meeting

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.request.*
import io.ktor.http.*
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig
import java.util.*

fun HttpRequestBuilder.addAuth(
    id: String = "test_user",
    groups: List<String> = listOf("ADMIN", "TEST"),
    config: BsAuthConfig = BsAuthConfig.TEST
) {
    val token = JWT.create()
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim(BsAuthConfig.GROUPS_CLAIM, groups)
        .withClaim(BsAuthConfig.ID_CLAIM, id)
        .withClaim(BsAuthConfig.F_NAME_CLAIM, "test_user")
        .withExpiresAt(Date(System.currentTimeMillis() + 120_000))
        .sign(Algorithm.HMAC256(config.secret))
    println(token)
    header(HttpHeaders.Authorization, "Bearer $token")
}