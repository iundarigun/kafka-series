package br.com.devcave.kafka.cloudstream.consumer

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel



interface ConsumerClient {
    @Input("cloud-stream-input")
    fun input(): SubscribableChannel
}