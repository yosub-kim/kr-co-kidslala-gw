package kr.co.kmac.pms.project.statistics.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.statistics.data.ProjectMemberMonthlyMM;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.internal.runners.model.EachTestNotifier;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="ProjectMemberMMPlanAction" path="/action/ProjectMemberMMPlanAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectMemberMMAnnualPlan" path="/project/statistics/projectMemberMMAnnualPlan.jsp" redirect="false"
 * @struts.action-forward name="projectMemberMMPlan" path="/project/statistics/projectMemberMMPlan.jsp" redirect="false"
 * @struts.action-forward name="projectMemberMMPlanByProject" path="/project/statistics/projectMemberMMPlanByProject.jsp" redirect="false"
 */
public class ProjectMemberMMPlanAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectMemberMMPlanAction.class);
	private ExpertPoolManager expertPoolManager;
	private ProjectMemberDao projectMemberDao;

	public ActionForward getProjectMemberMMAnnualPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String jobClass = ServletRequestUtils.getStringParameter(request, "jobClass", "J");
		String name = ServletRequestUtils.getStringParameter(request, "name", "");
		String deptCode = ServletRequestUtils.getStringParameter(request, "deptCode", "");
		String type = ServletRequestUtils.getStringParameter(request, "type", "A");
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("projectMemberMMPlanBean", ValueListHandler.class);
		ValueListInfo info = new ValueListInfo();
		Map<String, String> filters = new HashMap<String, String>();
		
		if (year != null && !year.equals("")) {
			filters.put("year", year);
		}
		if (month != null && !month.equals("")) {
			// 이전 년도인 경우 월을 13(sql where 절 비교연산자가 >= 이므로 마지막 12월 에서 + 1)월로 고정
			if (Integer.parseInt(year) < Integer.parseInt(DateTime.getYear())) {
				filters.put("month", "13");
			} else {
				filters.put("month", month);
			}
		}
		if (jobClass != null && !jobClass.equals("")) {
			filters.put("jobClass", jobClass);
		}
		if (name != null && !name.equals("")) {
			filters.put("name", "%" + name + "%");
		}
		if (deptCode != null && !deptCode.equals("")) {
			filters.put("deptCode", deptCode);
		}
		if (!year.equals("") && !month.equals("")) {
			filters.put("initYearMonth", year + "0101");
			// 이전 년도인 경우 다음 연도 1월 1일로 고정
			if (Integer.parseInt(year) < Integer.parseInt(DateTime.getYear())) {
				filters.put("thisYearMonth", Integer.toString(Integer.parseInt(year) + 1) + "0101");
			} else {
				filters.put("thisYearMonth", year + month + "01");
			}
		}
		
		info.setFilters(filters);

		ValueList valueListForAnnualMM = vlh.getValueList("getProjectMemberAnnualMMType", info);
		
		request.setAttribute("list", valueListForAnnualMM);
		request.setAttribute("year", year);
		request.setAttribute("jobClass", jobClass);
		request.setAttribute("deptCode", deptCode);
		request.setAttribute("name", name);
		request.setAttribute("type", type);

		return mapping.findForward("projectMemberMMAnnualPlan");
		
	}

	public ActionForward getProjectMemberMMPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", SessionUtils.getUsername(request));
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String state = ServletRequestUtils.getStringParameter(request, "state", "3");
		String type = ServletRequestUtils.getStringParameter(request, "type", "A");
		String searchDate = "";

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("projectMemberMMPlanBean", ValueListHandler.class);
		ValueListInfo info = new ValueListInfo();
		Map<String, String> filters = new HashMap<String, String>();

		if (ssn != null && !ssn.equals("")) {
			filters.put("ssn", ssn);
		}
		if (year != null && !year.equals("")) {
			filters.put("year", year);
			searchDate = year;
		}
		if (month != null && !month.equals("")) {
			filters.put("month", month);
			searchDate += month + "01";
		}
		if (!searchDate.equals("")) {
			filters.put("searchDate", searchDate);
		}
		if (!state.equals("")) {
			filters.put("state", state);
		}
		if (!year.equals("") && !month.equals("")) {
			filters.put("yearMonth", year+month+"%");
		}

		info.setFilters(filters);
		
		// 조회하는 월이 이번 달이거나 이후 월이면 계획 값을 처리하는 application-context 
		String valueListForMonthlyMMType = "getProjectMemberMonthlyPlanMM";
		// 조회하는 월이 이전 월인 경우 실적 값을 처리하는 application-context
		if (Integer.parseInt(year + month) < Integer.parseInt(StringUtil.getCurr("yyyyMM"))) {
			valueListForMonthlyMMType = "getProjectMemberMonthlyActualMM";
		}

		ValueList valueListForWholeMM = vlh.getValueList("getProjectMemberWholeMM", info);
		ValueList valueListForMonthlyMM = vlh.getValueList(valueListForMonthlyMMType, info);

		request.setAttribute("wholeMM", valueListForWholeMM);
		request.setAttribute("monthlyMM", valueListForMonthlyMM);
		request.setAttribute("state", state);
		request.setAttribute("ssn", ssn);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("type", type);
		request.setAttribute("thisYear", StringUtil.getCurr("yyyy"));
		request.setAttribute("thisMonth", StringUtil.getCurr("MM"));
		request.setAttribute("userSsn", SessionUtils.getUsername(request));
		request.setAttribute("userDept", this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request)).getDept());
		
		return mapping.findForward("projectMemberMMPlan");
	}
	
	public ActionForward getProjectMemberMMPlanByProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getStringParameter(request, "ssn", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyy"));
		String month = ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("MM"));

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("projectMemberMMPlanBean", ValueListHandler.class);
		ValueListInfo info = new ValueListInfo();
		Map<String, String> filters = new HashMap<String, String>();

		filters.put("ssn", ssn);
		filters.put("projectCode", projectCode);

		info.setFilters(filters);

		ValueList valueListForMMPlanByProject = vlh.getValueList("getProjectMemberMMPlanByProject", info);

		request.setAttribute("mmValueList", valueListForMMPlanByProject);
		request.setAttribute("ssn", ssn);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		
		return mapping.findForward("projectMemberMMPlanByProject");
	}
	
	public void updateProjectMemberMMPlanByProject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String projectTypeCode = ServletRequestUtils.getRequiredStringParameter(request, "projectTypeCode");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		String[] year = ServletRequestUtils.getStringParameters(request, "year");
		String[] month = ServletRequestUtils.getStringParameters(request, "month");
		String[] mmValue = ServletRequestUtils.getStringParameters(request, "mmValue");
		String[] mmState = ServletRequestUtils.getStringParameters(request, "mmState");
		String thisYearMonth = StringUtil.getCurr("yyyyMM");

		try {
			if (projectTypeCode.equals("MM")) {
				// 이번 달 이후의 투입 정보만 삭제함
				this.getProjectMemberDao().deleteMMInfoByTime(projectCode, ssn);
				// 이번 달 이후의 투입 정보만 저장
				for (int i = 0; i < year.length; i++) {
					if(Integer.parseInt(thisYearMonth) <= Integer.parseInt(year[i] + month[i])) {
						ProjectMemberMonthlyMM projectMemberMonthlyMM = new ProjectMemberMonthlyMM();
						projectMemberMonthlyMM.setProjectCode(projectCode);
						projectMemberMonthlyMM.setSsn(ssn);
						projectMemberMonthlyMM.setYear(year[i]);
						projectMemberMonthlyMM.setMonth(month[i]);
						projectMemberMonthlyMM.setMmValue(mmValue[i]);
						this.getProjectMemberDao().insertMMInfoByProject(projectMemberMonthlyMM, "Y", mmState[i]);
					}
				}
				
			} else {
				// 전체 투입 정보 삭제
				this.getProjectMemberDao().deleteMMInfo(projectCode, ssn);
				// 투입 정보 저장
				for (int i = 0; i < year.length; i++) {
					ProjectMemberMonthlyMM projectMemberMonthlyMM = new ProjectMemberMonthlyMM();
					projectMemberMonthlyMM.setProjectCode(projectCode);
					projectMemberMonthlyMM.setSsn(ssn);
					projectMemberMonthlyMM.setYear(year[i]);
					projectMemberMonthlyMM.setMonth(month[i]);
					projectMemberMonthlyMM.setMmValue(mmValue[i]);
					this.getProjectMemberDao().insertMMInfoByProject(projectMemberMonthlyMM, "Y", mmState[i]);
				}
			}
			// 변경자 정보 로깅
			this.getProjectMemberDao().createMMInfoModifiedLog(projectCode, ssn, SessionUtils.getUsername(request));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}	

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
	
	public ProjectMemberDao getProjectMemberDao() {
		return projectMemberDao;
	}

	public void setProjectMemberDao(ProjectMemberDao projectMemberDao) {
		this.projectMemberDao = projectMemberDao;
	}

}
