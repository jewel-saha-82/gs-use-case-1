package org.data.storage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Date date;
	
	@Column(name="closing_price")
	private float closing_price;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="created_by")
	private String created_by;
	
	@Column(name="created_date")
	private Date created_date;
	
	@Column(name="updated_by")
	private String updated_by;
	
	@Column(name="updated_date")
	private Date updated_date;
	
	public ChartData() {}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	@Override
	public String toString() {
		return "ChartData [id=" + id + ", symbol=" + symbol + ", stock_name=" + stock_name + ", date=" + date
				+ ", closing_price=" + closing_price + ", currency=" + currency + ", created_by=" + created_by
				+ ", created_date=" + created_date + ", updated_by=" + updated_by + ", updated_date=" + updated_date
				+ "]";
	}
	
	
	
}

