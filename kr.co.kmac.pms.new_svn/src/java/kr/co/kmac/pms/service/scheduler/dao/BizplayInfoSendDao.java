/*
 * $Id: CostOverDao.java,v 1.1 2009/09/19 11:15:40 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.data.bizplay.dvsn.BizplayDeptInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.empl.BizplayExpertPoolInfo;
import kr.co.kmac.pms.service.scheduler.data.bizplay.pjt.BizplayProjectInfo;

import org.springframework.dao.DataAccessException;

public interface BizplayInfoSendDao {
	public List<BizplayProjectInfo> projectInfoList() throws DataAccessException;
	
	public List<BizplayProjectInfo> projectInfoList(String companyCode) throws DataAccessException;

	public List<BizplayProjectInfo> projectInfoListAll(String CompanyCode) throws DataAccessException;

	
	
	public List<BizplayExpertPoolInfo> expertPoolList() throws DataAccessException;

	public List<BizplayExpertPoolInfo> expertPoolList(String companyCode) throws DataAccessException;

	
	
	public List<BizplayDeptInfo> deptList() throws DataAccessException;

	public List<BizplayDeptInfo> deptList(String companyCode) throws DataAccessException;

	
	
	public boolean insertHistory(String endPoint, String sendTxt, String ResultTxt, String exceptionStr) throws DataAccessException;
}
