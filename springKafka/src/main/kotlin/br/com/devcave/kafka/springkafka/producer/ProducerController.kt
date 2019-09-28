package br.com.devcave.kafka.springkafka.producer

import br.com.devcave.kafka.springkafka.domain.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.LocalDate

@RestController
class ProducerController(
    @Value("\${kafka.topic-name}") val topicName: String,
    @Value("\${kafka.topic-json-name}") val topicJsonName: String,
    @Autowired val kafkaTemplate: KafkaTemplate<String, String>,
    @Autowired val kafkaJsonTemplate: KafkaTemplate<String, Employee>
) {

    @GetMapping("producer/{text}")
    fun produceMessage(@PathVariable text:String): HttpEntity<Any?> {
        kafkaTemplate.send(topicName, text)
        return ResponseEntity(HttpStatus.CREATED)
    }
    @GetMapping("producer-json")
    fun produceJson(): HttpEntity<Any?> {
        kafkaJsonTemplate.send(topicJsonName,
            Employee(name = "uri",
                admissionDate = LocalDate.of(2019,9, 23),
                email = "oriol.canalias@gmail.com"))
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("employee")
    fun employee(): HttpEntity<Employee> {
        val employee = Employee(
            name = "uri",
            admissionDate = LocalDate.of(2019, 9, 23),
            email = "oriol.canalias@gmail.com"
        )
        return ResponseEntity.ok(employee)
    }

}