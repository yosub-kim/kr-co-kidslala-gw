/*
 * $Id: ExpenseRealTimeResult.java,v 1.5 2016/12/03 14:21:14 cvs Exp $
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
 * @version $Id: ExpenseRealTimeResult.java,v 1.5 2016/12/03 14:21:14 cvs Exp $
 */
public class ExpenseRealTimeResult {

	private String projectCode;
	private String projectName;
	private String name;
	private String ssn;
	private Long realTimeSalary;
	private String involvedPrjCnt;
	private String companyPositionName;
	private String rate;
	private String role;
	private String deptCode;
	private String deptName;
	private String isExceed;
	private String year;
	private String month;
	private String date;
	private String salarySum;
	private String mmValue;
	private String allCount;
	private String userId;
	private String labelName;
	private String posName;
	private String amountSum;
	private String projectCnt;
	private String userName;
	private String uid;
	private String dept;
	private String amount;
	private String amount2;
	private String pm;
	private Long salarySum2;
	private Long realTimeSalary2;
	private String isSanction;
	private String isSanction2;
	
	public String getIsSanction() {
		return isSanction;
	}

	public void setIsSanction(String isSanction) {
		this.isSanction = isSanction;
	}

	public String getIsSanction2() {
		return isSanction2;
	}

	public void setIsSanction2(String isSanction2) {
		this.isSanction2 = isSanction2;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public Long getRealTimeSalary2() {
		return realTimeSalary2;
	}

	public void setRealTimeSalary2(Long realTimeSalary2) {
		this.realTimeSalary2 = realTimeSalary2;
	}

	public Long getSalarySum2() {
		return salarySum2;
	}

	public void setSalarySum2(Long salarySum2) {
		this.salarySum2 = salarySum2;
	}

	public String getSalarySum() {
		return salarySum;
	}

	public void setSalarySum(String salarySum) {
		this.salarySum = salarySum;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount2() {
		return amount2;
	}

	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}

	public String getProjectCnt() {
		return projectCnt;
	}

	public void setProjectCnt(String projectCnt) {
		this.projectCnt = projectCnt;
	}

	public String getAllCount() {
		return allCount;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMmValue() {
		return mmValue;
	}

	public void setMmValue(String mmValue) {
		this.mmValue = mmValue;
	}


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
	 * @return Returns the deptCode.
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode The deptCode to set.
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return Returns the deptName.
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName The deptName to set.
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return Returns the involvedPrjCnt.
	 */
	public String getInvolvedPrjCnt() {
		return this.involvedPrjCnt;
	}

	/**
	 * @param involvedPrjCnt The involvedPrjCnt to set.
	 */
	public void setInvolvedPrjCnt(String involvedPrjCnt) {
		this.involvedPrjCnt = involvedPrjCnt;
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

	/**
	 * @return Returns the rate.
	 */
	public String getRate() {
		return this.rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the role.
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * @param role The role to set.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the realTimeSalary.
	 */
	public Long getRealTimeSalary() {
		return this.realTimeSalary;
	}

	/**
	 * @param realTimeSalary The realTimeSalary to set.
	 */
	public void setRealTimeSalary(Long realTimeSalary) {
		this.realTimeSalary = realTimeSalary;
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

	/**
	 * @return the isExceed
	 */
	public String getIsExceed() {
		return isExceed;
	}

	/**
	 * @param isExceed the isExceed to set
	 */
	public void setIsExceed(String isExceed) {
		this.isExceed = isExceed;
	}

}
