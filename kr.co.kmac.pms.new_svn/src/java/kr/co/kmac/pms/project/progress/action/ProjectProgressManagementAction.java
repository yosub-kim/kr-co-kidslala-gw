package kr.co.kmac.pms.project.progress.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.progress.data.GanttChart;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;
import kr.co.kmac.pms.project.progress.data.ProjectProgressContent;
import kr.co.kmac.pms.project.progress.form.ProjectProgressContentForm;
import kr.co.kmac.pms.project.progress.manager.ProjectProgressManager;

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
 * @struts.action name="projectProgressManagementAction" path="/action/ProjectProgressManagementAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectProgressContent" path="/project/progress/projectProgressContent.jsp" redirect="false"
 */
public class ProjectProgressManagementAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectProgressManagementAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectProgressManager projectProgressManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;

	public ActionForward loadProjectProgessContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String contentId = ServletRequestUtils.getRequiredStringParameter(request, "contentId");
		String workSeq = ServletRequestUtils.getRequiredStringParameter(request, "workSeq");
		boolean readOnly = ServletRequestUtils.getRequiredBooleanParameter(request, "readOnly");

		Project project = this.getProjectMasterInfoManager().getProject(projectCode);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		// ----파일관련 시작
		if(!readOnly){
			request.setAttribute("fileMode", "WRITE");			
		}else{
			request.setAttribute("fileMode", "READ");			
		}
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, project.getBusinessTypeCode())
				.getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, projectCode + "_" + contentId, null, null).getList());
		request.setAttribute("attachBusType", project.getBusinessTypeCode());
		// ----파일관련 끝

		ProjectProgressContent projectProgressContent = this.getProjectProgressManager().getProjectProgressContent(projectCode, contentId);
		projectProgressContent.setWorkSeq(workSeq);
		request.setAttribute("readOnly", readOnly);
		request.setAttribute("projectProgressContent", projectProgressContent);

		return mapping.findForward("projectProgressContent");
	}

	// 일정 저장
	public void storeProjectProgess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		int[] workSeqArr = ServletRequestUtils.getRequiredIntParameters(request, "workSeq");
		String[] workNameArr = ServletRequestUtils.getRequiredStringParameters(request, "workName");
		/*String[] startDateArr = ServletRequestUtils.getRequiredStringParameters(request, "");
		String[] endDateArr = ServletRequestUtils.getRequiredStringParameters(request, "");*/
		String[] realEndDateArr = ServletRequestUtils.getRequiredStringParameters(request, "realEndDate");
		String[] contentIdArr = ServletRequestUtils.getRequiredStringParameters(request, "contentId");
		// String ganttChartMsg = ServletRequestUtils.getRequiredStringParameter(request, "ganttChart");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			// GanttChart ganttChart = GanttChart.fromXML(ganttChartMsg, ProjectProgressManagementAction.class.getClassLoader());
			// this.getProjectProgressManager().storeProjectProgressExe(ganttChart.getProjectCode(), ganttChart.getProjectProgressList());
			List<ProjectProgress> projectProgressList = new ArrayList<ProjectProgress>();
			for (int i = 0; i < workSeqArr.length; i++) {
				ProjectProgress projectProgress = new ProjectProgress();
				projectProgress.setProjectCode(projectCode);
				projectProgress.setWorkSeq(workSeqArr[i]);
				projectProgress.setWorkName(workNameArr[i]);
				// 일정 주석
				projectProgress.setStartDate("");
				projectProgress.setEndDate("");
				projectProgress.setRealEndDate("");
				projectProgress.setContentId(contentIdArr[i]);
				projectProgressList.add(projectProgress);
			}

			this.getProjectProgressManager().storeProjectProgressExe(projectCode, projectProgressList);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	// 업무 내용 저장
	public void storeProjectProgessContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ProjectProgressContentForm projectProgressContentForm = (ProjectProgressContentForm) form;
		projectProgressContentForm.setCreatorId(SessionUtils.getUsername(request));

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			AttachForm attachForm = (AttachForm) projectProgressContentForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager()
						.deleteAllInTask(projectProgressContentForm.getProjectCode() + "_" + projectProgressContentForm.getContentId());
				attachForm.setTaskId(projectProgressContentForm.getProjectCode() + "_" + projectProgressContentForm.getContentId());
				this.getAttachManager().insert(attachForm);
			}

			this.getProjectProgressManager().insertProjectProgressContent((ProjectProgressContent) projectProgressContentForm);
			boolean isFinished = this.getProjectProgressManager().isProjectProgressFinish(projectProgressContentForm.getProjectCode());
			
			map.put("isFinished", isFinished);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 프로젝트 평가 가능한지 여부
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void canDoProjectEval(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
			List<ProjectProgress> progressList = this.getProjectProgressManager().getProjectProgressExe(projectCode);
			boolean flag = true;
			for (ProjectProgress projectProgress : progressList) {
				if (projectProgress.getRealEndDate() == null || projectProgress.getRealEndDate().equals("")) {
					flag = false;
					break;
				}
			}
			map.put("canFinish", new Boolean(flag));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}

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

	/**
	 * @return the projectProgressManager
	 */
	public ProjectProgressManager getProjectProgressManager() {
		return projectProgressManager;
	}

	/**
	 * @param projectProgressManager the projectProgressManager to set
	 */
	public void setProjectProgressManager(ProjectProgressManager projectProgressManager) {
		this.projectProgressManager = projectProgressManager;
	}

	/**
	 * @return the attachManager
	 */
	public AttachManager getAttachManager() {
		return attachManager;
	}

	/**
	 * @param attachManager the attachManager to set
	 */
	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	/**
	 * @return the attachTemplateManager
	 */
	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	/**
	 * @param attachTemplateManager the attachTemplateManager to set
	 */
	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

}
