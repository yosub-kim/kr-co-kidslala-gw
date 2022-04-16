/*
 * $Id: ProjectReportScheduleServiceDao.java,v 1.3 2012/01/12 11:55:48 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.data.ProjectReportScheduleData;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleServiceDao.java,v 1.3 2012/01/12 11:55:48 cvs Exp $
 */
public interface ProjectReportScheduleServiceDao {

	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday();

	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilGivenDate(String yyyymmdd);

	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday(String projectCode);

	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilToday(String projectCode, String ssn);

	public List<ProjectReportScheduleData> getScheduledAllListFromJulyUtilApproveDate(String projectCode, String approveDate);

	public String getTeamManagerSsn(String deptCode);

	public String getCfoManagerSsn(String divCode);
}
