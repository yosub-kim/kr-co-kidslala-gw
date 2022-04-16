/*
 * $Id: AuthenticationDaoImpl.java,v 1.2 2018/03/21 13:29:21 cvs Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 11.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.common.org.dao.IUserDao;
import kr.co.kmac.pms.common.org.dao.IUserRoleDao;
import kr.co.kmac.pms.common.org.data.User;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * security.core의 dao를 사용하고 기본 Authority를 추가할 수 있도록 수정한 JdbcDaoImpl
 * 
 * @author jiwoongLee
 * @version $Id: AuthenticationDaoImpl.java,v 1.2 2018/03/21 13:29:21 cvs Exp $
 */
public class AuthenticationDaoImpl implements UserDetailsService {
	private static final Log logger = LogFactory.getLog(AuthenticationDaoImpl.class);

	private List<String> defaultAuthorityList;
	private IUserDao userDao;
	private IUserRoleDao userRoleDao;

	/**
	 * @return the userDao
 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the userRoleDao
	 */
	public IUserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	/**
	 * @param userRoleDao the userRoleDao to set
	 */
	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	/**
	 * @return Returns the defaultAuthorityList.
	 */
	public List<String> getDefaultAuthorityList() {
		return defaultAuthorityList;
	}

	/**
	 * @param defaultAuthorityList The defaultAuthorityList to set.
	 */
	public void setDefaultAuthorityList(List<String> defaultAuthorityList) {
		this.defaultAuthorityList = defaultAuthorityList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.acegisecurity.providers.dao.jdbc.JdbcDaoImpl#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails user = null;
		// 1. User 정보 획득
		try {
			user = (UserDetails) this.getUserDao().retrieveForLogin(username);
		} catch (ObjectRetrievalFailureException e) {
			logger.debug(e);
			throw new UsernameNotFoundException(username + "User not found");
		}

		// 2. Authority(접근권한) 정보 설정
		List<GrantedAuthorityImpl> dbAuths = new ArrayList<GrantedAuthorityImpl>();
		try {
			addCustomAuthorities(user.getUsername(), dbAuths);
		} catch (NullPointerException e1) {
			logger.error(e1);
			throw new UsernameNotFoundException("User not found", e1);
		}
		if (dbAuths.size() == 0) {
			throw new UsernameNotFoundException("User has no GrantedAuthority");
		}
		GrantedAuthority[] arrayAuths = {};
		arrayAuths = (GrantedAuthority[]) dbAuths.toArray(arrayAuths);
		((User)user).setAuthorities(arrayAuths);

		// 3. 역할 설정
		// String[] roles = permissionDao.getUserRoles(user.getUserid());
		// user.setRoles(roles);
		// PermissionRole permissionRole = PermissionController.getPermissionRoles(user);
		// user.setPermissionRole(permissionRole);

		return user;
	}

	protected void addCustomAuthorities(String username, List<GrantedAuthorityImpl> authorities) {
		List<String> list = getDefaultAuthorityList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				GrantedAuthorityImpl authority = new GrantedAuthorityImpl((String) list.get(i));
				if (logger.isDebugEnabled()) {
					logger.debug("Default Authority [" + (String) list.get(i) + "] was added to User [" + username + "]");
				}
				authorities.add(authority);
			}
		}
	}
}
