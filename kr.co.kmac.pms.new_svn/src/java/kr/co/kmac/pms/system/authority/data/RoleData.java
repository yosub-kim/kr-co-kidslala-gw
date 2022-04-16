/*
 * $Id: RoleData.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authority.data;

public class RoleData {
	private String roleNum;
	private String roleName;
	private String roleUseInfo;
	private int roleSeq;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleNum() {
		return roleNum;
	}
	public void setRoleNum(String roleNum) {
		this.roleNum = roleNum;
	}
	public String getRoleUseInfo() {
		return roleUseInfo;
	}
	public void setRoleUseInfo(String roleUseInfo) {
		this.roleUseInfo = roleUseInfo;
	}
	public int getRoleSeq() {
		return roleSeq;
	}
	public void setRoleSeq(int roleSeq) {
		this.roleSeq = roleSeq;
	}
	
	
}
