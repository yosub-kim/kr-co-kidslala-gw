/*
 * $Id: UserAuthenticationManagerImpl.java,v 1.1 2013/01/30 09:19:16 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authentication.manager.impl;

import java.util.List;

import kr.co.kmac.pms.system.authentication.dao.UserAuthenticationDao;
import kr.co.kmac.pms.system.authentication.data.UserAuthentication;
import kr.co.kmac.pms.system.authentication.manager.UserAuthenticationManager;

import org.springframework.dao.DataAccessException;

public class UserAuthenticationManagerImpl implements UserAuthenticationManager {

	private UserAuthenticationDao userAuthenticationDao;

	public UserAuthenticationDao getUserAuthenticationDao() {
		return userAuthenticationDao;
	}

	public void setUserAuthenticationDao(UserAuthenticationDao userAuthenticationDao) {
		this.userAuthenticationDao = userAuthenticationDao;
	}

	@Override
	public List<UserAuthentication> getUserAuthentication(String userSsn, String device) throws DataAccessException {
		return this.getUserAuthenticationDao().getUserAuthentication(userSsn, device);
	}

	@Override
	public UserAuthentication getUserAuthentication(String userSsn, String device, boolean isValid) throws DataAccessException {
		return this.getUserAuthenticationDao().getUserAuthentication(userSsn, device, isValid);
	}

	@Override
	public void insertUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException {
		this.getUserAuthenticationDao().updateUserAuthentication2(userAuthentication.getUserSsn(), userAuthentication.getDevice());
		this.getUserAuthenticationDao().insertUserAuthentication(userAuthentication);
	}

	@Override
	public void updateUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException {
		this.getUserAuthenticationDao().updateUserAuthentication(userAuthentication);
	}

}
