package kr.co.kmac.pms.project.mreport.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;

public interface MonthlyReportManager {

	public List<MonthlyReport> getMonthlyReport(String projectCode) throws ProjectException;

	public MonthlyReport getMonthlyReport(String projectCode, String assignYear, String assignMonth, String writerSsn) throws ProjectException;

	public void storeMonthlyReport(MonthlyReport monthlyReport) throws ProjectException;

	public void deleteMonthlyReport(String projectCode) throws ProjectException;

	public void deleteMonthlyReport(String projectCode, String assignYear, String assignMonth, String writerSsn) throws ProjectException;

	public void entrustMonthlyReport(MonthlyReport monthlyReport) throws ProjectException;

	public void executeProjectReport(MonthlyReport monthlyReport) throws ProjectException;
}
