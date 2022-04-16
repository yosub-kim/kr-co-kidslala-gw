/*
 * $Id: ScheduleMobileAction.java,v 1.8 2016/07/22 01:55:31 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.schedule.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
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

/**
 * @struts.action name="scheduleMobileAction" path="/action/ScheduleMobileAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="scheduleMonth"		 path="/m/web/schedule/scheduleMonth.jsp" redirect="false"
 * @struts.action-forward name="scheduleProjectView" path="/m/web/schedule/scheduleProjectView.jsp" redirect="false"
 * @struts.action-forward name="scheduleDateView"	 path="/m/web/schedule/scheduleDateView.jsp" redirect="false"
 * 
 * @struts.action-forward name="scheduleByAll" path="/schedule/scheduleByAll.jsp" redirect="false"
 * @struts.action-forward name="scheduleList" path="/schedule/jsp/scheduleList.jsp" redirect="false"
 * @struts.action-forward name="myScheduleByMain" path="/schedule/main_mySchedule.jsp" redirect="false"
 * @struts.action-forward name="companyScheduleByMain" path="/schedule/main_companySchedule.jsp" redirect="false"
 * @struts.action-forward name="result" path="/common/jsp/result.jsp" redirect="false"
 */
public class ScheduleMobileAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ScheduleMobileAction.class);
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

	// --------------------------------------------------------------------------

	public ActionForward scheduleOfMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		String history = ServletRequestUtils.getStringParameter(request, "history", "false");
		String chkOption = ServletRequestUtils.getStringParameter(request, "chkOption", "Y");
		String ssn = SessionUtils.getUsername(request);
		String step = ServletRequestUtils.getStringParameter(request, "step", "");
		if (!step.equals("")) {
			int selYear = Integer.parseInt(selectedYear);
			int selMonth = Integer.parseInt(selectedMonth);
			if (step.equals("prev")) {
				if (selMonth == 1) {
					selectedYear = String.valueOf(selYear - 1);
					selectedMonth = "12";
				} else {
					selectedMonth = String.valueOf(selMonth - 1);
				}
			} else if (step.equals("next")) {
				if (selMonth == 12) {
					selectedYear = String.valueOf(selYear + 1);
					selectedMonth = "1";
				} else {
					selectedMonth = String.valueOf(selMonth + 1);
				}

			}
		}

		logger.debug("ssn : " + SessionUtils.getUsername(request));

		String scheduleUserName = this.getExpertPoolManager().retrieve(ssn).getName();

		boolean cSsn = (ssn.equals(SessionUtils.getUserObjext().getId())) ? true : false;
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

		List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "");
		List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, Integer.toString(intYear), strMonth, "", secretYN);
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(Integer.toString(intYear), strMonth);

		int pi = 0;
		int si = 0;
		int hi = 0;
		List<List<ScheduleDay>> arrWeek = new ArrayList<List<ScheduleDay>>();
		while (iDay <= endDay) {
			List<ScheduleDay> arrDay = new ArrayList<ScheduleDay>();
			for (int j = 0; j < 7; j++) {
				String dayId = "";
				String day = "";
				String date = "";
				List<DailyProjectInfo> dailyProjectLst = new ArrayList<DailyProjectInfo>();
				List<DailyScheduleInfo> dailyScheduleLst = new ArrayList<DailyScheduleInfo>();
				List<HolidayInfo> holidayLst = new ArrayList<HolidayInfo>();

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
					while ((si < scheduleList.size()) && (iDay == Integer.parseInt(scheduleList.get(si).getDay()))) {
						DailyScheduleInfo dailyScheduleInfo = new DailyScheduleInfo();
						dailyScheduleInfo = scheduleList.get(si);

						dailyScheduleLst.add(dailyScheduleInfo);

						si++;
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
				nDay.setDailyScheduleLst(dailyScheduleLst);
				nDay.setHolidayLst(holidayLst);
				nDay.setDailyProjectLstCount(dailyProjectLst.size());
				nDay.setDailyScheduleLstCount(dailyScheduleLst.size());
				nDay.setHolidayLstCount(holidayLst.size());

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
		request.setAttribute("scheduleUserName", scheduleUserName);
		request.setAttribute("NowDate", DateUtil.getDateTimeByPattern("yyyy-MM-dd"));

		request.setAttribute("calendar", arrWeek);
		return mapping.findForward("scheduleMonth");
	}

	public void selectedScheduleList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String ssn = SessionUtils.getUsername(request);
			String sDate = request.getParameter("sdate");

			String[] arrDate = sDate.split("-");

			List<DailyProjectInfo> projectList = scheduleManager.getProjectScheduleListByMonth(ssn, arrDate[0], 
					(arrDate[1].length() == 1 ? "0" + arrDate[1] : arrDate[1]),
					(arrDate[2].length() == 1 ? "0" + arrDate[2] : arrDate[2]));
			List<DailyScheduleInfo> scheduleList = scheduleManager.getScheduleListByMonth(ssn, arrDate[0], 
					(arrDate[1].length() == 1 ? "0" + arrDate[1] : arrDate[1]),
					(arrDate[2].length() == 1 ? "0" + arrDate[2] : arrDate[2]), "");

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("projectList", projectList);
			map.put("scheduleList", scheduleList);
			map.put("ssn", ssn);
			map.put("sdate", sDate);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
		}

	}

	public void saveSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
			String ssn = SessionUtils.getUsername(request);
			String saveMode = ServletRequestUtils.getRequiredStringParameter(request, "saveMode");

			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sDate");
			String[] stDate = sDate.split("-");
			stDate[1] = stDate[1].length() == 1 ? "0" + stDate[1] : stDate[1];
			stDate[2] = stDate[2].length() == 1 ? "0"+stDate[2] : stDate[2];
			
			String eDate = ServletRequestUtils.getStringParameter(request, "eDate", "");
			String[] enDate = eDate.split("-");
			enDate[1] = enDate[1].length() == 1 ? "0" + enDate[1] : enDate[1];
			enDate[2] = enDate[2].length() == 1 ? "0"+enDate[2] : enDate[2];
			// String[] orDate = null;//oDate.split("-");

			String sTime = ServletRequestUtils.getRequiredStringParameter(request, "sTime");
			String startHour = sTime.split(":")[0];
			String startMin = sTime.split(":")[1];
			String eTime = ServletRequestUtils.getStringParameter(request, "eTime", "");
			String endHour = eTime.split(":")[0];
			String endMin = eTime.split(":")[1];

			String inWeekYN = ServletRequestUtils.getStringParameter(request, "inWeekYN", "N");
			String secretYN = ServletRequestUtils.getStringParameter(request, "secretYN", "N");

			String type = request.getParameter("type");
			String content = request.getParameter("content");
			String customerName = request.getParameter("customerName");
			String relationUser = request.getParameter("relationUser");
			String place = request.getParameter("place");
			String workType = request.getParameter("workType");

			String googleSyncId = request.getParameter("googleSyncId");

			if (saveMode.equals("UPDATE")) {
				//scheduleManager.remove(orDate[0], orDate[1], orDate[2], ssn, Integer.parseInt(seq));
				scheduleManager.remove(stDate[0], stDate[1], stDate[2], ssn, Integer.parseInt(seq));

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
				if (!type.equals("휴가") && !type.equals("교육참석")) {
					dailyScheduleInfo.setWorkType(workType);
				}
				
				if(googleSyncId != null && !googleSyncId.equals("")){
					dailyScheduleInfo.setGoogleSyncId(googleSyncId);
				}

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
				if (!type.equals("휴가") && !type.equals("교육참석"))
					dailyScheduleInfo.setWorkType(workType);
								
				if(googleSyncId != null && !googleSyncId.equals("")){
					dailyScheduleInfo.setGoogleSyncId(googleSyncId);
				}

				scheduleManager.create(dailyScheduleInfo);

				if (!(eDate.equals(""))) {
					long lngi = DateUtil.getDifferDays(stDate[0] + stDate[1] + stDate[2], enDate[0] + enDate[1] + enDate[2]);
					int ei = (int) lngi;
					if (ei > 0) {
						int i = 0;
						String tmpDate = stDate[0] + stDate[1] + stDate[2];
						while (i < ei) {

							tmpDate = DateUtil.getOpDate(java.util.Calendar.DATE, 1, tmpDate);
							logger.debug("날짜 : " + tmpDate);
							boolean weekYN = false;
							Calendar cal = DateUtil.getCalendarInstance(tmpDate);
							int weekIdx = cal.get(Calendar.DAY_OF_WEEK);
							logger.debug("날짜 : " + Integer.toString(cal.get(Calendar.DATE)));
							// logger.debug("요일 : " + Integer.toString(weekIdx));
							if ((weekIdx == 7) || (weekIdx == 1))
								weekYN = true;
							if (!((inWeekYN.equals("Y")) && (weekYN))) {
								String strYear = Integer.toString(cal.get(Calendar.YEAR));
								String strMonth = "0" + Integer.toString(cal.get(Calendar.MONTH) + 1);
								strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
								String strDay = "0" + Integer.toString(cal.get(Calendar.DATE));
								strDay = strDay.substring(strDay.length() - 2, strDay.length());

								dailyScheduleInfo.setYear(strYear);
								dailyScheduleInfo.setMonth(strMonth);
								dailyScheduleInfo.setDay(strDay);

								scheduleManager.create(dailyScheduleInfo);
								i++;
							}
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

	public ActionForward selectedProjectSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = SessionUtils.getUsername(request);
		String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		String[] arrDate = sDate.split("-");
		DailyProjectInfo dailyProjectInfo = scheduleManager.getProjectSchedule(projectCode, ssn, arrDate[0], arrDate[1], arrDate[2]);
		request.setAttribute("scheduleInfo", dailyProjectInfo);
		request.setAttribute("ssn", ssn);
		request.setAttribute("sdate", sDate); 
		request.setAttribute("projectCode", projectCode);

		return mapping.findForward("scheduleProjectView");
	}

	public ActionForward selectedDateSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ssn = SessionUtils.getUsername(request);
		String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
		String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");
		
		String[] arrDate = sDate.split("-");
		DailyScheduleInfo scheduleInfo = scheduleManager.getScheduleInfo(ssn, arrDate[0], arrDate[1], arrDate[2], Integer.parseInt(seq));
		
		request.setAttribute("scheduleInfo", scheduleInfo);
		request.setAttribute("ssn", ssn);
		request.setAttribute("sdate", sDate);
		
		return mapping.findForward("scheduleDateView");
	}

	public void removeSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
			String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");

			String[] stDate = sDate.split("-");

			scheduleManager.remove(stDate[0], stDate[1], stDate[2], ssn, Integer.parseInt(seq));

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

}
