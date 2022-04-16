package kr.co.kmac.pms.project.progress.dao;

import java.util.List;

import kr.co.kmac.pms.project.progress.data.ProjectProgress;

import org.springframework.dao.DataAccessException;

/**
 * @deprecated
 * @author Jiwoong Lee
 */
public interface ProjectProgressPlanDao {

	public int count(String projectCode) throws DataAccessException;

	public List<ProjectProgress> select(String projectCode) throws DataAccessException;

	public ProjectProgress select(String projectCode, String workSeq) throws DataAccessException;

	public void insert(ProjectProgress projectProgress) throws DataAccessException;

	public void delete(String projectCode) throws DataAccessException;

	public void delete(String projectCode, String workSeq) throws DataAccessException;

}
