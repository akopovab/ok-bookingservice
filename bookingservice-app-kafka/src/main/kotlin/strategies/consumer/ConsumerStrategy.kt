package ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer

import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.InputOutputTopics
import ru.otuskotlin.public.bookingservice.common.context.BsContext

interface ConsumerStrategy<T> {
    fun topics(config: KafkaConfig): InputOutputTopics

    fun serialize(): String

    fun deserialize(value: String)

    suspend fun processor()
}