package kr.co.kmac.pms.project.endprocess.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndProcessManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndTaskAssignManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectEndProcessAction" path="/action/ProjectEndProcessAction" parameter="mode" scope="request"
 */
public class ProjectEndProcessAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectEndProcessAction.class);
	private ProjectEndProcessManager projectEndProcessManager;
	private ProjectEndTaskAssignManager projectEndTaskAssignManager;
	
	public void assignEndProcessTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		try {
			this.getProjectEndProcessManager().doEndProcess(projectCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	public void assignReviewTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		try {
			if (this.getProjectEndTaskAssignManager().isRollingAndRateingFinished(projectCode)) {
				this.getProjectEndTaskAssignManager().assignEndProcessTask(projectCode);
				AjaxUtil.successWrite(response);
			} else {
				AjaxUtil.failWrite(response);
			}
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
		}
	}
	
	public void reSendRncMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		try {
			this.getProjectEndProcessManager().doResendCSMail(projectCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			log.error(e);
			AjaxUtil.failWrite(response);
		}
	}
	
	public ProjectEndProcessManager getProjectEndProcessManager() {
		return projectEndProcessManager;
	}

	public void setProjectEndProcessManager(ProjectEndProcessManager projectEndProcessManager) {
		this.projectEndProcessManager = projectEndProcessManager;
	}

	public ProjectEndTaskAssignManager getProjectEndTaskAssignManager() {
		return projectEndTaskAssignManager;
	}

	public void setProjectEndTaskAssignManager(
			ProjectEndTaskAssignManager projectEndTaskAssignManager) {
		this.projectEndTaskAssignManager = projectEndTaskAssignManager;
	}
}
