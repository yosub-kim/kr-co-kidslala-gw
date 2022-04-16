package kr.co.kmac.pms.project.progress.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.progress.data.ProjectProgressEntity;
import kr.co.kmac.pms.project.progress.manager.ProjectProgressManager;
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
 * @struts.action name="projectProgressInfoAction" path="/action/ProjectProgressInfoAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectProgressEntityXml" path="/project/progress/projectProgressEntityXml.jsp" redirect="false"
 * @struts.action-forward name="projectProgressInfo" path="/project/progress/projectProgressInfo.jsp" redirect="false"
 */
public class ProjectProgressInfoAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectProgressInfoAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectProgressManager projectProgressManager;

	/**
	 * 간트차트를 로딩시키는 Action<br>
	 * 인자로 넘어갈 수 있는것은 아래와 같다. <br>
	 * <li> viewMode : 현재 페이지가 어디서 부터 온 것인지(ex. 등록화면, 마이프로젝트, 프로젝트현황, 결재 ...
	 * <li> showToolbar : 간트차트에 툴바의 visible 속성, 프로젝트 시작일, 종료일 필드의 editable 속성 
	 * <li> contentEdit : 간트차트에 내용편집 버튼의 visible 속성
	 * <li> showEndDate : 간트차트에 실완료일 필드의 visible 속성
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProjectProgressInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		Project project = this.getProjectMasterInfoManager().getProject(projectCode);

		if (project.getProjectState().equals("1")) {
			if (viewMode.equals("register")) {
				request.setAttribute("showToolbar", true);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			} else if (viewMode.equals("myProject")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			} else if (viewMode.equals("projectSearch")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			}
		} else if (project.getProjectState().equals("2")) {
			if (ServletRequestUtils.getBooleanParameter(request, "readonly", false)) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			} else {
				request.setAttribute("showToolbar", true);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			}
			request.setAttribute("contentEdit", false);
			request.setAttribute("showEndDate", false);
		} else if (project.getProjectState().equals("3")) {
			if (viewMode.equals("register")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			} else if (viewMode.equals("myProject")) {
				if (ServletRequestUtils.getBooleanParameter(request, "readonly", false)) {
					request.setAttribute("showToolbar", false);
					request.setAttribute("contentEdit", false);
					request.setAttribute("contentShow", false);
					request.setAttribute("showEndDate", false);
				} else {
					request.setAttribute("showToolbar", true);
					request.setAttribute("contentEdit", true);
					request.setAttribute("contentShow", true);
					request.setAttribute("showEndDate", true);
				}
			} else if (viewMode.equals("lsanction")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", true);
				request.setAttribute("showEndDate", false);
			} else if (viewMode.equals("projectSearch")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", true);
				request.setAttribute("showEndDate", true);
			}
		} else if (project.getProjectState().equals("4") || project.getProjectState().equals("5") || project.getProjectState().equals("6")) {
			if (viewMode.equals("register")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", false);
				request.setAttribute("showEndDate", false);
			} else if (viewMode.equals("myProject")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", true);
				request.setAttribute("showEndDate", true);
			} else if (viewMode.equals("lsanction")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", true);
				request.setAttribute("showEndDate", true);
			} else if (viewMode.equals("projectSearch")) {
				request.setAttribute("showToolbar", false);
				request.setAttribute("contentEdit", false);
				request.setAttribute("contentShow", true);
				request.setAttribute("showEndDate", true);
			}
		}
		
		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("projectCode", projectCode);
			filters.put("level", "0");
			info.setFilters(filters);
			List<ProjectProgressEntity> list = vlh.getValueList("progessEntityListSelect", info).getList();

			List<ProjectProgressEntity> hierarchicalList = new ArrayList<ProjectProgressEntity>();
			if (list != null && list.size() > 0) {
				for (ProjectProgressEntity projectProgressEntity : list) {
					Map<String, String> filters1 = new HashMap<String, String>();
					filters1.put(ValueListInfo.PAGING_PAGE, "1");
					filters1.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
					filters1.put("projectCode", projectCode);
					filters1.put("level", "1");
					filters1.put("parentWorkSeq", String.valueOf(projectProgressEntity.getWorkSeq()));
					info.setFilters(filters1);
					projectProgressEntity.setProjectProgressEntityList(vlh.getValueList("progessEntityListSelect", info).getList());
					hierarchicalList.add(projectProgressEntity);
				}
			}

			request.setAttribute("list", hierarchicalList);
			request.setAttribute("maxWorkSeq", String.valueOf(this.getProjectProgressManager().getMaxWorkSeq(projectCode)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", project);
		request.setAttribute("viewMode", ServletRequestUtils.getRequiredStringParameter(request, "viewMode"));
		request.setAttribute("readOnly", ServletRequestUtils.getBooleanParameter(request, "readonly", false));

		return mapping.findForward("projectProgressInfo");
	}

		
	@SuppressWarnings("unchecked")
	public ActionForward getProjectProgessEntityList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext wc;
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			Project project = this.getProjectMasterInfoManager().getProject(projectCode);

			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("projectCode", projectCode);
			filters.put("level", "0");
			info.setFilters(filters);
			List<ProjectProgressEntity> list = vlh.getValueList("progessEntityListSelect", info).getList();

			List<ProjectProgressEntity> hierarchicalList = new ArrayList<ProjectProgressEntity>();
			if (list != null && list.size() > 0) {
				for (ProjectProgressEntity projectProgressEntity : list) {
					Map<String, String> filters1 = new HashMap<String, String>();
					filters1.put(ValueListInfo.PAGING_PAGE, "1");
					filters1.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
					filters1.put("projectCode", projectCode);
					filters1.put("level", "1");
					filters1.put("parentWorkSeq", String.valueOf(projectProgressEntity.getWorkSeq()));
					info.setFilters(filters1);
					projectProgressEntity.setProjectProgressEntityList(vlh.getValueList("progessEntityListSelect", info).getList());
					hierarchicalList.add(projectProgressEntity);
				}
			}

			request.setAttribute("list", hierarchicalList);
			request.setAttribute("project", project);
			request.setAttribute("maxWorkSeq", String.valueOf(this.getProjectProgressManager().getMaxWorkSeq(projectCode)));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("projectProgressEntityXml");
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProjectProgressManager getProjectProgressManager() {
		return projectProgressManager;
	}

	public void setProjectProgressManager(ProjectProgressManager projectProgressManager) {
		this.projectProgressManager = projectProgressManager;
	}

}
