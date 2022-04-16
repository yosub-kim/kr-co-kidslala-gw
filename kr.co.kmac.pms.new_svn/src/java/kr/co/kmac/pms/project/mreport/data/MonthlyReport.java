/*
 * $Id: WeeklyReport.java,v 1.2 2019/03/24 00:39:02 cvs Exp $
 * created by : jiwoongLee creation-date : 2006. 3. 14.
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.mreport.data;

import kr.co.kmac.pms.project.mreport.form.MonthlyReportForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportListEntity.java,v 1.3 2011/01/17 08:52:55 cvs Exp $
 */

public class MonthlyReport {

	static final long serialVersionUID = -4034897190745766939L;
	private String workId;// 실행업무 아이디
	private String appYN;

	private String projectCode;// key
	private String projectName;

	private String taskFormTypeId;
	private String taskFormTypeName;

	private String projectStartDate;
	private String projectEndDate;

	private String assignYear;// key
	private String assignMonth;// key
	private String assignDate;

	private String weekOfProject;
	private String monthOfProject;

	private String reportTitle;
	private String reportContent;
	private String etcContent;

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

	private String projectState;
	private String projectStateStr;
	private String runningDivCode;
	private String runningDeptCode;
	private String writerCnt;
	private String reviewerCnt;
	private String approverCnt;
	private String completeCnt;
	private String assignWeekOfMonth;

	public String getAssignWeekOfMonth() {
		return assignWeekOfMonth;
	}

	public void setAssignWeekOfMonth(String assignWeekOfMonth) {
		this.assignWeekOfMonth = assignWeekOfMonth;
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

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getEtcContent() {
		return etcContent;
	}

	public void setEtcContent(String etcContent) {
		this.etcContent = etcContent;
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

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public String getProjectStateStr() {
		return projectStateStr;
	}

	public void setProjectStateStr(String projectStateStr) {
		this.projectStateStr = projectStateStr;
	}

	public String getRunningDivCode() {
		return runningDivCode;
	}

	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	public String getRunningDeptCode() {
		return runningDeptCode;
	}

	public void setRunningDeptCode(String runningDeptCode) {
		this.runningDeptCode = runningDeptCode;
	}

	public String getWriterCnt() {
		return writerCnt;
	}

	public void setWriterCnt(String writerCnt) {
		this.writerCnt = writerCnt;
	}

	public String getReviewerCnt() {
		return reviewerCnt;
	}

	public void setReviewerCnt(String reviewerCnt) {
		this.reviewerCnt = reviewerCnt;
	}

	public String getApproverCnt() {
		return approverCnt;
	}

	public void setApproverCnt(String approverCnt) {
		this.approverCnt = approverCnt;
	}

	public String getCompleteCnt() {
		return completeCnt;
	}

	public void setCompleteCnt(String completeCnt) {
		this.completeCnt = completeCnt;
	}

	public String getSeq() {
		return this.getAssignYear() + "" + this.getAssignMonth() + "|" + getWriterSsn();
	}

	public String getWeekOfProject() {
		return weekOfProject;
	}

	public void setWeekOfProject(String weekOfProject) {
		this.weekOfProject = weekOfProject;
	}

	public String getMonthOfProject() {
		return monthOfProject;
	}

	public void setMonthOfProject(String monthOfProject) {
		this.monthOfProject = monthOfProject;
	}

	public static MonthlyReport valueOf(MonthlyReportForm form) {
		MonthlyReport monthlyReport = new MonthlyReport();
		monthlyReport.setWorkId(form.getWorkId());// 실행업무 아이디
		monthlyReport.setAppYN(form.getAppYN());

		monthlyReport.setProjectCode(form.getProjectCode());
		monthlyReport.setTaskFormTypeId(form.getTaskFormTypeId());
		monthlyReport.setTaskFormTypeName(form.getTaskFormTypeName());

		monthlyReport.setAssignYear(form.getAssignYear());// key
		monthlyReport.setAssignMonth(form.getAssignMonth());// key
		monthlyReport.setAssignDate(form.getAssignDate());

		monthlyReport.setReportTitle(form.getReportTitle());
		monthlyReport.setReportContent(form.getReportContent());
		monthlyReport.setEtcContent(form.getEtcContent());

		monthlyReport.setProjectMemberStr(form.getProjectMemberStr());

		monthlyReport.setAttach(form.getAttach());
		monthlyReport.setState(form.getState());

		monthlyReport.setWriterSsn(form.getWriterSsn());
		monthlyReport.setWriterName(form.getWriterName());
		monthlyReport.setWriteDate(form.getWriteDate());
		monthlyReport.setReviewerSsn(form.getReviewerSsn());
		monthlyReport.setReviewerName(form.getReviewerName());
		monthlyReport.setRevieweDate(form.getRevieweDate());
		monthlyReport.setRevieweOpinion(form.getRevieweOpinion());
		monthlyReport.setApproverSsn(form.getApproverSsn());
		monthlyReport.setApproverName(form.getApproverName());
		monthlyReport.setApproveDate(form.getApproveDate());
		monthlyReport.setApproveOpinion(form.getApproveOpinion());

		monthlyReport.setEntrustUserSsn(form.getEntrustUserSsn());
		monthlyReport.setEntrustUserName(form.getEntrustUserName());

		return monthlyReport;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}