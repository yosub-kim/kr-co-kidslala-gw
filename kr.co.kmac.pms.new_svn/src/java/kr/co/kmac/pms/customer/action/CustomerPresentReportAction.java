/*
 * $Id: CustomerPresentReportAction.java,v 1.4 2011/06/14 09:00:35 cvs Exp $
 * creation-date : 2009. 8. 28. 오영택
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.customer.data.CustomerForm;
import kr.co.kmac.pms.customer.exception.CustomerException;

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
 * @struts.action name="responseForm" path="/action/CustomerPresentReportAction" parameter="mode" scope="request"
 * @struts.action-forward name="customerTab"					path="/customer/customer_main.jsp"					redirect="false"
 * @struts.action-forward name="customerProjectPresentReport" path="/customer/customerProjectPresentReport.jsp"	redirect="false"
 * @struts.action-forward name="saveToExcel"					path="/customer/saveToExcel.jsp"					redirect="false"
 * @struts.action-forward name="customerSelectListResult"		path="/customer/customerDetailList.jsp"				redirect="false"
 * @struts.action-forward name="customerSelectResult"			path="/customer/customerDetailView.jsp"				redirect="false"
 */
public class CustomerPresentReportAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(CustomerPresentReportAction.class);

	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	
	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public ActionForward movePagePresentReportTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward("customerTab");
	}
	
	public ActionForward getCustomerProjectPresentReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String from = ServletRequestUtils.getStringParameter(request, "from", "");
		String to = ServletRequestUtils.getStringParameter(request, "to", "");
		String reportType = ServletRequestUtils.getStringParameter(request, "reportType","");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		
		if (!from.equals("")) {
			String fromDate = from.replaceAll("-", "");
			filters.put("from", fromDate);
		}
		if (!to.equals("")) { 
			String toDate = to.replaceAll("-", "");
			filters.put("to", toDate);
		}
		String entryKey = "customerPresentReportForBUQuery";
		if (!reportType.equals("")) {
			if (reportType.equals("DEPT")) {
				//entryKey = "customerProjectPresentReportQuery" ;
				entryKey = "customerProjectPresentReportQuery" ;
			}else if (reportType.equals("PU")) {
				entryKey = "customerProjectPresentReportForPersonPUQueryWithSubtotal" ;
			}else if (reportType.equals("BU")) {
				entryKey = "customerProjectPresentReportForPersonBUQueryWithSubtotal" ;
			}
		}
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("customerSelectListBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();  // ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList(entryKey, info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("customerProjectPresentReport");
	}
	
	public ActionForward saveToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String from = ServletRequestUtils.getStringParameter(request, "from", "");
		String to = ServletRequestUtils.getStringParameter(request, "to", "");
		String reportType = ServletRequestUtils.getStringParameter(request, "reportType","");
		
		String fileName = "고객등록현황_개인("+ from + "~" + to + ").xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		
		if (!from.equals("")) {
			String fromDate = from.replaceAll("-", "");
			filters.put("from", fromDate);
		}
		if (!to.equals("")) { 
			String toDate = to.replaceAll("-", "");
			filters.put("to", toDate);
		}
		String entryKey = "customerPresentReportForBUQuery";
		if (!reportType.equals("")) {
			if (reportType.equals("DEPT")) {
				//entryKey = "customerProjectPresentReportQuery" ;
				entryKey = "customerProjectPresentReportQuery" ;
			}else if (reportType.equals("PU")) {
				entryKey = "customerProjectPresentReportForPersonPUQueryWithSubtotal" ;
			}else if (reportType.equals("BU")) {
				entryKey = "customerProjectPresentReportForPersonBUQueryWithSubtotal" ;
			}
		}
		
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("customerSelectListBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();  // ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList(entryKey, info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("saveToExcel");
	}
	
	/**
	 * 개인별 고객정보 리스트 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception job date: 2007-04-06 author: yhyim purpose:
	 */
	public ActionForward selectListForIndividual(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CustomerForm customerForm = new CustomerForm();

		String pageSize			= ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pg				= ServletRequestUtils.getStringParameter(request, "pg", "1");
		String writer			= ServletRequestUtils.getStringParameter(request, "writer");
		String customerInfoType = ServletRequestUtils.getStringParameter(request, "customerInfoType");
		String from				= ServletRequestUtils.getStringParameter(request, "from");
		String to				= ServletRequestUtils.getStringParameter(request, "to");
		String industryTypeCode	= ServletRequestUtils.getStringParameter(request, "industryTypeCode", "");
		String businessTypeCode	= ServletRequestUtils.getStringParameter(request, "businessTypeCode");
		String valueType		= ServletRequestUtils.getStringParameter(request, "valueType");

		if (writer != null && !writer.equals("")) {
			customerForm.setWriter(writer);
		} //

		if (customerInfoType != null && !customerInfoType.equals("")) {
			customerForm.setCustomerInfoType(customerInfoType);
		} //

		if (from != null && !from.equals("")) {
			customerForm.setFrom(from);
		}
		if (to != null && !to.equals("")) {
			customerForm.setTo(to);
		}
		//if (industryTypeCode != null && !industryTypeCode.equals("")) {
			customerForm.setIndustryTypeCode(industryTypeCode);
		//}
		if (businessTypeCode != null && !businessTypeCode.equals("")) {
			customerForm.setBusinessTypeCode(businessTypeCode);
		}
		if (valueType != null && !valueType.equals("")) {
			customerForm.setValueType(valueType);
		}
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		try {
			request.setAttribute("result", this.selectListForIndividual(wc, customerForm, pg, pageSize));

		} catch (CustomerException e) {
			logger.error(e.getMessage(), e);

		}
		Map<String, String> pageData = new HashMap<String, String>();
		pageData.put("pg"				, pg);
		pageData.put("writer"			, writer);
		pageData.put("customerInfoType"	, customerInfoType);
		pageData.put("from"				, from);
		pageData.put("to"				, to);
		pageData.put("industryTypeCode"	, industryTypeCode);
		pageData.put("businessTypeCode"	, businessTypeCode);
		pageData.put("valueType"		, valueType);
		
		request.setAttribute("pageData", pageData);
		
		return mapping.findForward("customerSelectListResult");
	}
	
	/*
	 * 개인별 고객정보 리스트
	 * @see kr.co.kmac.pms.customer.manager.CustomerManager#selectList(org.springframework.web.context.WebApplicationContext,
	 *      kr.co.kmac.pms.customer.data.CustomerForm, java.lang.String, java.lang.String)
	 * job date: 2007-04-06
	 * author: yhyim
	 * purpose: 개인별 고객정보 리스트 가져오기
	 */
	public ValueList selectListForIndividual(WebApplicationContext wc, CustomerForm customerForm, String pg,
			String pageSize) throws CustomerException {

		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals("")) {
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		}
		if (pageSize != null && !pageSize.equals("")) {
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		}
		if (customerForm.getCustomerInfoType() != null
				&& !customerForm.getCustomerInfoType().equals("")) {
			filters.put("customerInfoType", customerForm.getCustomerInfoType());
		}
		if (customerForm.getIndustryTypeCode() != null
				&& !customerForm.getIndustryTypeCode().equals("")) {
			filters.put("industryTypeCode", customerForm.getIndustryTypeCode());
		}
		if (customerForm.getBusinessTypeCode() != null
				&& !customerForm.getBusinessTypeCode().equals("")) {			
			if (customerForm.getBusinessTypeCode().equals("BTBC"))
				filters.put("BTBC", customerForm.getBusinessTypeCode());
			else if (customerForm.getBusinessTypeCode().equals("BTEF"))
				filters.put("BTEF", customerForm.getBusinessTypeCode());
			else if (customerForm.getBusinessTypeCode().equals("BTHI"))
				filters.put("BTHI", customerForm.getBusinessTypeCode());
			else
				filters.put("businessTypeCode", customerForm.getBusinessTypeCode());
		}
		if (customerForm.getWriter() != null && !customerForm.getWriter().equals("")) {
			filters.put("writer", customerForm.getWriter());
		}		
		if (customerForm.getFrom() != null && !customerForm.getFrom().equals("")) {
			filters.put("from", customerForm.getFrom());
		}
		if (customerForm.getTo() != null && !customerForm.getTo().equals("")) {
			filters.put("to", customerForm.getTo());
		}
		if (customerForm.getValueType() != null && !customerForm.getValueType().equals("")) {
			filters.put("valueType", customerForm.getValueType());
		}
		//if (customerForm.getIndustryTypeCode() != null && !customerForm.getIndustryTypeCode().equals("")) {
		//	filters.put("industryTypeCode", customerForm.getIndustryTypeCode());
		//}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("customerSelectListBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo(ValueListInfo.SORT_DIRECTION,
					ValueListInfo.DESCENDING);
			info.setFilters(filters);
			if (customerForm.getIndustryTypeCode().equals("A")) {
				valueList = vlh.getValueList("customerSelectListEntryForCIOTypeA", info);
			} else if (customerForm.getIndustryTypeCode().equals("B")) {
				valueList = vlh.getValueList("customerSelectListEntryForCIOTypeB", info);
			}else
				valueList = vlh.getValueList("customerSelectListEntryForIndividual", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
	
	/**
	 * 고객정보 상세조회 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		Map<String, String> filters = new HashMap<String, String>();
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		try {
			
			ValueListHandler vlh = (ValueListHandler) wc.getBean("customerSelectListBean",
					ValueListHandler.class);
			ValueListInfo info = new ValueListInfo(ValueListInfo.SORT_DIRECTION,
					ValueListInfo.DESCENDING);
			filters.put("idx", idx);
			info.setFilters(filters);
			
			ValueList valueList = vlh.getValueList("customerSelectListEntryForCIOTypeA", info);
			

			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("fileMode", "READ");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "customer_" + idx, null, null, null, null).getList());

		} catch (CustomerException e) {
			logger.error(e.getMessage(), e);

		}
		return mapping.findForward("customerSelectResult");
	}
}
