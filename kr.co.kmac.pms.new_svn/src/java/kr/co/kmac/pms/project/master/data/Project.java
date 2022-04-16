/*
 * $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.master.data;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 */
public class Project {

	private int seq;

	private String entNo;
	private String projectCode;
	private String processTypeCode;
	private String projectName;
	private String projectSubName;
	private String industryTypeCode;
	private String businessTypeCode;
	private String cioTypeCode;
	private String projectConditionCode;
	private String projectConditionCode2;
	private String projectConditionCode3;
	private String projectTypeCode;
	private String projectDetailCode;
	private String businessFunctionType;
	private String runningDivCode;
	private String runningDeptCode;
	private String runningPuCode;
	private String runningGroupCode;
	private String preStartDate;
	private String preEndDate;
	private String realStartDate;
	private String realEndDate;
	private String projectState;
	private String projectStateDetail;
	private String attach;
	private Date createDate;
	private String createrSsn;
	private Date modifyDate;
	private String modifySsn;
	private String remark;
	private String customerSeq;
	private String customerName;
	private String customerCode;
	private String customerWorkPName;
	private String customerWorkPEmail;
	private String customerWorkPPhone;
	private String customerContPName;
	private String customerContPEmail;
	private String customerContPPhone;
	private String endGubun;
	private String endReason;
	private String endRate;
	private String groupComment;
	private String cfoComment;
	private String cboComment;
	private String costOver;
	private String projectContractType;
	private String parentProjectCode;
	private String payCostOver;
	private String etcCostOver;
	private String endBackground;
	private String endResult;
	private String endReview;
	private String endSuggestion;
	private String isVoc;
	private String isEvaluate;
	private String isEduConnected;
	private String adminOpen;
	private String lang;
	private String coGRP;
	private String projectScale;
	private String salesAmt;
	private String func;
	private String jukyo;
	private String projectCodeBp;
	private String bp_projId;
	private String bp_entNo;
	private String realWorkFromDt;
	private String realWorkToDt;

	public String getRealWorkFromDt() {
		return realWorkFromDt;
	}

	public void setRealWorkFromDt(String realWorkFromDt) {
		this.realWorkFromDt = realWorkFromDt;
	}

	public String getRealWorkToDt() {
		return realWorkToDt;
	}

	public void setRealWorkToDt(String realWorkToDt) {
		this.realWorkToDt = realWorkToDt;
	}

	public String getBp_entNo() {
		return bp_entNo;
	}

	public void setBp_entNo(String bp_entNo) {
		this.bp_entNo = bp_entNo;
	}

	public String getBp_projId() {
		return bp_projId;
	}

	public void setBp_projId(String bp_projId) {
		this.bp_projId = bp_projId;
	}

	public String getProjectCodeBp() {
		return projectCodeBp;
	}

	public void setProjectCodeBp(String projectCodeBp) {
		this.projectCodeBp = projectCodeBp;
	}

	public String getJukyo() {
		return jukyo;
	}

