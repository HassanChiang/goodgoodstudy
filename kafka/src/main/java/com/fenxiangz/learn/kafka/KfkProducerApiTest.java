package com.fenxiangz.learn.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KfkProducerApiTest {
    private static Logger logger = LoggerFactory.getLogger(KfkProducerApiTest.class);

    public static String TOPIC_NAME = "java-producer-api-test";

    public static void main(String[] args) {
        try {
            Producer<String, String> producer = getProducer();
            produceData(producer);
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void produceData(Producer<String, String> producer) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 20; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "test " + i);
            Future<RecordMetadata> send = producer.send(record);
            RecordMetadata result = send.get();
            logger.info("===produceData{}:{}, {}, {}, {}", i,
                    result.topic(), result.hasOffset(), result.offset(), result.partition());
        }
    }

    private static Producer<String, String> getProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "0");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String, String>(properties);
    }
}
