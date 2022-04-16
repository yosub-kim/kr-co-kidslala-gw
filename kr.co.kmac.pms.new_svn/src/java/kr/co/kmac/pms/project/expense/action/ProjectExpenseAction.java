package kr.co.kmac.pms.project.expense.action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.expense.form.ExpenseForm;
import kr.co.kmac.pms.project.expense.manager.ExpenseManager;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
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
 * @struts.action name="projectExpenseAction" path="/action/ProjectExpenseAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectExpenseManagement" path="/project/expense/projectExpenseManagement.jsp" redirect="false"
 * @struts.action-forward name="realTimeProjectExpenseManagement" path="/project/expense/realTimeProjectExpenseManagement.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseHistoryManagement" path="/project/expense/projectExpenseHistoryManagement.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseResultListForCheckCostOver" path="/project/expense/projectExpenseResultListForCheckCostOver.jsp" redirect="false"  
 */
public class ProjectExpenseAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectExpenseAction.class);
	private ExpenseManager expenseManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;

	//상태 별 성과급 화면(200102)
	public ActionForward getExpertPoolWorkPeriodList_repodetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		Calendar c = new GregorianCalendar();
		String nYear = Integer.toString(c.get(Calendar.YEAR));
		
		// 12월, 1월 체크
		String nMonth = Integer.toString(c.get(Calendar.MONTH) + 1);

		//nMonth = nMonth.substring(nMonth.length() - 2, nMonth.length());
		// nMonth 살리고 12월 지우기 (테스트 12월)

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("projectCode", projectCode);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getExpertPoolWorkPeriodList_repodetail", info);

			request.setAttribute("list", valueList);
			request.setAttribute("nYear", nYear);
			request.setAttribute("nMonth", nMonth);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("expertPoolWorkPeriodList_repodetail");
	}
	
	// 프로젝트 과거 성과급 확인(200318)
	public ActionForward getViewProjectSalary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("projectCode", projectCode);
			
			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getViewProjectSalary", info);
			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("viewProjectSalaryDetail");
	}
	
	
	public ActionForward projectExpenseManagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String projectTypeCode = ServletRequestUtils.getRequiredStringParameter(request, "projectTypeCode");

		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		
		String sYear = "";
		String sMonth = "";
		
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
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("projectCode", projectCode);
			filters.put("assignDate", sYear + sMonth + "__");
			filters.put("year", sYear);
			filters.put("month", sMonth);
			info.setFilters(filters);
			ValueList puRealTimeSalary = vlh.getValueList("retrieveExpenseRealTimeResultForProject", info);
			ValueList restSalaryAmount = vlh.getValueList("retrieveRestSalaryAmountForProject", info);
			ValueList incentiveAmount = vlh.getValueList("retrieveIncentiveSalaryAmountForProject", info);
			if (projectTypeCode.equals("MM")) {
				// MM형 - 상근/상임/엑스퍼트 멤버 리스트
				request.setAttribute("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
				
				// MM형 - 상근/상임/엑스퍼트  성과급 입력
				request.setAttribute("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, sYear, sMonth));
				
				// MM형 - 상근/상임/엑스퍼트 투입일정 가저오기(사용x)
				//request.setAttribute("puSchedule", this.getExpenseManager().getScheduleDetailListPU(projectCode, sYear, sMonth ));*/
				
				// MM형 - 주간진척관리 카운트
				request.setAttribute("puScheduleReport", this.getExpenseManager().getScheduleReportDetail(projectCode, sYear, sMonth ));
				
				// MM형 - RA/산업계강사/대학교수 멤버 리스트
				request.setAttribute("buMember2", this.getExpenseManager().getExpenseProjectMember2(projectCode));
				
				// MM형 - RA/산업계강사/대학교수 멤버 리스트
				request.setAttribute("buSalary2", this.getExpenseManager().getExpenseDetailList2(projectCode, sYear, sMonth));
				
				// MM형 - 프로젝트 인건비 예산 정보
				request.setAttribute("restSalary", restSalaryAmount.getList());
				
				// MM형 - 프로젝트 인센티브 예산 정보
				request.setAttribute("incentive", incentiveAmount.getList());
				
				// MM형 - 성과급 100% 금액
				request.setAttribute("salaryHistory", this.getExpenseManager().getProjectExpenseHistory_mmplan(projectCode));
				
				// MM형 - 성과급 20% 금액
			/*	request.setAttribute("salaryHistory_rest", this.getExpenseManager().getProjectExpenseHistory_rest(projectCode));*/
				
			} else {
				// MD형 - 전체 멤버 리스트
				request.setAttribute("buMember", this.getExpenseManager().getExpenseProjectMember(projectCode));
				request.setAttribute("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
				
				// MD형 - 성과급 입력
				request.setAttribute("buSalary", this.getExpenseManager().getExpenseDetailList(projectCode, sYear, sMonth));
				request.setAttribute("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, sYear, sMonth));
				
				// MD형 - 실시간 성과급 조회(사용x)
				request.setAttribute("puRealTimeSalary", puRealTimeSalary.getList());
				
				// ND형 - 프로젝트 레포트 여부 확인
				request.setAttribute("puMonthlyReport", this.getExpenseManager().getMonthlyReportDetail(projectCode, sYear, sMonth ));
				
				// ND형 - 성과급 100% 금액
				request.setAttribute("salaryHistory", this.getExpenseManager().getProjectExpenseHistory(projectCode));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		request.setAttribute("projectTypeCode", projectTypeCode);

		return mapping.findForward("projectExpenseManagement");
	}
	
	public ActionForward realTimeProjectExpenseManagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		
		String sYear = "";
		String sMonth = "";
		
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
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth + "__");
			filters.put("assignDate1", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("06CB") 
					|| expertPool.getCompanyPosition().equals("05CC") ) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDivCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("09CJ") 
					|| expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("realTimeProjectExpense", info);
			
			// 20% 실시간 성과급 확인
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters2.put("assignDate", sYear + sMonth + "__");
			filters2.put("assignDate1", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("06CB") 
					|| expertPool.getCompanyPosition().equals("05CC") ) {
				filters2.put("AR", SessionUtils.getUsername(request));
				filters2.put("runningDivCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("09CJ") 
					|| expertPool.getCompanyPosition().equals("08CF")) {
				filters2.put("AR", SessionUtils.getUsername(request));
				filters2.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters2.put("PM", SessionUtils.getUsername(request));
				filters2.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info2.setFilters(filters2);
			ValueList result_20 = vlh.getValueList("realTimeProjectExpense_20", info2);
			
			request.setAttribute("list", result);
			request.setAttribute("list2", result_20);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("realTimeProjectExpenseManagement");
	}

	public ActionForward realTimeProjectExpenseManagement2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		
		String sYear = "";
		String sMonth = "";
		
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
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth + "__");
			filters.put("assignDate1", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("06CB") 
					|| expertPool.getCompanyPosition().equals("05CC") ) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDivCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("09CJ") 
					|| expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("realTimeProjectExpense2", info);
			request.setAttribute("list", result);
			
			// 20% 실시간 성과급 지급
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters2.put("assignDate", sYear + sMonth + "__");
			filters2.put("assignDate1", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("06CB") 
					|| expertPool.getCompanyPosition().equals("05CC") ) {
				filters2.put("AR", SessionUtils.getUsername(request));
				filters2.put("runningDivCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("09CJ") 
					|| expertPool.getCompanyPosition().equals("08CF")) {
				filters2.put("AR", SessionUtils.getUsername(request));
				filters2.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters2.put("PM", SessionUtils.getUsername(request));
				filters2.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info2.setFilters(filters2);
			ValueList result2 = vlh.getValueList("realTimeProjectExpense2_20", info2);
			request.setAttribute("list2", result2);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("realTimeProjectExpenseManagement2");
	}
	
	public ActionForward projectExpenseHistoryManagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistory", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("projectExpenseHistoryManagement");
	}
	
	public ActionForward projectExpenseHistoryManagement_20(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistory_20", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("projectExpenseHistoryManagement_20");
	}
	
	// COO 성과급 화면
	public ActionForward projectExpenseHistoryManagement_coo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistory_coo", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("projectExpenseHistoryManagement_coo");
	}
	
	public ActionForward projectExpenseHistoryManagement2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistory2", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("projectExpenseHistoryManagement2");
	}
	
	// COO 성과급 화면
	public ActionForward projectExpenseHistoryManagement2_coo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistory2_coo", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		
		return mapping.findForward("projectExpenseHistoryManagement2_coo");
	}
	
	public ActionForward projectExpenseHistoryManagement2_20(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		int year = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4)));
		int month = Integer.parseInt(ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6)));
		
		String sYear = String.valueOf(year);
		String sMonth = "";

		if (String.valueOf(month).length() == 1) {
			sMonth = "0" + String.valueOf(month);
		} else {
			sMonth = String.valueOf(month);
		}
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("assignDate", sYear + sMonth);
			
			if (expertPool.getCompanyPosition().equals("09CJ") || expertPool.getCompanyPosition().equals("08CF")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
				request.setAttribute("role", "AR");
			} else if (expertPool.getCompanyPosition().equals("10TM")) {
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "AR");
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				request.setAttribute("role", "PM");
			}
			info.setFilters(filters); 
			ValueList result = vlh.getValueList("projectExpenseHistory2_20", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		 
		return mapping.findForward("projectExpenseHistoryManagement2_20");
	}
	
	// 결산세부정보
	public ActionForward projectExpenseHistoryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {                         
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode","");
                          
		try {       
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo)); 
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			 
			filters.put("projectCode", projectCode);
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectExpenseHistoryDetail", info);
			request.setAttribute("list", result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return mapping.findForward("projectExpenseHistoryDetail");
	}
	
	public ActionForward getLaborSalesCostResultListForCheckCostOverIndividual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WebApplicationContext wc;
		
		String isCostOver = ServletRequestUtils.getStringParameter(request, "isCostOver", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String expenseMode = ServletRequestUtils.getStringParameter(request, "expenseMode", "");
		
		Map<String, String> filters = new HashMap<String, String>();
		filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
		filters.put(ValueListInfo.PAGING_PAGE, "1");
		if (isCostOver != null && !isCostOver.equals("")) {
			filters.put("costOver", isCostOver);
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
		filters.put("searchMonth", String.valueOf(year) + sMonth + "__");
		filters.put("year", String.valueOf(year));
		filters.put("month", sMonth);
		
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		if (expertPool.getCompanyPosition().equals("05CC")) {	// CCO
			filters.put("AR", SessionUtils.getUsername(request));			
		} else if (expertPool.getCompanyPosition().equals("06CB")) {	// CBO
			filters.put("AR", SessionUtils.getUsername(request));
			filters.put("runningDivCode", expertPool.getDept());
		} else if (expertPool.getCompanyPosition().equals("09CJ") 
				|| expertPool.getCompanyPosition().equals("08CF")) {	// COO
			filters.put("AR", SessionUtils.getUsername(request));
			filters.put("ssn", SessionUtils.getUsername(request));
			request.setAttribute("role", "AR");
		} else {
			filters.put("PM", SessionUtils.getUsername(request));
			filters.put("ssn", SessionUtils.getUsername(request));
			request.setAttribute("role", "PM");
		}
		
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("getValueProjectReportList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getLaborSalesCostResultListForCheckCostOverIndividual", info);
			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("isCostOver", isCostOver);
			request.setAttribute("expenseMode", expenseMode);
			request.setAttribute("searchMonth", String.valueOf(year) + sMonth);
			request.setAttribute("year", String.valueOf(year));
			request.setAttribute("month", sMonth);
		
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseResultListForCheckCostOver");
	}
	
	public ActionForward projectExpenseHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String projectTypeCode = ServletRequestUtils.getRequiredStringParameter(request, "projectTypeCode");

		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		
		String sYear = "";
		String sMonth = "";
		
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
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("projectCode", projectCode);
			filters.put("assignDate", sYear + sMonth + "__");
			filters.put("year", sYear);
			filters.put("month", sMonth);
			info.setFilters(filters);
			ValueList puRealTimeSalary = vlh.getValueList("retrieveExpenseRealTimeResultForProject", info);
			ValueList restSalaryAmount = vlh.getValueList("retrieveRestSalaryAmountForProject", info);
			ValueList incentiveAmount = vlh.getValueList("retrieveIncentiveSalaryAmountForProject", info);
			
			if (projectTypeCode.equals("MM")) {
				// MM형 - 상근/상임/엑스퍼트 멤버 리스트
				request.setAttribute("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
				
				// MM형 - 상근/상임/엑스퍼트  성과급 입력
				request.setAttribute("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, sYear, sMonth));
				
				// MM형 - 상근/상임/엑스퍼트 투입일정 가저오기(사용x)
				//request.setAttribute("puSchedule", this.getExpenseManager().getScheduleDetailListPU(projectCode, sYear, sMonth ));*/
				
				// MM형 - 주간진척관리 카운트
				request.setAttribute("puScheduleReport", this.getExpenseManager().getScheduleReportDetail(projectCode, sYear, sMonth ));
				
				// MM형 - RA/산업계강사/대학교수 멤버 리스트
				request.setAttribute("buMember2", this.getExpenseManager().getExpenseProjectMember2(projectCode));
				
				// MM형 - RA/산업계강사/대학교수 멤버 리스트
				request.setAttribute("buSalary2", this.getExpenseManager().getExpenseDetailList2(projectCode, sYear, sMonth));
				
				// MM형 - 프로젝트 인건비 예산 정보
				request.setAttribute("restSalary", restSalaryAmount.getList());
				
				// MM형 - 프로젝트 인센티브 예산 정보
				request.setAttribute("incentive", incentiveAmount.getList());
				
				// MM형 - 성과급 100% 금액
				request.setAttribute("salaryHistory", this.getExpenseManager().getProjectExpenseHistory_mmplan(projectCode));
				
				// MM형 - 성과급 20% 금액
				//request.setAttribute("salaryHistory_rest", this.getExpenseManager().getProjectExpenseHistory_rest(projectCode));
				
			} else {
				// MD형 - 전체 멤버 리스트
				request.setAttribute("buMember", this.getExpenseManager().getExpenseProjectMember(projectCode));
				request.setAttribute("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
				
				// MD형 - 성과급 입력
				request.setAttribute("buSalary", this.getExpenseManager().getExpenseDetailList(projectCode, sYear, sMonth));
				request.setAttribute("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, sYear, sMonth));
				
				// MD형 - 실시간 성과급 조회(사용x)
				request.setAttribute("puRealTimeSalary", puRealTimeSalary.getList());
				
				// ND형 - 프로젝트 레포트 여부 확인
				request.setAttribute("puMonthlyReport", this.getExpenseManager().getMonthlyReportDetail(projectCode, sYear, sMonth ));
				
				// ND형 - 성과급 100% 금액
				request.setAttribute("salaryHistory", this.getExpenseManager().getProjectExpenseHistory(projectCode));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("viewMode", viewMode);
		request.setAttribute("year", sYear);
		request.setAttribute("month", sMonth);
		request.setAttribute("projectTypeCode", projectTypeCode);		

		return mapping.findForward("projectExpenseManagement");
	}

	public void insertProjectExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ExpenseForm expenseForm = (ExpenseForm) form;
		try {
			this.getExpenseManager().insertProjectExpense(expenseForm);
			map.put("buMember", this.getExpenseManager().getExpenseProjectMember(expenseForm.getProjectCode()));
			map.put("buSalary", this.getExpenseManager().getExpenseDetailList(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void insertProjectExpensePU(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ExpenseForm expenseForm = (ExpenseForm) form;
		try {
			this.getExpenseManager().insertProjectExpensePU(expenseForm);
			map.put("puMember", this.getExpenseManager().getExpenseProjectMemberPU(expenseForm.getProjectCode()));
			map.put("puSalary", this.getExpenseManager().getExpenseDetailListPU(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void insertProjectExpensePU2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ExpenseForm expenseForm = (ExpenseForm) form;
		try {
			this.getExpenseManager().insertProjectExpensePU2(expenseForm);
			map.put("puMember", this.getExpenseManager().getExpenseProjectMemberPU(expenseForm.getProjectCode()));
			map.put("puSalary", this.getExpenseManager().getExpenseDetailListPU(expenseForm.getProjectCode(), expenseForm.getYear(), expenseForm.getMonth()));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void createProjectRestExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", "");
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		String chargeSsn = ServletRequestUtils.getStringParameter(request, "chargeSsn", "");
		String restAmount = ServletRequestUtils.getStringParameter(request, "restAmount", "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (!projectCode.equals("") && !year.equals("") && !month.equals("") && !chargeSsn.equals(""))
				this.getExpenseManager().createProjectRestExpense(projectCode, year, month, chargeSsn, restAmount);
			map.put("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
			map.put("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, year, month));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void deleteProjectRestExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", "");
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		String chargeSsn = ServletRequestUtils.getStringParameter(request, "chargeSsn", "");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (!projectCode.equals("") && !year.equals("") && !month.equals("") && !chargeSsn.equals(""))
				this.getExpenseManager().deleteProjectRestExpense(projectCode, year, month, chargeSsn);
			map.put("puMember", this.getExpenseManager().getExpenseProjectMemberPU(projectCode));
			map.put("puSalary", this.getExpenseManager().getExpenseDetailListPU(projectCode, year, month));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}	

	public void deleteProjectExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		int seq = ServletRequestUtils.getRequiredIntParameter(request, "seq");
		try {
			this.getExpenseManager().deleteProjectExpense(projectCode, year, month, ssn, seq);
			//mmplan 주석
			//this.getExpenseManager().deleteProjectMMPlan(projectCode, year, month, ssn);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void deleteProjectExpense2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		int seq = ServletRequestUtils.getRequiredIntParameter(request, "seq");
		
		System.out.println(projectCode +"-"+ year +"-"+ month +"-"+ ssn +"-"+ seq);
		try {
			this.getExpenseManager().deleteProjectExpense2(projectCode, year, month, ssn, seq);
			//mmplan 주석
			//this.getExpenseManager().deleteProjectMMPlan(projectCode, year, month, ssn);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void deleteProjectSalary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String chargeSsn = ServletRequestUtils.getRequiredStringParameter(request, "chargeSsn");
		
		try {
			this.getExpenseManager().deleteProjectSalary(projectCode, year, month, chargeSsn);
			this.getExpenseManager().deleteProjectRestSalary(projectCode, year, month, chargeSsn);
			this.getExpenseManager().deleteProjectMMPlan(projectCode, year, month, chargeSsn);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void projectExpenseView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		request.setAttribute("projectCode", projectCode);
	}

	/**
	 * @return the expenseManager
	 */
	public ExpenseManager getExpenseManager() {
		return expenseManager;
	}

	/**
	 * @param expenseManager the expenseManager to set
	 */
	public void setExpenseManager(ExpenseManager expenseManager) {
		this.expenseManager = expenseManager;
	}

	/**
	 * @return the projectMasterInfoManager
	 */
	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	/**
	 * @param projectMasterInfoManager the projectMasterInfoManager to set
	 */
	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}
	
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
