import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otuskotlin.public.bookingservice.api.apiMeetingRequestSerialize
import ru.otuskotlin.public.bookingservice.api.apiMeetingResponseDeserialize
import ru.otuskotlin.public.bookingservice.api.apiSlotRequestSerialize
import ru.otuskotlin.public.bookingservice.api.apiSlotResponseDeserialize
import ru.otuskotlin.public.bookingservice.api.models.*
import ru.otuskotlin.public.bookingservice.app.kafka.AppKafkaConsumer
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl.ConsumerStrategyMeeting
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl.ConsumerStrategySlot
import java.util.*
import kotlin.test.assertEquals

class KafkaTest {
    @Test
    fun runMeetingKafka() {
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
        assertEquals(outputTopic, message.topic())
        assertEquals("1234", result.requestId)
        assertEquals("1230984567", result.meeting?.clientId)
        assertEquals("0987654321", result.meeting?.employeeId)
        assertEquals("Запись на стрижку", result.meeting?.description)
    }

    @Test
    fun runSlotKafka() {
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
        assertEquals(outputTopic, message.topic())
        assertEquals("12345", result.requestId)
        assertEquals("123000111", result.slots?.get(0)?.slotId)
        assertEquals("1230002222", result.slots?.get(1)?.slotId)
    }

    companion object {
        const val PARTITION = 0
    }

}