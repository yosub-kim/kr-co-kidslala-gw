/*
 * $Id: SanctionDocDao.java,v 1.8 2016/02/03 23:49:00 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.dao;

import kr.co.kmac.pms.sanction.general.data.SanctionDoc;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocDao.java,v 1.8 2016/02/03 23:49:00 cvs Exp $
 */
public interface SanctionDocDao {

	public SanctionDoc insert(SanctionDoc sanctionDoc) throws DataAccessException;

	public void update(SanctionDoc sanctionDoc) throws DataAccessException;
	
	public void insertLog(SanctionDoc sanctionDoc) throws DataAccessException;

	public void delete(String seq, String projectCode, String approvalType) throws DataAccessException;

	public SanctionDoc select(String seq, String projectCode, String approvalType) throws DataAccessException;

	public SanctionDoc select(String projectCode) throws DataAccessException;

	public boolean isExist(String seq, String projectCode, String approvalType) throws DataAccessException;

	public boolean isIngSanctionExist(String projectCode, String approvalType) throws DataAccessException;

	public boolean updateErpState(String projectCode) throws DataAccessException;

	public void updateEduState(String projectCode) throws DataAccessException;
	
	public void updateEduStateDisabled(String projectCode) throws DataAccessException;
	
	// Job Date: 2010-02-07	Author: yhyim	Description: 기획사업현황 업데이트
	public void updateEduStateAgain(String oc_code) throws DataAccessException;
	
	// Job Date: 2010-02-07	Author: yhyim	Description: 기획사업현황 업데이트
	public void updateEduStateCancle(String oc_code) throws DataAccessException;
	
	// Job Date: 2015-07-15	Author: yhyim	Description: 기획사업현황 업데이트
	public void updateEduStateResanction(String oc_code) throws DataAccessException;
	
	// Job Date: 2015-07-20	Author: yhyim	Description: 기획사업현황 업데이트
	public void restoreEduStateResanction(String oc_code) throws DataAccessException;	
}
