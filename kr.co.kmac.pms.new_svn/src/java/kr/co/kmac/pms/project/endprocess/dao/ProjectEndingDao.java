/*
 * $Id: ProjectEndingDao.java,v 1.2 2010/02/20 04:12:11 cvs1 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 28.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao;

import kr.co.kmac.pms.project.endprocess.form.ProjectEndingForm;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectEndingDao.java,v 1.2 2010/02/20 04:12:11 cvs1 Exp $
 */
public interface ProjectEndingDao {

	public void insertRateC(ProjectEndingForm projectEndingForm) throws DataAccessException;

	public void insertRateP(ProjectEndingForm projectEndingForm) throws DataAccessException;

	public void insertRateE(ProjectEndingForm projectEndingForm) throws DataAccessException;

	public void insertEnding(ProjectEndingForm projectEndingForm) throws DataAccessException;

	public int updateProjectState(String prjectCode) throws DataAccessException;
	
	public void sendInfoResearchCustomerSatisfactionTask(String projectCode) throws DataAccessException;
	
	// Job Date: 2010-02-08	Author: yhyim	Description: 컨설팅 고객만족도 평가 관련 
	public void sendInfoConsultingCustomerSatisfactionTask(String projectCode) throws DataAccessException;
}
