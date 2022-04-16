package kr.co.kmac.pms.project.manpower.dao;

import java.util.List;

import kr.co.kmac.pms.project.manpower.data.ProjectManpower;

import org.springframework.dao.DataAccessException;

public interface ProjectManpowerDetailDao {
	public List<ProjectManpower> select(String projectCode) throws DataAccessException;

	public List<ProjectManpower> select(String projectCode, String year, String month) throws DataAccessException;

	public List<ProjectManpower> select(String projectCode, String year, String month, String day) throws DataAccessException;

	public List<ProjectManpower> select2(String projectCode, String year, String month) throws DataAccessException;
	
	public ProjectManpower select(String projectCode, String year, String month, String day, String ssn) throws DataAccessException;

	public void insert(ProjectManpower projectManpower) throws DataAccessException;

	public void update(ProjectManpower projectManpower) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String day) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String day, String ssn) throws DataAccessException;

	public List<ProjectManpower> retrieve(String projectCode, String startDate, String endDate) throws DataAccessException;
	
	public List<ProjectManpower> retrieve2(String projectCode);
	
	public List<ProjectManpower> retrieve3(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ProjectManpower> retrieve4(String projectCode) throws DataAccessException;
	
	public List<ProjectManpower> retrieve5(String projectCode) throws DataAccessException;
}