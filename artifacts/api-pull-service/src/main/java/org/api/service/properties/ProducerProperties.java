package org.api.service.properties;


import org.springframework.stereotype.Component;

@Component
public class ProducerProperties {

    //@Value("${kafka.topic}")
    public static final String TOPIC = "script-raw-data";

}
