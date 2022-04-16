package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.statistics.data.ProjectMemberMonthlyMM;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;

import org.springframework.dao.DataAccessException;

public interface ProjectMemberDao {
	public List<ProjectMember> select(String projectCode) throws DataAccessException;
	
	public List<ProjectMember> selectAll(String projectCode) throws DataAccessException;

	public List<ProjectMember> selectForExpense(String projectCode) throws DataAccessException;
	
	public List<ProjectMember> selectForExpense2(String projectCode) throws DataAccessException;
	
	public List<ProjectMember> selectForExpensePU(String projectCode) throws DataAccessException;
	
	public List<ProjectMember> selectSchedule(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ProjectMember> selectScheduleReport(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ProjectMember> selectMonthlyReport(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ProjectMember> selectForExpenseAll(String projectCode) throws DataAccessException;

	public List<ProjectMember> selectOnlyMemberAll(String projectCode) throws DataAccessException;
	
	public List<Project> selectProjectCode(String ssn) throws DataAccessException;

	public ProjectMember select(String projectCode, String ssn) throws DataAccessException;
	
	public ProjectMemberMonthlyMM selectMMinfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException;

	public void insert(ProjectMember projectMember) throws DataAccessException;

	public void insert(List<RunningMemberChange> projectMemberlList) throws DataAccessException;
	
	public void insertMMInfo(String entno) throws DataAccessException;
	
	public void insertMMInfoByTime(String entno) throws DataAccessException;
	
	public void insertMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM);
	
	public void insertMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM, String trainingYN, String checkYN);
	
	public void createMMInfoModifiedLog(String projectCode, String ssn, String modifierSsn);

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String ssn) throws DataAccessException;
	
	public void deleteMMInfo(String projectCode) throws DataAccessException;
	
	public void deleteMMInfo(String projectCode, String ssn) throws DataAccessException;
	
	public void deleteMMInfoByTime(String projectCode) throws DataAccessException;
	
	public void deleteMMInfoByTime(String projectCode, String ssn) throws DataAccessException;
	
	public void deleteMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException;

	public void update(ProjectMember projectMember) throws DataAccessException;

	public void updateMemberChange(ProjectMember projectMember, String newRole, String newTrainingYn) throws DataAccessException;
	
	public void updateMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException;
}
