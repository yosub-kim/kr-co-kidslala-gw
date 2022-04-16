/*
 * $Id: ExpenseResultForCostOver.java,v 1.3 2011/10/24 10:08:25 cvs Exp $
 * created by    : 오영택
 * creation-date : 2009. 8. 24.
 * =========================================================
 * Copyright (c) KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseResultForCostOver.java,v 1.1 2009/09/19 11:15:35 cvs3
 *          Exp $
 */
public class ExpenseResultForCostOver {

	private int runningDivCodeCnt;
	private int projectCodeCnt;
	private String runningDivCode;
	private String runningDivName;
	private String projectCode;
	private String projectName;
	private String acctCode;
	private String planAmount;
	private String totalAmount;
	private String exeAmount;
	private String realtimeSalary;
	// private String realTimeSalaryAddedBasicSalary;
	private String grandPlanAmount;
	private String grandTotalAmount;
	private String grandExeAmount;
	private String grandRealtimeSalary;
	// private String grandRealTimeSalaryAddedBasicSalary;
	private String isOverYn;

	public int getRunningDivCodeCnt() {
		return runningDivCodeCnt;
	}

	public void setRunningDivCodeCnt(int runningDivCodeCnt) {
		this.runningDivCodeCnt = runningDivCodeCnt;
	}

	public int getProjectCodeCnt() {
		return projectCodeCnt;
	}

	public void setProjectCodeCnt(int projectCodeCnt) {
		this.projectCodeCnt = projectCodeCnt;
	}

	public String getRunningDivCode() {
		return runningDivCode;
	}

	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	public String getRunningDivName() {
		return runningDivName;
	}

	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
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

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getPlanAmount() {
		return planAmount;
	}

	public void setPlanAmount(String planAmount) {
		this.planAmount = planAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getExeAmount() {
		return exeAmount;
	}

	public void setExeAmount(String exeAmount) {
		this.exeAmount = exeAmount;
	}

	public String getRealtimeSalary() {
		return realtimeSalary;
	}

	public void setRealtimeSalary(String realtimeSalary) {
		this.realtimeSalary = realtimeSalary;
	}

	public String getGrandPlanAmount() {
		return grandPlanAmount;
	}

	public void setGrandPlanAmount(String grandPlanAmount) {
		this.grandPlanAmount = grandPlanAmount;
	}

	public String getGrandTotalAmount() {
		return grandTotalAmount;
	}

	public void setGrandTotalAmount(String grandTotalAmount) {
		this.grandTotalAmount = grandTotalAmount;
	}

	public String getGrandExeAmount() {
		return grandExeAmount;
	}

	public void setGrandExeAmount(String grandExeAmount) {
		this.grandExeAmount = grandExeAmount;
	}

	public String getGrandRealtimeSalary() {
		return grandRealtimeSalary;
	}

	public void setGrandRealtimeSalary(String grandRealtimeSalary) {
		this.grandRealtimeSalary = grandRealtimeSalary;
	}

	public String getIsOverYn() {
		return isOverYn;
	}

	public void setIsOverYn(String isOverYn) {
		this.isOverYn = isOverYn;
	}

}