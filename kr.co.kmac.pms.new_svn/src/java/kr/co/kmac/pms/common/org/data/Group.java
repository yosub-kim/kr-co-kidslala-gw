/*
 * $Id: Group.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
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
 * �׷� ��. �׷��� �׷���� ���ϴ� ��Ģ�� ���� �� �ִ�. �׷��� �⺻������ User�� �׷������ ���´�.
 * @author ����ȣ
 * @version $Id: Group.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class Group extends AbstractPrincipal implements IGroup {
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 4488475101296939352L;

	private int groupType = GROUP_GENERAL;
	private String memberRule;
	private String parentId;
	private IGroup parent;
	private List userList;
	private String path;
	private int depth = 0;
	private String orderSeq;

	public Group(String id, String name) {
		super(id, name);
	}

	public Group(String id) {
		super(id);
	}

	public void setId(String id) {
		super.setId(id);
		// ���̵� ����Ǹ� path�� ����Ǿ�� �Ѵ�. path�� root�� ��� /[id]�� �����ȴ�.
		setPath(generatePath(null));
	}

	/**
	 * @return Returns the groupType.
	 */
	public int getGroupType() {
		return this.groupType;
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#isSubTypeOf(int)
	 */
	public boolean isSubTypeOf(int groupType) {
		return this.groupType >= groupType && this.groupType < (groupType + GroupUtils.getLevelStep(groupType));
	}

	/**
	 * @param groupType The groupType to set.
	 */
	protected void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return Returns the parent.
	 */
	public IGroup getParent() {
		return this.parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(IGroup parent) throws HierarchyRuleViolationException {
		checkParentRule(parent);
		this.parent = parent;
		if (parent != null) {
			setParentId(parent.getId());
		} else {
			setParentId(null);
		}
		setPath(generatePath(parent));
		setDepth(parent == null ? 0 : parent.getDepth() + 1);
	}
	/**
	 * �н��� �����Ѵ�. parent�� null�̸� /[���̵�], null�� �ƴϸ� [�θ� �н�]/[���̵�]�� �����ȴ�.
	 * @param parent
	 * @return
	 */
	protected final String generatePath(IGroup parent) {
		return GroupUtils.generatePath(parent, this);
	}
	/**
	 * @return the orderSeq
	 */
	public String getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq the orderSeq to set
	 */
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#getParentId()
	 */
	public String getParentId() {
		return parentId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#setParentId(java.lang.String)
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return Returns the memberRule.
	 */
	public String getMemberRule() {
		return this.memberRule;
	}

	/**
	 * @param memberRule The memberRule to set.
	 */
	public void setMemberRule(String memberRule) {
		this.memberRule = memberRule;
	}

	/**
	 * @return Returns the depth.
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * @param depth The depth to set.
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return Returns the userList.
	 */
	public List getUserList() {
		return this.userList;
	}
	/**
	 * @param userList The userList to set.
	 */
	public void setUserList(List userList) {
		if (this.userList != null) {
			this.userList.clear();
		}
		this.userList = userList;
	}
	/**
	 * User �߰�. Ŭ���̾�Ʈ�� ���� �� �޼ҵ带 ȣ���ϸ� �ȵȴ�.
	 * @param user
	 */
	public void addUser(IUser user) {
		if (this.userList == null) {
			this.userList = new ArrayList();
		}
		if (!this.userList.contains(user)) {
			this.userList.add(user);
		}
		if (!user.belongsToGroup(this)) {
			((User) user).addGroup(this);
		}
	}
	/**
	 * User ����. Ŭ���̾�Ʈ�� ���� �� �޼ҵ带 ȣ���ϸ� �ȵȴ�.
	 * @param user
	 */
	public void removeUser(IUser user) {
		if (this.userList != null) {
			this.userList.remove(user);
		}
		if (user.belongsToGroup(this)) {
			((User) user).removeGroup(this);
		}
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#addMember(com.miracom.bpms.security.core.IPrincipal)
	 */
	public void addMember(IPrincipal member) throws HierarchyRuleViolationException {
		if (!(member instanceof IUser)) {
			throw new IllegalArgumentException("Member must be User");

		}
		addUser((IUser) member);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#getMemberList()
	 */
	public List getMemberList() {
		return getUserList();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#removeMember(com.miracom.bpms.security.core.IPrincipal)
	 */
	public void removeMember(IPrincipal member) throws HierarchyRuleViolationException {
		if (!(member instanceof IUser)) {
			throw new IllegalArgumentException("Member must be User");
		}
		removeUser((IUser) member);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#setMemberList(java.util.List)
	 */
	public void setMemberList(List memberList) throws HierarchyRuleViolationException {
		setUserList(memberList);
	}
	/**
	 * �θ� ��Ģ�� �����Ѵ�. ������ �߰ߵǸ� HierarchyRuleViolationException�� ������.
	 * <p>
	 * �θ� null�̸� �׳� �����ϰ�, null�� �ƴϸ� parent.isHierarchical()�� true�̾�߸� �θ� �� �� �ִ�.
	 * </p>
	 * <p>
	 * ���� Ŭ�������� ��Ģ�� �߰��Ϸ��� �� �޼ҵ带 Overide�ϵ� �ݵ�� <code>super.checkParentRule(parent)</code>�� ȣ���Ͽ��� �Ѵ�.
	 * </p>
	 * @param parent
	 * @throws HierarchyRuleViolationException
	 */
	protected void checkParentRule(IGroup parent) throws HierarchyRuleViolationException {
		if (parent != null && !parent.isHierarchical()) {
			throw new HierarchyRuleViolationException(parent + " can't have child group.");
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.Group#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(",groupType=" + this.groupType);
		sb.append(",memberRule=" + this.memberRule);
		sb.append(",parentId=" + this.parentId);
		sb.append(",path=" + this.path);
		sb.append(",depth=" + this.depth);
		if (this.userList != null) {
			for (Iterator i = this.userList.iterator(); i.hasNext();) {
				sb.append(",user=(" + ((IUser) i.next()).getId() + ")");
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroup#isHierarchical()
	 */
	public boolean isHierarchical() {
		return false;
	}

}
