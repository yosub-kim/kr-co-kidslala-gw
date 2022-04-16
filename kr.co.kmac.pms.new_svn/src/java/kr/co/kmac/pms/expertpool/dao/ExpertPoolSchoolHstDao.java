/*
 * $Id: ExpertPoolSchoolHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.dao;

import java.util.List;

import kr.co.kmac.pms.expertpool.data.ExpertPoolSchoolHst;

import org.springframework.dao.DataAccessException;

/**
 * �η�Ǯ �з� ����� dao
 * @author jiwoongLee
 * @version $Id: ExpertPoolSchoolHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public interface ExpertPoolSchoolHstDao {

	/**
	 * ������ �з� ����
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void create(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException;

	/**
	 * ������ �з� ����
	 * @param ExpertPoolSchoolHst
	 * @throws DataAccessException
	 */
	public void store(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException;

	/**
	 * ������ �з� ����
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn, String seqNo) throws DataAccessException;
	
	/**
	 * ������ �з� ����
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * �ش� ������ �з� ��ȸ
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPoolSchoolHst retrieve(String ssn, String seqNo) throws DataAccessException;

	/**
	 * �ش� �������� �з� ��ȯ
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List<ExpertPoolSchoolHst> findAll(String ssn) throws DataAccessException;

	/**
	 * ������ ���� ���� ��ȯ
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public boolean isExist(String ssn, String seqNo) throws DataAccessException;
}
