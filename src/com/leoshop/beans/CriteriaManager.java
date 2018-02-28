package com.leoshop.beans;

public class CriteriaManager {
	private int managerId;
	private String managerName;
	private String managerPassword;
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName == null?"%%":("%" + managerName +"%");
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", managerName=" + managerName + ", managerPassword="
				+ managerPassword + "]";
	}
	public CriteriaManager(int managerId, String managerName, String managerPassword) {
		super();
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerPassword = managerPassword;
	}
	public CriteriaManager(String managerName, String managerPassword) {
		super();
		this.managerName = managerName;
		this.managerPassword = managerPassword;
	}
	public CriteriaManager() {
		super();
	}
}
