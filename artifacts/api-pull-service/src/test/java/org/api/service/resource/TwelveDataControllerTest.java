package org.api.service.resource;

import org.api.service.service.ApiPullServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TwelveDataController.class)
class TwelveDataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ApiPullServiceImpl apiPullService;


    @Test
    void getDataTest() throws Exception {

        Mockito.when(apiPullService.getPullService()).thenReturn("Succeed");

        mockMvc.perform(MockMvcRequestBuilders.get("/api.twelvedata.com/stocks")).andExpect(status().isOk());

    }
}