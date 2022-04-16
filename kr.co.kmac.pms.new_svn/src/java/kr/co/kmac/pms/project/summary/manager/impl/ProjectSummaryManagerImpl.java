package kr.co.kmac.pms.project.summary.manager.impl;

import kr.co.kmac.pms.project.summary.dao.ProjectSummaryDao;
import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;
import kr.co.kmac.pms.project.summary.exception.ProjectSummaryException;
import kr.co.kmac.pms.project.summary.manager.ProjectSummaryManager;

public class ProjectSummaryManagerImpl implements ProjectSummaryManager {
	private ProjectSummaryDao projectSummaryDao;
	
	public ProjectSummaryDao getProjectSummaryDao() {
		return projectSummaryDao;
	}

	public void setProjectSummaryDao(ProjectSummaryDao projectSummaryDao) {
		this.projectSummaryDao = projectSummaryDao;
	}
	
	public ProjectSummaryData retrieve(String projectCode) throws ProjectSummaryException{
		return this.getProjectSummaryDao().retrieve(projectCode);
	}
}