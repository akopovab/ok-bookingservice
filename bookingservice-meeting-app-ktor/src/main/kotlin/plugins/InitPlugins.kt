package ru.otuskotlin.public.bookingservice.meeting.plugins


import com.auth0.jwt.JWT
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import org.slf4j.event.Level
import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import ru.otuskotlin.public.bookingservice.lib.logback.BsLogWrapperLogback
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.F_NAME_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.BsAuthConfig.Companion.GROUPS_CLAIM
import ru.otuskotlin.public.bookingservice.meeting.app.conf.resolveAlgorithm
import ru.otuskotlin.public.bookingservice.meeting.module

private val clazz = Application::module::class.qualifiedName ?: "Application"

fun Application.initPlugins(appSettings: BsAppSettings, authConfig: BsAuthConfig) {
    val loggerSecurity = appSettings.corSettings.loggerProvider.logger(Application::initPlugins)

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CORS){
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
        appSettings.appUrls.forEach {
            val split = it.split("://")
            println("$split")
            when (split.size) {
                2 -> allowHost(
                    split[1].split("/")[0],
                    listOf(split[0])
                )
                1 -> allowHost(
                    split[0].split("/")[0],
                    listOf("http", "https")
                )
            }
        }
    }

    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }

    install(CallLogging) {
        level = Level.INFO
        val wrapper = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? BsLogWrapperLogback
        wrapper?.logger?.also { logger = it }
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        loggerSecurity.error("Groups claim must not be empty in JWT token")
                        null
                    }
                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
}