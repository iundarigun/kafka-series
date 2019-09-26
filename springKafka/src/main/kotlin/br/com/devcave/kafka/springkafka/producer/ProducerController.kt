package br.com.devcave.kafka.springkafka.producer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    @Value("\${kafka.topic-name}") val topicName: String,
    @Autowired val kafkaTemplate: KafkaTemplate<String, String>
) {

    @GetMapping("producer/{text}")
    fun produceMessage(@PathVariable text:String): HttpEntity<Any?> {
        kafkaTemplate.send(topicName, text)
        return ResponseEntity(HttpStatus.CREATED)
    }
}