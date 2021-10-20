package org.api.service.resource;

import org.api.service.model.KafkaRootModel;
import org.api.service.model.RootModel;
import org.api.service.model.ValuesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TwelveDataController {

    private static Logger logger = LoggerFactory.getLogger(TwelveDataController.class);

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, KafkaRootModel> kafkaTemplate;

    @GetMapping(value = "api.twelvedata.com/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getData() {
        String uri = "https://api.twelvedata.com/time_series?symbol=AAPL&end_date=2021-10-18&interval=1day&outputsize=100&apikey=1f0a1019b83c459d9de4f9bdf5872841";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RootModel> responseEntity = restTemplate.getForEntity(uri, RootModel.class);
        RootModel root = responseEntity.getBody();
        kafkaProducer(root);
        return "Pushed Successfully";
    }

    public void kafkaProducer(RootModel rootModel){
        KafkaRootModel kafkaRootModel = new KafkaRootModel();
        kafkaRootModel.setMeta(rootModel.getMeta());
        kafkaRootModel.setStatus(rootModel.getStatus());
        for(ValuesModel valuesModel : rootModel.getValues()){
            kafkaRootModel.setValue(valuesModel);
            logger.info("output of kafka producer: "+ kafkaRootModel);
            kafkaTemplate.send(topic, kafkaRootModel);
        }
    }


}
