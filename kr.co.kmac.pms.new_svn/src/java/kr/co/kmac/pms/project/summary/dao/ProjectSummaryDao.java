package kr.co.kmac.pms.project.summary.dao;

import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;

import org.springframework.dao.DataAccessException;

public interface ProjectSummaryDao {
	public ProjectSummaryData retrieve(String projectCode) throws DataAccessException;
}
