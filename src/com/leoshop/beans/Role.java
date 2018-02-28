package com.leoshop.beans;

public class Role {
	private int roleId;
	private String rolename;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Role(int roleId, String rolename) {
		super();
		this.roleId = roleId;
		this.rolename = rolename;
	}
	public Role() {
		super();
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", rolename=" + rolename + "]";
	}
}
