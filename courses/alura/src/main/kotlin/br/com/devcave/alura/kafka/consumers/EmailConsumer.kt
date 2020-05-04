package br.com.devcave.alura.kafka.consumers

import br.com.devcave.alura.kafka.domain.Email
import br.com.devcave.alura.kafka.service.ConsumerAction
import br.com.devcave.alura.kafka.service.KafkaConsumerService
import org.apache.kafka.clients.consumer.ConsumerRecord

fun main() {
    KafkaConsumerService(groupId = "emailConsumer", topicName = "ECOMMERCE_SEND_EMAIL",
        consumerAction = object : ConsumerAction<Email> {
            override fun consume(record: ConsumerRecord<String, Email>) {
                println("Sending email")
                println("key ${record.key()}")
                println("value ${record.value()}")
                println("partition ${record.partition()}")
                println("offset ${record.offset()}")
                Thread.sleep(2000)
                println("Email sending!")
            }
        },
        clazz = Email::class.java).use {
        it.run()
    }
}

