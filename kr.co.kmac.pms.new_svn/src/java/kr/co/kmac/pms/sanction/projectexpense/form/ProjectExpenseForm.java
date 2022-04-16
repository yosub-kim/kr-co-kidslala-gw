/*
 * $Id: ProjectExpenseForm.java,v 1.7 2019/03/25 17:51:50 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.form;

import kr.co.kmac.pms.sanction.general.form.SanctionForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @struts:form name="expenseSanctionAction"
 */
public class ProjectExpenseForm extends SanctionForm {
	static final long serialVersionUID = -7034897190745766939L;

	private String projectExpenseSeq;
	private String deptCode;// 해당 본부
	private String year;// 년
	private String month;// 월
	private String assignDate;// 해당월

	private String salaryType[];
	private String salaryDeptType[];
	private String salaryJobClass[];
	private String salaryJobClassDesc[];
	private String salaryJobClassErp[];
	private String salaryYear[];
	private String salaryMonth[];
	private String salaryName[];
	private String salarySsn[];
	private String salaryTotalRealTimeSalary[];
	private String salaryProjectCode[];
	private String salaryProjectName[];
	private String salaryPreportCount[];
	private String salaryRealTimeSalaryEachProject[];
	private String salaryRealTimeSalaryEachProjectAddedBasicSalary[];
	private String salaryExecuted[];
	private String salaryPlaned[];
	private String salaryDept[];
	private String salarySeq[];
	private String salaryIsHolding[];
	private String salaryIsExceed[];
	private String salaryCost[];
	private String salaryResRate[];
	private String salaryMMValueRatio[];
	private String salaryIsMMExceed[];
	private String ssnCnt[];
	
	
	
	public String[] getSsnCnt() {
		return ssnCnt;
	}

	public void setSsnCnt(String[] ssnCnt) {
		this.ssnCnt = ssnCnt;
	}

	private String salaryCumulativeRealTimeSalary[];
	private String salaryBudgetEachProject[];	

