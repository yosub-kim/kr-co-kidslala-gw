/*
 * $Id: WorkTypeDao.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.worklist.dao;

import kr.co.kmac.pms.common.worklist.data.WorkType;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: WorkTypeDao.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public interface WorkTypeDao {

	public void createWorkType(WorkType workType) throws DataAccessException;

	public void deleteWorkType(String id) throws DataAccessException;

	public void updateWorkType(WorkType workType) throws DataAccessException;

	public WorkType getWorkType(String id) throws DataAccessException;

}
