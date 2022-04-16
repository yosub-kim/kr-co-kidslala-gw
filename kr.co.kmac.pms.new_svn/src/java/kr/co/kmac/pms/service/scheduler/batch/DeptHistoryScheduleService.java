/*
 * $Id: DeptHistoryScheduleService.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 * created by    : yhyim
 * creation-date : 2008. 1. 11.
 * =========================================================
 * Copyright (c) 2008 KMAC, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import java.util.List;

import kr.co.kmac.pms.service.scheduler.dao.DeptHistoryServiceDao;
import kr.co.kmac.pms.service.scheduler.data.DeptHistoryData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author yhyim
 * @version $Id: DeptHistoryScheduleService.java,v 1.1 2009/09/19 11:15:37 cvs3 Exp $
 */
public class DeptHistoryScheduleService {
	private static final Log log = LogFactory.getLog(DeptHistoryScheduleService.class);
	private DeptHistoryServiceDao deptHistoryServiceDao;

	public void insertDeptInfoIntoHistory() {
		List<DeptHistoryData> expertList = getDeptHistoryServiceDao().getWorkingExpertList();
		if (expertList != null && expertList.size() > 0) {
			for (DeptHistoryData deptHistoryData : expertList) {
				this.getDeptHistoryServiceDao().setWorkingPUMemberInfoToHistory(deptHistoryData);
			}
		}
		log.info("## DeptHistoryScheduleService was executed...");
	}

	// 이력 관리 할 PU원 리스트
	public DeptHistoryServiceDao getDeptHistoryServiceDao() {
		return this.deptHistoryServiceDao;
	}

	/**
	 * @param deptHistoryServiceDao The deptHistoryServiceDao to set.
	 */
	public void setDeptHistoryServiceDao(DeptHistoryServiceDao deptHistoryServiceDao) {
		this.deptHistoryServiceDao = deptHistoryServiceDao;
	}
}