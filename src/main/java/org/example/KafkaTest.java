package org.example;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaTest {

    private static final String TEST_TOPIC = "testTopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaEmbedded embeddedKafka;

    private Consumer<String, String> consumer;

    private BlockingQueue<ConsumerRecord<String, String>> records;

    @BeforeEach
    public void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafka.getBrokersAsString());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, TEST_TOPIC);
        records = new LinkedBlockingQueue<>();
        consumer.subscribe(Collections.singletonList(TEST_TOPIC));
    }

    @AfterEach
    public void tearDown() {
        consumer.close();
    }

    @Test
    public void testSendMessage() throws InterruptedException {
        String message = "Hello, Kafka!";
        kafkaTemplate.send(TEST_TOPIC, message);

        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(5));
        for (ConsumerRecord<String, String> record : consumerRecords) {
            records.add(record);
        }

        ConsumerRecord<String, String> record = records.poll(10, TimeUnit.SECONDS);
        assertEquals(message, record.value());
    }
}



//implementation 'org.springframework.kafka:spring-kafka'
