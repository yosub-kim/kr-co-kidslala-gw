/*
 * $Id: CompanyScheduleManagerImpl.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.manager.impl;

import java.util.List;

import kr.co.kmac.pms.companySchedule.dao.CompanyScheduleDao;
import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;
import kr.co.kmac.pms.companySchedule.exception.CompanyScheduleException;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;

public class CompanyScheduleManagerImpl implements CompanyScheduleManager {
	private CompanyScheduleDao companyScheduleDao;

	public CompanyScheduleDao getCompanyScheduleDao() {
		return companyScheduleDao;
	}

	public void setCompanyScheduleDao(CompanyScheduleDao companyScheduleDao) {
		this.companyScheduleDao = companyScheduleDao;
	}

	public List<CompanyScheduleInfoData> scheduleList(String searchYear, String searchMonth, String searchDay, String startHour, String startMin)
			throws CompanyScheduleException {
		return companyScheduleDao.scheduleList(searchYear, searchMonth, searchDay, startHour, startMin);
	}
	
	public List<CompanyScheduleInfoData> scheduleListOfMonth(String searchYear, String searchMonth, String searchDay)
		throws CompanyScheduleException {
		return companyScheduleDao.scheduleListOfMonth(searchYear, searchMonth, searchDay);
	}

	public void create(CompanyScheduleInfoData scheduleInfoData) throws CompanyScheduleException {
		companyScheduleDao.create(scheduleInfoData);
	}

	public void remove(String year, String month, String day, String startHour , String startMin) throws CompanyScheduleException {
		companyScheduleDao.remove(year, month, day, startHour, startMin);
	}

}
