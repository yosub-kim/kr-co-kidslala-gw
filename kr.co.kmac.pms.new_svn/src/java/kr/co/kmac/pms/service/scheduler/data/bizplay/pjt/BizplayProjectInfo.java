package kr.co.kmac.pms.service.scheduler.data.bizplay.pjt;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BizplayProjectInfo {

	private String projectCode;
	private String projectName;
	private String projectState;
	private String companyCode;
	private String bsnnNo;

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

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBsnnNo() {
		return bsnnNo;
	}

	public void setBsnnNo(String bsnnNo) {
		this.bsnnNo = bsnnNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
