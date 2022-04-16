package kr.co.kmac.pms.project.master.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;
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
 * @struts.action name="registerProjectAction" path="/action/RegisterProjectAction" parameter="mode" scope="request"
 * @struts.action-forward name="registerProjectList" path="/project/register/registerProjectList.jsp" redirect="false"
 */
public class RegisterProjectAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(RegisterProjectAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private IOrgUnitManager orgUnitManager;

	public ActionForward getRegisterProjectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "");

		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			filters.put("createrSsn", SessionUtils.getUsername(request));
			filters.put("projectName", "%" + projectName + "%");
			info.setFilters(filters);
			if (projectState.equals("ing")) {
				filters.put("ing", "1");
			} else if (projectState.equals("sanction")) {
				filters.put("sanction", "2");
			} else if (projectState.equals("finished")) { 
				filters.put("finished", "ohters");
			}

			ValueList valueList = vlh.getValueList("registerProjectList", info);
			request.setAttribute("list", valueList);
			request.setAttribute("projectName", projectName);
			request.setAttribute("projectState", projectState);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		WebApplicationContext wc2;
		String pageSize2 = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo2 = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectName2 = ServletRequestUtils.getStringParameter(request, "projectName", "");

		try {
			wc2 = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh2 = (ValueListHandler) wc2.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_PAGE, pageNo2);
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, pageSize2);

			User user = SessionUtils.getUserObjext();
			orgUnitManager.populateGroup(user);
			List<Group> groupList = user.getGroupList();
			String buCode = "~";
			if (groupList.size() > 0) {
				buCode = ((Group) (user.getGroupList().get(0))).getId();
			}
			// CFO 과제관리 PJ를 등록하기 위한 예외처리(담당1팀으로 코드값 치환, CFO 부서코드가 PMS 와 회계 간에 서로 다르기 때문)
			// CFO -> CBO 로 명칭 변경
			// CBO가 직접 프로젝트 등록하는 경우는 L&D4팀 프로젝트 일 때만 이므로 담당4팀으로 코드 치환하는 방식으로 예외 처리를 변경함. 2014-11-26   
			if (buCode.endsWith("0")) {
				buCode = buCode.substring(0, 3) + "4";
			}

			filters2.put("buCode", buCode);
			filters2.put("projectName", projectName);
			//filters.put("createrSsn", createrSsn);
			info2.setFilters(filters2);

			ValueList valueList2 = vlh2.getValueList("erpProjectList", info2);
			request.setAttribute("list2", valueList2);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("registerProjectList");
	}

	public void registerProjectCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("isRegistered", new Boolean(this.getProjectMasterInfoManager().isRegisteredProject(projectCode)));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void isEduProjectConnected(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("isConnected", new Boolean(this.getProjectMasterInfoManager().isConnectedProject(projectCode)).toString());
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

	public IOrgUnitManager getOrgUnitManager() {
		return orgUnitManager;
	}

	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}
}
