package kr.co.kmac.pms.project.statistics.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
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
 * @struts.action name="RealTimeProjectExpenseAction" path="/action/RealTimeProjectExpenseAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectExpenseRealTimeResult" path="/project/statistics/projectExpenseRealTimeResult.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseRealTimeResultByExpertGroup" path="/project/statistics/projectExpenseRealTimeResultByExpertGroup.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseRealTimeResultForDivManager" path="/project/statistics/projectExpenseRealTimeResultForDivManager.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseRealTimeResultByProjectBase" path="/project/statistics/projectExpenseRealTimeResultByProjectBase.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseRealTimeResultDetail" path="/project/statistics/projectExpenseRealTimeResultDetail.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseRealTimeResultDetail2" path="/project/statistics/projectExpenseRealTimeResultDetail2.jsp" redirect="false"
 * @struts.action-forward name="saveListToExcel" path="/project/statistics/saveListToExcel.jsp" redirect="false"
 * @struts.action-forward name="saveDetailListToExcel" path="/project/statistics/saveDetailListToExcel.jsp" redirect="false"
 * @struts.action-forward name="saveDetailListGroupByPjtToExcel" path="/project/statistics/saveDetailListGroupByPjtToExcel.jsp" redirect="false"
 * @struts.action-forward name="projectExpenseContributionCostRealTimeResultDetail"
 *                        path="/project/statistics/projectExpenseContributionCostRealTimeResultDetail.jsp" redirect="false"
 */
public class RealTimeProjectExpenseAction extends RepositoryDispatchActionSupport {
	private static final Log logger = LogFactory.getLog(RealTimeProjectExpenseAction.class);
	private ExpertPoolManager expertPoolManager;

	/**
	 * 실시간 강사료 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrieveExpenseRealTimeResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String readOnly = ServletRequestUtils.getStringParameter(request, "readOnly", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (deptCode.length() > 0) {
				if (jobClass.equals("B"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			/*filters.put("assignDate", year + month + "__");*/
			filters.put("year", year);
			filters.put("month", month); 

