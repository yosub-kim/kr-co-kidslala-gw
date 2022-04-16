package kr.co.kmac.pms.project.endprocess.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.project.endprocess.data.Ending;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.summary.data.ProjectSummaryData;
import kr.co.kmac.pms.project.summary.manager.ProjectSummaryManager;
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
 * @struts.action name="projectViewForm" path="/action/ProjectViewAction" parameter="mode" scope="request"
 * @struts.action-forward name="rateC_View" path="/project/endProcess/rateC_View.jsp" redirect="false"
 * @struts.action-forward name="rateP_View" path="/project/endProcess/rateP_View.jsp" redirect="false"
 * @struts.action-forward name="rateE_View" path="/project/endProcess/rateE_View.jsp" redirect="false"
 * @struts.action-forward name="rollingE_View" path="/project/endProcess/rollingE_View.jsp" redirect="false"
 * @struts.action-forward name="rollingT_View" path="/project/endProcess/rollingT_View.jsp" redirect="false"
 * @struts.action-forward name="rollingC_View" path="/project/endProcess/rollingC_View.jsp" redirect="false"
 * @struts.action-forward name="ending_View" path="/project/endProcess/ending_View.jsp" redirect="false"
 * @struts.action-forward name="ending_ExpertPoolView" path="/project/endProcess/ending_ExpertPoolView.jsp" redirect="false"
 * @struts.action-forward name="attachFile_inFo" path="/project/endProcess/attachFile_inFo.jsp" redirect="false"
 */
