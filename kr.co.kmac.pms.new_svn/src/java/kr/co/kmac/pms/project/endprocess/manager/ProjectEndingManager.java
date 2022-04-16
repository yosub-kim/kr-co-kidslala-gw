/*
 * $Id: ProjectEndingManager.java,v 1.3 2010/07/22 09:09:48 cvs2 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 28.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager;

import kr.co.kmac.pms.project.endprocess.form.ProjectEndingForm;
import kr.co.kmac.pms.project.exception.ProjectException;

/**
 * TODO 클래스 설명
 * @author CHO DAE HYON
 * @version $Id: ProjectEndingManager.java,v 1.3 2010/07/22 09:09:48 cvs2 Exp $
 */
public interface ProjectEndingManager {

	public void insertRateC(ProjectEndingForm projectEndingForm) throws ProjectException;
	
	public void insertRateP(ProjectEndingForm projectEndingForm) throws ProjectException;
	
	public void insertRateE(ProjectEndingForm projectEndingForm) throws ProjectException;

	public void insertRateCustomer(ProjectEndingForm projectEndingForm) throws ProjectException;
	
	public void insertEnding(ProjectEndingForm projectEndingForm) throws ProjectException;
	
	public void entrustEnding(ProjectEndingForm projectEndingForm) throws ProjectException;

}
