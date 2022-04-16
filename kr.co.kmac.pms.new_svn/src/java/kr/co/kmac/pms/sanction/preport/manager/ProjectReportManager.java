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
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportManager.java,v 1.2 2010/07/19 06:08:58 cvs2 Exp $
 */
public interface ProjectReportManager {

	/**
	 * 지도일지 한건 생성
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void createProjectReport(ProjectReportContent projectReportContent) throws SanctionException;
	
	/**
	 * 지도일지 업무 진행
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void executeProjectReport(ProjectReportContent projectReportContent) throws SanctionException;

	/**
	 * 지도일지 업무 위임
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void entrustProjectReport(ProjectReportContent projectReportContent) throws SanctionException;

	/**
	 * 지도일지 수정
	 * 
	 * @param projectReport
	 * @throws SanctionException
	 */
	public void updateProjectReport(ProjectReportContent projectReport) throws SanctionException;

	/**
	 * 지도일지 삭제
	 * 
	 * @param projectCode
	 * @param taskFormTypeId
	 * @param seq
	 * @throws SanctionException
	 */
	public void deleteProjectReport1(String projectCode, String taskFormTypeId, String seq) throws SanctionException;

	public void deleteProjectReport2(String projectCode, String assignDate, String writerSsn) throws SanctionException;

	/**
	 * 프로젝트 한건에 포함된 전체 지도일지 반환
	 * 
	 * @param projectCode
	 * @return
	 * @throws SanctionException
	 */
	public List<ProjectReportContent> getProjectReportContent1(String projectCode, String taskFormTypeId) throws DataAccessException;

	public List<ProjectReportContent> getProjectReportContent2(String projectCode, String assignDate) throws DataAccessException;

	/**
	 * 지도일지 한건 반환
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
