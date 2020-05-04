package br.com.devcave.kafka.cloudstream.consumer

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel


const val CONSUMER_CHANNEL = "cloud-stream-input"

interface ConsumerClient {
    @Input(CONSUMER_CHANNEL)
    fun input(): SubscribableChannel
}