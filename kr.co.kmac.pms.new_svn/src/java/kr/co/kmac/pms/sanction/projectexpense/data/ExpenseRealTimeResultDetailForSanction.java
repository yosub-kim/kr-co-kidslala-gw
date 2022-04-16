/*
 * $Id: ExpenseRealTimeResultDetailForSanction.java,v 1.9 2019/03/25 17:51:50 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 21.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.data;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.sanction.projectexpense.form.ProjectExpenseForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ExpenseRealTimeResultDetailForSanction.java,v 1.9 2019/03/25 17:51:50 cvs Exp $
 */
public class ExpenseRealTimeResultDetailForSanction {
	private String projectExpenseSeq;
	private String salaryType;
	private String salaryDeptType;
	private String salaryJobClass;
	private String salaryJobClassDesc;
	private String salaryJobClassErp;
	private String salaryYear;
	private String salaryMonth;
	private String salaryDept;
	private String salaryName;
	private String salarySsn;
	private String salaryUid;

	private String salaryProjectCode;
	private String salaryProjectName;
	private String salaryPreportCount;

	private double salaryCost; // 단가
	private String salaryResRate; // 요율
	private long salaryRealTimeSalaryEachProject;
	private long salaryTotalRealTimeSalary;
	private long salaryExecuted;
	private String salarySeq;
	private String salaryIsHolding;
	private String salaryIsExceed;
	
	private String salaryMMValueRatio;
	private String salaryIsMMExceed;
	
	private long salaryCumulativeRealTimeSalary;
	private long salaryBudgetEachProject;
	
	private String salaryApprovalType;
	private String salarySsnCnt;

	public String getSalarySsnCnt() {
		return salarySsnCnt;
	}

	public void setSalarySsnCnt(String salarySsnCnt) {
		this.salarySsnCnt = salarySsnCnt;
	}

	public static List<ExpenseRealTimeResultDetailForSanction> valueOf(ProjectExpenseForm form) {
		List<ExpenseRealTimeResultDetailForSanction> list = new ArrayList<ExpenseRealTimeResultDetailForSanction>();
		ExpenseRealTimeResultDetailForSanction d = null;
		for (int i = 0; i < form.getSalarySsn().length; i++) {
			d = new ExpenseRealTimeResultDetailForSanction();
			d.setProjectExpenseSeq(form.getProjectExpenseSeq());
			d.setSalaryType(form.getSalaryType()[i]);
			d.setSalaryDeptType(form.getSalaryDeptType()[i]);
			d.setSalaryJobClass(form.getSalaryJobClass()[i]);
			d.setSalaryJobClassDesc(form.getSalaryJobClassDesc()[i]);
			d.setSalaryJobClassErp(form.getSalaryJobClassErp()[i]);
			d.setSalaryYear(form.getSalaryYear()[i]);
			d.setSalaryMonth(form.getSalaryMonth()[i]);
			d.setSalaryName(form.getSalaryName()[i]);
			d.setSalarySsn(form.getSalarySsn()[i]);
			d.setSalaryTotalRealTimeSalary(Long.parseLong(form.getSalaryTotalRealTimeSalary()[i]));
			d.setSalaryProjectCode(form.getSalaryProjectCode()[i]);
			d.setSalaryProjectName(form.getSalaryProjectName()[i]);
			d.setSalaryPreportCount(form.getSalaryPreportCount()[i]);
			d.setSalaryRealTimeSalaryEachProject(Long.parseLong(form.getSalaryRealTimeSalaryEachProject()[i]));
			// d.setSalaryExecuted(Long.parseLong(form.getSalaryExecuted()[i]));
			d.setSalaryDept(form.getSalaryDept()[i]);
			d.setSalarySeq(form.getSalarySeq()[i]);
			// d.setSalaryIsExceed(form.getSalaryIsExceed()[i]);
			d.setSalaryIsHolding(form.getSalaryIsHolding()[i]);
			d.setSalaryCost(Double.parseDouble(form.getSalaryCost()[i]));
			d.setSalaryResRate(form.getSalaryResRate()[i]);
			d.setSalaryMMValueRatio(form.getSalaryMMValueRatio()[i]);
			d.setSalaryIsMMExceed(form.getSalaryIsMMExceed()[i]);
			d.setSalaryCumulativeRealTimeSalary(Long.parseLong(form.getSalaryCumulativeRealTimeSalary()[i]));
			d.setSalaryBudgetEachProject(Long.parseLong(form.getSalaryBudgetEachProject()[i]));
			try{
				d.setSalarySsnCnt(form.getSsnCnt()[i]);
			}catch(Exception e){
				e.getMessage();
			}
			list.add(d);
		}
		return list;
	}

	public String getSalaryDeptType() {
		return salaryDeptType;
	}

	public void setSalaryDeptType(String salaryDeptType) {
		this.salaryDeptType = salaryDeptType;
	}

	public String getSalaryJobClass() {
		return salaryJobClass;
	}

	public void setSalaryJobClass(String salaryJobClass) {
		this.salaryJobClass = salaryJobClass;
	}

	public String getSalaryJobClassDesc() {
		return salaryJobClassDesc;
	}

