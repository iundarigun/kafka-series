package br.com.devcave.kafka.cloudstream.consumer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.cloud.stream.annotation.StreamListener
import java.util.*


@Component
class ConsumerListener {

    val log: Logger = LoggerFactory.getLogger(ConsumerListener::class.java)

    @StreamListener(CONSUMER_CHANNEL)
    fun listen(message: String) {
        val index = Random().nextInt()
        log.info("M=listen, message=$message, index=$index")
        Thread.sleep(10000)
        log.info("M=listen, endConsumer, index=$index")
    }
}