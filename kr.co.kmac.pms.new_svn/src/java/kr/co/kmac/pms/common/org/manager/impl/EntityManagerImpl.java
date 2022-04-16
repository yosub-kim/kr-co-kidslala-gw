/*
 * $Id: EntityManagerImpl.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 3.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import kr.co.kmac.pms.common.org.dao.IGroupDao;
import kr.co.kmac.pms.common.org.dao.IRoleDao;
import kr.co.kmac.pms.common.org.dao.IUserDao;
import kr.co.kmac.pms.common.org.data.IEntity;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IRole;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IEntityManager;

import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * IEntityManager 구현
 * @author 최인호
 * @version $Id: EntityManagerImpl.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 */
public class EntityManagerImpl implements IEntityManager {
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;
	private IGroupDao groupDao;
	private IUserDao userDao;
	private IRoleDao roleDao;

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#clearAll()
	 */
	public void clearAll() {
		this.userDao.removeAll();
		this.roleDao.removeAll();
		this.groupDao.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#clearGroup()
	 */
	public void clearGroup() {
		this.groupDao.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#clearRole()
	 */
	public void clearRole() {
		this.roleDao.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#clearUser()
	 */
	public void clearUser() {
		this.userDao.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countGroup()
	 */
	public int countGroup() {
		return this.groupDao.count(GROUP_GENERAL);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countGroup(int)
	 */
	public int countGroup(int groupType) {
		return this.groupDao.count(groupType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countGroupByState(boolean)
	 */
	public int countGroup(boolean enabled) {
		return this.groupDao.count(enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countGroupByState(int, boolean)
	 */
	public int countGroup(int groupType, boolean enabled) {
		return this.groupDao.count(groupType, enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countRole()
	 */
	public int countRole() {
		return this.roleDao.count();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countRoleByState(boolean)
	 */
	public int countRole(boolean enabled) {
		return this.roleDao.count(enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countUser()
	 */
	public int countUser() {
		return this.userDao.count();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#countUserByState(boolean)
	 */
	public int countUser(boolean enabled) {
		return this.userDao.count(enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroup()
	 */
	public List findGroup() {
		return this.groupDao.find();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroup(int)
	 */
	public List findGroup(int groupType) {
		return this.groupDao.find(groupType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroupByName(int, java.lang.String)
	 */
	public List findGroupByName(int groupType, String name) {
		return this.groupDao.findByName(groupType, name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroupByName(java.lang.String)
	 */
	public List findGroupByName(String name) {
		return this.groupDao.findByName(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroupByState(boolean)
	 */
	public List findGroup(boolean enabled) {
		return this.groupDao.find(enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findGroupByState(int, boolean)
	 */
	public List findGroup(int groupType, boolean enabled) {
		return this.groupDao.find(groupType, enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findRole()
	 */
	public List findRole() {
		return this.roleDao.find();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findRoleByName(java.lang.String)
	 */
	public List findRoleByName(String name) {
		return this.roleDao.findByName(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findRoleByState(boolean)
	 */
	public List findRole(boolean enabled) {
		return this.roleDao.find(enabled);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUser()
	 */
	public List findUser() {
		return this.userDao.find();
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUserByEmail(java.lang.String)
	 */
	public List findUserByEmail(String email) {
		return this.userDao.findByEmail(email);
	}
	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUserByPosition(java.lang.String)
	 */
	public List findUserByPosition(String position) {
		return this.userDao.findByPosition(position);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUserBySsn(java.lang.String)
	 */
	public List findUserBySsn(String ssn) {
		return this.userDao.findBySsn(ssn);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUserByName(java.lang.String)
	 */
	public List findUserByName(String name) {
		return this.userDao.findByName(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#findUserByState(boolean)
	 */
	public List findUser(boolean enabled) {
		return this.userDao.find(enabled);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#getGroupType(java.lang.String)
	 */
	public int getGroupType(String id) {
		return this.groupDao.getGroupType(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#importFromXml(java.io.Reader, int)
	 */
	public void importFromXml(Reader reader, int importPolicy) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isGroupEnabled(java.lang.String)
	 */
	public boolean isGroupEnabled(String id) {
		return this.groupDao.isEnabled(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isGroupExist(java.lang.String)
	 */
	public boolean groupExists(String id) {
		return this.groupDao.entityExists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isRoleEnabled(java.lang.String)
	 */
	public boolean isRoleEnabled(String id) {
		return this.roleDao.isEnabled(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isRoleExist(java.lang.String)
	 */
	public boolean roleExists(String id) {
		return this.roleDao.entityExists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isUserEnabled(java.lang.String)
	 */
	public boolean isUserEnabled(String id) {
		return this.userDao.isEnabled(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#isUserExist(java.lang.String)
	 */
	public boolean userExists(String id) {
		return this.userDao.entityExists(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#getGroupName(java.lang.String)
	 */
	public String getGroupName(String id) {
		return this.groupDao.retrieveName(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#getRoleName(java.lang.String)
	 */
	public String getRoleName(String id) {
		return this.roleDao.retrieveName(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#getUserName(java.lang.String)
	 */
	public String getUserName(String id) {
		return this.userDao.retrieveName(id);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#removeGroup(java.lang.String)
	 */
	public void removeGroup(String id) {
		this.groupDao.remove(id);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#removeRole(java.lang.String)
	 */
	public void removeRole(String id) {
		this.roleDao.remove(id);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#removeUser(java.lang.String)
	 */
	public void removeUser(String id) {
		this.userDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#retrieveGroup(java.lang.String)
	 */
	public IGroup retrieveGroup(String id) {
		return this.groupDao.retrieve(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#retrieveRole(java.lang.String)
	 */
	public IRole retrieveRole(String id) {
		return this.roleDao.retrieve(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#retrieveUser(java.lang.String)
	 */
	public IUser retrieveUser(String id) {
		return this.userDao.retrieve(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#store(com.miracom.bpms.security.core.IEntity)
	 */
	public void store(IEntity entity) {
		if (entity instanceof IUser) {
			if (this.userDao.entityExists(entity.getId())) {
				this.userDao.update((IUser) entity);
			} else {
				User user = (User) entity;
				encodePassword(user);
				this.userDao.create((IUser) entity);
			}
		} else if (entity instanceof IGroup) {
			if (this.groupDao.entityExists(entity.getId())) {
				this.groupDao.update((IGroup) entity);
			} else {
				this.groupDao.create((IGroup) entity);
			}
		} else if (entity instanceof IRole) {
			if (this.roleDao.entityExists(entity.getId())) {
				this.roleDao.update((IRole) entity);
			} else {
				this.roleDao.create((IRole) entity);
			}
		} else {
			throw new IllegalArgumentException(entity.toString() + " is not allowed for store.");
		}
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IEntityManager#storeUserPassword(java.lang.String, java.lang.String)
	 */
	public void storeUserPassword(String id, String password) {
		Assert.notNull(password);
		User user = (User) this.userDao.retrieve(id);
		encodePassword(user);
		this.userDao.update(user);
	}

	private void encodePassword(User user) {
		Object salt = null;
		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(user);
		}
		if (this.passwordEncoder != null) {
			user.setPassword(this.passwordEncoder.encodePassword(user.getPassword() == null ? "" : user.getPassword(),
					salt));
		}
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

	/**
	 * @return Returns the passwordEncoder.
	 */
	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	/**
	 * @param passwordEncoder The passwordEncoder to set.
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @return Returns the saltSource.
	 */
	public SaltSource getSaltSource() {
		return this.saltSource;
	}

	/**
	 * @param saltSource The saltSource to set.
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}
}
