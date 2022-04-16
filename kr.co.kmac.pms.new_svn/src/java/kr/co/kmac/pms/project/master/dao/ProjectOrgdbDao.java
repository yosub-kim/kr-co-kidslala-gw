package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.ProjectOrgdb;

import org.springframework.dao.DataAccessException;

/**
 * @author hyunseong
 * @jobDate &2008. 6. 10.
 * @description : PMS ������Ʈ �⺻������ ���±�� ����� ���� Dao
 */
public interface ProjectOrgdbDao {

	public List<ProjectOrgdb> getProjectOrgdb(String projectCode) throws DataAccessException;

	public void setProjectOrgdb(String projectCode, String[] orgCode, String[] orgName) throws DataAccessException;

	public void deleteProjectOrgdb(String projectCode) throws DataAccessException;
}