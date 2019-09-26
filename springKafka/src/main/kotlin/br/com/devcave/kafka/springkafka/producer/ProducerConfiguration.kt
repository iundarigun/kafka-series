package br.com.devcave.kafka.springkafka.producer

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.core.KafkaTemplate



@Configuration
class ProducerConfiguration {
    @Bean
    fun producerFactory(@Value("\${kafka.bootstrap-servers}") address: String): ProducerFactory<String, String> =
        DefaultKafkaProducerFactory(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
            )
        )

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory)
    }
}