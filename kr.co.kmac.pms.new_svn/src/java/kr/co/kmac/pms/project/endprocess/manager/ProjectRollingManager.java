/*
 * $Id: ProjectRollingManager.java,v 1.5 2017/10/11 03:49:00 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import kr.co.kmac.pms.project.endprocess.form.ProjectRollingForm;
import kr.co.kmac.pms.project.exception.ProjectException;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectRollingManager.java,v 1.5 2017/10/11 03:49:00 cvs Exp $
 */
public interface ProjectRollingManager {

	public void insertRollingC(String projectCode) throws ProjectException;

	public void insertRollingR(ProjectRollingForm projectRollingForm) throws ProjectException;

	public void insertRollingE(ProjectRollingForm projectRollingForm) throws ProjectException;

	public void insertRollingF(ProjectRollingForm projectRollingForm) throws ProjectException;

	public void insertRollingT(ProjectRollingForm projectRollingForm) throws ProjectException;

	public Map isFinishedRolling(String projectCode, String seq) throws ProjectException;
	
	public int numOfCustomer(String project) throws ProjectException;
}
