/*
 * $Id: IEntityDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * Entity DAO ���� �������̽�
 * @author ����ȣ
 * @version $Id: IEntityDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface IEntityDao extends ICommonDao {
	/**
	 * Entity�� �����ϸ� true �ƴϸ� false�� �����Ѵ�.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public boolean entityExists(String id) throws DataAccessException;
	public boolean isEnabled(String id) throws DataAccessException;
	/**
	 * ���̵� <code>id</code>�� ��ƼƼ�� �����Ѵ�. �׷�ó�� �ڽ��� ���� ��ƼƼ�� �ش� ��ƼƼ�� ��� �ڽı��� �����Ѵ�.
	 * @param id
	 * @throws DataAccessException
	 */
	public void remove(String id) throws DataAccessException;
	/**
	 * ���̵� <code>id</code>�� entity�� �̸��� �����Ѵ�.
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public String retrieveName(String id) throws DataAccessException;
	/**
	 * Entity�� ���º� ������ �����Ѵ�.
	 * @param enabled
	 * @return
	 * @throws DataAccessException
	 */
	public int count(boolean enabled) throws DataAccessException;
	/**
	 * �̸����� Entity�� ã�´�. �̸��� like�� �˻��Ǳ� ������ ���� _, % �� like �˻� ���ý��� ����� �� �ִ�.
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public List findByName(String name) throws DataAccessException;
	/**
	 * ���º��� �˻��Ѵ�.
	 * @param enabled
	 * @return
	 * @throws DataAccessException
	 */
	public List find(boolean enabled) throws DataAccessException;
}
