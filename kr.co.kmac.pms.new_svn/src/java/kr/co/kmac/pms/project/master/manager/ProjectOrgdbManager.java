package kr.co.kmac.pms.project.master.manager;

import java.util.List;

import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.data.ProjectOrgdb;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 10.
 * @description : PMS ������Ʈ �⺻������ ���±�� ����� ���� Manager
 */
public interface ProjectOrgdbManager {

	public List<ProjectOrgdb> getProjectOrgdb(String projectCode) throws ProjectException;
	public void setProjectOrgdb(String projectCode, String[] orgCode, String[] orgName) throws ProjectException;
	public void deleteProjectOrgdb(String projectCode) throws ProjectException;
}
