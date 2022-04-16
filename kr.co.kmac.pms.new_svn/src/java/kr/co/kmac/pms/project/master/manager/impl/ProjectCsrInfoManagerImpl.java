package kr.co.kmac.pms.project.master.manager.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.master.dao.ProjectCsrInfoDao;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.manager.ProjectCsrInfoManager;

public class ProjectCsrInfoManagerImpl implements ProjectCsrInfoManager {

	private ProjectCsrInfoDao projectCsrInfoDao;

	public ProjectCsrInfoDao getProjectCsrInfoDao() {
		return projectCsrInfoDao;
	}

	public void setProjectCsrInfoDao(ProjectCsrInfoDao projectCsrInfoDao) {
		this.projectCsrInfoDao = projectCsrInfoDao;
	}

	@Override
	public ProjectCsrInfo select(String projectCode, int seq) throws DataAccessException {
		return this.projectCsrInfoDao.select(projectCode, seq);
	}

	@Override
	public List<ProjectCsrInfo> select(String projectCode) throws DataAccessException {
		return this.projectCsrInfoDao.select(projectCode);
	}

	@Override
	public void insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		this.projectCsrInfoDao.insert(projectCsrInfo);
	}

	@Override
	public void insert(String projectCode, List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException {
		this.projectCsrInfoDao.delete(projectCode);
		this.projectCsrInfoDao.insert(projectCsrInfoList);
	}

	@Override
	public void insertForExcel(String projectCode, List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException {
		this.projectCsrInfoDao.insert(projectCsrInfoList);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectCsrInfoDao.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, int seq) throws DataAccessException {
		this.projectCsrInfoDao.delete(projectCode, seq);
	}

	@Override
	public void update(ProjectCsrInfo projectCsrInfo) throws DataAccessException {
		this.projectCsrInfoDao.update(projectCsrInfo);
	}

}
