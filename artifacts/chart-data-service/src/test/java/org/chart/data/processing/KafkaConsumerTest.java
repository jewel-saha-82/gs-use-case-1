package org.chart.data.processing;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.BDDAssertions;
import org.chart.data.processing.kafka.consumer.KafkaConsumerScriptRawData;
import org.chart.data.processing.kafka.producer.KafkaProducerChartData;
import org.chart.data.processing.model.MetaModel;
import org.chart.data.processing.model.RootModel;
import org.chart.data.processing.model.ValuesModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaConsumerTest {

	private Producer<String, String> producer;

	private RootModel rm;

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private ObjectMapper objectMapper;

	@SpyBean
	private KafkaConsumerScriptRawData consumer;

	@Captor
	ArgumentCaptor<List<ConsumerRecord<String, String>>> argumentCaptor;

	@MockBean
	private KafkaProducerChartData kafkaProducerChartData;

	@BeforeAll
	void setUp() {
		Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
		producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer())
				.createProducer();
		// Models
		MetaModel mm = new MetaModel("AAPL", "1day", "USD", "America/New_York", "NASDAQ", "Common Stock");
		ValuesModel vm = new ValuesModel("2021-10-11", "142.27000", "144.81000", "141.81000", "142.81000", "63012662");
		rm = new RootModel(mm, vm, "ok");
	}

	@AfterAll
	void shutdown() {
		producer.close();
	}

	@Test
	void testLogKafkaMessages() throws JsonProcessingException {

		// Write a message to Kafka using a test producer
		String jsonMsg = objectMapper.writeValueAsString(rm);
		producer.send(new ProducerRecord<>(consumer.getTopic(), jsonMsg));
		producer.flush();

		// Read the message and assert its properties
		verify(consumer, timeout(5000).times(1)).consumeMessage(argumentCaptor.capture());

		RootModel rm1 = objectMapper.readValue(argumentCaptor.getValue().get(0).value(), RootModel.class);
		BDDAssertions.then(rm1).isNotNull();
		BDDAssertions.then(rm1.getMeta().getSymbol()).isEqualTo("AAPL");
	}

	@Test
	void sendMsgToPrdcr_exception_test() throws JsonProcessingException, InterruptedException, ExecutionException {

		// Write a message to Kafka using a test producer
		String jsonMsg = objectMapper.writeValueAsString(rm);
		producer.send(new ProducerRecord<>(consumer.getTopic(), jsonMsg));
		producer.flush();

		//Exception test
		doThrow(new InterruptedException()).when(kafkaProducerChartData).sendMessage(Mockito.anyString());

		// Read the message and assert its properties
		verify(consumer, timeout(5000).times(1)).consumeMessage(argumentCaptor.capture());

		RootModel rm1 = objectMapper.readValue(argumentCaptor.getValue().get(0).value(), RootModel.class);
		BDDAssertions.then(rm1).isNotNull();
		BDDAssertions.then(rm1.getMeta().getSymbol()).isEqualTo("AAPL");
	}
}
