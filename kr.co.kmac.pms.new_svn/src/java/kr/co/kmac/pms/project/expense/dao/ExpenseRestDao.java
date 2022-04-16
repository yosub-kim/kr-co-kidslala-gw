package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import kr.co.kmac.pms.project.expense.data.Expense;
import kr.co.kmac.pms.project.expense.data.ExpenseDetail;

import org.springframework.dao.DataAccessException;

public interface ExpenseRestDao {

	public void insert(Expense expense) throws DataAccessException;
	
	public void insertTemp(ExpenseDetail expenseDetail) throws DataAccessException;

	public void insertReal(ExpenseDetail expenseDetail) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;
	
	public void deleteTemp(ExpenseDetail expenseDetail) throws DataAccessException;
	
	public void deleteTemp(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException;

	public List<Expense> select(String projectCode) throws DataAccessException;

	public Expense select(String projectCode, String year, String month) throws DataAccessException;
	
	public ExpenseDetail selectTemp(ExpenseDetail expenseDetail) throws DataAccessException;
}
