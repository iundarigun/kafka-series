package br.com.devcave.alura.kafka.consumers

import br.com.devcave.alura.kafka.service.ConsumerAction
import br.com.devcave.alura.kafka.service.KafkaConsumerService
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.regex.Pattern

fun main() {
    KafkaConsumerService(
        groupId = "logConsumer", topicPattern = Pattern.compile("ECOMMERCE.*"),
        consumerAction = object : ConsumerAction<String> {
            override fun consume(record: ConsumerRecord<String, String>) {
                println("LOG")
                println("key ${record.key()}")
                println("value ${record.value()}")
                println("partition ${record.partition()}")
                println("offset ${record.offset()}")
            }
        }, clazz = String::class.java,
        map = mapOf(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name)
    ).use {
        it.run()
    }
}