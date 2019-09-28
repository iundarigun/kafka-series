package br.com.devcave.kafka.springkafka.consumer

import br.com.devcave.kafka.springkafka.domain.Employee
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*


@EnableKafka
@Configuration
class ConsumerConfiguration (
    @Value(value = "\${kafka.bootstrap-servers}")
    val bootstrapAddress: String,
    @Value(value = "\${kafka.consumer.group-id}")
    val groupId: String,
    @Autowired
    val objectMapper: ObjectMapper
) {
    fun consumerFactory(groupId: String): ConsumerFactory<String, String> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    fun consumerJsonFactory(groupId: String): ConsumerFactory<String, Employee> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"

        val jsonDeserializer = JsonDeserializer<Employee>(objectMapper)
        jsonDeserializer.typeMapper.addTrustedPackages("br.com.devcave.kafka.springkafka.domain")

        return DefaultKafkaConsumerFactory<String, Employee>(
            props,
            StringDeserializer(),
            jsonDeserializer
        )
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

    @Bean
    fun kafkaJsonListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Employee> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Employee>()
        factory.consumerFactory = consumerJsonFactory(groupId)
        return factory
    }
}