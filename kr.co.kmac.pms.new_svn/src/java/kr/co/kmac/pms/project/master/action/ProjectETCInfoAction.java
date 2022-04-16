package kr.co.kmac.pms.project.master.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.repository.action.RepositoryDispatchActionSupport;
import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.manager.ProjectCsrInfoManager;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
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

/**
 * @struts.action name="projectMasterInfoAction" path="/action/ProjectETCInfoAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectWrokNamePopupForProjectReport" path="/project/master/projectWrokNamePopupForProjectReport.jsp" redirect="false"
 * @struts.action-forward name="projectCsrInfoList" path="/project/endProcess/projectCsrInfoList.jsp" redirect="false"
 */
public class ProjectETCInfoAction extends RepositoryDispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectETCInfoAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private PmsInfoMailSender pmsInfoMailSender;
	private ProjectCsrInfoManager projectCsrInfoManager;

	/**
	 * 단계별 업무 명
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward selectProjectWorkNameList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);
			ValueList result = vlh.getValueList("projectWorkNameListSelect", info);

			request.setAttribute("list", result);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectWrokNamePopupForProjectReport");
	}

	public void sendProjectThxMailAgain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		try {
			this.getPmsInfoMailSender().sendCustomerThx(projectCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void insertProjectCsrInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String[] customerCodes = ServletRequestUtils.getStringParameters(request, "customerCode");
		String[] customerNames = ServletRequestUtils.getStringParameters(request, "customerName");
		String[] customerWorkPNames = ServletRequestUtils.getStringParameters(request, "customerWorkPName");
		String[] customerWorkPEmails = ServletRequestUtils.getStringParameters(request, "customerWorkPEmail");
		String[] customerWorkPPhones = ServletRequestUtils.getStringParameters(request, "customerWorkPPhone");

		try {
			List<ProjectCsrInfo> list = new ArrayList<ProjectCsrInfo>();
			for (int i = 0; i < customerNames.length; i++) {
				ProjectCsrInfo pci = new ProjectCsrInfo();
				pci.setProjectCode(projectCode);
				pci.setCustomerCode(customerCodes[i]);
				pci.setCustomerName(customerNames[i]);
				pci.setCustomerWorkPName(customerWorkPNames[i]);
				pci.setCustomerWorkPEmail(customerWorkPEmails[i]);
				pci.setCustomerWorkPPhone(customerWorkPPhones[i]);
				list.add(pci);
			}
			if (list.size() > 0)
				this.getProjectCsrInfoManager().insert(projectCode, list);
			else
				this.getProjectCsrInfoManager().delete(projectCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ActionForward uploadProjectCsrInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		List<UploadFile> fileList = super.fileUploadObj(form, request);
		try {
			if (fileList.size() < 1)
				throw new Exception();
		} catch (Exception e) {
			throw new Exception("no file exception");
		}

		Workbook workbook = Workbook.getWorkbook(fileList.get(0).getInputStream());
		Sheet sheet = workbook.getSheet(0);

		List<ProjectCsrInfo> list = new ArrayList<ProjectCsrInfo>();
		for (int i = 1; i < sheet.getRows(); i++) {
			Cell[] cells = sheet.getRow(i);
			ProjectCsrInfo pci = new ProjectCsrInfo();
			pci.setProjectCode(projectCode);
			pci.setCustomerCode(cells[0].getContents());
			pci.setCustomerName(cells[1].getContents());
			pci.setCustomerWorkPName(cells[2].getContents());
			pci.setCustomerWorkPEmail(cells[3].getContents());
			pci.setCustomerWorkPPhone(cells[4].getContents());
			list.add(pci);
		}
		this.getProjectCsrInfoManager().insertForExcel(projectCode, list);

		List<ProjectCsrInfo> projectCsrInfos = this.getProjectCsrInfoManager().select(projectCode);
		request.setAttribute("list", projectCsrInfos);
		request.setAttribute("projectCode", projectCode);
		return mapping.findForward("projectCsrInfoList");
	}

	public ActionForward selectProjectCsrInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String projectState = ServletRequestUtils.getStringParameter(request, "projectState", "1");

		try {
			List<ProjectCsrInfo> list = this.getProjectCsrInfoManager().select(projectCode);
			request.setAttribute("list", list);
			request.setAttribute("projectCode", projectCode);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		request.setAttribute("projectState", projectState);
		return mapping.findForward("projectCsrInfoList");
	}

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
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

	public ProjectCsrInfoManager getProjectCsrInfoManager() {
		return projectCsrInfoManager;
	}

	public void setProjectCsrInfoManager(ProjectCsrInfoManager projectCsrInfoManager) {
		this.projectCsrInfoManager = projectCsrInfoManager;
	}

}
