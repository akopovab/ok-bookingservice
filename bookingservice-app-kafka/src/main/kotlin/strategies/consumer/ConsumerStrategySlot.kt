package ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer

import kotlinx.datetime.Clock
import ru.otuskotlin.public.bookingservice.api.apiSlotRequestDeserialize
import ru.otuskotlin.public.bookingservice.api.apiSlotResponseSerialize
import ru.otuskotlin.public.bookingservice.api.models.ISlotRequest
import ru.otuskotlin.public.bookingservice.api.models.ISlotResponse
import ru.otuskotlin.public.bookingservice.app.kafka.InputOutputTopics
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.business.processors.SlotProcessor
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportSlot
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting

class ConsumerStrategySlot : ConsumerStrategy<BsSlotContext> {

    private val context = BsSlotContext().apply { this.timeStart = Clock.System.now() }

    override fun topics(config: KafkaConfig): InputOutputTopics =
        InputOutputTopics(config.kafkaTopicInSlot, config.kafkaTopicOutSlot)

    override fun serialize(): String {
        val response: ISlotResponse = context.toTransportMeeting()
        return apiSlotResponseSerialize(response)
    }

    override fun deserialize(value: String) {
        val request: ISlotRequest = apiSlotRequestDeserialize(value)
        context.fromTransportSlot(request)
    }

    override suspend fun processor(){
        SlotProcessor().exec(context)
    }
}
