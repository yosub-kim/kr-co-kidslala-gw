/*
 * $Id: CostOverManager.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.manager;

import java.util.List;

import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfoSet;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfoSet;

public interface BizplayInfoSendManager {

	//public List<BizplayProjectInfo> projectInfoList() throws ServiceException;

	//public List<BizplayProjectInfo> projectInfoList(String companyCode) throws ServiceException;

	public List<BizplayProjectInfoSet> projectInfoJsonObject(String companyCode) throws ServiceException;
	
	public List<BizplayProjectInfoSet> projectInfoJsonObjectAll(String companyCode) throws ServiceException;

	
	//public List<BizplayExpertPoolInfo> expertPoolList() throws ServiceException;

	//public List<BizplayExpertPoolInfo> expertPoolList(String companyCode) throws ServiceException;

	public List<BizplayExpertPoolInfoSet> expertPoolJsonObject(String companyCode) throws ServiceException;

	
	//public List<BizplayDeptInfo> deptInfoList() throws ServiceException;

	//public List<BizplayDeptInfo> deptInfoList(String companyCode) throws ServiceException;

	public List<BizplayDeptInfoSet> deptInfoJsonObject(String companyCode) throws ServiceException;


	public boolean insertHistory(String endPoint, String sendTxt, String ResultTxt, String exceptionStr) throws ServiceException;

}
