package org.api.service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaRootModel {

    private MetaModel meta;

    private ValuesModel value;

    private String status;

}
