/*
 * $Id: IGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

import kr.co.kmac.pms.common.org.exception.HierarchyRuleViolationException;

/**
 * �׷� ��. �׷��� �׷�� ���� ��Ģ�� ���´�. �׷��� �⺻������ User�� ���������� ���´�.
 * @author ����ȣ
 * @version $Id: IGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IGroup extends IPrincipal, IGroupConstants {
	public int getGroupType();
	/**
	 * �׷��� �׷�Ÿ���� groupType�� ���ϴ� �׷�Ÿ���̸� true�� �����Ѵ�.
	 * @param groupType
	 * @return
	 */
	public boolean isSubTypeOf(int groupType);
	public String getMemberRule();
	public void setMemberRule(String rule);
	public List getMemberList();
	/**
	 * Group�� ����� �߰��Ѵ�. �׷� ����� �߰��Ƿ��� isHierarchical()�� true���� �Ѵ�. �׷� ��� �߰��� �ڽ��� parentId, path, depth�� �ڵ����� �����ȴ�.
	 * @param member
	 */
	public void addMember(IPrincipal member) throws HierarchyRuleViolationException;
	/**
	 * Group�� ����� �����Ѵ�. �׷� ����� �����Ƿ��� isHierarchical()�� true���� �Ѵ�. �׷� ��� ������ �ڽ��� parentId, path, depth�� �ڵ����� �����ȴ�.
	 * @param member
	 */
	public void removeMember(IPrincipal member) throws HierarchyRuleViolationException;
	public String getParentId();
	/**
	 * ParentId�� �׷��� �ڽ����� �߰��� �� �ڵ����� �����ǹǷ� Ŭ���̾�Ʈ�� �� �޼ҵ带 ���� ȣ���ϸ� �ȵȴ�.
	 * @param parentId
	 * @see IGroup.addMember()
	 */
	public void setParentId(String parentId);
	/**
	 * �׷��� �θ� �����Ѵ�.
	 * @return
	 */
	public IGroup getParent();
	/**
	 * �׷��� ���������� ���� �� ������ true�� �����Ѵ�.
	 * @return
	 */
	public boolean isHierarchical();
	/**
	 * ����ȭ�� �׷��� ��� path�� �����ȴ�. path�� root�� ��쿡�� /, ù��° �ڽ��� /root, �ι�° �ڽ��� /root/sub1, ����° �ڽ��� /root/sub1/sub2 ���� ���� ���´�.
	 * @return
	 */
	public String getPath();
	/**
	 * ����ȭ�� �׷��� ��� depth�� 0���� Ŭ�� �ִ�. ��Ʈ �׷��� depth�� 0�̴�.
	 * @return
	 */
	public int getDepth();
}
