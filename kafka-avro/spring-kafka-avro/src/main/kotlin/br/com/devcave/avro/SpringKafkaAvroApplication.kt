package br.com.devcave.avro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKafkaAvroApplication

fun main(args: Array<String>) {
	runApplication<SpringKafkaAvroApplication>(*args)
}
