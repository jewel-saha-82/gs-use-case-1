package org.data.storage.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TopGainerLooser")
public class TopGainerLooser {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="symbol")
	private String symbol;
	
	@Column(name="stock_name")
	private String stock_name;
	
	@Column(name="date")
	private String date;
		
	@Column(name="change_price")
	private float change_price;
	
	@Column(name="closing_price")
	private float closing_price;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="status")
	private String status;
			
	public TopGainerLooser() {
	}
	
	public TopGainerLooser(int id, String symbol, String stock_name, String date,
			float change_price, float closing_price, String currency, String status) {
		this.id = id;
		this.symbol = symbol;
		this.stock_name = stock_name;
		this.date = date;
		this.change_price = change_price;
		this.closing_price = closing_price;
		this.currency = currency;
		this.status = status;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getChange_price() {
		return change_price;
	}

	public void setChange_price(float change_price) {
		this.change_price = change_price;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TopGainerLooser [id=" + id + ", symbol=" + symbol + ", stock_name=" + stock_name + ", date=" + date
				+ ", change_price=" + change_price + ", closing_price="
				+ closing_price + ", currency=" + currency + ", status=" + status + "]";
	}
	
	
}
