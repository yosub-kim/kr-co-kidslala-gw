/*
 * $Id: EntityNameProviderImpl.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 7.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import kr.co.kmac.pms.common.org.dao.IGroupDao;
import kr.co.kmac.pms.common.org.dao.IRoleDao;
import kr.co.kmac.pms.common.org.dao.IUserDao;
import kr.co.kmac.pms.common.org.manager.IEntityNameProvider;

/**
 * IEntityNameProvider 구현
 * @author 최인호
 * @version $Id: EntityNameProviderImpl.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 */
public class EntityNameProviderImpl implements IEntityNameProvider {
	private IUserDao userDao;
	private IGroupDao groupDao;
	private IRoleDao roleDao;

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityNameProvider#getGroupName(java.lang.String)
	 */
	public String getGroupName(String id) {
		return this.groupDao.retrieveName(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityNameProvider#getRoleName(java.lang.String)
	 */
	public String getRoleName(String id) {
		return this.roleDao.retrieveName(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityNameProvider#getUserName(java.lang.String)
	 */
	public String getUserName(String id) {
		return this.userDao.retrieveName(id);
	}

	/**
	 * @return Returns the groupDao.
	 */
	public IGroupDao getGroupDao() {
		return this.groupDao;
	}

	/**
	 * @param groupDao The groupDao to set.
	 */
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/**
	 * @return Returns the roleDao.
	 */
	public IRoleDao getRoleDao() {
		return this.roleDao;
	}

	/**
	 * @param roleDao The roleDao to set.
	 */
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * @return Returns the userDao.
	 */
	public IUserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @param userDao The userDao to set.
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	
}
