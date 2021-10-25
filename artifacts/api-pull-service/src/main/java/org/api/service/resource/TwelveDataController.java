package org.api.service.resource;

import org.api.service.service.ApiPullServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwelveDataController {

    @Autowired
    ApiPullServiceImpl apiPullService;

    @GetMapping(value = "api.twelvedata.com/stocks", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getData() {

        String pullService = apiPullService.getPullService();

        return pullService;
    }


}

