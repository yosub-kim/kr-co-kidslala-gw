/*
 * $Id: DownloadLimitService.java,v 1.1 2012/07/17 09:13:59 cvs Exp $
 * created by    : yhyim
 * creation-date : 2012. 7. 17.
 * =========================================================
 * Copyright (c) 2012 KMAC, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import kr.co.kmac.pms.expertpool.dao.ExpertPoolDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: DownloadLimitService.java,v 1.1 2012/07/17 09:13:59 cvs Exp $
 */
public class DownloadLimitService {
	private static final Log log = LogFactory.getLog(DeptHistoryScheduleService.class);
	private ExpertPoolDao expertPoolDao;

	public void initDownloadUnlimitState() {
		this.expertPoolDao.initDownloadUnlimitState();
		log.info("## DownloadLimitService was executed...");
	}

	public ExpertPoolDao getExpertPoolDao() {
		return expertPoolDao;
	}

	public void setExpertPoolDao(ExpertPoolDao expertPoolDao) {
		this.expertPoolDao = expertPoolDao;
	}
}