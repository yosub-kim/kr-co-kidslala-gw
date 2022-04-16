/*
 * $Id: ProjectReportContent.java,v 1.2 2010/07/19 06:08:58 cvs2 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.preport.data;

import kr.co.kmac.pms.sanction.preport.form.ProjectReportContentForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ProjectReportContent.java,v 1.2 2010/07/19 06:08:58 cvs2 Exp $
 */
/**
 * @struts:form name="projectReportContentAction"
 */
public class ProjectReportContent {

	private String workId;
	private String appYN;

	private String projectCode;
	private String projectName;
	private String taskFormTypeId;
	private String taskFormTypeName;
	private String seq;
	private String seqPerMan;
	private String workStartDate;
	private String workEndDate;
	private String assignDate;
	private String dueDate;
	private String title;
	private String workContent;
	private String impoInstContent;
	private String nextPlan;
	private String consultantOpinion;
	private String requestContent;
	private String attach;
	private String state;
	private String writerSsn;
	private String writeDate;
	private String reviewerSsn;
	private String revieweDate;
	private String revieweOpinion;
	private String approverSsn;
	private String approveDate;
	private String approveOpinion;
	private String payYN;
	private String payAmount;
	private String isExceed; // Job Date: 2007-12-05 Author: yhyim Description: 예산초과 여부 항목

	private String entrustUserSsn;
	private String entrustUserName;
	
	// constructor, init variable isExceed to N
	public ProjectReportContent() {
		this.setIsExceed("N");
	}

	public String getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * @return Returns the payYN.
	 */
	public String getPayYN() {
		return this.payYN;
	}

	/**
	 * @param payYN The payYN to set.
	 */
	public void setPayYN(String payYN) {
		this.payYN = payYN;
	}

	/**
	 * @return Returns the taskFormTypeName.
	 */
	public String getTaskFormTypeName() {
		return this.taskFormTypeName;
	}

	/**
	 * @param taskFormTypeName The taskFormTypeName to set.
	 */
	public void setTaskFormTypeName(String taskFormTypeName) {
		this.taskFormTypeName = taskFormTypeName;
	}

	/**
	 * @return Returns the projectName.
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * @param projectName The projectName to set.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return Returns the seqPerMan.
	 */
	public String getSeqPerMan() {
		return this.seqPerMan;
	}

	/**
	 * @param seqPerMan The seqPerMan to set.
	 */
	public void setSeqPerMan(String seqPerMan) {
		this.seqPerMan = seqPerMan;
	}

	/**
	 * @return Returns the approveDate.
	 */
	public String getApproveDate() {
		return this.approveDate;
	}

	/**
	 * @param approveDate The approveDate to set.
	 */
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * @return Returns the approveOpinion.
	 */
	public String getApproveOpinion() {
		return this.approveOpinion;
	}

	/**
	 * @param approveOpinion The approveOpinion to set.
	 */
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	/**
	 * @return Returns the approverSsn.
	 */
	public String getApproverSsn() {
		return this.approverSsn;
	}

	/**
	 * @param approverSsn The approverSsn to set.
	 */
	public void setApproverSsn(String approverSsn) {
		this.approverSsn = approverSsn;
	}

	/**
	 * @return Returns the appYN.
	 */
	public String getAppYN() {
		return this.appYN;
	}

	/**
	 * @param appYN The appYN to set.
	 */
	public void setAppYN(String appYN) {
		this.appYN = appYN;
	}

	/**
	 * @return Returns the assignDate.
	 */
	public String getAssignDate() {
		return this.assignDate;
	}

	/**
	 * @param assignDate The assignDate to set.
	 */
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return Returns the attach.
	 */
	public String getAttach() {
		return this.attach;
	}

	/**
	 * @param attach The attach to set.
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return Returns the consultantOpinion.
	 */
	public String getConsultantOpinion() {
		return this.consultantOpinion;
	}

	/**
	 * @param consultantOpinion The consultantOpinion to set.
	 */
	public void setConsultantOpinion(String consultantOpinion) {
		this.consultantOpinion = consultantOpinion;
	}

	/**
	 * @return Returns the dueDate.
	 */
	public String getDueDate() {
		return this.dueDate;
	}

	/**
	 * @param dueDate The dueDate to set.
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return Returns the impoInstContent.
	 */
	public String getImpoInstContent() {
		return this.impoInstContent;
	}

	/**
	 * @param impoInstContent The impoInstContent to set.
	 */
	public void setImpoInstContent(String impoInstContent) {
		this.impoInstContent = impoInstContent;
	}

	/**
	 * @return Returns the nextPlan.
	 */
	public String getNextPlan() {
		return this.nextPlan;
	}

