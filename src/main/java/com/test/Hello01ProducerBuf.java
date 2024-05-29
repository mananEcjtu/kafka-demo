package com.test;

import com.ieslab.protobuf.GenderProto;
import com.ieslab.protobuf.UserProto;
import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class Hello01ProducerBuf extends Thread {

    private static final String serial = ByteArraySerializer.class.getName();

    private final Producer<byte[], byte[]> producer;

    public Hello01ProducerBuf(String name) {
        super.setName(name);
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.139.5:9093,192.168.139.5:9094,192.168.139.5:9095");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serial);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serial);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1000);

        // 幂等性
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 事务id
        // properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "user-log-tx" + UUID.randomUUID());
        producer = new KafkaProducer<>(properties);
    }

    @SneakyThrows
    @Override
    public void run() {
        int count = 0;
        System.out.println("====Hello01Producer开始发送消息====");
        while (count < 20) {
            UserProto.User user = UserProto.User.newBuilder().
                    setUserName("ma").
                    setAge(++count).setGender(GenderProto.GenderType.Male).build();
            producer.send(new ProducerRecord<>("user-log", "proto".getBytes(), user.toByteArray()));
            System.out.println("Producer run--" + "--" + user);
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) {
        //createTopic();
        Hello01ProducerBuf producer = new Hello01ProducerBuf("上海");
        producer.start();
    }

    /**
     * 创建主题
     */
    public static void createTopic() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.139.5:9093,192.168.139.5:9094,192.168.139.5:9095");
        AdminClient adminClient = AdminClient.create(properties);
        ArrayList<NewTopic> topics = new ArrayList<>();
        NewTopic topic = new NewTopic("user-log", 3, (short) 2);
        topics.add(topic);
        adminClient.createTopics(topics);
    }

}
