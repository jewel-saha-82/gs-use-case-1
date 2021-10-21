package com.stocks.processor.controller;


import com.stocks.processor.model.RootModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateDataController {

    @Value("${kafka.consumerTopic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, RootModel> kafkaTemplate;

    @PostMapping("/publish")
    public String post(@RequestBody RootModel rootModel) {

        kafkaTemplate.send(topic, rootModel);

        return "Published successfully";
    }
}
