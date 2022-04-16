/*
 * $Id: MonthlyReportScheduleService.java,v 1.5 2019/03/11 17:26:39 cvs Exp $
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
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.mreport.dao.MonthlyReportDao;
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.service.scheduler.dao.MonthlyReportScheduleServiceDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: MonthlyReportScheduleService.java,v 1.5 2019/03/11 17:26:39 cvs Exp $
 */
public class MonthlyReportScheduleService {
	private static final Log log = LogFactory.getLog(MonthlyReportScheduleService.class);
	private MonthlyReportScheduleServiceDao monthlyReportScheduleServiceDao;
	private MonthlyReportDao monthlyReportDao;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private WorklistManager worklistManager;

	public int assignMonthlyReport() {
		List<MonthlyReport> scheduleList = this.getMonthlyReportScheduleServiceDao().getMonthlyReportList();
		this.assignTask(scheduleList);
		return scheduleList.size();
	}

	public int assignMonthlyReportMissed(String projectCode) {
		List<MonthlyReport> scheduleList = this.getMonthlyReportScheduleServiceDao().getMonthlyReportListMissed(projectCode);
		this.assignTask(scheduleList);
		return scheduleList.size();
	}

	public String assignMonthlyReportUntilGivenDated(String yyyymmdd) {
		List<MonthlyReport> scheduleList = this.getMonthlyReportScheduleServiceDao().getMonthlyReportUntilGivenDated(yyyymmdd);
		String res = this.assignTask(scheduleList);
		return res;
	}

	private String assignTask(List<MonthlyReport> scheduleList) {
		String resultStr = "";
		if (scheduleList != null && scheduleList.size() > 0) {
			int i = 1;
			for (MonthlyReport monthlyReport : scheduleList) {
				try {
					ProjectSimpleInfo projectInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(monthlyReport.getProjectCode());
					monthlyReport.setState("writer");
					monthlyReport.setTaskFormTypeId("4028809e0a20e689010a2b4be76c2386");
					monthlyReport.setTaskFormTypeName(this.getWorklistManager().getWorkType("4028809e0a20e689010a2b4be76c2386").getName());
					getMonthlyReportDao().insert(monthlyReport);

					Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a20e689010a2b4be76c2386", "PMS", monthlyReport.getWriterSsn(), monthlyReport.getProjectCode(), monthlyReport.getTaskFormTypeId(), monthlyReport.getSeq());
					assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
					assigneeWork.setCreateDate(DateUtil.getDateTime("yyyyMMdd", monthlyReport.getAssignDate()));
					assigneeWork.setDraftDate(new Date());
					assigneeWork.setDraftUserId(monthlyReport.getWriterSsn());
					// assigneeWork.setDraftUserDept(projectReportContent.getWriterSsn());
					assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_DRAFT);
					assigneeWork.setState(WorkConstants.WORK_STATE_READY);
					assigneeWork.setTitle(this.getWorklistManager().getWorkType("4028809e0a20e689010a2b4be76c2386").getName());
					if (this.getWorklistManager().assignWork(assigneeWork)) {
						resultStr += "[" + monthlyReport.getWriterSsn() + " <-- " + projectInfo.getProjectName() + "||" + projectInfo.getProjectCode() + "] MonthlyReport Task was assigned.<br>";
					} else {
						resultStr += "[" + monthlyReport.getWriterSsn() + " <-- " + projectInfo.getProjectName() + "||" + projectInfo.getProjectCode() + "] MonthlyReport Task was not created. something was wrong.<br> ";
					}
					resultStr += "## MonthlyReport Scheduler was executed. [" + i + "] <br>";
					i++;
				} catch (Exception e) {
					resultStr += "$$ MonthlyReport Scheduler error occured - . " + monthlyReport.getProjectCode() + "|||" + e.getMessage() + "<br>";
				}
			}

		}
		resultStr += "## MonthlyReport Scheduler was finished. <br>";

		System.out.println(resultStr);

		return resultStr;
	}

	// /////////////////
	public MonthlyReportScheduleServiceDao getMonthlyReportScheduleServiceDao() {
		return monthlyReportScheduleServiceDao;
	}

	public void setMonthlyReportScheduleServiceDao(MonthlyReportScheduleServiceDao monthlyReportScheduleServiceDao) {
		this.monthlyReportScheduleServiceDao = monthlyReportScheduleServiceDao;
	}

	public MonthlyReportDao getMonthlyReportDao() {
		return monthlyReportDao;
	}

	public void setMonthlyReportDao(MonthlyReportDao monthlyReportDao) {
		this.monthlyReportDao = monthlyReportDao;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

}
