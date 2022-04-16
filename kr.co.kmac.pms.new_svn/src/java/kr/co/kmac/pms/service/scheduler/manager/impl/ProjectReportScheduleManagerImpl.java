package kr.co.kmac.pms.service.scheduler.manager.impl;

import kr.co.kmac.pms.service.scheduler.batch.ProjectReportScheduleService;
import kr.co.kmac.pms.service.scheduler.manager.ProjectReportScheduleManager;

public class ProjectReportScheduleManagerImpl implements ProjectReportScheduleManager {
	private ProjectReportScheduleService projectReportScheduleService;

	public ProjectReportScheduleService getProjectReportScheduleService() {
		return projectReportScheduleService;
	}

	public void setProjectReportScheduleService(ProjectReportScheduleService projectReportScheduleService) {
		this.projectReportScheduleService = projectReportScheduleService;
	}

	@Override
	public void assignProjectReportUntilToday() {
		this.getProjectReportScheduleService().assignProjectReportUntilToday();
	}

	@Override
	public void assignProjectReportUntilToday(String projectCode) {
		this.getProjectReportScheduleService().assignProjectReportUntilToday(projectCode);
	}

	@Override
	public void assignProjectReportUntilApproveDate(String projectCode, String approveDate) {
		this.getProjectReportScheduleService().assignProjectReportUntilApproveDate(projectCode, approveDate);
	}

}
