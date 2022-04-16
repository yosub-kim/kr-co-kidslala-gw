package kr.co.kmac.pms.sanction.preport.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.sanction.preport.form.ProjectReportContentForm;
import kr.co.kmac.pms.sanction.preport.manager.ProjectReportManager;
import kr.co.kmac.pms.service.scheduler.manager.ProjectReportScheduleManager;

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
 * 프로젝트 전자결재 업무함
 */
/**
 * @struts.action name="projectReportContentAction"			path="/action/ProjectReportContentAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectReportForm"			path="/sanction/sanctionForm/projectReportForm.jsp"	redirect="false"
 * @struts.action-forward name="projectReportMobileForm"	path="/m/web/preport/preportWrite.jsp"				redirect="false"
 */
public class ProjectReportContentAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectReportContentAction.class);

	private WorklistManager worklistManager;
	private ProjectReportManager projectReportManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectReportScheduleManager projectReportScheduleManager;

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
		ProjectReportContentForm projectReportContentForm = (ProjectReportContentForm) form;
		ProjectReportContent projectReportContent = ProjectReportContent.valueOf(projectReportContentForm);

		try {
			if (projectReportContentForm.getAttachFileId() != null && projectReportContentForm.getAttachFileId().length > 0) {
				projectReportContentForm.setTaskId("preport_" + projectReportContent.getProjectCode() + "_"
						+ projectReportContent.getTaskFormTypeId() + "_" + projectReportContent.getSeq());
				this.getAttachManager().deleteAllInTask(projectReportContentForm.getTaskId());
				this.getAttachManager().insert(projectReportContentForm);
			}

			this.getProjectReportManager().executeProjectReport(projectReportContent);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 지도일지 일괄 승인 처리 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void executeProjectReportTaskForList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] workId = ServletRequestUtils.getRequiredStringParameters(request, "workId");
		String[] appYN = ServletRequestUtils.getRequiredStringParameters(request, "appYN");
		String[] payAmount = ServletRequestUtils.getRequiredStringParameters(request, "payAmount");
		
		HashMap<String, String> map = new HashMap<String, String>();

		try {
			if(workId != null && workId.length > 0){
				for (int i = 0; i < workId.length; i++) {
					if(appYN[i].equals("Y") || appYN[i].equals("N")){
						Work work = this.getWorklistManager().getWork(workId[i]);
						ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent1(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
						projectReportContent.setWorkId(workId[i]);
						projectReportContent.setAppYN(appYN[i]);
						projectReportContent.setPayAmount(payAmount[i]);
						this.getProjectReportManager().executeProjectReport(projectReportContent);
					}
				}
			}
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
		ProjectReportContentForm projectReportContentForm = (ProjectReportContentForm) form;
		ProjectReportContent projectReportContent = ProjectReportContent.valueOf(projectReportContentForm);

		try {
			if (projectReportContentForm.getAttachFileId() != null && projectReportContentForm.getAttachFileId().length > 0) {
				projectReportContentForm.setTaskId("preport_" + projectReportContent.getProjectCode() + "_"
						+ projectReportContent.getTaskFormTypeId() + "_" + projectReportContent.getSeq());
				this.getAttachManager().deleteAllInTask(projectReportContentForm.getTaskId());
				this.getAttachManager().insert(projectReportContentForm);
			}

			this.getProjectReportManager().updateProjectReport(projectReportContent);
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
		ProjectReportContentForm projectReportContentForm = (ProjectReportContentForm) form;
		try {
			this.getProjectReportManager().deleteProjectReport1(projectReportContentForm.getProjectCode(),
					projectReportContentForm.getTaskFormTypeId(), projectReportContentForm.getSeq());
			this.getAttachManager().deleteAllInTask(projectReportContentForm.getTaskId());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 지도일지를 한 건을 반환
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getProjectReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		Work work = this.getWorklistManager().getWork(workId);
		ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent1(work.getRefWorkId1(),
				work.getRefWorkId2(), work.getRefWorkId3());
		projectReportContent.setWorkId(workId);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		request.setAttribute("preportEssentialFlag", "selected");
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(
				wc,
				"preport_" + projectReportContent.getProjectCode() + "_" + projectReportContent.getTaskFormTypeId() + "_"
						+ projectReportContent.getSeq(), null, null).getList());
		request.setAttribute("projectReportContent", projectReportContent);
		return mapping.findForward("projectReportForm");
	}
	
	/**
	 * 지도일지 미리보기
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getProjectReportContnetByWorkId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		Work work = this.getWorklistManager().getWork(workId);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent1(work.getRefWorkId1(),
					work.getRefWorkId2(), work.getRefWorkId3());
			projectReportContent.setWorkId(workId);
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			
			map.put("projectReportContent", projectReportContent);
			map.put("fileList", this.getAttachManager().selectListForTask(
					wc,
					"preport_" + projectReportContent.getProjectCode() + "_" + projectReportContent.getTaskFormTypeId() + "_"
							+ projectReportContent.getSeq(), null, null).getList());
			request.setAttribute("projectReportContent", projectReportContent);
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(
					wc,
					"preport_" + projectReportContent.getProjectCode() + "_" + projectReportContent.getTaskFormTypeId() + "_"
							+ projectReportContent.getSeq(), null, null).getList());
	
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}		
	}
	/**
	 * 지도일지를 한 건을 반환
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getProjectReportForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getRequiredStringParameter(request, "workId");
		Work work = this.getWorklistManager().getWork(workId);
		ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent1(work.getRefWorkId1(),
				work.getRefWorkId2(), work.getRefWorkId3());
		projectReportContent.setWorkId(workId);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		request.setAttribute("preportEssentialFlag", "selected");
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(
				wc,
				"preport_" + projectReportContent.getProjectCode() + "_" + projectReportContent.getTaskFormTypeId() + "_"
						+ projectReportContent.getSeq(), null, null).getList());
		request.setAttribute("projectReportContent", projectReportContent);
		return mapping.findForward("projectReportMobileForm");
	}

	public void createProjectReportContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			this.getProjectReportScheduleManager().assignProjectReportUntilToday(projectCode);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void entrustProjectReportTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ProjectReportContentForm projectReportContentForm = (ProjectReportContentForm) form;
		ProjectReportContent projectReportContent = ProjectReportContent.valueOf(projectReportContentForm);

		try {
			if (projectReportContentForm.getAttachFileId() != null && projectReportContentForm.getAttachFileId().length > 0) {
				projectReportContentForm.setTaskId("preport_" + projectReportContent.getProjectCode() + "_"
						+ projectReportContent.getTaskFormTypeId() + "_" + projectReportContent.getSeq());
				this.getAttachManager().deleteAllInTask(projectReportContentForm.getTaskId());
				this.getAttachManager().insert(projectReportContentForm);
			}

			this.getProjectReportManager().entrustProjectReport(projectReportContent);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ProjectReportManager getProjectReportManager() {
		return projectReportManager;
	}

	public void setProjectReportManager(ProjectReportManager projectReportManager) {
		this.projectReportManager = projectReportManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public ProjectReportScheduleManager getProjectReportScheduleManager() {
		return projectReportScheduleManager;
	}

	public void setProjectReportScheduleManager(ProjectReportScheduleManager projectReportScheduleManager) {
		this.projectReportScheduleManager = projectReportScheduleManager;
	}

}
