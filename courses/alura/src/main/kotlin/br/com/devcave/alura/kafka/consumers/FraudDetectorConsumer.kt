package br.com.devcave.alura.kafka.consumers

import br.com.devcave.alura.kafka.domain.Order
import br.com.devcave.alura.kafka.service.ConsumerAction
import br.com.devcave.alura.kafka.service.KafkaConsumerService
import br.com.devcave.alura.kafka.service.KafkaProducerService
import org.apache.kafka.clients.consumer.ConsumerRecord
import java.math.BigDecimal

fun main() {
    val kafkaProducerService = KafkaProducerService<Order>()

    val kafkaConsumerService = KafkaConsumerService(groupId = "fraudDetectorConsumer",
        topicName = "ECOMMERCE_NEW_ORDER",
        consumerAction = object : ConsumerAction<Order> {
            override fun consume(record: ConsumerRecord<String, Order>) {
                println("Processing new order, checking fraud")
                println("key ${record.key()}")
                println("value ${record.value()}")
                println("partition ${record.partition()}")
                println("offset ${record.offset()}")
                Thread.sleep(2000)
                if (record.value().amount >= BigDecimal("4500")) {
                    println("fraud detected")
                    kafkaProducerService.send("ECOMMERCE_ORDER_REJECTED", record.key(), record.value())
                }
                else{
                    println("fraud approved")
                    kafkaProducerService.send("ECOMMERCE_ORDER_APPROVED", record.key(), record.value())
                }
                println("order processed!")
            }
        }, clazz = Order::class.java)
    kafkaConsumerService.run()
}