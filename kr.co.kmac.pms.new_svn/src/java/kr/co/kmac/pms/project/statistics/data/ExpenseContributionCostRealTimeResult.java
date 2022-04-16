/*
 * $Id: ExpenseContributionCostRealTimeResult.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 * created by    : yhyim
 * creation-date : 2009. 5. 7.
 * =========================================================
 * Copyright (c) 2009 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

/**
 * TODO 클래스 설명
 * @author yhyim
 * @version $Id: ExpenseContributionCostRealTimeResult.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 */
public class ExpenseContributionCostRealTimeResult {

	private String dept;
	private String name;
	private String ssn;
	private String projectName;
	private String realWorkCount;
	private String realTimeSalary;
	private String totalContributionCost;
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRealWorkCount() {
		return realWorkCount;
	}
	public void setRealWorkCount(String realWorkCount) {
		this.realWorkCount = realWorkCount;
	}
	public String getRealTimeSalary() {
		return realTimeSalary;
	}
	public void setRealTimeSalary(String realTimeSalary) {
		this.realTimeSalary = realTimeSalary;
	}
	public String getTotalContributionCost() {
		return totalContributionCost;
	}
	public void setTotalContributionCost(String totalContributionCost) {
		this.totalContributionCost = totalContributionCost;
	}
}