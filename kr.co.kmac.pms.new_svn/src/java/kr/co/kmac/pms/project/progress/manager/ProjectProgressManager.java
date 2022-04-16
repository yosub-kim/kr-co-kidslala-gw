package kr.co.kmac.pms.project.progress.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;
import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;

public interface ProjectProgressManager {

	public List<ProjectProgress> getProjectProgressPlan(String projectCode) throws ProjectException;

	public ProjectProgress getProjectProgressPlan(String projectCode, String workSeq) throws ProjectException;

	public void storeProjectProgressPlan(ProjectProgress projectProgress) throws ProjectException;

	public void storeProjectProgressPlan(List<ProjectProgress> projectProgressList) throws ProjectException;

	public List<ProjectProgress> getProjectProgressExe(String projectCode) throws ProjectException;

	public ProjectProgress getProjectProgressExe(String projectCode, String workSeq) throws ProjectException;

	public void storeProjectProgressExe(ProjectProgress projectProgress) throws ProjectException;

	public void storeProjectProgressExe(String projectCode, List<ProjectProgress> projectProgressList) throws ProjectException;

	public void deleteProjectProgress(String projectCode) throws ProjectException;

	public ProjectProgressContent getProjectProgressContent(String projectCode, String contentId) throws ProjectException;

	public void insertProjectProgressContent(ProjectProgressContent projectProgressContent) throws ProjectException;

	public int getMaxWorkSeq(String projectCode) throws ProjectException;

	public boolean isProjectProgressFinish(String projectCode) throws ProjectException;

}
