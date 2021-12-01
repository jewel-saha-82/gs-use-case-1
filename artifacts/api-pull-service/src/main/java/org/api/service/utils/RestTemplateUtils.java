package org.api.service.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateUtils {

    //public static final String URI = "https://api.twelvedata.com/time_series?symbol=AAPL&end_date=2021-10-18&interval=1day&outputsize=100&apikey=1f0a1019b83c459d9de4f9bdf5872841";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
