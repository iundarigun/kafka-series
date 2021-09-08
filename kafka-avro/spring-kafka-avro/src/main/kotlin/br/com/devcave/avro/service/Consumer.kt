package br.com.devcave.avro.service

import br.com.devcave.avro.domain.User
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["user-avro"])
    fun listener(record: ConsumerRecord<String, User>) {
        logger.info("receive, user={}", record.value())
    }
}