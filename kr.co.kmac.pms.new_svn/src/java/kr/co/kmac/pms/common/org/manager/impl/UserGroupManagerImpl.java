/*
 * $Id: UserGroupManagerImpl.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 4.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.org.manager.IUserGroupManager;

/**
 * IUserGroupManager 구현.
 * @author 최인호
 * @version $Id: UserGroupManagerImpl.java,v 1.1 2009/09/19 11:15:27 cvs3 Exp $
 */
public class UserGroupManagerImpl extends AbstractGroupManagerImpl implements IUserGroupManager {
	/*
	 * (non-Javadoc)
	 * @see com.miracom.bpms.security.core.IUserGroupManager#findUserGroup(java.lang.String)
	 */
	public List findUserGroup(String userId) {
		return getGroupDao().findGroupOfUser(userId, GROUP_USER_GROUP);
	}
}
