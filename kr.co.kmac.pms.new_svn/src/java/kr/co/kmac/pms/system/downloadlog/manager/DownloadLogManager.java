/*
 * $Id: DownloadLogManager.java,v 1.4 2012/07/18 04:14:04 cvs Exp $
 * created by    : �ȼ�ȣ
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.manager;

import kr.co.kmac.pms.system.downloadlog.data.DownloadLogData;
import kr.co.kmac.pms.system.downloadlog.exception.DownloadLogException;

public interface DownloadLogManager {
	public void create(DownloadLogData downloadLogData) throws DownloadLogException;

	// public DownloadLogData retrieve(String documentNumber) throws DownloadLogException;
	public int getWeeklyCount(String ssn) throws DownloadLogException;

	public int getMonthlycount(String ssn, String fromDate, String toDate) throws DownloadLogException;
}
