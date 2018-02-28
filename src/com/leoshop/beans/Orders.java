package com.leoshop.beans;

import java.util.Date;

public class Orders {
	private String orderNum;
	private Date orderTime;
	private int orderStatus;
	private String note;
	private int userId;
	private String sendPlace;
	private String sendMan;
	private String sendPhone;
	private int visible;
	private int orderId;
	private int productId;
	private String productName;
	private double productPrice;
	private int saleCount;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSendPlace() {
		return sendPlace;
	}
	public void setSendPlace(String sendPlace) {
		this.sendPlace = sendPlace;
	}
	public String getSendMan() {
		return sendMan;
	}
	public void setSendMan(String sendMan) {
		this.sendMan = sendMan;
	}
	public String getSendPhone() {
		return sendPhone;
	}
	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
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
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	public int getVisible() {
		return visible;
	}
	public void setVisible(int visible) {
		this.visible = visible;
	}
	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderNum=" + orderNum + ", orderTime=" + orderTime + ", orderStatus="
				+ orderStatus + ", note=" + note + ", userId=" + userId + ", sendPlace=" + sendPlace + ", sendMan="
				+ sendMan + ", sendPhone=" + sendPhone + ", productId=" + productId + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", saleCount=" + saleCount + ", visible=" + visible + "]";
	}
	public Orders(int orderId, String orderNum, Date orderTime, int orderStatus, String note, int userId,
			String sendPlace, String sendMan, String sendPhone, int productId, String productName, double productPrice,
			int saleCount, int visible) {
		super();
		this.orderId = orderId;
		this.orderNum = orderNum;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.note = note;
		this.userId = userId;
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.saleCount = saleCount;
		this.visible = visible;
	}
	public Orders() {
		super();
	}
	public Orders(String orderNum, Date orderTime, int orderStatus, String note, int userId, String sendPlace,
			String sendMan, String sendPhone, int productId, String productName, double productPrice, int saleCount) {
		super();
		this.orderNum = orderNum;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.note = note;
		this.userId = userId;
		this.sendPlace = sendPlace;
		this.sendMan = sendMan;
		this.sendPhone = sendPhone;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.saleCount = saleCount;
	}
}
