package br.com.devcave.avro.service;

import br.com.devcave.avro.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(final User user) {
        this.kafkaTemplate.send("user-replication", user.getFirstName(), user);
        log.info(String.format("Produced user -> %s", user));
    }
}