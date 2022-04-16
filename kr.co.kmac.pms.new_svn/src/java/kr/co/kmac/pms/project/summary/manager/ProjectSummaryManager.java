package kr.co.kmac.pms.project.summary.manager;

import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;
import kr.co.kmac.pms.project.summary.exception.ProjectSummaryException;

public interface ProjectSummaryManager {
	public ProjectSummaryData retrieve(String projectCode) throws ProjectSummaryException;
}