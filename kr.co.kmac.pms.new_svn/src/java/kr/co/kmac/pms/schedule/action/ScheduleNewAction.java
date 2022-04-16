/*
 * $Id: ScheduleAction.java,v 1.27 2019/03/19 03:43:27 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.data.CustomerPickerInfo;
import kr.co.kmac.pms.schedule.data.DailyProjectInfo;
import kr.co.kmac.pms.schedule.data.DailyScheduleInfo;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.data.ScheduleDay;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

// jobDate: 2016-06-17	Author: yhyim

/**
 * @struts.action name="ScheduleNewAction" path="/action/ScheduleNewAction" parameter="mode" scope="request"
 * @struts.action-forward name="scheduleMonth_new" path="/schedule/scheduleMonth_new.jsp" redirect="false"
 * @struts.action-forward name="scheduleMonth_time_new" path="/schedule/scheduleMonth_time_new.jsp" redirect="false"
 */
public class ScheduleNewAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ScheduleNewAction.class);
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

	
	public ActionForward scheduleOfMonth2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		List<DailyScheduleInfo> internalScheduleList = scheduleManager.getInternalScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "",
				secretYN); // 내부일정
		List<DailyScheduleInfo> externalScheduleList = scheduleManager.getExternalScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "",
				secretYN); // 외부일정
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
				nDay.setDayOfWeek(j);
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
		return mapping.findForward("scheduleMonth_new");
	}
	
	public ActionForward scheduleOfMonth_time2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		return mapping.findForward("scheduleMonth_time_new");
	}
	
	
}
