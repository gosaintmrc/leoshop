package com.leoshop.beans;

public class ManagerPermission {
	private int managerId;
	private String managerName;
	private int roleId;
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public ManagerPermission(int managerId, String managerName, int roleId) {
		super();
		this.managerId = managerId;
		this.managerName = managerName;
		this.roleId = roleId;
	}
	public ManagerPermission() {
		super();
	}
	@Override
	public String toString() {
		return "ManagerPermission [managerId=" + managerId + ", managerName="
				+ managerName + ", roleId=" + roleId + "]";
	}
}
