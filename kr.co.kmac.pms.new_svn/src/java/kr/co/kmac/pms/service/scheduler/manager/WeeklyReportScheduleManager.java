/*
 * $Id: WeeklyReportScheduleManager.java,v 1.3 2019/02/12 16:58:28 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.manager;


public interface WeeklyReportScheduleManager {

	public int assignWeeklyReport();

	public int assignWeeklyReportOnThieWeek(String projectCode);

	public int assignWeeklyReportWhenApproved(String projectCode);
	
	public int assignWeeklyReportMissed(String projectCode);

}
