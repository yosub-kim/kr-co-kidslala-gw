/*
 * $Id: IUserDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ����ȣ
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
 * @author ����ȣ
 * @version $Id: IUserDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IUserDao extends IEntityDao {
	public void create(IUser user) throws DataAccessException;
	public void update(IUser user) throws DataAccessException;
	public IUser retrieve(String id) throws DataAccessException;
	public IUser retrieveForLogin(String loginId) throws DataAccessException;

	public int countGroup(String userId) throws DataAccessException;

	/**
	 * �̸��Ϸ� ����ڸ� ã�´�. �̸����� like�� �˻��Ǳ� ������ ���� _, % �� like �˻� ���ý��� ����� �� �ִ�.
	 * @param email
	 * @return
	 * @throws DataAccessException
	 */
	public List findByEmail(String email) throws DataAccessException;
	/**
	 * �������� ã�´�.
	 * @param pos ���� �ڵ� �Ǵ� �̸�
	 * @return
	 * @throws DataAccessException
	 */
	public List findByPosition(String pos) throws DataAccessException;
	/**
	 * �ֹε�Ϲ�ȣ�� ã�´�.
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List findBySsn(String ssn) throws DataAccessException;
}
