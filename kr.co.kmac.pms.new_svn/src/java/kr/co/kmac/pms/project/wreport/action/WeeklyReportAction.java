package kr.co.kmac.pms.project.wreport.action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.wreport.data.WeeklyReport;
import kr.co.kmac.pms.project.wreport.form.WeeklyReportForm;
import kr.co.kmac.pms.project.wreport.manager.WeeklyReportManager;
import kr.co.kmac.pms.schedule.data.ScheduleUserInfo;
import kr.co.kmac.pms.service.scheduler.manager.WeeklyReportScheduleManager;
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
 * @struts.action name="weeklyReportAction" path="/action/WeeklyReportAction" parameter="mode" 			scope="request"
 * 
 * @struts.action-forward name="weeklyReportList" path="/project/wreport/weeklyReportList.jsp" 			redirect="false"
 * @struts.action-forward name="weeklyReportStatus" path="/project/wreport/weeklyReportStatus.jsp" 			redirect="false"
 * @struts.action-forward name="weeklyReportForm" path="/sanction/sanctionForm/weeklyReportForm.jsp"	redirect="false"
 * @struts.action-forward name="projectMemberPopup" path="/project/wreport/projectMemberPopup.jsp" 		redirect="false"
 */
public class WeeklyReportAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(WeeklyReportAction.class);

	private ProjectMasterInfoManager projectMasterInfoManager;
	private WeeklyReportManager weeklyReportManager;
	private WeeklyReportScheduleManager weeklyReportScheduleManager;
	private WorklistManager worklistManager;
	private AttachTemplateManager attachTemplateManager; 
	private AttachManager attachManager; 

	
	
	public ActionForward getWeeklyReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "1000");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String toDate = ServletRequestUtils.getStringParameter(request, "toDate", "");
		String fromDate = ServletRequestUtils.getStringParameter(request, "fromDate", "");
		String writerName = ServletRequestUtils.getStringParameter(request, "writerName", "");
		String state = ServletRequestUtils.getStringParameter(request, "state", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "");
		String selectedYear = ServletRequestUtils.getStringParameter(request, "selectedYear", "");
		String selectedMonth = ServletRequestUtils.getStringParameter(request, "selectedMonth", "");
		
		System.out.println(fromDate + '-' + toDate);
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("projectCode", projectCode);
			if (!writerName.equals("")) {
				filters.put("writerName", "%" + writerName + "%");
				request.setAttribute("writerName", writerName);
			}
			if (!state.equals("")) {
				filters.put("state", state);
				request.setAttribute("state", state);
			}
			if (!fromDate.equals("")) {
				filters.put("from", StringUtil.replace(fromDate, "-", ""));
				request.setAttribute("fromDate", fromDate);
			}
			if (!toDate.equals("")) {
				filters.put("until", StringUtil.replace(toDate, "-", ""));
				request.setAttribute("toDate", toDate);
			}

			info.setFilters(filters);
			
			// 날짜 추가
			int intYear = 0;
			int intMonth = 0;
			String strNowDay = "";
			Calendar c = new GregorianCalendar();
			if (StringUtil.isNull(selectedMonth, "").equals("")) {
				intYear = c.get(Calendar.YEAR);
				intMonth = c.get(Calendar.MONTH);
			} else {
				intYear = Integer.parseInt(selectedYear);
				intMonth = Integer.parseInt(selectedMonth) - 1;
			}

			ValueList result = vlh.getValueList("getWeeklyReportList", info);
			request.setAttribute("weeklyReportList", result);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("viewMode", viewMode);
			ProjectSimpleInfo psi = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
			request.setAttribute("projectName", psi.getProjectName());
			request.setAttribute("intYear", intYear);
			request.setAttribute("intMonth", intMonth + 1);
			request.setAttribute("gubun", "123");
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("weeklyReportList");
	}

	/**
	 * 주간보고 한 건을 반환
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWeeklyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String assignYear = ServletRequestUtils.getRequiredStringParameter(request, "assignYear");
		String assignMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignMonth");
		String assignWeekOfMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignWeekOfMonth");
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		WeeklyReport weeklyReport = weeklyReportManager.getWeeklyReport(projectCode, assignYear, assignMonth, assignWeekOfMonth);
		// 주간진척관리 엑티비티 호출
		List<ProjectProgressEntity> activity = weeklyReportManager.getWeeklyActivity(projectCode);

		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("attachBusType", "BTZ");
		/*request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getAssignDate(), null, null).getList()); */
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc,"wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq(), null, null).getList());
		request.setAttribute("weeklyReport", weeklyReport);
		request.setAttribute("readonly", true);
		request.setAttribute("activity", activity);

		return mapping.findForward("weeklyReportForm");
	}
	
	/**
	 * 주간보고 한 건을 반환
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getWeeklyReportByWorkId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		Work work = this.getWorklistManager().getWork(workId);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		WeeklyReport weeklyReport = this.getWeeklyReportManager().getWeeklyReport(work.getRefWorkId1(),
				work.getRefWorkId3().substring(0, 4),
				work.getRefWorkId3().substring(4, 6),
				work.getRefWorkId3().substring(6));
		weeklyReport.setWorkId(workId);
		
		// 주간진척관리 엑티비티 호출
		List<ProjectProgressEntity> activity = weeklyReportManager.getWeeklyActivity(work.getRefWorkId1());
		request.setAttribute("activity", activity);
		
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc,"wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq(), null, null).getList());
		request.setAttribute("weeklyReport", weeklyReport);
		request.setAttribute("readonly", false);
		request.setAttribute("work", work);

		return mapping.findForward("weeklyReportForm");
	}

	/**
	 * 프로젝트별  주간보고 할당 (mm형 프로젝트의 해당 주의 해당 프로젝트 주간보고 할당) 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void assignWeeklyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			//주간업무보고 생성 메소드
			int res = this.getWeeklyReportScheduleManager().assignWeeklyReportMissed(projectCode);
			map.put("res", res + "");
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 주간보고 할당 (mm형 프로젝트의 해당 주의 해당 프로젝트 주간보고 할당) 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void assignWeeklyReportByProjectCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, String> map = new HashMap<String, String>();
		
		try {
			//주간업무보고 생성 메소드
			int res = this.getWeeklyReportScheduleManager().assignWeeklyReportOnThieWeek(projectCode);
			map.put("res", res + "");
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 작업을 임시 저장 한다
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveProjectReportTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		WeeklyReportForm weeklyReportForm = (WeeklyReportForm) form;
		WeeklyReport weeklyReport = WeeklyReport.valueOf(weeklyReportForm);

		try {
			if (weeklyReportForm.getAttachFileId() != null && weeklyReportForm.getAttachFileId().length > 0) {
				weeklyReportForm.setTaskId("wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq());
				this.getAttachManager().deleteAllInTask(weeklyReportForm.getTaskId());
				this.getAttachManager().insert(weeklyReportForm);
			}
			this.getWeeklyReportManager().storeWeeklyReport(weeklyReport);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 작업을 위임
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void entrustProjectReportTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		WeeklyReportForm weeklyReportForm = (WeeklyReportForm) form;
		WeeklyReport weeklyReport = WeeklyReport.valueOf(weeklyReportForm);
		
		try {
			if (weeklyReportForm.getAttachFileId() != null && weeklyReportForm.getAttachFileId().length > 0) {
				weeklyReportForm.setTaskId("wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq());
				this.getAttachManager().deleteAllInTask(weeklyReportForm.getTaskId());
				this.getAttachManager().insert(weeklyReportForm);
			}

			this.getWeeklyReportManager().entrustWeeklyReport(weeklyReport);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}



	/**
	 * 작업을 실행 혹은 반려를 후 해당 사람에게 할당 및 처리내용 저장
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void executeProjectReportTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		WeeklyReportForm weeklyReportForm = (WeeklyReportForm) form;
		WeeklyReport weeklyReport = WeeklyReport.valueOf(weeklyReportForm);
		//activity 추가
		weeklyReport.setActivity(weeklyReportForm.getActivity());
		
		try {
			if (weeklyReportForm.getAttachFileId() != null && weeklyReportForm.getAttachFileId().length > 0) {
				weeklyReportForm.setTaskId("wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq());
				this.getAttachManager().deleteAllInTask(weeklyReportForm.getTaskId());
				this.getAttachManager().insert(weeklyReportForm);
			}
			
			this.getWeeklyReportManager().executeProjectReport(weeklyReport);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	

	/**
	 * 작업을 삭제 한다
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void removeProjectReportTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		WeeklyReportForm weeklyReportForm = (WeeklyReportForm) form;
		WeeklyReport weeklyReport = WeeklyReport.valueOf(weeklyReportForm);
		try {
			this.getWeeklyReportManager().deleteWeeklyReport(weeklyReportForm.getProjectCode(), weeklyReportForm.getAssignYear(), weeklyReportForm.getAssignMonth(), weeklyReportForm.getAssignWeekOfMonth());
			weeklyReportForm.setTaskId("wreport_" + weeklyReport.getProjectCode() + "_" + weeklyReport.getTaskFormTypeId() + "_" + weeklyReport.getSeq());
			this.getWorklistManager().terminatWork(weeklyReportForm.getWorkId());
			this.getAttachManager().deleteAllInTask(weeklyReportForm.getTaskId());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 작업을 삭제 한다 (리스트)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void removeProjectReportTask2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		/*WeeklyReportForm weeklyReportForm = (WeeklyReportForm) form;
		WeeklyReport weeklyReport = WeeklyReport.valueOf(weeklyReportForm);*/
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String assignYear = ServletRequestUtils.getRequiredStringParameter(request, "assignYear");
		String assignMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignMonth");
		String assignWeekOfMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignWeekOfMonth");
		String assignId = ServletRequestUtils.getRequiredStringParameter(request, "assignId");
		
		try {
			this.getWeeklyReportManager().deleteWeeklyReport(projectCode, assignYear, assignMonth, assignWeekOfMonth);
			this.getWorklistManager().terminatWork(assignId);
			this.getAttachManager().deleteAllInTask("wreport_" + projectCode + "_" + "4028809e0a20e689010a2b4be76c2226" + "_" + (assignYear + "" + assignMonth + "" + assignWeekOfMonth));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public ActionForward loadProjectMemberPopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>(); 
			filters.put("projectCode", projectCode);
			filters.put("PLMB", "PLMB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList projectMemberList = vlh.getValueList("projectMemberListForProjectReportSelect", info);

			request.setAttribute("projectCode", projectCode);
			request.setAttribute("projectMemberList", projectMemberList);
			request.setAttribute("businessTypeCode", this.getProjectMasterInfoManager().getProjectBusinessTypeCode(projectCode));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("projectMemberPopup");
	}
	
	public ActionForward getWeeklyReportStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String keywordType = ServletRequestUtils.getStringParameter(request, "keywordType", "");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
		String fromDate = ServletRequestUtils.getStringParameter(request, "fromDate", "");
		String toDate = ServletRequestUtils.getStringParameter(request, "toDate", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			
			
			if(keywordType.equals("PNAME")) {				
				if (!keyword.equals("")) {
					filters.put("projectName", "%" + keyword + "%");
					request.setAttribute("keyword", keyword);
				}
			} else if(keywordType.equals("PCODE")) {
				if (!keyword.equals("")) {
					filters.put("projectCode", "%" + keyword + "%");
					request.setAttribute("keyword", keyword);
				}				
			}
			if (!fromDate.equals("")) {
				filters.put("fromDate", StringUtil.replace(fromDate, "-", ""));
				request.setAttribute("fromDate", fromDate);
			}
			if (!toDate.equals("")) {
				filters.put("toDate", StringUtil.replace(toDate, "-", ""));
				request.setAttribute("toDate", toDate);
			}
			if (!projectState.equals("")) {
				filters.put("projectState", projectState);
				request.setAttribute("projectState", projectState);
			}
			if (!runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
				request.setAttribute("runningDeptCode", runningDeptCode);
			}

			info.setFilters(filters);

			ValueList result = vlh.getValueList("getWeeklyReportStatus", info);
			request.setAttribute("weeklyReportStatus", result);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("weeklyReportStatus");
	}
	
	/////////////////////////////
	
	
	public WeeklyReportScheduleManager getWeeklyReportScheduleManager() {
		return weeklyReportScheduleManager;
	}

	public void setWeeklyReportScheduleManager(
			WeeklyReportScheduleManager weeklyReportScheduleManager) {
		this.weeklyReportScheduleManager = weeklyReportScheduleManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public WeeklyReportManager getWeeklyReportManager() {
		return weeklyReportManager;
	}

	public void setWeeklyReportManager(WeeklyReportManager weeklyReportManager) {
		this.weeklyReportManager = weeklyReportManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}
	
	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}


	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}


	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

}
