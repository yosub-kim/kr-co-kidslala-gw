/*
 * $Id: CustomerSatisfactionReportList.java,v 1.4 2015/12/01 08:08:04 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom; Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

public class CustomerSatisfactionReportList {
	private String projectCode;
	private String projectName;
	private String businessTypeCode;
	private String businessTypeName;
	private String processTypeCode;
	private String runningDivCode;
	private String runningDivName;
	private String seq;
	private String customerName;
	private String receiveEmail;
	private String writeDate;
	
	public String getRunningDivName() {
		return runningDivName;
	}

	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}
	private String ratio;

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
	
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	public String getProcessTypeCode() {
		return processTypeCode;
	}

	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
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
	 * @return Returns the ratio.
	 */
	public String getRatio() {
		return this.ratio;
	}
	/**
	 * @param ratio The ratio to set.
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
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
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
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
	 * @return the receiveEmail
	 */
	public String getReceiveEmail() {
		return receiveEmail;
	}

	/**
	 * @param receiveEmail the receiveEmail to set
	 */
	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

}