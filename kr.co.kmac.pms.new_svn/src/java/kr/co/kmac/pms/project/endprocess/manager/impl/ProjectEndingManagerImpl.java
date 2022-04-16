package kr.co.kmac.pms.project.endprocess.manager.impl;

import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.endprocess.dao.ProjectEndingDao;
import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;
import kr.co.kmac.pms.project.endprocess.form.ProjectEndingForm;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndingManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;

public class ProjectEndingManagerImpl implements ProjectEndingManager {

	private ProjectEndingDao projectEndingDao;
	private WorklistManager worklistManager;
	private ProjectEndTaskAssignManager projectEndTaskAssignManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectDao projectDao;

	@Override
	public void insertRateC(ProjectEndingForm projectEndingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectEndingForm.getTaskId());
		this.getProjectEndingDao().insertRateC(projectEndingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectEndingForm.getProjectCode());
		endProcessCheck.setTaskId(projectEndingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectEndingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectEndingForm.getProjectCode());
		}
	}

	@Override
	public void insertRateP(ProjectEndingForm projectEndingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectEndingForm.getTaskId());
		this.getProjectEndingDao().insertRateP(projectEndingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectEndingForm.getProjectCode());
		endProcessCheck.setTaskId(projectEndingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectEndingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectEndingForm.getProjectCode());
		}
	}

	@Override
	public void insertRateE(ProjectEndingForm projectEndingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectEndingForm.getTaskId());
		this.getProjectEndingDao().insertRateE(projectEndingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectEndingForm.getProjectCode());
		endProcessCheck.setTaskId(projectEndingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectEndingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectEndingForm.getProjectCode());
		}
	}

	@Override
	public void insertRateCustomer(ProjectEndingForm projectEndingForm)
			throws ProjectException {
		this.getWorklistManager().executeWork(projectEndingForm.getTaskId());
		//this.getProjectEndingDao().insertRateE(projectEndingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectEndingForm.getProjectCode());
		endProcessCheck.setTaskId(projectEndingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectEndingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectEndingForm.getProjectCode());
		}
	}

	@Override
	public void insertEnding(ProjectEndingForm projectEndingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectEndingForm.getWorkId());
		if (getProjectEndingDao().updateProjectState(projectEndingForm.getProjectCode()) > 0) {
			getProjectEndingDao().insertEnding(projectEndingForm);

			EndProcessCheck endProcessCheck = new EndProcessCheck();
			endProcessCheck.setProjectCode(projectEndingForm.getProjectCode());
			endProcessCheck.setTaskId(projectEndingForm.getTaskId());
			endProcessCheck.setIsExecuted("Y");
			this.getProjectEndTaskAssignManager().store(endProcessCheck);
		}

		Project project = this.getProjectMasterInfoManager().getProject(projectEndingForm.getProjectCode());

		//��ü ������Ʈ�� �濵��ȹ�� ������ ���� �ʿ�� �ش� ���� ���� 20180124 -jiwoong
		if (projectEndingForm.getApproveYn().equals("Y")) {// ----------------------->������ �ۼ� ������ ���
			if (projectEndingForm.getEndTaskState().startsWith("next:fin")) {
				//��ü ������Ʈ�� �濵��ȹ�� ������ ���� �ʿ�� �ش� ���� ����, 20180124 -jiwoong
				this.getProjectMasterInfoManager().updateProjectState(projectEndingForm.getProjectCode(), "5");
				getProjectEndTaskAssignManager().assignEndProcessTaskForKM(projectEndingForm.getProjectCode());
				getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:complete");
			} else if (projectEndingForm.getEndTaskState().startsWith("next:approve")) {
				//����������� ������/���ܸ� �����ϹǷ� �̵�� ���� ���� ����, 20180125 -yhyim
				this.getProjectMasterInfoManager().updateProjectState(projectEndingForm.getProjectCode(), "5");
				getProjectEndTaskAssignManager().assignEndProcessTaskForCOO(projectEndingForm.getProjectCode());
				getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:fin");
			} else if (projectEndingForm.getEndTaskState().startsWith("next:review-1")) {
				this.getProjectMasterInfoManager().updateProjectState(projectEndingForm.getProjectCode(), "5");
				//������,������ PM���� ���� �Ҵ� , 20180124 -jiwoong
				if (project.getBusinessTypeCode().equals("BTA") || project.getBusinessTypeCode().equals("BTJ")) {	
					ProjectSimpleInfo projectSimpleInfo = getProjectDao().getProjectSimpleInfo(projectEndingForm.getProjectCode());
					if(projectSimpleInfo.getPmSsn().equals(projectSimpleInfo.getPlSsn())){//pl, pm�� ������ ��� �ٷ� coo�� 
						getProjectEndTaskAssignManager().assignEndProcessTaskForCOO(projectEndingForm.getProjectCode());
						getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:fin");						
					} else {						
						getProjectEndTaskAssignManager().assignEndProcessTaskForMReview(projectEndingForm.getProjectCode());
						getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:approve");
					}
				} else {
					//��Ÿ ������ PMb�ۼ� �� �߰� ���� ���� coo���� �Ҵ� , 20180124 -jiwoong
					getProjectEndTaskAssignManager().assignEndProcessTaskForCOO(projectEndingForm.getProjectCode());
					getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:fin");
				}
				// Job Script: �系������ ��� ������Ʈ �о� ������ ������ Job Date: 2014-04-09	Author: yhyim
				if (project.getProcessTypeCode().equals("N4")) {
					getProjectDao().updateBusinessFunctionType(projectEndingForm.getProjectCode(), projectEndingForm.getBizFunction());
				}
			}
			
 			if (!projectEndingForm.getKdbYn().equals("")) {	// KDB �̰����� ó��
				getProjectDao().updateKdbState(projectEndingForm.getProjectCode(), projectEndingForm.getKdbYn());
			}
		} else {// ----------------------->������ �ۼ� �ݷ��� ���
			this.getProjectMasterInfoManager().updateProjectState(projectEndingForm.getProjectCode(), "5");
			getProjectDao().updateProjectEndTaskState(projectEndingForm.getProjectCode(), "next:review-1-init");
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectEndingForm.getProjectCode());
		}

	}
	
	@Override
	public void entrustEnding(ProjectEndingForm projectEndingForm) throws ProjectException {
		Work work = this.getWorklistManager().getWork(projectEndingForm.getWorkId());
		
		work.setAssigneeId(projectEndingForm.getEntrustUserSsn());
		this.getWorklistManager().entrustWork(work);
	}

	public ProjectEndingDao getProjectEndingDao() {
		return this.projectEndingDao;
	}

	public void setProjectEndingDao(ProjectEndingDao projectEndingDao) {
		this.projectEndingDao = projectEndingDao;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ProjectEndTaskAssignManager getProjectEndTaskAssignManager() {
		return projectEndTaskAssignManager;
	}

	public void setProjectEndTaskAssignManager(ProjectEndTaskAssignManager projectEndTaskAssignManager) {
		this.projectEndTaskAssignManager = projectEndTaskAssignManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
