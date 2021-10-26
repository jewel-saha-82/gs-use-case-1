package org.api.service.producer;

import org.api.service.model.KafkaRootModel;
import org.api.service.model.RootModel;
import org.api.service.model.ValuesModel;
import org.api.service.properties.ProducerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Component
public class ApiServiceProducer {

    private static Logger logger = LoggerFactory.getLogger(ApiServiceProducer.class);

    @Autowired
    ProducerProperties producerProperties;

    @Autowired
    KafkaTemplate kafkaTemplate;

    public String produceApiService(RootModel rootModel) {
        KafkaRootModel kafkaRootModel = new KafkaRootModel();
        kafkaRootModel.setMeta(rootModel.getMeta());
        kafkaRootModel.setStatus(rootModel.getStatus());
        extracted(rootModel, kafkaRootModel);
        return "Produced Successfully";
    }

    private void extracted(RootModel rootModel, KafkaRootModel kafkaRootModel) {
        rootModel.getValues().forEach(valuesModel -> {
            kafkaRootModel.setValue(valuesModel);
            logger.info(kafkaRootModel.toString());
            kafkaTemplate.send(producerProperties.TOPIC, kafkaRootModel);
        });
    }
}
