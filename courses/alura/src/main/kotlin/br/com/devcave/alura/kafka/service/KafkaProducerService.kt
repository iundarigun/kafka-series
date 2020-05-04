package br.com.devcave.alura.kafka.service

import br.com.devcave.alura.kafka.utils.GsonSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.io.Closeable
import java.util.Properties

class KafkaProducerService<T> : Closeable {

    private val producer = KafkaProducer<String, T>(properties())

    fun send(topic: String, key: String, value: T) {
        val producerRecord = ProducerRecord(topic, key, value)
        producer.send(producerRecord) { data, ex ->
            ex?.let {
                it.printStackTrace()
            } ?: println(
                "topico-${data.topic()}:::" +
                        "partition-${data.partition()}/" +
                        "offset-${data.offset()}/" +
                        "timestamp-${data.timestamp()}"
            )
        }.get()
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer::class.java.name)
        return properties
    }

    override fun close() {
        producer.close()
    }
}