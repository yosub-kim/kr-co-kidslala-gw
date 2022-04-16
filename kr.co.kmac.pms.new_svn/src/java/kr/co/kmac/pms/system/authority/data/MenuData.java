/*
 * $Id: MenuData.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.data;

public class MenuData {
	private String menuNum;
	private String menuName;
	private String menuPath;
	private String menuType;
	private String menuUseInfo;
	private String menuSort;
	
	
	public String getMenuSort() {
		return this.menuSort;
	}
	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuPath() {
		return menuPath;
	}
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getMenuUseInfo() {
		return menuUseInfo;
	}
	public void setMenuUseInfo(String menuUseInfo) {
		this.menuUseInfo = menuUseInfo;
	}
	public String getMenuNum() {
		return menuNum;
	}
	public void setMenuNum(String menuNum) {
		this.menuNum = menuNum;
	}
	
	
}
