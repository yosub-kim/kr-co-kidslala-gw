/*
 * $Id: CustomerDao.java,v 1.1 2013/01/06 10:04:26 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.dao;

import kr.co.kmac.pms.customer.data.CustomerForm;

import org.springframework.dao.DataAccessException;

public interface CustomerDao {
	public String insert(CustomerForm customerForm) throws DataAccessException;
	
	public String select(String ssn) throws DataAccessException;
	
	public String select2(String ssn) throws DataAccessException;
}