	public String[] getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String[] salaryType) {
		this.salaryType = salaryType;
	}

	public String[] getSalaryDeptType() {
		return salaryDeptType;
	}

	public void setSalaryDeptType(String[] salaryDeptType) {
		this.salaryDeptType = salaryDeptType;
	}

	public String[] getSalaryJobClass() {
		return salaryJobClass;
	}

	public void setSalaryJobClass(String[] salaryJobClass) {
		this.salaryJobClass = salaryJobClass;
	}

	public String[] getSalaryJobClassDesc() {
		return salaryJobClassDesc;
	}

	public void setSalaryJobClassDesc(String[] salaryJobClassDesc) {
		this.salaryJobClassDesc = salaryJobClassDesc;
	}

	public String[] getSalaryJobClassErp() {
		return salaryJobClassErp;
	}

	public void setSalaryJobClassErp(String[] salaryJobClassErp) {
		this.salaryJobClassErp = salaryJobClassErp;
	}

	public String getProjectExpenseSeq() {
		return projectExpenseSeq;
	}

	public void setProjectExpenseSeq(String projectExpenseSeq) {
		this.projectExpenseSeq = projectExpenseSeq;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public String[] getSalaryYear() {
		return salaryYear;
	}

	public void setSalaryYear(String[] salaryYear) {
		this.salaryYear = salaryYear;
	}

	public String[] getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String[] salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public String[] getSalaryName() {
		return salaryName;
	}

	public void setSalaryName(String[] salaryName) {
		this.salaryName = salaryName;
	}

	public String[] getSalarySsn() {
		return salarySsn;
	}

	public void setSalarySsn(String[] salarySsn) {
		this.salarySsn = salarySsn;
	}

	public String[] getSalaryTotalRealTimeSalary() {
		return salaryTotalRealTimeSalary;
	}

	public void setSalaryTotalRealTimeSalary(String[] salaryTotalRealTimeSalary) {
		this.salaryTotalRealTimeSalary = salaryTotalRealTimeSalary;
	}

	public String[] getSalaryProjectCode() {
		return salaryProjectCode;
	}

	public void setSalaryProjectCode(String[] salaryProjectCode) {
		this.salaryProjectCode = salaryProjectCode;
	}

	public String[] getSalaryProjectName() {
		return salaryProjectName;
	}

	public void setSalaryProjectName(String[] salaryProjectName) {
		this.salaryProjectName = salaryProjectName;
	}

	public String[] getSalaryPreportCount() {
		return salaryPreportCount;
	}

	public void setSalaryPreportCount(String[] salaryPreportCount) {
		this.salaryPreportCount = salaryPreportCount;
	}

	public String[] getSalaryRealTimeSalaryEachProject() {
		return salaryRealTimeSalaryEachProject;
	}

	public void setSalaryRealTimeSalaryEachProject(String[] salaryRealTimeSalaryEachProject) {
		this.salaryRealTimeSalaryEachProject = salaryRealTimeSalaryEachProject;
	}

	public String[] getSalaryRealTimeSalaryEachProjectAddedBasicSalary() {
		return salaryRealTimeSalaryEachProjectAddedBasicSalary;
	}

	public void setSalaryRealTimeSalaryEachProjectAddedBasicSalary(String[] salaryRealTimeSalaryEachProjectAddedBasicSalary) {
		this.salaryRealTimeSalaryEachProjectAddedBasicSalary = salaryRealTimeSalaryEachProjectAddedBasicSalary;
	}

	public String[] getSalaryExecuted() {
		return salaryExecuted;
	}

	public void setSalaryExecuted(String[] salaryExecuted) {
		this.salaryExecuted = salaryExecuted;
	}

	public String[] getSalaryPlaned() {
		return salaryPlaned;
	}

	public void setSalaryPlaned(String[] salaryPlaned) {
		this.salaryPlaned = salaryPlaned;
	}

	public String[] getSalaryDept() {
		return salaryDept;
	}

	public void setSalaryDept(String[] salaryDept) {
		this.salaryDept = salaryDept;
	}

	public String[] getSalarySeq() {
		return salarySeq;
	}

	public void setSalarySeq(String[] salarySeq) {
		this.salarySeq = salarySeq;
	}

	public String[] getSalaryIsHolding() {
		return salaryIsHolding;
	}

	public void setSalaryIsHolding(String[] salaryIsHolding) {
		this.salaryIsHolding = salaryIsHolding;
	}

	public String[] getSalaryCost() {
		return salaryCost;
	}

	public void setSalaryCost(String[] salaryCost) {
		this.salaryCost = salaryCost;
	}

	public String[] getSalaryResRate() {
		return salaryResRate;
	}

	public void setSalaryResRate(String[] salaryResRate) {
		this.salaryResRate = salaryResRate;
	}

	public String[] getSalaryIsExceed() {
		return salaryIsExceed;
	}

	public void setSalaryIsExceed(String[] salaryIsExceed) {
		this.salaryIsExceed = salaryIsExceed;
	}

	public String[] getSalaryMMValueRatio() {
		return salaryMMValueRatio;
	}

	public void setSalaryMMValueRatio(String[] salaryMMValueRatio) {
		this.salaryMMValueRatio = salaryMMValueRatio;
	}

	public String[] getSalaryIsMMExceed() {
		return salaryIsMMExceed;
	}

	public void setSalaryIsMMExceed(String[] salaryIsMMExceed) {
		this.salaryIsMMExceed = salaryIsMMExceed;
	}

	public String[] getSalaryCumulativeRealTimeSalary() {
		return salaryCumulativeRealTimeSalary;
	}

	public void setSalaryCumulativeRealTimeSalary(String[] salaryCumulativeRealTimeSalary) {
		this.salaryCumulativeRealTimeSalary = salaryCumulativeRealTimeSalary;
	}

	public String[] getSalaryBudgetEachProject() {
		return salaryBudgetEachProject;
	}

	public void setSalaryBudgetEachProject(String[] salaryBudgetEachProject) {
		this.salaryBudgetEachProject = salaryBudgetEachProject;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}