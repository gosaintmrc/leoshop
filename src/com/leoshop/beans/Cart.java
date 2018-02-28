package com.leoshop.beans;

public class Cart {
	private int cartId;
	private int productId;
	private int saleCount;
	private int userId;
	@Override
	public String toString() {
		return "Cart [cardId=" + cartId + ", productId=" + productId
				+ ", saleCount=" + saleCount + ", userId=" + userId + "]";
	}
	public Cart( int productId, int saleCount, int userId) {
		super();
		this.productId = productId;
		this.saleCount = saleCount;
		this.userId = userId;
	}
	public Cart() {
		super();
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cardId) {
		this.cartId = cardId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
