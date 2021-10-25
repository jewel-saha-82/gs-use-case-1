package org.chart.data.processing.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.chart.data.processing.model.RootModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfiguration {

	@Value("${kafka.bootstrap-server}")
	private String bootstrapServer;

	@Value("${kafka.groupId}")
	private String groupId;

	@Value("${kafka.consumer.batch.size}")
	private int batchSize;

	@Value("${auto.offset.report.config}")
	private String offsetConfig;

	@Bean
	public ConsumerFactory<String, RootModel> rawDataConsumerFactory() {

		JsonDeserializer<RootModel> deserializer = new JsonDeserializer<>(RootModel.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);

		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetConfig);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, batchSize);

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, RootModel> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, RootModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(rawDataConsumerFactory());
		factory.setBatchListener(true);
		return factory;
	}

}
