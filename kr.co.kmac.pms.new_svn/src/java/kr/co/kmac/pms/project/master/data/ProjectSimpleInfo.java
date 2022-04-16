/*
 * $Id: ProjectSimpleInfo.java,v 1.6 2018/06/29 04:42:52 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 관한 간단한 정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: ProjectSimpleInfo.java,v 1.6 2018/06/29 04:42:52 cvs Exp $
 */
public class ProjectSimpleInfo {

	private String projectCode;
	private String projectName;
	private String clientId;
	private String clientName;
	private String industryTypeCode;
	private String businessTypeCode;
	private String cioTypeCode;
	private String projectTypeCode;
	private String projectDetailCode;
	private String runningDivCode;
	private String runningDetpCode;
	private String puSsn;
	private String puName;
	private String arSsn;
	private String arName;
	private String advisorSsn;
	private String advisorName;
	private String pmSsn;
	private String pmName;
	private String plSsn;
	private String plName;
	private String startDate;
	private String endDate;
	private String projectState;
	private String projectStateDetail;
	private String processTypeCode;
	private String advisorDeptCode; // Job Date: 2007-07-05 Author: yhyim Description: var for Advisor dept code
	private String coGRP; //  Job Date: 2018-06-19 Author: lokal07 Description: 리서치센터(KCSMA) 프로젝트 고객만족도 메일 별도로 나가게 하기 위한 필드

	// 고객사 정보 추가
	private String customerCompanyName;
	private String customerCompanyCode;
	private String customerWorkerName;
	private String customerEmail;

	// 다국어 관련 정보 추가
	private String projectSubName;
	private String lang;
	
	//다면평가 추가
	private String mbSsn;
	private String mbName;
	private String mbCnt;
	private String mbRole;
	
	public String getMbRole() {
		return mbRole;
	}

	public void setMbRole(String mbRole) {
		this.mbRole = mbRole;
	}

	public String getMbSsn() {
		return mbSsn;
	}

	public void setMbSsn(String mbSsn) {
		this.mbSsn = mbSsn;
	}

	public String getMbName() {
		return mbName;
	}

	public void setMbName(String mbName) {
		this.mbName = mbName;
	}

	public String getMbCnt() {
		return mbCnt;
	}

	public void setMbCnt(String mbCnt) {
		this.mbCnt = mbCnt;
	}

	/**
	 * @return the projectSubName
	 */
	public String getProjectSubName() {
		return projectSubName;
	}

