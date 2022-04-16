/*
 * $Id: CompanyScheduleAction.java,v 1.3 2012/06/28 16:34:05 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 6. 2.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.companySchedule.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.companySchedule.data.CompanyScheduleInfoData;
import kr.co.kmac.pms.companySchedule.data.ScheduleDay;
import kr.co.kmac.pms.companySchedule.manager.CompanyScheduleManager;
import kr.co.kmac.pms.schedule.data.HolidayInfo;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="responseForm" path="/action/CompanyScheduleAction" parameter="mode" scope="request"
 * @struts.action-forward name="ComScheduleOfMonth"	path="/schedule/companyScheduleOfMonth.jsp"		redirect="false"
 * @struts.action-forward name="ComScheduleForm"		path="/schedule/companyScheduleForm.jsp"		redirect="false"
 * @struts.action-forward name="result" path="/common/jsp/result.jsp" redirect="false"
 */
public class CompanyScheduleAction extends DispatchActionSupport {
	
	private static final Log logger = LogFactory.getLog(CompanyScheduleAction.class);

	private CompanyScheduleManager companyScheduleManager;
	private ScheduleManager scheduleManager;
	
	public CompanyScheduleManager getCompanyScheduleManager() {
		return companyScheduleManager;
	}
	public void setCompanyScheduleManager(CompanyScheduleManager companyScheduleManager) {
		this.companyScheduleManager = companyScheduleManager;
	}
	
