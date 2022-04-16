/*
 * $Id: WeeklyReportForm.java,v 1.1 2019/02/07 00:13:45 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.wreport.form;

import kr.co.kmac.pms.attach.form.AttachForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: WeeklyReportForm.java,v 1.1 2019/02/07 00:13:45 cvs Exp $
 */
/**
 * @struts:form name="weeklyReportAction"
 */
public class WeeklyReportForm extends AttachForm {

	private static final long serialVersionUID = 6299279835570742432L;

	private String workId;// 실행업무 아이디
	private String appYN;

	private String projectCode;
	private String projectName;
	private String taskFormTypeId;
	private String taskFormTypeName;

	private String projectStartDate;
	private String projectEndDate;

	private String assignYear;// key
	private String assignMonth;// key
	private String assignWeekOfMonth;// key
	private String assignDate;
	private String weekOfProject;

	private String realProgress;
	private String planProgress;
	private String progressRatio;

	private String thisWeekFromDate;
	private String thisWeekToDate;
	private String thisWeekContent;
	private String thisWeekOutputList;
	private String thisWeekPlComment;

	private String nextWeekFromDate;
	private String nextWeekToDate;
	private String nextWeekContent;
	private String nextWeekOutputList;
	private String nextWeekPlComment;

	private String delayReason;
	private String projectMemberStr;

	private String attach;
	private String state;

	private String writerSsn;
	private String writerName;
	private String writeDate;
	private String reviewerSsn;
	private String reviewerName;
	private String revieweDate;
	private String revieweOpinion;
	private String approverSsn;
	private String approverName;
	private String approveDate;
	private String approveOpinion;

	private String entrustUserSsn;
	private String entrustUserName;
	
	private String activity;

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getAppYN() {
		return appYN;
	}

	public void setAppYN(String appYN) {
		this.appYN = appYN;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskFormTypeId() {
		return taskFormTypeId;
	}

	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	public String getTaskFormTypeName() {
		return taskFormTypeName;
	}

	public void setTaskFormTypeName(String taskFormTypeName) {
		this.taskFormTypeName = taskFormTypeName;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getAssignYear() {
		return assignYear;
	}

	public void setAssignYear(String assignYear) {
		this.assignYear = assignYear;
	}

	public String getAssignMonth() {
		return assignMonth;
	}

	public void setAssignMonth(String assignMonth) {
		this.assignMonth = assignMonth;
	}

	public String getAssignWeekOfMonth() {
		return assignWeekOfMonth;
	}

	public void setAssignWeekOfMonth(String assignWeekOfMonth) {
		this.assignWeekOfMonth = assignWeekOfMonth;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String getWeekOfProject() {
		return weekOfProject;
	}

	public void setWeekOfProject(String weekOfProject) {
		this.weekOfProject = weekOfProject;
	}

	public String getRealProgress() {
		return realProgress;
	}

	public void setRealProgress(String realProgress) {
		this.realProgress = realProgress;
	}

	public String getPlanProgress() {
		return planProgress;
	}

	public void setPlanProgress(String planProgress) {
		this.planProgress = planProgress;
	}

	public String getProgressRatio() {
		return progressRatio;
	}

	public void setProgressRatio(String progressRatio) {
		this.progressRatio = progressRatio;
	}

	public String getThisWeekFromDate() {
		return thisWeekFromDate;
	}

	public void setThisWeekFromDate(String thisWeekFromDate) {
		this.thisWeekFromDate = thisWeekFromDate;
	}

	public String getThisWeekToDate() {
		return thisWeekToDate;
	}

	public void setThisWeekToDate(String thisWeekToDate) {
		this.thisWeekToDate = thisWeekToDate;
	}

	public String getThisWeekContent() {
		return thisWeekContent;
	}

	public void setThisWeekContent(String thisWeekContent) {
		this.thisWeekContent = thisWeekContent;
	}

	public String getThisWeekOutputList() {
		return thisWeekOutputList;
	}

	public void setThisWeekOutputList(String thisWeekOutputList) {
		this.thisWeekOutputList = thisWeekOutputList;
	}

	public String getThisWeekPlComment() {
		return thisWeekPlComment;
	}

	public void setThisWeekPlComment(String thisWeekPlComment) {
		this.thisWeekPlComment = thisWeekPlComment;
	}

	public String getNextWeekFromDate() {
		return nextWeekFromDate;
	}

	public void setNextWeekFromDate(String nextWeekFromDate) {
		this.nextWeekFromDate = nextWeekFromDate;
	}

	public String getNextWeekToDate() {
		return nextWeekToDate;
	}

	public void setNextWeekToDate(String nextWeekToDate) {
		this.nextWeekToDate = nextWeekToDate;
	}

	public String getNextWeekContent() {
		return nextWeekContent;
	}

	public void setNextWeekContent(String nextWeekContent) {
		this.nextWeekContent = nextWeekContent;
	}

	public String getNextWeekOutputList() {
		return nextWeekOutputList;
	}

	public void setNextWeekOutputList(String nextWeekOutputList) {
		this.nextWeekOutputList = nextWeekOutputList;
	}

	public String getNextWeekPlComment() {
		return nextWeekPlComment;
	}

	public void setNextWeekPlComment(String nextWeekPlComment) {
		this.nextWeekPlComment = nextWeekPlComment;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getProjectMemberStr() {
		return projectMemberStr;
	}

	public void setProjectMemberStr(String projectMemberStr) {
		this.projectMemberStr = projectMemberStr;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWriterSsn() {
		return writerSsn;
	}

	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getReviewerSsn() {
		return reviewerSsn;
	}

	public void setReviewerSsn(String reviewerSsn) {
		this.reviewerSsn = reviewerSsn;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public String getRevieweDate() {
		return revieweDate;
	}

	public void setRevieweDate(String revieweDate) {
		this.revieweDate = revieweDate;
	}

	public String getRevieweOpinion() {
		return revieweOpinion;
	}

	public void setRevieweOpinion(String revieweOpinion) {
		this.revieweOpinion = revieweOpinion;
	}

	public String getApproverSsn() {
		return approverSsn;
	}

	public void setApproverSsn(String approverSsn) {
		this.approverSsn = approverSsn;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getEntrustUserSsn() {
		return entrustUserSsn;
	}

	public void setEntrustUserSsn(String entrustUserSsn) {
		this.entrustUserSsn = entrustUserSsn;
	}

	public String getEntrustUserName() {
		return entrustUserName;
	}

	public void setEntrustUserName(String entrustUserName) {
		this.entrustUserName = entrustUserName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}