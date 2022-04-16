/*
 * $Id: RoleAssign.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.Date;

/**
 * 역할 할당 모델
 * @author 최인호
 * @version $Id: RoleAssign.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class RoleAssign implements IRoleAssign {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 476361600998221332L;

	private String roleId;
	private String roleName;
	private String principalId;
	private String principalName;
	private String assignerId;
	private String assignerName;
	private Date assignDate;
	private int inheritance;

	public RoleAssign(String principalId, String roleId) {
		setPrincipalId(principalId);
		setRoleId(roleId);
	}
	/**
	 * @return Returns the assignDate.
	 */
	public Date getAssignDate() {
		return this.assignDate;
	}
	/**
	 * @param assignDate The assignDate to set.
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	/**
	 * @return Returns the assignerId.
	 */
	public String getAssignerId() {
		return this.assignerId;
	}
	/**
	 * @param assignerId The assignerId to set.
	 */
	public void setAssignerId(String assignerId) {
		this.assignerId = assignerId;
	}
	/**
	 * @return Returns the assignerName.
	 */
	public String getAssignerName() {
		return this.assignerName;
	}
	/**
	 * @param assignerName The assignerName to set.
	 */
	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}
	/**
	 * @return Returns the inheritance.
	 */
	public int getInheritance() {
		return this.inheritance;
	}
	/**
	 * @param inheritance The inheritance to set.
	 */
	public void setInheritance(int inheritance) {
		this.inheritance = inheritance;
	}
	/**
	 * @return Returns the principalId.
	 */
	public String getPrincipalId() {
		return this.principalId;
	}
	/**
	 * @param principalId The principalId to set.
	 */
	public void setPrincipalId(String principalId) {
		if (principalId == null) {
			throw new IllegalArgumentException("PrincipalId should not be null");
		}
		this.principalId = principalId;
	}

	/**
	 * @return Returns the principanName.
	 */
	public String getPrincipalName() {
		return this.principalName;
	}
	/**
	 * @param principanName The principanName to set.
	 */
	public void setPrincipalName(String principanName) {
		this.principalName = principanName;
	}
	/**
	 * @return Returns the roleId.
	 */
	public String getRoleId() {
		return this.roleId;
	}
	/**
	 * @param roleId The roleId to set.
	 */
	public void setRoleId(String roleId) {
		if (roleId == null) {
			throw new IllegalArgumentException("RoleId should not be null");
		}
		this.roleId = roleId;
	}
	/**
	 * @return Returns the roleName.
	 */
	public String getRoleName() {
		return this.roleName;
	}
	/**
	 * @param roleName The roleName to set.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof RoleAssign)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		RoleAssign other = (RoleAssign) obj;
		return other.getRoleId().equals(this.roleId) && other.getPrincipalId().equals(this.principalId);
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.roleId.hashCode() * 13 + this.principalId.hashCode() * 31;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("roleId=" + this.roleId);
		sb.append(",roleName=" + this.roleName);
		sb.append(",principalId=" + this.principalId);
		sb.append(",principalName=" + this.principalName);
		sb.append(",assignerId=" + this.assignerId);
		sb.append(",assignerName=" + this.assignerName);
		sb.append(",assignDate=" + this.assignDate);
		sb.append(",inheritance=" + this.inheritance);
		return sb.toString();
	}

	public static final IRoleAssign createUserRoleAssign(String id, String name) {
		return new RoleAssign(id, name);
	}
}
