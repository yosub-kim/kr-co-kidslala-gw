package kr.co.kmac.pms.project.wreport.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;

public interface WeeklyReportManager {
	
	public List<WeeklyReport> getWeeklyReport(String projectCode) throws ProjectException;
	
	public List<ProjectProgressEntity> getWeeklyActivity(String projectCode) throws ProjectException;

	public WeeklyReport getWeeklyReport(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws ProjectException;

	public void storeWeeklyReport(WeeklyReport weeklyReport) throws ProjectException;

	public void deleteWeeklyReport(String projectCode) throws ProjectException;

	public void deleteWeeklyReport(String projectCode, String assignYear, String assignMonth, String assignWeekOfMonth) throws ProjectException;

	public void entrustWeeklyReport(WeeklyReport weeklyReport) throws ProjectException;

	public void executeProjectReport(WeeklyReport weeklyReport) throws ProjectException;
}
