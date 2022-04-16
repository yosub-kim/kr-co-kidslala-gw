/*
 * $Id: ProjectReportContentDao.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.preport.dao;

import java.util.List;

import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;

import org.springframework.dao.DataAccessException;

/**
 * TODO Ŭ���� ����
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportContentDao.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public interface ProjectReportContentDao {
	/**
	 * 
	 * ���ο� �������� ����
	 * 
	 * @param projectReport
	 * @throws DataAccessException
	 */
	public void create(ProjectReportContent projectReportContent) throws DataAccessException;

	/**
	 * 
	 * �������� ������Ʈ
	 * 
	 * @param projectReport
	 * @throws DataAccessException
	 */
	public void update(ProjectReportContent projectReportContent) throws DataAccessException;

	/**
	 * 
	 * �������� ����
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @throws DataAccessException
	 */
	public void delete1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException;

	/**
	 * 
	 * �������� ����
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @throws DataAccessException
	 */
	public void delete2(String projectCode, String assignDate, String writerSsn) throws DataAccessException;

	/**
	 * 
	 * �������� �˻�
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProjectReportContent> getProjectReportContent1(String projectCode) throws DataAccessException;

	public List<ProjectReportContent> getProjectReportContent1(String projectCode, String taskFormTypeId) throws DataAccessException;

	public ProjectReportContent getProjectReportContent1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException;

	public List<ProjectReportContent> getProjectReportContent2(String projectCode, String assignDate) throws DataAccessException;
	
	public ProjectReportContent getProjectReportContent2(String projectCode, String assignDate, String writerSsn) throws DataAccessException;

}
