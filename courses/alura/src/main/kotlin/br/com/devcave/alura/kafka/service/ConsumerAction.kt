package br.com.devcave.alura.kafka.service

import org.apache.kafka.clients.consumer.ConsumerRecord

interface ConsumerAction<T> {
    fun consume(consumerRecord: ConsumerRecord<String, T>)
}
