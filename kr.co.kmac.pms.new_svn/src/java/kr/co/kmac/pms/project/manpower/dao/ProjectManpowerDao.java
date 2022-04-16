package kr.co.kmac.pms.project.manpower.dao;

import java.util.List;

import kr.co.kmac.pms.project.manpower.data.ProjectManpower;

import org.springframework.dao.DataAccessException;

public interface ProjectManpowerDao {
	public String getSeqNo(String projectCode, String taskFormTypeId) throws DataAccessException;

	public int count(String projectCode, String year, String month) throws DataAccessException;

	public List<ProjectManpower> select(String projectCode) throws DataAccessException;

	public List<ProjectManpower> select(String projectCode, String year, String month) throws DataAccessException;

	public ProjectManpower select(String projectCode, String year, String month, String day) throws DataAccessException;

	public void insert(ProjectManpower projectManpower) throws DataAccessException;

	public void update(ProjectManpower projectManpower) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

}