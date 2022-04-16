package kr.co.kmac.pms.system.accesslog.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.system.accesslog.manager.IMenuLogManager;
import kr.co.kmac.pms.system.accesslog.manager.AccountHistoryManager;
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
 * @struts.action name="accessLogAction"					path="/action/AccessLogAction" parameter="mode" scope="request"
 * @struts.action-forward name="loginLogReport"				path="/system/loginlog/loginLog.jsp"					redirect="false"
 * @struts.action-forward name="loginLogDetailReport"		path="/system/loginlog/loginLogDetail.jsp"				redirect="false"
 * @struts.action-forward name="loginLogTimeOrderReport"	path="/system/loginlog/loginLogTimeOrder.jsp"			redirect="false"
 * @struts.action-forward name="loginLogDetailExcel"		path="/system/loginlog/loginLogExcel.jsp"				redirect="false" 
 * @struts.action-forward name="accountHistoryList" 		path="/system/accountHistoryLog/accountHistoryList.jsp"	redirect="false"
 */
public class AccessLogAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(AccessLogAction.class);
	private IMenuLogManager menuLogManager;
	private AccountHistoryManager accountHistoryManager; 

	public void insertLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String menuId = ServletRequestUtils.getRequiredStringParameter(request, "menuId");

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			menuLogManager.insertMenuLog(request.getSession(), menuId);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public ActionForward getLoginLogReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String pg 		= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String dateType = ServletRequestUtils.getStringParameter(request, "dateType"	, "2");
		String from 	= ServletRequestUtils.getStringParameter(request, "from"		, DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String to   	= ServletRequestUtils.getStringParameter(request, "to"			, DateTime.getYear() + "-" + DateTime.getMonth() + "-31");
		String name 	= ServletRequestUtils.getStringParameter(request, "name"		, "");
		
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
		
		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("systemLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLoginLogReport", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String, String> searchData = new HashMap<String, String>();
		searchData.put("dateType"	, dateType);
		searchData.put("from"		, from);
		searchData.put("to"			, to);
		searchData.put("name"		, name);
		request.setAttribute("search", searchData);
		
		return mapping.findForward("loginLogReport");
	}
	
	public ActionForward getLoginLogDetailReport(ActionMapping mapping, ActionForm form,
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
			ValueListHandler vlh = (ValueListHandler) wc.getBean("systemLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLoginLogReportDetail", info);
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
		
		return mapping.findForward("loginLogDetailReport");
	}
	
	public ActionForward getLoginLogTimeOrderReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String pg 		= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String dateType = ServletRequestUtils.getStringParameter(request, "dateType"	, "2");
		String from 	= ServletRequestUtils.getStringParameter(request, "from"		, DateTime.getYear() + "-" + DateTime.getMonth() + "-01");
		String to   	= ServletRequestUtils.getStringParameter(request, "to"			, DateTime.getYear() + "-" + DateTime.getMonth() + "-31");
		String name 	= ServletRequestUtils.getStringParameter(request, "name"		, "");
		
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
		
		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("systemLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLoginLogTimeOrderReport", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String, String> searchData = new HashMap<String, String>();
		searchData.put("dateType"	, dateType);
		searchData.put("from"		, from);
		searchData.put("to"			, to);
		searchData.put("name"		, name);
		request.setAttribute("search", searchData);
		
		return mapping.findForward("loginLogTimeOrderReport");
	}
	
	public ActionForward saveLoginLogDetailReport(ActionMapping mapping, ActionForm form,
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
		
		String fileName = "(" + name + ") 로그인 내역.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_PAGE, String.valueOf("1"));
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));

		if (!ssn.equals("")) {
			filters.put("ssn", ssn);
		}
		if (!from.equals("")) {
			filters.put("from", from);
		}
		if (!to.equals("")) {
			filters.put("to", sTo);
		}
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("systemLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLoginLogReportDetail", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("loginLogDetailExcel");		
	}
	
	public ActionForward getAccountHistoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize		= ServletRequestUtils.getStringParameter(request, "pageSize"	, "15");
		String pg 			= ServletRequestUtils.getStringParameter(request, "pg"			, "1");
		String accessType	= ServletRequestUtils.getStringParameter(request, "accessType"	, "");
		String from 		= ServletRequestUtils.getStringParameter(request, "from"		, "");
		String to 			= ServletRequestUtils.getStringParameter(request, "to"			, "");
		String jobClass		= ServletRequestUtils.getStringParameter(request, "jobClass"	, "");
		String deptCode 	= ServletRequestUtils.getStringParameter(request, "deptCode"	, "");
		String name 		= ServletRequestUtils.getStringParameter(request, "name"		, "");
		
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("systemLogReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			
			if (!from.equals("") && !to.equals("")) {
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				cal.setTime(Date.valueOf(to));
				cal.add(Calendar.DATE, 1);
				String sTo = df.format(cal.getTime());
				filters.put("from", from);
				filters.put("to", sTo);
			}
			
			if (!accessType.equals("")) {
				filters.put("accessType", accessType);
			}
			
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			
			if (!deptCode.equals("")) {
				filters.put("deptCode", deptCode);
			}
			
			if (!pg.equals("")) {
				filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			}
			
			if (!pageSize.equals("")) {
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			}

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getAccountHistoryList", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("accessType"	, accessType);
		request.setAttribute("from"			, StringUtil.replace(from, "-", ""));
		request.setAttribute("to"			, StringUtil.replace(to, "-", ""));
		request.setAttribute("jobClass"		, jobClass);
		request.setAttribute("deptCode"		, deptCode);
		request.setAttribute("name"			, name);
		
		return mapping.findForward("accountHistoryList");
	}
	

	public void updateEtc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String seq = request.getParameter("seq");
		String etc = request.getParameter("etc");
		try {
			accountHistoryManager.updateEtc(seq, etc);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public IMenuLogManager getMenuLogManager() {
		return menuLogManager;
	}

	public void setMenuLogManager(IMenuLogManager menuLogManager) {
		this.menuLogManager = menuLogManager;
	}
	
	public AccountHistoryManager getAccountHistoryManager() {
		return accountHistoryManager;
	}

	public void setAccountHistoryManager(AccountHistoryManager accountHistoryManager) {
		this.accountHistoryManager = accountHistoryManager;
	}
}
