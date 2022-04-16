/*
 * $Id: WeeklyReportForm.java,v 1.1 2019/02/07 00:13:45 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.mreport.form;

import kr.co.kmac.pms.attach.form.AttachForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: WeeklyReportForm.java,v 1.1 2019/02/07 00:13:45 cvs Exp $
 */
/**
 * @struts:form name="monthlyReportAction"
 */
public class MonthlyReportForm extends AttachForm {

	private static final long serialVersionUID = 6299279835570742432L;

	private String workId;// 실행업무 아이디
	private String appYN;

	private String projectCode;

	private String taskFormTypeId;
	private String taskFormTypeName;

	private String projectStartDate;
	private String projectEndDate;

	private String assignYear;// key
	private String assignMonth;// key
	private String assignDate;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}