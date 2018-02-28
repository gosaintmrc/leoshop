package com.leoshop.beans;

public class Menu implements Comparable<Menu>{
	private int menuId;
	private String menuName;
	private int parentMenu;
	private String menuUrl;
	private int menuStatus;
	private String note;
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(int parentMenu) {
		this.parentMenu = parentMenu;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public int getMenuStatus() {
		return menuStatus;
	}
	public void setMenuStatus(int menuStatus) {
		this.menuStatus = menuStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Menu(int menuId, String menuName, int parentMenu, String menuUrl,
			int menuStatus, String note) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.parentMenu = parentMenu;
		this.menuUrl = menuUrl;
		this.menuStatus = menuStatus;
		this.note = note;
	}
	public Menu() {
		super();
	}
	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", menuName=" + menuName
				+ ", parentMenu=" + parentMenu + ", menuUrl=" + menuUrl
				+ ", menuStatus=" + menuStatus + ", note=" + note + "]";
	}
	@Override
	public int compareTo(Menu o) {
		return this.menuId-o.menuId;
	}
}
