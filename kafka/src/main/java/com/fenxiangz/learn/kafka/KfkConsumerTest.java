package com.fenxiangz.learn.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KfkConsumerTest {
    private static Logger logger = LoggerFactory.getLogger(KfkConsumerTest.class);

    public static String TOPIC_NAME = "java-producer-api-test";

    public static void main(String[] args) {
        try {
            Consumer<String, String> consumer = getConsumer();
            consumeData0(consumer);
            return;
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private static void consumeData0(Consumer<String, String> consumer) throws InterruptedException {
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                logger.info("offset = {}, key = {}, value = {}",
                        record.offset(), record.key(), record.value());
            }
            Thread.sleep(500);
        }
    }

    private static Consumer<String, String> getConsumer() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer(props);
    }
}
