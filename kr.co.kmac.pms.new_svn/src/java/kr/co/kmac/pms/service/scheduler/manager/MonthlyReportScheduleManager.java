package kr.co.kmac.pms.service.scheduler.manager;

public interface MonthlyReportScheduleManager {

	public int assignMonthlyReport();

	public int assignMonthlyReportMissed(String projectCode);

}
