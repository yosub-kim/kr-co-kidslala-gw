package kr.co.kmac.pms.project.summary.data;

import org.apache.struts.action.ActionForm;

public class ProjectSummaryForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String projectCode;
	private String projectName;
	private String runningDivCode;
	private String customerName;
	private String customerContPName;
	private String industryTypeCode;
	private String realStartDate;
	private String realEndDate;
	private String businessTypeCode;
	private String projectTypeCode;
	private String pmSsn;
	private String pmName;
	private String plSsn;
	private String plName;
	private String memberName;
	private String endBackground;
	private String endResult;
	private String endReview;
	private String endSuggestion;
	
	private String attachIsEssential;
	private String attachOutputBusType;
	private String attachOutputType;
	private String attachOutputName;
	private String attachFileName;
	private String attachCreateDate;
	private String attachCreatorName;
	private String attachCount;
	
	private String reportTitle;
	private String writeName;
	private String writeDate;
	public String getAttachCount() {
		return attachCount;
	}
	public void setAttachCount(String attachCount) {
		this.attachCount = attachCount;
	}
	public String getAttachCreateDate() {
		return attachCreateDate;
	}
	public void setAttachCreateDate(String attachCreateDate) {
		this.attachCreateDate = attachCreateDate;
	}
	public String getAttachCreatorName() {
		return attachCreatorName;
	}
	public void setAttachCreatorName(String attachCreatorName) {
		this.attachCreatorName = attachCreatorName;
	}
	public String getAttachFileName() {
		return attachFileName;
	}
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	public String getAttachIsEssential() {
		return attachIsEssential;
	}
	public void setAttachIsEssential(String attachIsEssential) {
		this.attachIsEssential = attachIsEssential;
	}
	public String getAttachOutputBusType() {
		return attachOutputBusType;
	}
	public void setAttachOutputBusType(String attachOutputBusType) {
		this.attachOutputBusType = attachOutputBusType;
	}
	public String getAttachOutputName() {
		return attachOutputName;
	}
	public void setAttachOutputName(String attachOutputName) {
		this.attachOutputName = attachOutputName;
	}
	public String getAttachOutputType() {
		return attachOutputType;
	}
	public void setAttachOutputType(String attachOutputType) {
		this.attachOutputType = attachOutputType;
	}
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public String getCustomerContPName() {
		return customerContPName;
	}
	public void setCustomerContPName(String customerContPName) {
		this.customerContPName = customerContPName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEndBackground() {
		return endBackground;
	}
	public void setEndBackground(String endBackground) {
		this.endBackground = endBackground;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getEndReview() {
		return endReview;
	}
	public void setEndReview(String endReview) {
		this.endReview = endReview;
	}
	public String getEndSuggestion() {
		return endSuggestion;
	}
	public void setEndSuggestion(String endSuggestion) {
		this.endSuggestion = endSuggestion;
	}
	public String getIndustryTypeCode() {
		return industryTypeCode;
	}
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPlName() {
		return plName;
	}
	public void setPlName(String plName) {
		this.plName = plName;
	}
	public String getPlSsn() {
		return plSsn;
	}
	public void setPlSsn(String plSsn) {
		this.plSsn = plSsn;
	}
	public String getPmName() {
		return pmName;
	}
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	public String getPmSsn() {
		return pmSsn;
	}
	public void setPmSsn(String pmSsn) {
		this.pmSsn = pmSsn;
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
	public String getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}
	public String getRealStartDate() {
		return realStartDate;
	}
	public void setRealStartDate(String realStateDate) {
		this.realStartDate = realStateDate;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getRunningDivCode() {
		return runningDivCode;
	}
	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getWriteName() {
		return writeName;
	}
	public void setWriteName(String writeName) {
		this.writeName = writeName;
	}
}