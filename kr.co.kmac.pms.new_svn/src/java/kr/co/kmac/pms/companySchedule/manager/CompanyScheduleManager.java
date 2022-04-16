/*
 * $Id: CompanyScheduleManager.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.manager;

import java.util.List;

import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;
import kr.co.kmac.pms.companySchedule.exception.CompanyScheduleException;

public interface CompanyScheduleManager {
	public List<CompanyScheduleInfoData> scheduleList(String searchYear, String searchMonth, String searchDay, String startHour, String startMin) throws CompanyScheduleException;
	public List<CompanyScheduleInfoData> scheduleListOfMonth(String searchYear, String searchMonth, String searchDay) throws CompanyScheduleException;
	public void create(CompanyScheduleInfoData scheduleInfoData) throws CompanyScheduleException;
	public void remove(String year, String month, String day, String startHour , String startMin) throws CompanyScheduleException;
}
