/*
 * $Id: ProjectEndProcessManager.java,v 1.2 2010/09/01 12:59:33 cvs1 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 28.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.manager;

import kr.co.kmac.pms.project.exception.ProjectException;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectEndProcessManager.java,v 1.2 2010/09/01 12:59:33 cvs1 Exp $
 */
public interface ProjectEndProcessManager {

	public void doEndProcess(String projectCode) throws ProjectException;
	public void doResendCSMail(String projectCode) throws ProjectException;

}
