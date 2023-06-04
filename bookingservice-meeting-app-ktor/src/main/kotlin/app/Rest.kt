package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.meeting() {
    route("meeting") {
        post("create") {
            call.createMeeting()
        }
        post("read") {
            call.readMeeting()
        }
        post("update") {
            call.updateMeeting()
        }
        post("delete") {
            call.deleteMeeting()
        }
        post("search") {
            call.searchMeetings()
        }
    }
}

fun Route.slot() {
    route("slot") {
        post("search") {
            call.searchSlot()
        }
    }

}