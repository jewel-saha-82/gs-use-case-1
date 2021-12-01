package org.api.service.service;

import org.api.service.model.MetaModel;
import org.api.service.model.RootModel;
import org.api.service.model.ValuesModel;
import org.api.service.producer.ApiServiceProducer;
import org.api.service.resource.TweleveDataRestResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ApiPullServiceImpl.class)
class ApiPullServiceImplTest {

    @MockBean
    TweleveDataRestResource tweleveDataRestResource;

    @MockBean
    ApiServiceProducer apiServiceProducer;

    @Autowired
    ApiPullServiceImpl apiPullService;

    RootModel rootModel;


    @Before
    public void set(){
         rootModel = new RootModel(new MetaModel("AAPL","1min","USD","America/New_York","NASDAQ","Common Stock"),
                Arrays.asList(new ValuesModel("2021-10-15 15:59:00","144.87000","44.89500","44.73000","144.84000","942966")),"OK");
    }

    @Test
    void getPullServiceTest() {

        Mockito.when(tweleveDataRestResource.sendRestTemplate()).thenReturn(rootModel);
        Mockito.when(apiServiceProducer.produceApiService(any())).thenReturn("Success");
        String pullService = apiPullService.getPullService();
        Assert.assertEquals("Success",pullService);

    }
}