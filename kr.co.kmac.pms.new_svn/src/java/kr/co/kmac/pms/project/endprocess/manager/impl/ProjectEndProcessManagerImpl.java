/*
 * $Id: ProjectEndProcessManagerImpl.java,v 1.11 2015/02/17 00:08:50 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager.impl;

import kr.co.kmac.pms.project.endprocess.manager.ProjectEndProcessManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectEndProcessManagerImpl.java,v 1.11 2015/02/17 00:08:50 cvs Exp $
 */
public class ProjectEndProcessManagerImpl implements ProjectEndProcessManager {

	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectEndTaskAssignManager projectEndTaskAssignManager;

	public void doEndProcess(String projectCode) throws ProjectException {
		Project project = this.getProjectMasterInfoManager().getProject(projectCode);

		String businessTypeCode = project.getBusinessTypeCode();
		this.getProjectMasterInfoManager().updateProjectState(projectCode, "4");
		
		// By-Pass
		if("Y".equals(project.getAttach())){
			this.getProjectMasterInfoManager().updateProjectState(project.getParentProjectCode(), "6");
		} else { System.out.println("Not By-Pass 4"); }

		if (businessTypeCode.equals("BTA") || businessTypeCode.equals("BTJ")) { // 컨설팅 및 진단
			if (project.getIsEvaluate().equals("N")) { // 미평가
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
			} else {
				// 고객만족도평가 (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingCustomerSatisfactionTask(projectCode);	// 온라인 평가
				// MEMBER를 평가 (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingMemberValuationTask(projectCode);
				// PL을 평가 (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingPLValuationTask(projectCode);
				// PARTPL을 평가 (INSERT 2021.10.06)
				//this.getProjectEndTaskAssignManager().assignConsultingPartPLValuationTask(projectCode);
			}

		} else if (businessTypeCode.equals("BTD")) { // 리서치
			if (project.getIsEvaluate().equals("N")) { // 미평가
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
			} else {
				this.getProjectEndTaskAssignManager().assignResearchCustomerSatisfactionTask(projectCode); // 고객만족도평가
			}

		} else if (businessTypeCode.equals("BTE")) { // 교육 및 국내연수
			if (project.getProcessTypeCode().endsWith("N1")) {
				// 장기공개교육 고객만족도
				// 단기공개교육은 평가 대상에서 제외시킴(2015-01-19)
				this.getProjectEndTaskAssignManager().assignPublicEduCustomerSatisfactionTask(projectCode);
			} else if (project.getProcessTypeCode().endsWith("N4")) {
				// 사내교육
				this.getProjectEndTaskAssignManager().assignEduOnlineSatisfactionTask(projectCode);					
			} else if (project.getProcessTypeCode().endsWith("SS")) {
				// 특강 고객만족도 -- 2014년 2월, 특강 추가
				this.getProjectEndTaskAssignManager().assignSpLectureOnlineSatisfactionTask(projectCode);					
			} else if (project.getProcessTypeCode().endsWith("DE")) {
				// 수주 국내연수 고객만족도
				this.getProjectEndTaskAssignManager().assignTrainingOnlineSatisfactionTask(projectCode);
			} else if (project.getProcessTypeCode().endsWith("DD")) {
				// 기획 국내연수 고객만족도
				this.getProjectEndTaskAssignManager().assignTrainingSatisfactionTask(projectCode);
			} else {
				// 그 외는 평가생략 생략
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);				
			}

		} else if (businessTypeCode.equals("BTF")) { // 해외연수
			if (project.getProcessTypeCode().endsWith("DE")) {
				// 수주 해외연수
				this.getProjectEndTaskAssignManager().assignTrainingOnlineSatisfactionTask(projectCode);
			} else {
				// 기획 해외연수
				this.getProjectEndTaskAssignManager().assignTrainingSatisfactionTask(projectCode);
			}
		} else if (businessTypeCode.equals("BTK")) {	// 과제관리
			// 즉시종료(평가 및 리뷰 생략)
			this.getProjectMasterInfoManager().updateProjectState(projectCode, "6");
		} else {
			this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
		}
	}
	
	public void doResendCSMail(String projectCode) throws ProjectException {
		this.getProjectEndTaskAssignManager().sendCSMail(projectCode); // 리서치 고객만족도평가 재발송
	}

	/**
	 * @return the projectMasterInfoManager
	 */
	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	/**
	 * @param projectMasterInfoManager the projectMasterInfoManager to set
	 */
	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	/**
	 * @return the projectEndTaskAssignManager
	 */
	public ProjectEndTaskAssignManager getProjectEndTaskAssignManager() {
		return projectEndTaskAssignManager;
	}

	/**
	 * @param projectEndTaskAssignManager the projectEndTaskAssignManager to set
	 */
	public void setProjectEndTaskAssignManager(ProjectEndTaskAssignManager projectEndTaskAssignManager) {
		this.projectEndTaskAssignManager = projectEndTaskAssignManager;
	}

}
