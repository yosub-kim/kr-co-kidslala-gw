package kr.co.kmac.pms.project.master.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectBudjetInfoAction" path="/action/ProjectBudjetInfoAction" parameter="mode" scope="request"
 * @struts.action-forward name="budgetMasterInfo" path="/project/budget/budgetMasterInfo.jsp" redirect="false"
 * @struts.action-forward name="budgetDetailInfo" path="/project/budget/budgetDetailInfo.jsp" redirect="false"
 */
public class ProjectBudjetInfoAction extends DispatchActionSupport {

	// private static final Log logger = LogFactory.getLog(ProjectBudjetInfoAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;

	public ActionForward goProjectBudgetInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");

		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		return mapping.findForward("budgetMasterInfo");
	}
	
	public ActionForward goProjectBudgetInfo2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");

		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		return mapping.findForward("budgetMasterInfo2");
	}
	
	public ActionForward goProjectBudgetDetailInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		return mapping.findForward("budgetDetailInfo");
	}

	public void goProjectEntNos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String[] entNos = this.getProjectMasterInfoManager().getProjectEntNo(projectCode);
			
			map.put("entNoNew", entNos[0]);
			map.put("entNoOld", entNos[1]);
			
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			e.printStackTrace();
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

}
