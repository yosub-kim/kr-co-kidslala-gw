/*
 * $Id: IUserDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 13.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import kr.co.kmac.pms.common.org.data.IUser;

import org.springframework.dao.DataAccessException;

/**
 * User DAO
 * @author 최인호
 * @version $Id: IUserDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IUserDao extends IEntityDao {
	public void create(IUser user) throws DataAccessException;
	public void update(IUser user) throws DataAccessException;
	public IUser retrieve(String id) throws DataAccessException;
	public IUser retrieveForLogin(String loginId) throws DataAccessException;

	public int countGroup(String userId) throws DataAccessException;

	/**
	 * 이메일로 사용자를 찾는다. 이메일은 like로 검색되기 때문에 에는 _, % 등 like 검색 신택스를 사용할 수 있다.
	 * @param email
	 * @return
	 * @throws DataAccessException
	 */
	public List findByEmail(String email) throws DataAccessException;
	/**
	 * 직급으로 찾는다.
	 * @param pos 직급 코드 또는 이름
	 * @return
	 * @throws DataAccessException
	 */
	public List findByPosition(String pos) throws DataAccessException;
	/**
	 * 주민등록번호로 찾는다.
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySsn(String ssn) throws DataAccessException;
}
