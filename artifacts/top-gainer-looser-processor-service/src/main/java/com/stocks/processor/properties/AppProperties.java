package com.stocks.processor.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties {

    @Value("${kafka.producerTopic}")
    private String topic;

    @Value("${kafka.bootstrap-server}")
    private String bootstrapServer;

    @Value("${kafka.groupId}")
    private String groupId;


}
