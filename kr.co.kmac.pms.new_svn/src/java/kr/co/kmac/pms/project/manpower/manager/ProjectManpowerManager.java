package kr.co.kmac.pms.project.manpower.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;

public interface ProjectManpowerManager {

	public List<ProjectManpower> getProjectManpower(String projectCode, String year, String month) throws ProjectException;

	public List<ProjectManpower> getProjectWorkName(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectManpower> getProjecMonthWork(String projectCode, String year, String month) throws ProjectException;
	
	public List<ProjectManpower> getProjectManpowerForWreport(String projectCode, String startDate, String endDate) throws ProjectException;

	public List<ProjectManpower> getProjectMember(String projectCode) throws ProjectException;
	
	public List<ProjectManpower> getProjectRunningInfo(String projectCode) throws ProjectException;
	
	public List<ProjectManpower> getProjectActivity(String projectCode) throws ProjectException;
	
	public ProjectManpower getProjectManpower(String projectCode, String year, String month, String day, String ssn) throws ProjectException;

	public void storeProjectManpower(ProjectManpower projectManpower) throws ProjectException;

	public void storeProjectManpower(List<ProjectManpower> projectManpowerList) throws ProjectException;

	public void deleteProjectManpower(String projectCode, String year, String month, String day, String ssn) throws ProjectException;

	public void deleteProjectManpower(String projectCode) throws ProjectException;

}