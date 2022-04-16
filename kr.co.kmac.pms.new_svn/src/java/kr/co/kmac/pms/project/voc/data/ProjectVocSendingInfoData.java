package kr.co.kmac.pms.project.voc.data;

import kr.co.kmac.pms.project.voc.data.ProjectVocInfoData;
/**
 * @author yhyim
 * @jobDate &2010. 8. 20.
 * @description :
 */
public class ProjectVocSendingInfoData extends ProjectVocInfoData{
	String projectName;
	String runningDivName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getRunningDivName() {
		return runningDivName;
	}
	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}
	
	
}