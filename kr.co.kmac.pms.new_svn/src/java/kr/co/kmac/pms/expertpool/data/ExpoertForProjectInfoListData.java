/*
 * $Id: ExpoertForProjectInfoListData.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

public class ExpoertForProjectInfoListData {
	private String projectCode;
	private String customerName; 
	private String projectName; 
	private String bussinessTypeName;  
	private String runningDivName; 
	private String realStartDate; 
	private String realEndDate;
	
	public String getBussinessTypeName() {
		return bussinessTypeName;
	}
	public void setBussinessTypeName(String bussinessTypeName) {
		this.bussinessTypeName = bussinessTypeName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public String getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(String realEndDate) {
		this.realEndDate = realEndDate;
	}
	public String getRealStartDate() {
		return realStartDate;
	}
	public void setRealStartDate(String realStartDate) {
		this.realStartDate = realStartDate;
	}
	public String getRunningDivName() {
		return runningDivName;
	}
	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}
}
