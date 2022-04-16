/*
 * $Id: ProjectReportManager.java,v 1.2 2010/07/19 06:08:58 cvs2 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.preport.manager;

import java.util.List;

import kr.co.kmac.pms.sanction.exception.SanctionException;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;

import org.springframework.dao.DataAccessException;

/**
 * TODO Ŭ���� ����
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportManager.java,v 1.2 2010/07/19 06:08:58 cvs2 Exp $
 */
public interface ProjectReportManager {

	/**
	 * �������� �Ѱ� ����
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void createProjectReport(ProjectReportContent projectReportContent) throws SanctionException;
	
	/**
	 * �������� ���� ����
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void executeProjectReport(ProjectReportContent projectReportContent) throws SanctionException;

	/**
	 * �������� ���� ����
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void entrustProjectReport(ProjectReportContent projectReportContent) throws SanctionException;

	/**
	 * �������� ����
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void updateProjectReport(ProjectReportContent projectReport) throws SanctionException;

	/**
	 * �������� ����
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @throws SanctionException
	 */
	public void deleteProjectReport1(String projectCode, String taskFormTypeId, String seq) throws SanctionException;

	public void deleteProjectReport2(String projectCode, String assignDate, String writerSsn) throws SanctionException;

	/**
	 * ������Ʈ �Ѱǿ� ���Ե� ��ü �������� ��ȯ
	 * 
	 * @param projectCode
	 * @return
	 * @throws SanctionException
	 */
	public List<ProjectReportContent> getProjectReportContent1(String projectCode, String taskFormTypeId) throws DataAccessException;

	public List<ProjectReportContent> getProjectReportContent2(String projectCode, String assignDate) throws DataAccessException;

	/**
	 * �������� �Ѱ� ��ȯ
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @return
	 * @throws SanctionException
	 */
	public ProjectReportContent getProjectReportContent1(String projectCode, String taskFormTypeId, String seq) throws DataAccessException;

	public ProjectReportContent getProjectReportContent2(String projectCode, String assignDate, String writerSsn) throws DataAccessException;

}
