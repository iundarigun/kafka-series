package br.com.devcave.alura.kafka.service

import br.com.devcave.alura.kafka.utils.GsonDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.io.Closeable
import java.time.Duration
import java.util.Properties
import java.util.regex.Pattern

class KafkaConsumerService<T>(
    groupId: String,
    private val topicName: String? = null,
    private val topicPattern: Pattern? = null,
    private val consumerAction: ConsumerAction<T>,
    clazz: Class<T>,
    map: Map<String, String> = mapOf()
) : Closeable {

    private val kafkaConsumer = KafkaConsumer<String, T>(properties(groupId, clazz, map))

    fun run() {
        topicName?.let {
            kafkaConsumer.subscribe(listOf(topicName))
        }
        topicPattern?.let {
            kafkaConsumer.subscribe(topicPattern)
        }
        while (true) {
            val records = kafkaConsumer.poll(Duration.ofMillis(100))
            if (records.isEmpty.not()) {
                for (record in records) {
                    consumerAction.consume(record)
                }
            }
        }
    }

    private fun properties(groupId: String, clazz: Class<T>, map: Map<String, String>): Properties {
        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, clazz.name)
        map.forEach { (key, value) -> properties.setProperty(key, value) }
        return properties
    }

    override fun close() {
        kafkaConsumer.close()
    }
}