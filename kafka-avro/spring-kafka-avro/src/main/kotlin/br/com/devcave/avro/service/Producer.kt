package br.com.devcave.avro.service

import br.com.devcave.avro.domain.User
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, User>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun send() {
        val user = User.newBuilder()
            .setFirstName("Uri")
            .setLastName("Cana")
            .setAge(41)
            .setHeight(170f)
            .build()
        logger.info("send, user={}", user)
        kafkaTemplate.send("user-avro", Random.nextInt().toString(), user)
    }
}