package com.stocks.processor.producer;

import com.stocks.processor.model.TopGainerLooserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopGainerLooserProducer {

    @Value("${kafka.producerTopic}")
    private String topic;

    @Autowired
    private KafkaTemplate<Object, TopGainerLooserData> kafkaTemplate;

    public String produceTopGainerLooser(TopGainerLooserData data) {
        kafkaTemplate.send(topic, data);
        return "Published successfully";
    }

}
