/*
 * $Id: ProjectReportScheduleService.java,v 1.10 2018/01/17 01:15:11 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 8.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDao;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.sanction.preport.manager.ProjectReportManager;
import kr.co.kmac.pms.service.scheduler.dao.ProjectReportScheduleServiceDao;
import kr.co.kmac.pms.service.scheduler.data.ProjectReportScheduleData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleService.java,v 1.10 2018/01/17 01:15:11 cvs Exp $
 */
public class ProjectReportScheduleService {
	private static final Log log = LogFactory.getLog(ProjectReportScheduleService.class);
	private ProjectReportScheduleServiceDao projectReportScheduleServiceDao;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectReportManager projectReportManager;
	private ProjectReportDao projectReportDao;
	private WorklistManager worklistManager;
	private ExpertPoolDao expertPoolDao;

	// properties and collaborators
	public void assignProjectReportUntilToday(String projectCode, String assigneeSsn) {
		List<ProjectReportScheduleData> scheduleList = this.getProjectReportScheduleServiceDao().getScheduledAllListFromJulyUtilToday(projectCode,
				assigneeSsn);
		this.assignTask(scheduleList);
	}

	public void assignProjectReportUntilApproveDate(String projectCode, String approveDate) {
		List<ProjectReportScheduleData> scheduleList = this.getProjectReportScheduleServiceDao().getScheduledAllListFromJulyUtilApproveDate(
				projectCode, approveDate);
		this.assignTask(scheduleList);
	}

	public void assignProjectReportUntilToday(String projectCode) {
		List<ProjectReportScheduleData> scheduleList = this.getProjectReportScheduleServiceDao().getScheduledAllListFromJulyUtilToday(projectCode);
		this.assignTask(scheduleList);
	}

	public void assignProjectReportUntilToday() {
		List<ProjectReportScheduleData> scheduleList = this.getProjectReportScheduleServiceDao().getScheduledAllListFromJulyUtilToday();
		this.assignTask(scheduleList);
	}

	public String assignProjectReportUntilGivenDate(String yyyymmdd) {
		List<ProjectReportScheduleData> scheduleList = this.getProjectReportScheduleServiceDao().getScheduledAllListFromJulyUtilGivenDate(yyyymmdd);
		return this.assignTask(scheduleList);
	}

	private String assignTask(List<ProjectReportScheduleData> scheduleList) {
		String resultStr = "";
		if (scheduleList != null && scheduleList.size() > 0) {
			for (ProjectReportScheduleData projectReportScheduleData : scheduleList) {
				ProjectSimpleInfo projectInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectReportScheduleData.getProjectCode());
				ProjectReportContent projectReportContent = new ProjectReportContent();
				projectReportContent.setProjectCode(projectReportScheduleData.getProjectCode());
				projectReportContent.setTaskFormTypeId(projectReportScheduleData.getTaskFormTypeId());
				projectReportContent.setSeq(getProjectReportSeqNo(projectReportScheduleData.getProjectCode(),
						projectReportScheduleData.getTaskFormTypeId()));
				projectReportContent.setAssignDate(projectReportScheduleData.getYear() + projectReportScheduleData.getMonth()
						+ projectReportScheduleData.getDay());
				projectReportContent.setDueDate("");
				projectReportContent.setWriterSsn(projectReportScheduleData.getChargeSsn());

				// 컨설팅 : 팀원 작성 -> PL 검토 -> AR 승인 (작성자가 PL인 경우 검토 생략, PL와  AR이 동일인 경우 검토 생략)
				if (projectReportScheduleData.getBusinessTypeCode().equals("BTA") || projectReportScheduleData.getBusinessTypeCode().equals("BTJ")) {
					// ------------------------------------------------------------------------------------------------------------------------------------------------------
					if (projectReportScheduleData.getChargeSsn().equals(projectInfo.getPlSsn()) 
							|| projectInfo.getArSsn().equals(projectInfo.getPlSsn())) {
						projectReportContent.setReviewerSsn("");
						projectReportContent.setApproverSsn(projectInfo.getArSsn());
					} else {
						projectReportContent.setReviewerSsn(projectInfo.getPlSsn());
						projectReportContent.setApproverSsn(projectInfo.getArSsn());
					}
				} else {
					// 컨설팅 외 프로젝트는 작성 -> 승인
					// ------------------------------------------------------------------------------------------------------------------------------------------------------
					projectReportContent.setReviewerSsn("");
					projectReportContent.setApproverSsn(projectInfo.getArSsn());
					// ------------------------------------------------------------------------------------------------------------------------------------------------------
				}

				projectReportContent.setState("writer");
				getProjectReportManager().createProjectReport(projectReportContent);

				Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a20e689010a2b4be76c0046", "PMS",
						projectReportContent.getWriterSsn(), projectReportContent.getProjectCode(), projectReportContent.getTaskFormTypeId(),
						projectReportContent.getSeq());
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(DateUtil.getDateTime("yyyyMMdd", projectReportContent.getAssignDate()));
				assigneeWork.setDraftDate(new Date());
				assigneeWork.setDraftUserId(projectReportContent.getWriterSsn());
				// assigneeWork.setDraftUserDept(projectReportContent.getWriterSsn());
				assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_DRAFT);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(this.getWorklistManager().getWorkType("4028809e0a20e689010a2b4be76c0046").getName());

				if (this.getWorklistManager().assignWork(assigneeWork)) {
					resultStr += "[" + projectReportScheduleData.getChargeSsn() + " <-- " + projectInfo.getProjectName() + "||"
							+ projectInfo.getProjectCode() + "] projectReport Task was assigned.<br>";
				} else {
					resultStr += "[" + projectReportScheduleData.getChargeSsn() + " <-- " + projectInfo.getProjectName() + "||"
							+ projectInfo.getProjectCode() + "] projectReport Task was not created. something was wrong.<br>";
				}

				resultStr += "## ProjectReport Scheduler was executed. <br>";
			}

		}
		resultStr += "## ProjectReport Scheduler was finished. <br>";
		return resultStr;
	}

	private String getProjectReportSeqNo(String projectCode, String taskFormTypeId) {
		return getProjectReportDao().getSeqNo(projectCode, taskFormTypeId);
	}

	public ProjectReportScheduleServiceDao getProjectReportScheduleServiceDao() {
		return projectReportScheduleServiceDao;
	}

	public void setProjectReportScheduleServiceDao(ProjectReportScheduleServiceDao projectReportScheduleServiceDao) {
		this.projectReportScheduleServiceDao = projectReportScheduleServiceDao;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProjectReportManager getProjectReportManager() {
		return projectReportManager;
	}

	public void setProjectReportManager(ProjectReportManager projectReportManager) {
		this.projectReportManager = projectReportManager;
	}

	public ProjectReportDao getProjectReportDao() {
		return projectReportDao;
	}

	public void setProjectReportDao(ProjectReportDao projectReportDao) {
		this.projectReportDao = projectReportDao;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ExpertPoolDao getExpertPoolDao() {
		return expertPoolDao;
	}

	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}

}
