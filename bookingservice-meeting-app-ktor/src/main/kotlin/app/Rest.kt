package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otuskotlin.public.bookingservice.meeting.BsAppSettings

fun Route.meeting(appSettings: BsAppSettings) {
    val logger = appSettings.logSettings.loggerProvider.logger(Route::meeting)
    route("meeting") {
        post("create") {
            call.createMeeting(logger, appSettings)
        }
        post("read") {
            call.readMeeting(logger, appSettings)
        }
        post("update") {
            call.updateMeeting(logger, appSettings)
        }
        post("delete") {
            call.deleteMeeting(logger, appSettings)
        }
        post("search") {
            call.searchMeetings(logger, appSettings)
        }
    }
}

fun Route.slot(appSettings: BsAppSettings) {
    val logger = appSettings.logSettings.loggerProvider.logger(Route::slot)
    route("slot") {
        post("search") {
            call.searchSlot(logger, appSettings)
        }
    }

}