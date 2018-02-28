package com.leoshop.beans;

public class CriteriaUser {
	private int userId;
	private String username;
	private String password;
	private String truename;
	private String phone;
	private String address;
	private int accountId;
	public CriteriaUser(int userId, String username, String password, String truename,
			String phone, String address, int accountId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.truename = truename;
		this.phone = phone;
		this.address = address;
		this.accountId = accountId;
	}
	public CriteriaUser() {
		super();
	}
	public CriteriaUser(String username, String password) {
		super();
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
		return username == null? "%%":"%"+username+"%";
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
		return truename == null? "%%":"%"+truename+"%";
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getPhone() {
		return phone == null? "%%":"%"+phone+"%";
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address == null? "%%":"%"+address+"%";
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username
				+ ", password=" + password + ", truename=" + truename
				+ ", phone=" + phone + ", address=" + address + ", accountId="
				+ accountId + "]";
	}
	

}
