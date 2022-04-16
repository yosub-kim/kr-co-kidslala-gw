package kr.co.kmac.pms.project.preport.dao;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

import org.springframework.dao.DataAccessException;

public interface ProjectReportDao {
	public String getSeqNo(String projectCode, String taskFormTypeId) throws DataAccessException;

	public int count(String projectCode, String year, String month) throws DataAccessException;

	public List<ProjectReportPlan> select(String projectCode) throws DataAccessException;

	public List<ProjectReportPlan> select(String projectCode, String year, String month) throws DataAccessException;

	public ProjectReportPlan select(String projectCode, String year, String month, String day) throws DataAccessException;

	public void insert(ProjectReportPlan projectReportPlan) throws DataAccessException;

	public void update(ProjectReportPlan projectReportPlan) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public void updateProjectReportPayAmount(String projectCode, String assignDate, 
			String ssn, String payAmount) throws DataAccessException;

}
