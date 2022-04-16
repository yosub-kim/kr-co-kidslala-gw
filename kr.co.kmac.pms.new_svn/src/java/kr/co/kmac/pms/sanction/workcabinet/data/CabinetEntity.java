package kr.co.kmac.pms.sanction.workcabinet.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import kr.co.kmac.pms.common.worklist.data.Work;

public class CabinetEntity extends Work {
	private String approvalName;
	private String projectName;
	private String writeDate;
	private String assignDate;
	private String payAmount;
	private String assignYear;
	private String assignMonth;
	private String assignWeekOfMonth;
	private String projectCode;
	private String useMobile;
	
	public String getUseMobile() {
		return useMobile;
	}

	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the approvalName
	 */
	public String getApprovalName() {
		return approvalName;
	}

	/**
	 * @param approvalName
	 *            the approvalName to set
	 */
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the writeDate
	 */
	public String getWriteDate() {
		return writeDate;
	}

	/**
	 * @param writeDate
	 *            the writeDate to set
	 */
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return the payAmount
	 */
	public String getPayAmount() {
		return payAmount;
	}

	/**
	 * @param payAmount
	 *            the payAmount to set
	 */
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getAssignYear() {
		return assignYear;
	}

	public void setAssignYear(String assignYear) {
		this.assignYear = assignYear;
	}

	public String getAssignMonth() {
		return assignMonth;
	}

	public void setAssignMonth(String assignMonth) {
		this.assignMonth = assignMonth;
	}

	public String getAssignWeekOfMonth() {
		return assignWeekOfMonth;
	}

	public void setAssignWeekOfMonth(String assignWeekOfMonth) {
		this.assignWeekOfMonth = assignWeekOfMonth;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
