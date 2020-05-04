package br.com.devcave.alura.kafka

import br.com.devcave.alura.kafka.domain.Email
import br.com.devcave.alura.kafka.domain.Order
import br.com.devcave.alura.kafka.service.KafkaProducerService
import java.math.BigDecimal
import java.util.UUID

fun main() {
    KafkaProducerService<Order>().use { orderProducer ->
        KafkaProducerService<Email>().use { emailProducer ->
            (1..10).forEach {
                val userId = UUID.randomUUID().toString()
                val orderId = UUID.randomUUID().toString()
                val amount = BigDecimal(Math.random() * 5000 + it)

                orderProducer.send("ECOMMERCE_NEW_ORDER", userId,
                    Order(userId, orderId, amount)
                )
                val subject = UUID.randomUUID().toString()
                val body = "Thanks you motherfucker number $it!"
                emailProducer.send("ECOMMERCE_SEND_EMAIL", userId, Email(subject, body))
            }
        }
    }
}
