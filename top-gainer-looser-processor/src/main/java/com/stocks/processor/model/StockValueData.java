package com.stocks.processor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockValueData {


    private String datetime;

    private String open;

    private String high;

    private String low;

    private String close;

    private String volume;

    public String profitStatus;

    public StockValueData(ValuesModel valuesModel) {
        this.datetime = valuesModel.getDatetime();
        this.open = valuesModel.getOpen();
        this.high = valuesModel.getHigh();
        this.low = valuesModel.getLow();
        this.close = valuesModel.getClose();
        this.volume = valuesModel.getVolume();
    }
}