	/**
	 * @param projectSubName the projectSubName to set
	 */
	public void setProjectSubName(String projectSubName) {
		this.projectSubName = projectSubName;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the customerCompanyName
	 */
	public String getCustomerCompanyName() {
		return customerCompanyName;
	}

	/**
	 * @param customerCompanyName the customerCompanyName to set
	 */
	public void setCustomerCompanyName(String customerCompanyName) {
		this.customerCompanyName = customerCompanyName;
	}

	/**
	 * @return the customerCompanyCode
	 */
	public String getCustomerCompanyCode() {
		return customerCompanyCode;
	}

	/**
	 * @param customerCompanyCode the customerCompanyCode to set
	 */
	public void setCustomerCompanyCode(String customerCompanyCode) {
		this.customerCompanyCode = customerCompanyCode;
	}

	/**
	 * @return the customerWorkerName
	 */
	public String getCustomerWorkerName() {
		return customerWorkerName;
	}

	/**
	 * @param customerWorkerName the customerWorkerName to set
	 */
	public void setCustomerWorkerName(String customerWorkerName) {
		this.customerWorkerName = customerWorkerName;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return Returns the puSsn.
	 */
	public String getPuSsn() {
		return puSsn;
	}

	/**
	 * @param puSsn The puSsn to set.
	 */
	public void setPuSsn(String puSsn) {
		this.puSsn = puSsn;
	}

	/**
	 * @return Returns the processTypeCode.
	 */
	public String getProcessTypeCode() {
		return this.processTypeCode;
	}

	/**
	 * @param processTypeCode The processTypeCode to set.
	 */
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	/**
	 * @return Returns the runningDivCode.
	 */
	public String getRunningDivCode() {
		return this.runningDivCode;
	}

	/**
	 * @param runningDivCode The runningDivCode to set.
	 */
	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	/**
	 * @return Returns the projectStateDetail.
	 */
	public String getProjectStateDetail() {
		return this.projectStateDetail;
	}

	/**
	 * @param projectStateDetail The projectStateDetail to set.
	 */
	public void setProjectStateDetail(String projectStateDetail) {
		this.projectStateDetail = projectStateDetail;
	}

	/**
	 * @return Returns the projectState.
	 */
	public String getProjectState() {
		return this.projectState;
	}

	/**
	 * @param projectState The projectState to set.
	 */
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	/**
	 * @return Returns the advisorSsn.
	 */
	public String getAdvisorSsn() {
		return this.advisorSsn;
	}

	/**
	 * @param advisorSsn The advisorSsn to set.
	 */
	public void setAdvisorSsn(String advisorSsn) {
		this.advisorSsn = advisorSsn;
	}

	/**
	 * @return Returns the plSsn.
	 */
	public String getPlSsn() {
		return this.plSsn;
	}

	/**
	 * @param plSsn The plSsn to set.
	 */
	public void setPlSsn(String plSsn) {
		this.plSsn = plSsn;
	}

	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return this.clientName;
	}

	/**
	 * @param clientName The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return Returns the businessTypeCode.
	 */
	public String getBusinessTypeCode() {
		return this.businessTypeCode;
	}

	/**
	 * @param businessTypeCode The businessTypeCode to set.
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return the cioTypeCode
	 */
	public String getCioTypeCode() {
		return cioTypeCode;
	}

	/**
	 * @param cioTypeCode the cioTypeCode to set
	 */
	public void setCioTypeCode(String cioTypeCode) {
		this.cioTypeCode = cioTypeCode;
	}

	/**
	 * @return Returns the clientId.
	 */
	public String getClientId() {
		return this.clientId;
	}

	/**
	 * @param clientId The clientId to set.
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return this.endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return Returns the industryTypeCode.
	 */
	public String getIndustryTypeCode() {
		return this.industryTypeCode;
	}

	/**
	 * @param industryTypeCode The industryTypeCode to set.
	 */
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	/**
	 * @return Returns the pmSsn.
	 */
	public String getPmSsn() {
		return this.pmSsn;
	}

	/**
	 * @param pmSsn The pmSsn to set.
	 */
	public void setPmSsn(String pmSsn) {
		this.pmSsn = pmSsn;
	}

	/**
	 * @return Returns the projectDetailCode.
	 */
	public String getProjectDetailCode() {
		return this.projectDetailCode;
	}

	/**
	 * @param projectDetailCode The projectDetailCode to set.
	 */
	public void setProjectDetailCode(String projectDetailCode) {
		this.projectDetailCode = projectDetailCode;
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
	 * @return Returns the projectTypeCode.
	 */
	public String getProjectTypeCode() {
		return this.projectTypeCode;
	}

	/**
	 * @param projectTypeCode The projectTypeCode to set.
	 */
	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}

	/**
	 * @return Returns the startDate.
	 */
	public String getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return Returns the runningDetpCode.
	 */
	public String getRunningDetpCode() {
		return this.runningDetpCode;
	}

	/**
	 * @param runningDetpCode The runningDetpCode to set.
	 */
	public void setRunningDetpCode(String runningDetpCode) {
		this.runningDetpCode = runningDetpCode;
	}

	/**
	 * @return Returns the advisorDeptCode. Job Date: 2007-07-05 Author: yhyim
	 */
	public String getAdvisorDeptCode() {
		return this.advisorDeptCode;
	}

	/**
	 * @param advisorDeptCode The advisorDeptCode to set. Job Date: 2007-07-05 Author: yhyim
	 */
	public void setAdvisorDeptCode(String advisorDeptCode) {
		this.advisorDeptCode = advisorDeptCode;
	}
	
	/**
	 * @return Returns the coGRP
	 */
	public String getcoGRP() {
		return this.coGRP;
	}

	/**
	 * @param advisorDeptCode The advisorDeptCode to set. Job Date: 2007-07-05 Author: yhyim
	 */
	public void setcoGRP(String coGRP) {
		this.coGRP = coGRP;
	}

	/**
	 * @return the arSsn
	 */
	public String getArSsn() {
		return arSsn;
	}

	/**
	 * @param arSsn the arSsn to set
	 */
	public void setArSsn(String arSsn) {
		this.arSsn = arSsn;
	}

	/**
	 * @return the puName
	 */
	public String getPuName() {
		return puName;
	}

	/**
	 * @param puName the puName to set
	 */
	public void setPuName(String puName) {
		this.puName = puName;
	}

	/**
	 * @return the arName
	 */
	public String getArName() {
		return arName;
	}

	/**
	 * @param arName the arName to set
	 */
	public void setArName(String arName) {
		this.arName = arName;
	}

	/**
	 * @return the advisorName
	 */
	public String getAdvisorName() {
		return advisorName;
	}

	/**
	 * @param advisorName the advisorName to set
	 */
	public void setAdvisorName(String advisorName) {
		this.advisorName = advisorName;
	}

	/**
	 * @return the pmName
	 */
	public String getPmName() {
		return pmName;
	}

	/**
	 * @param pmName the pmName to set
	 */
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	/**
	 * @return the plName
	 */
	public String getPlName() {
		return plName;
	}

	/**
	 * @param plName the plName to set
	 */
	public void setPlName(String plName) {
		this.plName = plName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
