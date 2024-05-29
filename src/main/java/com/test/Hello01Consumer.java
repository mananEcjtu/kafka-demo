package com.test;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class Hello01Consumer extends Thread {

    private final Consumer<String, String> consumer;

    public Hello01Consumer(String name) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.139.5:9093,192.168.139.5:9094,192.168.139.5:9095");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "co-group");
        //properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        //properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(properties);
    }

    @Override
    public void run() {
        String topic = "user-log";
        TopicPartition partition = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);
        // consumer.assign(Arrays.asList(partition,partition1));
        consumer.subscribe(Arrays.asList(topic));
        // consumer.seek(partition, 2);
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("分区:" + record.partition() + " " + record.value() + " " + record.key());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        Hello01Consumer consumer = new Hello01Consumer("太原");
        consumer.start();
    }

}
