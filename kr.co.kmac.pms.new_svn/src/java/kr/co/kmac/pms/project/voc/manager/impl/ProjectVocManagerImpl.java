package kr.co.kmac.pms.project.voc.manager.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.voc.dao.ProjectVocDao;
import kr.co.kmac.pms.project.voc.data.ProjectVocInfoData;
import kr.co.kmac.pms.project.voc.data.ProjectVocSendingInfoData;
import kr.co.kmac.pms.project.voc.manager.ProjectVocManager;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 17.
 * @description :
 */
public class ProjectVocManagerImpl implements ProjectVocManager {
	private ProjectVocDao projectVocDao;

	/**
	 * @return Returns the projectVocDao.
	 */
	public ProjectVocDao getProjectVocDao() {
		return projectVocDao;
	}

	/**
	 * @param projectVocDao The projectVocDao to set.
	 */
	public void setProjectVocDao(ProjectVocDao projectVocDao) {
		this.projectVocDao = projectVocDao;
	}

	public List<ProjectVocInfoData> getProjectVoc(String projectCode) throws ProjectException {
		return getProjectVocDao().getProjectVoc(projectCode);
	}

	@Override
	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month) throws ProjectException {
		return getProjectVocDao().getSendingProjectVoc(year, month);
	}

	@Override
	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month, String day) throws DataAccessException {
		return getProjectVocDao().getSendingProjectVoc(year, month, day);
	}

	public void setProjectVoc(String projectCode, String[] requestDate, String[] vocType) throws ProjectException {
		deleteProjectVoc(projectCode);
		if (requestDate != null)
			getProjectVocDao().setProjectVoc(projectCode, requestDate, vocType);
	}

	public void deleteProjectVoc(String projectCode) throws ProjectException {
		getProjectVocDao().deleteProjectVoc(projectCode);
	}

	@Override
	public void updateProjectVoc(ProjectVocInfoData projectVocInfoData, ProjectSimpleInfo projectSimpleInfo) throws ProjectException {
		this.getProjectVocDao().updateProjectVoc(projectVocInfoData, projectSimpleInfo);
	}
	
}
