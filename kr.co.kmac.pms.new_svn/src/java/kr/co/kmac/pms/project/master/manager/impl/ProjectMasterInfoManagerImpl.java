package kr.co.kmac.pms.project.master.manager.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ErpProjectDao;
import kr.co.kmac.pms.project.master.dao.ErpProjectMemberDao;
import kr.co.kmac.pms.project.master.dao.ProjectCsrInfoDao;
import kr.co.kmac.pms.project.master.dao.ProjectCustomerInfoDao;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectBudget;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.data.ProjectCustomerInfo;
import kr.co.kmac.pms.project.master.data.ProjectDelayInfo;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.master.manager.ProjectOrgdbManager;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;
import kr.co.kmac.pms.project.progress.manager.ProjectProgressManager;
import kr.co.kmac.pms.project.voc.dao.ProjectVocDao;
import kr.co.kmac.pms.sanction.general.dao.SanctionDocDao;

public class ProjectMasterInfoManagerImpl implements ProjectMasterInfoManager {

	private ProjectDao projectDao;
	private ProjectMemberDao projectMemberDao;
	private ErpProjectDao erpProjectDao;
	private ErpProjectMemberDao erpProjectMemberDao;
	private ExpertPoolManager expertPoolManager;
	private ProjectReportInfoManager projectReportInfoManager;
	private ProjectOrgdbManager projectOrgdbManager;
	private ProjectProgressManager projectProgressManager;
	private ProjectVocDao projectVocDao;
	private ProjectCsrInfoDao projectCsrInfoDao;
	private ProjectCustomerInfoDao projectCustomerInfoDao;
	private SanctionDocDao sanctionDocDao;
	private AttachManager attachManager;
	
	@Override
	public String getProjectBusinessTypeCode(String projectCode) throws ProjectException {
		return this.getProject(projectCode).getBusinessTypeCode();
	}

	@Override
	public void createProject(Project project) throws ProjectException {
		this.deleteProject(project.getProjectCode());
		this.getProjectDao().insert(project); 
		// 프로젝트 멤버 세팅 
		List<ProjectMember> prjectMembers = project.getProjectMemberList();
		if (!prjectMembers.isEmpty()) {
			this.getProjectMemberDao().delete(project.getProjectCode());
			for (ProjectMember projectMember : prjectMembers) {
				this.projectMemberDao.insert(projectMember);
			}
		}
		// 프로젝트 고객사 세팅(컨설팅, 진단, 리서치, 장/단기공개교육, 사내교육, 특강, 수주연수 일 때만 저장함)
		if (project.getBusinessTypeCode().equals("BTA") || project.getBusinessTypeCode().equals("BTJ") || project.getBusinessTypeCode().equals("BTD") 
				|| project.getProcessTypeCode().equals("N1") || project.getProcessTypeCode().equals("N2") || project.getProcessTypeCode().equals("N4") 
				|| project.getProcessTypeCode().equals("SS") || project.getProcessTypeCode().equals("DE")) {
			List<ProjectCsrInfo> projectCsrInfos = project.getProjectCsrInfoList();
			if (!projectCsrInfos.isEmpty()) {
				this.getProjectCsrInfoDao().deleteRepCustomer(project.getProjectCode());
				for (ProjectCsrInfo projectCsrInfo : projectCsrInfos) {
					projectCsrInfo.setIsRep("Y");
					projectCsrInfo.setCustomerName(project.getCustomerName());
					projectCsrInfo.setCustomerCode(project.getCustomerCode());
					projectCsrInfo.setIsEvaluatel(project.getIsEvaluate());
					projectCsrInfo.setIsVoc(project.getIsVoc());
					this.getProjectCsrInfoDao().insert(projectCsrInfo);
				}
			}
		}
	}

	@Override
	public void createProjectMember(String projectCode, List<ProjectMember> projectMemberList) throws ProjectException {
		if (!projectMemberList.isEmpty()) {
			this.getProjectMemberDao().delete(projectCode);
			for (ProjectMember projectMember : projectMemberList) {
				this.projectMemberDao.insert(projectMember);
			}
		}
	}
	
