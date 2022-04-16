package kr.co.kmac.pms.project.wreport.manager.impl;

import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.data.WorkConstants;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.wreport.dao.WeeklyReportDao;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import kr.co.kmac.pms.project.wreport.manager.WeeklyReportManager;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;

public class WeeklyReportManagerImpl implements WeeklyReportManager {

	private WeeklyReportDao weeklyReportDao;
	private ProjectDao projectDao;
	private WorklistManager worklistManager; 
	

	@Override
	public List<WeeklyReport> getWeeklyReport(String projectCode) throws ProjectException {
		return this.getWeeklyReportDao().select(projectCode);
	}
	
	@Override
	public List<ProjectProgressEntity> getWeeklyActivity(String projectCode) throws ProjectException {
		return this.getWeeklyReportDao().selectActivity(projectCode);
	}

	@Override
	public WeeklyReport getWeeklyReport(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws ProjectException {
		return this.getWeeklyReportDao().select(projectCode, assignYear, assignMonth, assignWeekOfMonth);
	}

	@Override
	public void storeWeeklyReport(WeeklyReport weeklyReport) throws ProjectException {
		this.getWeeklyReportDao().update(weeklyReport);
	}

	@Override
	public void deleteWeeklyReport(String projectCode) throws ProjectException {
		 this.getWeeklyReportDao().delete(projectCode);	
	}

	@Override
	public void deleteWeeklyReport(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws ProjectException {
		 this.getWeeklyReportDao().delete(projectCode, assignYear, assignMonth, assignWeekOfMonth);
	}

	@Override
	public void entrustWeeklyReport(WeeklyReport weeklyReport) throws ProjectException {
		Work work = this.getWorklistManager().getWork(weeklyReport.getWorkId());
		work.setAssigneeId(weeklyReport.getEntrustUserSsn());
		this.getWorklistManager().entrustWork(work);
	}

	@Override
	public void executeProjectReport(WeeklyReport weeklyReport) throws ProjectException {
		try {
			String assigneeId = null;
			String taskTypeId = null;
			String level = null;
			Work work = this.getWorklistManager().getWork(weeklyReport.getWorkId());
			// 반려 여부 체크
			if (weeklyReport.getAppYN() != null && weeklyReport.getAppYN().equals("N")) {
				weeklyReport.setWriteDate("");
				weeklyReport.setRevieweDate("");
				weeklyReport.setApproveDate("");
				assigneeId = weeklyReport.getWriterSsn();
				weeklyReport.setState("reject");
				this.getWorklistManager().rejectWork(work.getId());
			} else {
				// 승인일 경우
				if (weeklyReport.getState().equals("writer") || weeklyReport.getState().equals("reject")) {
					weeklyReport.setWriteDate(DateTime.getDateString(""));
					if (weeklyReport.getReviewerSsn() != null && !weeklyReport.getReviewerSsn().equals("")) {
						assigneeId = weeklyReport.getReviewerSsn();
						weeklyReport.setState("reviewer");
					} else {
						assigneeId = weeklyReport.getApproverSsn();
						weeklyReport.setState("approver");
					}
				} else if (weeklyReport.getState().equals("reviewer")) {
					weeklyReport.setRevieweDate(DateTime.getDateString(""));
					assigneeId = weeklyReport.getApproverSsn();
					weeklyReport.setState("approver");
				} else if (weeklyReport.getState().equals("approver")) {
					weeklyReport.setApproveDate(DateTime.getDateString(""));
					assigneeId = weeklyReport.getApproverSsn();
					weeklyReport.setState("complete");
				} else {
					weeklyReport.setApproveDate(DateTime.getDateString(""));
					assigneeId = weeklyReport.getApproverSsn();
					weeklyReport.setState("complete");
				}
				this.getWorklistManager().executeWork(work.getId());
			}
			this.storeWeeklyReport(weeklyReport);

			// 업무를 할당한다.
			if (weeklyReport.getState().equals("writer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2226";
				level = SanctionConstants.SANCTION_STATE_DRAFT;
			} else if (weeklyReport.getState().equals("reject")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2227";
				level = SanctionConstants.SANCTION_STATE_REJECT_DRAFT;
			} else if (weeklyReport.getState().equals("reviewer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2228";
				level = SanctionConstants.SANCTION_STATE_REVIEW;
			} else if (weeklyReport.getState().equals("approver")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2229";
				level = SanctionConstants.SANCTION_STATE_APPROVE;
			}
			
			// Job Date: 2010-05-11 Author: yhyim Description: 지도일지가 승인되면 업무가 종료되도록 조건절을 수정함(complete 부분 추가)
			if ((assigneeId != null && !assigneeId.trim().equals("")) && !weeklyReport.getState().equals("complete")) {
				Work assigneeWork = this.getWorklistManager().getWorkTemplate(taskTypeId, weeklyReport.getWriterSsn(), assigneeId,
						weeklyReport.getProjectCode(), weeklyReport.getTaskFormTypeId(), weeklyReport.getSeq());
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(DateUtil.getDateTime("yyyyMMdd", weeklyReport.getWriteDate()));
				assigneeWork.setDraftUserId(weeklyReport.getWriterSsn());
				assigneeWork.setLevel(level);
				assigneeWork.setState(WorkConstants.WORK_STATE_READY);
				assigneeWork.setTitle(this.getWorklistManager().getWorkType(taskTypeId).getName());

				this.getWorklistManager().assignWork(assigneeWork);
				System.out.println(assigneeWork.toString());
			}
			
			System.out.println(work.toString());
		} catch (SanctionException e) {
			e.printStackTrace();
			throw new SanctionException();
		}
		
	}
	
	//////////////////////////////////////////


	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public WeeklyReportDao getWeeklyReportDao() {
		return weeklyReportDao;
	}

	public void setWeeklyReportDao(WeeklyReportDao weeklyReportDao) {
		this.weeklyReportDao = weeklyReportDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
