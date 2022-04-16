/*
 * $Id: DeptHistoryServiceDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : yhyim
 * creation-date : 2008. 1. 11.
 * =========================================================
 * Copyright (c) 2008 KMAC All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.data.DeptHistoryData;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: DeptHistoryServiceDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 */
public interface DeptHistoryServiceDao {
	public void setWorkingPUMemberInfoToHistory(DeptHistoryData deptHistoryData) throws DataAccessException;

	public List<DeptHistoryData> getWorkingExpertList();
}