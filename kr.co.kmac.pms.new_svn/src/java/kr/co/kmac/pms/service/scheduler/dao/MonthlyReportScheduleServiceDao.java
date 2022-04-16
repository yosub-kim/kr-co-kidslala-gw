/*
 * $Id: MonthlyReportScheduleServiceDao.java,v 1.2 2019/02/12 16:58:28 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.project.mreport.data.MonthlyReport;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleServiceDao.java,v 1.3 2012/01/12 11:55:48
 *          cvs Exp $
 */
public interface MonthlyReportScheduleServiceDao {

	public List<MonthlyReport> getMonthlyReportList();

//	public List<MonthlyReport> getMonthlyReportList(String projectCode);

//	public List<MonthlyReport> getMonthlyReportListWhenApproved(String projectCode);

	public List<MonthlyReport> getMonthlyReportListMissed(String projectCode);
	
	public List<MonthlyReport> getMonthlyReportUntilGivenDated(String yyyymmdd);
	
	// public MonthlyReport getMonthlyReportList(String projectCode, String assignDate);

	// public boolean hasMonthlyReportIssued(String projectCode, String assignDate);

}
