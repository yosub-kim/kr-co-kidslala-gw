/*
 * $Id: ScheduleAction.java,v 1.27 2019/03/19 03:43:27 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.action;

import java.io.Serializable;
import java.net.InetAddress;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDailyMasterInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDay;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.schedule.data.UserGroupInfo;
import kr.co.kmac.pms.schedule.data.UserInfo;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

// jobDate: 2016-06-17	Author: yhyim

/**
 * @struts.action name="scheduleAction" path="/action/ScheduleAction" parameter="mode" scope="request"
 * @struts.action-forward name="scheduleMonth" path="/schedule/scheduleMonth.jsp" redirect="false"
 * @struts.action-forward name="scheduleByAll" path="/schedule/scheduleByAll.jsp" redirect="false"
 * @struts.action-forward name="scheduleForm" path="/schedule/scheduleForm.jsp" redirect="false"
 * @struts.action-forward name="scheduleForm_time" path="/schedule/scheduleForm_time.jsp" redirect="false"
 * @struts.action-forward name="scheduleMonth_time" path="/schedule/scheduleMonth_time.jsp" redirect="false"
 * @struts.action-forward name="scheduleManagerSelectList" path="/schedule/scheduleManagerSelectList.jsp" redirect="false"
 * @struts.action-forward name="myScheduleByMain" path="/schedule/main_mySchedule.jsp" redirect="false"
 * @struts.action-forward name="companyScheduleByMain" path="/schedule/main_companySchedule.jsp" redirect="false"
 * @struts.action-forward name="result" path="/common/jsp/result.jsp" redirect="false"
 */
