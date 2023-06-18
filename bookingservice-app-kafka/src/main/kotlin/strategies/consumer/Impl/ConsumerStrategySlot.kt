package ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl

import ru.otuskotlin.public.bookingservice.api.apiSlotRequestDeserialize
import ru.otuskotlin.public.bookingservice.api.apiSlotResponseSerialize
import ru.otuskotlin.public.bookingservice.api.models.ISlotRequest
import ru.otuskotlin.public.bookingservice.api.models.ISlotResponse
import ru.otuskotlin.public.bookingservice.app.kafka.InputOutputTopics
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategy
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsSlotContext
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportSlot
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportSlot

class ConsumerStrategySlot : ConsumerStrategy<BsSlotContext> {
    override fun topics(config: KafkaConfig): InputOutputTopics =
        InputOutputTopics(config.kafkaTopicInSlot, config.kafkaTopicOutSlot)

    override fun serialize(source: BsContext): String {
        val response: ISlotResponse = (source as BsSlotContext).toTransportSlot()
        return apiSlotResponseSerialize(response)
    }

    override fun deserialize(value: String, target: BsContext) {
        val request: ISlotRequest = apiSlotRequestDeserialize(value)
        (target as BsSlotContext).fromTransportSlot(request)
    }
}
