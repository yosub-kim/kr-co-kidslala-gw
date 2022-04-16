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
 * 전문가 경력 Dao Interface
 * @author jiwoongLee
 * @version $Id: ExpertPoolCareerHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public interface ExpertPoolCareerHstDao {

	/**
	 * 전문가 이력 생성
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void create(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException;

	/**
	 * 전문가 이력 수정
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void store(ExpertPoolCareerHst expertPoolCareerHst) throws DataAccessException;

	/**
	 * 전문가 이력 삭제
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn, String seqNo) throws DataAccessException;
	
	/**
	 * 전문가 이력 삭제
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * 해당 전문가 이력 조회
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPoolCareerHst retrieve(String ssn, String seqNo) throws DataAccessException;

	/**
	 * 해당 전문가의 이력 반환
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List<ExpertPoolCareerHst> findAll(String ssn) throws DataAccessException;

	/**
	 * 데이터 존재 유무 반환
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public boolean isExist(String ssn, String seqNo) throws DataAccessException;
}
