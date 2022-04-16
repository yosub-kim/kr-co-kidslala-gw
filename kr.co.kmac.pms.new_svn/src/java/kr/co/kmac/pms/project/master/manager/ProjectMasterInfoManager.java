package kr.co.kmac.pms.project.master.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectBudget;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.data.ProjectCustomerInfo;
import kr.co.kmac.pms.project.master.data.ProjectDelayInfo;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

public interface ProjectMasterInfoManager {

	public String getProjectBusinessTypeCode(String projectCode) throws ProjectException;

	public boolean isRegisteredProject(String projectCode) throws ProjectException;

	public boolean isConnectedProject(String projectCode) throws ProjectException;

	public Project getProject(String projectCode) throws ProjectException;

	public ProjectSimpleInfo getProjectSimpleInfo(String projectCode) throws ProjectException;

	public Project getErpProject(String projectCode) throws ProjectException;

	public Project getErpProject2(String projectCode) throws ProjectException;
	
	public ProjectBudget getProjectBudget(String projectCode) throws ProjectException;
	
	public List<ProjectMember> getErpProjectMember(String projectCode) throws ProjectException;

	public List<ProjectMember> getProjectMember(String projectCode) throws ProjectException;
	
	public List<Project> getProjectCodeInfo(String ssn) throws ProjectException;

	public List<ProjectMember> getProjectAllMember(String projectCode) throws ProjectException;

	public ProjectMember getProjectMember(String projectCode, String ssn) throws ProjectException;

	public ProjectCsrInfo getProjectCsrInfo(String projectCode, int seq) throws ProjectException;

	public List<ProjectCsrInfo> getProjectCsrInfo(String projectCode) throws ProjectException;
	
	public List<ProjectCsrInfo> getProjectAllCsrInfo(String projectCode) throws ProjectException;
	
	public void updateProjectCsrInfo(ProjectCsrInfo projectCsrInfo) throws ProjectException;

	public void insertProjectThxInfo(ProjectCsrInfo projectCsrInfo) throws ProjectException;

	public void createProject(Project project) throws ProjectException;

	public void createProjectMember(String projectCode, List<ProjectMember> projectMemberList) throws ProjectException;
	
	public void createProjectMemberMMInfo(String projectCode) throws ProjectException;

	public void updateProject(Project project) throws ProjectException;

	public void updateProjectMember(String projectCode, List<ProjectMember> projectMemberList) throws ProjectException;
	
	public void updateProjectMemberMMInfo(String projectCode, int seq) throws ProjectException;
	
	public void updateProjectMemberMMInfoForMemberChange(String projectCode, int seq) throws ProjectException;

	public void deleteProject(String projectCode) throws ProjectException;

	public void deleteProjectMember(String projectCode) throws ProjectException;

	public void deleteProjectMember(String projectCode, String ssn) throws ProjectException;

	public List<String> getEndProcessTaskList(String projectCode) throws ProjectException;

	public void updateProjectState(String projectCode, String state) throws ProjectException;

	public void updateBudgetState(String projectCode) throws ProjectException;
	
	public void updateBudgetState(String projectCode, int seq) throws ProjectException;
	
	public void updateBudgetStateForMemberChange(String projectCode, int seq) throws ProjectException;

	public void sendBudgetState(String projectCode) throws ProjectException;
	
	public void sendBudgetState(String projectCode, int seq) throws ProjectException;

	public boolean updateErpState(String projectCode) throws ProjectException;

	public void updateEduState(String projectCode) throws ProjectException;

	public void updateEduStateAgain(String oc_code) throws ProjectException;

	public void updateEduStateCancle(String projectCode, String oc_code) throws ProjectException;

	public void updateEduStateResanction(String oc_code) throws ProjectException;
	
	public void restoreEduStateResanction(String oc_code) throws ProjectException;

	public void updateAdminOpenState(String projectCode, String adminOpenYn) throws ProjectException;

	public List<ProjectDelayInfo> getProjectDelayInfo(String projectCode, String delayType) throws ProjectException;

	public List<ProjectDelayInfo> getProjectTaskDelayInfo(String projectCode, String delayType) throws ProjectException;

	public String[] getProjectEntNo(String projectCode) throws ProjectException;
	
	public String getProjectEntNoForMemberChange(String projectCode) throws ProjectException;
	
	public List<ProjectMember> getProjectMemberFromEntNo(String entNo) throws ProjectException;

	public ProjectRelatedEntNo getProjectRelatedEntNo(String seq) throws ProjectException;

	public void insertProjectRelatedEntNo(ProjectRelatedEntNo projectRelatedEntNo) throws ProjectException;

	public void deleteProjectRelatedEntNo(String seq) throws ProjectException;
	
	public ProjectRelatedEntNo getProjectRelatedEntNoForMemberChange(String seq) throws ProjectException;
	
	public void insertProjectRelatedEntNoForMemberChange(ProjectRelatedEntNo projectRelatedEntNo) throws ProjectException;
	
	public void deleteProjectRelatedEntNoForMemberChange(int seq) throws ProjectException;
	
	public List<ProjectCustomerInfo> getProjectCustomerInfo(String projectCode) throws ProjectException;

	public void createProjectCustomerInfo(String projectCode, List<ProjectCustomerInfo> projectCustomerInfos) throws ProjectException;

	public void deleteProjectCustomerInfo(String projectCode, int[] seq) throws ProjectException;
}
