package br.com.devcave.avro.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class KafkaConfiguration {
    @Bean
    NewTopic moviesTopic() {
        return new NewTopic("user-replication", 3, (short) 1);
    }
}
