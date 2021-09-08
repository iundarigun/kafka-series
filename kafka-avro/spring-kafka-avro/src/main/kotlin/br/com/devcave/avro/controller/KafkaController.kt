package br.com.devcave.avro.controller

import br.com.devcave.avro.service.Producer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("kafka")
class KafkaController(
    private val producer: Producer
) {

    @PostMapping
    fun sendUser(): String {
        producer.send()
        return ""
    }
}