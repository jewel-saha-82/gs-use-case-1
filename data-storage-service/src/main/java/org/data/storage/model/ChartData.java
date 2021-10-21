package org.data.storage.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="CHARTDATA")
public class ChartData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="stock_name")
	private String stock_name;
	
	@Column(name="date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@Column(name="closing_price")
	private float closing_price;
	
	@Column(name="currency")
	private String currency;
		
	public ChartData() {}

	public ChartData(int id, String symbol, String stock_name, LocalDate date, float closing_price, String currency) {
		this.id = id;
		this.symbol = symbol;
		this.stock_name = stock_name;
		this.date = date;
		this.closing_price = closing_price;
		this.currency = currency;
	}

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

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public float getClosing_price() {
		return closing_price;
	}

	public void setClosing_price(float closing_price) {
		this.closing_price = closing_price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "ChartData [id=" + id + ", symbol=" + symbol + ", stock_name=" + stock_name + ", date=" + date
				+ ", closing_price=" + closing_price + ", currency=" + currency + ", created_by="
				+ "]";
	}
	
	
	
}

