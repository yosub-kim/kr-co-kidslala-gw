package kr.co.kmac.pms.project.preport.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

public interface ProjectReportInfoManager {

	public List<ProjectReportPlan> getProjectReportInfo(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectReportPlan> getProjectMember(String projectCode) throws ProjectException;
	
	public List<ProjectReportPlan> getProjectWorkName(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectReportPlan> getProjecMonthWork(String projectCode, String year, String month) throws ProjectException;

	public ProjectReportPlan getProjectReportInfo(String projectCode, String year, String month, String day, String ssn) throws ProjectException;

	public void storeProjectReportInfo(ProjectReportPlan projectReportPlan) throws ProjectException;

	public void storeProjectReportInfoForTeachingTime(ProjectReportPlan projectReportPlan) throws ProjectException;

	public void storeProjectReportInfo(List<ProjectReportPlan> projectReportPlanList) throws ProjectException;

	public void deleteProjectReportInfo(String projectCode, String year, String month, String day, String ssn) throws ProjectException;

	public void deleteProjectReportInfo(String projectCode) throws ProjectException;
	
	public void updateProjectReportPayAmount(String projectCode, String assignDate, 
			String ssn, String payAmount) throws ProjectException;
}
