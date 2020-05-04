package br.com.devcave.alura.kafka.domain

import java.math.BigDecimal

data class Order(
    val userId: String,
    val orderId: String,
    val amount: BigDecimal
)
