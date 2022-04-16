package kr.co.kmac.pms.project.mreport.manager.impl;

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
import kr.co.kmac.pms.project.mreport.dao.MonthlyReportDao;
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;
import kr.co.kmac.pms.project.mreport.manager.MonthlyReportManager;
import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;

public class MonthlyReportManagerImpl implements MonthlyReportManager {

	private MonthlyReportDao monthlyReportDao;
	private ProjectDao projectDao;
	private WorklistManager worklistManager;

	@Override
	public List<MonthlyReport> getMonthlyReport(String projectCode) throws ProjectException {
		return this.getMonthlyReportDao().select(projectCode);
	}

	@Override
	public MonthlyReport getMonthlyReport(String projectCode, String assignYear, String assignMonth, String writerSsn) throws ProjectException {
		return this.getMonthlyReportDao().select(projectCode, assignYear, assignMonth, writerSsn);
	}

	@Override
	public void storeMonthlyReport(MonthlyReport monthlyReport) throws ProjectException {
		this.getMonthlyReportDao().update(monthlyReport);
	}

	@Override
	public void deleteMonthlyReport(String projectCode) throws ProjectException {
		this.getMonthlyReportDao().delete(projectCode);
	}

	@Override
	public void deleteMonthlyReport(String projectCode, String assignYear, String assignMonth, String writerSsn) throws ProjectException {
		this.getMonthlyReportDao().delete(projectCode, assignYear, assignMonth, writerSsn);
	}

	@Override
	public void entrustMonthlyReport(MonthlyReport monthlyReport) throws ProjectException {
		Work work = this.getWorklistManager().getWork(monthlyReport.getWorkId());
		work.setAssigneeId(monthlyReport.getEntrustUserSsn());
		this.getWorklistManager().entrustWork(work);
	}

	@Override
	public void executeProjectReport(MonthlyReport monthlyReport) throws ProjectException {
		try {
			String assigneeId = null;
			String taskTypeId = null;
			String level = null;
			Work work = this.getWorklistManager().getWork(monthlyReport.getWorkId());
			// 반려 여부 체크
			if (monthlyReport.getAppYN() != null && monthlyReport.getAppYN().equals("N")) {
				monthlyReport.setWriteDate("");
				monthlyReport.setRevieweDate("");
				monthlyReport.setApproveDate("");
				assigneeId = monthlyReport.getWriterSsn();
				monthlyReport.setState("reject");
				this.getWorklistManager().rejectWork(work.getId());
			} else {
				// 승인일 경우
				if (monthlyReport.getState().equals("writer") || monthlyReport.getState().equals("reject")) {
					monthlyReport.setWriteDate(DateTime.getDateString(""));
					if (monthlyReport.getReviewerSsn() != null && !monthlyReport.getReviewerSsn().equals("")) {
						assigneeId = monthlyReport.getReviewerSsn();
						monthlyReport.setState("reviewer");
					} else {
						assigneeId = monthlyReport.getApproverSsn();
						monthlyReport.setState("approver");
					}
				} else if (monthlyReport.getState().equals("reviewer")) {
					monthlyReport.setRevieweDate(DateTime.getDateString(""));
					assigneeId = monthlyReport.getApproverSsn();
					monthlyReport.setState("approver");
				} else if (monthlyReport.getState().equals("approver")) {
					monthlyReport.setApproveDate(DateTime.getDateString(""));
					assigneeId = monthlyReport.getApproverSsn();
					monthlyReport.setState("complete");
				} else {
					monthlyReport.setApproveDate(DateTime.getDateString(""));
					assigneeId = monthlyReport.getApproverSsn();
					monthlyReport.setState("complete");
				}
				this.getWorklistManager().executeWork(work.getId());
			}
			this.storeMonthlyReport(monthlyReport);

			// 업무를 할당한다.
			if (monthlyReport.getState().equals("writer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2386";
				level = SanctionConstants.SANCTION_STATE_DRAFT;
			} else if (monthlyReport.getState().equals("reject")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2387";
				level = SanctionConstants.SANCTION_STATE_REJECT_DRAFT;
			} else if (monthlyReport.getState().equals("reviewer")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2388";
				level = SanctionConstants.SANCTION_STATE_REVIEW;
			} else if (monthlyReport.getState().equals("approver")) {
				taskTypeId = "4028809e0a20e689010a2b4be76c2389";
				level = SanctionConstants.SANCTION_STATE_APPROVE;
			}

			// Job Date: 2010-05-11 Author: yhyim Description: 지도일지가 승인되면 업무가 종료되도록 조건절을 수정함(complete 부분 추가)
			if ((assigneeId != null && !assigneeId.trim().equals("")) && !monthlyReport.getState().equals("complete")) {
				Work assigneeWork = this.getWorklistManager().getWorkTemplate(taskTypeId, monthlyReport.getWriterSsn(), assigneeId,
						monthlyReport.getProjectCode(), monthlyReport.getTaskFormTypeId(), monthlyReport.getSeq());
				assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
				assigneeWork.setCreateDate(new Date());
				assigneeWork.setDraftDate(DateUtil.getDateTime("yyyyMMdd", monthlyReport.getWriteDate()));
				assigneeWork.setDraftUserId(monthlyReport.getWriterSsn());
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

	// ////////////////////////////////////////

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public MonthlyReportDao getMonthlyReportDao() {
		return monthlyReportDao;
	}

	public void setMonthlyReportDao(MonthlyReportDao monthlyReportDao) {
		this.monthlyReportDao = monthlyReportDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
