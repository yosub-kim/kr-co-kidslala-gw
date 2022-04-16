package kr.co.kmac.pms.project.statistics.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.exception.CodeException;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
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
 * @struts.action name="CustomerSatisfactionReportAction" path="/action/CustomerSatisfactionReportAction" parameter="mode" scope="request"
 * @struts.action-forward name="CustomerSatisfactionReportList" path="/project/statistics/CustomerSatisfactionReportList.jsp" redirect="false"
 * @struts.action-forward name="CustomerSatisfactionReportListToExcel" path="/project/statistics/CustomerSatisfactionReportListToExcel.jsp" redirect="false"
 * @struts.action-forward name="CustomerSatisfactionReportDetail" path="/project/statistics/CustomerSatisfactionReportDetail.jsp" redirect="false"
 * @struts.action-forward name="CustomerSatisfactionReportSummary" path="/project/statistics/CustomerSatisfactionReportSummary.jsp" redirect="false"
 * @struts.action-forward name="CustomerSatisfactionReportSummaryDetail" path="/project/statistics/CustomerSatisfactionReportSummaryDetail.jsp" redirect="false"
 */
public class CustomerSatisfactionReportAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(CustomerSatisfactionReportAction.class);

	public ActionForward getValueCustomerSatisfactionReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String projectName = request.getParameter("keyword");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String bizType = request.getParameter("bizType");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String groupCode = ServletRequestUtils.getStringParameter(request, "groupCode", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectName != null && !projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (startDate != null && !startDate.equals("")) {
				filters.put("realStartDate", startDate);
			}
			if (endDate != null && !endDate.equals("")) {
				filters.put("realendDate", endDate);
			}
			if (bizType != null && !bizType.equals("")) {
				filters.put("bizType", bizType);
			}
			if (groupCode != null && !groupCode.equals("")) {
				filters.put("groupCode", groupCode);
			}
			if (year != null && !year.equals("")) {
				filters.put("year", year+"%");
			}
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportList", info);
			request.setAttribute("result", valueList);

			request.setAttribute("keyword", projectName);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("bizType", bizType);
			request.setAttribute("year", year);
			request.setAttribute("groupCode", groupCode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportList");
	}
	
	public ActionForward getValueCustomerSatisfactionReportListToExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "10000");
		String projectName = request.getParameter("keyword");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String bizType = request.getParameter("bizType");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String groupCode = ServletRequestUtils.getStringParameter(request, "groupCode", "");
		
		String fileName = year+"년_프로젝트_고객만족도현황.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";");
		response.setHeader("Content-Description", "JSP Generated Data");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectName != null && !projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
			}
			if (startDate != null && !startDate.equals("")) {
				filters.put("realStartDate", startDate);
			}
			if (endDate != null && !endDate.equals("")) {
				filters.put("realendDate", endDate);
			}
			if (bizType != null && !bizType.equals("")) {
				filters.put("bizType", bizType);
			}
			if (groupCode != null && !groupCode.equals("")) {
				filters.put("groupCode", groupCode);
			}
			if (year != null && !year.equals("")) {
				filters.put("year", year);
			}
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportListToExcel", info);
			request.setAttribute("result", valueList);

			request.setAttribute("keyword", projectName);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.setAttribute("bizType", bizType);
			request.setAttribute("year", year);
			request.setAttribute("groupCode", groupCode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportListToExcel");
	}

	public ActionForward getValueCustomerSatisfactionReportDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String projectcode = request.getParameter("projectcode");
		String businessTypeCode = request.getParameter("businessTypeCode");
		//String processTypeCode = request.getParameter("processTypeCode");
		String seq = request.getParameter("seq");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectcode != null && !projectcode.equals("")) {
				filters.put("projectcode", projectcode);
			}
			if (seq != null && !seq.equals("") && !seq.equals("0")) {
				filters.put("seq", seq);
			}

			info.setFilters(filters);

			ValueList valueList = null;
			if (businessTypeCode.equals("N1") || businessTypeCode.equals("N2")) {
				valueList = vlh.getValueList("getValueCustomerSatisfactionReportDetailEdu", info);
			} else {
				valueList = vlh.getValueList("getValueCustomerSatisfactionReportDetail", info);
			}
			request.setAttribute("result", valueList);
			
			ValueList valueList2 = null; 
			if (businessTypeCode.equals("N2")) {
				valueList2 = vlh.getValueList("getValueCustomerSatisfactionReportDetailEdu2", info);
			} else {
				valueList2 = vlh.getValueList("getValueCustomerSatisfactionReportDetail2", info);
			}
			request.setAttribute("result2", valueList2);

			request.setAttribute("projectcode", projectcode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportDetail");
	}
	
	public ActionForward getValueCustomerSatisfactionReportSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (year != null && month != null) {
				String yearMonth = year;
				if (!month.equals(""))
					yearMonth = yearMonth + month;
				filters.put("yearMonth", yearMonth + "%");
			}
			if (runningDivCode != null && !runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}			
			if (runningDeptCode != null && !runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
			}
			if (businessTypeCode != null && !businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportSummary", info);
			
			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("businessTypeCode", businessTypeCode);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportSummary");
	}
	
	public ActionForward getValueCustomerSatisfactionReportSummary2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String ssn = SessionUtils.getUsername(request);
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (year != null && month != null) {
				String yearMonth = year;
				if (!month.equals(""))
					yearMonth = yearMonth + month;
				filters.put("yearMonth", yearMonth + "%");
			}
			if (runningDivCode != null && !runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}			
			if (runningDeptCode != null && !runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
			}
			if (businessTypeCode != null && !businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			filters.put("ssn", ssn);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportSummary2", info);
			
			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("businessTypeCode", businessTypeCode);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportSummary2");
	}
	
	public ActionForward getValueCustomerSatisfactionReportSummaryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String grade = ServletRequestUtils.getStringParameter(request, "grade", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (year != null && month != null) {
				String yearMonth = year;
				if (!month.equals(""))
					yearMonth = yearMonth + month;
				filters.put("yearMonth", yearMonth + "%");
			}
			if (runningDivCode != null && !runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}
			if (runningDeptCode != null && !runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
			}
			if (businessTypeCode != null && !businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (grade != null && !grade.equals("")) {
				if (grade.equals("high")) filters.put("high", grade);
				else if (grade.equals("medium")) filters.put("medium", grade);
				else filters.put("low", grade);					
			}
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportSummaryDetail", info);
			
			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("grade", grade);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportSummaryDetail");
	}
	
	public ActionForward getValueCustomerSatisfactionReportSummaryDetail2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pagingPage = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pagingNumberPer = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "");
		String grade = ServletRequestUtils.getStringParameter(request, "grade", "");
		String ssn = SessionUtils.getUsername(request);
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (year != null && month != null) {
				String yearMonth = year;
				if (!month.equals(""))
					yearMonth = yearMonth + month;
				filters.put("yearMonth", yearMonth + "%");
			}
			if (runningDivCode != null && !runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
			}
			if (runningDeptCode != null && !runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
			}
			if (businessTypeCode != null && !businessTypeCode.equals("")) {
				filters.put("businessTypeCode", businessTypeCode);
			}
			if (grade != null && !grade.equals("")) {
				if (grade.equals("high")) filters.put("high", grade);
				else if (grade.equals("medium")) filters.put("medium", grade);
				else filters.put("low", grade);					
			}
			filters.put("ssn", ssn);
			filters.put(ValueListInfo.PAGING_PAGE, pagingPage);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pagingNumberPer);

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportSummaryDetail2", info);
			
			request.setAttribute("result", valueList);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("runningDivCode", runningDivCode);
			request.setAttribute("runningDeptCode", runningDeptCode);
			request.setAttribute("businessTypeCode", businessTypeCode);
			request.setAttribute("grade", grade);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("CustomerSatisfactionReportSummaryDetail2");
	}
	
	public void getValueCustomerSatisfaction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("estimateValueListBean", ValueListHandler.class);
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("projectCode", projectCode);
			filters.put("seq", seq);
			info1.setFilters(filters);

			ValueList projectEstimateList = vlh1.getValueList("getProjectEstimate", info1);
			ValueList publicEduProjectEstimateList = vlh1.getValueList("getPublicEduProjectEstimate", info1);

			map.put("detailViewProEs", projectEstimateList.getList());
			map.put("detailViewPubEduProEs", publicEduProjectEstimateList.getList());
			AjaxUtil.successWrite(response, map);

		} catch (CodeException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}		
}
