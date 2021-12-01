package com.stocks.processor.producer;

import com.stocks.processor.model.TopGainerLooserData;
import com.stocks.processor.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopGainerLooserProducer {


    @Autowired
    AppProperties properties;


    @Autowired
    private KafkaTemplate<Object, TopGainerLooserData> kafkaTemplate;

    public String produceTopGainerLooser(TopGainerLooserData data) {
        kafkaTemplate.send(properties.getTopic(), data);
        return "Published successfully";
    }

}
