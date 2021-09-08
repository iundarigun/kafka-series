package br.com.devcave.avro.producer;

import br.com.devcave.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

public class KafkaProducerSample {
    public static void main(final String[] args) {
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "10");

        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        properties.setProperty(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        try (KafkaProducer<String, Customer> kafkaProducer = new KafkaProducer<>(properties)) {

            final String topic = "customer-avro";

            final Customer customer = Customer.newBuilder()
                    .setFirstName("uri")
                    .setLastName("cana")
                    .setAge(20)
                    .setHeight(170f)
                    .setWeight(94.5f)
                    .build();

            final ProducerRecord<String, Customer> producerRecord =
                    new ProducerRecord<>(topic, String.valueOf(new Random().nextInt()), customer);

            kafkaProducer.send(producerRecord);
            kafkaProducer.flush();
        }
    }
}
