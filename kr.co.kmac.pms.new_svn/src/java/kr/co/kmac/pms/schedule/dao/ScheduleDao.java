/*
 * $Id: ScheduleDao.java,v 1.13 2019/03/18 00:57:58 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.dao;

import java.util.List;

import kr.co.kmac.pms.orgdb.exception.OrgdbException;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDailyMasterInfo;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.schedule.exception.ScheduleException;

import org.springframework.dao.DataAccessException;

public interface ScheduleDao {
	/**
	 * 사용자별 스케줄정보 조회
	 * @param regNo
	 * @return
	 * @throws OrgdbException
	 */
	public List scheduleListOfUser(String searchYear, String searchMonth, String searchDay, String ssn, String jobClass) throws DataAccessException;
	
	public String getGroupName(String dept) throws DataAccessException;
	
	public String getSanctionDept(String seq) throws DataAccessException;
	
	public String getSanctionDept2(String seq) throws DataAccessException;
	
	public String getSanctionDept3(String seq) throws DataAccessException;
	
	public String getSanctionDept4(String seq) throws DataAccessException;
	
	public void create(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException;
	
	public void createLog(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException;
	
	public void remove(String year, String month, String day, String ssn, int seq) throws DataAccessException;

	public void create_time(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException;
	
	public void create_time2(DailyScheduleInfo dailyScheduleInfo) throws DataAccessException;
	
	public void remove_time(String year, String month, String day, String ssn, int seq) throws DataAccessException;
	
	public List<DailyScheduleInfo> getScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException;
	public List<DailyScheduleInfo> getInternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException;
	public List<DailyScheduleInfo> getExternalScheduleListByMonth(String ssn, String year, String month, String day, String secretYN) throws DataAccessException;
	public List<DailyScheduleInfo> getDayOffListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<DailyScheduleInfo> getEducationListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<DailyScheduleInfo> getUpdayListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<DailyProjectInfo> getProjectScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<DailyProjectInfo> getProjectReportScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	
	// 일정관리에서 MM 투입일정 보이도록 변경
	public List<DailyProjectInfo> getProjectManpowerScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	// 일정관리에서 PJT 보이도록 변경
	public List<DailyProjectInfo> getProjectPJTScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<DailyProjectInfo> getProjectBoardScheduleListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<CustomerPickerInfo> getCustomerPickerListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public List<CustomerPickerInfo> getCustomerPickerDetailListByMonth(String ssn, String year, String month, String day) throws DataAccessException;
	public DailyProjectInfo getProjectSchedule(String projectCode, String ssn, String year, String month, String day) throws ScheduleException;
	
	public List<HolidayInfo> getHolidayListByMonth(String year, String month) throws DataAccessException;
	
	public DailyScheduleInfo getScheduleInfo(String ssn, String year, String month, String day, int seq) throws DataAccessException;

	public List<ScheduleUserInfo> getScheduleUserInfoList(String jobClass) throws DataAccessException;
	public List<ScheduleUserInfo> getScheduleExpertInfoList(String jobClass, String year, String month) throws DataAccessException;
	public List<ScheduleUserInfo> getScheduleRA13InfoList(String jobClass) throws DataAccessException;
	public List<ScheduleUserInfo> getScheduleRA4InfoList(String jobClass) throws DataAccessException;
	
	public List<ScheduleDailyMasterInfo> getScheduleDailyMasterInfoList(String jobClass, String year, String month) throws DataAccessException;
	public boolean isHoliday(String yyyymmdd) throws DataAccessException;

	public List<ScheduleUserInfo> getScheduleExpertRealInfoList(String jobClass, String year, String month) throws DataAccessException;
	public List<ScheduleUserInfo> getScheduleExpertRealInfoList_after(String jobClass, String year, String month) throws DataAccessException;
	
	// 근무 시간 관리 개발
	public List<DailyScheduleInfo> getScheduleListByMonth_time(String ssn, String year, String month, String day) throws DataAccessException;
	public DailyScheduleInfo getScheduleInfo_time(String ssn, String year, String month, String day, int seq) throws DataAccessException;
	public List<DailyScheduleInfo> getScheduleCnt_time(String year, String month, String ssn) throws DataAccessException;
	public List<DailyScheduleInfo> getConScheduleCnt(String year, String month, String ssn) throws DataAccessException;
	public List<DailyScheduleInfo> getScheduleCnt_time_cco(String year, String month, String ssn) throws DataAccessException;
	public List<DailyScheduleInfo> getScheduleCnt_time_cbo(String year, String month, String ssn) throws DataAccessException;
	public List<DailyScheduleInfo> getScheduleCnt_customer(String day, String ssn) throws DataAccessException;
	public List<DailyScheduleInfo> getScheduleCnt_customer2(String day, String ssn) throws DataAccessException;
}
