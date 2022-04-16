/*
 * $Id: ICommonDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 10. 26.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.org.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * DAO 공통 인터페이스
 * @author 최인호
 * @version $Id: ICommonDao.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 */
public interface ICommonDao {
	/**
	 * 대상 테이블의 모든 행수를 리턴한다.
	 * @return
	 * @throws DataAccessException
	 */
	public int count() throws DataAccessException;
	/**
	 * 대상 테이블의 모든 내용을 삭제한다.
	 * @throws DataAccessException
	 */
	public void removeAll() throws DataAccessException;
	/**
	 * 대상 테이블의 모든 내용을 리턴한다.
	 * @return
	 * @throws DataAccessException
	 */
	public List find() throws DataAccessException;
}
