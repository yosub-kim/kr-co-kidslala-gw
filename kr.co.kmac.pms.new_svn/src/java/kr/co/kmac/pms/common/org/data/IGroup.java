/*
 * $Id: IGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.data;

import java.util.List;

import kr.co.kmac.pms.common.org.exception.HierarchyRuleViolationException;

/**
 * 그룹 모델. 그룹은 그룹원 설정 규칙을 갖는다. 그룹은 기본적으로 User를 구성원으로 갖는다.
 * @author 최인호
 * @version $Id: IGroup.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public interface IGroup extends IPrincipal, IGroupConstants {
	public int getGroupType();
	/**
	 * 그룹의 그룹타입이 groupType에 속하는 그룹타입이면 true를 리턴한다.
	 * @param groupType
	 * @return
	 */
	public boolean isSubTypeOf(int groupType);
	public String getMemberRule();
	public void setMemberRule(String rule);
	public List getMemberList();
	/**
	 * Group의 멤버를 추가한다. 그룹 멤버가 추가되려면 isHierarchical()가 true여야 한다. 그룹 멤버 추가시 자식의 parentId, path, depth는 자동으로 설정된다.
	 * @param member
	 */
	public void addMember(IPrincipal member) throws HierarchyRuleViolationException;
	/**
	 * Group의 멤버를 삭제한다. 그룹 멤버가 삭제되려면 isHierarchical()가 true여야 한다. 그룹 멤버 삭제시 자식의 parentId, path, depth는 자동으로 설정된다.
	 * @param member
	 */
	public void removeMember(IPrincipal member) throws HierarchyRuleViolationException;
	public String getParentId();
	/**
	 * ParentId는 그룹이 자식으로 추가될 때 자동으로 설정되므로 클라이언트가 이 메소드를 직접 호출하면 안된다.
	 * @param parentId
	 * @see IGroup.addMember()
	 */
	public void setParentId(String parentId);
	/**
	 * 그룹의 부모를 리턴한다.
	 * @return
	 */
	public IGroup getParent();
	/**
	 * 그룹을 구성원으로 가질 수 있으면 true를 리턴한다.
	 * @return
	 */
	public boolean isHierarchical();
	/**
	 * 구조화된 그룹의 경우 path가 설정된다. path는 root일 경우에는 /, 첫번째 자식은 /root, 두번째 자식은 /root/sub1, 세번째 자식은 /root/sub1/sub2 등의 값을 갖는다.
	 * @return
	 */
	public String getPath();
	/**
	 * 구조화된 그룹의 경우 depth가 0보다 클수 있다. 루트 그룹의 depth는 0이다.
	 * @return
	 */
	public int getDepth();
}
