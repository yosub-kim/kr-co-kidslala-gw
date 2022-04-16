/*
 * $Id: AbstractHierarchicalGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
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
 * ����ȭ�� �׷� ��. ����ȭ�� �׷��� �׷��� �ڽ����� ���� �� �ִ�.
 * @author ����ȣ
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
	 * ���� �׷� ����Ʈ�� �����Ѵ�. ����׷��� �����۵��� IGroup�̾�� �ϰ� �� IGroup �������� �θ� �� �׷����� �����ȴ�.
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
	 * �ڽ� �׷��� �߰��Ѵ�.
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
	 * ���� �׷� ���� ��Ģ�� �����Ѵ�. ������ �߰ߵǸ� HierarchyRuleViolationException�� ������.
	 * <p>
	 * ���� Ŭ�������� ��Ģ�� �߰��Ϸ��� �� �޼ҵ带 Overide�ϵ� �ݵ�� <code>super.checkParentRule(parent)</code>�� ȣ���Ͽ��� �Ѵ�.
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
	 * �ڽ� �׷� ����
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
