package com.leoshop.beans;

public class Comment {
	private int commentId;
	private int level;//取值1-5 1※ - 5※
	private String content;
	private int orderId;
	public Comment(int level, String content, int orderId) {
		super();
		this.level = level;
		this.content = content;
		this.orderId = orderId;
	}
	public Comment() {
		super();
	}
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
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", level=" + level
				+ ", content=" + content + ", orderId=" + orderId + "]";
	}
	
}
