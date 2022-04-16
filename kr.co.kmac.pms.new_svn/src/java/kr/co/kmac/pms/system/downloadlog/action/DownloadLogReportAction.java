/*
 * $Id: DownloadLogReportAction.java,v 1.7 2017/07/20 23:49:08 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.system.downloadlog.manager.*;
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

/**
 * 다운로드 로그를 검색하기 위한 Action Class
 * @author jiwoongLee
 * @version $Id: DownloadLogReportAction.java,v 1.7 2017/07/20 23:49:08 cvs Exp $
 */
/**
 * @struts.action name="downloadLogReportAction" path="/action/DownloadLogReportAction" parameter="mode" scope="request"
 * @struts.action-forward name="downloadLogReport"		path="/system/downloadlog/downloadLog.jsp"			redirect="false"
 * @struts.action-forward name="downloadLogDetailReport"	path="/system/downloadlog/downloadLogDetail.jsp"	redirect="false"
 * @struts.action-forward name="downloadLogDetailExcel"	path="/system/downloadlog/downloadLogExcel.jsp"		redirect="false"
 */
public class DownloadLogReportAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(DownloadLogReportAction.class);
	private DownloadLogManager downloadLogManager;

	/**
	 * 다운로드 로그를 검색
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDownloadLogReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String pg 		= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String dateType = ServletRequestUtils.getStringParameter(request, "dateType"	, "2");
		String from 	= ServletRequestUtils.getStringParameter(request, "from"		, DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String to   	= ServletRequestUtils.getStringParameter(request, "to"			, DateTime.getYear() + "-" + DateTime.getMonth() + "-31");
		String name 	= ServletRequestUtils.getStringParameter(request, "name"		, "");
		String limitYN 	= ServletRequestUtils.getStringParameter(request, "limitYN"		, "");
		
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(Date.valueOf(to));
		cal.add(Calendar.DATE, 1);
		String sTo = df.format(cal.getTime());			

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		if (!name.equals("")) {
			filters.put("name", "%" + name + "%");
		}
		filters.put("from", from);
		filters.put("to", sTo);
		
		if (!limitYN.equals("")) {
			if (limitYN.equals("Y")) filters.put("limit", "limit");
			else filters.put("unLimit", "unLimit");
		}
		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("downloadLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getDownloadLogReport", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String, String> searchData = new HashMap<String, String>();
		searchData.put("dateType"	, dateType);
		searchData.put("from"		, from);
		searchData.put("to"			, to);
		searchData.put("name"		, name);
		searchData.put("limitYN"	, limitYN);
		request.setAttribute("search", searchData);
		
		return mapping.findForward("downloadLogReport");
	}

	/**
	 * 다운로드 상세 로그를 검색
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDownloadLogDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageSize	= ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String pg		= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String ssn		= ServletRequestUtils.getStringParameter(request, "ssn"			, "");
		String from		= ServletRequestUtils.getStringParameter(request, "from"		, "");
		String to		= ServletRequestUtils.getStringParameter(request, "to"			, "");
		String name     = ServletRequestUtils.getStringParameter(request, "name"		, "");
		
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(Date.valueOf(to));
		cal.add(Calendar.DATE, 1);
		String sTo = df.format(cal.getTime());
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}
		if (!ssn.equals("")) {
			filters.put("ssn", ssn);
		}
		filters.put("from", from);
		filters.put("to", sTo);
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("downloadLogReport",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getDownloadLogReportDetail", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String, String> searchData = new HashMap<String, String>();
		searchData.put("from"		, from);
		searchData.put("to"			, to);
		searchData.put("ssn"		, ssn);
		searchData.put("name"		, name);
		request.setAttribute("search", searchData);
		
		return mapping.findForward("downloadLogDetailReport");
	}

	/**
	 * 다운로드 상세 로그를 검색
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDownloadLogDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String ssn		= ServletRequestUtils.getStringParameter(request, "ssn"	, "");
		String from		= ServletRequestUtils.getStringParameter(request, "from", "");
		String to		= ServletRequestUtils.getStringParameter(request, "to"	, "");
		String name     = ServletRequestUtils.getStringParameter(request, "name", "");
		
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.setTime(Date.valueOf(to));
		cal.add(Calendar.DATE, 1);
		String sTo = df.format(cal.getTime());
		
		String fileName = "(" + name + ") 다운로드 리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_PAGE, String.valueOf("1"));
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

		if (!ssn.equals("")) {
			filters.put("ssn", ssn);
		}
		filters.put("from", from);
		filters.put("to", sTo);
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("downloadLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getDownloadLogReportDetail", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("downloadLogDetailExcel");
	}

	public DownloadLogManager getDownloadLogManager() {
		return this.downloadLogManager;
	}
	
	public void setDownloadLogManager(DownloadLogManager downloadLogManager) {
		this.downloadLogManager = downloadLogManager;
	}
}