/*
 * $Id: ProjectRollingDao.java,v 1.4 2017/10/11 03:49:01 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao;

import java.util.Map;

import kr.co.kmac.pms.project.endprocess.form.ProjectRollingForm;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingDao.java,v 1.4 2017/10/11 03:49:01 cvs Exp $
 */
public interface ProjectRollingDao {

	public void insertRollingC(ProjectRollingForm projectRollingForm) throws DataAccessException;
	
	public void insertRollingR(ProjectRollingForm projectRollingForm) throws DataAccessException;
	
	public void insertRollingE(ProjectRollingForm projectRollingForm) throws DataAccessException;
	
	public void insertRollingF(ProjectRollingForm projectRollingForm) throws DataAccessException;
	
	public void insertRollingT(ProjectRollingForm projectRollingForm) throws DataAccessException;
	
	public Map isFinishedRolling(String projectCode) throws DataAccessException;

	public Map isFinishedRolling(String projectCode, String seq) throws DataAccessException;
	
	public int numOfCustomer(String projectCode) throws DataAccessException;
}
