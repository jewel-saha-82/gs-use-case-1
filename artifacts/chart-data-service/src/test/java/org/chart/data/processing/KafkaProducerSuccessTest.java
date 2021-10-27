package org.chart.data.processing;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.assertj.core.api.BDDAssertions;
import org.chart.data.processing.kafka.producer.KafkaProducerChartData;
import org.chart.data.processing.model.ChartData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaProducerSuccessTest {

	private BlockingQueue<ConsumerRecord<String, String>> records;

	private KafkaMessageListenerContainer<String, String> container;

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaProducerChartData producer;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${kafka.topic.chart-data}")
	private String topic;

	@BeforeAll
	void setUp() {
		DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
				getConsumerProperties());
		// Contains runtime properties for a listener container.
		ContainerProperties containerProperties = new ContainerProperties(topic);
		container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
		records = new LinkedBlockingQueue<>();
		container.setupMessageListener((MessageListener<String, String>) records::add);
		container.start();
		ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
	}

	private Map<String, Object> getConsumerProperties() {
		return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
				ConsumerConfig.GROUP_ID_CONFIG, "consumer", ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
				ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10", ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	}

	@AfterAll
	void tearDown() {
		container.stop();
	}

	@Test
	void testWriteToKafka_Success() throws InterruptedException, JsonProcessingException, ExecutionException {
		// Create a Model and write to Kafka
		String symbol = "AAPL";
		ChartData chartData = new ChartData(symbol, "Apple Inc.", "2021-10-27", new BigDecimal("120.22"), "USD");

		producer.sendMessage(objectMapper.writeValueAsString(chartData));

		// Read the message with a test consumer from Kafka and assert
		// its properties
		ConsumerRecord<String, String> message = records.poll(500, TimeUnit.MILLISECONDS);

		BDDAssertions.then(message.value()).isNotNull();
		ChartData result = objectMapper.readValue(message.value(), ChartData.class);
		BDDAssertions.then(result).isNotNull();
		BDDAssertions.then(result.getSymbol()).isEqualTo(symbol);
	}
}
