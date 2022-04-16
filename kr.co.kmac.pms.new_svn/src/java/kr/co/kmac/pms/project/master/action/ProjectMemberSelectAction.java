package kr.co.kmac.pms.project.master.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectMemberSelectAction" path="/action/ProjectMemberSelectAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectMemberPopupForProjectReport" path="/project/master/projectMemberPopupForProjectReport.jsp" redirect="false"
 */
public class ProjectMemberSelectAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectMemberSelectAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;

	/**
	 * 지도일정관리를 위한 프로젝트 멤버 리스트(여러명선택) - PM, PL, MB
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectProjectMemberListForProjectReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String projectCode = request.getParameter("projectCode");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PMPLMB", "PMPLMB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);

			ValueList result = vlh.getValueList("projectMemberListSelect", info);

			String businessTypeCode = this.getProjectMasterInfoManager().getProjectBusinessTypeCode(projectCode);

			request.setAttribute("list", result);
			request.setAttribute("businessTypeCode", businessTypeCode);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectMemberPopupForProjectReport");
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
