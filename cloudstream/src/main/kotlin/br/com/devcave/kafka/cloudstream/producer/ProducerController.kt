package br.com.devcave.kafka.cloudstream.producer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class ProducerController(@Autowired val producerClient: ProducerClient) {

    @GetMapping("producer/{text}")
    fun produceMessage(@PathVariable text:String):HttpEntity<Any?> {

        val message:Message<String> = MessageBuilder.withPayload(text).build()
        producerClient.output().send(message)

        return ResponseEntity(HttpStatus.CREATED)
    }
}