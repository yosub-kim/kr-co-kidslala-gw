/*
 * $Id: CustomerInfoMobileAction.java,v 1.11 2017/09/11 00:37:25 cvs Exp $
 * creation-date : 2009. 8. 28. 오영택
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.customer.data.CustomerForm;
import kr.co.kmac.pms.customer.manager.CustomerManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="customerInfoMobileAction" path="/action/CustomerInfoMobileAction" parameter="mode" scope="request"
 */
public class CustomerInfoMobileAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(CustomerInfoMobileAction.class);
	private CustomerManager customerManager;

	public CustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void getCustomerInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String page = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String customerinfo_type = ServletRequestUtils.getStringParameter(request, "customerinfo_type", "");
		String function_type = ServletRequestUtils.getStringParameter(request, "function_type", "");
		String subject = ServletRequestUtils.getStringParameter(request, "subject", "");
		String ssn = SessionUtils.getUsername(request);
		String jobClass = this.getCustomerManager().selectCustomerInfo(ssn);
		String manager_chk = this.getCustomerManager().selectManagerChk(ssn);

		try {
			URL url = new URL("https://intranet.kmac.co.kr/kmac/comlist/customer_list_mobile.asp" 
					+ "?page=" + page 
					+ "&customerinfo_type=" + customerinfo_type 
					+ "&function_type=" + function_type 
					+ "&subject=" + subject 
					+ "&ssn=" + ssn + "&manager_chk=" + manager_chk + "&jobClass=" + jobClass);
			URLConnection conn = url.openConnection();

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			response.setContentType("text/html;charset=utf-8");
			Writer w = response.getWriter();
			w.write(sb.toString());
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}

	}

	//ASP 수정필요 -- 정보 수집자/ 수집방법
	public void getCustomerInfoDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		String agent = ServletRequestUtils.getRequiredStringParameter(request, "agent");
		String ssn = SessionUtils.getUsername(request);
		try {
			URL url = new URL("https://intranet.kmac.co.kr/kmac/comlist/customer_detail_mobile.asp?idx=" + idx + "&ssn=" + ssn + "&agent="
					+ URLEncoder.encode(agent, "ms949"));
			URLConnection conn = url.openConnection();

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			response.setContentType("text/html;charset=utf-8");
			Writer w = response.getWriter();
			w.write(sb.toString());
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}

	}

	public void getCustomerCompanySearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String search_cpmpanyName = ServletRequestUtils.getRequiredStringParameter(request, "search_cpmpanyName");
		try {
			URL url = new URL("https://intranet.kmac.co.kr/kmac/comlist/comSearch_ins_mobile.asp?searchStr=" + search_cpmpanyName);
			URLConnection conn = url.openConnection();

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			response.setContentType("text/html;charset=utf-8");
			Writer w = response.getWriter();
			w.write(sb.toString());
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void getCustomerPersonSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String search_customerName = ServletRequestUtils.getRequiredStringParameter(request, "search_customerName");
		try {
			URL url = new URL("https://intranet.kmac.co.kr/kmac/comlist/embbsSearch_mobile.asp?pname=" + search_customerName);
			URLConnection conn = url.openConnection();

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			response.setContentType("text/html;charset=utf-8");
			Writer w = response.getWriter();
			w.write(sb.toString());
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	//ASP 수정필요-- 정보 수집자 검색 창
	public void getCustomerCollectorSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String search_collectorName = ServletRequestUtils.getRequiredStringParameter(request, "search_collectorName");
		try {
			URL url = new URL("https://intranet.kmac.co.kr/kmac/comlist/embbsGatherSearch_mobile.asp?s_word=" + search_collectorName);
			URLConnection conn = url.openConnection();

			StringBuilder sb = new StringBuilder();
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			response.setContentType("text/html;charset=utf-8");
			Writer w = response.getWriter();
			w.write(sb.toString());
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveCustomerInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CustomerForm customerForm = (CustomerForm) form;
			getCustomerManager().insertCustomerInfo(customerForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

}
