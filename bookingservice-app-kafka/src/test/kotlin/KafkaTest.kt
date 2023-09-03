package ru.otuskotlin.public.bookingservice.app.kafka

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
//import org.junit.Test
import ru.otuskotlin.public.bookingservice.api.apiMeetingRequestSerialize
import ru.otuskotlin.public.bookingservice.api.apiMeetingResponseDeserialize
import ru.otuskotlin.public.bookingservice.api.apiSlotRequestSerialize
import ru.otuskotlin.public.bookingservice.api.apiSlotResponseDeserialize
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategyMeeting
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategySlot
import java.util.*
//import kotlin.test.assertEquals

class KafkaTest : FunSpec({

    val PARTITION = 0

    test("runMeetingKafka") {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = KafkaConfig()
        val inputTopic = config.kafkaTopicInMeeting
        val outputTopic = config.kafkaTopicOutMeeting

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyMeeting()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "meeting-1",
                    apiMeetingRequestSerialize(
                        MeetingCreateRequest(
                            requestType = "create",
                            requestId = "1234",
                            debug = Debug(
                                mode = RequestDebugMode.STUB,
                                stub = RequestDebugStubs.SUCCESS
                            ),
                            meeting = MeetingCreateObject(
                                clientId = "1230984567",
                                employeeId = "0987654321",
                                description = "Запись на стрижку",
                                slots = listOf(SlotIdListSlotsInner("123"), SlotIdListSlotsInner("456"))
                            )
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiMeetingResponseDeserialize<MeetingCreateResponse>(message.value())

        outputTopic shouldBe message.topic()
        "1234" shouldBe result.requestId
        "1230984567" shouldBe result.meeting?.clientId
        "0987654321" shouldBe result.meeting?.employeeId
        "Запись на стрижку" shouldBe result.meeting?.description
    }

    test("runSlotKafka") {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = KafkaConfig()
        val inputTopic = config.kafkaTopicInSlot
        val outputTopic = config.kafkaTopicOutSlot

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategySlot()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "slot-1",
                    apiSlotRequestSerialize(
                        SlotSearchRequest(
                            requestType = "create",
                            requestId = "12345",
                            debug = Debug(
                                mode = RequestDebugMode.STUB,
                                stub = RequestDebugStubs.SUCCESS
                            ),
                            employeeId = "0987654321"
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiSlotResponseDeserialize<SlotSearchResponse>(message.value())
        outputTopic shouldBe message.topic()
        "12345" shouldBe result.requestId
        "123000111" shouldBe result.slots?.get(0)?.slotId
        "123000222" shouldBe result.slots?.get(1)?.slotId
    }

})