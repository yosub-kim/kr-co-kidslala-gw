package kr.co.kmac.pms.project.master.manager.impl;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.dao.ProjectOrgdbDao;
import kr.co.kmac.pms.project.master.data.ProjectOrgdb;
import kr.co.kmac.pms.project.master.manager.ProjectOrgdbManager;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 10.
 * @description : PMS 프로젝트 기본정보에 협력기관 등록을 위한 ManagerImpl
 */
public class ProjectOrgdbManagerImpl implements ProjectOrgdbManager {

	private ProjectOrgdbDao projectOrgdbDao;

	public ProjectOrgdbDao getProjectOrgdbDao() {
		return projectOrgdbDao;
	}

	public void setProjectOrgdbDao(ProjectOrgdbDao projectOrgdbDao) {
		this.projectOrgdbDao = projectOrgdbDao;
	}

	public List<ProjectOrgdb> getProjectOrgdb(String projectCode) throws ProjectException {
		return getProjectOrgdbDao().getProjectOrgdb(projectCode);
	}

	public void setProjectOrgdb(String projectCode, String[] orgCode, String[] orgName) throws ProjectException {
		deleteProjectOrgdb(projectCode);
		if (orgCode != null)
			getProjectOrgdbDao().setProjectOrgdb(projectCode, orgCode, orgName);
	}

	public void deleteProjectOrgdb(String projectCode) throws ProjectException {
		getProjectOrgdbDao().deleteProjectOrgdb(projectCode);
	}
}
