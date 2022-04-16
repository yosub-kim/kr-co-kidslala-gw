package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ExpenseHistoryDao {

	// ������Ʈ ������ ���� ����(100%) 
	public List<List<String>> select(String projectCode) throws DataAccessException;
	
	// ������Ʈ ������ ���� ����(20%) 
	public List<List<String>> select_rest(String projectCode) throws DataAccessException;
	
	// ������Ʈ ������ ���� ����(80%) 
	public List<List<String>> select_mmplan(String projectCode) throws DataAccessException;
}
