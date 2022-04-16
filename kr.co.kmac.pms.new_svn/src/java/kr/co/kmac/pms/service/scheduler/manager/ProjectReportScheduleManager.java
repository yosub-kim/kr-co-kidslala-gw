/*
 * $Id: ProjectReportScheduleManager.java,v 1.2 2012/01/12 11:55:48 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.manager;

public interface ProjectReportScheduleManager {

	public void assignProjectReportUntilToday();

	public void assignProjectReportUntilToday(String projectCode);

	public void assignProjectReportUntilApproveDate(String projectCode, String approveDate);

}
