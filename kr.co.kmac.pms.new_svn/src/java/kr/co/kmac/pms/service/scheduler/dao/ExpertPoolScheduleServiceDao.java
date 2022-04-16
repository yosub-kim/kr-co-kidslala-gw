/*
 * $Id: ExpertPoolScheduleServiceDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : yhyim
 * creation-date : 2019. 5. 20.
 * =========================================================
 * Copyright (c) 2019 KMAC All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.expertpool.data.ExpertPool;

import org.springframework.dao.DataAccessException;

/**
 * TODO Ŭ���� ����
 * 
 * @author yhyim
 * @version $Id: ExpertPoolScheduleServiceDao.java,v 1.0 2009/09/19 11:15:40 cvs3 Exp $
 */
public interface ExpertPoolScheduleServiceDao {

	public List<ExpertPool> getNotWorkingExpertList() throws DataAccessException;
	
	public void update(ExpertPool expertPool) throws DataAccessException;
	
	// (���)����谭��, (���)���б��� �߰�

	public List<ExpertPool> getNotWorkingExpertList_D() throws DataAccessException;
	
	public List<ExpertPool> getNotWorkingExpertList_E() throws DataAccessException; 
	
	public void update_D(ExpertPool expertPool) throws DataAccessException;

	public void update_E(ExpertPool expertPool) throws DataAccessException;


}