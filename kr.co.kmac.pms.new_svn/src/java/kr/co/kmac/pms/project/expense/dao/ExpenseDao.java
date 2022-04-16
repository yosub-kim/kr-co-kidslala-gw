package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import kr.co.kmac.pms.project.expense.data.Expense;

import org.springframework.dao.DataAccessException;

public interface ExpenseDao {

	public void insert(Expense expense) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String year, String month) throws DataAccessException;

	public List<Expense> select(String projectCode) throws DataAccessException;

	public Expense select(String projectCode, String year, String month) throws DataAccessException;
}
