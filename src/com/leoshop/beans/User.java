package com.leoshop.beans;

public class User {
	private int userId;
	private String username;
	private String password;
	private String truename;
	private String phone;
	private String address;
	private int userStatus;
	
	public User(int userId, String username, String password, String truename,
			String phone, String address, int userStatus) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
		this.userStatus = userStatus;
	}
	public User(int userId, String username, String password, String truename,
			String phone, String address) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
	}
	
	public User(int userId, String password, String truename, String phone,
			String address, int userStatus) {
		super();
		this.userId = userId;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
		this.userStatus = userStatus;
	}
	public User() {
		super();
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User(int userId, String username, String password) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", truename=" + truename
				+ ", phone=" + phone + ", address=" + address + ", userStatus=" + userStatus + "]";
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

}
