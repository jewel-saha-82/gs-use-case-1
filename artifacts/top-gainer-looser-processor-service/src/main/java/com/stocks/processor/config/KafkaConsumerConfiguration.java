package com.stocks.processor.config;

import com.stocks.processor.model.RootModel;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value("${kafka.bootstrap-server}")
    private String bootstrapServer;

    @Value("${kafka.groupId}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, RootModel> rawDataConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(RootModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RootModel> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RootModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(rawDataConsumerFactory());
        return factory;
    }

}
