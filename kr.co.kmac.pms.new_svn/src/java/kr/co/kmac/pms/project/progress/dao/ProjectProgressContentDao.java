package kr.co.kmac.pms.project.progress.dao;

import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;

import org.springframework.dao.DataAccessException;

public interface ProjectProgressContentDao {

	public void insert(ProjectProgressContent projectProgressContent) throws DataAccessException;

	public ProjectProgressContent select(String projectCode, String contentId) throws DataAccessException;

	public void delete(String projectCode, String contentId) throws DataAccessException;

	public void update(ProjectProgressContent projectProgressContent) throws DataAccessException;

}
