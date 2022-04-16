package kr.co.kmac.pms.project.statistics.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.service.exception.ServiceException;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.statistics.data.ExpenseResultForCostOver;
import kr.co.kmac.pms.service.scheduler.dao.ExpenseErpDataServiceDao;
import kr.co.kmac.pms.service.scheduler.data.CostOverData;
import kr.co.kmac.pms.service.scheduler.data.ExpenseErpData;
import kr.co.kmac.pms.service.scheduler.manager.CostOverManager;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="ExpenseManagerAction" path="/action/ExpenseManagerAction" parameter="mode" scope="request"
 * @struts.action-forward name="expenseResultListForCheckCostOver" path="/project/statistics/expenseResultListForCheckCostOver.jsp" redirect="false"
 * @struts.action-forward name="expenseResultListForCheckCostOverDetail" path="/project/statistics/expenseResultListForCheckCostOverDetail.jsp" redirect="false"
 * @struts.action-forward name="expenseResultListForCheckCostOverDetailEtc" path="/project/statistics/expenseResultListForCheckCostOverDetailEtc.jsp" redirect="false"
 * @struts.action-forward name="expenseResultListForCheckCostOverRA" path="/project/statistics/expenseResultListForCheckCostOverRA.jsp" redirect="false"
 * @struts.action-forward name="salaryMailingList" path="/project/statistics/salaryMailingList.jsp" redirect="false"
 * @struts.action-forward name="saveSalaryMailingListToExcel" path="/project/statistics/saveSalaryMailingListToExcel.jsp" redirect="false" 
 */
public class ExpenseManagerAction extends DispatchActionSupport {
	private ExpenseErpDataServiceDao expenseErpDataServiceDao;
	private CostOverManager costOverManager;

