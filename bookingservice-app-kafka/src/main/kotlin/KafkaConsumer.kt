package ru.otuskotlin.public.bookingservice.app.kafka

import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.otuskotlin.public.bookingservice.app.kafka.configuration.KafkaConfig
import ru.otuskotlin.public.bookingservice.common.context.BsContext
import java.time.Duration
import java.util.*
import mu.KotlinLogging
import ru.otuskotlin.public.bookingservice.app.kafka.strategies.consumer.ConsumerStrategy


private val log = KotlinLogging.logger {}

class AppKafkaConsumer(
    private val config: KafkaConfig,
    consumerStrategies: List<ConsumerStrategy<out BsContext>>,
    private val consumer: Consumer<String, String> = config.createKafkaConsumer(),
    private val producer: Producer<String, String> = config.createKafkaProducer()
) {

    private val process = atomic(true)
    private val topicsAndStrategyByInputTopic = consumerStrategies.associate {
        val topics = it.topics(config)
        Pair(
            topics.input,
            TopicsAndStrategy(topics.input, topics.output, it)
        )
    }

    fun run() = runBlocking {
        try {
            consumer.subscribe(topicsAndStrategyByInputTopic.keys)
            while (process.value) {

                val records: ConsumerRecords<String, String> = withContext(Dispatchers.IO) {
                    consumer.poll(Duration.ofSeconds(1))
                }
                if (!records.isEmpty)
                    log.info { "Receive ${records.count()} messages" }

                records.forEach { record: ConsumerRecord<String, String> ->
                    try {
                        log.info { "process ${record.key()} from ${record.topic()}:\n${record.value()}" }
                        val (_, outputTopic, strategy) = topicsAndStrategyByInputTopic[record.topic()]
                            ?: throw RuntimeException("Receive message from unknown topic ${record.topic()}")
                        strategy.apply {
                            deserialize(record.value())
                            processor()
                        }
                        sendResponse(strategy, outputTopic)
                    } catch (ex: Exception) {
                        log.error(ex) { "error" }
                    }
                }
            }
        } catch (ex: WakeupException) {
            // ignore for shutdown
        } catch (ex: RuntimeException) {
            // exception handling
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private fun sendResponse(strategy: ConsumerStrategy<out BsContext>, outputTopic: String) {
        val json = strategy.serialize()
        val resRecord = ProducerRecord(
            outputTopic,
            UUID.randomUUID().toString(),
            json
        )
        log.info { "sending ${resRecord.key()} to $outputTopic:\n$json" }
        producer.send(resRecord)
    }

    fun stop() {
        process.value = false
    }

    private data class TopicsAndStrategy<T : BsContext>(
        val inputTopic: String,
        val outputTopic: String,
        val strategy: ConsumerStrategy<T>
    )
}