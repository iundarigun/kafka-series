package br.com.devcave.kafka.springkafka.consumer

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import java.util.HashMap
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.context.annotation.Bean





@EnableKafka
@Configuration
class ConsumerConfiguration (
    @Value(value = "\${kafka.bootstrap-servers}")
    val bootstrapAddress: String,
    @Value(value = "\${kafka.consumer.group-id}")
    val groupId: String
) {

    fun consumerFactory(groupId: String): ConsumerFactory<String, String> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    fun kafkaListenerContainerFactory(groupId: String): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.consumerFactory = consumerFactory(groupId)
        return factory
    }

    @Bean
    fun filterKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
        val factory = kafkaListenerContainerFactory(groupId)
        factory.setRecordFilterStrategy { record ->
            record.value()
                .contains("World")
        }
        return factory
    }
}