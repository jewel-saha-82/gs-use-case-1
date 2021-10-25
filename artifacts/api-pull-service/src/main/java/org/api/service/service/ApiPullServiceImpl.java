package org.api.service.service;

import org.api.service.model.RootModel;
import org.api.service.producer.ApiServiceProducer;
import org.api.service.resource.TweleveDataRestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiPullServiceImpl implements ApiPullService{

    @Autowired
    TweleveDataRestResource tweleveDataRestResource;

    @Autowired
    ApiServiceProducer apiServiceProducer;

    public String getPullService() {

        RootModel rootModel = tweleveDataRestResource.sendRestTemplate();
        return apiServiceProducer.produceApiService(rootModel);

    }
}
