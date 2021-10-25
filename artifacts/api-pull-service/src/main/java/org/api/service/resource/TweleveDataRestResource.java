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

    @Autowired
    RestTemplate restTemplate;

    public RootModel sendRestTemplate() {

        ResponseEntity<RootModel> responseEntity = restTemplate.getForEntity(RestTemplateUtils.URI, RootModel.class);

        return  responseEntity.getBody();
    }


}
