package kr.co.kmac.pms.project.preport.dao;

import java.util.List;

import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

import org.springframework.dao.DataAccessException;

public interface ProjectReportDetailDao {
	public List<ProjectReportPlan> select(String projectCode) throws DataAccessException;

	public List<ProjectReportPlan> select(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ProjectReportPlan> selectProjectMember(String projectCode) throws DataAccessException;

	public List<ProjectReportPlan> selectProjectWorkName(String projectCode, String year, String month) throws DataAccessException;

	public List<ProjectReportPlan> selectProjectMonthWork(String projectCode, String year, String month) throws DataAccessException;

	public List<ProjectReportPlan> select(String projectCode, String year, String month, String day) throws DataAccessException;

	public ProjectReportPlan select(String projectCode, String year, String month, String day, String ssn) throws DataAccessException;

	public void insert(ProjectReportPlan projectReportPlan) throws DataAccessException;

	public void update(ProjectReportPlan projectReportPlan) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String day) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String day, String ssn) throws DataAccessException;
}
