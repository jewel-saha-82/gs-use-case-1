package org.data.storage.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.data.storage.model.TopGainerLooser;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServer;

//    @Value("${kafka.groupId}")
    private String groupId = "raw-data-consumer";

    @Bean
    public ConsumerFactory<String, TopGainerLooser> rawDataConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(TopGainerLooser.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TopGainerLooser> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TopGainerLooser> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rawDataConsumerFactory());
        return factory;
    }

}
