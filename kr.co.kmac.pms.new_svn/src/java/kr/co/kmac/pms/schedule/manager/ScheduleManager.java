/*
 * $Id: ScheduleManager.java,v 1.11 2018/06/06 13:11:27 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.manager;

import java.util.List;

import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDailyMasterInfo;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.schedule.exception.ScheduleException;

public interface ScheduleManager {
	/**
	 * 사용자별 스케줄정보  조회
	 * @param regNo
	 * @return
	 * @throws ExpertPoolException
	 */
	public List scheduleListOfUser(String searchYear, String searchMonth, String searchDay, String ssn, String jobClass) throws ScheduleException;
	
	public String getGroupName(String dept) throws ScheduleException;
	
	public void create(DailyScheduleInfo dailyScheduleInfo) throws ExpertPoolException;
	
	public void createLog(DailyScheduleInfo dailyScheduleInfo) throws ExpertPoolException;
	
	public void remove(String year, String month, String day, String ssn, int seq) throws ExpertPoolException;
	
	public List<DailyScheduleInfo> getScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException;

	public List<DailyScheduleInfo> getInternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException;

	public List<DailyScheduleInfo> getExternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws ScheduleException;

	public List<DailyScheduleInfo> getDayOffListByMonth(String ssn, String year, String month, String day) throws ScheduleException;

	public List<DailyScheduleInfo> getEducationListByMonth(String ssn, String year, String month, String day) throws ScheduleException;

	public List<CustomerPickerInfo> getCustomerPickerListByMonth(String ssn, String year, String month, String day, String detailYN) throws ScheduleException;
	
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException;
	
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day, String type) throws ScheduleException;
	
	// 일정관리에서 MM 투입일정 보이도록 변경
	public List<DailyProjectInfo> getProjectManpowerScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException;
	
	// 일정관리에서 PJT 게시판 보이도록 변경
	public List<DailyProjectInfo> getProjectPJTScheduleListByMonth(String ssn, String year, String month, String day) throws ScheduleException;
	
	public DailyProjectInfo getProjectSchedule(String projectCode, String ssn, String year, String month, String day) throws ScheduleException;
	
	// JobDate: 2012-06-28	Author: yhyim	Description: get holiday by month
	public List<HolidayInfo> getHolidayListByMonth(String year, String month) throws ScheduleException;
	
	// JobDate: 2018-06-05	Author: yhyim	Description: get up-day by month
	public List<DailyScheduleInfo> getUpdayListByMonth(String ssn, String year, String month, String day) throws ScheduleException;
	
	public DailyScheduleInfo getScheduleInfo(String ssn, String year, String month, String day, int seq) throws ScheduleException;
	
	public List<ScheduleUserInfo> getScheduleUserList(String jobClass, String year, String month) throws ScheduleException;
	
	public List<ScheduleDailyMasterInfo> getScheduleDailyMasterInfoList(String jobClass, String year, String month) throws ScheduleException;

	public boolean isHoliday(String yyyymmdd) throws ScheduleException;
	
	// JobDate: 2019-11-13 Author: yskim	Descriptiono: 근무 시간 관리 화면 개선
	public DailyScheduleInfo getScheduleInfo_time(String ssn, String year, String month, String day, int seq) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleListByMonth_time(String ssn, String year, String month, String day) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleCnt_time(String year, String month, String ssn) throws ScheduleException;
	public List<DailyScheduleInfo> getConScheduleCnt(String year, String month, String ssn) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleCnt_time_cco(String year, String month, String ssn) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleCnt_time_cbo(String year, String month, String ssn) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleCnt_customer(String day, String ssn) throws ScheduleException;
	public List<DailyScheduleInfo> getScheduleCnt_customer2(String day, String ssn) throws ScheduleException;
	
	
	public void create_time(DailyScheduleInfo dailyScheduleInfo) throws ExpertPoolException;
	public void create_time2(DailyScheduleInfo dailyScheduleInfo) throws ExpertPoolException;
	
	public void remove_time(String year, String month, String day, String ssn, int seq) throws ExpertPoolException;
}
