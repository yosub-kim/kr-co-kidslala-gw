package kr.co.kmac.pms.project.manpower.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.manpower.manager.ProjectManpowerManager;
import kr.co.kmac.pms.project.master.data.ProjectWorkName;
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
 * @struts.action name="ProjectManpowerAction" path="/action/ProjectManpowerAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectManpowerList" path="/project/manpower/projectManpowerList.jsp" redirect="false"
 * @struts.action-forward name="projectManpowerScheduleRegist" path="/project/manpower/projectManpowerScheduleRegist.jsp" redirect="false"
 */
public class ProjectManpowerAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectManpowerAction.class);

	private ProjectManpowerManager projectManpowerManager;
	private ProjectMasterInfoManager projectMasterInfoManager;

	// private ProjectReportManager projectReportManager;
	// private WorklistManager worklistManager;

	public ActionForward getProjectManpowerList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		String year =ServletRequestUtils.getStringParameter(request, "year", StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		String month = ServletRequestUtils.getStringParameter(request, "month", StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		if (String.valueOf(month).length() == 1) {
			month = "0" + String.valueOf(month);
		} else {
			month = String.valueOf(month);
		}

		try {
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpower(projectCode, year, month);
			// 프로젝트 별 전체 멤버
			List<ProjectManpower> projectMemberList = this.getProjectManpowerManager().getProjectMember(projectCode); // 멤버
			List<ProjectManpower> projectWorkNameList = this.getProjectManpowerManager().getProjectWorkName(projectCode, year, month); // 부분
			List<ProjectManpower> projectMonthWorkList = this.getProjectManpowerManager().getProjecMonthWork(projectCode, year, month); // 전체
			
			request.setAttribute("projectMemberList", projectMemberList);
			request.setAttribute("projectWorkNameList", projectWorkNameList);
			request.setAttribute("projectMonthWorkList", projectMonthWorkList);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
			request.setAttribute("viewMode", viewMode);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("today", DateTime.getDay());
			request.setAttribute("projectManpowerList", projectManpowerList);
			request.setAttribute("readonly", readonly);

			if (viewMode.equals("wreport")) {
				request.setAttribute("assignWeekOfMonth", ServletRequestUtils.getStringParameter(request, "assignWeekOfMonth"));
				request.setAttribute("thisWeekFromDate", ServletRequestUtils.getStringParameter(request, "thisWeekFromDate"));
				request.setAttribute("thisWeekToDate", ServletRequestUtils.getStringParameter(request, "thisWeekToDate"));
				request.setAttribute("nextWeekFromDate", ServletRequestUtils.getStringParameter(request, "nextWeekFromDate"));
				request.setAttribute("nextWeekToDate", ServletRequestUtils.getStringParameter(request, "nextWeekToDate"));
				request.setAttribute("year", ServletRequestUtils.getRequiredStringParameter(request, "assignYear"));
				request.setAttribute("month", ServletRequestUtils.getRequiredStringParameter(request, "assignMonth"));

			} else  if (viewMode.equals("mreport")) {
				request.setAttribute("year", ServletRequestUtils.getRequiredStringParameter(request, "assignYear"));
				request.setAttribute("month", ServletRequestUtils.getRequiredStringParameter(request, "assignMonth"));

			}
			
			/* work popup */
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PLMB", "PLMB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList projectWorkNameList2 = vlh.getValueList("projectWorkNameListSelect", info);
			ValueList projectMemberList2 = vlh.getValueList("projectMemberListForProjectManpowerSelect", info);

			request.setAttribute("selectedDay", DateTime.getYear()+"-"+DateTime.getMonth()+"-"+DateTime.getDay());
			request.setAttribute("projectWorkNameList2", projectWorkNameList2);
			request.setAttribute("projectMemberList2", projectMemberList2);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectManpowerList");
	}
	
	public ActionForward getProjectManpowerList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		String year = null;
		String month = null;

		if (viewMode.equals("wreport") || viewMode.equals("mreport")) {
			year = ServletRequestUtils.getRequiredStringParameter(request, "assignYear");
			month = ServletRequestUtils.getRequiredStringParameter(request, "assignMonth");
		} else {
			year = DateTime.getYear();
			month = DateTime.getMonth();
		}

		try {
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpower(projectCode, year, month);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
			request.setAttribute("viewMode", viewMode);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("today", DateTime.getDay());
			request.setAttribute("projectManpowerList", projectManpowerList);
			request.setAttribute("readonly", readonly);

			if (viewMode.equals("wreport")) {
				request.setAttribute("assignWeekOfMonth", ServletRequestUtils.getStringParameter(request, "assignWeekOfMonth"));
				request.setAttribute("thisWeekFromDate", ServletRequestUtils.getStringParameter(request, "thisWeekFromDate"));
				request.setAttribute("thisWeekToDate", ServletRequestUtils.getStringParameter(request, "thisWeekToDate"));
				request.setAttribute("nextWeekFromDate", ServletRequestUtils.getStringParameter(request, "nextWeekFromDate"));
				request.setAttribute("nextWeekToDate", ServletRequestUtils.getStringParameter(request, "nextWeekToDate"));
				request.setAttribute("year", ServletRequestUtils.getRequiredStringParameter(request, "assignYear"));
				request.setAttribute("month", ServletRequestUtils.getRequiredStringParameter(request, "assignMonth"));

			} else  if (viewMode.equals("mreport")) {
				request.setAttribute("year", ServletRequestUtils.getRequiredStringParameter(request, "assignYear"));
				request.setAttribute("month", ServletRequestUtils.getRequiredStringParameter(request, "assignMonth"));

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectManpowerList2");
	}

	public void searchProjectManpowerList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getStringParameter(request, "year", null);
		String month = ServletRequestUtils.getStringParameter(request, "month", null);

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpower(projectCode, year, month);

			map.put("project", this.getProjectMasterInfoManager().getProject(projectCode));
			map.put("projectManpowerList", projectManpowerList);
			map.put("readonly", ServletRequestUtils.getBooleanParameter(request, "readonly", false));
			map.put("viewMode", ServletRequestUtils.getStringParameter(request, "viewMode"));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void searchProjectManpowerListForWreport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String startDate = ServletRequestUtils.getRequiredStringParameter(request, "startDate");
		String endDate = ServletRequestUtils.getRequiredStringParameter(request, "endDate");

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpowerForWreport(projectCode, startDate, endDate);
			if (projectManpowerList != null && projectManpowerList.size() > 0) {
				List<String> nameList = new ArrayList<String>();
				for (ProjectManpower projectManpower : projectManpowerList) {
					nameList.add(projectManpower.getName());
				}
				map.put("projectManpowerNameList", new ArrayList<String>(new TreeSet<String>(nameList)));
			} else {
				map.put("projectManpowerNameList", new ArrayList<String>());
			}

			map.put("projectManpowerList", projectManpowerList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void searchProjectManpowerListForMreport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpowerForWreport(projectCode, year+month+"01", year+month+"31");
			
			if (projectManpowerList != null && projectManpowerList.size() > 0) {
				List<String> nameList = new ArrayList<String>();
				for (ProjectManpower projectManpower : projectManpowerList) {
					nameList.add(projectManpower.getName());
				}
				map.put("projectManpowerNameList", new ArrayList<String>(new TreeSet<String>(nameList)));
			}

			map.put("projectManpowerList", projectManpowerList);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void storeProjectManpower(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String startDate = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "startDate", ""), "-", "");
		String endDate = StringUtil.replace(ServletRequestUtils.getStringParameter(request, "endDate", ""), "-", "");
		boolean chkWeek1 = ServletRequestUtils.getBooleanParameter(request, "chkWeek1", false);
		boolean chkWeek2 = ServletRequestUtils.getBooleanParameter(request, "chkWeek2", false);
		boolean chkWeek3 = ServletRequestUtils.getBooleanParameter(request, "chkWeek3", false);
		boolean chkWeek4 = ServletRequestUtils.getBooleanParameter(request, "chkWeek4", false);
		boolean chkWeek5 = ServletRequestUtils.getBooleanParameter(request, "chkWeek5", false);
		boolean chkWeek6 = ServletRequestUtils.getBooleanParameter(request, "chkWeek6", false);
		boolean chkWeek7 = ServletRequestUtils.getBooleanParameter(request, "chkWeek7", false);
		boolean chkWeek8 = ServletRequestUtils.getBooleanParameter(request, "chkWeek8", false);

		String workSeq = ServletRequestUtils.getStringParameter(request, "workSeq", null);
		String[] ssn = ServletRequestUtils.getStringParameters(request, "ssn");

		try {
			// 지도일정 저장
			List<ProjectManpower> projectManpowerList = new ArrayList<ProjectManpower>();
			Calendar sDate = DateUtil.getCalendarInstance(startDate);
			if (endDate.equals("")) {
				ProjectManpower projectManpower = new ProjectManpower();
				projectManpower.setProjectCode(projectCode);
				projectManpower.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
				projectManpower.setMonth(String.valueOf(sDate.get(Calendar.MONTH) + 1));
				projectManpower.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
						+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
				projectManpower.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
						+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
				projectManpower.setSsnArray(ssn);

				projectManpower.setWorkSeq(workSeq);
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put("projectCode", projectCode);
				filters.put("workSeq", workSeq);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
				info.setFilters(filters);
				ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
				projectManpower.setWorkName(projectWorkName.getWorkName());

				this.getProjectManpowerManager().storeProjectManpower(projectManpower);
			} else {
				Calendar eDate = DateUtil.getCalendarInstance(endDate);
				while (DateUtil.getDifferDays(sDate, eDate) >= 0) {
					System.out.println(DateUtil.getDifferDays(sDate, eDate));
					System.out.println(DateUtil.getYyyymmdd(sDate));
					System.out.println(DateUtil.getYyyymmdd(eDate));
					ProjectManpower projectManpower = new ProjectManpower();
					projectManpower.setProjectCode(projectCode);
					projectManpower.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
					projectManpower.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
							+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
					projectManpower.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
							+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
					projectManpower.setSsnArray(ssn);

					projectManpower.setWorkSeq(workSeq);
					WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
					ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
					ValueListInfo info = new ValueListInfo();
					Map<String, String> filters = new HashMap<String, String>();
					filters.put("projectCode", projectCode);
					filters.put("workSeq", workSeq);
					filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
					info.setFilters(filters);
					ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
					projectManpower.setWorkName(projectWorkName.getWorkName());

					int weekInfo = sDate.get(Calendar.DAY_OF_WEEK);
					int monthInfo = sDate.get(Calendar.MONTH);
					int weekInfo2 = sDate.get(Calendar.DATE);

					switch (weekInfo) {
					case Calendar.MONDAY:
						if (chkWeek1) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.TUESDAY:
						if (chkWeek2) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.WEDNESDAY:
						if (chkWeek3) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.THURSDAY:
						if (chkWeek4) {
							System.out.println(Calendar.MONTH + " " + Calendar.DAY_OF_MONTH);
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.FRIDAY:
						if (chkWeek5) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.SATURDAY:
						if (chkWeek6) {
							projectManpowerList.add(projectManpower);
						}
						break;
					case Calendar.SUNDAY:
						if (chkWeek7) {
							projectManpowerList.add(projectManpower);
						}
						break;
					}

					sDate.add(Calendar.DATE, 1);
				}
				this.getProjectManpowerManager().storeProjectManpower(projectManpowerList);
			}

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward loadProjectManpowerSchedulePopup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String selectedDay = ServletRequestUtils.getRequiredStringParameter(request, "selectedDay");
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PLMB", "PLMB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList projectWorkNameList = vlh.getValueList("projectWorkNameListSelect", info);
			ValueList projectMemberList = vlh.getValueList("projectMemberListForProjectManpowerSelect", info);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("selectedDay", selectedDay);
			request.setAttribute("projectWorkNameList", projectWorkNameList);
			request.setAttribute("projectMemberList", projectMemberList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectManpowerScheduleRegist");
	}

	public void deleteProjectManpower(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String day = ServletRequestUtils.getRequiredStringParameter(request, "day");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");

		try {
			// 투입일정 삭제
			this.getProjectManpowerManager().deleteProjectManpower(projectCode, year, month, day, ssn);
			// 삭제 후 투입일정 조회
			List<ProjectManpower> projectManpowerList = this.getProjectManpowerManager().getProjectManpower(projectCode, year, month);

			map.put("project", this.getProjectMasterInfoManager().getProject(projectCode));
			map.put("projectManpowerList", projectManpowerList);
			map.put("message", "삭제하였습니다.");
			map.put("canDelete", new Boolean(true));

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ProjectManpowerManager getProjectManpowerManager() {
		return projectManpowerManager;
	}

	public void setProjectManpowerManager(ProjectManpowerManager projectManpowerManager) {
		this.projectManpowerManager = projectManpowerManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}
}