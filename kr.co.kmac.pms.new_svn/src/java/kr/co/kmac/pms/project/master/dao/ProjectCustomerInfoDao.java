package kr.co.kmac.pms.project.master.dao;

import java.util.List;

import kr.co.kmac.pms.project.master.data.ProjectCustomerInfo;

import org.springframework.dao.DataAccessException;

public interface ProjectCustomerInfoDao {

	public ProjectCustomerInfo select(String projectCode, int seq) throws DataAccessException;

	public List<ProjectCustomerInfo> select(String projectCode) throws DataAccessException;

	public void insert(ProjectCustomerInfo projectCustomerInfo) throws DataAccessException;

	public void insert(List<ProjectCustomerInfo> projectCustomerInfoList) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, int seq) throws DataAccessException;

}
