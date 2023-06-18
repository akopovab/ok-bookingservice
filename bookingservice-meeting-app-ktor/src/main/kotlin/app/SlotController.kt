package ru.otuskotlin.public.bookingservice.meeting.app

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otuskotlin.public.bookingservice.api.models.SlotSearchRequest
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportSlot
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportSlot
import ru.otuskotlin.public.bookingservice.stubs.SlotStub

suspend fun ApplicationCall.searchSlot(){
    val request = receive<SlotSearchRequest>()
    val context = BsSlotContext()
    context.fromTransportSlot(request)
    context.slotResponse = SlotStub.getSlots()
    respond(context.toTransportSlot())
}