	public ActionForward getValueProjectReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String isCostOver = ServletRequestUtils.getStringParameter(request, "isCostOver", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");
		String expenseMode = ServletRequestUtils.getStringParameter(request, "expenseMode", "");

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		if (isCostOver != null && !isCostOver.equals("")) {
			filters.put("costOver", isCostOver);
		}
		if (dept != null && !dept.equals("")) {
			filters.put("dept", dept);
		}
		if (projectName != null && !projectName.equals("")) {
			filters.put("projectName", "%" + projectName + "%");
		}
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		String sMonth = "";
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
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
		} else {
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
		}
		filters.put("searchMonth", String.valueOf(year) + sMonth + "01");
		filters.put("year", String.valueOf(year));
		filters.put("month", sMonth);
		

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLaborSalesCostResultListForCheckCostOver", info);
			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("dept", dept);
			request.setAttribute("isCostOver", isCostOver);
			request.setAttribute("expenseMode", expenseMode);
			request.setAttribute("searchMonth", String.valueOf(year) + sMonth);
			request.setAttribute("year", String.valueOf(year));
			request.setAttribute("month", sMonth);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("expenseResultListForCheckCostOver");
	}

	public ActionForward getValueProjectReportListDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebApplicationContext wc;

		String searchMonth = ServletRequestUtils.getRequiredStringParameter(request, "searchMonth");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String acctCode = ServletRequestUtils.getRequiredStringParameter(request, "acctCode");

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put("searchMonth", searchMonth+"__");
		filters.put("year", searchMonth.substring(0, 4));
		filters.put("month", searchMonth.substring(4, 6));
		filters.put("projectCode", projectCode);
		
		String strValueList = "getLaborSalesCostResultListForCheckCostOverDetail";
		String targetForword = "expenseResultListForCheckCostOverDetail";
		if (acctCode.equals("D")) {
			strValueList = "getLaborSalesCostResultListForCheckCostOverDetailEtc";
			targetForword = "expenseResultListForCheckCostOverDetailEtc";
		} else if (acctCode.equals("A")) {
			filters.put("jobClassA", "A");
		} else {
			filters.put("jobClassB", "C', 'J");
		}

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList(strValueList, info);
			
			ValueListInfo info2 = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info2.setFilters(filters);
			ValueList valueList2 = vlh.getValueList("getLaborSalesCostResultListForCheckCostOverDetailMM", info2);
			request.setAttribute("list2", valueList2);
			int r2 = valueList2.getValueListInfo().getTotalNumberOfEntries();
			request.setAttribute("list2Records", r2);

			request.setAttribute("list", valueList);
			int rs1 = valueList.getValueListInfo().getTotalNumberOfEntries();
			request.setAttribute("listRecords", rs1);
			request.setAttribute("searchMonth", searchMonth);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("acctCode", acctCode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward(targetForword);
	}

	public ActionForward getValueProjectReportListRA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String isCostOver = ServletRequestUtils.getStringParameter(request, "isCostOver", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");
		String expenseMode = ServletRequestUtils.getStringParameter(request, "expenseMode", "");

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		if (isCostOver != null && !isCostOver.equals("")) {
			filters.put("costOver", isCostOver);
		}
		if (dept != null && !dept.equals("")) {
			filters.put("dept", dept);
		}
		if (projectName != null && !projectName.equals("")) {
			filters.put("projectName", "%" + projectName + "%");
		}

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLaborSalesCostResultListForCheckCostOverRA", info);
			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("dept", dept);
			request.setAttribute("isCostOver", isCostOver);
			request.setAttribute("expenseMode", expenseMode);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("expenseResultListForCheckCostOverRA");
	}

	public ActionForward selectExpenseTeachingFee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;

		String isCostOver = ServletRequestUtils.getStringParameter(request, "isCostOver", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String dept = ServletRequestUtils.getStringParameter(request, "dept", "");
		String expenseMode = ServletRequestUtils.getStringParameter(request, "expenseMode", "");
		String searchOK = ServletRequestUtils.getStringParameter(request, "searchOK", "0");

		if (searchOK.equals("0")) {
			dept = "6010";
		}

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		if (isCostOver != null && !isCostOver.equals("")) {
			filters.put("costOver", isCostOver);
		}
		if (dept != null && !dept.equals("")) {
			filters.put("dept", dept);
		}
		if (projectName != null && !projectName.equals("")) {
			filters.put("projectName", "%" + projectName + "%");
		}
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		String sMonth = "";
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
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
			day = StringUtil.getMonthlyDayCount(year, month);
			filters.put("today", String.valueOf(year) + sMonth + String.valueOf(day));
			filters.put("startedDay", String.valueOf(year) + sMonth + "01");
			filters.put("addedDay", String.valueOf(year) + sMonth + String.valueOf(day));
		} else {
			filters.put("today", StringUtil.getCurr("yyyyMMdd"));
			filters.put("startedDay", StringUtil.getCurr("yyyyMM") + "01");
			int dayCnt = StringUtil.getMonthlyDayCount(year, month);
			if (day <= 20)
				day = day + 10;
			else
				day = dayCnt;
			filters.put("addedDay", StringUtil.getCurr("yyyyMM") + String.valueOf(day));
		}

		// Job Date: 2008-12-19 Author: yhyim Description: 성과급 현황과 인건비매출 현황 구분
		String valueListType = "";
		if (expenseMode.equals("incentive"))
			valueListType = "getExpenseResultListForCheckCostOver";
		else
			valueListType = "getLaborSalesCostResultListForCheckCostOver";

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList(valueListType, info);
			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("dept", dept);
			request.setAttribute("isCostOver", isCostOver);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("expenseResultListForCheckCostOver");
	}

	/**
	 * 프로젝트 성과급 현황 회계 데이터 갱신
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void refreshErpData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// String jobClass = ServletRequestUtils.getRequiredStringParameter(request, "jobClass");
		try {
			List<ExpenseErpData> budgetList = getExpenseErpDataServiceDao().getExpenseDataFromErpFullData();
			getExpenseErpDataServiceDao().setExpenseDataToPms(budgetList);
			log.info("데이터를 갱신하였습니다.");
		} catch (Exception e) {
			log.error("Exception occured during execut ErpScheduleService ....");
		}

		WebApplicationContext wc;

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put("costOver", "Y");
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		String sMonth = "";
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
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
		} else {
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
		}
		filters.put("searchMonth", String.valueOf(year) + sMonth + "01");
		filters.put("year", String.valueOf(year));
		filters.put("month", sMonth);

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			List<ExpenseResultForCostOver> list = vlh.getValueList("getLaborSalesCostOverList", info).getList();

			this.getExpenseErpDataServiceDao().updateProjectReportExceedStateInit();
			this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedStateInit();
			this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedStateInit();

			if (list != null && list.size() > 0) {
				for (ExpenseResultForCostOver expenseResultForCostOver : list) {
					// 인건비가 초과된 프로젝트 지도일지 초과 여부 필드 업데이트
					this.getExpenseErpDataServiceDao().updateProjectReportExceedState(expenseResultForCostOver.getProjectCode());
					this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedState(expenseResultForCostOver.getProjectCode());
					this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedState(expenseResultForCostOver.getProjectCode());
				}
			}
			log.info("## ExpenseErpScheduleService was executed...");
			
			
			try {
				// get list of cost over project from Accounting System Database
				List<CostOverData> totalList = this.getCostOverManager().costOverList();
				List<CostOverData> payList = this.getCostOverManager().payCostOverList();
				List<CostOverData> etcList = this.getCostOverManager().etcCostOverList();

				// reset the information of cost over in Project master table
				this.getCostOverManager().resetUpdateProjectCostOver();

				// update the information of cost over in Project master table
				if (totalList != null && totalList.size() > 0) {
					for (CostOverData costOverData : totalList) {
						this.getCostOverManager().updateProjectCostOver(costOverData.getProjId());
					}
				}
				// Job Date: 2007-10-16 Author: yhyim Description: 인건비 초과 여부 처리
				if (payList != null && payList.size() > 0) {
					for (CostOverData costOverData : payList) {
						this.getCostOverManager().updateProjectPayCostOver(costOverData.getProjId());
					}
				}
				// Job Date: 2007-10-16 Author: yhyim Description: 제경비 초과 여부 처리
				if (etcList != null && etcList.size() > 0) {
					for (CostOverData costOverData : etcList) {
						this.getCostOverManager().updateProjectEtcCostOver(costOverData.getProjId());
					}
				}
				log.info("##  CostOverService was executed... ");
			} catch (ServiceException e) {
				log.info("##  CostOverService was failed... ");
				log.error(e.getMessage(), e);
			}
			
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * 프로젝트 성과급 현황 회계 데이터 갱신
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void refreshErpDataSpcProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;

		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		filters.put("costOver", "Y");
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		String sMonth = "";
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
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
		} else {
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
		}
		filters.put("searchMonth", String.valueOf(year) + sMonth + "01");
		filters.put("year", String.valueOf(year));
		filters.put("month", sMonth);
		filters.put("projectCode", projectCode);

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			List<ExpenseResultForCostOver> list = vlh.getValueList("getLaborSalesCostOverList", info).getList();

			this.getExpenseErpDataServiceDao().updateProjectReportExceedStateSpcProjectInit(projectCode);
			this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedStateSpcProjectInit(projectCode);
			this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedStateSpcProjectInit(projectCode);

			if (list != null && list.size() > 0) {
				for (ExpenseResultForCostOver expenseResultForCostOver : list) {
					// 인건비가 초과된 프로젝트 지도일지 초과 여부 필드 업데이트
					this.getExpenseErpDataServiceDao().updateProjectReportExceedState(expenseResultForCostOver.getProjectCode());
					this.getExpenseErpDataServiceDao().updateProjectTeachingFeeExceedState(expenseResultForCostOver.getProjectCode());
					this.getExpenseErpDataServiceDao().updateProjectTeachingRestFeeExceedState(expenseResultForCostOver.getProjectCode());
				}
			}
			log.info("## ExpenseErpScheduleService was executed...");
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
		}
	}
	
	public ActionForward getSalaryMailingList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		//String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String mailingYN = ServletRequestUtils.getStringParameter(request, "mailingYN", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", "");
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			//filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			//filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			if (!mailingYN.equals("")) {
				filters.put("mailingYN", mailingYN);
			}
			
			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			
			if (!year.equals("") &!month.equals("")) {
				filters.put("year", year);
				filters.put("month", month);
			} else {
				int tempDay = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
				int tempMonth = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
				int tempYear = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
				if (tempDay >= 1 && tempDay <= 5) {
					if (tempMonth == 1) {
						tempMonth = 12;
						tempYear = tempYear - 1;
						month = String.valueOf(tempMonth);
					} else {
						tempMonth = tempMonth - 1;
						month = String.valueOf(tempMonth);
					}
					if (String.valueOf(tempMonth).length() == 1) {
						month = "0" + String.valueOf(tempMonth);
					} else {
						month = String.valueOf(tempMonth);
					}
				} else {
					if (String.valueOf(tempMonth).length() == 1) {
						month = "0" + String.valueOf(tempMonth);
					} else {
						month = String.valueOf(tempMonth);
					}
				}
				year = String.valueOf(tempYear);
				filters.put("year", year);
				filters.put("month", month);
			}
			
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getSalaryMailingList", info);

			request.setAttribute("list", valueList);
			request.setAttribute("name", name);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("mailingYN", mailingYN);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("salaryMailingList");
	}
	
	public ActionForward saveSalaryMailingListToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String mailingYN = ServletRequestUtils.getStringParameter(request, "mailingYN", "");
		
		String fileName = year + "년" + month + "월_성과급 안내메일_발송대상자_리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
	    
	    try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (!mailingYN.equals("")) {
				filters.put("mailingYN", mailingYN);
			}
			
			if (!name.equals("")) {
				filters.put("name", "%" + name + "%");
			}
			
			if (!year.equals("") &!month.equals("")) {
				filters.put("year", year);
				filters.put("month", month);
			}
			
			info.setFilters(filters);

			ValueList result = vlh.getValueList("getSalaryMailingList", info);
			request.setAttribute("list", result);
			
	    } catch (Exception e) {
	    	log.error(e.getMessage(), e);
	    }
		
		return mapping.findForward("saveSalaryMailingListToExcel");
	}

	public ExpenseErpDataServiceDao getExpenseErpDataServiceDao() {
		return expenseErpDataServiceDao;
	}

	public void setExpenseErpDataServiceDao(ExpenseErpDataServiceDao expenseErpDataServiceDao) {
		this.expenseErpDataServiceDao = expenseErpDataServiceDao;
	}
	
	public CostOverManager getCostOverManager() {
		return costOverManager;
	}

	public void setCostOverManager(CostOverManager costOverManager) {
		this.costOverManager = costOverManager;
	}

}
