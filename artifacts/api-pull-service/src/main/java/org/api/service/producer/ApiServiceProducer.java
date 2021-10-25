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
        for (ValuesModel valuesModel : rootModel.getValues()) {
            kafkaRootModel.setValue(valuesModel);
            logger.info(kafkaRootModel.toString());
            kafkaTemplate.send(producerProperties.TOPIC, kafkaRootModel);
        }
        return "Produced Successfully";
    }
}
