/*
 * $Id: EndProcessCheckDao.java,v 1.4 2018/01/29 02:25:47 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.dao;

import java.util.Map;

import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: EndProcessCheckDao.java,v 1.4 2018/01/29 02:25:47 cvs Exp $
 */
public interface EndProcessCheckDao {

	public void create(String projectCode, String taskId, String isExecuted) throws DataAccessException;

	public void create(EndProcessCheck endProcessCheck) throws DataAccessException;

	public void create(EndProcessCheck[] endProcessCheck) throws DataAccessException;

	public void store(EndProcessCheck endProcessCheck) throws DataAccessException;

	public boolean isExist(String projectCode, String taskId) throws DataAccessException;

	public boolean isFinished(String projectCode) throws DataAccessException;

	public String getCooSsn(String runningDivCode) throws DataAccessException;

	public String getKMSsn() throws DataAccessException;
}