public class ScheduleAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ScheduleAction.class);
	private ScheduleManager scheduleManager;
	private CommonCodeManager commonCodeManager;
	private ExpertPoolManager expertPoolManager;
	private CompanyScheduleManager companyScheduleManager;

	public CompanyScheduleManager getCompanyScheduleManager() {
		return companyScheduleManager;
	}

	public void setCompanyScheduleManager(CompanyScheduleManager companyScheduleManager) {
		this.companyScheduleManager = companyScheduleManager;
	}

	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;

	}

	public ActionForward scheduleOfMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		String history = ServletRequestUtils.getStringParameter(request, "history", "false");
		String chkOption = ServletRequestUtils.getStringParameter(request, "chkOption", "N");
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", SessionUtils.getUsername(request));
		boolean isManager = (StringUtil.isNull(request.getParameter("isManager"), "").equals("MANAGER")) ? true : false; // 스케줄 관리자 여부 체크

		logger.debug("ssn : " + SessionUtils.getUsername(request));

		String scheduleUserName = this.getExpertPoolManager().retrieve(ssn).getName();

		boolean cSsn = (ssn.equals(SessionUtils.getUserObjext().getId())) ? true : false;

		// 스케줄 관리자에게 입력 기능 활성화
		if (isManager)
			cSsn = true;

		String secretYN = "";
		if (!ssn.equals(SessionUtils.getUserObjext().getId())) {
			secretYN = "N";
		}
		int intYear = 0;
		int intMonth = 0;
		String strNowDay = "";
		Calendar c = new GregorianCalendar();
		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			intYear = c.get(Calendar.YEAR);
			intMonth = c.get(Calendar.MONTH);
		} else {
			intYear = Integer.parseInt(selectedYear);
			intMonth = Integer.parseInt(selectedMonth) - 1;
		}
		strNowDay = "0" + Integer.toString(c.get(Calendar.DATE));
		strNowDay = strNowDay.substring(strNowDay.length() - 2, strNowDay.length());

		c.set(intYear, intMonth, 01);

		int startIndex = c.get(Calendar.DAY_OF_WEEK) - 1;

		int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int iDay = 1;
		boolean startCheck = false;
		String strMonth = "0" + Integer.toString(intMonth + 1);
		strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());

		List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // 지도일정
		List<DailyProjectInfo> projectList_MM = scheduleManager.getProjectManpowerScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // 투입일정관리
		List<DailyProjectInfo> projectList_PJT = scheduleManager.getProjectPJTScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // PJT 게시판
		List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "", secretYN); // 일정 전체(휴가 포함)
		List<DailyScheduleInfo> internalScheduleList = scheduleManager.getInternalScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "", secretYN); // 내부일정
		List<DailyScheduleInfo> externalScheduleList = scheduleManager.getExternalScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "", secretYN); // 외부일정
		List<DailyScheduleInfo> dayOffScheduleList = scheduleManager.getDayOffListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // 휴가
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(Integer.toString(intYear), strMonth); // 고객정보 리스트
		// JobDate: 2016-06-18 Author: yhyim Description: get the list of customerInfo
		List<CustomerPickerInfo> customerPickerList = scheduleManager.getCustomerPickerListByMonth(ssn, Integer.toString(intYear), strMonth, "", "");
		// JobDate: 2016-07-13 Author: yhyim Description: get the list of education
		List<DailyScheduleInfo> educationScheduleList = scheduleManager.getEducationListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // 교육일정
		List<DailyScheduleInfo> updayScheduleList = scheduleManager.getUpdayListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // Up-day

		int mi = 0;
		int pi = 0;
		int si = 0;
		int ii = 0;
		int ei = 0;
		int di = 0;
		int hi = 0;
		int ci = 0;
		int edu = 0;
		int ud = 0;
		int pji = 0;

		List<List<ScheduleDay>> arrWeek = new ArrayList<List<ScheduleDay>>();
		while (iDay <= endDay) {
			List<ScheduleDay> arrDay = new ArrayList<ScheduleDay>();
			for (int j = 0; j < 7; j++) {
				String dayId = "";
				String day = "";
				String date = "";
				List<DailyProjectInfo> dailyProjectLst = new ArrayList<DailyProjectInfo>();
				List<DailyProjectInfo> dailyProjectLst_MM = new ArrayList<DailyProjectInfo>();
				List<DailyProjectInfo> dailyProjectLst_PJT = new ArrayList<DailyProjectInfo>();
				List<DailyScheduleInfo> dailyScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyInternalScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyExternalScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyDayOffLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyEducationLst = new ArrayList<DailyScheduleInfo>();
				List<CustomerPickerInfo> dailyCustomerInfoLst = new ArrayList<CustomerPickerInfo>();
				List<HolidayInfo> holidayLst = new ArrayList<HolidayInfo>();
				List<DailyScheduleInfo> dailyUpdayLst = new ArrayList<DailyScheduleInfo>();

				if (startIndex == j) {
					startCheck = true;
				}
				if (iDay > endDay)
					startCheck = false;
				if (startCheck) {
					String tmpStr;

					dayId = "ID" + Integer.toString(intYear);
					tmpStr = "0" + Integer.toString(intMonth + 1);
					dayId += "_" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
					tmpStr = "0" + Integer.toString(iDay);
					dayId += "_" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());

					day = Integer.toString(iDay);

					date = Integer.toString(intYear);
					tmpStr = "0" + Integer.toString(intMonth + 1);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
					tmpStr = "0" + Integer.toString(iDay);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());

					while ((pi < projectList.size()) && (iDay == Integer.parseInt(projectList.get(pi).getDay()))) {
						DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
						dailyProjectInfo = projectList.get(pi);

						dailyProjectLst.add(dailyProjectInfo);

						pi++;
					}

					// 일정관리에서 MM 투입일정 보이도록 변경

					while ((mi < projectList_MM.size()) && (iDay == Integer.parseInt(projectList_MM.get(mi).getDay()))) {
						DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
						dailyProjectInfo = projectList_MM.get(mi);

						dailyProjectLst_MM.add(dailyProjectInfo);

						mi++;
					}

					while ((pji < projectList_PJT.size()) && (iDay == Integer.parseInt(projectList_PJT.get(pji).getDay()))) {
						DailyProjectInfo dailyProjectInfo = new DailyProjectInfo();
						dailyProjectInfo = projectList_PJT.get(pji);

						dailyProjectLst_PJT.add(dailyProjectInfo);

						pji++;
					}

					while ((si < scheduleList.size()) && (iDay == Integer.parseInt(scheduleList.get(si).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = scheduleList.get(si);

						dailyScheduleLst.add(dailyScheduleInfo);

						si++;
					}
					while ((ii < internalScheduleList.size()) && (iDay == Integer.parseInt(internalScheduleList.get(ii).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = internalScheduleList.get(ii);

						dailyInternalScheduleLst.add(dailyScheduleInfo);

						ii++;
					}
					while ((ei < externalScheduleList.size()) && (iDay == Integer.parseInt(externalScheduleList.get(ei).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = externalScheduleList.get(ei);

						dailyExternalScheduleLst.add(dailyScheduleInfo);

						ei++;
					}
					while ((di < dayOffScheduleList.size()) && (iDay == Integer.parseInt(dayOffScheduleList.get(di).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = dayOffScheduleList.get(di);

						dailyDayOffLst.add(dailyScheduleInfo);

						di++;
					}
					while ((edu < educationScheduleList.size()) && (iDay == Integer.parseInt(educationScheduleList.get(edu).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = educationScheduleList.get(edu);

						dailyEducationLst.add(dailyScheduleInfo);

						edu++;
					}
					while ((ci < customerPickerList.size()) && (iDay == Integer.parseInt(customerPickerList.get(ci).getDay()))) {
						CustomerPickerInfo customerPickerInfo = new CustomerPickerInfo();
						customerPickerInfo = customerPickerList.get(ci);

						dailyCustomerInfoLst.add(customerPickerInfo);

						ci++;
					}
					while ((hi < holidayList.size()) && (iDay == Integer.parseInt(holidayList.get(hi).getDay()))) {
						HolidayInfo holidayInfo = new HolidayInfo();
						holidayInfo = holidayList.get(hi);

						holidayLst.add(holidayInfo);

						hi++;
					}
					while ((ud < updayScheduleList.size()) && (iDay == Integer.parseInt(updayScheduleList.get(ud).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = updayScheduleList.get(ud);

						dailyUpdayLst.add(dailyScheduleInfo);

						ud++;
					}

					iDay++;
				}

				ScheduleDay nDay = new ScheduleDay();
				nDay.setDayId(dayId);
				nDay.setDay(day);
				nDay.setDate(date);
				nDay.setDailyProjectLst(dailyProjectLst);
				nDay.setDailyProjectLst_MM(dailyProjectLst_MM);
				nDay.setDailyProjectLst_PJT(dailyProjectLst_PJT);
				nDay.setDailyScheduleLst(dailyScheduleLst);
				nDay.setDailyInternalScheduleLst(dailyInternalScheduleLst);
				nDay.setDailyExternalScheduleLst(dailyExternalScheduleLst);
				nDay.setDailyDayOffLst(dailyDayOffLst);
				nDay.setDailyEducationLst(dailyEducationLst);
				nDay.setCustomerPickerLst(dailyCustomerInfoLst);
				nDay.setHolidayLst(holidayLst);
				nDay.setDailyUpdayLst(dailyUpdayLst);
				nDay.setDailyProjectLstCount(dailyProjectLst.size());

				// 추가
				nDay.setDailyProjectLst_MMCount(dailyProjectLst_MM.size());
				nDay.setDailyProjectLst_PJTCount(dailyProjectLst_PJT.size());
				nDay.setDailyInternalScheduleLstCount(dailyInternalScheduleLst.size());
				nDay.setDailyExternalScheduleLstCount(dailyExternalScheduleLst.size());
				nDay.setDailyDayOffLstCount(dailyDayOffLst.size());
				nDay.setDailyEducationLstCount(dailyEducationLst.size());
				nDay.setCustomerPickerLstCount(dailyCustomerInfoLst.size());
				nDay.setHolidayLstCount(holidayLst.size());
				nDay.setDailyUpdayLstCount(dailyUpdayLst.size());

				arrDay.add(nDay);
			}
			arrWeek.add(arrDay);
		}

		request.setAttribute("intYear", intYear);
		request.setAttribute("intMonth", intMonth + 1);
		request.setAttribute("chkOption", chkOption);
		request.setAttribute("history", history);
		request.setAttribute("ssn", ssn);
		request.setAttribute("cSsn", cSsn);
		request.setAttribute("isManager", isManager);
		request.setAttribute("scheduleUserName", scheduleUserName);
		request.setAttribute("NowDate", Integer.toString(intYear) + "-" + strMonth + "-" + strNowDay);

		request.setAttribute("calendar", arrWeek);
		return mapping.findForward("scheduleMonth");
	}


	public ActionForward scheduleOfMonth_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		String history = ServletRequestUtils.getStringParameter(request, "history", "false");
		String chkOption = ServletRequestUtils.getStringParameter(request, "chkOption", "N");
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", SessionUtils.getUsername(request));
		boolean isManager = (StringUtil.isNull(request.getParameter("isManager"), "").equals("MANAGER")) ? true : false; // 스케줄 관리자 여부 체크

		logger.debug("ssn : " + SessionUtils.getUsername(request));

		String scheduleUserName = this.getExpertPoolManager().retrieve(ssn).getName();

		boolean cSsn = (ssn.equals(SessionUtils.getUserObjext().getId())) ? true : false;

		// 스케줄 관리자에게 입력 기능 활성화
		if (isManager)
			cSsn = true;

		int intYear = 0;
		int intMonth = 0;
		String strNowDay = "";
		Calendar c = new GregorianCalendar();
		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			intYear = c.get(Calendar.YEAR);
			intMonth = c.get(Calendar.MONTH);
		} else {
			intYear = Integer.parseInt(selectedYear);
			intMonth = Integer.parseInt(selectedMonth) - 1;
		}
		strNowDay = "0" + Integer.toString(c.get(Calendar.DATE));
		strNowDay = strNowDay.substring(strNowDay.length() - 2, strNowDay.length());

		c.set(intYear, intMonth, 01);

		int startIndex = c.get(Calendar.DAY_OF_WEEK) - 1;

		int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		int iDay = 1;
		boolean startCheck = false;
		String strMonth = "0" + Integer.toString(intMonth + 1);
		strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
		/*
		 * List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); // 지도일정
		 * List<DailyProjectInfo> projectList_MM = scheduleManager.getProjectManpowerScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); //
		 * 투입일정관리 List<DailyProjectInfo> projectList_PJT = scheduleManager.getProjectPJTScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, ""); //
		 * PJT 게시판 List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "", secretYN); // 일정
		 * 전체(휴가 포함) List<DailyScheduleInfo> internalScheduleList = scheduleManager.getInternalScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "",
		 * secretYN); // 내부일정 List<DailyScheduleInfo> externalScheduleList = scheduleManager.getExternalScheduleListByMonth(ssn, Integer.toString(intYear),
		 * strMonth, "", secretYN); // 외부일정 List<DailyScheduleInfo> dayOffScheduleList = scheduleManager.getDayOffListByMonth(ssn, Integer.toString(intYear),
		 * strMonth, ""); // 휴가 // JobDate: 2016-06-18 Author: yhyim Description: get the list of customerInfo List<CustomerPickerInfo> customerPickerList =
		 * scheduleManager.getCustomerPickerListByMonth(ssn, Integer.toString(intYear), strMonth, "", ""); // JobDate: 2016-07-13 Author: yhyim Description: get
		 * the list of education List<DailyScheduleInfo> educationScheduleList = scheduleManager.getEducationListByMonth(ssn, Integer.toString(intYear),
		 * strMonth, ""); // 교육일정 List<DailyScheduleInfo> updayScheduleList = scheduleManager.getUpdayListByMonth(ssn, Integer.toString(intYear), strMonth, "");
		 * // Up-day
		 */
		// 근무기간
		List<DailyScheduleInfo> scheduleList_time = scheduleManager.getScheduleListByMonth_time(ssn, Integer.toString(intYear), strMonth, ""); // 일정 전체(휴가 포함)
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(Integer.toString(intYear), strMonth);

		int sit = 0;
		int hi = 0;

		List<List<ScheduleDay>> arrWeek = new ArrayList<List<ScheduleDay>>();
		while (iDay <= endDay) {
			List<ScheduleDay> arrDay = new ArrayList<ScheduleDay>();
			for (int j = 0; j < 7; j++) {
				String dayId = "";
				String day = "";
				String date = "";
				List<DailyProjectInfo> dailyProjectLst = new ArrayList<DailyProjectInfo>();
				List<DailyProjectInfo> dailyProjectLst_MM = new ArrayList<DailyProjectInfo>();
				List<DailyProjectInfo> dailyProjectLst_PJT = new ArrayList<DailyProjectInfo>();
				List<DailyScheduleInfo> dailyScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyInternalScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyExternalScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyDayOffLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyEducationLst = new ArrayList<DailyScheduleInfo>();
				List<CustomerPickerInfo> dailyCustomerInfoLst = new ArrayList<CustomerPickerInfo>();
				List<HolidayInfo> holidayLst = new ArrayList<HolidayInfo>();
				List<DailyScheduleInfo> dailyUpdayLst = new ArrayList<DailyScheduleInfo>();
				List<DailyScheduleInfo> dailyScheduleLst_time = new ArrayList<DailyScheduleInfo>();

				if (startIndex == j) {
					startCheck = true;
				}
				if (iDay > endDay)
					startCheck = false;
				if (startCheck) {
					String tmpStr;

					dayId = "ID" + Integer.toString(intYear);
					tmpStr = "0" + Integer.toString(intMonth + 1);
					dayId += "_" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
					tmpStr = "0" + Integer.toString(iDay);
					dayId += "_" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());

					day = Integer.toString(iDay);

					date = Integer.toString(intYear);
					tmpStr = "0" + Integer.toString(intMonth + 1);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
					tmpStr = "0" + Integer.toString(iDay);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());

					while ((sit < scheduleList_time.size()) && (iDay == Integer.parseInt(scheduleList_time.get(sit).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = scheduleList_time.get(sit);

						dailyScheduleLst_time.add(dailyScheduleInfo);

						sit++;
					}
					while ((hi < holidayList.size()) && (iDay == Integer.parseInt(holidayList.get(hi).getDay()))) {
						HolidayInfo holidayInfo = new HolidayInfo();
						holidayInfo = holidayList.get(hi);

						holidayLst.add(holidayInfo);

						hi++;
					}

					iDay++;
				}

				ScheduleDay nDay = new ScheduleDay();
				nDay.setDayId(dayId);
				nDay.setDay(day);
				nDay.setDate(date);
				nDay.setDailyProjectLst(dailyProjectLst);
				nDay.setDailyProjectLst_MM(dailyProjectLst_MM);
				nDay.setDailyProjectLst_PJT(dailyProjectLst_PJT);
				nDay.setDailyScheduleLst(dailyScheduleLst);
				nDay.setDailyInternalScheduleLst(dailyInternalScheduleLst);
				nDay.setDailyExternalScheduleLst(dailyExternalScheduleLst);
				nDay.setDailyDayOffLst(dailyDayOffLst);
				nDay.setDailyEducationLst(dailyEducationLst);
				nDay.setCustomerPickerLst(dailyCustomerInfoLst);
				nDay.setHolidayLst(holidayLst);
				nDay.setDailyUpdayLst(dailyUpdayLst);
				nDay.setDailyProjectLstCount(dailyProjectLst.size());
				nDay.setDailyScheduleLst_time(dailyScheduleLst_time);

				// 추가
				/*
				 * nDay.setDailyProjectLst_MMCount(dailyProjectLst_MM.size()); nDay.setDailyProjectLst_PJTCount(dailyProjectLst_PJT.size());
				 * nDay.setDailyInternalScheduleLstCount(dailyInternalScheduleLst.size());
				 * nDay.setDailyExternalScheduleLstCount(dailyExternalScheduleLst.size()); nDay.setDailyDayOffLstCount(dailyDayOffLst.size());
				 * nDay.setDailyEducationLstCount(dailyEducationLst.size()); nDay.setCustomerPickerLstCount(dailyCustomerInfoLst.size());
				 * nDay.setHolidayLstCount(holidayLst.size()); nDay.setDailyUpdayLstCount(dailyUpdayLst.size());
				 */
				nDay.setDailyScheduleLst_timeCount(dailyScheduleLst_time.size());

				arrDay.add(nDay);
			}
			arrWeek.add(arrDay);
		}

		request.setAttribute("intYear", intYear);
		request.setAttribute("intMonth", intMonth + 1);
		request.setAttribute("chkOption", chkOption);
		request.setAttribute("history", history);
		request.setAttribute("ssn", ssn);
		request.setAttribute("cSsn", cSsn);
		request.setAttribute("isManager", isManager);
		request.setAttribute("scheduleUserName", scheduleUserName);
		request.setAttribute("NowDate", Integer.toString(intYear) + "-" + strMonth + "-" + strNowDay);

		request.setAttribute("calendar", arrWeek);
		return mapping.findForward("scheduleMonth_time");
	}

	public ActionForward scheduleByAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		String jobClass = StringUtil.isNull(request.getParameter("jobClass"), "A");

		Calendar c = new GregorianCalendar();
		int intYear = 0;
		int intMonth = 0;
		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			intYear = c.get(Calendar.YEAR);
			intMonth = c.get(Calendar.MONTH);
		} else {
			intYear = Integer.parseInt(selectedYear);
			intMonth = Integer.parseInt(selectedMonth) - 1;
		}

		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			selectedYear = Integer.toString(intYear);
			selectedMonth = "0" + Integer.toString(intMonth + 1);
			selectedMonth = selectedMonth.substring(selectedMonth.length() - 2, selectedMonth.length());
		} else {
			selectedMonth = "0" + selectedMonth;
			selectedMonth = selectedMonth.substring(selectedMonth.length() - 2, selectedMonth.length());
		}

		String tempGroupName = "";
		int groupUserCount = 0;
		List<ScheduleUserInfo> userList = scheduleManager.getScheduleUserList(jobClass, selectedYear, selectedMonth);
		// Job Date: 2012-08-13	Author: yhyim	Description: 공휴일 리스트 생성
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(selectedYear, selectedMonth);
		
		if (!jobClass.equals("A")) {
			ScheduleUserInfo dummy = new ScheduleUserInfo();
			dummy.setGroupName("");
			dummy.setSsn("");
			dummy.setDeptName("");
			dummy.setUserName("");
			userList.add(dummy);
		}

		//한방 쿼리 로직 추가 리스트로 가져온거 ssn키로 맵으로 전환 -20170102
		HashMap<String, ScheduleDailyMasterInfo> dailyMasterInfoHashMap = new HashMap<String, ScheduleDailyMasterInfo>();
		List<ScheduleDailyMasterInfo> dailyMasterInfoList = scheduleManager.getScheduleDailyMasterInfoList(jobClass, selectedYear, selectedMonth);		
		for (ScheduleDailyMasterInfo scheduleDailyMasterInfo : dailyMasterInfoList) {
			dailyMasterInfoHashMap.put(scheduleDailyMasterInfo.getSsn(), scheduleDailyMasterInfo);
		}
		

		List<UserGroupInfo> groupList = new ArrayList<UserGroupInfo>();
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (int i = 0; i < userList.size(); i++) {
			if ((i != 0) && (!userList.get(i).getGroupName().equals(tempGroupName))) {
				UserGroupInfo userGroupInfo = new UserGroupInfo();

				userGroupInfo.setGroupName(userList.get(i - 1).getGroupName());
				userGroupInfo.setUserCount(groupUserCount);
				userGroupInfo.setUserList(userInfoList);

				groupList.add(userGroupInfo);

				userInfoList = new ArrayList<UserInfo>();
				groupUserCount = 0;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setSsn(userList.get(i).getSsn());
			userInfo.setUserName(userList.get(i).getUserName());
			userInfo.setScheduleDailyMasterInfo(dailyMasterInfoHashMap.get(userList.get(i).getSsn()));
			
			userInfoList.add(userInfo);
			groupUserCount++;

			tempGroupName = userList.get(i).getGroupName();
			
			// 제일 마지막 KMAC 부서가 화면에 안 타나는 문제를 해결하기 위해 추가한 임시기능
			if (i == userList.size()-1 && jobClass.equals("A")) {
				UserGroupInfo userGroupInfo = new UserGroupInfo();

				userGroupInfo.setGroupName(userList.get(i).getGroupName());
				userGroupInfo.setUserCount(groupUserCount);
				userGroupInfo.setUserList(userInfoList);

				groupList.add(userGroupInfo);

				userInfoList = new ArrayList<UserInfo>();
				groupUserCount = 0;
			}
			// 여기까지

		}

		c.set(intYear, intMonth, 01);
		int intWeek;
		int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<String> weekDay = new ArrayList<String>();
		int hi = 0;	// 공휴일 List 처리를 위한 Indicator
		for (int i = 0; i < endDay; i++) {
			c.set(intYear, intMonth, i + 1);
			intWeek = c.get(Calendar.DAY_OF_WEEK);
			
			// Job Date: 2012-08-13	Author: yhyim	Description: 공휴일 처리 추가
			while ((hi < holidayList.size()) && (i == Integer.parseInt(holidayList.get(hi).getDay())-1)) {
				intWeek = 8;
				hi++;
			}
			switch (intWeek) {
			case 1:
				weekDay.add("SUN");
				break;
			case 7:
				weekDay.add("SAT");
				break;
			case 8:
				weekDay.add("HOL");
				break;
			default:
				weekDay.add("NOM");
				break;
			}
		}
		request.setAttribute("weekDay", weekDay);
		request.setAttribute("intYear", intYear);
		request.setAttribute("intMonth", String.valueOf(intMonth + 1).length() == 1 ? "0"+String.valueOf(intMonth + 1) : String.valueOf(intMonth + 1));
		request.setAttribute("jobClass", jobClass);
		request.setAttribute("groupList", groupList);

		return mapping.findForward("scheduleByAll");
	}



	public ActionForward scheduleByAll_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		String jobClass = StringUtil.isNull(request.getParameter("jobClass"), "A");

		Calendar c = new GregorianCalendar();
		int intYear = 0;
		int intMonth = 0;
		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			intYear = c.get(Calendar.YEAR);
			intMonth = c.get(Calendar.MONTH);
		} else {
			intYear = Integer.parseInt(selectedYear);
			intMonth = Integer.parseInt(selectedMonth) - 1;
		}

		if (StringUtil.isNull(selectedMonth, "").equals("")) {
			selectedYear = Integer.toString(intYear);
			selectedMonth = "0" + Integer.toString(intMonth + 1);
			selectedMonth = selectedMonth.substring(selectedMonth.length() - 2, selectedMonth.length());
		} else {
			selectedMonth = "0" + selectedMonth;
			selectedMonth = selectedMonth.substring(selectedMonth.length() - 2, selectedMonth.length());
		}

		String tempGroupName = "";
		int groupUserCount = 0;
		List<ScheduleUserInfo> userList = scheduleManager.getScheduleUserList(jobClass, selectedYear, selectedMonth);
		// Job Date: 2012-08-13 Author: yhyim Description: 공휴일 리스트 생성
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(selectedYear, selectedMonth);

		if (!jobClass.equals("A")) {
			ScheduleUserInfo dummy = new ScheduleUserInfo();
			dummy.setGroupName("");
			dummy.setSsn("");
			dummy.setDeptName("");
			dummy.setUserName("");
			userList.add(dummy);
		}

		// 한방 쿼리 로직 추가 리스트로 가져온거 ssn키로 맵으로 전환 -20170102
		HashMap<String, ScheduleDailyMasterInfo> dailyMasterInfoHashMap = new HashMap<String, ScheduleDailyMasterInfo>();
		List<ScheduleDailyMasterInfo> dailyMasterInfoList = scheduleManager.getScheduleDailyMasterInfoList(jobClass, selectedYear, selectedMonth);
		for (ScheduleDailyMasterInfo scheduleDailyMasterInfo : dailyMasterInfoList) {
			dailyMasterInfoHashMap.put(scheduleDailyMasterInfo.getSsn(), scheduleDailyMasterInfo);
		}

		List<UserGroupInfo> groupList = new ArrayList<UserGroupInfo>();
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		for (int i = 0; i < userList.size(); i++) {
			if ((i != 0) && (!userList.get(i).getGroupName().equals(tempGroupName))) {
				UserGroupInfo userGroupInfo = new UserGroupInfo();

				userGroupInfo.setGroupName(userList.get(i - 1).getGroupName());
				userGroupInfo.setUserCount(groupUserCount);
				userGroupInfo.setUserList(userInfoList);

				groupList.add(userGroupInfo);

				userInfoList = new ArrayList<UserInfo>();
				groupUserCount = 0;
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setSsn(userList.get(i).getSsn());
			userInfo.setUserName(userList.get(i).getUserName());
			userInfo.setScheduleDailyMasterInfo(dailyMasterInfoHashMap.get(userList.get(i).getSsn()));

			userInfoList.add(userInfo);
			groupUserCount++;

			tempGroupName = userList.get(i).getGroupName();

			// 제일 마지막 KMAC 부서가 화면에 안 타나는 문제를 해결하기 위해 추가한 임시기능
			if (i == userList.size() - 1 && jobClass.equals("A")) {
				UserGroupInfo userGroupInfo = new UserGroupInfo();

				userGroupInfo.setGroupName(userList.get(i - 1).getGroupName());
				userGroupInfo.setUserCount(groupUserCount);
				userGroupInfo.setUserList(userInfoList);

				groupList.add(userGroupInfo);

				userInfoList = new ArrayList<UserInfo>();
				groupUserCount = 0;
			}
			// 여기까지

		}

		c.set(intYear, intMonth, 01);
		int intWeek;
		int endDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<String> weekDay = new ArrayList<String>();
		int hi = 0; // 공휴일 List 처리를 위한 Indicator
		for (int i = 0; i < endDay; i++) {
			c.set(intYear, intMonth, i + 1);
			intWeek = c.get(Calendar.DAY_OF_WEEK);

			// Job Date: 2012-08-13 Author: yhyim Description: 공휴일 처리 추가
			while ((hi < holidayList.size()) && (i == Integer.parseInt(holidayList.get(hi).getDay()) - 1)) {
				intWeek = 8;
				hi++;
			}
			switch (intWeek) {
			case 1:
				weekDay.add("SUN");
				break;
			case 7:
				weekDay.add("SAT");
				break;
			case 8:
				weekDay.add("HOL");
				break;
			default:
				weekDay.add("NOM");
				break;
			}
		}
		request.setAttribute("weekDay", weekDay);
		request.setAttribute("intYear", intYear);
		request.setAttribute("intMonth",
				String.valueOf(intMonth + 1).length() == 1 ? "0" + String.valueOf(intMonth + 1) : String.valueOf(intMonth + 1));
		request.setAttribute("jobClass", jobClass);
		request.setAttribute("groupList", groupList);

		return mapping.findForward("scheduleByAll_time");
	}

	public ActionForward scheduleForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = request.getParameter("ssn");
		String checkSsn = SessionUtils.getUsername(request);
		boolean cSsn = (ssn.equals(SessionUtils.getUserObjext().getId())) ? true : false;
		String sDate = request.getParameter("sdate");
		String seq = StringUtil.isNull(request.getParameter("seq"), "");
		boolean isManager = (StringUtil.isNull(request.getParameter("isManager"), "").equals("MANAGER")) ? true : false; // 스케줄 관리자 여부 체크

		String saveMode = "INSERT";

		String[] arrDate = sDate.split("-");
		DailyScheduleInfo scheduleInfo = new DailyScheduleInfo();
		if (!"".equals(seq)) {
			scheduleInfo = scheduleManager.getScheduleInfo(ssn, arrDate[0], arrDate[1], arrDate[2], Integer.parseInt(seq));
			saveMode = "UPDATE";
		}
		String secretYN = "N";
		if (cSsn)
			secretYN = "";

		// 스케줄 관리자에게 입력 기능 활성화
		if (isManager)
			cSsn = true;

		List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, arrDate[0], arrDate[1], arrDate[2], "report");
		List<DailyProjectInfo> projectManpowerList = scheduleManager.getProjectScheduleListByMonth(ssn, arrDate[0], arrDate[1], arrDate[2],
				"manpower");
		List<DailyProjectInfo> projectBoardList = scheduleManager.getProjectScheduleListByMonth(ssn, arrDate[0], arrDate[1], arrDate[2], "board");
		List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, arrDate[0], arrDate[1], arrDate[2], secretYN);
		List<CustomerPickerInfo> customerPickerList = scheduleManager.getCustomerPickerListByMonth(ssn, arrDate[0], arrDate[1], arrDate[2], "Y");

		// 스케줄관리 클릭 로그
		DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
		dailyScheduleInfo.setViewSsn(checkSsn);
		dailyScheduleInfo.setScheduleSsn(ssn);
		dailyScheduleInfo.setScheduleDate(sDate);

		InetAddress local;
		local = InetAddress.getLocalHost();
		/* String ip = local.getHostAddress(); */
		String ip = request.getRemoteAddr();
		dailyScheduleInfo.setViewIp(ip);
		System.out.println(ip);
		scheduleManager.createLog(dailyScheduleInfo);

		String scheduleUserName = this.getExpertPoolManager().retrieve(ssn).getName();
		String[] hourList = { "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00", "01",
				"02", "03", "04" };
		String[] minList = { "00", "10", "20", "30", "40", "50" };
		String[] typeList = { "사업관리", "전사행사", "교육참석", "회의일정", "휴가", "개인휴무", "기타" };

		request.setAttribute("projectList", projectList);
		request.setAttribute("projectManpowerList", projectManpowerList);
		request.setAttribute("projectBoardList", projectBoardList);
		request.setAttribute("scheduleList", scheduleList);
		request.setAttribute("customerPickerList", customerPickerList);

		request.setAttribute("scheduleInfo", scheduleInfo);
		request.setAttribute("hourList", hourList);
		request.setAttribute("minList", minList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("cSsn", cSsn);
		request.setAttribute("isManager", isManager);
		request.setAttribute("scheduleUserName", scheduleUserName);
		request.setAttribute("ssn", ssn);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("sdate", sDate);

		return mapping.findForward("scheduleForm");
	}

	public ActionForward scheduleForm_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = request.getParameter("ssn");
		boolean cSsn = (ssn.equals(SessionUtils.getUserObjext().getId())) ? true : false;
		String sDate = request.getParameter("sdate");
		String seq = StringUtil.isNull(request.getParameter("seq"), "");
		boolean isManager = (StringUtil.isNull(request.getParameter("isManager"), "").equals("MANAGER")) ? true : false; // 스케줄 관리자 여부 체크

		String saveMode = "INSERT";

		String[] arrDate = sDate.split("-");
		DailyScheduleInfo scheduleInfo = new DailyScheduleInfo();
		if (!"".equals(seq)) {
			scheduleInfo = scheduleManager.getScheduleInfo_time(ssn, arrDate[0], arrDate[1], arrDate[2], Integer.parseInt(seq));
			saveMode = "UPDATE";
		}
		String secretYN = "N";
		if (cSsn)
			secretYN = "";

		// 스케줄 관리자에게 입력 기능 활성화
		if (isManager)
			cSsn = true;

		List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth_time(ssn, arrDate[0], arrDate[1], arrDate[2]);

		String scheduleUserName = this.getExpertPoolManager().retrieve(ssn).getName();
		String[] hourList = { "07", "08", "09", "10" };
		String[] hourList_end = { "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
		String[] minList = { "00", "30" };
		String[] typeList = { "사업관리", "전사행사", "교육참석", "회의일정", "휴가", "개인휴무", "기타" };

		request.setAttribute("scheduleList", scheduleList);
		request.setAttribute("scheduleInfo", scheduleInfo);
		request.setAttribute("hourList", hourList);
		request.setAttribute("hourList_end", hourList_end);
		request.setAttribute("minList", minList);
		request.setAttribute("typeList", typeList);
		request.setAttribute("cSsn", cSsn);
		request.setAttribute("isManager", isManager);
		request.setAttribute("scheduleUserName", scheduleUserName);
		request.setAttribute("ssn", ssn);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("sdate", sDate);

		return mapping.findForward("scheduleForm_time");
	}

	public ActionForward mySchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = SessionUtils.getUsername(request);
		String secretYN = "N";
		Calendar c = new GregorianCalendar();

		String nYear = Integer.toString(c.get(Calendar.YEAR));
		String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
		nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
		String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		nDay = nDay.substring(nDay.length() - 2, nDay.length());
		// logger.debug("날짜 : " + nYear + "." + nMonth + "." + nDay);
		// logger.debug("ssn : " + ssn);
		List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay);
		List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, secretYN);
		// logger.debug("갯수 : " + Integer.toString(scheduleList.size()));
		request.setAttribute("ssn", ssn);
		request.setAttribute("sdate", nYear + "-" + nMonth + "-" + nDay);
		request.setAttribute("prjList", projectList);
		request.setAttribute("schList", scheduleList);

		return mapping.findForward("myScheduleByMain");
	}

	public void countMySchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String ssn = SessionUtils.getUsername(request);
			String secretYN = "N";
			Calendar c = new GregorianCalendar();

			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
			nDay = nDay.substring(nDay.length() - 2, nDay.length());
			int projectCnt = scheduleManager.getProjectScheduleListByMonth(ssn, nYear, nMonth, nDay).size();
			int scheduleCnt = scheduleManager.getScheduleListByMonth(ssn, nYear, nMonth, nDay, secretYN).size();
			// logger.debug("갯수 : " + Integer.toString(scheduleList.size()));
			map.put("scheduleCount", String.valueOf(projectCnt + scheduleCnt));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}

	}

	public ActionForward companySchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Calendar c = new GregorianCalendar();

		String nYear = Integer.toString(c.get(Calendar.YEAR));
		String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
		nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
		String nDay = "0" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		nDay = nDay.substring(nDay.length() - 2, nDay.length());
		List<CompanyScheduleInfoData> scheduleList = this.getCompanyScheduleManager().scheduleListOfMonth(nYear, nMonth, nDay);

		request.setAttribute("sdate", nYear + "-" + nMonth + "-" + nDay);
		request.setAttribute("schList", scheduleList);

		return mapping.findForward("companyScheduleByMain");
	}

	public void saveSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
			String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");

			String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
			String eDate = ServletRequestUtils.getStringParameter(request, "edate", "");
			String oDate = ServletRequestUtils.getStringParameter(request, "odate", "");
			String[] stDate = sDate.split("-");
			String[] enDate = eDate.split("-");
			String[] orDate = oDate.split("-");

			String multiYN = ServletRequestUtils.getStringParameter(request, "multiYN", "N");
			String inWeekYN = ServletRequestUtils.getStringParameter(request, "inWeekYN", "N");
			String secretYN = ServletRequestUtils.getStringParameter(request, "secretYN", "N");

			String type = request.getParameter("type");
			String content = request.getParameter("content");
			String customerName = request.getParameter("customerName");
			String relationUser = request.getParameter("relationUser_val");
			String place = request.getParameter("place");
			String startHour = request.getParameter("startHour");
			String startMin = request.getParameter("startMin");
			String endHour = request.getParameter("endHour");
			String endMin = request.getParameter("endMin");

			String workType = request.getParameter("workType");

			String googleSyncId = request.getParameter("googleSyncId");

			if (saveMode.equals("UPDATE")) {
				scheduleManager.remove(orDate[0], orDate[1], orDate[2], ssn, Integer.parseInt(seq));

				DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
				dailyScheduleInfo.setSsn(ssn);
				dailyScheduleInfo.setYear(stDate[0]);
				dailyScheduleInfo.setMonth(stDate[1]);
				dailyScheduleInfo.setDay(stDate[2]);

				dailyScheduleInfo.setType(type);
				dailyScheduleInfo.setSecretYN(secretYN);
				dailyScheduleInfo.setContent(content);
				dailyScheduleInfo.setCustomerName(customerName);
				dailyScheduleInfo.setRelationUser(relationUser);
				dailyScheduleInfo.setPlace(place);
				dailyScheduleInfo.setStartHour(startHour);
				dailyScheduleInfo.setStartMin(startMin);
				dailyScheduleInfo.setEndHour(endHour);
				dailyScheduleInfo.setEndMin(endMin);
				if (googleSyncId != null && !googleSyncId.equals("")) {
					dailyScheduleInfo.setGoogleSyncId(googleSyncId);
				}
				dailyScheduleInfo.setWorkType(workType);

				scheduleManager.create(dailyScheduleInfo);

			} else if (saveMode.equals("INSERT")) {
				DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
				dailyScheduleInfo.setSsn(ssn);
				dailyScheduleInfo.setYear(stDate[0]);
				dailyScheduleInfo.setMonth(stDate[1]);
				dailyScheduleInfo.setDay(stDate[2]);

				dailyScheduleInfo.setType(type);
				dailyScheduleInfo.setSecretYN(secretYN);
				dailyScheduleInfo.setContent(content);
				dailyScheduleInfo.setCustomerName(customerName);
				dailyScheduleInfo.setRelationUser(relationUser);
				dailyScheduleInfo.setPlace(place);
				dailyScheduleInfo.setStartHour(startHour);
				dailyScheduleInfo.setStartMin(startMin);
				dailyScheduleInfo.setEndHour(endHour);
				dailyScheduleInfo.setEndMin(endMin);
				if (googleSyncId != null && !googleSyncId.equals("")) {
					dailyScheduleInfo.setGoogleSyncId(googleSyncId);
				}
				dailyScheduleInfo.setWorkType(workType);

				scheduleManager.create(dailyScheduleInfo);

				if ((multiYN.equals("Y")) && (!(eDate.equals("")))) {
					long lngi = DateUtil.getDifferDays(stDate[0] + stDate[1] + stDate[2], enDate[0] + enDate[1] + enDate[2]);
					int ei = (int) lngi;
					if (ei > 0) {
						int i = 0;
						String tmpDate = stDate[0] + stDate[1] + stDate[2];
						while (i < ei) {

							tmpDate = DateUtil.getOpDate(java.util.Calendar.DATE, 1, tmpDate);
							logger.debug("날짜 : " + tmpDate);
							boolean weekYN = false;
							boolean holidayYN = false;
							Calendar cal = DateUtil.getCalendarInstance(tmpDate);
							int weekIdx = cal.get(Calendar.DAY_OF_WEEK);
							logger.debug("날짜 : " + Integer.toString(cal.get(Calendar.DATE)));
							// logger.debug("요일 : " + Integer.toString(weekIdx));
							if ((weekIdx == 7) || (weekIdx == 1))
								weekYN = true;
							if (tmpDate.equals("20190204") || tmpDate.equals("20190205") || tmpDate.equals("20190206") || tmpDate.equals("20190301")
									|| tmpDate.equals("20190505") || tmpDate.equals("20190506") || tmpDate.equals("20190606")
									|| tmpDate.equals("20190815") || tmpDate.equals("20191003") || tmpDate.equals("20191009")
									|| tmpDate.equals("20191225") || tmpDate.equals("20190912") || tmpDate.equals("20190913")
									|| tmpDate.equals("20190914")) {
								holidayYN = true;
							}
							if (scheduleManager.isHoliday(tmpDate)) {
								holidayYN = true;
							}
							if (!((inWeekYN.equals("Y")) && ((weekYN) || holidayYN))) {
								String strYear = Integer.toString(cal.get(Calendar.YEAR));
								String strMonth = "0" + Integer.toString(cal.get(Calendar.MONTH) + 1);
								strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
								String strDay = "0" + Integer.toString(cal.get(Calendar.DATE));
								strDay = strDay.substring(strDay.length() - 2, strDay.length());

								dailyScheduleInfo.setYear(strYear);
								dailyScheduleInfo.setMonth(strMonth);
								dailyScheduleInfo.setDay(strDay);

								scheduleManager.create(dailyScheduleInfo);
							}
							i++;
						}
					}

				}
			}

			map.put("result", true);
			map.put("resultMsg", "저장성공!");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "저장실패!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void removeSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
			String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");

			String[] stDate = sDate.split("-");

			scheduleManager.remove(stDate[0], stDate[1], stDate[2], ssn, Integer.parseInt(seq));

			// JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "삭제성공!");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "삭제실패!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void saveSchedule_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
			String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");

			String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
			String eDate = ServletRequestUtils.getStringParameter(request, "edate", "");
			String oDate = ServletRequestUtils.getStringParameter(request, "odate", "");
			String[] stDate = sDate.split("-");
			String[] enDate = eDate.split("-");
			String[] orDate = oDate.split("-");

			String multiYN = ServletRequestUtils.getStringParameter(request, "multiYN", "N");
			String inWeekYN = ServletRequestUtils.getStringParameter(request, "inWeekYN", "N");

			String startHour = request.getParameter("startHour");
			String startMin = request.getParameter("startMin");
			String endHour = request.getParameter("endHour");
			String endMin = request.getParameter("endMin");

			if (saveMode.equals("UPDATE")) {
				scheduleManager.remove_time(orDate[0], orDate[1], orDate[2], ssn, Integer.parseInt(seq));

				DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
				dailyScheduleInfo.setSsn(ssn);
				dailyScheduleInfo.setYear(stDate[0]);
				dailyScheduleInfo.setMonth(stDate[1]);
				dailyScheduleInfo.setDay(stDate[2]);

				dailyScheduleInfo.setStartHour(startHour);
				dailyScheduleInfo.setStartMin(startMin);
				dailyScheduleInfo.setEndHour(endHour);
				dailyScheduleInfo.setEndMin(endMin);

				scheduleManager.create_time(dailyScheduleInfo);

				if ((multiYN.equals("Y")) && (!(eDate.equals("")))) {
					long lngi = DateUtil.getDifferDays(stDate[0] + stDate[1] + stDate[2], enDate[0] + enDate[1] + enDate[2]);
					int ei = (int) lngi;
					if (ei > 0) {
						int i = 0;
						String tmpDate = stDate[0] + stDate[1] + stDate[2];
						while (i < ei) {

							tmpDate = DateUtil.getOpDate(java.util.Calendar.DATE, 1, tmpDate);
							logger.debug("날짜 : " + tmpDate);
							boolean weekYN = false;
							boolean holidayYN = false;
							Calendar cal = DateUtil.getCalendarInstance(tmpDate);
							int weekIdx = cal.get(Calendar.DAY_OF_WEEK);
							logger.debug("날짜 : " + Integer.toString(cal.get(Calendar.DATE)));
							// logger.debug("요일 : " + Integer.toString(weekIdx));
							if ((weekIdx == 7) || (weekIdx == 1))
								weekYN = true;
							if (scheduleManager.isHoliday(tmpDate)) {
								holidayYN = true;
							}
							if (!((inWeekYN.equals("Y")) && ((weekYN) || holidayYN))) {
								String strYear = Integer.toString(cal.get(Calendar.YEAR));
								String strMonth = "0" + Integer.toString(cal.get(Calendar.MONTH) + 1);
								strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
								String strDay = "0" + Integer.toString(cal.get(Calendar.DATE));
								strDay = strDay.substring(strDay.length() - 2, strDay.length());

								scheduleManager.remove_time(strYear, strMonth, strDay, ssn, Integer.parseInt(seq));

								dailyScheduleInfo.setSsn(ssn);
								dailyScheduleInfo.setYear(strYear);
								dailyScheduleInfo.setMonth(strMonth);
								dailyScheduleInfo.setDay(strDay);

								dailyScheduleInfo.setStartHour(startHour);
								dailyScheduleInfo.setStartMin(startMin);
								dailyScheduleInfo.setEndHour(endHour);
								dailyScheduleInfo.setEndMin(endMin);

								scheduleManager.create_time(dailyScheduleInfo);
							}
							i++;
						}
					}

				}

			} else if (saveMode.equals("INSERT")) {
				DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
				dailyScheduleInfo.setSsn(ssn);
				dailyScheduleInfo.setYear(stDate[0]);
				dailyScheduleInfo.setMonth(stDate[1]);
				dailyScheduleInfo.setDay(stDate[2]);

				dailyScheduleInfo.setStartHour(startHour);
				dailyScheduleInfo.setStartMin(startMin);
				dailyScheduleInfo.setEndHour(endHour);
				dailyScheduleInfo.setEndMin(endMin);

				scheduleManager.create_time2(dailyScheduleInfo);

				if ((multiYN.equals("Y")) && (!(eDate.equals("")))) {
					long lngi = DateUtil.getDifferDays(stDate[0] + stDate[1] + stDate[2], enDate[0] + enDate[1] + enDate[2]);
					int ei = (int) lngi;
					if (ei > 0) {
						int i = 0;
						String tmpDate = stDate[0] + stDate[1] + stDate[2];
						while (i < ei) {

							tmpDate = DateUtil.getOpDate(java.util.Calendar.DATE, 1, tmpDate);
							logger.debug("날짜 : " + tmpDate);
							boolean weekYN = false;
							boolean holidayYN = false;
							Calendar cal = DateUtil.getCalendarInstance(tmpDate);
							int weekIdx = cal.get(Calendar.DAY_OF_WEEK);
							logger.debug("날짜 : " + Integer.toString(cal.get(Calendar.DATE)));
							// logger.debug("요일 : " + Integer.toString(weekIdx));
							if ((weekIdx == 7) || (weekIdx == 1))
								weekYN = true;
							if (scheduleManager.isHoliday(tmpDate)) {
								holidayYN = true;
							}
							if (!((inWeekYN.equals("Y")) && ((weekYN) || holidayYN))) {
								String strYear = Integer.toString(cal.get(Calendar.YEAR));
								String strMonth = "0" + Integer.toString(cal.get(Calendar.MONTH) + 1);
								strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
								String strDay = "0" + Integer.toString(cal.get(Calendar.DATE));
								strDay = strDay.substring(strDay.length() - 2, strDay.length());

								dailyScheduleInfo.setYear(strYear);
								dailyScheduleInfo.setMonth(strMonth);
								dailyScheduleInfo.setDay(strDay);

								scheduleManager.create_time2(dailyScheduleInfo);
							}
							i++;
						}
					}

				}
			}

			map.put("result", true);
			map.put("resultMsg", "저장성공!");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "저장실패!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void removeSchedule_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
			String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");

			String[] stDate = sDate.split("-");

			scheduleManager.remove_time(stDate[0], stDate[1], stDate[2], ssn, Integer.parseInt(seq));

			// JSONWriter writer = new JSONWriter();
			map.put("result", true);
			map.put("resultMsg", "삭제성공!");

			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			map.put("result", false);
			map.put("resultMsg", "삭제실패!");
			map.put("ErrMsg", e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public ActionForward scheduleManagerSelectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 15);
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String sanctionYN = ServletRequestUtils.getStringParameter(request, "sanctionYN", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("scheduleManagerSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			// 검색 기간 설정: 검색 월 1일 부터 검색 다음월 1일까지
			String from = year + "-" + month + "-01";
			cal.setTime(Date.valueOf(from));
			cal.add(Calendar.MONTH, 1);
			String to = df.format(cal.getTime());

			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("year", year);
			filters.put("month", month);
			filters.put("from", from);
			filters.put("to", to);
			if (sanctionYN.equals("Y"))
				filters.put("sanctionY", "sanctionY");
			if (sanctionYN.equals("N"))
				filters.put("sanctionN", "sanctionN");
			if (!name.equals(""))
				filters.put("name", "%" + name + "%");
			filters.put("jobClass", jobClass);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("scheduleManagerSelectList", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("sanctionYN", sanctionYN);
		request.setAttribute("name", name);
		request.setAttribute("jobClass", jobClass);
		return mapping.findForward("scheduleManagerSelectList");

	}

	public ActionForward scheduleManagerSelectList_time(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", 15);
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String sanctionYN = ServletRequestUtils.getStringParameter(request, "sanctionYN", "");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "A");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("scheduleManagerSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			// 검색 기간 설정: 검색 월 1일 부터 검색 다음월 1일까지
			String from = year + "-" + month + "-01";
			cal.setTime(Date.valueOf(from));
			cal.add(Calendar.MONTH, 1);
			String to = df.format(cal.getTime());

			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("year", year);
			filters.put("month", month);
			filters.put("from", from);
			filters.put("to", to);
			if (sanctionYN.equals("Y"))
				filters.put("sanctionY", "sanctionY");
			if (sanctionYN.equals("N"))
				filters.put("sanctionN", "sanctionN");
			if (!name.equals(""))
				filters.put("name", "%" + name + "%");
			filters.put("jobClass", jobClass);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("scheduleManagerSelectList", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("sanctionYN", sanctionYN);
		request.setAttribute("name", name);
		request.setAttribute("jobClass", jobClass);
		return mapping.findForward("scheduleManagerSelectList_time");

	}

	public void countMySchedule_time(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();

		try {
			String ssn = SessionUtils.getUsername(request);
			Calendar c = new GregorianCalendar();
			String nYear = Integer.toString(c.get(Calendar.YEAR));

			// 12월, 1월 체크 & 월 변경
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 2);
			if (nMonth.equals("013")) {
				nMonth = "01";
			}else if(nMonth.length() == 3){
				nMonth = nMonth.substring(1, 3);
			}

			int scheduleCnt = 0;

			// CCO, CBO, COO, 구분
			/*
			 * if(ssn.equals("A000006")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cco(nYear, nMonth, ssn).size(); }else if(ssn.equals("A000006") ||
			 * ssn.equals("A000008") || ssn.equals("A000011") || ssn.equals("A000013") || ssn.equals("A000013") || ssn.equals("A000020") ||
			 * ssn.equals("A000021")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cbo(nYear, nMonth, ssn).size(); }else{
			 */
	
			scheduleCnt = scheduleManager.getScheduleCnt_time(nYear, nMonth, ssn).size();

			map.put("scheduleCount", String.valueOf(scheduleCnt));
			map.put("year", nYear);
			map.put("month", nMonth);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void countMySchedule_customer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();

		try {
			String ssn = SessionUtils.getUsername(request);
			Calendar c = new GregorianCalendar();
			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String day = DateTime.getDateString();

			// 12월, 1월 체크
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 2);
			if (nMonth.equals("013")) {
				nMonth = "01";
			}
			// nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			// nMonth 살리고 12월 지우기 (테스트 12월)
			// nMonth = "04";
			// System.out.println("ssn : "+ ssn);
			// System.out.println("nYear : "+ nYear);
			// System.out.println("nMonth : "+ nMonth);

			int scheduleCnt = 0;

			// CCO, CBO, COO, 구분
			/*
			 * if(ssn.equals("A000006")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cco(nYear, nMonth, ssn).size(); }else if(ssn.equals("A000006") ||
			 * ssn.equals("A000008") || ssn.equals("A000011") || ssn.equals("A000013") || ssn.equals("A000013") || ssn.equals("A000020") ||
			 * ssn.equals("A000021")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cbo(nYear, nMonth, ssn).size(); }else{
			 */
			System.out.println(ssn);
			System.out.println(day);
			scheduleCnt = scheduleManager.getScheduleCnt_customer(day, ssn).size();
			/* } */
			// logger.debug("갯수 : " + Integer.toString(scheduleList.size()));
			System.out.println(scheduleCnt);
			map.put("scheduleCount", String.valueOf(scheduleCnt));
			map.put("year", nYear);
			map.put("month", nMonth);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void countMySchedule_customer2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();

		try {
			String ssn = SessionUtils.getUsername(request);
			Calendar c = new GregorianCalendar();
			String nYear = Integer.toString(c.get(Calendar.YEAR));
			String day = DateTime.getDateString();

			// 12월, 1월 체크
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 2);
			if (nMonth.equals("013")) {
				nMonth = "01";
			}
			// nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
			// nMonth 살리고 12월 지우기 (테스트 12월)
			// nMonth = "04";
			// System.out.println("ssn : "+ ssn);
			// System.out.println("nYear : "+ nYear);
			// System.out.println("nMonth : "+ nMonth);

			int scheduleCnt = 0;

			// CCO, CBO, COO, 구분
			/*
			 * if(ssn.equals("A000006")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cco(nYear, nMonth, ssn).size(); }else if(ssn.equals("A000006") ||
			 * ssn.equals("A000008") || ssn.equals("A000011") || ssn.equals("A000013") || ssn.equals("A000013") || ssn.equals("A000020") ||
			 * ssn.equals("A000021")){ scheduleCnt = scheduleManager.getScheduleCnt_time_cbo(nYear, nMonth, ssn).size(); }else{
			 */
			System.out.println(ssn);
			System.out.println(day);
			scheduleCnt = scheduleManager.getScheduleCnt_customer2(day, ssn).size();
			/* } */
			// logger.debug("갯수 : " + Integer.toString(scheduleList.size()));
			System.out.println(scheduleCnt);
			map.put("scheduleCount", String.valueOf(scheduleCnt));
			map.put("year", nYear);
			map.put("month", nMonth);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void countConSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			String ssn = SessionUtils.getUsername(request);
			Calendar c = new GregorianCalendar();
			String nYear = Integer.toString(c.get(Calendar.YEAR));
		
			// 12월, 1월 체크
			String nMonth = "0" + Integer.toString(c.get(Calendar.MONTH) + 1);
			if(nMonth.equals("013")){
				nMonth = "01";
			}
			
			int scheduleCnt = 0;
			if(expertPool.getJobClass().equals("J"))
				scheduleCnt = scheduleManager.getConScheduleCnt(nYear, nMonth, ssn).size();
			else
				scheduleCnt = 999;
			
			map.put("scheduleCount", String.valueOf(scheduleCnt));
			map.put("year", nYear);
			map.put("month", nMonth);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
}
