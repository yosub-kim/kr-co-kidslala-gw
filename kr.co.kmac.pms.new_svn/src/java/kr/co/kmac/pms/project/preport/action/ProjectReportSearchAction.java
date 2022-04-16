package kr.co.kmac.pms.project.preport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.preport.data.ProjectReportMonthlyAnnual;
import kr.co.kmac.pms.sanction.preport.data.ProjectReportContent;
import kr.co.kmac.pms.sanction.preport.manager.ProjectReportManager;
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
 * @struts.action name="ProjectReportSearchAction" path="/action/ProjectReportSearchAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectReportList" path="/project/preport/projectReportList.jsp" redirect="false"
 * @struts.action-forward name="projectReportMonthlyAnnualList" path="/project/preport/projectReportMonthlyAnnualList.jsp" redirect="false"
 * @struts.action-forward name="projectReportForm" path="/sanction/sanctionForm/projectReportForm.jsp" redirect="false"
 */
public class ProjectReportSearchAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectReportSearchAction.class);
	private AttachTemplateManager attachTemplateManager;
	private AttachManager attachManager;
	private ProjectReportManager projectReportManager;

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
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String taskFormTypeId = ServletRequestUtils.getRequiredStringParameter(request, "taskFormTypeId");
		String seq = ServletRequestUtils.getRequiredStringParameter(request, "seq");

		ProjectReportContent projectReportContent = this.getProjectReportManager().getProjectReportContent1(projectCode, taskFormTypeId, seq);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
		request.setAttribute("attachBusType", "BTZ");
		request.setAttribute(
				"fileList",
				this.getAttachManager()
						.selectListForTask(
								wc,
								"preport_" + projectReportContent.getProjectCode() + "_" + projectReportContent.getTaskFormTypeId() + "_"
										+ projectReportContent.getSeq(), null, null).getList());
		request.setAttribute("projectReportContent", projectReportContent);
		request.setAttribute("readonly", true);

		return mapping.findForward("projectReportForm");
	}

	public ActionForward getProjectReportList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String toDate = ServletRequestUtils.getStringParameter(request, "toDate", "");
		String fromDate = ServletRequestUtils.getStringParameter(request, "fromDate", "");
		String writerName = ServletRequestUtils.getStringParameter(request, "writerName", "");

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
			if (!fromDate.equals("")) {
				filters.put("from", StringUtil.replace(fromDate, "-", ""));
				request.setAttribute("fromDate", fromDate);
			}
			if (!toDate.equals("")) {
				filters.put("until", StringUtil.replace(toDate, "-", ""));
				request.setAttribute("toDate", toDate);
			}

			info.setFilters(filters);

			ValueList result = vlh.getValueList("getProjectReportList", info);
			request.setAttribute("projectReportList", result);
			request.setAttribute("projectCode", projectCode);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectReportList");
	}

	public ActionForward getProjectReportMonthlyAnnualList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put(ValueListInfo.PAGING_PAGE, "1");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put("year", year);
			info.setFilters(filters);
			
			List<ProjectReportMonthlyAnnual> list = vlh.getValueList("getProjectReportMonthlyAnnualList", info).getList();

			request.setAttribute("projectReportMonthlyAnnualList", list);
			request.setAttribute("year", year);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectReportMonthlyAnnualList");
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

	public ProjectReportManager getProjectReportManager() {
		return projectReportManager;
	}

	public void setProjectReportManager(ProjectReportManager projectReportManager) {
		this.projectReportManager = projectReportManager;
	}

}
