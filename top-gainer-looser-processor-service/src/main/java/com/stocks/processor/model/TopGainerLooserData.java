package com.stocks.processor.model;

import lombok.Data;


@Data
public class TopGainerLooserData {

    private String symbol;

    private String stock_name;

    private String date;

    private String change_price;

    private String closing_price;

    private String currency;

    private String status;

}
