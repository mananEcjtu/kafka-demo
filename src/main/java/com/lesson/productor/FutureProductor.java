package com.lesson.productor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureProductor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.139.5:9093");
//        props.put("acks", "all");
//        props.put("retries", 0);
        // 每一批消息最大的大小
        // props.put("batch.size", 16384);
        // 延迟时间（毫秒）
        // props.put("linger.ms", 1000);
//        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 20; i++) {
            Future<RecordMetadata> result = producer.send(new ProducerRecord<>(
                    "my-topic", Integer.toString(i), Integer.toString(i)));
            try {
                RecordMetadata recordMetadata = result.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        producer.close();
    }
}
