package kr.co.kmac.pms.project.expense.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ExpenseHistoryDao {

	// 프로젝트 성과급 지급 내역(100%) 
	public List<List<String>> select(String projectCode) throws DataAccessException;
	
	// 프로젝트 성과급 지급 내역(20%) 
	public List<List<String>> select_rest(String projectCode) throws DataAccessException;
	
	// 프로젝트 성과급 지급 내역(80%) 
	public List<List<String>> select_mmplan(String projectCode) throws DataAccessException;
}
