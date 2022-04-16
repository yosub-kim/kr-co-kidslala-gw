/*
 * $Id: DownloadLogDao.java,v 1.4 2012/07/18 04:14:04 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.dao;

import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;

import org.springframework.dao.DataAccessException;

public interface DownloadLogDao {
	public void create(DownloadLogData downloadLogData) throws DataAccessException;

	public int getWeeklyCount(String ssn) throws DataAccessException;

	public int getMonthlycount(String ssn, String fromDate, String toDate) throws DataAccessException;

}