	public void setSalaryJobClassDesc(String salaryJobClassDesc) {
		this.salaryJobClassDesc = salaryJobClassDesc;
	}

	public String getSalaryJobClassErp() {
		return salaryJobClassErp;
	}

	public void setSalaryJobClassErp(String salaryJobClassErp) {
		this.salaryJobClassErp = salaryJobClassErp;
	}

	public String getProjectExpenseSeq() {
		return projectExpenseSeq;
	}

	public void setProjectExpenseSeq(String projectExpenseSeq) {
		this.projectExpenseSeq = projectExpenseSeq;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getSalaryYear() {
		return salaryYear;
	}

	public void setSalaryYear(String salaryYear) {
		this.salaryYear = salaryYear;
	}

	public String getSalaryMonth() {
		return salaryMonth;
	}

	public void setSalaryMonth(String salaryMonth) {
		this.salaryMonth = salaryMonth;
	}

	public String getSalaryDept() {
		return salaryDept;
	}

	public void setSalaryDept(String salaryDept) {
		this.salaryDept = salaryDept;
	}

	public String getSalaryName() {
		return salaryName;
	}

	public void setSalaryName(String salaryName) {
		this.salaryName = salaryName;
	}

	public String getSalarySsn() {
		return salarySsn;
	}

	public void setSalarySsn(String salarySsn) {
		this.salarySsn = salarySsn;
	}

	public String getSalaryProjectCode() {
		return salaryProjectCode;
	}

	public void setSalaryProjectCode(String salaryProjectCode) {
		this.salaryProjectCode = salaryProjectCode;
	}

	public String getSalaryProjectName() {
		return salaryProjectName;
	}

	public void setSalaryProjectName(String salaryProjectName) {
		this.salaryProjectName = salaryProjectName;
	}

	public String getSalaryPreportCount() {
		return salaryPreportCount;
	}

	public void setSalaryPreportCount(String salaryPreportCount) {
		this.salaryPreportCount = salaryPreportCount;
	}

	public long getSalaryRealTimeSalaryEachProject() {
		return salaryRealTimeSalaryEachProject;
	}

	public void setSalaryRealTimeSalaryEachProject(long salaryRealTimeSalaryEachProject) {
		this.salaryRealTimeSalaryEachProject = salaryRealTimeSalaryEachProject;
	}

	public long getSalaryTotalRealTimeSalary() {
		return salaryTotalRealTimeSalary;
	}

	public void setSalaryTotalRealTimeSalary(long salaryTotalRealTimeSalary) {
		this.salaryTotalRealTimeSalary = salaryTotalRealTimeSalary;
	}

	public long getSalaryExecuted() {
		return salaryExecuted;
	}

	public void setSalaryExecuted(long salaryExecuted) {
		this.salaryExecuted = salaryExecuted;
	}

	public String getSalarySeq() {
		return salarySeq;
	}

	public void setSalarySeq(String salarySeq) {
		this.salarySeq = salarySeq;
	}

	public String getSalaryIsHolding() {
		return salaryIsHolding;
	}

	public void setSalaryIsHolding(String salaryIsHolding) {
		this.salaryIsHolding = salaryIsHolding;
	}

	public double getSalaryCost() {
		return salaryCost;
	}

	public void setSalaryCost(double salaryCost) {
		this.salaryCost = salaryCost;
	}

	public String getSalaryResRate() {
		return salaryResRate;
	}

	public void setSalaryResRate(String salaryResRate) {
		this.salaryResRate = salaryResRate;
	}

	public String getSalaryIsExceed() {
		return salaryIsExceed;
	}

	public void setSalaryIsExceed(String salaryIsExceed) {
		this.salaryIsExceed = salaryIsExceed;
	}

	public String getSalaryUid() {
		return salaryUid;
	}

	public void setSalaryUid(String salaryUid) {
		this.salaryUid = salaryUid;
	}

	public String getSalaryMMValueRatio() {
		return salaryMMValueRatio;
	}

	public void setSalaryMMValueRatio(String salaryMMValueRatio) {
		this.salaryMMValueRatio = salaryMMValueRatio;
	}

	public String getSalaryIsMMExceed() {
		return salaryIsMMExceed;
	}

	public void setSalaryIsMMExceed(String salaryIsMMExceed) {
		this.salaryIsMMExceed = salaryIsMMExceed;
	}

	public long getSalaryCumulativeRealTimeSalary() {
		return salaryCumulativeRealTimeSalary;
	}

	public void setSalaryCumulativeRealTimeSalary(long salaryCumulativeRealTimeSalary) {
		this.salaryCumulativeRealTimeSalary = salaryCumulativeRealTimeSalary;
	}

	public long getSalaryBudgetEachProject() {
		return salaryBudgetEachProject;
	}

	public void setSalaryBudgetEachProject(long salaryBudgetEachProject) {
		this.salaryBudgetEachProject = salaryBudgetEachProject;
	}

	public String getSalaryApprovalType() {
		return salaryApprovalType;
	}

	public void setSalaryApprovalType(String salaryApprovalType) {
		this.salaryApprovalType = salaryApprovalType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}