package br.com.devcave.kafka.springkafka.consumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.kafka.annotation.KafkaListener
import java.util.*


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
        println("Recieved Message: $message, index=$index")
        Thread.sleep(10_000)
        println("finish, index=$index")
    }
}