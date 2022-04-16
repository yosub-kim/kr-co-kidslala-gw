/*
 * $Id: ProjectRollingMonitor.java,v 1.3 2011/09/27 14:50:16 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingMonitor.java,v 1.3 2011/09/27 14:50:16 cvs Exp $
 */
public class ProjectRollingMonitor {

	private String projectCode;// 프로젝트 코드
	private String customerSeq;// 고객코드
	private String projectName;
	private String runningDivCode;
	private String runningDiv;
	private String businessTypeCode;
	private String businessTypeName;
	private String projectState;
	private String projectStateName;
	private String realEndDate;
	private String surveySendDate;
	private String receiveDate;
	private String receiveEmail;	
	private String surveyEndDate;
	private String evalDelayDate;
	private String hasCeoComment;
	private String answer;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the customerSeq
	 */
	public String getCustomerSeq() {
		return customerSeq;
	}

	/**
	 * @param customerSeq the customerSeq to set
	 */
	public void setCustomerSeq(String customerSeq) {
		this.customerSeq = customerSeq;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the runningDivCode
	 */
	public String getRunningDivCode() {
		return runningDivCode;
	}

	/**
	 * @param runningDivCode the runningDivCode to set
	 */
	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	/**
	 * @return the runningDiv
	 */
	public String getRunningDiv() {
		return runningDiv;
	}

	/**
	 * @param runningDiv the runningDiv to set
	 */
	public void setRunningDiv(String runningDiv) {
		this.runningDiv = runningDiv;
	}

	/**
	 * @return the projectState
	 */
	public String getProjectState() {
		return projectState;
	}

	/**
	 * @return the businessTypeCode
	 */
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	/**
	 * @param businessTypeCode the businessTypeCode to set
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @param projectState the projectState to set
	 */
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	/**
	 * @return the projectStateName
	 */
	public String getProjectStateName() {
		return projectStateName;
	}

	/**
	 * @param projectStateName the projectStateName to set
	 */
	public void setProjectStateName(String projectStateName) {
		this.projectStateName = projectStateName;
	}

	/**
	 * @return the realEndDate
	 */
	public String getRealEndDate() {
		if (realEndDate != null) {
			return realEndDate.substring(0, 4) + "-" + realEndDate.substring(4, 6) + "-" + realEndDate.substring(6, 8);
		}
		return realEndDate;
	}

	/**
	 * @param realEndDate the realEndDate to set
	 */
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}

	/**
	 * @return the surveySendDate
	 */
	public String getSurveySendDate() {
		if (surveySendDate != null) {
			return surveySendDate.substring(0, 10);
		}
		return surveySendDate;
	}

	/**
	 * @param surveySendDate the surveySendDate to set
	 */
	public void setSurveySendDate(String surveySendDate) {
		this.surveySendDate = surveySendDate;
	}

	/**
	 * @return the receiveDate
	 */
	public String getReceiveDate() {
		if (receiveDate != null) {
			return receiveDate.substring(0, 10);
		}
		return receiveDate;
	}

	/**
	 * @param receiveDate the receiveDate to set
	 */
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	/**
	 * @return the receiveEmail
	 */
	public String getReceiveEmail() {
		return receiveEmail;
	}

	/**
	 * @param receiveEmail the receiveEmail to set
	 */
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	/**
	 * @return the surveyEndDate
	 */
	public String getSurveyEndDate() {
		if (surveyEndDate != null) {
			return surveyEndDate.substring(0, 10);
		}
		return surveyEndDate;
	}

	/**
	 * @param surveyEndDate the surveyEndDate to set
	 */
	public void setSurveyEndDate(String surveyEndDate) {
		this.surveyEndDate = surveyEndDate;
	}
	
	/**
	 * @return the evalDelayDate
	 */
	public String getEvalDelayDate() {
		return evalDelayDate;
	}

	/**
	 * @param evalDelayDate the evalDelayDate to set
	 */
	public void setEvalDelayDate(String evalDelayDate) {
		this.evalDelayDate = evalDelayDate;
	}

	/**
	 * @return the ceoComment
	 */
	public String getHasCeoComment() {
		return hasCeoComment;
	}

	/**
	 * @param ceoComment the ceoComment to set
	 */
	public void setHasCeoComment(String hasCeoComment) {
		this.hasCeoComment = hasCeoComment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
