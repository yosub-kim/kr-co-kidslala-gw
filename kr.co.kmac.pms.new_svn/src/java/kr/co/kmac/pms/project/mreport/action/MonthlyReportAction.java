package kr.co.kmac.pms.project.mreport.action;

import java.util.HashMap;
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
import kr.co.kmac.pms.project.mreport.data.MonthlyReport;
import kr.co.kmac.pms.project.mreport.form.MonthlyReportForm;
import kr.co.kmac.pms.project.mreport.manager.MonthlyReportManager;
import kr.co.kmac.pms.service.scheduler.manager.MonthlyReportScheduleManager;
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
 * @struts.action name="monthlyReportAction" path="/action/MonthlyReportAction" parameter="mode" scope="request"
 * 
 * @struts.action-forward name="monthlyReportList" path="/project/mreport/monthlyReportList.jsp" redirect="false"
 * @struts.action-forward name="monthlyReportStatus" path="/project/mreport/monthlyReportStatus.jsp" redirect="false"
 * @struts.action-forward name="monthlyReportForm" path="/sanction/sanctionForm/monthlyReportForm.jsp" redirect="false"
 * @struts.action-forward name="projectMemberPopup" path="/project/mreport/projectMemberPopup.jsp" redirect="false"
 */
public class MonthlyReportAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MonthlyReportAction.class);

	private ProjectMasterInfoManager projectMasterInfoManager;
	private MonthlyReportManager monthlyReportManager;
	private MonthlyReportScheduleManager monthlyReportScheduleManager;
	private WorklistManager worklistManager;
	private AttachTemplateManager attachTemplateManager;
	private AttachManager attachManager;

	public ActionForward getMonthlyReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String toDate = ServletRequestUtils.getStringParameter(request, "toDate", "");
		String fromDate = ServletRequestUtils.getStringParameter(request, "fromDate", "");
		String writerName = ServletRequestUtils.getStringParameter(request, "writerName", "");
		String state = ServletRequestUtils.getStringParameter(request, "state", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "");

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

			ValueList result = vlh.getValueList("getMonthlyReportList", info);
			request.setAttribute("monthlyReportList", result);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("viewMode", viewMode);
			ProjectSimpleInfo psi = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
			request.setAttribute("projectName", psi.getProjectName());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("monthlyReportList");
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
	public ActionForward getMonthlyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String assignYear = ServletRequestUtils.getRequiredStringParameter(request, "assignYear");
		String assignMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignMonth");
		String writerSsn = ServletRequestUtils.getRequiredStringParameter(request, "writerSsn");

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		MonthlyReport monthlyReport = monthlyReportManager.getMonthlyReport(projectCode, assignYear, assignMonth, writerSsn);
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute(
				"fileList",
				this.getAttachManager()
						.selectListForTask(wc,
								"mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_" + monthlyReport.getSeq(),
								null, null).getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("monthlyReport", monthlyReport);
		request.setAttribute("readonly", true);

		return mapping.findForward("monthlyReportForm");
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
	public ActionForward getMonthlyReportByWorkId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		Work work = this.getWorklistManager().getWork(workId);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		MonthlyReport monthlyReport = this.getMonthlyReportManager().getMonthlyReport(
				work.getRefWorkId1(), 
				work.getRefWorkId3().substring(0, 4),
				work.getRefWorkId3().substring(4, 6), 
				work.getRefWorkId3().substring(7));

		monthlyReport.setWorkId(workId);
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute(
				"fileList",
				this.getAttachManager()
						.selectListForTask(wc,
								"mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_" + monthlyReport.getSeq(),
								null, null).getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("monthlyReport", monthlyReport);
		request.setAttribute("readonly", false);
		request.setAttribute("work", work);

		return mapping.findForward("monthlyReportForm");
	}

	/**
	 * 프로젝트별 주간보고 할당 (mm형 프로젝트의 해당 주의 해당 프로젝트 주간보고 할당)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void assignMonthlyReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			// 주간업무보고 생성 메소드
			int res = this.getMonthlyReportScheduleManager().assignMonthlyReportMissed(projectCode);
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
		MonthlyReportForm monthlyReportForm = (MonthlyReportForm) form;
		MonthlyReport monthlyReport = MonthlyReport.valueOf(monthlyReportForm);

		try {
			if (monthlyReportForm.getAttachFileId() != null && monthlyReportForm.getAttachFileId().length > 0) {
				monthlyReportForm.setTaskId("mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_"
						+ monthlyReport.getSeq());
				this.getAttachManager().deleteAllInTask(monthlyReportForm.getTaskId());
				this.getAttachManager().insert(monthlyReportForm);
			}
			this.getMonthlyReportManager().storeMonthlyReport(monthlyReport);
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
		MonthlyReportForm monthlyReportForm = (MonthlyReportForm) form;
		MonthlyReport monthlyReport = MonthlyReport.valueOf(monthlyReportForm);

		try {
			if (monthlyReportForm.getAttachFileId() != null && monthlyReportForm.getAttachFileId().length > 0) {
				monthlyReportForm.setTaskId("mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_"
						+ monthlyReport.getSeq());
				this.getAttachManager().deleteAllInTask(monthlyReportForm.getTaskId());
				this.getAttachManager().insert(monthlyReportForm);
			}

			this.getMonthlyReportManager().entrustMonthlyReport(monthlyReport);
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
		MonthlyReportForm monthlyReportForm = (MonthlyReportForm) form;
		MonthlyReport monthlyReport = MonthlyReport.valueOf(monthlyReportForm);

		try {
			if (monthlyReportForm.getAttachFileId() != null && monthlyReportForm.getAttachFileId().length > 0) {
				monthlyReportForm.setTaskId("mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_"
						+ monthlyReport.getSeq());
				this.getAttachManager().deleteAllInTask(monthlyReportForm.getTaskId());
				this.getAttachManager().insert(monthlyReportForm);
			}

			this.getMonthlyReportManager().executeProjectReport(monthlyReport);
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
		MonthlyReportForm monthlyReportForm = (MonthlyReportForm) form;
		MonthlyReport monthlyReport = MonthlyReport.valueOf(monthlyReportForm);

		try {
			this.getMonthlyReportManager().deleteMonthlyReport(monthlyReportForm.getProjectCode(), monthlyReportForm.getAssignYear(),
					monthlyReportForm.getAssignMonth(), monthlyReport.getWriterSsn());
			monthlyReportForm.setTaskId("mreport_" + monthlyReport.getProjectCode() + "_" + monthlyReport.getTaskFormTypeId() + "_"
					+ monthlyReport.getSeq());
			this.getWorklistManager().terminatWork(monthlyReportForm.getWorkId());
			this.getAttachManager().deleteAllInTask(monthlyReportForm.getTaskId());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 작업을 삭제 한다(리스트)
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
	/*	MonthlyReportForm monthlyReportForm = (MonthlyReportForm) form;
		MonthlyReport monthlyReport = MonthlyReport.valueOf(monthlyReportForm);*/
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String assignYear = ServletRequestUtils.getRequiredStringParameter(request, "assignYear");
		String assignMonth = ServletRequestUtils.getRequiredStringParameter(request, "assignMonth");
		String assignDraftUserId = ServletRequestUtils.getRequiredStringParameter(request, "assignDraftUserId");
		String assignId = ServletRequestUtils.getRequiredStringParameter(request, "assignId");

		try {
			this.getMonthlyReportManager().deleteMonthlyReport(projectCode, assignYear, assignMonth, assignDraftUserId);
			this.getWorklistManager().terminatWork(assignId);
			this.getAttachManager().deleteAllInTask("mreport_" + projectCode + "_" + assignId + "_" + (assignYear + "" + assignMonth + "|" + assignDraftUserId));
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

	public ActionForward getMonthlyReportStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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

			if (keywordType.equals("PNAME")) {
				if (!keyword.equals("")) {
					filters.put("projectName", "%" + keyword + "%");
					request.setAttribute("keyword", keyword);
				}
			} else if (keywordType.equals("PCODE")) {
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

			ValueList result = vlh.getValueList("getMonthlyReportStatus", info);
			request.setAttribute("monthlyReportStatus", result);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("monthlyReportStatus");
	}

	// ///////////////////////////

	public MonthlyReportScheduleManager getMonthlyReportScheduleManager() {
		return monthlyReportScheduleManager;
	}

	public void setMonthlyReportScheduleManager(MonthlyReportScheduleManager monthlyReportScheduleManager) {
		this.monthlyReportScheduleManager = monthlyReportScheduleManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public MonthlyReportManager getMonthlyReportManager() {
		return monthlyReportManager;
	}

	public void setMonthlyReportManager(MonthlyReportManager monthlyReportManager) {
		this.monthlyReportManager = monthlyReportManager;
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
