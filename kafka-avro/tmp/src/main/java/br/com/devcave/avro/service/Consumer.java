package br.com.devcave.avro.service;

import br.com.devcave.avro.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    @KafkaListener(topics = "user-replication", groupId = "group_id")
    public void consume(ConsumerRecord<String, User> record) {
        log.info("Consumed message -> {}", record.value());
    }
}
