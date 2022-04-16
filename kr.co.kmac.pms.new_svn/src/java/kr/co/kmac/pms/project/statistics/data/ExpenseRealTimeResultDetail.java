/*
 * $Id: ExpenseRealTimeResultDetail.java,v 1.4 2011/11/03 05:06:06 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 10. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseRealTimeResultDetail.java,v 1.4 2011/11/03 05:06:06 cvs Exp $
 */
public class ExpenseRealTimeResultDetail extends ExpenseRealTimeResult {

	private String projectCode;
	private String projectName;
	private String assignDate;
	private String cost;
	private String reportRate;
	private String resRate;
	private String manDay;
	private String eachSalary;	
	private String totalRealTimeSalary;
	private String businessTypeCode;
	private String isSanction;
	private String companyPositionName;
	private String year;
	private String month;
	private String ssnCnt;
	private String projectCnt;
	private String sumcost;
	
	public String getSumCost(){
		return sumcost;
	}
	
	public void setSumCost(String sumcost){
		this.sumcost = sumcost;
	}
	
	public String getSsnCnt(){
		return ssnCnt;
	}
	
	public void setSsnCnt(String ssnCnt){
		this.ssnCnt = ssnCnt;
	}
	
	public String getProjectCnt(){
		return projectCnt;
	}
	
	public void setProjectCnt(String projectCnt){
		this.projectCnt = projectCnt;
	}
	
	public String getYear(){
		return year;
	}
	
	public void setYear(String year){
		this.year = year;
	}
	
	public String getMonth(){
		return month;
	}
	
	public void setMonth(String month){
		this.month = month;
	}

	/**
	 * @return the manDay
	 */
	public String getManDay() {
		return manDay;
	}

	/**
	 * @param manDay the manDay to set
	 */
	public void setManDay(String manDay) {
		this.manDay = manDay;
	}

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

	/**
	 * @return Returns the assignDate.
	 */
	public String getAssignDate() {
		return this.assignDate;
	}

	/**
	 * @param assignDate The assignDate to set.
	 */
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return Returns the totalRealTimeSalary.
	 */
	public String getTotalRealTimeSalary() {
		return this.totalRealTimeSalary;
	}

	/**
	 * @param totalRealTimeSalary The totalRealTimeSalary to set.
	 */
	public void setTotalRealTimeSalary(String totalRealTimeSalary) {
		this.totalRealTimeSalary = totalRealTimeSalary;
	}

	/**
	 * @return Returns the cost.
	 */
	public String getCost() {
		return this.cost;
	}

	/**
	 * @param cost The cost to set.
	 */
	public void setCost(String cost) {
		this.cost = cost;
	}

	/**
	 * @return Returns the eachSalary.
	 */
	public String getEachSalary() {
		return this.eachSalary;
	}

	/**
	 * @param eachSalary The eachSalary to set.
	 */
	public void setEachSalary(String eachSalary) {
		this.eachSalary = eachSalary;
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
	 * @return Returns the reportRate.
	 */
	public String getReportRate() {
		return this.reportRate;
	}

	/**
	 * @param reportRate The reportRate to set.
	 */
	public void setReportRate(String reportRate) {
		this.reportRate = reportRate;
	}

	/**
	 * @return the resRate
	 */
	public String getResRate() {
		return resRate;
	}

	/**
	 * @param resRate the resRate to set
	 */
	public void setResRate(String resRate) {
		this.resRate = resRate;
	}

	/**
	 * @return the isSanction
	 */
	public String getIsSanction() {
		return isSanction;
	}

	/**
	 * @param isSanction the isSanction to set
	 */
	public void setIsSanction(String isSanction) {
		this.isSanction = isSanction;
	}

	/**
	 * @return the companyPositionName
	 */
	public String getCompanyPositionName() {
		return companyPositionName;
	}

	/**
	 * @param companyPositionName the companyPositionName to set
	 */
	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}
}
