package kr.co.kmac.pms.project.master.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * @struts:form name="projectMasterInfoAction"
 */
public class ProjectMasterInfoForm extends ActionForm {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7557302939987520378L;

	private String entNo;

	private String projectCode;
	private String projectState;
	private String processTypeCode;
	private String projectDetailCode;
	private String businessFunctionType;
	private String isVoc;
	private String isEvaluate;

	private String customerWorkPName;
	private String customerWorkPEmail;
	private String customerWorkPPhone;
	private String customerContPName;
	private String customerContPEmail;
	private String customerContPPhone;

	private String[] memberSsn;
	private String[] memberName;
	private String[] memberPosition;
	private String[] memberRole;
	private String[] memberResRate;
	private String[] memberCost;
	private String[] memberTrainingYn;
	private String[] memberContributionCost;
	private String[] memberStartDate;
	private String[] memberEndDate;

	private String orgCodes;
	private String orgNames;

	private String lang;
	private String projectSubName;
	private String isEduConnected;

	private String isRefresh = "false";

	public String getEntNo() {
		return entNo;
	}

	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public String getProcessTypeCode() {
		return processTypeCode;
	}

	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	public String getProjectDetailCode() {
		return projectDetailCode;
	}

	public void setProjectDetailCode(String projectDetailCode) {
		this.projectDetailCode = projectDetailCode;
	}

	public String getIsVoc() {
		return isVoc;
	}

	public void setIsVoc(String isVoc) {
		this.isVoc = isVoc;
	}

	public String getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public String getCustomerWorkPName() {
		return customerWorkPName;
	}

	public void setCustomerWorkPName(String customerWorkPName) {
		this.customerWorkPName = customerWorkPName;
	}

	public String getCustomerWorkPEmail() {
		return customerWorkPEmail;
	}

	public void setCustomerWorkPEmail(String customerWorkPEmail) {
		this.customerWorkPEmail = customerWorkPEmail;
	}

	public String getCustomerWorkPPhone() {
		return customerWorkPPhone;
	}

	public void setCustomerWorkPPhone(String customerWorkPPhone) {
		this.customerWorkPPhone = customerWorkPPhone;
	}

	public String getCustomerContPName() {
		return customerContPName;
	}

	public void setCustomerContPName(String customerContPName) {
		this.customerContPName = customerContPName;
	}

	public String getCustomerContPEmail() {
		return customerContPEmail;
	}

	public void setCustomerContPEmail(String customerContPEmail) {
		this.customerContPEmail = customerContPEmail;
	}

	public String getCustomerContPPhone() {
		return customerContPPhone;
	}

	public void setCustomerContPPhone(String customerContPPhone) {
		this.customerContPPhone = customerContPPhone;
	}

	public String[] getMemberSsn() {
		return memberSsn;
	}

	public void setMemberSsn(String[] memberSsn) {
		this.memberSsn = memberSsn;
	}

	public String[] getMemberName() {
		return memberName;
	}

	public void setMemberName(String[] memberName) {
		this.memberName = memberName;
	}

	public String[] getMemberPosition() {
		return memberPosition;
	}

	public void setMemberPosition(String[] memberPosition) {
		this.memberPosition = memberPosition;
	}

	public String[] getMemberRole() {
		return memberRole;
	}

	public void setMemberRole(String[] memberRole) {
		this.memberRole = memberRole;
	}

	public String[] getMemberResRate() {
		return memberResRate;
	}

	public void setMemberResRate(String[] memberResRate) {
		this.memberResRate = memberResRate;
	}

	public String[] getMemberCost() {
		return memberCost;
	}

	public void setMemberCost(String[] memberCost) {
		this.memberCost = memberCost;
	}

	public String[] getMemberContributionCost() {
		return memberContributionCost;
	}

	public void setMemberContributionCost(String[] memberContributionCost) {
		this.memberContributionCost = memberContributionCost;
	}

	
	public String[] getMemberStartDate() {
		return memberStartDate;
	}

	public void setMemberStartDate(String[] memberStartDate) {
		this.memberStartDate = memberStartDate;
	}

	public String[] getMemberEndDate() {
		return memberEndDate;
	}

	public void setMemberEndDate(String[] memberEndDate) {
		this.memberEndDate = memberEndDate;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getOrgNames() {
		return orgNames;
	}

	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	public String[] getMemberTrainingYn() {
		return memberTrainingYn;
	}

	public void setMemberTrainingYn(String[] memberTrainingYn) {
		this.memberTrainingYn = memberTrainingYn;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public String getIsEduConnected() {
		return isEduConnected;
	}

	public void setIsEduConnected(String isEduConnected) {
		this.isEduConnected = isEduConnected;
	}

	public String getIsRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(String isRefresh) {
		this.isRefresh = isRefresh;
	}

	public String getBusinessFunctionType() {
		return businessFunctionType;
	}

	public void setBusinessFunctionType(String businessFunctionType) {
		this.businessFunctionType = businessFunctionType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
