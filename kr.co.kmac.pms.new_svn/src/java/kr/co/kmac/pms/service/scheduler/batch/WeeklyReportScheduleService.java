/*
 * $Id: WeeklyReportScheduleService.java,v 1.5 2019/03/11 17:26:39 cvs Exp $
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
import kr.co.kmac.pms.project.wreport.dao.WeeklyReportDao;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.service.scheduler.dao.WeeklyReportScheduleServiceDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: WeeklyReportScheduleService.java,v 1.5 2019/03/11 17:26:39 cvs Exp $
 */
public class WeeklyReportScheduleService {
	private static final Log log = LogFactory.getLog(WeeklyReportScheduleService.class);
	private WeeklyReportScheduleServiceDao weeklyReportScheduleServiceDao;
	private WeeklyReportDao weeklyReportDao;	
	private ProjectMasterInfoManager projectMasterInfoManager;
	private WorklistManager worklistManager;
	
	public int assignWeeklyReport() {
		List<WeeklyReport> scheduleList = this.getWeeklyReportScheduleServiceDao().getWeeklyReportList();
		this.assignTask(scheduleList);
		return scheduleList.size();
	}

	// properties and collaborators
	public int assignWeeklyReportOnThieWeek(String projectCode) {
		List<WeeklyReport> scheduleList = this.getWeeklyReportScheduleServiceDao().getWeeklyReportList(projectCode);
		this.assignTask(scheduleList);
		return scheduleList.size();
	}
	

	public int assignWeeklyReportWhenApproved(String projectCode) {
		List<WeeklyReport> scheduleList = this.getWeeklyReportScheduleServiceDao().getWeeklyReportListWhenApproved(projectCode);
		this.assignTask(scheduleList);
		return scheduleList.size();
	}	

	public int assignWeeklyReportMissed(String projectCode) {
		List<WeeklyReport> scheduleList = this.getWeeklyReportScheduleServiceDao().getWeeklyReportListMissed(projectCode);
		this.assignTask(scheduleList);
		return scheduleList.size();
	}

	private String assignTask(List<WeeklyReport> scheduleList) {
		String resultStr = "";
		if (scheduleList != null && scheduleList.size() > 0) {
			for (WeeklyReport weeklyReport : scheduleList) {
				try {					
					ProjectSimpleInfo projectInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(weeklyReport.getProjectCode());
					weeklyReport.setState("writer");
					weeklyReport.setTaskFormTypeId("4028809e0a20e689010a2b4be76c2226");
					weeklyReport.setTaskFormTypeName(this.getWorklistManager().getWorkType("4028809e0a20e689010a2b4be76c2226").getName());
					getWeeklyReportDao().insert(weeklyReport);

					Work assigneeWork = this.getWorklistManager().getWorkTemplate("4028809e0a20e689010a2b4be76c2226",
							"PMS", weeklyReport.getWriterSsn(),
							weeklyReport.getProjectCode(),
							weeklyReport.getTaskFormTypeId(),
							weeklyReport.getSeq());
					assigneeWork.setId(IdGenerator.generate(IdGenerator.PREFIX_WORK));
					assigneeWork.setCreateDate(DateUtil.getDateTime("yyyyMMdd", weeklyReport.getAssignDate()));
					assigneeWork.setDraftDate(new Date());
					assigneeWork.setDraftUserId(weeklyReport.getWriterSsn());
					// assigneeWork.setDraftUserDept(projectReportContent.getWriterSsn());
					assigneeWork.setLevel(SanctionConstants.SANCTION_STATE_DRAFT);
					assigneeWork.setState(WorkConstants.WORK_STATE_READY);
					assigneeWork.setTitle(this.getWorklistManager().getWorkType("4028809e0a20e689010a2b4be76c2226").getName());
					if (this.getWorklistManager().assignWork(assigneeWork)) {
						resultStr += "[" + weeklyReport.getWriterSsn() + " <-- " + projectInfo.getProjectName() + "||"
								+ projectInfo.getProjectCode() + "] WeeklyReport Task was assigned.<br>";
					} else {
						resultStr += "[" + weeklyReport.getWriterSsn() + " <-- " + projectInfo.getProjectName() + "||"
								+ projectInfo.getProjectCode() + "] WeeklyReport Task was not created. something was wrong.<br> ";
					}
					resultStr += "## WeeklyReport Scheduler was executed. <br>";
				} catch (Exception e) {
					resultStr += "$$ WeeklyReport Scheduler error occured - . "+weeklyReport.getProjectCode()+"<br>";
				}
			}

		}
		resultStr += "## WeeklyReport Scheduler was finished. <br>";
		
		System.out.println(resultStr);
		
		return resultStr;
	}

	
	
	///////////////////
	public WeeklyReportScheduleServiceDao getWeeklyReportScheduleServiceDao() {
		return weeklyReportScheduleServiceDao;
	}

	public void setWeeklyReportScheduleServiceDao(
			WeeklyReportScheduleServiceDao weeklyReportScheduleServiceDao) {
		this.weeklyReportScheduleServiceDao = weeklyReportScheduleServiceDao;
	}

	public WeeklyReportDao getWeeklyReportDao() {
		return weeklyReportDao;
	}

	public void setWeeklyReportDao(WeeklyReportDao weeklyReportDao) {
		this.weeklyReportDao = weeklyReportDao;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(
			ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}


}
