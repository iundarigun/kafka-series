package br.com.devcave.kafka.springkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKafkaApplication

fun main(args: Array<String>) {
	runApplication<SpringKafkaApplication>(*args)
}
