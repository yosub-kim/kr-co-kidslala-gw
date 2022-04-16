package kr.co.kmac.pms.project.master.manager;

import java.util.List;

import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;

import org.springframework.dao.DataAccessException;

public interface ProjectCsrInfoManager {

	public ProjectCsrInfo select(String projectCode, int seq) throws DataAccessException;

	public List<ProjectCsrInfo> select(String projectCode) throws DataAccessException;

	public void insert(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

	public void insert(String projectCode, List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException;

	public void insertForExcel(String projectCode, List<ProjectCsrInfo> projectCsrInfoList) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, int seq) throws DataAccessException;

	public void update(ProjectCsrInfo projectCsrInfo) throws DataAccessException;

}
