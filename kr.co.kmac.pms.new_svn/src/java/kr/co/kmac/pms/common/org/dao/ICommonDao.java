/*
 * $Id: ICommonDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : ����ȣ
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * DAO ���� �������̽�
 * @author ����ȣ
 * @version $Id: ICommonDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface ICommonDao {
	/**
	 * ��� ���̺��� ��� ����� �����Ѵ�.
	 * @return
	 * @throws DataAccessException
	 */
	public int count() throws DataAccessException;
	/**
	 * ��� ���̺��� ��� ������ �����Ѵ�.
	 * @throws DataAccessException
	 */
	public void removeAll() throws DataAccessException;
	/**
	 * ��� ���̺��� ��� ������ �����Ѵ�.
	 * @return
	 * @throws DataAccessException
	 */
	public List find() throws DataAccessException;
}
