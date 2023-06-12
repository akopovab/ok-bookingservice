package ru.otuskotlin.public.bookingservice.app.kafka.configuration

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInMeeting: String = KAFKA_TOPIC_IN_MEETING,
    val kafkaTopicOutMeeting: String = KAFKA_TOPIC_OUT_MEETING,
    val kafkaTopicInSlot: String = KAFKA_TOPIC_IN_SLOT,
    val kafkaTopicOutSlot: String = KAFKA_TOPIC_OUT_SLOT
) {
    companion object {
        private const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        private const val KAFKA_TOPIC_IN_MEETING_VAR = "KAFKA_TOPIC_IN_MEETING"
        private const val KAFKA_TOPIC_OUT_MEETING_VAR = "KAFKA_TOPIC_OUT_MEETING"
        private const val KAFKA_TOPIC_IN_SLOT_VAR = "KAFKA_TOPIC_IN_SLOT"
        private const val KAFKA_TOPIC_OUT_SLOT_VAR = "KAFKA_TOPIC_OUT_SLOT"
        private const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "bookingservice" }
        val KAFKA_TOPIC_IN_MEETING by lazy { System.getenv(KAFKA_TOPIC_IN_MEETING_VAR) ?: "in-meeting" }
        val KAFKA_TOPIC_OUT_MEETING by lazy { System.getenv(KAFKA_TOPIC_OUT_MEETING_VAR) ?: "out-meeting" }
        val KAFKA_TOPIC_IN_SLOT by lazy { System.getenv(KAFKA_TOPIC_IN_SLOT_VAR) ?: "in-slot" }
        val KAFKA_TOPIC_OUT_SLOT by lazy { System.getenv(KAFKA_TOPIC_OUT_SLOT_VAR) ?: "out-slot"}
    }

    fun createKafkaConsumer() : KafkaConsumer<String, String> {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHosts)
            put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        }
        return KafkaConsumer<String, String>(props)
    }

    fun createKafkaProducer(): KafkaProducer<String, String> {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHosts)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        }
        return KafkaProducer<String, String>(props)
    }

}