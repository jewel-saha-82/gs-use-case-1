package org.api.service.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaModel {

    private String symbol;

    private String interval;

    private String currency;

    private String exchange_timezone;

    private String exchange;

    private String type;
}
