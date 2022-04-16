/*
 * $Id: OrgdbManager.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 1. 17
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.manager;

import kr.co.kmac.pms.orgdb.data.OrgdbDetail;
import kr.co.kmac.pms.orgdb.form.OrgdbDetailForm;

import org.springframework.dao.DataAccessException;

/**
 * 전문가 풀을 관리하기 위한 인터페이스
 * @author jiwoongLee
 * @version $Id: OrgdbManager.java,v 1.1 2009/09/19 11:15:43 cvs3 Exp $
 */
public interface OrgdbManager {

	public String create(OrgdbDetailForm orgdbDetailForm) throws DataAccessException;

	public void update(OrgdbDetailForm orgdbDetailForm) throws DataAccessException;

	public void remove(String orgCode) throws DataAccessException;

	public OrgdbDetail retrieve(String orgCode) throws DataAccessException;
 
	public boolean isExist(String orgName) throws DataAccessException;

	public void projectOrgdbUpdate(String orgCode, String orgName, String projectCode, String idx)
			throws DataAccessException;

	public void customerOrgdbUpdate(String customerCode, String customerName, String idx) throws DataAccessException;

	public void expertOrgdbUpdate(String companyId, String companyName, String ssn) throws DataAccessException;
	
	public void orgdbCheck(String orgCode, String ssn) throws DataAccessException;
	
	public void orgdbCheck2(String orgCode) throws DataAccessException;

}
