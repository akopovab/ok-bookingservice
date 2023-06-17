package ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl

import ru.otuskotlin.public.bookingservice.api.apiMeetingRequestDeserialize
import ru.otuskotlin.public.bookingservice.api.apiMeetingResponseSerialize
import ru.otuskotlin.public.bookingservice.api.models.IMeetingRequest
import ru.otuskotlin.public.bookingservice.api.models.IMeetingResponse
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.InputOutputTopics
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategy
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import ru.otuskotlin.public.bookingservice.mappers.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting

class ConsumerStrategyMeeting : ConsumerStrategy<BsMeetingContext> {
    override fun topics(config: KafkaConfig): InputOutputTopics =
        InputOutputTopics(config.kafkaTopicInMeeting, config.kafkaTopicOutMeeting)

    override fun serialize(source: BsContext): String {
        val response: IMeetingResponse = (source as BsMeetingContext).toTransportMeeting()
        return apiMeetingResponseSerialize(response)
    }

    override fun deserialize(value: String, target: BsContext) {
        val request: IMeetingRequest = apiMeetingRequestDeserialize(value)
        (target as BsMeetingContext).fromTransportMeeting(request)
    }
}