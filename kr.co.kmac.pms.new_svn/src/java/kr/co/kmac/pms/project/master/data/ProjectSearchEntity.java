package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProjectSearchEntity {

	private String projectCode;
	private String projectName;
	private String projectTypeCode;
	private String projectDetailCode;
	private String businessTypeCode;
	private String processTypeCode;
	private String cioTypeCode;
	private String runningDivCode;
	private String runningDeptCode;
	private String industryTypeCode;
	private String realStartDate;
	private String realEndDate;
	private String projectState;
	private String endGubun;
	private String costOver;
	private String payCostOver;
	private String etcCostOver;
	private String preStartDate;
	private String preEndDate;
	private String businessTypeCodeName;
	private String runningDivCodeName;
	private String runningDeptCodeName;
	private String pmSsn;
	private String plSsn;
	private String runningPUCode;
	private String boardArticleCount;
	private String delayState;
	private String adminOpen;

	private String pmname;
	private String boardArticleCountQM;
	
	private String attach;
	private String parentProjectCode;
	private String customerName;
	private String businessFunctionType;
	
	public String getBusinessFunctionType() {
		return businessFunctionType;
	}

	public void setBusinessFunctionType(String businessFunctionType) {
		this.businessFunctionType = businessFunctionType;
	}
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

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

	public String getPmname(){
		return pmname;
	}
	
	public void setPmname(String pmname){
		this.pmname = pmname;
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

	public String getProjectTypeCode() {
		return projectTypeCode;
	}

	public void setProjectTypeCode(String projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}

	public String getProjectDetailCode() {
		return projectDetailCode;
	}

	public void setProjectDetailCode(String projectDetailCode) {
		this.projectDetailCode = projectDetailCode;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String getProcessTypeCode() {
		return processTypeCode;
	}

	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	public String getCioTypeCode() {
		return cioTypeCode;
	}

	public void setCioTypeCode(String cioTypeCode) {
		this.cioTypeCode = cioTypeCode;
	}

	public String getRunningDivCode() {
		return runningDivCode;
	}

	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	public String getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(String realStartDate) {
		this.realStartDate = realStartDate;
	}

	public String getRealEndDate() {
		return realEndDate;
	}

	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public String getEndGubun() {
		return endGubun;
	}

	public void setEndGubun(String endGubun) {
		this.endGubun = endGubun;
	}

	public String getCostOver() {
		return costOver;
	}

	public void setCostOver(String costOver) {
		this.costOver = costOver;
	}

	public String getPayCostOver() {
		return payCostOver;
	}

	public void setPayCostOver(String payCostOver) {
		this.payCostOver = payCostOver;
	}

	public String getEtcCostOver() {
		return etcCostOver;
	}

	public void setEtcCostOver(String etcCostOver) {
		this.etcCostOver = etcCostOver;
	}

	public String getPreStartDate() {
		return preStartDate;
	}

	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
	}

	public String getPreEndDate() {
		return preEndDate;
	}

	public void setPreEndDate(String preEndDate) {
		this.preEndDate = preEndDate;
	}

	public String getBusinessTypeCodeName() {
		return businessTypeCodeName;
	}

	public void setBusinessTypeCodeName(String businessTypeCodeName) {
		this.businessTypeCodeName = businessTypeCodeName;
	}

	public String getRunningDivCodeName() {
		return runningDivCodeName;
	}

	public void setRunningDivCodeName(String runningDivCodeName) {
		this.runningDivCodeName = runningDivCodeName;
	}

	public String getPmSsn() {
		return pmSsn;
	}

	public void setPmSsn(String pmSsn) {
		this.pmSsn = pmSsn;
	}

	public String getPlSsn() {
		return plSsn;
	}

	public void setPlSsn(String plSsn) {
		this.plSsn = plSsn;
	}

	public String getRunningPUCode() {
		return runningPUCode;
	}

	public void setRunningPUCode(String runningPUCode) {
		this.runningPUCode = runningPUCode;
	}

	public String getBoardArticleCount() {
		return boardArticleCount;
	}

	public void setBoardArticleCount(String boardArticleCount) {
		this.boardArticleCount = boardArticleCount;
	}

	public String getDelayState() {
		return delayState;
	}

	public void setDelayState(String delayState) {
		this.delayState = delayState;
	}	

	public String getAdminOpen() {
		return adminOpen;
	}

	public void setAdminOpen(String adminOpen) {
		this.adminOpen = adminOpen;
	}

	public String getRunningDeptCode() {
		return runningDeptCode;
	}

	public void setRunningDeptCode(String runningDeptCode) {
		this.runningDeptCode = runningDeptCode;
	}

	public String getRunningDeptCodeName() {
		return runningDeptCodeName;
	}

	public void setRunningDeptCodeName(String runningDeptCodeName) {
		this.runningDeptCodeName = runningDeptCodeName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
