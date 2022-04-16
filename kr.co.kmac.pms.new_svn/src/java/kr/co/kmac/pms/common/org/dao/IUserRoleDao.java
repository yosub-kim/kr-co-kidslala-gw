/*
 * $Id: IUserRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 21.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * User Role DAO
 * @author 최인호
 * @version $Id: IUserRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IUserRoleDao extends IPrincipalRoleDao {
	public int countByUserId(String userId) throws DataAccessException;
	public List findByUserId(String userId) throws DataAccessException;
	public List findUserByRoleId(String roleId, boolean checkInheritance) throws DataAccessException;
	public List findRoleByUserId(String userId, boolean checkInheritance) throws DataAccessException;
}
