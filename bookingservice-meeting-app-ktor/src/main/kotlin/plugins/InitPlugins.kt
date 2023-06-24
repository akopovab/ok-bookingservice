package ru.otuskotlin.public.bookingservice.meeting.plugins


import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
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
import ru.otuskotlin.public.bookingservice.meeting.module

private val clazz = Application::module::class.qualifiedName ?: "Application"

fun Application.initPlugins(appSettings: BsAppSettings) {

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CORS)

    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }

    install(CallLogging) {
        level = Level.INFO
        val wrapper = appSettings
            .logSettings
            .loggerProvider
            .logger(clazz) as? BsLogWrapperLogback
        wrapper?.logger?.also { logger = it }
    }
}