/*
 * $Id: IOrgUnitManager.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IOrgUnit;

/**
 * OrgUnitManager는 OrgUnit의 구조(부모-자식)와 관련된 작업을 수행한다.
 * @author 최인호
 * @version $Id: IOrgUnitManager.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 */
public interface IOrgUnitManager extends IGroupManager {
	public boolean hasOrgUnit(String parentId);
	public boolean containsOrgUnit(String parentId, String orgUnitId);

	public int countMember(String parentId, boolean inDepth);
	public int countUser(String parentId, boolean inDepth);
	public int countOrgUnit(String parentId, boolean inDepth);

	/**
	 * 아이디가 orgUnitId인 부서의 부모 부서를 리턴한다.
	 * @param orgUnitId
	 * @return
	 */
	public IOrgUnit retrieveParent(String orgUnitId);
	public void populateOrgUnitMember(IOrgUnit orgUnit);
	/**
	 * OrgUnit의 루트를 리턴한다. 자신이 루트인 경우에는 null을 리턴한다.
	 * @param orgUnit
	 * @return
	 */
	public IOrgUnit getRoot(IOrgUnit orgUnit);

	public void addChild(IOrgUnit parent, IOrgUnit child);
	public void removeChild(IOrgUnit parent, IOrgUnit child);
	public void removeAllGroupMember(IOrgUnit parent);
	/**
	 * dest 자식으로 src를 이동한다. src의 모든 자식이 함께 이동된다.
	 * destId가 null이면 src를 루트로 이동한다.
	 * @param srcId
	 * @param destId
	 */
	public void move(String srcId, String destId);

	public List findRoot();
	public List findRootByType(int orgUnitType);
	public List findMember(String parentId, boolean inDepth);
	public List findMember(String parentId, boolean inDepth, boolean isAll);
	public List findUser(String parentId, boolean inDepth);
	public List findOrgUnit(String parentId, boolean inDepth);

	/**
	 * 아아디가 <code>userId</code>인 사용자가 속한 부서를 찾는다.
	 * @param userId
	 * @return
	 */
	public List findOrgUnit(String userId);
}
