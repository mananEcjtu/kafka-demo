package com.test;

import com.ieslab.protobuf.UserProto;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class Hello01ConsumerBuf extends Thread {

    private final Consumer<byte[], byte[]> consumer;

    private static final String deSerial = ByteArrayDeserializer.class.getName();

    public Hello01ConsumerBuf(String name) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.139.5:9093,192.168.139.5:9094,192.168.139.5:9095");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "co-group");
        //properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        //properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deSerial);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deSerial);
        consumer = new KafkaConsumer<>(properties);
    }

    @Override
    public void run() {
        String topic = "user-log";
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<byte[], byte[]> records = consumer.poll(100);
                for (ConsumerRecord<byte[], byte[]> record : records) {
                    UserProto.User value = UserProto.User.parseFrom(record.value());
                    String key = new String(record.key());
                    System.out.println("分区:" + record.partition() + " " + value.toString() + " " + key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    public static void main(String[] args) {
        Hello01ConsumerBuf consumer = new Hello01ConsumerBuf("太原");
        consumer.start();
    }

}
