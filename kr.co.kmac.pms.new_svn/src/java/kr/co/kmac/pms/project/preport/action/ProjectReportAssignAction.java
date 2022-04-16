package kr.co.kmac.pms.project.preport.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.service.scheduler.batch.ProjectReportScheduleService;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectReportAssignAction" path="/action/ProjectReportAssignAction" parameter="mode" scope="request"
 */
public class ProjectReportAssignAction extends DispatchActionSupport {

	private ProjectReportScheduleService projectReportScheduleService;

	public ProjectReportScheduleService getProjectReportScheduleService() {
		return projectReportScheduleService;
	}

	public void setProjectReportScheduleService(ProjectReportScheduleService projectReportScheduleService) {
		this.projectReportScheduleService = projectReportScheduleService;
	}

	public void assigneProjectReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String yyyymmdd = ServletRequestUtils.getRequiredStringParameter(request, "yyyymmdd");
		try {
			StringUtil.replace(yyyymmdd, "-", "");
			String resString = this.getProjectReportScheduleService().assignProjectReportUntilGivenDate(yyyymmdd);
			map.put("resString", resString);

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
		}
	}
}
