package kr.co.kmac.pms.project.voc.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.voc.manager.ProjectVocManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectVocAction" path="/action/ProjectVocAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectVocList" path="/project/voc/projectVocList.jsp" redirect="false"
 * @struts.action-forward name="projectVocSendingList" path="/project/voc/projectVocSendingList.jsp" redirect="false"
 * @struts.action-forward name="projectVocInsert" path="/project/voc/projectVocInsert.jsp" redirect="false"
 */
public class ProjectVocAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(ProjectVocAction.class);

	private ProjectVocManager projectVocManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private PmsInfoMailSender pmsInfoMailSender;

	public ActionForward selectVocInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		request.setAttribute("vocList", this.getProjectVocManager().getProjectVoc(projectCode));

		return mapping.findForward("projectVocList");
	}

	public ActionForward selectSendingVocInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		if (month.length() < 2)
			month = "0" + month;

		request.setAttribute("vocList", this.getProjectVocManager().getSendingProjectVoc(year, month));
		request.setAttribute("year", year);
		request.setAttribute("month", month);

		return mapping.findForward("projectVocSendingList");
	}

	public ActionForward registProjectVoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		request.setAttribute("today", DateUtil.getCurrentYyyymmdd());
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("vocList", this.getProjectVocManager().getProjectVoc(projectCode));

		return mapping.findForward("projectVocInsert");
	}

	public void hasProjectVoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			boolean res = this.getProjectVocManager().getProjectVoc(projectCode).size() > 0;
			map.put("hasProjectVoc", res);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
		}

	}

	public void insertProjectVoc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		String[] requestDate = ServletRequestUtils.getStringParameters(request, "requestDate");
		String[] vocType = ServletRequestUtils.getStringParameters(request, "vocType");
		// String receiveName = request.getParameter("receiveName");
		// String receiveEmail = request.getParameter("receiveEmail");
		try {
			this.getProjectVocManager().deleteProjectVoc(projectCode);
			// projectVocManager.setProjectVoc(projectCode, requestDate, vocType, receiveName, receiveEmail);
			this.getProjectVocManager().setProjectVoc(projectCode, requestDate, vocType);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void getVocMonthlySchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = ServletRequestUtils.getRequiredStringParameter(request, "type");
		String fromDate = ServletRequestUtils.getRequiredStringParameter(request, "fromDate");
		String toDate = ServletRequestUtils.getRequiredStringParameter(request, "toDate");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (type.equals("everyMonth")) {
			map.put("date", DateUtil.getEveryMonth(fromDate, toDate));
		} else if (type.equals("everyOtherMonth")) {
			map.put("date", DateUtil.getEveryOhterMonth(fromDate, toDate));
		}

		AjaxUtil.successWrite(response, map);
	}

	public void sendProjectVOCMailAgain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		int customerSeq = ServletRequestUtils.getRequiredIntParameter(request, "customerSeq");
		try {
			this.getPmsInfoMailSender().sendProjectCSMailAgain(projectCode, customerSeq);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

	public ProjectVocManager getProjectVocManager() {
		return projectVocManager;
	}

	public void setProjectVocManager(ProjectVocManager projectVocManager) {
		this.projectVocManager = projectVocManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}
}