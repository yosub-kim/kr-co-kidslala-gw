package kr.co.kmac.pms.project.voc.manager;

import java.util.List;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.voc.data.ProjectVocInfoData;
import kr.co.kmac.pms.project.voc.data.ProjectVocSendingInfoData;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 17.
 * @description :
 */
public interface ProjectVocManager {

	public List<ProjectVocInfoData> getProjectVoc(String projectCode) throws ProjectException;

	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month) throws ProjectException;

	public List<ProjectVocSendingInfoData> getSendingProjectVoc(String year, String month, String day) throws DataAccessException;

	public void setProjectVoc(String projectCode, String[] requestDate, String[] vocType) throws ProjectException;

	public void deleteProjectVoc(String projectCode) throws ProjectException;

	public void updateProjectVoc(ProjectVocInfoData projectVocInfoData, ProjectSimpleInfo projectSimpleInfo) throws ProjectException;

}
