package org.data.storage.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.data.storage.model.ChartData;
import org.data.storage.model.TopGainerLooser;
import org.data.storage.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Autowired
	AppProperties appProperties;

    @Bean
    public ConsumerFactory<String, TopGainerLooser> rawDataConsumerFactory() {
    	JsonDeserializer<TopGainerLooser> deserializer = new JsonDeserializer<>(TopGainerLooser.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getBootstrapServer());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, appProperties.getGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(TopGainerLooser.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TopGainerLooser> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TopGainerLooser> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rawDataConsumerFactory());
        return factory;
    }
    
    @Bean
    public ConsumerFactory<String, ChartData> rawChartDataConsumerFactory() {
    	JsonDeserializer<ChartData> deserializer = new JsonDeserializer<>(ChartData.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appProperties.getBootstrapServer());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, appProperties.getGroupId());
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, appProperties.getBATCH_SIZE());

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChartData> kafkaListenerContainerFactoryChartData() {
        ConcurrentKafkaListenerContainerFactory<String, ChartData> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rawChartDataConsumerFactory());
        factory.setBatchListener(true);
        return factory;
    }

}
