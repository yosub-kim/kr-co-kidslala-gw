/*
 * $Id: DownloadLogManagerImpl.java,v 1.4 2012/07/18 04:14:04 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.manager.impl;

import kr.co.kmac.pms.system.downloadlog.dao.DownloadLogDao;
import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;
import kr.co.kmac.pms.system.downloadlog.exception.DownloadLogException;
import kr.co.kmac.pms.system.downloadlog.manager.DownloadLogManager;

public class DownloadLogManagerImpl implements DownloadLogManager {
	private DownloadLogDao downloadLogDao;

	@Override
	public void create(DownloadLogData downloadLogData) throws DownloadLogException {
		downloadLogDao.create(downloadLogData);
	}

	@Override
	public int getWeeklyCount(String ssn) throws DownloadLogException {
		return this.downloadLogDao.getWeeklyCount(ssn);
	}

	@Override
	public int getMonthlycount(String ssn, String fromDate, String toDate) throws DownloadLogException {
		return this.downloadLogDao.getMonthlycount(ssn, fromDate, toDate);
	}

	public DownloadLogDao getDownloadLogDao() {
		return downloadLogDao;
	}

	public void setDownloadLogDao(DownloadLogDao downloadLogDao) {
		this.downloadLogDao = downloadLogDao;
	}

}
