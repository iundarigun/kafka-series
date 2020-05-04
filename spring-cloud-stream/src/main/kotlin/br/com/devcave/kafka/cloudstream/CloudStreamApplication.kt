package br.com.devcave.kafka.cloudstream

import br.com.devcave.kafka.cloudstream.producer.ProducerClient
import br.com.devcave.kafka.cloudstream.consumer.ConsumerClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(ProducerClient::class, ConsumerClient::class)
class CloudStreamApplication

	fun main(args: Array<String>) {
		runApplication<CloudStreamApplication>(*args)
	}