	public ScheduleManager getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}
	
	
	/**
	 * 스케쥴 저장 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String saveMode	= ServletRequestUtils.getRequiredStringParameter(request, "saveMode");
			String sDate	= ServletRequestUtils.getRequiredStringParameter(request, "sdate");

			String eDate	= ServletRequestUtils.getStringParameter(request, "edate", "");
			String oDate	= ServletRequestUtils.getStringParameter(request, "odate",	"");
			String[] stDate = sDate.split("-");
			String[] enDate = eDate.split("-");
			String[] orDate = oDate.split("-");

			String multiYN	= ServletRequestUtils.getStringParameter(request, "multiYN" , "N");
			String inWeekYN = ServletRequestUtils.getStringParameter(request, "inWeekYN", "N");
			String secretYN = ServletRequestUtils.getStringParameter(request, "secretYN", "N");

			String content		= request.getParameter("content");
			String place		= request.getParameter("place");
			String startHour	= request.getParameter("startHour");
			String startMin		= request.getParameter("startMin");
			String oStartHour	= request.getParameter("oStartHour");
			String oStartMin	= request.getParameter("oStartMin");
			String endHour		= request.getParameter("endHour");
			String endMin		= request.getParameter("endMin");

			if (saveMode.equals("UPDATE")) {
				this.getCompanyScheduleManager().remove(orDate[0], orDate[1], orDate[2], oStartHour, oStartMin);

				CompanyScheduleInfoData scheduleInfoData = new CompanyScheduleInfoData();
				scheduleInfoData.setYear(stDate[0]);
				scheduleInfoData.setMonth(stDate[1]);
				scheduleInfoData.setDay(stDate[2]);

				scheduleInfoData.setContent(content);
				scheduleInfoData.setPlace(place);
				scheduleInfoData.setStartHour(startHour);
				scheduleInfoData.setStartMin(startMin);
				scheduleInfoData.setEndHour(endHour);
				scheduleInfoData.setEndMin(endMin);

				this.getCompanyScheduleManager().create(scheduleInfoData);

			} else if (saveMode.equals("INSERT")) {
				CompanyScheduleInfoData scheduleInfoData = new CompanyScheduleInfoData();
				scheduleInfoData.setYear(stDate[0]);
				scheduleInfoData.setMonth(stDate[1]);
				scheduleInfoData.setDay(stDate[2]);

				scheduleInfoData.setContent(content);
				scheduleInfoData.setPlace(place);
				scheduleInfoData.setStartHour(startHour);
				scheduleInfoData.setStartMin(startMin);
				scheduleInfoData.setEndHour(endHour);
				scheduleInfoData.setEndMin(endMin);

				this.getCompanyScheduleManager().create(scheduleInfoData);

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
							Calendar cal = DateUtil.getCalendarInstance(tmpDate);
							int weekIdx = cal.get(Calendar.DAY_OF_WEEK);
							//logger.debug("날짜 : " + Integer.toString(cal.get(cal.DATE)));
							// logger.debug("요일 : " + Integer.toString(weekIdx));
							if ((weekIdx == 7) || (weekIdx == 1))
								weekYN = true;
							if (!((inWeekYN.equals("Y")) && (weekYN))) {
								String strYear = Integer.toString(cal.get(cal.YEAR));
								String strMonth = "0" + Integer.toString(cal.get(cal.MONTH) + 1);
								strMonth = strMonth.substring(strMonth.length() - 2, strMonth.length());
								String strDay = "0" + Integer.toString(cal.get(cal.DATE));
								strDay = strDay.substring(strDay.length() - 2, strDay.length());

								scheduleInfoData.setYear(strYear);
								scheduleInfoData.setMonth(strMonth);
								scheduleInfoData.setDay(strDay);

								this.getCompanyScheduleManager().create(scheduleInfoData);
								i++;
							}
						}
					}

				}
			}

			JSONWriter writer = new JSONWriter();
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

	/**
	 * 스케쥴 삭제
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void removeSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		try {

			String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
			String startHour = ServletRequestUtils.getRequiredStringParameter(request, "startHour");
			String startMin  = ServletRequestUtils.getRequiredStringParameter(request, "startMin");

			String[] stDate = sDate.split("-");

			this.getCompanyScheduleManager().remove(stDate[0], stDate[1], stDate[2], startHour, startMin);
			

			//JSONWriter writer = new JSONWriter();
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
	
	/**
	 * 한달간 스케쥴
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward companyScheduleOfMonth(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		String selectedYear = request.getParameter("selectedYear");
		String selectedMonth = request.getParameter("selectedMonth");
		
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
		
		
		List<CompanyScheduleInfoData> scheduleList = this.getCompanyScheduleManager().scheduleListOfMonth(Integer.toString(intYear), strMonth, "");
		List<HolidayInfo> holidayList = scheduleManager.getHolidayListByMonth(Integer.toString(intYear), strMonth);
		
		int si = 0;
		int hi = 0;
		List<List> arrWeek = new ArrayList<List>();
		while (iDay <= endDay) {
			List<ScheduleDay> arrDay = new ArrayList<ScheduleDay>();
			for (int j = 0; j < 7; j++) {
				String dayId = "";
				String day = "";
				String date = "";
				
				List<CompanyScheduleInfoData> dailyScheduleLst = new ArrayList<CompanyScheduleInfoData>();
				List<HolidayInfo> holidayLst = new ArrayList<HolidayInfo>();
		
				if (startIndex == j) {
					startCheck = true;
				}
				if (iDay > endDay)
					startCheck = false;
				if (startCheck) {
					String tmpStr;

					day = Integer.toString(iDay);
		
					date = Integer.toString(intYear);
					tmpStr = "0" + Integer.toString(intMonth + 1);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
					tmpStr = "0" + Integer.toString(iDay);
					date += "-" + tmpStr.substring(tmpStr.length() - 2, tmpStr.length());
		
					dayId = "ID" + date;
					
					while ((si < scheduleList.size()) && (iDay == Integer.parseInt(scheduleList.get(si).getDay()))) {
						CompanyScheduleInfoData companyScheduleInfoData = new CompanyScheduleInfoData();
						companyScheduleInfoData = scheduleList.get(si);
		
						dailyScheduleLst.add(companyScheduleInfoData);
		
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
				nDay.setDailyScheduleLst(dailyScheduleLst);
				nDay.setHolidayLst(holidayLst);
		
				arrDay.add(nDay);
			}
			arrWeek.add(arrDay);
		}
		
		request.setAttribute("intYear", intYear);
		request.setAttribute("intMonth", intMonth + 1);
		request.setAttribute("NowDate", Integer.toString(intYear) + "-" + strMonth + "-" + strNowDay);		
		request.setAttribute("calendar", arrWeek);
		return mapping.findForward("ComScheduleOfMonth");
	}
	
	
	/**
	 * 스케쥴 입력 및 관리 화면
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward companyScheduleForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sDate = ServletRequestUtils.getRequiredStringParameter(request, "sdate");
		String startHour	= ServletRequestUtils.getStringParameter(request, "startHour"	, "");
		String startMin		= ServletRequestUtils.getStringParameter(request, "startMin"	, "");
		String checkWriter  = ServletRequestUtils.getStringParameter(request, "checkWriter", "Y");
		String[] strDate = sDate.split("-");
		String saveMode = "";
		CompanyScheduleInfoData scheduleInfoData = new CompanyScheduleInfoData();
		List<CompanyScheduleInfoData> list = this.getCompanyScheduleManager().scheduleList(strDate[0], strDate[1], strDate[2], startHour, startMin);
		if(list.size() > 0){
			scheduleInfoData = list.get(0);
			saveMode = "UPDATE";
		} else {
			saveMode = "INSERT";
		}
		List<CompanyScheduleInfoData> scheduleList = this.getCompanyScheduleManager().scheduleListOfMonth(strDate[0], strDate[1], strDate[2]);
	
		
		request.setAttribute("scheduleInfoData", scheduleInfoData);
		String[] hourList = { "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00", "01", "02", "03", "04" };
		String[] minList = { "00", "10", "20", "30", "40", "50" };
		request.setAttribute("hourList", hourList);
		request.setAttribute("minList", minList);
		request.setAttribute("saveMode", saveMode);
		request.setAttribute("scheduleList", scheduleList);
		request.setAttribute("sdate", sDate);
		request.setAttribute("checkWriter", checkWriter);
		
		return mapping.findForward("ComScheduleForm");
	}
	
	/*
	public ActionForward scheduleDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sumXml = new StringBuffer();
		String searchYear = request.getParameter("searchYear");
		String searchMonth = request.getParameter("searchMonth");
		if (searchYear == null || "".equals(searchYear)) {
			searchYear = StringUtil.getCurr("yyyy");
		}
		if (searchMonth == null || "".equals(searchMonth)) {
			searchMonth = StringUtil.getCurr("MM");
		}
		int lastDay = StringUtil.getMonthlyDayCount(Integer.parseInt(searchYear),
				Integer.parseInt(searchMonth));
		List scheduleList = companyScheduleManager.scheduleList(searchYear, searchMonth, null);
		List schedulePerDayModelList = new ArrayList();
		SchedulePerDayModel temp = null;
		schedulePerDayModelList.add(0, temp);
		for (int i = 1; i < (lastDay + 1); i++) {
			schedulePerDayModelList.add(i, new SchedulePerDayModel()); // 한달의 갯수만큼 schedulePerDayModel만든다.
		}
		for (Iterator it = scheduleList.iterator(); it.hasNext();) {
			CompanyScheduleInfoData scheduleInfoData = (CompanyScheduleInfoData) it.next();
			String day = scheduleInfoData.getDay().trim();
			int dayInt = 0;
			if (day != null && !"".equals(day)) {
				dayInt = Integer.parseInt(day);
				SchedulePerDayModel schedulePerDayModel = (SchedulePerDayModel) schedulePerDayModelList.get(dayInt);
				if (schedulePerDayModel.seq < 4) {
					ScheduleModel scheduleModel = new ScheduleModel();
					scheduleModel.content = scheduleInfoData.getContent();
					schedulePerDayModel.schedulePerDay.append(scheduleModel.makeInfo());
					schedulePerDayModel.seq++;
				}
			}
		}
		for (Iterator it = schedulePerDayModelList.iterator(); it.hasNext();) {
			SchedulePerDayModel row = (SchedulePerDayModel) it.next();
			if (row != null) {
				sumXml.append(row.makeInfo());
			}
		}
		request.setAttribute("xml", "<ReceiveData>" + sumXml.toString() + "</ReceiveData>");
		return mapping.findForward("scheduleList");
	}

	public ActionForward scheduleDetailOfDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sumXml = new StringBuffer();
		String searchYear = request.getParameter("searchYear");
		String searchMonth = request.getParameter("searchMonth");
		String searchDay = request.getParameter("searchDay");
		String ssn = request.getParameter("ssn");
		if (ssn == null || "".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}

		if (searchYear == null || "".equals(searchYear)) {
			searchYear = StringUtil.getCurr("yyyy");
		}
		if (searchMonth == null || "".equals(searchMonth)) {
			searchMonth = StringUtil.getCurr("MM");
		}
		if (searchDay == null || "".equals(searchDay)) {
			searchDay = StringUtil.getCurr("dd");
		}
		if (searchDay != null) {
			if (searchDay.length() == 1) {
				searchDay = "0" + searchDay;
			}
		}
		List scheduleList = companyScheduleManager.scheduleList(searchYear, searchMonth, searchDay);

		for (Iterator it = scheduleList.iterator(); it.hasNext();) {
			CompanyScheduleInfoData scheduleInfoData = (CompanyScheduleInfoData) it.next();
			ScheduleDayModel scheduleDayModel = new ScheduleDayModel();
			scheduleDayModel.content = scheduleInfoData.getContent();
			scheduleDayModel.place = scheduleInfoData.getPlace();
			scheduleDayModel.time = scheduleInfoData.getStartHour() + "시"
					+ scheduleInfoData.getStartMin() + "분 ~ " + scheduleInfoData.getEndHour() + "시"
					+ scheduleInfoData.getEndMin() + "분";
			scheduleDayModel.startHour = scheduleInfoData.getStartHour();
			scheduleDayModel.startMin = scheduleInfoData.getStartMin();
			scheduleDayModel.endHour = scheduleInfoData.getEndHour();
			scheduleDayModel.endMin = scheduleInfoData.getEndMin();
			sumXml.append(scheduleDayModel.makeInfo());
		}

		request.setAttribute("xml", "<ReceiveData>" + sumXml.toString() + "</ReceiveData>");
		return mapping.findForward("scheduleList");
	}

	public ActionForward scheduleDetailOfCurrDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sumXml = new StringBuffer();
		String searchYear = StringUtil.getCurr("yyyy");
		String searchMonth = StringUtil.getCurr("MM");
		String searchDay = StringUtil.getCurr("dd");
		String ssn = request.getParameter("ssn");
		if (ssn == null || "".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}

		if (searchYear == null || "".equals(searchYear)) {
			searchYear = StringUtil.getCurr("yyyy");
		}
		if (searchMonth == null || "".equals(searchMonth)) {
			searchMonth = StringUtil.getCurr("MM");
		}
		if (searchDay == null || "".equals(searchDay)) {
			searchDay = StringUtil.getCurr("dd");
		}
		if (searchDay != null) {
			if (searchDay.length() == 1) {
				searchDay = "0" + searchDay;
			}
		}
		List scheduleList = companyScheduleManager.scheduleList(searchYear, searchMonth, searchDay);

		for (Iterator it = scheduleList.iterator(); it.hasNext();) {
			CompanyScheduleInfoData scheduleInfoData = (CompanyScheduleInfoData) it.next();
			ScheduleDayModel scheduleDayModel = new ScheduleDayModel();
			scheduleDayModel.content = scheduleInfoData.getContent();
			scheduleDayModel.place = scheduleInfoData.getPlace();
			scheduleDayModel.time = scheduleInfoData.getStartHour() + "시"
					+ scheduleInfoData.getStartMin() + "분 ~ " + scheduleInfoData.getEndHour() + "시"
					+ scheduleInfoData.getEndMin() + "분";
			scheduleDayModel.startHour = scheduleInfoData.getStartHour();
			scheduleDayModel.startMin = scheduleInfoData.getStartMin();
			scheduleDayModel.endHour = scheduleInfoData.getEndHour();
			scheduleDayModel.endMin = scheduleInfoData.getEndMin();
			sumXml.append(scheduleDayModel.makeInfo());
		}

		request.setAttribute("xml", "<ReceiveData>" + sumXml.toString() + "</ReceiveData>");
		return mapping.findForward("scheduleList");
	}

	/**
	 * 초기화면의 회사일정(당월 표함 3개월)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	/*
	public ActionForward scheduleDetailOfMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer sumXml = new StringBuffer();
		String searchYear = request.getParameter("searchYear");
		String searchMonth = request.getParameter("searchMonth");
		String searchDay = request.getParameter("searchDay");
		String ssn = request.getParameter("ssn");
		if (ssn == null || "".equals(ssn)) {
			ssn = SessionUtils.getUsername(request);
		}

		if (searchYear == null || "".equals(searchYear)) {
			searchYear = StringUtil.getCurr("yyyy");
		}
		if (searchMonth == null || "".equals(searchMonth)) {
			searchMonth = StringUtil.getCurr("MM");
		}
		if (searchDay == null || "".equals(searchDay)) {
			searchDay = StringUtil.getCurr("dd");
		}
		if (searchDay != null) {
			if (searchDay.length() == 1) {
				searchDay = "0" + searchDay;
			}
		}
		List scheduleList = companyScheduleManager.scheduleListOfMonth(searchYear,
				searchMonth,
				searchDay);

		int i = 0;
		for (Iterator it = scheduleList.iterator(); it.hasNext();) {
			CompanyScheduleInfoData scheduleInfoData = (CompanyScheduleInfoData) it.next();
			ScheduleMonthModel scheduleMonthModel = new ScheduleMonthModel();

			scheduleMonthModel.content = scheduleInfoData.getContent();
			scheduleMonthModel.place = scheduleInfoData.getPlace();
			scheduleMonthModel.time = scheduleInfoData.getStartHour() + "시"
					+ scheduleInfoData.getStartMin() + "분 ~ " + scheduleInfoData.getEndHour() + "시"
					+ scheduleInfoData.getEndMin() + "분";
			scheduleMonthModel.startHour = scheduleInfoData.getStartHour();
			scheduleMonthModel.startMin = scheduleInfoData.getStartMin();
			scheduleMonthModel.endHour = scheduleInfoData.getEndHour();
			scheduleMonthModel.endMin = scheduleInfoData.getEndMin();

			scheduleMonthModel.yearMonthDay = scheduleInfoData.getYear()
					+ scheduleInfoData.getMonth() + scheduleInfoData.getDay();

			sumXml.append(scheduleMonthModel.makeInfo());
			i++;
			if (i == 8) 
				break;
		}

		request.setAttribute("xml", "<ReceiveData>" + sumXml.toString() + "</ReceiveData>");
		return mapping.findForward("scheduleList");
	}
	*/

}
