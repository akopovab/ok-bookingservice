package ru.otuskotlin.public.bookingservice.meeting

import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import ru.otuskotlin.public.bookingservice.meeting.app.meeting
import ru.otuskotlin.public.bookingservice.meeting.app.slot
import ru.otuskotlin.public.bookingservice.meeting.plugins.initAppSettings
import ru.otuskotlin.public.bookingservice.meeting.plugins.initPlugins

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module(appSettings: BsAppSettings = initAppSettings()) {
    initPlugins(appSettings)

    routing {
        route("api") {
            meeting(appSettings)
            slot(appSettings)
        }
    }

}