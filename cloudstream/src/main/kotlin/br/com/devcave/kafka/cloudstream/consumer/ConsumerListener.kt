package br.com.devcave.kafka.cloudstream.consumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.cloud.stream.annotation.StreamListener



@Component
class ConsumerListener {

    val log: Logger = LoggerFactory.getLogger(ConsumerListener::class.java)

    @StreamListener("cloud-stream-input")
    fun listen(message: String) {
        log.info("M=listen, message={}", message)
    }
}