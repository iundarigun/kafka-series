package br.com.devcave.kafka.springkafka.consumer

import br.com.devcave.kafka.springkafka.domain.Employee
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.Random


@Service
class MessageListener {

    val log: Logger = LoggerFactory.getLogger(MessageListener::class.java)

    @KafkaListener(
        topics = ["\${kafka.topic-name}"],
        containerFactory = "filterKafkaListenerContainerFactory",
        concurrency = "\${kafka.consumer.concurrency}"
        )
    fun listenWithFilter(message: String) {
        val index = Random().nextInt()
        log.info("Recieved Message: $message, index=$index")
        Thread.sleep(10_000)
        log.info("finish, index=$index")
    }

    @KafkaListener(
        topics = ["\${kafka.topic-json-name}"],
        containerFactory = "kafkaJsonListenerContainerFactory",
        concurrency = "\${kafka.consumer.concurrency}"
    )
    fun listenJson(message: Employee) {
        log.info("Recieved Message: $message")
    }
}