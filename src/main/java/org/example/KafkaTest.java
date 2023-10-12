package org.example;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "testTopic")
public class KafkaTest {

    private static final String TEST_TOPIC = "testTopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    private Consumer<String, String> consumer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @BeforeEach
    public void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker);
        consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TEST_TOPIC);
    }

    @AfterEach
    public void tearDown() {
        consumer.close();
    }

    @Test
    public void testSendMessage() {
        String message = "Hello, Kafka!";
        kafkaTemplate.send(TEST_TOPIC, message);

        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);
        for (ConsumerRecord<String, String> record : records) {
            assertEquals(message, record.value());
        }
    }
}
//implementation 'org.springframework.kafka:spring-kafka'
