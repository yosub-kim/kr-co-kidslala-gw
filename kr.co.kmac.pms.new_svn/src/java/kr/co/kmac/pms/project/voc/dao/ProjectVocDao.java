package kr.co.kmac.pms.project.voc.dao;

import java.util.List;

import kr.co.kmac.pms.project.voc.data.ProjectVocInfoData;
import kr.co.kmac.pms.project.voc.data.ProjectVocSendingInfoData;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

import org.springframework.dao.DataAccessException;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 17.
 * @description :
 */
public interface ProjectVocDao {
	public List<ProjectVocInfoData> getProjectVoc(String projectCode) throws DataAccessException;

	// Job Date: 2010-08-20 Author: yhyim Description: project voc 발송 리스트 return
	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month) throws DataAccessException;

	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month, String day) throws DataAccessException;

	// public void setProjectVoc(String projectCode, String[] requestDate, String[] vocType, String receiveName, String receiveEmail) throws
	// DataAccessException;
	public void setProjectVoc(String projectCode, String[] requestDate, String[] vocType) throws DataAccessException;

	public void deleteProjectVoc(String projectCode) throws DataAccessException;

	public void updateProjectVoc(ProjectVocInfoData projectVocInfoData, ProjectSimpleInfo projectSimpleInfo) throws DataAccessException;
}
