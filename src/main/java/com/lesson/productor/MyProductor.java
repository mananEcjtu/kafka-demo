package com.lesson.productor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MyProductor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.139.5:9093");
        props.put("acks", "all");
        props.put("retries", 2);
        // 每一批消息最大的大小
        props.put("batch.size", 16384);
        // 延迟时间（毫秒）
        props.put("linger.ms", 1000);
//        props.put("buffer.memory", 33554432);

        // 幂等性
//        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_DOC, true);
//        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
//        props.put(ProducerConfig.ACKS_CONFIG, "all");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 20; i++) {
            String key = "key1";
            if (i > 10) {
                key = "key2";
            }
            producer.send(new ProducerRecord<>("my-topic", key, Integer.toString(i)));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        producer.close();
    }
}
