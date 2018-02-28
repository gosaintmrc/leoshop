package com.leoshop.beans;

public class OrderBody extends Orders{
	private int commentId;
	private int level;//取值1-5 1※ - 5※
	private String content;
	private double subtotal;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "OrderBody [subtotal=" + subtotal + ",commentId=" + commentId + ", level=" + level + ", content=" + content + ", getOrderId()="
				+ getOrderId() + ", getProductId()=" + getProductId() + ", getProductName()=" + getProductName()
				+ ", getProductPrice()=" + getProductPrice() + ", getSaleCount()=" + getSaleCount() + "]";
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
}
