package br.com.devcave.kafka.springkafka.consumer

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel



interface ProducerClient {
    @Output("cloud-stream-output")
    fun output(): MessageChannel
}
