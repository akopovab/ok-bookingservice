package ru.otuskotlin.public.bookingservice.app.kafka

import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl.ConsumerStrategyMeeting
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.impl.ConsumerStrategySlot


fun main() {
    val config =  KafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyMeeting(), ConsumerStrategySlot()))
    consumer.run()
}
