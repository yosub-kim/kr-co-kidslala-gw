package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import kr.co.kmac.pms.project.expense.data.ExpenseDetail;
import kr.co.kmac.pms.project.expense.data.ExpenseDetailAdded;

import org.springframework.dao.DataAccessException;

public interface ExpenseDetailDao {

	public void insert(ExpenseDetail expenseDetail) throws DataAccessException;

	public void update(ExpenseDetail expenseDetail) throws DataAccessException;
	
	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String ssn) throws DataAccessException;

	public void delete(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException;
	
	public void delete2(String projectCode, String year, String month, String ssn, int seq) throws DataAccessException;

	public List<ExpenseDetail> select(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ExpenseDetail> select2(String projectCode, String year, String month) throws DataAccessException;
	
	public List<ExpenseDetailAdded> selectPU(String projectCode, String year, String month) throws DataAccessException;

	public List<ExpenseDetail> select(String projectCode, String year, String month, String ssn) throws DataAccessException;
	
	public List<ExpenseDetail> selectAllExpense(String projectCode, String year, String month) throws DataAccessException;

	public int getExpenseSeqMaxNum(String projectCode, String year, String month, String ssn) throws DataAccessException;
	
	public int getExpenseSeqMaxNum2(String projectCode, String year, String month, String ssn) throws DataAccessException;
}
