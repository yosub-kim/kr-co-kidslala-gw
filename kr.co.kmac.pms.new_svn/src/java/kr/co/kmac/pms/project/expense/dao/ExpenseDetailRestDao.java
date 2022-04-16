package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import kr.co.kmac.pms.project.expense.data.ExpenseDetail;

import org.springframework.dao.DataAccessException;

public interface ExpenseDetailRestDao {

	public void insert(ExpenseDetail expenseDetail) throws DataAccessException;

	public void update(ExpenseDetail expenseDetail) throws DataAccessException;
	
	public void updateAmount(ExpenseDetail expenseDetail) throws DataAccessException;
	
	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String ssn) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException;

	public List<ExpenseDetail> select(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ExpenseDetail> select(String projectCode, String year, String month, String ssn) throws DataAccessException;
	
	public List<ExpenseDetail> selectAllExpense(String projectCode, String year, String month) throws DataAccessException;

	public int getExpenseSeqMaxNum(String projectCode, String year, String month, String ssn) throws DataAccessException;
	
	public boolean isExpenseValid(ExpenseDetail expenseDetail) throws DataAccessException;

	public void deleteRestSalary(String projectCode, String year, String month, String ssn) throws DataAccessException;
	
	public void deleteMMPlan(String projectCode, String year, String month, String ssn) throws DataAccessException;
}