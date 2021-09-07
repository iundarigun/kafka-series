package br.com.devcave.avro.producer;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {
    public static void main(final String[] args) {
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, String.class.getName());
    }
}
