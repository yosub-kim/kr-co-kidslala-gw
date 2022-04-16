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
 * 인력풀 학력 입출력 dao
 * @author jiwoongLee
 * @version $Id: ExpertPoolSchoolHstDao.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public interface ExpertPoolSchoolHstDao {

	/**
	 * 전문가 학력 생성
	 * @param expertPoolCareerHst
	 * @throws DataAccessException
	 */
	public void create(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException;

	/**
	 * 전문가 학력 수정
	 * @param ExpertPoolSchoolHst
	 * @throws DataAccessException
	 */
	public void store(ExpertPoolSchoolHst expertPoolSchoolHst) throws DataAccessException;

	/**
	 * 전문가 학력 삭제
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn, String seqNo) throws DataAccessException;
	
	/**
	 * 전문가 학력 삭제
	 * @param ssn
	 * @param seqNo
	 * @throws DataAccessException
	 */
	public void remove(String ssn) throws DataAccessException;

	/**
	 * 해당 전문가 학력 조회
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public ExpertPoolSchoolHst retrieve(String ssn, String seqNo) throws DataAccessException;

	/**
	 * 해당 전문가의 학력 반환
	 * @param ssn
	 * @return
	 * @throws DataAccessException
	 */
	public List<ExpertPoolSchoolHst> findAll(String ssn) throws DataAccessException;

	/**
	 * 데이터 존재 유무 반환
	 * @param ssn
	 * @param seqNo
	 * @return
	 * @throws DataAccessException
	 */
	public boolean isExist(String ssn, String seqNo) throws DataAccessException;
}
