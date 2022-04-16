/*
 * $Id: UserAuthenticationDao.java,v 1.1 2013/01/30 09:19:16 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authentication.dao;

import java.util.List;

import kr.co.kmac.pms.system.authentication.data.UserAuthentication;

import org.springframework.dao.DataAccessException;

public interface UserAuthenticationDao {
	public List<UserAuthentication> getUserAuthentication(String userSsn, String device) throws DataAccessException;

	public UserAuthentication getUserAuthentication(String userSsn, String device, boolean isValid) throws DataAccessException;

	public void insertUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException;

	public void updateUserAuthentication(UserAuthentication userAuthentication) throws DataAccessException;

	public void updateUserAuthentication2(String userSsn, String device) throws DataAccessException;

}
