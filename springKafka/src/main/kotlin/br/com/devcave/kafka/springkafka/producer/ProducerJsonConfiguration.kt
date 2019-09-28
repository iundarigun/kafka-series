package br.com.devcave.kafka.springkafka.producer

import br.com.devcave.kafka.springkafka.domain.Employee
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class ProducerJsonConfiguration {
    @Bean
    fun producerJsonFactory(@Value("\${kafka.bootstrap-servers}") address: String,
                            objectMapper: ObjectMapper): ProducerFactory<String, Employee> =
        DefaultKafkaProducerFactory(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to address,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
            ),
            StringSerializer(),
            JsonSerializer<Employee>(objectMapper)
        )

    @Bean
    fun kafkaJsonTemplate(producerJsonFactory: ProducerFactory<String, Employee>): KafkaTemplate<String, Employee> {
        return KafkaTemplate(producerJsonFactory)
    }
}