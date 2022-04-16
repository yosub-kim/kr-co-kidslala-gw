package kr.co.kmac.pms.project.master.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RegisterProject {

	private String projectCode;
	private String projectName;
	private String industryTypeCode;
	private String industryTypeName;
	private String businessTypeCode;
	private String businessTypeName;
	private String projectState;
	private Date createDate;
	private String createrSsn;
	private String createrName;

	private String isReject;

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
	 * @return the industryTypeName
	 */
	public String getIndustryTypeName() {
		return industryTypeName;
	}

	/**
	 * @param industryTypeName the industryTypeName to set
	 */
	public void setIndustryTypeName(String industryTypeName) {
		this.industryTypeName = industryTypeName;
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
	 * @return the createrName
	 */
	public String getCreaterName() {
		return createrName;
	}

	/**
	 * @param createrName the createrName to set
	 */
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getIsReject() {
		return isReject;
	}

	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
