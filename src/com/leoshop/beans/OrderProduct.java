package com.leoshop.beans;

public class OrderProduct {
	private int productId;
	private String productName;
	private double productPrice;
	private String productImagePath;
	private int saleCount;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductImagePath() {
		return productImagePath;
	}
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	public OrderProduct() {
		super();
	}
	public OrderProduct(int productId, String productName, double productPrice,
			String productImagePath, int saleCount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productImagePath = productImagePath;
		this.saleCount = saleCount;
	}
	@Override
	public String toString() {
		return "OrderProduct [productId=" + productId + ", productName="
				+ productName + ", productPrice=" + productPrice
				+ ", productImagePath=" + productImagePath + ", saleCount="
				+ saleCount + "]";
	}
}
