/*
 * $Id: IRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import kr.co.kmac.pms.common.org.data.IRole;

import org.springframework.dao.DataAccessException;

/**
 * Role Dao 인터페이스.
 * @author 최인호
 * @version $Id: IRoleDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IRoleDao extends IEntityDao {
	public void create(IRole role) throws DataAccessException;
	public void update(IRole role) throws DataAccessException;
	public IRole retrieve(String id) throws DataAccessException;
}
