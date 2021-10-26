/*
package org.api.service.resource;

import org.api.service.model.MetaModel;
import org.api.service.model.RootModel;
import org.api.service.model.ValuesModel;
import org.api.service.utils.RestTemplateUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(TwelveDataController.class)
class TweleveDataRestResourceTest {

    @Autowired
     RestTemplate restTemplate;

    @InjectMocks
     TweleveDataRestResource tweleveDataRestResource;

    @MockBean
    RestTemplateUtils restTemplateUtils;

    private MockRestServiceServer mockServer;


    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


  RootModel rootModel = new RootModel(new MetaModel("AAPL","1min","USD","America/New_York","NASDAQ","Common Stock"),
                Arrays.asList(new ValuesModel("2021-10-15 15:59:00","144.87000","44.89500","44.73000","144.84000","942966")),"OK");


  @Test
    public void sendRestTemplateTest(){

       Mockito.when(restTemplate.getForEntity(RestTemplateUtils.URI, RootModel.class)).thenReturn(new ResponseEntity(rootModel,HttpStatus.OK));
       restTemplateUtils.restTemplate();


  }




}*/
