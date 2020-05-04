package br.com.devcave.kafka.beginners;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";

        // Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());


        // Create kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Create Producer record
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "first_topic", "hello java world");
        // Send data
        producer.send(record);

        producer.flush();
        producer.close();
    }
}
