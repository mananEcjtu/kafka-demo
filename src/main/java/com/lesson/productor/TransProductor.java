package com.lesson.productor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;

public class TransProductor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.139.5:9093");
        props.put("transactional.id", "my-transactional-id" + UUID.randomUUID());
        Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 200; i < 300; i++) {
                producer.send(new ProducerRecord<>("my-topic", Integer.toString(1), "tx-->" + i));
                producer.flush();
                if (i == 280) {
                    throw new KafkaException("manual exeception");
                }
            }
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
            //终止事务
            producer.abortTransaction();
            e.printStackTrace();
        }
        producer.close();
    }
}
