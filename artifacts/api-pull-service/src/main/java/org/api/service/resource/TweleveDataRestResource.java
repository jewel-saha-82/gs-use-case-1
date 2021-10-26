package org.api.service.resource;

import org.api.service.model.RootModel;
import org.api.service.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TweleveDataRestResource {

    public static final String URI = "https://api.twelvedata.com/time_series?symbol=AAPL&end_date=2021-10-18&interval=1day&outputsize=100&apikey=1f0a1019b83c459d9de4f9bdf5872841";

    @Autowired
    RestTemplate restTemplate;

    public RootModel sendRestTemplate() {

        ResponseEntity<RootModel> responseEntity = restTemplate.getForEntity(URI, RootModel.class);

        return  responseEntity.getBody();
    }


}
