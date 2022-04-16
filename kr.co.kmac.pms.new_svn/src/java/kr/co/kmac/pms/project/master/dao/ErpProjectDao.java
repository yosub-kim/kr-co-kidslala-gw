package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectBudget;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;

import org.springframework.dao.DataAccessException;

public interface ErpProjectDao {

	public Project select(String projectCode) throws DataAccessException;
	
	public ProjectBudget selectBudget(String projectCode) throws DataAccessException;

	public List<String> selectEntNo(String projectCode) throws DataAccessException;
	
	public String selectMemberChangeEntNo(String projectCode) throws DataAccessException;

	public String selectEntNoForUpdate(String projectCode) throws DataAccessException;
	
	public List<ProjectMember> selectAllProjectMember(String entNo) throws DataAccessException; 

	public ProjectRelatedEntNo getProjectRelatedEntNo(String seq) throws DataAccessException;

	public void insertProjectRelatedEntNo(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException;

	public void deleteProjectRelatedEntNo(String seq) throws DataAccessException;
	
	public ProjectRelatedEntNo getProjectRelatedEntNoForMemberChange(String seq) throws DataAccessException;
	
	public void insertProjectRelatedEntNoForMemberChange(ProjectRelatedEntNo projectRelatedEntNo) throws DataAccessException;

	public void deleteProjectRelatedEntNoForMemberChange(String seq) throws DataAccessException;	
}
