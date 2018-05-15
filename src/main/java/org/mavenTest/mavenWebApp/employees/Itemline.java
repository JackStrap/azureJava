package org.mavenTest.mavenWebApp.employees;

import java.math.BigDecimal;

public class Itemline {

	private int id;
	private String productName;
	private BigDecimal price;
	private int quantity;

	public Itemline() {
	}

	public Itemline(int id, String productName, BigDecimal price, int quantity) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	public Itemline(int id, String productName, double price, int quantity) {
		this.id = id;
		this.productName = productName;
		this.price = new BigDecimal(price);
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
