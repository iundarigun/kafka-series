package br.com.devcave.spring.listener;

import br.com.devcave.spring.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "customer-topic", concurrency = "1")
    public void listener(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) final int partition, final Customer customer) {
        log.info("partition={}, customer, {}", partition, customer);
    }
}
