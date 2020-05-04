package br.com.devcave.kafka.cloudstream.producer

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel



interface ProducerClient {
    @Output("cloud-stream-output")
    fun output(): MessageChannel
}
