package org.data.storage.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="CHARTDATA")
public class ChartData {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="stockName")
	private String stockName;
	
	@Column(name="date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@Column(name="closingPrice")
	private float closingPrice;
	
	@Column(name="currency")
	private String currency;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public float getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(float closingPrice) {
		this.closingPrice = closingPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "ChartData [id=" + id + ", symbol=" + symbol + ", stockName=" + stockName + ", date=" + date
				+ ", closingPrice=" + closingPrice + ", currency=" + currency + "]";
	}

	public ChartData(int id, String symbol, String stockName, LocalDate date, float closingPrice, String currency) {
		this.id = id;
		this.symbol = symbol;
		this.stockName = stockName;
		this.date = date;
		this.closingPrice = closingPrice;
		this.currency = currency;
	}

	public ChartData() {
	}
		
	
	
	
	
}

