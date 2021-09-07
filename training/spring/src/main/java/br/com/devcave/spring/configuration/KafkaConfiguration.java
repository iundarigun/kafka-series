package br.com.devcave.spring.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final ObjectMapper objectMapper;
    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        return new DefaultKafkaProducerFactory(
                Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProducer().getBootstrapServers(),
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer(),
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer()),
                new StringSerializer(),
                new JsonSerializer<>(objectMapper));

    }

    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate(final ProducerFactory<String, ?> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, ?> consumerFactory() {
        final JsonDeserializer<Object> objectJsonDeserializer = new JsonDeserializer<>(objectMapper);
        objectJsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory(
                Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getConsumer().getBootstrapServers(),
                        ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId(),
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer(),
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer()),
                new StringDeserializer(),
                objectJsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ?> concurrentKafkaListenerContainerFactory(
            final ConsumerFactory<String, Object> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, ?> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordFilterStrategy(consumerRecord -> {
            log.info("consumer key={}", consumerRecord.key());
            return consumerRecord.key().equals("oriol");
        });
        return factory;
    }
}
