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
 * TODO Ŭ���� ����
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

		if (businessTypeCode.equals("BTA") || businessTypeCode.equals("BTJ")) { // ������ �� ����
			if (project.getIsEvaluate().equals("N")) { // ����
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
			} else {
				// ���������� (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingCustomerSatisfactionTask(projectCode);	// �¶��� ��
				// MEMBER�� �� (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingMemberValuationTask(projectCode);
				// PL�� �� (UPDATE 2021.10.06)
				this.getProjectEndTaskAssignManager().assignConsultingPLValuationTask(projectCode);
				// PARTPL�� �� (INSERT 2021.10.06)
				//this.getProjectEndTaskAssignManager().assignConsultingPartPLValuationTask(projectCode);
			}

		} else if (businessTypeCode.equals("BTD")) { // ����ġ
			if (project.getIsEvaluate().equals("N")) { // ����
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
			} else {
				this.getProjectEndTaskAssignManager().assignResearchCustomerSatisfactionTask(projectCode); // ����������
			}

		} else if (businessTypeCode.equals("BTE")) { // ���� �� ��������
			if (project.getProcessTypeCode().endsWith("N1")) {
				// ���������� ��������
				// �ܱ���������� �� ��󿡼� ���ܽ�Ŵ(2015-01-19)
				this.getProjectEndTaskAssignManager().assignPublicEduCustomerSatisfactionTask(projectCode);
			} else if (project.getProcessTypeCode().endsWith("N4")) {
				// �系����
				this.getProjectEndTaskAssignManager().assignEduOnlineSatisfactionTask(projectCode);					
			} else if (project.getProcessTypeCode().endsWith("SS")) {
				// Ư�� �������� -- 2014�� 2��, Ư�� �߰�
				this.getProjectEndTaskAssignManager().assignSpLectureOnlineSatisfactionTask(projectCode);					
			} else if (project.getProcessTypeCode().endsWith("DE")) {
				// ���� �������� ��������
				this.getProjectEndTaskAssignManager().assignTrainingOnlineSatisfactionTask(projectCode);
			} else if (project.getProcessTypeCode().endsWith("DD")) {
				// ��ȹ �������� ��������
				this.getProjectEndTaskAssignManager().assignTrainingSatisfactionTask(projectCode);
			} else {
				// �� �ܴ� �򰡻��� ����
				this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);				
			}

		} else if (businessTypeCode.equals("BTF")) { // �ؿܿ���
			if (project.getProcessTypeCode().endsWith("DE")) {
				// ���� �ؿܿ���
				this.getProjectEndTaskAssignManager().assignTrainingOnlineSatisfactionTask(projectCode);
			} else {
				// ��ȹ �ؿܿ���
				this.getProjectEndTaskAssignManager().assignTrainingSatisfactionTask(projectCode);
			}
		} else if (businessTypeCode.equals("BTK")) {	// ��������
			// �������(�� �� ���� ����)
			this.getProjectMasterInfoManager().updateProjectState(projectCode, "6");
		} else {
			this.getProjectMasterInfoManager().updateProjectState(projectCode, "5");
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
		}
	}
	
	public void doResendCSMail(String projectCode) throws ProjectException {
		this.getProjectEndTaskAssignManager().sendCSMail(projectCode); // ����ġ ���������� ��߼�
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
