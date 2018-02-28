package com.leoshop.beans;

public class OrderHead extends Orders implements Comparable<OrderHead>{

	private String username;
	private double totalPrice;
	
	@Override
	public String toString() {
		return "OrderHead [totalPrice=" + totalPrice +",username=" + username +",getOrderNum()=" + getOrderNum() + ", getOrderTime()=" + getOrderTime()
				+ ", getOrderStatus()=" + getOrderStatus() + ", getNote()=" + getNote() + ", getUserId()=" + getUserId()
				+ ", getSendPlace()=" + getSendPlace() + ", getSendMan()=" + getSendMan() + ", getSendPhone()="
				+ getSendPhone() + ", getVisible()=" + getVisible() + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	@Override
	public int compareTo(OrderHead o) {
		// TODO Auto-generated method stub
		return o.getOrderTime().compareTo(this.getOrderTime());
	}
	
}
