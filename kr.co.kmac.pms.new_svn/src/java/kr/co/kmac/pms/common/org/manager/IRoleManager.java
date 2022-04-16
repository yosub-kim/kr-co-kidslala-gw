/*
 * $Id: IRoleManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.org.data.IPrincipal;
import kr.co.kmac.pms.common.org.data.IRoleAssign;

/**
 * ���� �Ҵ� ������ �������̽�.
 * @author ����ȣ
 * @version $Id: IRoleManager.java,v 1.1 2009/09/19 11:15:34 cvs3 Exp $
 */
public interface IRoleManager {
	public void assignRole(IPrincipal principal, IRoleAssign roleAssign);
	public void assignRole(IPrincipal principal, String roleId, String assignerId, Date assignDate);
	public void unassignRole(IPrincipal principal, String roleId);

	/**
	 * principal �� role�� ä���.
	 * @param principal
	 */
	public void populateRoleAssign(IPrincipal principal);
	public IRoleAssign retrieveUserRoleAssign(String userId, String roleId);

	public int countUserRole(String userId);

	public boolean hasUserRole(String userId, String roleId);
	public boolean hasUserRole(String userId, String roleId, boolean checkInheritance);

	public List findUserRole(String userId);
	public List findRoleByUserId(String userId, boolean checkInheritance);
	public List findUser(String roleId, boolean checkInheritance);
}