	@Override
	public void createProjectMemberMMInfo(String projectCode) throws ProjectException {
		try {
			//	JobDate: 2019-02-05	Author: yhyim	Description: 프로젝트 MM 정보 생성
			String entNo = this.getErpProjectDao().selectEntNoForUpdate(projectCode);
			this.getProjectMemberDao().insertMMInfo(entNo);
		} catch (Exception e) {
			// do nothing
		}
	}

	@Override
	public void deleteProject(String projectCode) throws ProjectException {
		// 프로젝트 상태가 등록, 품의 일 때만 삭제할 수 있게 수정
		// 프로젝트 시작품의 완료 이후 반려 업무가 남아 있을 때 업무 삭제 시 프로젝트도 같이 삭제되는 현상을 방지하기 위함
		try {
			if (Integer.parseInt(this.getProjectSimpleInfo(projectCode).getProjectState()) < 3) {
				this.getProjectReportInfoManager().deleteProjectReportInfo(projectCode);
				this.getProjectOrgdbManager().deleteProjectOrgdb(projectCode);
				this.getProjectProgressManager().deleteProjectProgress(projectCode);
				this.getProjectVocDao().deleteProjectVoc(projectCode);
				this.getProjectMemberDao().delete(projectCode);
				this.getProjectDao().delete(projectCode);
				this.getAttachManager().deleteAllInProject(projectCode);
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	@Override
	public void deleteProjectMember(String projectCode, String ssn) throws ProjectException {
		this.getProjectMemberDao().delete(projectCode, ssn);
	}

	@Override
	public void deleteProjectMember(String projectCode) throws ProjectException {
		this.getProjectMemberDao().delete(projectCode);
	}

	@Override
	public Project getProject(String projectCode) throws ProjectException {
		Project project = this.getProjectDao().select(projectCode);
		List<ProjectMember> projectMemberList = this.getProjectMemberDao().select(projectCode);
		if (project != null) {
			project.setProjectMemberList(projectMemberList);
			projectCsrInfoDao.getRepCustomer(projectCode);
		}
		return project;
	}

	@Override
	public ProjectSimpleInfo getProjectSimpleInfo(String projectCode) throws ProjectException {
		return this.getProjectDao().getProjectSimpleInfo(projectCode);
	}

	@Override
	public Project getErpProject(String projectCode) throws ProjectException {
		Project project = this.getErpProjectDao().select(projectCode);
		List<ProjectMember> projectMemberList = this.getErpProjectMemberDao().select(project.getEntNo());
		List<ProjectMember> list = new ArrayList<ProjectMember>();
		if (project != null) {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUserObjext().getId());
			ProjectMember pm = new ProjectMember();
			pm.setRole("PM");
			pm.setProjectCode(projectCode);
			pm.setSsn(expertPool.getSsn());
			pm.setName(expertPool.getName());
			// pm.setPosition((expertPool.getCompanyPosition() == null || expertPool.getCompanyPosition().equals("") ? expertPool
			// .getCompanyPositionName() : expertPool.getCompanyPositionName()));
			pm.setPosition(expertPool.getCompanyPosition());
			pm.setTrainingYn("Y");
			pm.setResRate("1");
			pm.setCost("0");
			pm.setContributionCost("0");
			pm.setJobClass(expertPool.getJobClass());
			list.add(pm);
			
			// Job Date: 2009-12-24 Author: yhyim Description: Adding AR
			// Job Date: 2018-01-02 Author: yhyim Description: COO로 대체
			expertPool = this.getExpertPoolManager().retrieveOfficer(project.getRunningDeptCode());
			ProjectMember ar = new ProjectMember();
			ar.setRole("AR");
			ar.setProjectCode(projectCode);
			ar.setSsn(expertPool.getSsn());
			ar.setName(expertPool.getName());
			ar.setPosition(expertPool.getCompanyPosition());
			ar.setTrainingYn("Y");
			ar.setResRate("1");
			ar.setCost("0");
			ar.setContributionCost("0");
			ar.setJobClass(expertPool.getJobClass());
			list.add(ar);
			
			//QM 대형일 때는 CBO, 중형일때는 COO
			if(Long.valueOf(project.getSalesAmt()) >= 230000000){
				expertPool = this.getExpertPoolManager().retrieveOfficer2(project.getRunningDeptCode());
				ProjectMember qm = new ProjectMember();
				qm.setRole("QM");
				qm.setProjectCode(projectCode);
				qm.setSsn("");
				qm.setName("");
				qm.setPosition("");
				qm.setTrainingYn("Y");
				qm.setResRate("1");
				qm.setCost("0");
				qm.setContributionCost("0");
				qm.setJobClass("");
				list.add(qm);
			}else if(100000000 <= Long.valueOf(project.getSalesAmt()) && Integer.valueOf(project.getSalesAmt()) < 230000000){
				expertPool = this.getExpertPoolManager().retrieveOfficer(project.getRunningDeptCode());
				ProjectMember qm = new ProjectMember();
				qm.setRole("QM");
				qm.setProjectCode("");
				qm.setSsn("");
				qm.setName("");
				qm.setPosition("");
				qm.setTrainingYn("Y");
				qm.setResRate("1");
				qm.setCost("0");
				qm.setContributionCost("0");
				qm.setJobClass("");
				list.add(qm);
				
			}
		}
		if (!projectMemberList.isEmpty())
			list.addAll(projectMemberList);
		project.setProjectMemberList(list);
		return project;
	}
	
	@Override
	public Project getErpProject2(String projectCode) throws ProjectException {
		Project project = this.getErpProjectDao().select(projectCode);

		return project;
	}
	
	@Override
	public ProjectBudget getProjectBudget(String projectCode) throws ProjectException {
		ProjectBudget projectBudget = this.getErpProjectDao().selectBudget(projectCode);
		
		return projectBudget;
	}

	@Override
	public List<ProjectMember> getErpProjectMember(String projectCode) throws ProjectException {
		Project project = this.getErpProjectDao().select(projectCode);
		if (project != null) {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUserObjext().getSsn());
			ProjectMember pm = new ProjectMember();
			pm.setRole("PM");
			pm.setProjectCode(projectCode);
			pm.setSsn(expertPool.getSsn());
			pm.setName(expertPool.getName());
			pm.setPosition(expertPool.getCompanyPositionName());
			pm.setTrainingYn("Y");

			List<ProjectMember> list = new ArrayList<ProjectMember>();
			list.add(pm);
			list.addAll(this.getErpProjectMemberDao().select(project.getEntNo()));
			return list;
		}
		return new ArrayList<ProjectMember>();
	}

	@Override
	public List<ProjectMember> getProjectMember(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().select(projectCode);
	}
	
	@Override
	public List<Project> getProjectCodeInfo(String ssn) throws ProjectException {
		return this.getProjectMemberDao().selectProjectCode(ssn);
	}

	@Override
	public List<ProjectMember> getProjectAllMember(String projectCode) throws ProjectException {
		return this.getProjectMemberDao().selectAll(projectCode);
	}

	public ProjectMember getProjectMember(String projectCode, String ssn) throws ProjectException {
		return this.getProjectMemberDao().select(projectCode, ssn);
	}

	@Override
	public boolean isRegisteredProject(String projectCode) throws ProjectException {
		return this.getProjectDao().select(projectCode) != null;
	}

	@Override
	public boolean isConnectedProject(String projectCode) throws ProjectException {
		return this.getProjectDao().isConnectedProject(projectCode);
	}

	@Override
	public void updateProject(Project project) throws ProjectException {
		
		this.getProjectDao().update(project);

		// 프로젝트 멤버 세팅
		List<ProjectMember> projectMembsers = project.getProjectMemberList();
		this.getProjectMemberDao().delete(project.getProjectCode());
		if (!projectMembsers.isEmpty()) {
			for (ProjectMember projectMember : projectMembsers) {
				this.projectMemberDao.insert(projectMember);
			}
		}
		// 프로젝트 고객사 세팅(컨설팅, 진단, 리서치, 장/단기공개교육, 사내교육, 특강, 수주연수 일 때만 저장함)
		if (project.getBusinessTypeCode().equals("BTA") || project.getBusinessTypeCode().equals("BTJ") || project.getBusinessTypeCode().equals("BTD") 
				|| project.getProcessTypeCode().equals("N1") || project.getProcessTypeCode().equals("N2") || project.getProcessTypeCode().equals("N4")
				|| project.getProcessTypeCode().equals("SS") || project.getProcessTypeCode().equals("DE")) {
			List<ProjectCsrInfo> projectCsrInfos = project.getProjectCsrInfoList();
			if (!projectCsrInfos.isEmpty()) {
				this.getProjectCsrInfoDao().deleteRepCustomer(project.getProjectCode());
				for (ProjectCsrInfo projectCsrInfo : projectCsrInfos) {
					projectCsrInfo.setIsRep("Y");
					projectCsrInfo.setCustomerName(project.getCustomerName());
					projectCsrInfo.setCustomerCode(project.getCustomerCode());
					projectCsrInfo.setIsEvaluatel(project.getIsEvaluate());
					projectCsrInfo.setIsVoc(project.getIsVoc());
					this.getProjectCsrInfoDao().insert(projectCsrInfo);
				}
			}
		}
	}

	@Override
	public void updateProjectMember(String projectCode, List<ProjectMember> projectMemberList) throws ProjectException {
		if (!projectMemberList.isEmpty()) {
			this.getProjectMemberDao().delete(projectCode);
			for (ProjectMember projectMember : projectMemberList) {
				this.projectMemberDao.insert(projectMember);
			}
		}
	}
	
	@Override
	public void updateProjectMemberMMInfo(String projectCode, int seq) throws ProjectException {
		try {
			// 프로젝트의 기존 MM Plan 정보 삭제
			// 이번 달 이후의 MM Plan 정보만 삭제하도록 변경(2019-07-10)
			this.getProjectMemberDao().deleteMMInfoByTime(projectCode);
			String entNo = this.getErpProjectDao().getProjectRelatedEntNo(String.valueOf(seq)).getNewEntNo();
			// 프로젝트의 새 MM Plan 정보 입력
			// 이번 달 이후의 MM Plan 정보만 입력하도록 변경(2019-07-10)
			this.getProjectMemberDao().insertMMInfoByTime(entNo);
		} catch (Exception e) {
			// do nothing
		}
	}
	
	@Override
	public void updateProjectMemberMMInfoForMemberChange(String projectCode, int seq) throws ProjectException {
		try {
			// 프로젝트의 기존 MM Plan 정보 삭제
			// 이번 달 이후의 MM Plan 정보만 삭제하도록 변경(2019-07-10)
			this.getProjectMemberDao().deleteMMInfoByTime(projectCode);
			String entNo = this.getErpProjectDao().getProjectRelatedEntNoForMemberChange(String.valueOf(seq)).getNewEntNo();
			// 프로젝트의 새 MM Plan 정보 입력
			// 이번 달 이후의 MM Plan 정보만 입력하도록 변경(2019-07-10)
			this.getProjectMemberDao().insertMMInfoByTime(entNo);
		} catch (Exception e) {
			// do nothing
		}
	}	

	@Override
	public List<String> getEndProcessTaskList(String projectCode) throws ProjectException {
		return this.getProjectDao().getEndProcessTaskList(projectCode);
	}

	@Override
	public void updateProjectState(String projectCode, String state) throws ProjectException {
		this.getProjectDao().updateProjectState(projectCode, state);
	}

	@Override
	public void updateBudgetState(String projectCode) throws ProjectException {
		String entNo = this.getErpProjectDao().selectEntNoForUpdate(projectCode);
		this.getProjectDao().updateBudgetState(entNo);
	}

	@Override
	public void updateBudgetState(String projectCode, int seq) throws ProjectException {
		String entNo = this.getErpProjectDao().getProjectRelatedEntNo(String.valueOf(seq)).getNewEntNo();
		this.getProjectDao().updateBudgetState(entNo);
	}
	
	@Override
	public void updateBudgetStateForMemberChange(String projectCode, int seq) throws ProjectException {
		String entNo = this.getErpProjectDao().getProjectRelatedEntNoForMemberChange(String.valueOf(seq)).getNewEntNo();
		this.getProjectDao().updateBudgetState(entNo);
	}

	@Override
	public void sendBudgetState(String projectCode) throws ProjectException {
		String entNo = this.getErpProjectDao().selectEntNoForUpdate(projectCode);
		this.getProjectDao().sendBudgetState(entNo);
	}
	
	@Override
	public void sendBudgetState(String projectCode, int seq) throws ProjectException {
		String entNo = this.getErpProjectDao().getProjectRelatedEntNo(String.valueOf(seq)).getNewEntNo();
		this.getProjectDao().sendBudgetState(entNo);
	}

	@Override
	public boolean updateErpState(String projectCode) throws ProjectException {
		return this.getSanctionDocDao().updateErpState(projectCode);
	}

	@Override
	public void updateEduState(String projectCode) throws ProjectException {
		// 기획사업 ceo confirm 'Y' 상태로 변경
		this.getSanctionDocDao().updateEduState(projectCode);
		// 기획사업 날짜변경, 삭제 'N' 상태로 변경
		this.getSanctionDocDao().updateEduStateDisabled(projectCode);
	}

	@Override
	public void updateEduStateAgain(String oc_code) throws ProjectException {
		// 기획사업현황 업데이트(재품의)
		this.getSanctionDocDao().updateEduStateAgain(oc_code);
	}

	@Override
	public void updateEduStateCancle(String projectCode, String oc_code) throws ProjectException {
		// 기획사업현황 업데이트(취소)
		this.getSanctionDocDao().updateEduStateCancle(oc_code);
		if (!this.getProjectDao().getProjectSimpleInfo(projectCode).getProcessTypeCode().equals("N2"))
			this.updateProjectState(projectCode, "7");
	}
	
	@Override
	public void updateEduStateResanction(String oc_code) throws ProjectException {
		// 기획사업현황 업데이트(재품의 진행 중)
		this.getSanctionDocDao().updateEduStateResanction(oc_code);
	}
	
	@Override
	public void restoreEduStateResanction(String oc_code) throws ProjectException {
		// 기획사업현황 업데이트(재품의 정보 초기화)
		this.getSanctionDocDao().restoreEduStateResanction(oc_code);
	}	

	@Override
	public List<ProjectDelayInfo> getProjectDelayInfo(String projectCode, String delayType) throws ProjectException {
		return this.getProjectDao().getProjectDelayInfo(projectCode, delayType);
	}

	@Override
	public List<ProjectDelayInfo> getProjectTaskDelayInfo(String projectCode, String delayType) throws ProjectException {
		return this.getProjectDao().getProjectTaskDelayInfo(projectCode, delayType);
	}

	@Override
	public void updateAdminOpenState(String projectCode, String adminOpenYn) throws ProjectException {
		this.getProjectDao().updateAdminOpenState(projectCode, adminOpenYn);
	}

	@Override
	public String[] getProjectEntNo(String projectCode) throws ProjectException {
		String[] entNos = new String[2];
		List<String> entNoList = this.getErpProjectDao().selectEntNo(projectCode);
		for (int i = 0; i < entNoList.size(); i++) {
			if (i > 1)
				break;
			entNos[i] = entNoList.get(i);
		}
		return entNos;
	}
	
	@Override
	public String getProjectEntNoForMemberChange(String projectCode) throws ProjectException {
		return this.getErpProjectDao().selectMemberChangeEntNo(projectCode);
	}
	
	@Override
	public List<ProjectMember> getProjectMemberFromEntNo(String entNo) throws ProjectException {
		return this.getErpProjectDao().selectAllProjectMember(entNo);
	}

	@Override
	public ProjectRelatedEntNo getProjectRelatedEntNo(String seq) throws ProjectException {
		return this.getErpProjectDao().getProjectRelatedEntNo(seq);
	}

	@Override
	public void insertProjectRelatedEntNo(ProjectRelatedEntNo projectRelatedEntNo) throws ProjectException {
		this.getErpProjectDao().insertProjectRelatedEntNo(projectRelatedEntNo);
	}

	@Override
	public void deleteProjectRelatedEntNo(String seq) throws ProjectException {
		this.getErpProjectDao().deleteProjectRelatedEntNo(seq);
	}
	
	@Override
	public ProjectRelatedEntNo getProjectRelatedEntNoForMemberChange(String seq) throws ProjectException {
		return this.getErpProjectDao().getProjectRelatedEntNoForMemberChange(seq);
	}

	@Override
	public void insertProjectRelatedEntNoForMemberChange(ProjectRelatedEntNo projectRelatedEntNo) throws ProjectException {
		this.getErpProjectDao().insertProjectRelatedEntNoForMemberChange(projectRelatedEntNo);
	}

	@Override
	public void deleteProjectRelatedEntNoForMemberChange(int seq) throws ProjectException {
		this.getErpProjectDao().deleteProjectRelatedEntNoForMemberChange(String.valueOf(seq));
	}

	@Override
	public ProjectCsrInfo getProjectCsrInfo(String projectCode, int seq) throws ProjectException {
		return this.getProjectCsrInfoDao().select(projectCode, seq);
	}

	@Override
	public List<ProjectCsrInfo> getProjectCsrInfo(String projectCode) throws ProjectException {
		return this.getProjectCsrInfoDao().select(projectCode);
	}

	@Override
	public List<ProjectCsrInfo> getProjectAllCsrInfo(String projectCode) throws ProjectException {
		return this.getProjectCsrInfoDao().selectAll(projectCode);
	}

	@Override
	public void updateProjectCsrInfo(ProjectCsrInfo projectCsrInfo) throws ProjectException {
		this.getProjectCsrInfoDao().update(projectCsrInfo);
	}

	@Override
	public void insertProjectThxInfo(ProjectCsrInfo projectCsrInfo) throws ProjectException {
		this.getProjectCsrInfoDao().insertThxInfo(projectCsrInfo);
	}

	@Override
	public void createProjectCustomerInfo(String projectCode, List<ProjectCustomerInfo> projectCustomerInfos) throws ProjectException {
		this.getProjectCustomerInfoDao().delete(projectCode);
		this.getProjectCustomerInfoDao().insert(projectCustomerInfos);
	}

	@Override
	public void deleteProjectCustomerInfo(String projectCode, int[] seq) throws ProjectException {
		for (int i : seq) {
			this.getProjectCustomerInfoDao().delete(projectCode, i);
		}
	}

	@Override
	public List<ProjectCustomerInfo> getProjectCustomerInfo(String projectCode) throws ProjectException {
		return this.getProjectCustomerInfoDao().select(projectCode);
	}
	
	// ----------------------setter/getter------------------------
	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public ProjectMemberDao getProjectMemberDao() {
		return projectMemberDao;
	}

	public void setProjectMemberDao(ProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}

	public ErpProjectDao getErpProjectDao() {
		return erpProjectDao;
	}

	public void setErpProjectDao(ErpProjectDao erpProjectDao) {
		this.erpProjectDao = erpProjectDao;
	}

	public ErpProjectMemberDao getErpProjectMemberDao() {
		return erpProjectMemberDao;
	}

	public void setErpProjectMemberDao(ErpProjectMemberDao erpProjectMemberDao) {
		this.erpProjectMemberDao = erpProjectMemberDao;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public ProjectReportInfoManager getProjectReportInfoManager() {
		return projectReportInfoManager;
	}

	public void setProjectReportInfoManager(ProjectReportInfoManager projectReportInfoManager) {
		this.projectReportInfoManager = projectReportInfoManager;
	}
	
	public ProjectOrgdbManager getProjectOrgdbManager() {
		return projectOrgdbManager;
	}

	public void setProjectOrgdbManager(ProjectOrgdbManager projectOrgdbManager) {
		this.projectOrgdbManager = projectOrgdbManager;
	}

	public ProjectProgressManager getProjectProgressManager() {
		return projectProgressManager;
	}

	public void setProjectProgressManager(ProjectProgressManager projectProgressManager) {
		this.projectProgressManager = projectProgressManager;
	}

	public ProjectVocDao getProjectVocDao() {
		return projectVocDao;
	}

	public void setProjectVocDao(ProjectVocDao projectVocDao) {
		this.projectVocDao = projectVocDao;
	}

	public void setSanctionDocDao(SanctionDocDao sanctionDocDao) {
		this.sanctionDocDao = sanctionDocDao;
	}

	public SanctionDocDao getSanctionDocDao() {
		return sanctionDocDao;
	}

	public ProjectCsrInfoDao getProjectCsrInfoDao() {
		return projectCsrInfoDao;
	}

	public void setProjectCsrInfoDao(ProjectCsrInfoDao projectCsrInfoDao) {
		this.projectCsrInfoDao = projectCsrInfoDao;
	}

	public ProjectCustomerInfoDao getProjectCustomerInfoDao() {
		return projectCustomerInfoDao;
	}

	public void setProjectCustomerInfoDao(ProjectCustomerInfoDao projectCustomerInfoDao) {
		this.projectCustomerInfoDao = projectCustomerInfoDao;
	}
	
	public AttachManager getAttachManager() {
		return attachManager;
	}
	
	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}
}
