/*
 * $Id: AbstractHierarchicalGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.co.kmac.pms.common.org.exception.HierarchyRuleViolationException;
import kr.co.kmac.pms.common.org.util.GroupUtils;

/**
 * 구조화된 그룹 모델. 구조화된 그룹은 그룹을 자식으로 가질 수 있다.
 * @author 최인호
 * @version $Id: AbstractHierarchicalGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public abstract class AbstractHierarchicalGroup extends Group {
	private static final long serialVersionUID = -6380972228960573969L;
	private List subGroupList;

	public AbstractHierarchicalGroup(String id, String name) {
		super(id, name);
	}

	public AbstractHierarchicalGroup(String id) {
		super(id);
	}

	/**
	 * @return Returns the subGroupList.
	 */
	public List getSubGroupList() {
		return this.subGroupList;
	}

	/**
	 * 서브 그룹 리스트를 설정한다. 서브그룹의 아이템들은 IGroup이어야 하고 각 IGroup 아이템의 부모가 이 그룹으로 설정된다.
	 * @param subGroupList The subGroupList to set.
	 */
	public void setSubGroupList(List groupList) throws HierarchyRuleViolationException {
		if (this.subGroupList != null) {
			this.subGroupList.clear();
		}
		this.subGroupList = groupList;
		if (groupList != null) {
			for (Iterator i = groupList.iterator(); i.hasNext();) {
				IGroup subGroup = (IGroup) i.next();
				checkSubGroupRule(subGroup);
				GroupUtils.setParent(this, subGroup);
			}
		}
	}
	/**
	 * 자식 그룹을 추가한다.
	 * @param subGroup
	 */
	public void addSubGroup(IGroup subGroup) throws HierarchyRuleViolationException {
		checkSubGroupRule(subGroup);
		if (this.subGroupList == null) {
			this.subGroupList = new ArrayList();
		}
		if (!this.subGroupList.contains(subGroup)) {
			this.subGroupList.add(subGroup);
			GroupUtils.setParent(this, subGroup);
		}
	}
	/**
	 * 서브 그룹 구조 규칙을 점검한다. 위반이 발견되면 HierarchyRuleViolationException을 던진다.
	 * <p>
	 * 서브 클래스에서 규칙을 추가하려면 이 메소드를 Overide하되 반드시 <code>super.checkParentRule(parent)</code>를 호출하여야 한다.
	 * </p>
	 * @param subGroup
	 * @throws HierarchyRuleViolationException
	 */
	protected void checkSubGroupRule(IGroup subGroup) throws HierarchyRuleViolationException {
		if (equals(subGroup)) {
			throw new HierarchyRuleViolationException("Self subGroup is not allowed.");
		}
	}
	/**
	 * 자식 그룹 삭제
	 * @param subGroup
	 */
	public void removeSubGroup(IGroup subGroup) throws HierarchyRuleViolationException {
		if (this.subGroupList != null) {
			this.subGroupList.remove(subGroup);
			GroupUtils.makeRoot(subGroup);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#addMember(com.miracom.bpms.security.core.IPrincipal)
	 */
	public void addMember(IPrincipal member) throws HierarchyRuleViolationException {
		if (!(member instanceof IUser || member instanceof IGroup)) {
			throw new IllegalArgumentException("Member must be Group or User");
		}
		if (member instanceof IGroup) {
			addSubGroup((IGroup) member);
		} else {
			addUser((IUser) member);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#getMemberList()
	 */
	public List getMemberList() {
		List memberList = new ArrayList();
		if (getUserList() != null) {
			memberList.addAll(getUserList());
		}
		if (getSubGroupList() != null) {
			memberList.addAll(getSubGroupList());
		}
		return memberList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#removeMember(com.miracom.bpms.security.core.IPrincipal)
	 */
	public void removeMember(IPrincipal member) throws HierarchyRuleViolationException {
		if (member instanceof IUser) {
			removeUser((IUser) member);
		} else if (member instanceof IGroup) {
			removeSubGroup((IGroup) member);
		} else {
			throw new IllegalArgumentException("Member must be Group or User");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#isHierarchical()
	 */
	public boolean isHierarchical() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#setMemberList(java.util.List)
	 */
	public void setMemberList(List memberList) throws HierarchyRuleViolationException {
		setUserList(null);
		setSubGroupList(null);

		if (memberList != null) {
			for (Iterator i = memberList.iterator(); i.hasNext();) {
				Object member = i.next();
				addMember((IPrincipal) member);
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		if (this.subGroupList != null) {
			for (Iterator i = this.subGroupList.iterator(); i.hasNext();) {
				sb.append(",subGroup=(" + i.next().toString() + ")");
			}
		}
		return sb.toString();
	}
}
