/*
 * $Id: AbstractPrincipal.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 역할을 부여받을 있는 Entity
 * @author 최인호
 * @version $Id: AbstractPrincipal.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public abstract class AbstractPrincipal extends AbstractEntity implements IPrincipal {
	private static final long serialVersionUID = -5609811265977797995L;
	private List roleAssignList;
	private List roleList;

	public AbstractPrincipal(String id) {
		super(id);
	}
	public AbstractPrincipal(String id, String name) {
		super(id, name);
	}

	/**
	 * @return Returns the roleList.
	 */
	public List getRoleAssignList() {
		return this.roleAssignList;
	}
	/**
	 * @param roleAssignList The roleList to set.
	 */
	public void setRoleAssignList(List roleAssignList) {
		if (this.roleAssignList != null) {
			this.roleAssignList.clear();
		}
		this.roleAssignList = roleAssignList;
	}

	/**
	 * @return Returns the roleList.
	 */
	public List getRoleList() {
		return this.roleList;
	}
	/**
	 * @param roleList The roleList to set.
	 */
	public void setRoleList(List roleList) {
		if (this.roleList != null) {
			this.roleList.clear();
		}
		this.roleList = roleList;
	}
	/**
	 * 역할을 추가한다. 클라이언트가 직접 이 메소드를 호출하면 안된다.
	 * @param assign
	 */
	public void addRoleAssign(IRoleAssign assign) {
		if (this.roleAssignList == null) {
			this.roleAssignList = new ArrayList();
		}
		if (!this.roleAssignList.contains(assign)) {
			this.roleAssignList.add(assign);
		}
	}
	/**
	 * 역할을 삭제한다. 클라이언트가 직접 이 메소드를 호출하면 안된다.
	 * @param assign
	 */
	public void removeRoleAssign(IRoleAssign assign) {
		if (this.roleAssignList != null) {
			this.roleAssignList.remove(assign);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IPrincipal#getRoleAssgin(java.lang.String)
	 */
	public IRoleAssign getRoleAssign(String roleId) {
		if (this.roleAssignList != null) {
			for (Iterator i = this.roleAssignList.iterator(); i.hasNext();) {
				IRoleAssign roleAssign = (IRoleAssign) i.next();
				if (roleAssign.getRoleId().equals(roleId)) {
					return roleAssign;
				}
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.AbstractEntity#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		if (this.roleAssignList != null) {
			for (Iterator i = this.roleAssignList.iterator(); i.hasNext();) {
				sb.append(",roleAssign=(" + i.next().toString() + ")");
			}
		}
		return sb.toString();
	}
}
