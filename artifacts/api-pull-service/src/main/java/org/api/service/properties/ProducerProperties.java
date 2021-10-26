package org.api.service.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProducerProperties {

    @Value("${kafka.topic}")
    public String TOPIC;

}
