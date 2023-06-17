package ru.otuskotlin.public.bookingservice.meeting.plugins

import BsLogWrapperLogback
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import org.slf4j.event.Level
import ru.otuskotlin.public.bookingservice.api.apiV1Mapper
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings
import ru.otuskotlin.public.bookingservice.meeting.module

private val clazz = Application::module::class.qualifiedName ?: "Application"

fun Application.initPlugins(appSettings: BsAppSettings) {

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)

    install(CORS) {
        allowMethod(HttpMethod.Post)
        appSettings.appUrls.forEach {
            val split = it.split("://")
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
}