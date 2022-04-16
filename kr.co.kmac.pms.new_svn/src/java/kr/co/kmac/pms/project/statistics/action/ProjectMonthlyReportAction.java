/*
 * $Id: ProjectMonthlyReportAction.java,v 1.14 2018/01/29 02:25:46 cvs Exp $
 * creation-date : 2009. 8. 28. 오영택
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
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

/**
 * 지도일지월별현황
 * 8. 18 오영택
 */

/**
 * @struts.action name="responseForm" path="/action/ProjectMonthlyReportAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="ProjectMonthlyReportView" path="/project/statistics/ProjectMonthlyReportView.jsp" redirect="false"
 * @struts.action-forward name="ProjectMonthlyReportViewDetail" path="/project/statistics/ProjectMonthlyReportViewDetail.jsp" redirect="false"
 * @struts.action-forward name="ProjectMonthlyReportView2" path="/project/statistics/ProjectMonthlyReportView2.jsp" redirect="false"
 * @struts.action-forward name="ProjectMonthlyReportViewDetail2" path="/project/statistics/ProjectMonthlyReportViewDetail2.jsp" redirect="false" *
 * @struts.action-forward name="projectWreportStatList" path="/project/statistics/projectWreportStatList.jsp" redirect="false" *
 * @struts.action-forward name="projectMreportStatList" path="/project/statistics/projectMreportStatList.jsp" redirect="false" *
 * @struts.action-forward name="projectWreportStatDetailList" path="/project/statistics/projectWreportStatDetailList.jsp" redirect="false" *
 * @struts.action-forward name="projectMreportStatDetailList" path="/project/statistics/projectMreportStatDetailList.jsp" redirect="false" *
 */
