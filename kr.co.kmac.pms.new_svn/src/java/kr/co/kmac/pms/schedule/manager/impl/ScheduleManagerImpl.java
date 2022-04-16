/*
 * $Id: ScheduleManagerImpl.java,v 1.13 2019/03/18 00:58:00 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.manager.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import kr.co.kmac.pms.schedule.dao.ScheduleDao;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDailyMasterInfo;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.schedule.exception.ScheduleException;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;

public class ScheduleManagerImpl implements ScheduleManager {

	private ScheduleDao scheduleDao;
	
	public ScheduleDao getScheduleDao() {
		return scheduleDao;
	}

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	public List scheduleListOfUser(String searchYear, String searchMonth, String searchDay, String ssn, String jobClass) throws ScheduleException {
		return scheduleDao.scheduleListOfUser(searchYear, searchMonth, searchDay, ssn, jobClass);
	}
	
	public String getGroupName(String dept) throws ScheduleException {
		return scheduleDao.getGroupName(dept);
	}
	
	public void create(DailyScheduleInfo dailyScheduleInfo) throws ScheduleException {
		scheduleDao.create(dailyScheduleInfo);
	}
	
	public void createLog(DailyScheduleInfo dailyScheduleInfo) throws ScheduleException {
		scheduleDao.createLog(dailyScheduleInfo);
	}

	public void remove(String year, String month, String day, String ssn, int seq) throws ScheduleException {
		scheduleDao.remove(year, month, day, ssn, seq);
	}
	
	public void create_time(DailyScheduleInfo dailyScheduleInfo) throws ScheduleException {
		scheduleDao.create_time(dailyScheduleInfo);
	}
	
	public void create_time2(DailyScheduleInfo dailyScheduleInfo) throws ScheduleException {
		scheduleDao.create_time2(dailyScheduleInfo);
	}

	public void remove_time(String year, String month, String day, String ssn, int seq) throws ScheduleException {
		scheduleDao.remove_time(year, month, day, ssn, seq);
	}
	
	public List<DailyScheduleInfo> getScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException {
		return scheduleDao.getScheduleListByMonth(ssn, year, month, day, secretYN);
	}
	
	public List<DailyScheduleInfo> getScheduleListByMonth_time(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getScheduleListByMonth_time(ssn, year, month, day);
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time(String year, String month, String ssn) throws ScheduleException {
		return scheduleDao.getScheduleCnt_time(year, month, ssn);
	}
	
	public List<DailyScheduleInfo> getConScheduleCnt(String year, String month, String ssn) throws ScheduleException {
		return scheduleDao.getConScheduleCnt(year, month, ssn);
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time_cco(String year, String month, String ssn) throws ScheduleException {
		return scheduleDao.getScheduleCnt_time_cco(year, month, ssn);
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_time_cbo(String year, String month, String ssn) throws ScheduleException {
		return scheduleDao.getScheduleCnt_time_cbo(year, month, ssn);
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_customer(String day, String ssn) throws ScheduleException {
		return scheduleDao.getScheduleCnt_customer(day, ssn);
	}
	
	public List<DailyScheduleInfo> getScheduleCnt_customer2(String day, String ssn) throws ScheduleException {
		return scheduleDao.getScheduleCnt_customer2(day, ssn);
	}
	
	public List<DailyScheduleInfo> getInternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException {
		return scheduleDao.getInternalScheduleListByMonth(ssn, year, month, day, secretYN);
	}
	
	public List<DailyScheduleInfo> getExternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException {
		return scheduleDao.getExternalScheduleListByMonth(ssn, year, month, day, secretYN);
	}
	
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getProjectScheduleListByMonth(ssn, year, month, day);
	}
	// 일정관리에서 MM 투입일정 보이도록 변경
	public List<DailyProjectInfo> getProjectManpowerScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getProjectManpowerScheduleListByMonth(ssn, year, month, day);
	}
	
	// 일정관리에서 PJT 게시판 보이도록 변경
	public List<DailyProjectInfo> getProjectPJTScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getProjectPJTScheduleListByMonth(ssn, year, month, day);
	}
	
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day, String type) throws ScheduleException {
		if (type.equals("report"))
			return scheduleDao.getProjectReportScheduleListByMonth(ssn, year, month, day);
		else if (type.equals("manpower"))
			return scheduleDao.getProjectManpowerScheduleListByMonth(ssn, year, month, day);
		else
			return scheduleDao.getProjectBoardScheduleListByMonth(ssn, year, month, day);
	}
	
	// jobDate: 2016-06-17	Author: yhyim	Description; get the list of day off
	public List<DailyScheduleInfo> getDayOffListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getDayOffListByMonth(ssn, year, month, day);
	}
	
	// jobDate: 2016-07-13	Author: yhyim	Description; get the list of education
	public List<DailyScheduleInfo> getEducationListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getEducationListByMonth(ssn, year, month, day);
	}
	
	// jobDate: 2016-06-17	Author: yhyim	Description; get the list of customer info
	public List<CustomerPickerInfo> getCustomerPickerListByMonth(String ssn, String year, String month, String day, String detailYN) throws ScheduleException {
		if (detailYN.equals("Y"))
			return scheduleDao.getCustomerPickerDetailListByMonth(ssn, year, month, day);
		else
			return scheduleDao.getCustomerPickerListByMonth(ssn, year, month, day);
	}
	
	// jobDate: 2018-06-05	Author: yhyim	Description; get the list of up-day
	public List<DailyScheduleInfo> getUpdayListByMonth(String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getUpdayListByMonth(ssn, year, month, day);
	}
	
	@Override
	public DailyProjectInfo getProjectSchedule(String projectCode, String ssn, String year, String month, String day) throws ScheduleException {
		return scheduleDao.getProjectSchedule(projectCode, ssn, year, month, day);
	}

	public List<HolidayInfo> getHolidayListByMonth(String year, String month) throws ScheduleException {
		return scheduleDao.getHolidayListByMonth(year, month);
	}
	
	public DailyScheduleInfo getScheduleInfo(String ssn, String year, String month, String day, int seq) throws ScheduleException {
		return scheduleDao.getScheduleInfo(ssn, year, month, day, seq);
	}
	
	public DailyScheduleInfo getScheduleInfo_time(String ssn, String year, String month, String day, int seq) throws ScheduleException {	
		return scheduleDao.getScheduleInfo_time(ssn, year, month, day, seq);
	}
	
	public List<ScheduleUserInfo> getScheduleUserList(String jobClass, String year, String month) throws ScheduleException {
		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		int nYear = calendar.get(Calendar.YEAR);
		int nMonth = calendar.get(Calendar.MONTH)+1;
		
		if (jobClass.equals("C")) {
			if(Integer.valueOf(year) >= nYear && Integer.valueOf(month) >= nMonth){
				return scheduleDao.getScheduleExpertRealInfoList_after(jobClass, year, month);
			}else{
				return scheduleDao.getScheduleExpertRealInfoList(jobClass, year, month);	
			}
		} else if (jobClass.equals("H")) {			
			return scheduleDao.getScheduleRA13InfoList(jobClass);   // RA1~3 검색		
		} else if (jobClass.equals("H2")) {		
			String jobclassChange = "N"; 
			return scheduleDao.getScheduleRA4InfoList(jobclassChange); // RA4 검색
		} else {
			return scheduleDao.getScheduleUserInfoList(jobClass);
		}
	}

	@Override
	public List<ScheduleDailyMasterInfo> getScheduleDailyMasterInfoList(String jobClass, String year, String month) throws ScheduleException {
		String jobclassChange = jobClass;
		
		if (jobclassChange.equals("H2")) {
			jobclassChange = "N";
		}
		
		return scheduleDao.getScheduleDailyMasterInfoList(jobclassChange, year, month);
	}

	@Override
	public boolean isHoliday(String yyyymmdd) throws ScheduleException {
		return scheduleDao.isHoliday(yyyymmdd);
	}
}
