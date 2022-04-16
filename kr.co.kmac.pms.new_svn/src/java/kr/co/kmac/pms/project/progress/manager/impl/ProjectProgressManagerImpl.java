package kr.co.kmac.pms.project.progress.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.progress.dao.ProjectProgressContentDao;
import kr.co.kmac.pms.project.progress.dao.ProjectProgressExeDao;
import kr.co.kmac.pms.project.progress.dao.ProjectProgressExeDetailDao;
import kr.co.kmac.pms.project.progress.dao.ProjectProgressPlanDao;
import kr.co.kmac.pms.project.progress.dao.ProjectProgressPlanDetailDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;
import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;
import kr.co.kmac.pms.project.progress.form.ProjectProgressContentForm;
import kr.co.kmac.pms.project.progress.manager.ProjectProgressManager;

public class ProjectProgressManagerImpl implements ProjectProgressManager {

	private ProjectProgressExeDao projectProgressExeDao;
	private ProjectProgressContentDao projectProgressContentDao;
	private ProjectProgressPlanDao projectProgressPlanDao;
	private ProjectProgressExeDetailDao projectProgressExeDetailDao;
	private ProjectProgressPlanDetailDao projectProgressPlanDetailDao;

	@Override
	public void deleteProjectProgress(String projectCode) throws ProjectException {
		this.getProjectProgressExeDetailDao().delete(projectCode);
		this.getProjectProgressExeDao().delete(projectCode);
		this.getProjectProgressPlanDetailDao().delete(projectCode);
		this.getProjectProgressPlanDao().delete(projectCode);
	}

	@Override
	public ProjectProgress getProjectProgressPlan(String projectCode, String workSeq) throws ProjectException {
		return this.getProjectProgressPlanDao().select(projectCode, workSeq);
	}

	@Override
	public List<ProjectProgress> getProjectProgressPlan(String projectCode) throws ProjectException {
		return this.getProjectProgressPlanDao().select(projectCode);
	}

	@Override
	public ProjectProgress getProjectProgressExe(String projectCode, String workSeq) throws ProjectException {
		return this.getProjectProgressExeDao().select(projectCode, workSeq);
	}

	@Override
	public List<ProjectProgress> getProjectProgressExe(String projectCode) throws ProjectException {
		return this.getProjectProgressExeDao().select(projectCode);
	}

	@Override
	public void storeProjectProgressPlan(List<ProjectProgress> projectProgressList) throws ProjectException {

	}

	@Override
	public void storeProjectProgressPlan(ProjectProgress projectProgress) throws ProjectException {
		this.getProjectProgressPlanDao().delete(projectProgress.getProjectCode());
		this.getProjectProgressPlanDetailDao().delete(projectProgress.getProjectCode());
	}

	@Override
	public void storeProjectProgressExe(String projectCode, List<ProjectProgress> projectProgressList) throws ProjectException {
		this.getProjectProgressExeDao().delete(projectCode);
		int orderSeq = 1;
		for (ProjectProgress projectProgress : projectProgressList) {
			projectProgress.setOrderSeq(orderSeq);
			projectProgress.setLevel(0);
			if(!StringUtil.isNotNull(projectProgress.getContentId())){
				projectProgress.setContentId("TASK" + IdGenerator.generate(""));
			}
			this.storeProjectProgressExe(projectProgress);
			orderSeq++;
			if (projectProgress.getProjectProgressList() != null && projectProgress.getProjectProgressList().size() > 0) {
				for (ProjectProgress subProjectProgress : projectProgress.getProjectProgressList()) {
					subProjectProgress.setOrderSeq(orderSeq);
					subProjectProgress.setLevel(1);
					subProjectProgress.setParantWorkSeq(projectProgress.getWorkSeq());
					this.storeProjectProgressExe(subProjectProgress);
					orderSeq++;
				}
			}
		}
	}

