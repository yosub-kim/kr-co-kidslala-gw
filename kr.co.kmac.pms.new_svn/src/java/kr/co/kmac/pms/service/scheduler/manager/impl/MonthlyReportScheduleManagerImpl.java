package kr.co.kmac.pms.service.scheduler.manager.impl;

import kr.co.kmac.pms.service.scheduler.batch.MonthlyReportScheduleService;
import kr.co.kmac.pms.service.scheduler.manager.MonthlyReportScheduleManager;

public class MonthlyReportScheduleManagerImpl implements MonthlyReportScheduleManager {
	private MonthlyReportScheduleService monthlyReportScheduleService;

	public MonthlyReportScheduleService getMonthlyReportScheduleService() {
		return monthlyReportScheduleService;
	}

	public void setMonthlyReportScheduleService(MonthlyReportScheduleService monthlyReportScheduleService) {
		this.monthlyReportScheduleService = monthlyReportScheduleService;
	}

	@Override
	public int assignMonthlyReport() {
		return this.getMonthlyReportScheduleService().assignMonthlyReport();
	}

	@Override
	public int assignMonthlyReportMissed(String projectCode) {
		return this.getMonthlyReportScheduleService().assignMonthlyReportMissed(projectCode);
	}

}
