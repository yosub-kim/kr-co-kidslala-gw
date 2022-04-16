package kr.co.kmac.pms.project.mreport.dao;

import java.util.List;

import kr.co.kmac.pms.project.mreport.data.MonthlyReport;

import org.springframework.dao.DataAccessException;

public interface MonthlyReportDao {

	public int insert(MonthlyReport weeklyReport) throws DataAccessException;

	public List<MonthlyReport> select(String projectCode) throws DataAccessException; 
	
	public MonthlyReport select(String projectCode, String assignYear, String assignMonth, String writerSsn) throws DataAccessException;

	public int update(MonthlyReport weeklyReport) throws DataAccessException;

	public int delete(String projectCode) throws DataAccessException;

	public int delete(String projectCode, String assignYear, String assignMonth, String writerSsn) throws DataAccessException;

}
