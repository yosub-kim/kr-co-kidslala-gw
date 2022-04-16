/*
 * $Id: CustomerSatisfactionReportSummary.java,v 1.1 2012/03/27 15:41:44 cvs Exp $
 * created by    : yhyim
 * creation-date : 2012. 3. 26.
 * =========================================================
 * Copyright KMAC. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

public class CustomerSatisfactionReportSummary {
	private String businessTypeCode;
	private String businessTypeName;
	private String runningDivCode;
	private String runningDivName;
	private String high;
	private String medium;
	private String low;
	private String delayCnt;
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
	 * @return the runningDivName
	 */
	public String getRunningDivName() {
		return runningDivName;
	}
	/**
	 * @param runningDivName the runningDivName to set
	 */
	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}
	/**
	 * @return the high
	 */
	public String getHigh() {
		return high;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(String high) {
		this.high = high;
	}
	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}
	/**
	 * @param medium the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}
	/**
	 * @return the low
	 */
	public String getLow() {
		return low;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(String low) {
		this.low = low;
	}
	/**
	 * @return the delayCnt
	 */
	public String getDelayCnt() {
		return delayCnt;
	}
	/**
	 * @param delayCnt the delayCnt to set
	 */
	public void setDelayCnt(String delayCnt) {
		this.delayCnt = delayCnt;
	}
}