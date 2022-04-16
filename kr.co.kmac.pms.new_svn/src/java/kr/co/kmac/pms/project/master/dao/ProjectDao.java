package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectDelayInfo;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

import org.springframework.dao.DataAccessException;

public interface ProjectDao {

	public Project select(String projectCode) throws DataAccessException;

	public void insert(Project project) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void update(Project project) throws DataAccessException;

	public ProjectSimpleInfo getProjectSimpleInfo(String projectCode) throws DataAccessException;
	
	public List<ProjectSimpleInfo> getProjectSimpleInfo2(String projectCode) throws DataAccessException;
	
	public List<ProjectSimpleInfo> getProjectSimpleInfo3(String projectCode) throws DataAccessException;
	
	public List<ProjectSimpleInfo> getProjectSimpleInfo4(String projectCode) throws DataAccessException;

	public List<String> getEndProcessTaskList(String projectCode) throws DataAccessException;

	public void updateProjectState(String projectCode, String state) throws DataAccessException;

	public void updateProjectEndTaskState(String projectCode, String state) throws DataAccessException;

	public void updateKdbState(String projectCode, String state) throws DataAccessException;

	public void updateBudgetState(String entNo) throws DataAccessException;

	public void sendBudgetState(String entNo) throws DataAccessException;
	
	public void updateAdminOpenState(String projectCode, String adminOpenYn) throws DataAccessException;
	
	public void updateBusinessFunctionType(String projectCode, String functionType) throws DataAccessException;

	public List<ProjectDelayInfo> getProjectDelayInfo(String projectCode, String delayType) throws DataAccessException;

	public List<ProjectDelayInfo> getProjectTaskDelayInfo(String projectCode, String delayType) throws DataAccessException;

	public boolean isConnectedProject(String projectCode) throws ProjectException;

}
