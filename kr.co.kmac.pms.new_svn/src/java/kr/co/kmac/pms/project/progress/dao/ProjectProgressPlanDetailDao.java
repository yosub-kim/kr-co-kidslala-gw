package kr.co.kmac.pms.project.progress.dao;

import java.util.List;

import kr.co.kmac.pms.project.progress.data.ProjectProgressDetail;

import org.springframework.dao.DataAccessException;

/**
 * @deprecated
 * @author Jiwoong Lee
 */
public interface ProjectProgressPlanDetailDao {

	public int count(String projectCode, String workSeq) throws DataAccessException;

	public List<ProjectProgressDetail> select(String projectCode, String workSeq) throws DataAccessException;

	public ProjectProgressDetail select(String projectCode, String workSeq, String ssn) throws DataAccessException;

	public void insert(ProjectProgressDetail projectProgressDetail) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String workSeq) throws DataAccessException;

	public void delete(String projectCode, String workSeq, String ssn) throws DataAccessException;

}
