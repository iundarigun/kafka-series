package br.com.devcave.spring.listener;

import br.com.devcave.spring.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

//    @KafkaListener(topics = "customer-topic", concurrency = "1")
    public void listener(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) final int partition,
                         @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) final String key,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic,
                         final Customer customer) {
        log.info("partition={}, key {}. topic={}, customer, {}", partition, key, topic, customer);
    }

    @KafkaListener(topics = "customer-topic", concurrency = "10", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listenerConsumerRecord(final ConsumerRecord<String, Customer> record) {
        log.info("partition={}, key {}. topic={}, customer, {}", record.partition(), record.key(), record.topic(), record.value());
    }
}
