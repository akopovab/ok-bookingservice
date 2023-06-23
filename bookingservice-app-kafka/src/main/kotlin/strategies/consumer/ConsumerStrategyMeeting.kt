package ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer

import kotlinx.datetime.Clock
import ru.otuskotlin.public.bookingservice.api.apiMeetingRequestDeserialize
import ru.otuskotlin.public.bookingservice.api.apiMeetingResponseSerialize
import ru.otuskotlin.public.bookingservice.api.models.IMeetingRequest
import ru.otuskotlin.public.bookingservice.api.models.IMeetingResponse
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.InputOutputTopics
import ru.otuskotlin.public.bookingservice.common.context.Impl.BsMeetingContext
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategy
import ru.otuskotlin.public.bookingservice.business.processors.impl.MeetingProcessor
import ru.otuskotlin.public.bookingservice.mappers.mapper.fromTransportMeeting
import ru.otuskotlin.public.bookingservice.mappers.mapper.toTransportMeeting
import ru.otuskotlin.public.bookingservice.stubs.MeetingStub

class ConsumerStrategyMeeting : ConsumerStrategy<BsMeetingContext> {

    private val context = BsMeetingContext().apply { this.timeStart = Clock.System.now() }

    override fun topics(config: KafkaConfig): InputOutputTopics =
        InputOutputTopics(config.kafkaTopicInMeeting, config.kafkaTopicOutMeeting)

    override fun serialize(): String {
        val response: IMeetingResponse = context.toTransportMeeting()
        return apiMeetingResponseSerialize(response)
    }

    override fun deserialize(value: String) {
        val request: IMeetingRequest = apiMeetingRequestDeserialize(value)
        context.fromTransportMeeting(request)
    }

    override suspend fun processor(){
        MeetingProcessor().exec(context)
    }

}