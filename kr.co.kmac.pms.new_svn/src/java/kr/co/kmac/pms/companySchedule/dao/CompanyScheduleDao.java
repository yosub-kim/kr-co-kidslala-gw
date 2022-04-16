/*
 * $Id: CompanyScheduleDao.java,v 1.1 2009/09/19 11:15:21 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.dao;

import java.util.List;

import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;

import org.springframework.dao.DataAccessException;

public interface CompanyScheduleDao {
	public List<CompanyScheduleInfoData> scheduleList(String searchYear, String searchMonth, String searchDay, String startHour, String startMin) throws DataAccessException;
	public List<CompanyScheduleInfoData> scheduleListOfMonth(String searchYear, String searchMonth, String searchDay) throws DataAccessException;
	public void create(CompanyScheduleInfoData scheduleInfoData) throws DataAccessException;
	public void remove(String year, String month, String day, String startHour , String startMin) throws DataAccessException;
}
