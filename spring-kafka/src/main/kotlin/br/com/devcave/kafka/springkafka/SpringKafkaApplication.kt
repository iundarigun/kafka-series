package br.com.devcave.kafka.springkafka

import br.com.devcave.kafka.springkafka.consumer.ProducerClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@EnableBinding(ProducerClient::class)
@SpringBootApplication
class SpringKafkaApplication

fun main(args: Array<String>) {
	runApplication<SpringKafkaApplication>(*args)
}