public class ProjectViewAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectViewAction.class);

	private ProjectMasterInfoManager projectMasterInfoManager;
	private AttachManager attachManager;
	private ProjectSummaryManager projectSummaryManager;

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public ProjectSummaryManager getProjectSummaryManager() {
		return projectSummaryManager;
	}

	public void setProjectSummaryManager(ProjectSummaryManager projectSummaryManager) {
		this.projectSummaryManager = projectSummaryManager;
	}

	/**
	 * ��ȭ��(MB) - C viewer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rateC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rateC", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateC_View");
	}

	/**
	 * ��ȭ��(PL, MB) - P viewer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rateP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rateP", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateP_View");

	}

	/**
	 * 
	 * ��ȭ��(MB) - E
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rateE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rateE", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateE_View");
	}

	/**
	 * 
	 * ��ȭ��(����������)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rollingE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			// ���� ��
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rolling", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);
			
			// ���� ��
			vlh = (ValueListHandler) wc.getBean("rollingC", ValueListHandler.class);

			info = new ValueListInfo();
			filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result2 = vlh.getValueList("select", info);
			request.setAttribute("result2", result2);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rollingE_View");
	}
	
	/**
	 * 
	 * ��ȭ��(����������)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rollingT(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rolling", ValueListHandler.class);
			
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);
			
			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rollingT_View");
	}

	/**
	 * 
	 * ��ȭ��(��������)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rollingC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			// ������ ������ �� ��
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rolling", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);

			// ������Ʈ ��
			vlh = (ValueListHandler) wc.getBean("rollingC", ValueListHandler.class);

			info = new ValueListInfo();
			filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result2 = vlh.getValueList("select", info);
			request.setAttribute("result2", result2);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("rollingC_View");
	}

	/**
	 * 
	 * ���⹰(�˾�â)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward attachFilePopup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			// ������ ������ �� ��
			ValueListHandler vlh = (ValueListHandler) wc.getBean("rolling", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			request.setAttribute("result", result);

			// ������Ʈ ��
			vlh = (ValueListHandler) wc.getBean("rollingC", ValueListHandler.class);

			info = new ValueListInfo();
			filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result2 = vlh.getValueList("select", info);
			request.setAttribute("result2", result2);

			request.setAttribute("fileMode", "READ");// READ WRITE

			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);

			request.setAttribute("fileList", this.getAttachManager().selectList(wc, attachForm, null, null).getList());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("attachFile_inFo");
	}

	/**
	 * 
	 * ������Ʈ Summary
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward summary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = request.getParameter("projectCode");
		// String taskId = "ending_" + projectCode;

		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);
			request.setAttribute("projectSummaryData", projectSummaryData);
			// 

			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);
			
			ValueListInfo info1 = new ValueListInfo();
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put("projectCode", projectCode);
			info1.setFilters(filters1);

			ValueList result1 = vlh1.getValueList("select", info1);
			Ending ending = (Ending) result1.getList().get(0);
			request.setAttribute("ending", (Ending) result1.getList().get(0));

			// ��������
			ValueListHandler vlh2 = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);
			ValueListInfo info2 = new ValueListInfo();
			Map<String, String> filters2 = new HashMap<String, String>();
			filters2.put(ValueListInfo.PAGING_PAGE, 1+"");
			filters2.put(ValueListInfo.PAGING_NUMBER_PER, Integer.MAX_VALUE+"");
			filters2.put("projectCode", projectCode);
			info2.setFilters(filters2);
			ValueList result = vlh2.getValueList("getProjectReportList", info2);
			request.setAttribute("projectReportList", result);
			//
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			String essential[] = new String[1];
			
			// Job Date: 2010-06-18	Author: yhyim	Description: �ʼ� ���⹰�� ���ʼ� ���⹰ ����Ʈ�� ���� �����ϱ� ���� �Ʒ��� ���� ������
			essential[0] = "1";	// �ʼ�
			attachForm.setAttachIsEssential(essential);
			request.setAttribute("reqFile1", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			essential[0] = "0";	// ���ʼ�
			attachForm.setAttachIsEssential(essential);
			request.setAttribute("reqFile0", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			
			request.setAttribute("fileMode", "WRITE");// READ WRITE

			Map<String, String> objTitle = new HashMap<String, String>();
			
			if (ending.getBusinessTypeCode().equals("BTE")) {
				objTitle.put("strA", "���� �ð� �� <br>����");
				objTitle.put("strB", "���� ���α׷�<br>�� ����<br>(������)");
				objTitle.put("strC", "���� ����<br>�ݿ� ����Ʈ");
				objTitle.put("strD", "��Ÿ<br>(���� ��)");
			} else {
				objTitle.put("strA", "������Ʈ ���<br>(������Ʈ ���� ���<br>�� ����)");
				objTitle.put("strB", "������Ʈ ����<br>(������Ʈ ��ǥ��<br>�޼������ν� ����<br>���� ����)");
				objTitle.put("strC", "������Ʈ ����<br>(������Ʈ �غ�,<br>����,<br>��� ���� ��������<br>�־��� �ֿ� ����)");
				objTitle.put("strD", "�û���<br>(��ǰ��, ���� Ȯ��<br>���ɼ��� ���� ����)");
			}
			request.setAttribute("objTitle", objTitle);
			// request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, taskId, null, null).getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("ending_View");
	}
	/**
	 * 
	 * ������Ʈ summaryForExpertPool
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward summaryForExpertPool(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String projectCode = request.getParameter("projectCode");
		String ssn = request.getParameter("ssn");
		// String taskId = "ending_" + projectCode;

		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);
			request.setAttribute("projectSummaryData", projectSummaryData);

			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			
			ValueListHandler vlh1 = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);			
			ValueListInfo info1 = new ValueListInfo();
			
			Map<String, String> filters1 = new HashMap<String, String>();
			filters1.put("projectCode", projectCode);
			info1.setFilters(filters1);

			ValueList result1 = vlh1.getValueList("select", info1);
			Ending ending = (Ending) result1.getList().get(0);
			request.setAttribute("ending", (Ending) result1.getList().get(0));

			// ������Ʈ ��
			ValueListHandler vlh2 = (ValueListHandler) wc.getBean("estimateValueListBean", ValueListHandler.class);
			ValueListInfo info2 = new ValueListInfo();
			
			//Map<String, String> filters2 = new HashMap<String, String>();
			//filters2.put("projectCode", projectCode);
			//info2.setFilters(filters2);
			info2.setFilters(filters1);
			ValueList rollingResult = vlh2.getValueList("getProjectEstimate", info2);
			request.setAttribute("projectRolling", rollingResult);
			
			// ������Ʈ ��
			ValueListHandler vlh3 = (ValueListHandler) wc.getBean("estimateValueListBean", ValueListHandler.class);
			ValueListInfo info3 = new ValueListInfo();
			
			filters1.put("ssn", ssn);
			info3.setFilters(filters1);
			ValueList rollingCResult = vlh3.getValueList("getConsultantEstimate", info3);
			request.setAttribute("projectRollingC", rollingCResult);
			

			Map<String, String> objTitle = new HashMap<String, String>();
			
			if (ending.getBusinessTypeCode().equals("BTE")) {
				objTitle.put("strA", "���� �ð� �� <br>����");
				objTitle.put("strB", "���� ���α׷�<br>�� ����<br>(������)");
				objTitle.put("strC", "���� ����<br>�ݿ� ����Ʈ");
				objTitle.put("strD", "��Ÿ<br>(���� ��)");
			} else {
				objTitle.put("strA", "������Ʈ ���<br>(������Ʈ ���� ���<br>�� ����)");
				objTitle.put("strB", "������Ʈ ����<br>(������Ʈ ��ǥ��<br>�޼������ν� ����<br>���� ����)");
				objTitle.put("strC", "������Ʈ ����<br>(������Ʈ �غ�,<br>����,<br>��� ���� ��������<br>�־��� �ֿ� ����)");
				objTitle.put("strD", "�û���<br>(��ǰ��, ���� Ȯ��<br>���ɼ��� ���� ����)");
			}
			request.setAttribute("objTitle", objTitle);
			// request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, taskId, null, null).getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("ending_ExpertPoolView");
	}
}