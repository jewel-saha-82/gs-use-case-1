package com.stocks.processor.model;

import lombok.Data;

import java.time.LocalDate;


@Data
public class TopGainerLooserData {

    private String symbol;

    private String stock_name;

    private LocalDate date;

    private String change_price;

    private String closing_price;

    private String currency;

    private String status;

}
