package kr.co.kmac.pms.project.endprocess.manager.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.endprocess.dao.ProjectRollingDao;
import kr.co.kmac.pms.project.endprocess.data.EndProcessCheck;
import kr.co.kmac.pms.project.endprocess.form.ProjectRollingForm;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectRollingManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender; // JobDate: 2012-01-10	Author: yhyim Description: 고객만족도결과 이메일 전송을 위해 추가

public class ProjectRollingManagerImpl implements ProjectRollingManager {

	private static final Log logger = LogFactory.getLog(SanctionDocManager.class);
	private ProjectRollingDao projectRollingDao;
	private WorklistManager worklistManager;
	private ProjectEndTaskAssignManager projectEndTaskAssignManager;
	private PmsInfoMailSender pmsInfoMailSender; // JobDate: 2012-01-10	Author: yhyim Description: 고객만족도결과 이메일 전송을 위해 추가

	public void insertRollingC(String projectCode) throws ProjectException {
		//this.getWorklistManager().executeWork(projectRollingForm.getTaskId());
		//this.getProjectRollingDao().insertRollingC(projectRollingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectCode);
		endProcessCheck.setTaskId("VOC");
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectCode)) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
		}

	}

	public void insertRollingR(ProjectRollingForm projectRollingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectRollingForm.getTaskId());
		this.getProjectRollingDao().insertRollingR(projectRollingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectRollingForm.getProjectCode());
		endProcessCheck.setTaskId(projectRollingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectRollingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectRollingForm.getProjectCode());
		}
	}

	public void insertRollingE(ProjectRollingForm projectRollingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectRollingForm.getTaskId());
		// 온라인 만족도 평가로 대체
		//this.getProjectRollingDao().insertRollingE(projectRollingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectRollingForm.getProjectCode());
		endProcessCheck.setTaskId(projectRollingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectRollingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectRollingForm.getProjectCode());
		}
	}

	public void insertRollingF(ProjectRollingForm projectRollingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectRollingForm.getTaskId());
		this.getProjectRollingDao().insertRollingF(projectRollingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectRollingForm.getProjectCode());
		endProcessCheck.setTaskId(projectRollingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectRollingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectRollingForm.getProjectCode());
		}
	}

	public void insertRollingT(ProjectRollingForm projectRollingForm) throws ProjectException {
		this.getWorklistManager().executeWork(projectRollingForm.getTaskId());
		this.getProjectRollingDao().insertRollingT(projectRollingForm);

		EndProcessCheck endProcessCheck = new EndProcessCheck();
		endProcessCheck.setProjectCode(projectRollingForm.getProjectCode());
		endProcessCheck.setTaskId(projectRollingForm.getTaskId());
		endProcessCheck.setIsExecuted("Y");
		this.getProjectEndTaskAssignManager().store(endProcessCheck);
		if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectRollingForm.getProjectCode())) {
			this.getProjectEndTaskAssignManager().assignEndProcessTask(projectRollingForm.getProjectCode());
		}
	}

	@Override
	public Map isFinishedRolling(String projectCode, String seq) throws ProjectException {
		if (seq.equals("")) {
			return this.projectRollingDao.isFinishedRolling(projectCode);
		} else {
			return this.projectRollingDao.isFinishedRolling(projectCode, seq);
		}
	}	

	@Override
	public int numOfCustomer(String projectCode) throws ProjectException {
		return this.projectRollingDao.numOfCustomer(projectCode);
	}

	public ProjectRollingDao getProjectRollingDao() {
		return this.projectRollingDao;
	}

	public void setProjectRollingDao(ProjectRollingDao projectRollingDao) {
		this.projectRollingDao = projectRollingDao;
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
	
	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}
}