	/**
	 * @param nextPlan The nextPlan to set.
	 */
	public void setNextPlan(String nextPlan) {
		this.nextPlan = nextPlan;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return this.projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the requestContent.
	 */
	public String getRequestContent() {
		return this.requestContent;
	}

	/**
	 * @param requestContent The requestContent to set.
	 */
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	/**
	 * @return Returns the revieweDate.
	 */
	public String getRevieweDate() {
		return this.revieweDate;
	}

	/**
	 * @param revieweDate The revieweDate to set.
	 */
	public void setRevieweDate(String revieweDate) {
		this.revieweDate = revieweDate;
	}

	/**
	 * @return Returns the revieweOpinion.
	 */
	public String getRevieweOpinion() {
		return this.revieweOpinion;
	}

	/**
	 * @param revieweOpinion The revieweOpinion to set.
	 */
	public void setRevieweOpinion(String revieweOpinion) {
		this.revieweOpinion = revieweOpinion;
	}

	/**
	 * @return Returns the reviewerSsn.
	 */
	public String getReviewerSsn() {
		return this.reviewerSsn;
	}

	/**
	 * @param reviewerSsn The reviewerSsn to set.
	 */
	public void setReviewerSsn(String reviewerSsn) {
		this.reviewerSsn = reviewerSsn;
	}

	/**
	 * @return Returns the seq.
	 */
	public String getSeq() {
		return this.seq;
	}

	/**
	 * @param seq The seq to set.
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return Returns the taskFormTypeId.
	 */
	public String getTaskFormTypeId() {
		return this.taskFormTypeId;
	}

	/**
	 * @param taskFormTypeId The taskFormTypeId to set.
	 */
	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	/**
	 * @return the workId
	 */
	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId the workId to set
	 */
	public void setWorkId(String workId) {
		this.workId = workId;
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the workContent.
	 */
	public String getWorkContent() {
		return this.workContent;
	}

	/**
	 * @param workContent The workContent to set.
	 */
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	/**
	 * @return Returns the workEndDate.
	 */
	public String getWorkEndDate() {
		return this.workEndDate;
	}

	/**
	 * @param workEndDate The workEndDate to set.
	 */
	public void setWorkEndDate(String workEndDate) {
		this.workEndDate = workEndDate;
	}

	/**
	 * @return Returns the workStartDate.
	 */
	public String getWorkStartDate() {
		return this.workStartDate;
	}

	/**
	 * @param workStartDate The workStartDate to set.
	 */
	public void setWorkStartDate(String workStartDate) {
		this.workStartDate = workStartDate;
	}

	/**
	 * @return Returns the writeDate.
	 */
	public String getWriteDate() {
		return this.writeDate;
	}

	/**
	 * @param writeDate The writeDate to set.
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	/**
	 * @return Returns the writerSsn.
	 */
	public String getWriterSsn() {
		return this.writerSsn;
	}

	/**
	 * @param writerSsn The writerSsn to set.
	 */
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	/**
	 * @return the isExceed
	 */
	public String getIsExceed() {
		return isExceed;
	}

	/**
	 * @param isExceed the isExceed to set
	 */
	public void setIsExceed(String isExceed) {
		this.isExceed = isExceed;
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

	public static ProjectReportContent valueOf(ProjectReportContentForm form) {
		ProjectReportContent projectReportContent = new ProjectReportContent();
		projectReportContent.setWorkId(form.getWorkId());
		projectReportContent.setAppYN(form.getAppYN());

		projectReportContent.setProjectCode(form.getProjectCode());
		projectReportContent.setProjectName(form.getProjectName());
		projectReportContent.setTaskFormTypeId(form.getTaskFormTypeId());
		projectReportContent.setTaskFormTypeName(form.getTaskFormTypeName());
		projectReportContent.setSeq(form.getSeq());
		projectReportContent.setSeqPerMan(form.getSeqPerMan());
		projectReportContent.setWorkStartDate(form.getWorkStartDate());
		projectReportContent.setWorkEndDate(form.getWorkEndDate());
		projectReportContent.setAssignDate(form.getAssignDate());
		projectReportContent.setDueDate(form.getDueDate());
		projectReportContent.setTitle(form.getTitle());
		projectReportContent.setWorkContent(form.getWorkContent());
		projectReportContent.setImpoInstContent(form.getImpoInstContent());
		projectReportContent.setNextPlan(form.getNextPlan());
		projectReportContent.setConsultantOpinion(form.getConsultantOpinion());
		projectReportContent.setRequestContent(form.getRequestContent());
		projectReportContent.setAttach(form.getAttach());
		projectReportContent.setState(form.getState());
		projectReportContent.setWriterSsn(form.getWriterSsn());
		projectReportContent.setWriteDate(form.getWriteDate());
		projectReportContent.setReviewerSsn(form.getReviewerSsn());
		projectReportContent.setRevieweDate(form.getRevieweDate());
		projectReportContent.setRevieweOpinion(form.getRevieweOpinion());
		projectReportContent.setApproverSsn(form.getApproverSsn());
		projectReportContent.setApproveDate(form.getApproveDate());
		projectReportContent.setApproveOpinion(form.getApproveOpinion());
		projectReportContent.setPayYN(form.getPayYN());
		projectReportContent.setPayAmount(form.getPayAmount());
		projectReportContent.setIsExceed(form.getIsExceed());
		projectReportContent.setEntrustUserSsn(form.getEntrustUserSsn());
		projectReportContent.setEntrustUserName(form.getEntrustUserName());
		
		return projectReportContent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}