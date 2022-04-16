/*
 * $Id: IOrgUnitManager.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IOrgUnit;

/**
 * OrgUnitManager�� OrgUnit�� ����(�θ�-�ڽ�)�� ���õ� �۾��� �����Ѵ�.
 * @author ����ȣ
 * @version $Id: IOrgUnitManager.java,v 1.2 2010/07/07 16:27:42 cvs2 Exp $
 */
public interface IOrgUnitManager extends IGroupManager {
	public boolean hasOrgUnit(String parentId);
	public boolean containsOrgUnit(String parentId, String orgUnitId);

	public int countMember(String parentId, boolean inDepth);
	public int countUser(String parentId, boolean inDepth);
	public int countOrgUnit(String parentId, boolean inDepth);

	/**
	 * ���̵� orgUnitId�� �μ��� �θ� �μ��� �����Ѵ�.
	 * @param orgUnitId
	 * @return
	 */
	public IOrgUnit retrieveParent(String orgUnitId);
	public void populateOrgUnitMember(IOrgUnit orgUnit);
	/**
	 * OrgUnit�� ��Ʈ�� �����Ѵ�. �ڽ��� ��Ʈ�� ��쿡�� null�� �����Ѵ�.
	 * @param orgUnit
	 * @return
	 */
	public IOrgUnit getRoot(IOrgUnit orgUnit);

	public void addChild(IOrgUnit parent, IOrgUnit child);
	public void removeChild(IOrgUnit parent, IOrgUnit child);
	public void removeAllGroupMember(IOrgUnit parent);
	/**
	 * dest �ڽ����� src�� �̵��Ѵ�. src�� ��� �ڽ��� �Բ� �̵��ȴ�.
	 * destId�� null�̸� src�� ��Ʈ�� �̵��Ѵ�.
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
	 * �ƾƵ� <code>userId</code>�� ����ڰ� ���� �μ��� ã�´�.
	 * @param userId
	 * @return
	 */
	public List findOrgUnit(String userId);
}
