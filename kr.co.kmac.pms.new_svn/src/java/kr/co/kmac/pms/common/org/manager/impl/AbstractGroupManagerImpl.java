/*
 * $Id: AbstractGroupManagerImpl.java,v 1.2 2010/01/03 12:46:49 cvs1 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 4.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import java.util.Iterator;
import java.util.List;

import kr.co.kmac.pms.common.org.dao.IGroupDao;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.IGroup;
import kr.co.kmac.pms.common.org.data.IUser;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IGroupManager;

/**
 * IGroupManager 추상 구현.
 * @author 최인호
 * @version $Id: AbstractGroupManagerImpl.java,v 1.2 2010/01/03 12:46:49 cvs1 Exp $
 */
public abstract class AbstractGroupManagerImpl implements IGroupManager {
	protected IGroupDao groupDao;

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#addChild(com.miracom.bpms.security.core.IGroup,
	 *      com.miracom.bpms.security.core.IUser)
	 */
	public void addChild(IGroup parent, IUser child) {
		this.groupDao.addUserMember(parent.getId(), child.getId());
		parent.addMember(child);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#containsUser(java.lang.String, java.lang.String)
	 */
	public boolean containsUser(String parentId, String userId) {
		return this.groupDao.containsUser(parentId, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#countChildren(java.lang.String)
	 */
	public int countMember(String parentId) {
		return countUser(parentId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#countUser(java.lang.String)
	 */
	public int countUser(String parentId) {
		return this.groupDao.countUserMember(parentId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#find()
	 */
	public List find() {
		return this.groupDao.find(GROUP_USER_GROUP);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#findGroup(java.lang.String, int)
	 */
	public List findGroupOfUser(String userId, int groupType) {
		return this.groupDao.findGroupOfUser(userId, groupType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#findUser(java.lang.String)
	 */
	public List findUser(String parentId) {
		return this.groupDao.findUserMember(parentId);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#findUserNotInGroup(java.lang.String, boolean)
	 */
	public List findUserNotInGroup(String parentId, boolean includeSelf) {
		return this.groupDao.findUserNotInGroup(parentId, includeSelf);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#hasChildren(java.lang.String)
	 */
	public boolean hasMember(String parentId) {
		return hasUser(parentId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#hasUser(java.lang.String)
	 */
	public boolean hasUser(String parentId) {
		return this.groupDao.countUserMember(parentId) > 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#populateGroup(com.miracom.bpms.security.core.IUser)
	 */
	public void populateGroup(IUser user) {
		assert user != null : "User must not be null.";
		((User) user).setGroupList(this.groupDao.findGroupOfUser(user.getId()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#populateMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void populateMember(IGroup group) {
		populateUserMember(group);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#populateUserMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void populateUserMember(IGroup group) {
		assert group != null : "Group must not be null.";
		((Group) group).setUserList(this.groupDao.findUserMember(group.getId()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#removeChild(com.miracom.bpms.security.core.IGroup,
	 *      com.miracom.bpms.security.core.IUser)
	 */
	public void removeChild(IGroup parent, IUser child) {
		assert parent != null && child != null;
		this.groupDao.removeUserMember(parent.getId(), child.getId());
		//parent.removeMember(child);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#removeAllMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void removeAllMember(IGroup parent) {
		removeAllUserMember(parent);
	}

	/* (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IGroupManager#removeAllUserMember(com.miracom.bpms.security.core.IGroup)
	 */
	public void removeAllUserMember(IGroup parent) {
		List userList = this.groupDao.findUserMember(parent.getId());
		for (Iterator i = userList.iterator(); i.hasNext();) {
			this.groupDao.removeUserMember(parent.getId(), ((IUser) i.next()).getId());
		}
		((Group) parent).setUserList(null);
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
}
