package br.com.devcave.avro

import br.com.devcave.Customer

fun main() {
    val customer = Customer.newBuilder()
    .setFirstName("John")
        .setLastName("Doe")
        .setAge(25)
        .setHeight(175f)
        .setWeight(85.5f)
        .build()

    println(customer)
}