	public void setJukyo(String jukyo) {
		this.jukyo = jukyo;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(String salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getProjectScale() {
		return projectScale;
	}

	public void setProjectScale(String projectScale) {
		this.projectScale = projectScale;
	}

	private List<ProjectCsrInfo> projectCsrInfoList;
	private List<ProjectMember> projectMemberList;

	/**
	 * @return the entNo
	 */
	public String getEntNo() {
		return entNo;
	}

	/**
	 * @param entNo the entNo to set
	 */
	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
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
	 * @return the industryTypeCode
	 */
	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	/**
	 * @param industryTypeCode the industryTypeCode to set
	 */
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
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
	 * @return the projectConditionCode
	 */
	public String getProjectConditionCode() {
		return projectConditionCode;
	}

	/**
	 * @param projectConditionCode the projectConditionCode to set
	 */
	public void setProjectConditionCode(String projectConditionCode) {
		this.projectConditionCode = projectConditionCode;
	}

	/**
	 * @return the projectConditionCode2
	 */
	public String getProjectConditionCode2() {
		return projectConditionCode2;
	}

	/**
	 * @param projectConditionCode2 the projectConditionCode2 to set
	 */
	public void setProjectConditionCode2(String projectConditionCode2) {
		this.projectConditionCode2 = projectConditionCode2;
	}

	/**
	 * @return the projectConditionCode3
	 */
	public String getProjectConditionCode3() {
		return projectConditionCode3;
	}

	/**
	 * @param projectConditionCode3 the projectConditionCode3 to set
	 */
	public void setProjectConditionCode3(String projectConditionCode3) {
		this.projectConditionCode3 = projectConditionCode3;
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
	 * @return the projectDetailCode
	 */
	public String getProjectDetailCode() {
		return projectDetailCode;
	}

	/**
	 * @param projectDetailCode the projectDetailCode to set
	 */
	public void setProjectDetailCode(String projectDetailCode) {
		this.projectDetailCode = projectDetailCode;
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
	 * @return the runningDeptCode
	 */
	public String getRunningDeptCode() {
		return runningDeptCode;
	}

	/**
	 * @param runningDeptCode the runningDeptCode to set
	 */
	public void setRunningDeptCode(String runningDeptCode) {
		this.runningDeptCode = runningDeptCode;
	}

	/**
	 * @return the preStartDate
	 */
	public String getPreStartDate() {
		return preStartDate;
	}

	/**
	 * @param preStartDate the preStartDate to set
	 */
	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
	}

	/**
	 * @return the preEndDate
	 */
	public String getPreEndDate() {
		return preEndDate;
	}

	/**
	 * @param preEndDate the preEndDate to set
	 */
	public void setPreEndDate(String preEndDate) {
		this.preEndDate = preEndDate;
	}

	/**
	 * @return the realStartDate
	 */
	public String getRealStartDate() {
		return realStartDate;
	}

	/**
	 * @param realStartDate the realStartDate to set
	 */
	public void setRealStartDate(String realStartDate) {
		this.realStartDate = realStartDate;
	}

	/**
	 * @return the realEndDate
	 */
	public String getRealEndDate() {
		return realEndDate;
	}

	/**
	 * @param realEndDate the realEndDate to set
	 */
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
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
	 * @return the projectStateDetail
	 */
	public String getProjectStateDetail() {
		return projectStateDetail;
	}

	/**
	 * @param projectStateDetail the projectStateDetail to set
	 */
	public void setProjectStateDetail(String projectStateDetail) {
		this.projectStateDetail = projectStateDetail;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createrSsn
	 */
	public String getCreaterSsn() {
		return createrSsn;
	}

	/**
	 * @param createrSsn the createrSsn to set
	 */
	public void setCreaterSsn(String createrSsn) {
		this.createrSsn = createrSsn;
	}

	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the modifySsn
	 */
	public String getModifySsn() {
		return modifySsn;
	}

	/**
	 * @param modifySsn the modifySsn to set
	 */
	public void setModifySsn(String modifySsn) {
		this.modifySsn = modifySsn;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the customerWorkPName
	 */
	public String getCustomerWorkPName() {
		return customerWorkPName;
	}

	/**
	 * @param customerWorkPName the customerWorkPName to set
	 */
	public void setCustomerWorkPName(String customerWorkPName) {
		this.customerWorkPName = customerWorkPName;
	}

	/**
	 * @return the customerWorkPEmail
	 */
	public String getCustomerWorkPEmail() {
		return customerWorkPEmail;
	}

	/**
	 * @param customerWorkPEmail the customerWorkPEmail to set
	 */
	public void setCustomerWorkPEmail(String customerWorkPEmail) {
		this.customerWorkPEmail = customerWorkPEmail;
	}

	/**
	 * @return the customerWorkPPhone
	 */
	public String getCustomerWorkPPhone() {
		return customerWorkPPhone;
	}

	/**
	 * @param customerWorkPPhone the customerWorkPPhone to set
	 */
	public void setCustomerWorkPPhone(String customerWorkPPhone) {
		this.customerWorkPPhone = customerWorkPPhone;
	}

	/**
	 * @return the customerContPName
	 */
	public String getCustomerContPName() {
		return customerContPName;
	}

	/**
	 * @param customerContPName the customerContPName to set
	 */
	public void setCustomerContPName(String customerContPName) {
		this.customerContPName = customerContPName;
	}

	/**
	 * @return the customerContPEmail
	 */
	public String getCustomerContPEmail() {
		return customerContPEmail;
	}

	/**
	 * @param customerContPEmail the customerContPEmail to set
	 */
	public void setCustomerContPEmail(String customerContPEmail) {
		this.customerContPEmail = customerContPEmail;
	}

	/**
	 * @return the customerContPPhone
	 */
	public String getCustomerContPPhone() {
		return customerContPPhone;
	}

	/**
	 * @param customerContPPhone the customerContPPhone to set
	 */
	public void setCustomerContPPhone(String customerContPPhone) {
		this.customerContPPhone = customerContPPhone;
	}

	/**
	 * @return the endGubun
	 */
	public String getEndGubun() {
		return endGubun;
	}

	/**
	 * @param endGubun the endGubun to set
	 */
	public void setEndGubun(String endGubun) {
		this.endGubun = endGubun;
	}

	/**
	 * @return the endReason
	 */
	public String getEndReason() {
		return endReason;
	}

	/**
	 * @param endReason the endReason to set
	 */
	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}

	/**
	 * @return the endRate
	 */
	public String getEndRate() {
		return endRate;
	}

	/**
	 * @param endRate the endRate to set
	 */
	public void setEndRate(String endRate) {
		this.endRate = endRate;
	}

	/**
	 * @return the groupComment
	 */
	public String getGroupComment() {
		return groupComment;
	}

	/**
	 * @param groupComment the groupComment to set
	 */
	public void setGroupComment(String groupComment) {
		this.groupComment = groupComment;
	}

	/**
	 * @return the cfoComment
	 */
	public String getCfoComment() {
		return cfoComment;
	}

	/**
	 * @param cfoComment the cfoComment to set
	 */
	public void setCfoComment(String cfoComment) {
		this.cfoComment = cfoComment;
	}

	/**
	 * @return the cboComment
	 */
	public String getCboComment() {
		return cboComment;
	}

	/**
	 * @param cboComment the cboComment to set
	 */
	public void setCboComment(String cboComment) {
		this.cboComment = cboComment;
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
	 * @return the projectContractType
	 */
	public String getProjectContractType() {
		return projectContractType;
	}

	/**
	 * @param projectContractType the projectContractType to set
	 */
	public void setProjectContractType(String projectContractType) {
		this.projectContractType = projectContractType;
	}

	/**
	 * @return the parentProjectCode
	 */
	public String getParentProjectCode() {
		return parentProjectCode;
	}

	/**
	 * @param parentProjectCode the parentProjectCode to set
	 */
	public void setParentProjectCode(String parentProjectCode) {
		this.parentProjectCode = parentProjectCode;
	}

	/**
	 * @return the payCostOver
	 */
	public String getPayCostOver() {
		return payCostOver;
	}

	/**
	 * @param payCostOver the payCostOver to set
	 */
	public void setPayCostOver(String payCostOver) {
		this.payCostOver = payCostOver;
	}

	/**
	 * @return the etcCostOver
	 */
	public String getEtcCostOver() {
		return etcCostOver;
	}

	/**
	 * @param etcCostOver the etcCostOver to set
	 */
	public void setEtcCostOver(String etcCostOver) {
		this.etcCostOver = etcCostOver;
	}

	/**
	 * @return the endBackground
	 */
	public String getEndBackground() {
		return endBackground;
	}

	/**
	 * @param endBackground the endBackground to set
	 */
	public void setEndBackground(String endBackground) {
		this.endBackground = endBackground;
	}

	/**
	 * @return the endResult
	 */
	public String getEndResult() {
		return endResult;
	}

	/**
	 * @param endResult the endResult to set
	 */
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}

	/**
	 * @return the endReview
	 */
	public String getEndReview() {
		return endReview;
	}

	/**
	 * @param endReview the endReview to set
	 */
	public void setEndReview(String endReview) {
		this.endReview = endReview;
	}

	/**
	 * @return the endSuggestion
	 */
	public String getEndSuggestion() {
		return endSuggestion;
	}

	/**
	 * @param endSuggestion the endSuggestion to set
	 */
	public void setEndSuggestion(String endSuggestion) {
		this.endSuggestion = endSuggestion;
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
	 * @return the isEvaluate
	 */
	public String getIsEvaluate() {
		return isEvaluate;
	}

	/**
	 * @param isEvaluate the isEvaluate to set
	 */
	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	/**
	 * @return the projectCsrInfoList
	 */
	public List<ProjectCsrInfo> getProjectCsrInfoList() {
		return projectCsrInfoList;
	}

	/**
	 * @param projectCsrInfoList the projectCsrInfoList to set
	 */
	public void setProjectCsrInfoList(List<ProjectCsrInfo> projectCsrInfoList) {
		this.projectCsrInfoList = projectCsrInfoList;
	}

	/**
	 * @return the projectMemberList
	 */
	public List<ProjectMember> getProjectMemberList() {
		return projectMemberList;
	}

	/**
	 * @param projectMemberList the projectMemberList to set
	 */
	public void setProjectMemberList(List<ProjectMember> projectMemberList) {
		this.projectMemberList = projectMemberList;
	}

	/**
	 * @return the runningPuCode
	 */
	public String getRunningPuCode() {
		return runningPuCode;
	}

	/**
	 * @param runningPuCode the runningPuCode to set
	 */
	public void setRunningPuCode(String runningPuCode) {
		this.runningPuCode = runningPuCode;
	}

	/**
	 * @return the runningGroupCode
	 */
	public String getRunningGroupCode() {
		return runningGroupCode;
	}

	/**
	 * @param runningGroupCode the runningGroupCode to set
	 */
	public void setRunningGroupCode(String runningGroupCode) {
		this.runningGroupCode = runningGroupCode;
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

	public String getIsEduConnected() {
		return isEduConnected;
	}

	public void setIsEduConnected(String isEduConnected) {
		this.isEduConnected = isEduConnected;
	}

	/**
	 * @return the businessFunctionType
	 */
	public String getBusinessFunctionType() {
		return businessFunctionType;
	}

	/**
	 * @param businessFunctionType the businessFunctionType to set
	 */
	public void setBusinessFunctionType(String businessFunctionType) {
		this.businessFunctionType = businessFunctionType;
	}

	/**
	 * @return the coGRP
	 */
	public String getCoGRP() {
		return coGRP;
	}

	/**
	 * @param coGRP the coGRP to set
	 */
	public void setCoGRP(String coGRP) {
		this.coGRP = coGRP;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