public class ProjectMonthlyReportAction extends RepositoryDispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ProjectMonthlyReportAction.class);
	private ExpertPoolManager expertPoolManager;

	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		String month = request.getParameter("month") == null ? "" : request.getParameter("month");
		String jobClass = request.getParameter("jobClass") == null ? "" : request.getParameter("jobClass");
		String deptCode = request.getParameter("deptCode") == null ? "" : request.getParameter("deptCode");
		String writer = request.getParameter("writer") == null ? "" : request.getParameter("writer");
		String searchOK = request.getParameter("searchOK") == null || request.getParameter("searchOK").equals("") ? "0" : request.getParameter("searchOK");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			Calendar c = new GregorianCalendar();
			String strEyear = Integer.toString(c.get(Calendar.YEAR));

			if (searchOK.equals("0")) {
				year = strEyear;
				month = Integer.toString(c.get(Calendar.MONTH) + 1);
				jobClass = "J";
			}
			if (month.length() < 2)
				month = "0" + month;

			filters.put("assignDate", year + month + "__");

			if (jobClass != null && !jobClass.equals("")) {
				if (jobClass.equals("A")) {
					filters.put("A", jobClass);
				} else if (jobClass.equals("J")) {
					filters.put("J", jobClass);
				} else if (jobClass.equals("H")) {
					filters.put("H", jobClass);
				} else {
					filters.put("C", jobClass);
				}
			}

			if (deptCode != null && !deptCode.equals("")) {
				if (jobClass.equals("A"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			filters.put("writer", writer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myselectListEntry", info);

			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("writer", writer);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("strEyear", strEyear);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMonthlyReportView");
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String writerSsn = request.getParameter("writerSsn") == null ? "" : request.getParameter("writerSsn");
		String writerName = request.getParameter("writerName") == null ? "" : request.getParameter("writerName");
		String yearMonth = request.getParameter("yearMonth") == null ? "" : request.getParameter("yearMonth");
		String state = request.getParameter("state") == null ? "" : request.getParameter("state");
		String writeState = request.getParameter("writeState") == null ? "" : request.getParameter("writeState");
		String doneState = request.getParameter("doneState") == null ? "" : request.getParameter("doneState");
		String bizTypeCode = request.getParameter("bizTypeCode") == null ? "" : request.getParameter("bizTypeCode");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (writerSsn != null && !writerSsn.equals("")) {
				filters.put("writerSsn", writerSsn);
			}
			if (yearMonth != null && !yearMonth.equals("")) {
				filters.put("yearMonth", yearMonth + "__");
			}
			if (state != null && !state.equals("")) {
				filters.put("state", state);
			}
			if (writeState != null && !writeState.equals("")) {
				filters.put("writeState", writeState);
			}
			if (doneState != null && !doneState.equals("")) {
				filters.put("doneState", doneState);
			}
			if (bizTypeCode != null && !bizTypeCode.equals("")) {
				filters.put("bizTypeCode", bizTypeCode);
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myProjectReportDetailListEntry", info);

			request.setAttribute("result", valueList);
			request.setAttribute("writerSsn", writerSsn);
			request.setAttribute("writerName", writerName);
			request.setAttribute("yearMonth", yearMonth);
			request.setAttribute("state", state);
			request.setAttribute("writeState", writeState);
			request.setAttribute("doneState", doneState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMonthlyReportViewDetail");
	}

	public ActionForward selectProjectReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String ssn = SessionUtils.getUsername(request);

		try {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (businessTypeCode != null && !businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (jobClass != null && !jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (projectName != null && !projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			String sMonth = month;
			if (month.length() < 2)
				sMonth = "0" + month;
			filters.put("yearMonth", year + sMonth + "__");

			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR"); // COO
			} else if (expertPool.getCompanyPosition().equals("06CB")) {
				filters.put("runningDivCode", expertPool.getDept()); // CBO
			} else if (expertPool.getJobClass().equals("J")) {
				filters.put("writerSsn", SessionUtils.getUsername(request)); // 전문가그룹
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM"); // PM
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectMreportStatList", info);

			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("projectName", projectName);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMonthlyReportView2");
	}

	public ActionForward selectProjectReportListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String yearMonth = ServletRequestUtils.getStringParameter(request, "yearMonth", "");
		String state = ServletRequestUtils.getStringParameter(request, "state", "");
		String writeState = ServletRequestUtils.getStringParameter(request, "writeState", "");
		String doneState = ServletRequestUtils.getStringParameter(request, "doneState", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		String writerSsn = ServletRequestUtils.getStringParameter(request, "writerSsn", "");

		try {

			// ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectCode != null && !projectCode.equals("")) {
				filters.put("projectCode", projectCode);
			}
			if (yearMonth != null && !yearMonth.equals("")) {
				filters.put("yearMonth", yearMonth + "__");
			}
			if (state != null && !state.equals("")) {
				filters.put("state", state);
			}
			if (writeState != null && !writeState.equals("")) {
				filters.put("writeState", writeState);
			}
			if (doneState != null && !doneState.equals("")) {
				filters.put("doneState", doneState);
			}
			if (jobClass != null && !jobClass.equals("")) {
				if (jobClass.equals("B"))
					jobClass = "J";
				filters.put("jobClass", jobClass);
			}
			if (writerSsn != null && !writerSsn.equals("")) {
				filters.put("writerSsn", writerSsn);
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("myProjectReportDetailListEntry", info);

			request.setAttribute("result", valueList);
			request.setAttribute("yearMonth", yearMonth);
			request.setAttribute("state", state);
			request.setAttribute("writeState", writeState);
			request.setAttribute("doneState", doneState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("ProjectMonthlyReportViewDetail2");
	}

	public ActionForward selectListMreportStat(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		String month = request.getParameter("month") == null ? "" : request.getParameter("month");
		String jobClass = request.getParameter("jobClass") == null ? "" : request.getParameter("jobClass");
		String deptCode = request.getParameter("deptCode") == null ? "" : request.getParameter("deptCode");
		String writer = request.getParameter("writer") == null ? "" : request.getParameter("writer");
		String searchOK = request.getParameter("searchOK") == null || request.getParameter("searchOK").equals("") ? "0" : request.getParameter("searchOK");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			Calendar c = new GregorianCalendar();
			String strEyear = Integer.toString(c.get(Calendar.YEAR));

			if (searchOK.equals("0")) {
				year = strEyear;
				month = Integer.toString(c.get(Calendar.MONTH) + 1);
				//jobClass = "J";
			}
			if (month.length() < 2)
				month = "0" + month;

			filters.put("assignDate", year + month + "__");

			if (jobClass != null && !jobClass.equals("")) {
				if (jobClass.equals("A")) {
					filters.put("A", jobClass);
				} else if (jobClass.equals("J")) {
					filters.put("J", jobClass);
				} else if (jobClass.equals("H")) {
					filters.put("H", jobClass);
				}  else if (jobClass.equals("B")) {
					filters.put("B", jobClass);
				} else {
					filters.put("C", jobClass);
				}
			}

			if (deptCode != null && !deptCode.equals("")) {
				if (jobClass.equals("A"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			filters.put("writer", writer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectMreportStatList", info);

			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("writer", writer);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("strEyear", strEyear);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectMreportStatList");
	}

	public ActionForward selectListMreportStatDetailList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String writerSsn = request.getParameter("writerSsn") == null ? "" : request.getParameter("writerSsn");
		String writerName = request.getParameter("writerName") == null ? "" : request.getParameter("writerName");
		String yearMonth = request.getParameter("yearMonth") == null ? "" : request.getParameter("yearMonth");
		String state = request.getParameter("state") == null ? "" : request.getParameter("state");
		String writeState = request.getParameter("writeState") == null ? "" : request.getParameter("writeState");
		String doneState = request.getParameter("doneState") == null ? "" : request.getParameter("doneState");
		String bizTypeCode = request.getParameter("bizTypeCode") == null ? "" : request.getParameter("bizTypeCode");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (writerSsn != null && !writerSsn.equals("")) {
				filters.put("writerSsn", writerSsn);
			}
			if (yearMonth != null && !yearMonth.equals("")) {
				filters.put("yearMonth", yearMonth + "__");
			}
			if (state != null && !state.equals("")) {
				filters.put("state", state);
			}
			if (writeState != null && !writeState.equals("")) {
				filters.put("writeState", writeState);
			}
			if (doneState != null && !doneState.equals("")) {
				filters.put("doneState", doneState);
			}
			if (bizTypeCode != null && !bizTypeCode.equals("")) {
				filters.put("bizTypeCode", bizTypeCode);
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectMreportStatDetailList", info);

			request.setAttribute("result", valueList);
			request.setAttribute("writerSsn", writerSsn);
			request.setAttribute("writerName", writerName);
			request.setAttribute("yearMonth", yearMonth);
			request.setAttribute("state", state);
			request.setAttribute("writeState", writeState);
			request.setAttribute("doneState", doneState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectMreportStatDetailList");
	}

	public ActionForward selectListWreportStat(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String year = request.getParameter("year") == null ? "" : request.getParameter("year");
		String month = request.getParameter("month") == null ? "" : request.getParameter("month");
		String jobClass = request.getParameter("jobClass") == null ? "" : request.getParameter("jobClass");
		String deptCode = request.getParameter("deptCode") == null ? "" : request.getParameter("deptCode");
		String writer = request.getParameter("writer") == null ? "" : request.getParameter("writer");
		String searchOK = request.getParameter("searchOK") == null || request.getParameter("searchOK").equals("") ? "0" : request.getParameter("searchOK");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			Calendar c = new GregorianCalendar();
			String strEyear = Integer.toString(c.get(Calendar.YEAR));

			if (searchOK.equals("0")) {
				year = strEyear;
				month = Integer.toString(c.get(Calendar.MONTH) + 1);
				jobClass = "J";
			}
			if (month.length() < 2)
				month = "0" + month;

			filters.put("assignDate", year + month + "__");

			if (jobClass != null && !jobClass.equals("")) {
				if (jobClass.equals("A")) {
					filters.put("A", jobClass);
				} else if (jobClass.equals("J")) {
					filters.put("J", jobClass);
				} else if (jobClass.equals("H")) {
					filters.put("H", jobClass);
				} else {
					filters.put("C", jobClass);
				}
			}

			if (deptCode != null && !deptCode.equals("")) {
				if (jobClass.equals("A"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			filters.put("writer", writer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectWreportStatList", info);

			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("writer", writer);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("strEyear", strEyear);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectWreportStatList");
	}

	public ActionForward selectListWreportStatDetailList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String writerSsn = request.getParameter("writerSsn") == null ? "" : request.getParameter("writerSsn");
		String writerName = request.getParameter("writerName") == null ? "" : request.getParameter("writerName");
		String yearMonth = request.getParameter("yearMonth") == null ? "" : request.getParameter("yearMonth");
		String state = request.getParameter("state") == null ? "" : request.getParameter("state");
		String writeState = request.getParameter("writeState") == null ? "" : request.getParameter("writeState");
		String doneState = request.getParameter("doneState") == null ? "" : request.getParameter("doneState");
		String bizTypeCode = request.getParameter("bizTypeCode") == null ? "" : request.getParameter("bizTypeCode");

		try {

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("ProjectMonthlyReportListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (writerSsn != null && !writerSsn.equals("")) {
				filters.put("writerSsn", writerSsn);
			}
			if (yearMonth != null && !yearMonth.equals("")) {
				filters.put("yearMonth", yearMonth + "__");
			}
			if (state != null && !state.equals("")) {
				filters.put("state", state);
			}
			if (writeState != null && !writeState.equals("")) {
				filters.put("writeState", writeState);
			}
			if (doneState != null && !doneState.equals("")) {
				filters.put("doneState", doneState);
			}
			if (bizTypeCode != null && !bizTypeCode.equals("")) {
				filters.put("bizTypeCode", bizTypeCode);
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("projectWreportStatDetailList", info);

			request.setAttribute("result", valueList);
			request.setAttribute("writerSsn", writerSsn);
			request.setAttribute("writerName", writerName);
			request.setAttribute("yearMonth", yearMonth);
			request.setAttribute("state", state);
			request.setAttribute("writeState", writeState);
			request.setAttribute("doneState", doneState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectWreportStatDetailList");
	}

	// //////////////////////////////////////////////
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
}