			info.setFilters(filters); 

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResult", info);
			request.setAttribute("list", result); 

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResult");
	}

	/**
	 * 실시간 강사료 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrieveExpenseRealTimeResult2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "300");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String readOnly = ServletRequestUtils.getStringParameter(request, "readOnly", "");
		String sessionDept = getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getDept();

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (deptCode.length() > 0) {
				if (jobClass.equals("A"))
					filters.put("deptCode", deptCode.substring(0, 3) + "_");
				else
					filters.put("deptCode", deptCode);
			}
			/*filters.put("assignDate", year + month + "__");*/
			filters.put("year", year);
			filters.put("month", month);
			filters.put("sessionDept", sessionDept);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResult2", info);
			request.setAttribute("list", result);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResult2");
	}

	/**
	 * 본부별 실시간 강사료 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrieveExpenseRealTimeResultForDivManager(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String deptCode = getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getDept();

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));

			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!name.equals("")) {
				filters.put("name", name);
			}
			if (deptCode.length() > 0) {
				filters.put("deptCode", deptCode);
			}
			/*filters.put("assignDate", year + month + "__");*/
			filters.put("year", year);
			filters.put("month", month);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultForDivManager", info);
			request.setAttribute("list", result);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("name", name);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("deptCode", deptCode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultForDivManager");
	}

	public ActionForward retrieveExpenseRealTimeResultByExpertGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String readOnly = ServletRequestUtils.getStringParameter(request, "readOnly", "");
		String deptCode = getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getDept();

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (!deptCode.equals("")) {
				filters.put("expertGroupCode", deptCode);
			}
			filters.put("assignDate", year + month + "__");
			filters.put("year", year);
			filters.put("month", month);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultByExpertGroup", info);
			request.setAttribute("list", result);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultByExpertGroup");
	}
	
	public ActionForward retrieveExpenseRealTimeResultByProjectBase(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String readOnly = ServletRequestUtils.getStringParameter(request, "readOnly", "");
		String deptCode = getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getDept();

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			if (deptCode.length() > 0) {
				filters.put("runningDeptCode", deptCode);
			}
			filters.put("assignDate", year + month + "__");
			filters.put("year", year);
			filters.put("month", month);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultByProjectBase", info);
			request.setAttribute("list", result);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);
			request.setAttribute("readOnly", readOnly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultByProjectBase");
	}	
	
	/**
	 * 실시간 강사료 리스트 상세정보
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrieveExpenseRealTimeResultPersonDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "60");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		//String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		//String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String ssn = SessionUtils.getUsername(request);
		String popupFlag = StringUtil.isNotNull((String) request.getAttribute("popupFlag")) ? (String) request.getAttribute("popupFlag") : "true";
		
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
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("ssn", ssn);
			filters.put("assignDate", sYear + sMonth + "__");
			filters.put("year", sYear);
			filters.put("month", sMonth);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultDetail", info);
			ValueList result2 = vlh.getValueList("retrieveMMExpenseRealTimeResultDetail", info);
			ValueList result3 = vlh.getValueList("retrieveMMrestExpenseRealTimeResultDetail", info);
			
			request.setAttribute("list", result);
			request.setAttribute("list2", result2);
			request.setAttribute("list3", result3);

			request.setAttribute("expertPool", this.getExpertPoolManager().retrieve(ssn));
			request.setAttribute("year", sYear);
			request.setAttribute("month", sMonth);
			request.setAttribute("ssn", ssn);
			request.setAttribute("role", "MB");
			request.setAttribute("popupFlag", popupFlag);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultDetail");
	}

	public ActionForward retrieveExpenseRealTimeResultDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "60");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", (String) request.getAttribute("ssn"));
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String isSanction = ServletRequestUtils.getStringParameter(request, "isSanction", "");
		String readySanction = ServletRequestUtils.getStringParameter(request, "readySanction", "");
		String sanctionState = ServletRequestUtils.getStringParameter(request, "sanctionState", "");
		String role = ServletRequestUtils.getStringParameter(request, "role", "");
		String popupFlag = StringUtil.isNotNull((String) request.getAttribute("popupFlag")) ? (String) request.getAttribute("popupFlag") : "true";
		String valueYearMonth = ServletRequestUtils.getStringParameter(request, "valueYearMonth", "");
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");
		String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");

		try {
			if (!valueYearMonth.equals("")) {
				int day = Integer.parseInt(DateTime.getDay());
				int sMonth = Integer.parseInt(month);
				int sYear = Integer.parseInt(year);
				if (day >= 1 && day <=5) {
					if (sMonth == 1) {
						sMonth = 12;
						sYear = sYear - 1;
						month = String.valueOf(sMonth);
						year = String.valueOf(sYear);
					} else {
						sMonth = sMonth - 1;
						month = String.valueOf(sMonth);
					}
					if (String.valueOf(sMonth).length() == 1) {
						month = "0" + String.valueOf(sMonth);
					} else {
						month = String.valueOf(sMonth);
					}
				} else {
					if (String.valueOf(sMonth).length() == 1) {
						month = "0" + String.valueOf(sMonth);
					} else {
						month = String.valueOf(sMonth);
					}
				}
			} else {
				if(isSanction.length() > 0 || readySanction.length() > 0){
					
					int day = Integer.parseInt(DateTime.getDay());
					if (day >= 1 && day <= 5) {
						if (month.equals("01")) {
							year = String.valueOf(Integer.parseInt(year) - 1);
							month = "12";
						} else {
							month = String.valueOf(Integer.parseInt(month) - 1);
							if (month.length() == 1) {
								month = "0" + month;
							}
						}
					}
				}
			}
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("ssn", ssn);
			filters.put("assignDate", year + month + "__");
			filters.put("year", year);
			filters.put("month", month);
			if (!projectCode.equals(""))
				filters.put("projectCode", projectCode);
			if (!divCode.equals(""))
				filters.put("divCode", divCode);
			if (!deptCode.equals(""))
				filters.put("deptCode", deptCode);
			if(sanctionState.equals("SANCTION_STATE_REJECT_DRAFT")){
			}else{
				if (!isSanction.equals(""))
					filters.put("isSanction", isSanction);
				if (!readySanction.equals(""))
					filters.put("readySanction", readySanction);			
			}
			
			
			info.setFilters(filters);
			info2.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultDetail", info);
			ValueList result2 = vlh.getValueList("retrieveMMExpenseRealTimeResultDetail", info2);
			ValueList result3 = vlh.getValueList("retrieveMMrestExpenseRealTimeResultDetail", info);
			
			request.setAttribute("list", result);
			request.setAttribute("list2", result2);
			request.setAttribute("list3", result3);

			request.setAttribute("expertPool", this.getExpertPoolManager().retrieve(ssn));
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("ssn", ssn);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("divCode", divCode);
			request.setAttribute("role", role);
			request.setAttribute("readySanction", readySanction);
			request.setAttribute("popupFlag", popupFlag);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultDetail");
	}
	
	public ActionForward retrieveExpenseRealTimeResultDetail2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "15");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", (String) request.getAttribute("ssn"));
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String isSanction = ServletRequestUtils.getStringParameter(request, "isSanction", "");
		String readySanction = ServletRequestUtils.getStringParameter(request, "readySanction", "");
		String sanctionState = ServletRequestUtils.getStringParameter(request, "sanctionState", "");
		String role = ServletRequestUtils.getStringParameter(request, "role", "");
		String popupFlag = StringUtil.isNotNull((String) request.getAttribute("popupFlag")) ? (String) request.getAttribute("popupFlag") : "true";
		String valueYearMonth = ServletRequestUtils.getStringParameter(request, "valueYearMonth", "");
		String divCode = ServletRequestUtils.getStringParameter(request, "divCode", "");
		
		try {
			if (!valueYearMonth.equals("")) {
				int day = Integer.parseInt(DateTime.getDay());
				int sMonth = Integer.parseInt(month);
				int sYear = Integer.parseInt(year);
				if (day >= 1 && day <=5) {
					if (sMonth == 1) {
						sMonth = 12;
						sYear = sYear - 1;
						month = String.valueOf(sMonth);
						year = String.valueOf(sYear);
					} else {
						sMonth = sMonth - 1;
						month = String.valueOf(sMonth);
					}
					if (String.valueOf(sMonth).length() == 1) {
						month = "0" + String.valueOf(sMonth);
					} else {
						month = String.valueOf(sMonth);
					}
				} else {
					if (String.valueOf(sMonth).length() == 1) {
						month = "0" + String.valueOf(sMonth);
					} else {
						month = String.valueOf(sMonth);
					}
				}
			} else {
				if(isSanction.length() > 0 || readySanction.length() > 0){
					
					int day = Integer.parseInt(DateTime.getDay());
					if (day >= 1 && day <= 5) {
						if (month.equals("01")) {
							year = String.valueOf(Integer.parseInt(year) - 1);
							month = "12";
						} else {
							month = String.valueOf(Integer.parseInt(month) - 1);
							if (month.length() == 1) {
								month = "0" + month;
							}
						}
					}
				}
			}
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			filters.put("ssn", ssn);
			filters.put("assignDate", year + month);
			if (!projectCode.equals(""))
				filters.put("projectCode", projectCode);
			if (!divCode.equals(""))
				filters.put("divCode", divCode);
			if(sanctionState.equals("SANCTION_STATE_REJECT_DRAFT")){
			}else{
				if (!isSanction.equals(""))
					filters.put("isSanction", isSanction);
				if (!readySanction.equals(""))
					filters.put("readySanction", readySanction);			
			}
			
			
			info.setFilters(filters);
			
			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultDetail2", info);
			request.setAttribute("list", result);
			
			request.setAttribute("expertPool", this.getExpertPoolManager().retrieve(ssn));
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("ssn", ssn);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("divCode", divCode);
			request.setAttribute("role", role);
			request.setAttribute("readySanction", readySanction);
			request.setAttribute("popupFlag", popupFlag);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeResultDetail2");
	}

	/**
	 * 실시간 성과급, 총기여금액 리스트
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward retrieveExpenseContributionCostRealTimeResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pageNo));
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
			if (deptCode.length() > 0) {
				filters.put("deptCode", deptCode.substring(0, 3) + "_");
			}
			if (!jobClass.equals("")) {
				filters.put("jobClass", jobClass);
			}
			filters.put("assignDate", year + month + "__");
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseContributionCostRealTimeResultDetail", info);
			request.setAttribute("list", result);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("deptCode", deptCode);
			request.setAttribute("jobClass", jobClass);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseContributionCostRealTimeResultDetail");
	}

	public ActionForward saveListToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		//String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		
		String fileName = year + "년" + month + "월_총성과급_리스트.xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
	    
	    try {
	    	WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			filters.put("assignDate", year + month + "__");
			filters.put("year", year);
			filters.put("month", month);
			
			if (!jobClass.equals(""))
				filters.put("jobClass", jobClass);
			//if (!deptCode.equals(""))
			//	filters.put("deptCode", deptCode);
			
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultForExcel1", info);
			request.setAttribute("list", result);
			
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
		
		return mapping.findForward("saveListToExcel");
	}
	
	public ActionForward saveDetailListToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		//String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		//String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		
		String fileName = year + "년" + month + "월_성과급_리스트(합산).xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
	    
	    try {
	    	WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
			//filters.put("assignDate", year + month + "__");
			filters.put("year", year);
			filters.put("month", month);
			
			//if (!jobClass.equals(""))
			//	filters.put("jobClass", jobClass);
			//if (!deptCode.equals(""))
			//	filters.put("deptCode", deptCode);
			
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultDetailForExcel", info);
			request.setAttribute("list", result);
			
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
		
		return mapping.findForward("saveDetailListToExcel");
	}

	public ActionForward saveDetailListGroupByPjtToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		/*String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "");
		  String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");*/
		
		String fileName = year + "년" + month + "월_성과급_리스트(상세).xls";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("EUC-KR"), "8859_1") + ";"); 
	    response.setHeader("Content-Description", "JSP Generated Data");
	    
	    try {
	    	WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			
		/*	filters.put("assignDate", year + month + "__");*/
			
			filters.put("year", year);
			filters.put("month", month);
			
			/*
			if (!jobClass.equals(""))
				filters.put("jobClass", jobClass);
			//if (!deptCode.equals(""))
			//	filters.put("deptCode", deptCode);
			*/
			info.setFilters(filters);

			ValueList result = vlh.getValueList("retrieveExpenseRealTimeResultDetailGroupByPjtForExcel", info);
			request.setAttribute("list", result);
			
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
		
		return mapping.findForward("saveDetailListGroupByPjtToExcel");
	}
	

	public ActionForward getProjectExpenseRealTimeRestResultDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectExpense", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("ssn", ssn);

			info.setFilters(filters);
			ValueList valueList = vlh.getValueList("getProjectExpenseRealTimeRestResultDetail", info);

			request.setAttribute("list", valueList);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectExpenseRealTimeRestResultDetail");
	}
	
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
