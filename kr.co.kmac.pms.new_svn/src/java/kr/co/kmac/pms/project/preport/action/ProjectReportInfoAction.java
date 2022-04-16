package kr.co.kmac.pms.project.preport.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.master.data.ProjectWorkName;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.sanction.preport.manager.ProjectReportManager;
import kr.co.kmac.pms.service.scheduler.manager.ProjectReportScheduleManager;
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
 * @struts.action name="projectReportInfoAction" path="/action/ProjectReportInfoAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectReportInfoList" path="/project/preport/projectReportInfoList.jsp" redirect="false"
 * @struts.action-forward name="projectReportScheduleRegist" path="/project/preport/projectReportScheduleRegist.jsp" redirect="false"
 */
public class ProjectReportInfoAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectReportInfoAction.class);

	private ProjectReportInfoManager projectReportInfoManager;
	private ProjectMasterInfoManager projectMasterInfoManager;

	private ProjectReportManager projectReportManager;
	private ProjectReportScheduleManager projectReportScheduleManager;
	private WorklistManager worklistManager;

	public ActionForward getProjectReportInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
			List<ProjectReportPlan> projectReportPlanList = this.getProjectReportInfoManager().getProjectReportInfo(projectCode, year, month);
			// 프로젝트 별 전체 멤버
			List<ProjectReportPlan> projectMemberList = this.getProjectReportInfoManager().getProjectMember(projectCode); // 멤버
			List<ProjectReportPlan> projectWorkNameList = this.getProjectReportInfoManager().getProjectWorkName(projectCode, year, month); // 부분
			List<ProjectReportPlan> projectMonthWorkList = this.getProjectReportInfoManager().getProjecMonthWork(projectCode, year, month); // 전체

			request.setAttribute("projectMemberList", projectMemberList);
			request.setAttribute("projectWorkNameList", projectWorkNameList);
			request.setAttribute("projectMonthWorkList", projectMonthWorkList);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
			request.setAttribute("viewMode", viewMode);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("today", DateTime.getDay());
			request.setAttribute("projectReportPlanList", projectReportPlanList);
			request.setAttribute("readonly", readonly);
			request.setAttribute("selectedDay", DateTime.getYear()+"-"+DateTime.getMonth()+"-"+DateTime.getDay());
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PLMB", "PLMB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList projectWorkNameList2 = vlh.getValueList("projectWorkNameListSelect", info);
			ValueList projectMemberList2 = vlh.getValueList("projectMemberListForProjectReportSelect", info);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("projectWorkNameList2", projectWorkNameList2);
			request.setAttribute("projectMemberList2", projectMemberList2);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectReportInfoList");
	}
	
	public ActionForward getProjectReportInfoList2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		String year = DateTime.getYear();
		String month = DateTime.getMonth();

		try {
			List<ProjectReportPlan> projectReportPlanList = this.getProjectReportInfoManager().getProjectReportInfo(projectCode, year, month);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
			request.setAttribute("viewMode", viewMode);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("today", DateTime.getDay());
			request.setAttribute("projectReportPlanList", projectReportPlanList);
			request.setAttribute("readonly", readonly);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectReportInfoList2");
	}

	public void searchProjectReportInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getStringParameter(request, "year", null);
		String month = ServletRequestUtils.getStringParameter(request, "month", null);

		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<ProjectReportPlan> projectReportPlanList = this.getProjectReportInfoManager().getProjectReportInfo(projectCode, year, month);

			map.put("project", this.getProjectMasterInfoManager().getProject(projectCode));
			map.put("projectReportPlanList", projectReportPlanList);
			map.put("readonly", ServletRequestUtils.getBooleanParameter(request, "readonly", false));
			map.put("viewMode", ServletRequestUtils.getStringParameter(request, "viewMode"));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void storeProjectReportInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
		String[] time = ServletRequestUtils.getStringParameters(request, "time");

		try {
			// 지도일정 저장
			List<ProjectReportPlan> projectReportPlansList = new ArrayList<ProjectReportPlan>();
			Calendar sDate = DateUtil.getCalendarInstance(startDate);
			if (endDate.equals("")) {
				ProjectReportPlan projectReportPlan = new ProjectReportPlan();
				projectReportPlan.setProjectCode(projectCode);
				projectReportPlan.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
				projectReportPlan.setMonth(String.valueOf(sDate.get(Calendar.MONTH) + 1));
				projectReportPlan.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
						+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
				projectReportPlan.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
						+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
				projectReportPlan.setSsnArray(ssn);
				projectReportPlan.setTimeArray(time);

				projectReportPlan.setWorkSeq(workSeq);
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
				ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
				ValueListInfo info = new ValueListInfo();
				Map<String, String> filters = new HashMap<String, String>();
				filters.put("projectCode", projectCode);
				filters.put("workSeq", workSeq);
				filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
				info.setFilters(filters);
				ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
				projectReportPlan.setWorkName(projectWorkName.getWorkName());
				projectReportPlan.setOutputName(projectWorkName.getOutputName());

				this.getProjectReportInfoManager().storeProjectReportInfo(projectReportPlan);
			} else {
				Calendar eDate = DateUtil.getCalendarInstance(endDate);
				while (DateUtil.getDifferDays(sDate, eDate) >= 0) {
					ProjectReportPlan projectReportPlan = new ProjectReportPlan();
					projectReportPlan.setProjectCode(projectCode);
					projectReportPlan.setYear(String.valueOf(sDate.get(Calendar.YEAR)));
					projectReportPlan.setMonth((sDate.get(Calendar.MONTH) + 1) >= 10 ? String.valueOf((sDate.get(Calendar.MONTH) + 1)) : "0"
							+ String.valueOf((sDate.get(Calendar.MONTH) + 1)));
					projectReportPlan.setDay(sDate.get(Calendar.DAY_OF_MONTH) >= 10 ? String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)) : "0"
							+ String.valueOf(sDate.get(Calendar.DAY_OF_MONTH)));
					projectReportPlan.setSsnArray(ssn);
					projectReportPlan.setTimeArray(time);

					projectReportPlan.setWorkSeq(workSeq);
					WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
					ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
					ValueListInfo info = new ValueListInfo();
					Map<String, String> filters = new HashMap<String, String>();
					filters.put("projectCode", projectCode);
					filters.put("workSeq", workSeq);
					filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
					info.setFilters(filters);
					ProjectWorkName projectWorkName = (ProjectWorkName) vlh.getValueList("projectWorkNameListSelect", info).getList().get(0);
					projectReportPlan.setWorkName(projectWorkName.getWorkName());
					projectReportPlan.setOutputName(projectWorkName.getOutputName());

					int weekInfo = sDate.get(Calendar.DAY_OF_WEEK);
					int monthInfo = sDate.get(Calendar.MONTH);
					int weekInfo2 = sDate.get(Calendar.DATE);
					switch (weekInfo) {
					case Calendar.MONDAY:
						if (chkWeek1) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.TUESDAY:
						if (chkWeek2) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.WEDNESDAY:
						if (chkWeek3) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.THURSDAY:
						if (chkWeek4) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.FRIDAY:
						if (chkWeek5) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.SATURDAY:
						if (chkWeek6) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					case Calendar.SUNDAY:
						if (chkWeek7) {
							projectReportPlansList.add(projectReportPlan);
						}
						break;
					}

					sDate.add(Calendar.DATE, 1);

				}
				this.getProjectReportInfoManager().storeProjectReportInfo(projectReportPlansList);
			}

			// 지도일지 생성
			// TODO 지도일지 생성 메소드 주석처리
			//this.getProjectReportScheduleManager().assignProjectReportUntilToday(projectCode);

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void assignProjectReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			// 지도일지 생성 메소드
			this.getProjectReportScheduleManager().assignProjectReportUntilToday();

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateTeachingTime(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String day = ServletRequestUtils.getRequiredStringParameter(request, "day");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");
		String teachingTime = ServletRequestUtils.getRequiredStringParameter(request, "teachingTime");

		try {
			ProjectReportPlan projectReportPlan = this.getProjectReportInfoManager().getProjectReportInfo(projectCode, year, month, day, ssn);
			projectReportPlan.setTime(teachingTime);
			this.getProjectReportInfoManager().storeProjectReportInfoForTeachingTime(projectReportPlan);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updatePreportPayAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = request.getParameter("projectCode");
		String ssn = request.getParameter("writerSsn");
		String assignDate = request.getParameter("assignDate");
		String payAmount = request.getParameter("payAmount");
		try {
			this.getProjectReportInfoManager().updateProjectReportPayAmount(projectCode, assignDate, ssn, payAmount);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
		}
	}

	public void deleteProjectReportInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String year = ServletRequestUtils.getRequiredStringParameter(request, "year");
		String month = ServletRequestUtils.getRequiredStringParameter(request, "month");
		String day = ServletRequestUtils.getRequiredStringParameter(request, "day");
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");

		try {
			this.getProjectReportInfoManager().deleteProjectReportInfo(projectCode, year, month, day, ssn);
			map.put("message", "삭제하였습니다. ");
			map.put("canDelete", new Boolean(true));
			// 지도일지가 있는 경우 같이 삭제
			/*ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent2(projectCode, year + month + day, ssn);
			if (projectReportContent == null) {
				// 지도일정 삭제
				this.getProjectReportInfoManager().deleteProjectReportInfo(projectCode, year, month, day, ssn);
				map.put("message", "삭제하였습니다. ");
				map.put("canDelete", new Boolean(true));
			} else {
				if (projectReportContent.getState() != null && !projectReportContent.getState().equals("writer")
						&& !projectReportContent.getState().equals("reject")) {
					map.put("message", "본 일정에 대한 지도일지를 작성하였으므로 삭제할 수 없습니다.");
					map.put("canDelete", new Boolean(false));
				} else {
					// 지도일지 삭제
					this.getProjectReportManager().deleteProjectReport2(projectCode, year + month + day, ssn);
					// 지도일정 삭제 또는 업데이트
					this.getProjectReportInfoManager().deleteProjectReportInfo(projectCode, year, month, day, ssn);
					// 지도일정 업무 삭제
					Work work = this.getWorklistManager().getWorkList(projectReportContent.getProjectCode(),
							projectReportContent.getTaskFormTypeId(), projectReportContent.getSeq());
					if (work != null)
						this.getWorklistManager().terminatWork(work.getId());
					map.put("message", "삭제하였습니다. ");
					map.put("canDelete", new Boolean(true));
				}
			}*/

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward loadProjectSchedulePopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
			ValueList projectMemberList = vlh.getValueList("projectMemberListForProjectReportSelect", info);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("selectedDay", selectedDay);
			request.setAttribute("projectWorkNameList", projectWorkNameList);
			request.setAttribute("projectMemberList", projectMemberList);
			request.setAttribute("businessTypeCode", this.getProjectMasterInfoManager().getProjectBusinessTypeCode(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectReportScheduleRegist");
	}

	public ProjectReportInfoManager getProjectReportInfoManager() {
		return projectReportInfoManager;
	}

	public void setProjectReportInfoManager(ProjectReportInfoManager projectReportInfoManager) {
		this.projectReportInfoManager = projectReportInfoManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProjectReportManager getProjectReportManager() {
		return projectReportManager;
	}

	public void setProjectReportManager(ProjectReportManager projectReportManager) {
		this.projectReportManager = projectReportManager;
	}

	public ProjectReportScheduleManager getProjectReportScheduleManager() {
		return projectReportScheduleManager;
	}

	public void setProjectReportScheduleManager(ProjectReportScheduleManager projectReportScheduleManager) {
		this.projectReportScheduleManager = projectReportScheduleManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

}
