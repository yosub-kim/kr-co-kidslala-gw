package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;

import org.springframework.dao.DataAccessException;

public interface ProjectCsrInfoDao {

	public ProjectCsrInfo getRepCustomer(String projectCode) throws DataAccessException;

	public ProjectCsrInfo select(String projectCode, int seq) throws DataAccessException;

	public List<ProjectCsrInfo> select(String projectCode) throws DataAccessException;

	public List<ProjectCsrInfo> selectAll(String projectCode) throws DataAccessException;

	public void insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

	public void insert(List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, int seq) throws DataAccessException;

	public void deleteRepCustomer(String projectCode) throws DataAccessException;

	public void update(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

	public int updateObj(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

	public void insertThxInfo(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

}
