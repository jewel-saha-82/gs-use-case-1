package com.stocks.processor.model;

public class MetaModel {

    private String symbol;

    private String interval;

    private String currency;

    private String exchange_timezone;

    private String exchange;

    private String type;

    public MetaModel() {
    }

    public MetaModel(String symbol, String interval, String currency, String exchange_timezone, String exchange, String type) {
        this.symbol = symbol;
        this.interval = interval;
        this.currency = currency;
        this.exchange_timezone = exchange_timezone;
        this.exchange = exchange;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange_timezone() {
        return exchange_timezone;
    }

    public void setExchange_timezone(String exchange_timezone) {
        this.exchange_timezone = exchange_timezone;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MetaDao{" +
                "symbol='" + symbol + '\'' +
                ", interval='" + interval + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange_timezone='" + exchange_timezone + '\'' +
                ", exchange='" + exchange + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