	@Override
	public void storeProjectProgressExe(ProjectProgress projectProgress) throws ProjectException {
		this.getProjectProgressExeDao().delete(projectProgress.getProjectCode(), String.valueOf(projectProgress.getWorkSeq()));
		this.getProjectProgressExeDao().insert(projectProgress);
	}

	@Override
	public ProjectProgressContent getProjectProgressContent(String projectCode, String contentId) throws ProjectException {
		ProjectProgressContent projectProgressContent = this.getProjectProgressContentDao().select(projectCode, contentId);
		if (projectProgressContent == null) {
			projectProgressContent = new ProjectProgressContentForm();
			projectProgressContent.setProjectCode(projectCode);
			projectProgressContent.setContentId(contentId);
		}
		return projectProgressContent;
	}

	@Override
	public void insertProjectProgressContent(ProjectProgressContent projectProgressContent) throws ProjectException {
		this.getProjectProgressContentDao().delete(projectProgressContent.getProjectCode(), projectProgressContent.getContentId());
		this.getProjectProgressContentDao().insert(projectProgressContent);

		ProjectProgress projectProgress = this.getProjectProgressExe(projectProgressContent.getProjectCode(), projectProgressContent.getWorkSeq());
		projectProgress.setRealEndDate(DateTime.getDateString());
		this.storeProjectProgressExe(projectProgress);
	}

	@Override
	public int getMaxWorkSeq(String projectCode) throws ProjectException {
		return this.getProjectProgressExeDao().getMaxWorkSeq(projectCode);
	}

	@Override
	public boolean isProjectProgressFinish(String projectCode) throws ProjectException {
		return this.getProjectProgressExeDao().isProjectProgressFinish(projectCode);
	}

	/**
	 * @return the projectProgressContentDao
	 */
	public ProjectProgressContentDao getProjectProgressContentDao() {
		return projectProgressContentDao;
	}

	/**
	 * @param projectProgressContentDao the projectProgressContentDao to set
	 */
	public void setProjectProgressContentDao(ProjectProgressContentDao projectProgressContentDao) {
		this.projectProgressContentDao = projectProgressContentDao;
	}

	/**
	 * @return the projectProgressExeDao
	 */
	public ProjectProgressExeDao getProjectProgressExeDao() {
		return projectProgressExeDao;
	}

	/**
	 * @param projectProgressExeDao the projectProgressExeDao to set
	 */
	public void setProjectProgressExeDao(ProjectProgressExeDao projectProgressExeDao) {
		this.projectProgressExeDao = projectProgressExeDao;
	}

	/**
	 * @return the projectProgressPlanDao
	 */
	public ProjectProgressPlanDao getProjectProgressPlanDao() {
		return projectProgressPlanDao;
	}

	/**
	 * @param projectProgressPlanDao the projectProgressPlanDao to set
	 */
	public void setProjectProgressPlanDao(ProjectProgressPlanDao projectProgressPlanDao) {
		this.projectProgressPlanDao = projectProgressPlanDao;
	}

	/**
	 * @return the projectProgressExeDetailDao
	 */
	public ProjectProgressExeDetailDao getProjectProgressExeDetailDao() {
		return projectProgressExeDetailDao;
	}

	/**
	 * @param projectProgressExeDetailDao the projectProgressExeDetailDao to set
	 */
	public void setProjectProgressExeDetailDao(ProjectProgressExeDetailDao projectProgressExeDetailDao) {
		this.projectProgressExeDetailDao = projectProgressExeDetailDao;
	}

	/**
	 * @return the projectProgressPlanDetailDao
	 */
	public ProjectProgressPlanDetailDao getProjectProgressPlanDetailDao() {
		return projectProgressPlanDetailDao;
	}

	/**
	 * @param projectProgressPlanDetailDao the projectProgressPlanDetailDao to set
	 */
	public void setProjectProgressPlanDetailDao(ProjectProgressPlanDetailDao projectProgressPlanDetailDao) {
		this.projectProgressPlanDetailDao = projectProgressPlanDetailDao;
	}

}
