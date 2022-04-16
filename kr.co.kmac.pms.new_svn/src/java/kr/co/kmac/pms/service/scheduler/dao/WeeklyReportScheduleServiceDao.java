/*
 * $Id: WeeklyReportScheduleServiceDao.java,v 1.2 2019/02/12 16:58:28 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.project.wreport.data.WeeklyReport;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleServiceDao.java,v 1.3 2012/01/12 11:55:48
 *          cvs Exp $
 */
public interface WeeklyReportScheduleServiceDao {

	public List<WeeklyReport> getWeeklyReportList();

	public List<WeeklyReport> getWeeklyReportList(String projectCode);

	public List<WeeklyReport> getWeeklyReportListWhenApproved(String projectCode);

	public List<WeeklyReport> getWeeklyReportListMissed(String projectCode);
	// public WeeklyReport getWeeklyReportList(String projectCode, String assignDate);

	// public boolean hasWeeklyReportIssued(String projectCode, String assignDate);

}
