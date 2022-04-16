package kr.co.kmac.pms.service.scheduler.manager.impl;

import kr.co.kmac.pms.service.scheduler.batch.WeeklyReportScheduleService;
import kr.co.kmac.pms.service.scheduler.manager.WeeklyReportScheduleManager;

public class WeeklyReportScheduleManagerImpl implements WeeklyReportScheduleManager {
	private WeeklyReportScheduleService weeklyReportScheduleService;

	public WeeklyReportScheduleService getWeeklyReportScheduleService() {
		return weeklyReportScheduleService;
	}

	public void setWeeklyReportScheduleService(WeeklyReportScheduleService weeklyReportScheduleService) {
		this.weeklyReportScheduleService = weeklyReportScheduleService;
	}

	@Override
	public int assignWeeklyReport() {
		return this.getWeeklyReportScheduleService().assignWeeklyReport();
	}

	@Override
	public int assignWeeklyReportOnThieWeek(String projectCode) {
		return this.getWeeklyReportScheduleService().assignWeeklyReportOnThieWeek(projectCode);
	}

	@Override
	public int assignWeeklyReportWhenApproved(String projectCode) {
		return this.getWeeklyReportScheduleService().assignWeeklyReportWhenApproved(projectCode);
	}

	@Override
	public int assignWeeklyReportMissed(String projectCode) {
		return this.getWeeklyReportScheduleService().assignWeeklyReportMissed(projectCode);
	}

}
