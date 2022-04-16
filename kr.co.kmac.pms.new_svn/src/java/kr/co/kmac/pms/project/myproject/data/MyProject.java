/*
 * $Id: MyProject.java,v 1.4 2012/05/23 09:34:55 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 3.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.myproject.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: MyProject.java,v 1.4 2012/05/23 09:34:55 cvs Exp $
 */
public class MyProject {

	private String delayState; // 프로젝트 상태
	private String delayForecast; // 프로젝트 지연 예상 상태
	private String projectState; // 프로젝트 상태
	private String projectCode; // 프로젝트 코드
	private String businessTypeCodeName; // 비즈니스타입 명
	private String projectName; // 프로젝트 명
	private String projectTypeCode; // 프로젝트타입 코드
	private String businessTypeCode; // 비즈니스타입 코드
	private String processTypeCode; // 프로세스타입 코드
	private String workSeq;
	private Date realEndDate;
	private String role;
	private String roleName;
	private String costOver;

	private String endDate;
	private String toDate;
	private String boardArticleCount; // 프로젝트 게시판 게시물 수
	private String runningDivCode; // 프로젝트 담당 BU
	private String isVoc;
	private String adminOpen;
	private String boardArticleCountQM;
	
	private String attach;
	private String parentProjectCode;

	
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getParentProjectCode() {
		return parentProjectCode;
	}

	public void setParentProjectCode(String parentProjectCode) {
		this.parentProjectCode = parentProjectCode;
	}

	public String getBoardArticleCountQM() {
		return boardArticleCountQM;
	}

	public void setBoardArticleCountQM(String boardArticleCountQM) {
		this.boardArticleCountQM = boardArticleCountQM;
	}

	public String getDelayForecast() {
		return delayForecast;
	}

	public void setDelayForecast(String delayForecast) {
		this.delayForecast = delayForecast;
	}

	/**
	 * @return the delayState
	 */
	public String getDelayState() {
		return delayState;
	}

	/**
	 * @param delayState the delayState to set
	 */
	public void setDelayState(String delayState) {
		this.delayState = delayState;
	}

	/**
	 * @return the projectState
	 */
	public String getProjectState() {
		return projectState;
	}

	/**
	 * @param projectState the projectState to set
	 */
	public void setProjectState(String projectState) {
		this.projectState = projectState;
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
	 * @return the businessTypeCodeName
	 */
	public String getBusinessTypeCodeName() {
		return businessTypeCodeName;
	}

	/**
	 * @param businessTypeCodeName the businessTypeCodeName to set
	 */
	public void setBusinessTypeCodeName(String businessTypeCodeName) {
		this.businessTypeCodeName = businessTypeCodeName;
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
	 * @return the projectTypeCode
	 */
	public String getProjectTypeCode() {
		return projectTypeCode;
	}

	/**
	 * @param projectTypeCode the projectTypeCode to set
	 */
	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
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
	 * @return the processTypeCode
	 */
	public String getProcessTypeCode() {
		return processTypeCode;
	}

	/**
	 * @param processTypeCode the processTypeCode to set
	 */
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	/**
	 * @return the workSeq
	 */
	public String getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return the realEndDate
	 */
	public Date getRealEndDate() {
		return realEndDate;
	}

	/**
	 * @param realEndDate the realEndDate to set
	 */
	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the costOver
	 */
	public String getCostOver() {
		return costOver;
	}

	/**
	 * @param costOver the costOver to set
	 */
	public void setCostOver(String costOver) {
		this.costOver = costOver;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the boardArticleCount
	 */
	public String getBoardArticleCount() {
		return boardArticleCount;
	}

	/**
	 * @param boardArticleCount the boardArticleCount to set
	 */
	public void setBoardArticleCount(String boardArticleCount) {
		this.boardArticleCount = boardArticleCount;
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
	 * @return the isVoc
	 */
	public String getIsVoc() {
		return isVoc;
	}

	/**
	 * @param isVoc the isVoc to set
	 */
	public void setIsVoc(String isVoc) {
		this.isVoc = isVoc;
	}
	
	/**
	 * @return the adminOpen
	 */
	public String getAdminOpen() {
		return adminOpen;
	}

	/**
	 * @param adminOpen the adminOpen to set
	 */
	public void setAdminOpen(String adminOpen) {
		this.adminOpen = adminOpen;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
