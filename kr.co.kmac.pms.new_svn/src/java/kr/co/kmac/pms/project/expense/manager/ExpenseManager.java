package kr.co.kmac.pms.project.expense.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.project.expense.data.ExpenseDetailAdded;
import kr.co.kmac.pms.project.expense.form.ExpenseForm;
import kr.co.kmac.pms.project.master.data.ProjectMember;

public interface ExpenseManager {

	public List<ProjectMember> getExpenseProjectMember(String projectCode) throws ProjectException;
	
	public List<ProjectMember> getExpenseProjectMember2(String projectCode) throws ProjectException;
	
	public List<ProjectMember> getExpenseProjectMemberPU(String projectCode) throws ProjectException;
	
	public List<ProjectMember> getScheduleDetailListPU(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectMember> getScheduleReportDetail(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectMember> getMonthlyReportDetail(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectMember> getExpenseProjectMemberAll(String projectCode) throws ProjectException;

	public List<ExpenseDetail> getExpenseDetailList(String projectCode, String year, String month) throws ProjectException;
	
	public List<ExpenseDetail> getExpenseDetailList2(String prpojectCode, String year, String month) throws ProjectException;
	
	public List<ExpenseDetailAdded> getExpenseDetailListPU(String projectCode, String year, String month) throws ProjectException;
	
	public List<ExpenseDetail> getExpenseAllDetailList(String projectCode, String year, String month) throws ProjectException;

	// 프로젝트 성과급 지급 내역(100%) 
	public List<List<String>> getProjectExpenseHistory(String projectCode) throws ProjectException;
	
	// 프로젝트 성과급 지급 내역(80%) 
	public List<List<String>> getProjectExpenseHistory_mmplan(String projectCode) throws ProjectException;
	
	// 프로젝트 성과급 지급 내역(20%) 
	public List<List<String>> getProjectExpenseHistory_rest(String projectCode) throws ProjectException;

	public void insertProjectExpense(ExpenseForm expenseForm) throws ProjectException;
	
	public void insertProjectExpensePU(ExpenseForm expenseForm) throws ProjectException;
	
	public void insertProjectExpensePU2(ExpenseForm expenseForm) throws ProjectException;
	
	public void deleteProjectExpense(String projectCode, String year, String month, String ssn, int seq);
	
	public void deleteProjectExpense2(String projectCode, String year, String month, String ssn, int seq);
	
	public void deleteProjectSalary(String projectCode, String year, String month, String ssn);

	public void createProjectRestExpense(String projectCode, String year, String month, String ssn, String restAmount);
	
	public void deleteProjectRestExpense(String projectCode, String year, String month, String ssn);
	
	public void deleteProjectRestSalary(String projectCode, String year, String month, String ssn);
	
	public void deleteProjectMMPlan(String projectCode, String year, String month, String ssn);
}
