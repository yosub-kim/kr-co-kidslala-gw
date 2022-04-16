/*
 * $Id: RoleManagerImpl.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 3.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.org.dao.IPrincipalRoleDao;
import kr.co.kmac.pms.common.org.dao.IUserRoleDao;
import kr.co.kmac.pms.common.org.data.IPrincipal;
import kr.co.kmac.pms.common.org.data.IRoleAssign;
import kr.co.kmac.pms.common.org.data.RoleAssign;
import kr.co.kmac.pms.common.org.manager.IRoleManager;

/**
 * IRoleManager 구현
 * @author 최인호
 * @version $Id: RoleManagerImpl.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 */
public class RoleManagerImpl implements IRoleManager {
	private IUserRoleDao userRoleDao; 

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#assignRole(com.miracom.bpms.security.core.IPrincipal,
	 *      com.miracom.bpms.security.core.IRoleAssign)
	 */
	public void assignRole(IPrincipal principal, IRoleAssign roleAssign) {
		assert principal != null && roleAssign != null : "Principal and RoleAssign must not be null.";

		IPrincipalRoleDao principalRoleDao = this.userRoleDao; 

		if (principalRoleDao.principalRoleExists(principal.getId(), roleAssign.getRoleId())) {
			principalRoleDao.update(roleAssign);
		} else {
			principalRoleDao.create(roleAssign);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#assignRole(com.miracom.bpms.security.core.IPrincipal,
	 *      java.lang.String, java.lang.String, java.util.Date)
	 */
	public void assignRole(IPrincipal principal, String roleId, String assignerId, Date assignDate) {
		assert principal != null && roleId != null : "Principal and RoleId must not be null.";

		assignRole(principal, new RoleAssign(principal.getId(), roleId));
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#unassignRole(com.miracom.bpms.security.core.IPrincipal,
	 *      java.lang.String)
	 */
	public void unassignRole(IPrincipal principal, String roleId) {
		assert principal != null && roleId != null : "Principal and RoleId must not be null.";

		IPrincipalRoleDao principalRoleDao = this.userRoleDao;

		principalRoleDao.remove(principal.getId(), roleId);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#unassignAllUser(java.lang.String)
	 */
	public void unassignAllUser(String roleId) {
		this.userRoleDao.removeAllByRoleId(roleId);
	}


	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#populateRole(com.miracom.bpms.security.core.IPrincipal)
	 */
	public void populateRoleAssign(IPrincipal principal) {
		assert principal != null : "Principal must not be null.";

		IPrincipalRoleDao principalRoleDao = this.userRoleDao;

		List list = principalRoleDao.findByPrincipalId(principal.getId());
		principal.setRoleAssignList(list);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#retrieveUserRole(java.lang.String, java.lang.String)
	 */
	public IRoleAssign retrieveUserRoleAssign(String userId, String roleId) {
		return this.userRoleDao.retrieve(userId, roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#countUserRole(java.lang.String)
	 */
	public int countUserRole(String userId) {
		return this.userRoleDao.countByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#hasUserRole(java.lang.String, java.lang.String)
	 */
	public boolean hasUserRole(String userId, String roleId) {
		return this.userRoleDao.principalRoleExists(userId, roleId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#hasUserRole(java.lang.String, java.lang.String, boolean)
	 */
	public boolean hasUserRole(String userId, String roleId, boolean checkInheritance) {
		return this.userRoleDao.hasRole(userId, roleId, checkInheritance);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#findUserRole(java.lang.String)
	 */
	public List findUserRole(String userId) {
		return this.userRoleDao.findByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#findRoleByUserId(java.lang.String, boolean)
	 */
	public List findRoleByUserId(String userId, boolean checkInheritance) {
		return this.userRoleDao.findRoleByUserId(userId, checkInheritance);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IRoleManager#findUser(java.lang.String, boolean)
	 */
	public List findUser(String roleId, boolean checkInheritance) {
		return this.userRoleDao.findUserByRoleId(roleId, checkInheritance);
	}

	/**
	 * @return Returns the userRoleDao.
	 */
	public IUserRoleDao getUserRoleDao() {
		return this.userRoleDao;
	}

	/**
	 * @param userRoleDao The userRoleDao to set.
	 */
	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	
}
