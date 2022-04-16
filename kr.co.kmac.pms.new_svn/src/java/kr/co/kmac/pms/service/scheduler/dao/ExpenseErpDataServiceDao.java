/*
 * $Id: ExpenseErpDataServiceDao.java,v 1.4 2011/10/24 10:08:26 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.data.ExpenseErpData;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseErpDataServiceDao.java,v 1.4 2011/10/24 10:08:26 cvs Exp $
 */
public interface ExpenseErpDataServiceDao {

	public List<ExpenseErpData> getExpenseDataFromErpFullData() throws DataAccessException;

	public List<String> getExpenseDataFromErpForFlagUpdate() throws DataAccessException;

	public int setExpenseDataToPms(List<ExpenseErpData> list) throws DataAccessException;

	public void updateProjectReportExceedState(String projectCode) throws DataAccessException;
	public void updateProjectTeachingFeeExceedState(String projectCode) throws DataAccessException;
	public void updateProjectTeachingRestFeeExceedState(String projectCode) throws DataAccessException;

	public void updateProjectReportExceedStateInit() throws DataAccessException;
	public void updateProjectTeachingFeeExceedStateInit() throws DataAccessException;
	public void updateProjectTeachingRestFeeExceedStateInit() throws DataAccessException;

	public void updateProjectReportExceedStateSpcProjectInit(String projectCode) throws DataAccessException;
	public void updateProjectTeachingFeeExceedStateSpcProjectInit(String projectCode) throws DataAccessException;
	public void updateProjectTeachingRestFeeExceedStateSpcProjectInit(String projectCode) throws DataAccessException;
}
