package br.com.devcave.spring.controller;

import br.com.devcave.spring.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("kafka")
@RequiredArgsConstructor
public class ProducerController {
    private final KafkaTemplate<String, Customer> kafkaTemplate;

    @PostMapping
    public void createCustomer() {
        final Customer customer = Customer.builder()
                .name("Oriol")
                .id(new Random().nextLong())
                .birthday(LocalDate.of(1980, 8, 7))
                .build();
        final String key = new Random().nextBoolean() ? "oriol" : customer.getId().toString();
        kafkaTemplate.send("customer-topic", key, customer);
    }
}
