
package kr.co.kmac.pms.project.statistics.data;

public class ProjectMemberEvalListDetail {
	private String projectCode;
	private String projectName;
	private String realEndDate;
	private String ssn;
	private String name;	
	private String companyPosition;
	private String businessTypeCode;
	private String businessType;
	private String item;
	private String total;
	private String writeDate;
	private String writerSsn;
	private String writerName;

	public String getWriterSsn() {
		return writerSsn;
	}
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * private String runningDivCode; private String realStartDate; private String realEndDate;
	 */

	/**
	 * @return Returns the businessType.
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	/**
	 * @param businessType The businessType to set.
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * @return Returns the total.
	 */
	public String getTotal() {
		return this.total;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(String total) {
		this.total = total;
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
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return this.ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public String getCompanyPosition() {
		return companyPosition;
	}
	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
}