/*
 * $Id: OrgdbManagerImpl.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : May 22, 2008
 * =========================================================
 * Copyright (c) 2008 ManInSoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.manager.impl;

import kr.co.kmac.pms.orgdb.dao.OrgdbDao;
import kr.co.kmac.pms.orgdb.data.OrgdbDetail;
import kr.co.kmac.pms.orgdb.exception.OrgdbException;
import kr.co.kmac.pms.orgdb.form.OrgdbDetailForm;
import kr.co.kmac.pms.orgdb.manager.OrgdbManager;

public class OrgdbManagerImpl implements OrgdbManager {

	private OrgdbDao orgdbDao;

	/**
	 * @return Returns the orgdbDao.
	 */
	public OrgdbDao getOrgdbDao() {
		return orgdbDao;
	}

	/**
	 * @param orgdbDao The orgdbDao to set.
	 */
	public void setOrgdbDao(OrgdbDao orgdbDao) {
		this.orgdbDao = orgdbDao;
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.orgdb.manager.OrgdbManager#create(kr.co.kmac.pms.orgdb.form.OrgdbDetailForm)
	 */
	public String create(OrgdbDetailForm orgdbDetailForm) throws OrgdbException {
		return getOrgdbDao().create(orgdbDetailForm);
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.orgdb.manager.OrgdbManager#isExist(java.lang.String)
	 */
	public boolean isExist(String orgName) throws OrgdbException {
		return getOrgdbDao().isExist(orgName);
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.orgdb.manager.OrgdbManager#remove(java.lang.String)
	 */
	public void remove(String orgCode) throws OrgdbException {
		getOrgdbDao().remove(orgCode);
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.orgdb.manager.OrgdbManager#retrieve(java.lang.String)
	 */
	public OrgdbDetail retrieve(String orgCode) throws OrgdbException {
		return getOrgdbDao().retrieve(orgCode);
	}

	/*
	 * (non-Javadoc)
	 * @see kr.co.kmac.pms.orgdb.manager.OrgdbManager#update(kr.co.kmac.pms.orgdb.form.OrgdbDetailForm)
	 */
	public void update(OrgdbDetailForm orgdbDetailForm) throws OrgdbException {
		getOrgdbDao().update(orgdbDetailForm);
	}

	public void projectOrgdbUpdate(String orgCode, String orgName, String projectCode, String idx)
			throws OrgdbException {
		getOrgdbDao().projectOrgdbUpdate(orgCode, orgName, projectCode, idx);
	}

	public void customerOrgdbUpdate(String customerCode, String customerName, String idx) throws OrgdbException {
		getOrgdbDao().customerOrgdbUpdate(customerCode, customerName, idx);
	}

	public void expertOrgdbUpdate(String companyId, String companyName, String ssn) throws OrgdbException {
		getOrgdbDao().expertOrgdbUpdate(companyId, companyName, ssn);
	}
	
	public void orgdbCheck(String orgCode, String ssn) throws OrgdbException {
		getOrgdbDao().OrgdbCheckUpdate(orgCode, ssn);
	}
	
	public void orgdbCheck2(String orgCode) throws OrgdbException {
		getOrgdbDao().OrgdbCheckDelete(orgCode);
	}

}
