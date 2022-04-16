package kr.co.kmac.pms.support.taxWork.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.support.taxWork.data.TaxWorkMobileData;
import kr.co.kmac.pms.support.taxWork.manager.TaxWorkManager;
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
 * Provide description for "BoardAction"
 * @author halberd
 * @version $Id: MobileBoardAction.java,v 1.9 2015/05/18 00:53:02 cvs Exp $
 */

/**
 * @struts.action name="TaxWorkAction" path="/action/MobileTaxWorkAction"
 *                parameter="mode" scope="request"
 * @struts.action-forward name="mobileSalaryList"
 *                        path="/m/web/support/salaryList.jsp" redirect="false"
 * @struts.action-forward name="mobileIncentiveList"
 *                        path="/m/web/support/incentiveList.jsp" redirect="false"
 * @struts.action-forward name="mobileBonusList"
 *                        path="/m/web/support/bonusList.jsp" redirect="false"
 * @struts.action-forward name="mobileSalaryView"
 *                        path="/m/web/support/salaryView.jsp" redirect="false"
 */
public class MobileTaxWorkAction extends DispatchActionSupport {
	
	private static final Log logger = LogFactory.getLog(MobileTaxWorkAction.class);
	private AttachManager attachManager;
	private ExpertPoolManager expertPoolManager;
	private TaxWorkManager taxWorkManager;
	
	public ActionForward selectSalaryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int pageSize		 = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String pg			 = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String empno		 = (String)request.getSession().getAttribute("empno");
		String start_date	 = ServletRequestUtils.getStringParameter(request, "start_date", "");
		String end_date		 = ServletRequestUtils.getStringParameter(request, "end_date", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("mobileSupportBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("empno", empno);
			filters.put("start_date", start_date);
			filters.put("end_date", end_date);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("mobileSalaryList", info);
			request.setAttribute("result", valueList);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("pg", pg);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("empno", empno);
		request.setAttribute("start_date", start_date);
		request.setAttribute("end_date", end_date);
		
		return mapping.findForward("mobileSalaryList");
	}
	
	public ActionForward selectIncentiveList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int pageSize		 = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String pg			 = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String empno		 = (String)request.getSession().getAttribute("empno");
		String jumin		 = "";
		String comp_code	 = "";
		String start_date	 = ServletRequestUtils.getStringParameter(request, "start_date", "");
		String end_date		 = ServletRequestUtils.getStringParameter(request, "end_date", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("mobileSupportBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			jumin = getTaxWorkManager().jumin(empno);
			comp_code = getTaxWorkManager().comp_code(empno);
			
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("empno", empno);
			filters.put("jumin", jumin);
			filters.put("comp_code", comp_code);
			filters.put("start_date", start_date);
			filters.put("end_date", end_date);
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("mobileIncentiveList", info);
			request.setAttribute("result", valueList);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("pg", pg);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("empno", empno);
		request.setAttribute("start_date", start_date);
		request.setAttribute("end_date", end_date);
		
		return mapping.findForward("mobileIncentiveList");
	}
	
	public ActionForward selectBonusList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");*/
		int pageSize		 = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String pg			 = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String pname		 = ServletRequestUtils.getStringParameter(request, "pname", "");
		String empno		 = (String)request.getSession().getAttribute("empno");
		String year			 = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month		 = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jumin		 = "";
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("mobileSupportBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			jumin = getTaxWorkManager().jumin(empno);
			jumin = jumin.substring(0, 6) + "-" + jumin.substring(6, 13);
		
			filters.put(ValueListInfo.PAGING_PAGE, pg);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize == -1 ? Integer.MAX_VALUE - 1 : pageSize));
			filters.put("empno", empno);
			filters.put("jumin", jumin);
			filters.put("year", year);
			filters.put("month", month);
			filters.put("pname", "%" + pname + "%");
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("mobileBonusList", info);
			request.setAttribute("result", valueList);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("pg", pg);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pname", pname);
		request.setAttribute("jumin", jumin);
		
		return mapping.findForward("mobileBonusList");
	}
	
	public ActionForward selectBonusPreList (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int pageSize		 = ServletRequestUtils.getIntParameter(request, "pageSize", 10);
		String pg			 = ServletRequestUtils.getStringParameter(request, "pg", "1");
		String ssn			 = SessionUtils.getUsername(request);
		
		int year 			 = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		int month			 = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		int day				 = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		
		String sYear = ""; String sMonth = "";
		
		if (day >= 1 && day <= 5) {
			if (month == 1) {
				month = 12;
				year = year - 1;
			} else {
				month = month - 1;
			}
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
			sYear = String.valueOf(year);
		} else {
			sYear = DateTime.getYear();
			sMonth = DateTime.getMonth();
		}
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("ssn", ssn);
			filters.put("year", sYear);
			filters.put("month", sMonth);
			info.setFilters(filters);
			
			ValueList valueList = vlh.getValueList("retrieveMMExpenseRealTimeResultDetail", info);
			
			request.setAttribute("result", valueList);
			request.setAttribute("year", sYear);
			request.setAttribute("month", sMonth);
			request.setAttribute("ssn", ssn);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	
		return mapping.findForward("mobileBonusPreList");
	}
	
	public ActionForward salaryView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String idx = ServletRequestUtils.getRequiredStringParameter(request, "idx");
		
		TaxWorkMobileData taxWorkMobileData = new TaxWorkMobileData();
		try {
			taxWorkMobileData = getTaxWorkManager().select(idx);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("result", taxWorkMobileData);
		
		return mapping.findForward("mobileSalaryView");
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public TaxWorkManager getTaxWorkManager() {
		return taxWorkManager;
	}

	public void setTaxWorkManager(TaxWorkManager taxWorkManager) {
		this.taxWorkManager = taxWorkManager;
	}
	
	

}
