package kr.co.kmac.pms.project.wreport.dao;

import java.util.List;

import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;

import org.springframework.dao.DataAccessException;

public interface WeeklyReportDao {

	public int insert(WeeklyReport weeklyReport) throws DataAccessException;

	public List<WeeklyReport> select(String projectCode) throws DataAccessException; 
	
	public List<ProjectProgressEntity> selectActivity(String projectCode) throws DataAccessException;
	
	public WeeklyReport select(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws DataAccessException;

	public int update(WeeklyReport weeklyReport) throws DataAccessException;

	public int delete(String projectCode) throws DataAccessException;

	public int delete(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws DataAccessException;

}
