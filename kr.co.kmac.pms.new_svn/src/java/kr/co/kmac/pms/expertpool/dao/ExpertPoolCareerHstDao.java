/*
 * $Id: ExpertPoolCareerHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 1. 17
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao;

import java.util.List;

import kr.co.kmac.pms.expertpool.data.ExpertPoolCareerHst;

import org.springframework.dao.DataAccessException;

/**
 * ������ ��� Dao Interface
 * @author jiwoongLee
 * @version $Id: ExpertPoolCareerHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public interface ExpertPoolCareerHstDao {

	/**
	 * ������ �̷� ����
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void create(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException;

	/**
	 * ������ �̷� ����
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void store(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException;

	/**
	 * ������ �̷� ����
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn, String seqNo) throws DataAccessException;
	
	/**
	 * ������ �̷� ����
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * �ش� ������ �̷� ��ȸ
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPoolCareerHst retrieve(String ssn, String seqNo) throws DataAccessException;

	/**
	 * �ش� �������� �̷� ��ȯ
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List<ExpertPoolCareerHst> findAll(String ssn) throws DataAccessException;

	/**
	 * ������ ���� ���� ��ȯ
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public boolean isExist(String ssn, String seqNo) throws DataAccessException;
}
