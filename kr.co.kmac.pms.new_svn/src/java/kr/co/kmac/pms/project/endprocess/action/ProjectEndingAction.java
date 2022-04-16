package kr.co.kmac.pms.project.endprocess.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.project.endprocess.data.Ending;
import kr.co.kmac.pms.project.endprocess.form.ProjectEndingForm;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndProcessManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectEndingManager;
import kr.co.kmac.pms.project.endprocess.manager.ProjectRollingManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.statistics.data.CustomerSatisfactionReportDetail;
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
 * @struts.action name="projectEndingForm" path="/action/ProjectEndingAction" parameter="mode" scope="request"
 * @struts.action-forward name="rateC" path="/project/endProcess/rateC.jsp" redirect="false"
 * @struts.action-forward name="rateP" path="/project/endProcess/rateP.jsp" redirect="false"
 * @struts.action-forward name="rateE" path="/project/endProcess/rateE.jsp" redirect="false"
 * @struts.action-forward name="ending" path="/project/endProcess/ending.jsp" redirect="false"
 * @struts.action-forward name="ending_OK" path="/project/endProcess/ending_ok.jsp" redirect="false"
 * @struts.action-forward name="endingForOutputUpload" path="/project/endProcess/endingForOutputUpload.jsp" redirect="false"
 * @struts.action-forward name="rateCustomer" path="/project/endProcess/rateCustomer.jsp" redirect="false"
 */
public class ProjectEndingAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectEndingAction.class);

	private ProjectEndingManager projectEndingManager;
	private ProjectEndProcessManager projectEndProcessManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectSummaryManager projectSummaryManager;
	private AttachManager attachManager;
	private ProjectRollingManager projectRollingManager;
	private WorklistManager worklistManager;

	/**
	 * ������Ʈ ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRateC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			getProjectEndingManager().insertRateC(projectEndingForm);
			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * PL ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRateP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			if (projectEndingForm.getSsn() != null) {
				getProjectEndingManager().insertRateP(projectEndingForm);
			}
			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * ������, ����ġ �������� ��Ȳ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void insertRateCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			getProjectEndingManager().insertRateCustomer(projectEndingForm);
			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void isRateFinished(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		HashMap<String, Object> map = new HashMap<String, Object>();

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
		ValueListInfo info = new ValueListInfo();
		Map<String, String> filters = new HashMap<String, String>();
		filters.put("projectcode", projectCode);
		info.setFilters(filters);
		try {
			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportDetail", info);
			if (valueList.getList() != null && valueList.getList().size() > 0) {
				if (((CustomerSatisfactionReportDetail) valueList.getList().get(0)).getCs3() != null) {
					map.put("isFinished", true);
				} else {
					map.put("isFinished", false);
				}
			} else {
				map.put("isFinished", false);
			}

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void deleteRate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;

		try {
			this.getProjectMasterInfoManager().updateProjectState(projectEndingForm.getProjectCode(), "7");
			// ����ó�� Logic ����
			this.getWorklistManager().terminatWork(projectEndingForm.getTaskId());
			// ����ó�� Logic ����

			AjaxUtil.successWrite(response);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * ���� ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRateE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			getProjectEndingManager().insertRateE(projectEndingForm);

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * ��ȭ��(MB) - C
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadRateFormC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("MB", "MB");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);

			request.setAttribute("result", vlh.getValueList("projectMemberListSelect", info));
			request.setAttribute("taskId", workId);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateC");
	}

	/**
	 * ��ȭ��(PL, MB) - P
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadRateFormP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PL", "PL");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);

			request.setAttribute("result", vlh.getValueList("projectMemberListSelect", info));
			request.setAttribute("taskId", workId);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateP");

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
	public ActionForward loadRateFormE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			filters.put("PLMB_ALL", "PLMB_ALL");
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE - 1));
			info.setFilters(filters);

			request.setAttribute("result", vlh.getValueList("projectMemberListSelect", info));
			request.setAttribute("taskId", workId);
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("rateE");
	}

	public ActionForward loadRateCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";
		String seq = "";
		try {
			if (!workId.equals("")) {
				Work work = this.getWorklistManager().getWork(workId);
				projectCode = work.getRefWorkId1();
				
				if (getProjectRollingManager().numOfCustomer(projectCode) > 1) 
					seq = work.getRefWorkId3();
			} else {
				projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
			}
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("CustomerSatisfactionReport", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();

			if (projectCode != null && !projectCode.equals("")) {
				filters.put("projectcode", projectCode);
			}
			if (seq != null && !seq.equals("")) {
				filters.put("seq", seq);
			}

			info.setFilters(filters);

			
			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportDetail", info);
			request.setAttribute("result", valueList);
			ValueList valueList2 = vlh.getValueList("getValueCustomerSatisfactionReportDetail2", info);
			request.setAttribute("result2", valueList2);
			request.setAttribute("taskId", workId);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", getProjectMasterInfoManager().getProject(projectCode));
			request.setAttribute("rollingState", getProjectRollingManager().isFinishedRolling(projectCode, seq));
			request.setAttribute("seq", seq);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("rateCustomer");
	}

	/**
	 * 
	 * ������Ʈ ����ó�� ȭ��(businessTypeCode, processTypeCode)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectEnding(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		WebApplicationContext wc;

		try {
			request.setAttribute("workId", workId);

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);

			request.setAttribute("projectSummaryData", projectSummaryData);
			// 
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			Ending ending = new Ending();
			ending = (Ending) result.getList().get(0);
			request.setAttribute("ending", ending);

			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("isReview", "TRUE");
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputType", attachOutputTypeList);
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			//attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("fileList", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			// ----���ϰ��� ��



			Map<String, String> objTitle = new HashMap<String, String>();
			if (ending.getBusinessTypeCode().equals("BTE")) {

				objTitle.put("strA", "���� �ð�<br>�� ����");
				objTitle.put("strB", "���� ���α׷�<br>�� ����<br>(������)");
				objTitle.put("strC", "���� ����<br>�ݿ� ����Ʈ");
				objTitle.put("strD", "��Ÿ<br>(���� ��)");

			} else {

				objTitle.put("strA", "������Ʈ ���<br>(������Ʈ ���� ���<br>�� ����)");
				objTitle.put("strB", "������Ʈ ����<br>(������Ʈ ��ǥ��<br>�޼������ν� ����<br>���� ����)");
				objTitle.put("strC", "������Ʈ ����<br>(������Ʈ �غ�,����,<br>������� ��������<br>�־��� �ֿ����)");
				objTitle.put("strD", "�û���<br>(��ǰ��, ���� Ȯ��<br>���ɼ��� ���� ����)");

			}
			request.setAttribute("objTitle", objTitle);
			// request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, taskId, null, null).getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("ending");
	}

	/**
	 * 
	 * ������Ʈ ����ó�� ȭ��(businessTypeCode, processTypeCode)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectEndingOK(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		// String taskId = "ending_" + projectCode;

		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);

			request.setAttribute("projectSummaryData", projectSummaryData);
			// 
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			Ending ending = new Ending();
			ending = (Ending) result.getList().get(0);
			request.setAttribute("ending", ending);

			request.setAttribute("fileMode", "WRITE");// READ WRITE

			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);

			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());

			Map<String, String> objTitle = new HashMap<String, String>();
			if (ending.getBusinessTypeCode().equals("BTA") || ending.getBusinessTypeCode().equals("BTB")
					|| ending.getBusinessTypeCode().equals("BTC") || ending.getBusinessTypeCode().equals("BTD")
					|| ending.getBusinessTypeCode().equals("BTF") || ending.getBusinessTypeCode().equals("BTG")
					|| ending.getBusinessTypeCode().equals("BTH") || ending.getBusinessTypeCode().equals("BTI")) {

				objTitle.put("strA", "������Ʈ ���<br>(������Ʈ ���� ���<br>�� ����)");
				objTitle.put("strB", "������Ʈ ����<br>(������Ʈ ��ǥ��<br>�޼������ν� ����<br>���� ����)");
				objTitle.put("strC", "������Ʈ ����<br>(������Ʈ �غ�, ����,<br>��� ���� ��������<br>�־��� �ֿ� ����)");
				objTitle.put("strD", "�û���<br>(��ǰ��, ���� Ȯ��<br>���ɼ��� ���� ����)");

			} else {

				objTitle.put("strA", "���� �ð�<br>�� ����");
				objTitle.put("strB", "���� ���α׷�<br>�� ����<br>(������)");
				objTitle.put("strC", "���� ����<br>�ݿ� ����Ʈ");
				objTitle.put("strD", "��Ÿ<br>(���� ��)");

			}

			// request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, taskId, null, null).getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("ending");
	}
	
	/**
	 * 
	 * ������Ʈ ���⹰ �߰� ���ε��� ���� ����ó�� ȭ��(projectCode)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectEndingForOutputUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);

			request.setAttribute("projectSummaryData", projectSummaryData);
			// 
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			Ending ending = new Ending();
			ending = (Ending) result.getList().get(0);
			request.setAttribute("ending", ending);

			/*
			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----���ϰ��� ��
			*/
			
			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputType", attachOutputTypeList);
			
			List attachOutputBusTypeList = new ArrayList();
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BT3").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BTZ").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputBusType", attachOutputBusTypeList);
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("fileList1", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			
			attachForm.setAttachIsEssential(new String[] { "0" });
			request.setAttribute("fileList0", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----���ϰ��� ��

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("endingForOutputUpload");
	}	
	
	public ActionForward selectEndingForOutputUpload2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);

			request.setAttribute("projectSummaryData", projectSummaryData);
			// 
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			Ending ending = new Ending();
			ending = (Ending) result.getList().get(0);
			request.setAttribute("ending", ending);

			/*
			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----���ϰ��� ��
			*/
			
			// ----���ϰ��� ����
			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("fileMode", "WRITE");
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputType", attachOutputTypeList);
			
			List attachOutputBusTypeList = new ArrayList();
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BT3").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BTZ").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputBusType", attachOutputBusTypeList);
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			request.setAttribute("fileList1", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("fileList0", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("fileListSize", this.getAttachManager().selectList2(wc, attachForm, null, null).getList().size());
			// ----���ϰ��� ��

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("endingForOutputUpload2");
	}	
	
	public ActionForward selectEndingForOutputUpload3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		WebApplicationContext wc;

		try {

			// ������Ʈ �⺻ ������
			ProjectSummaryData projectSummaryData = new ProjectSummaryData();
			projectSummaryData = getProjectSummaryManager().retrieve(projectCode);

			request.setAttribute("projectSummaryData", projectSummaryData);
			// 
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("ending", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();

			Map<String, String> filters = new HashMap<String, String>();
			filters.put("projectCode", projectCode);
			info.setFilters(filters);

			ValueList result = vlh.getValueList("select", info);
			Ending ending = new Ending();
			ending = (Ending) result.getList().get(0);
			request.setAttribute("ending", ending);

			/*
			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----���ϰ��� ��
			*/
			
			// ----���ϰ��� ����
			request.setAttribute("fileMode", "WRITE");
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputType", attachOutputTypeList);
			
			List attachOutputBusTypeList = new ArrayList();
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BT3").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, "BTZ").getList());
			attachOutputBusTypeList.addAll(this.getAttachTemplateManager().selectOutputBusTypeCodeList(wc, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputBusType", attachOutputBusTypeList);
			
			// ������Ʈ ���⹰ ����Ʈ
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			request.setAttribute("fileList1", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("fileList0", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("fileListSize", this.getAttachManager().selectList3(wc, attachForm, null, null).getList().size());
			// ----���ϰ��� ��

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("endingForOutputUpload3");
	}	
	

	/**
	 * ����ó�� ���μ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertEnding(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;

		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			/***** ���� ÷�� ���� *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("ending_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
			}
			/***** ���� ÷�� ���� *****/
			// ����.
			getProjectEndingManager().insertEnding(projectEndingForm);

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * �������� ���μ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void entrustEnding(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;
		
		try {
			// ������ ������
			this.getProjectEndingManager().entrustEnding(projectEndingForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * �߰� ���⹰ ���� ���μ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertAditionalOutput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;

		String writerSsn = ServletRequestUtils.getStringParameter(request, "pmSsn", SessionUtils.getUsername(request));
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			/***** ���� ÷�� ���� *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("ending_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// ���� ���� ������Ʈ �� �ű� ���� �߰�
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// �������
				
				/* ���� ���� ������Ʈ �� �ű� ���� �߰�
				this.getAttachManager().insert(attachForm);
				������� */
			}
			/***** ���� ÷�� ���� *****/			

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * �߰� ���⹰ ���� ���μ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertAditionalOutput2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;

		String writerSsn = ServletRequestUtils.getStringParameter(request, "pmSsn", SessionUtils.getUsername(request));
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			/***** ���� ÷�� ���� *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("tax_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// ���� ���� ������Ʈ �� �ű� ���� �߰�
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// �������
				
				/* ���� ���� ������Ʈ �� �ű� ���� �߰�
				this.getAttachManager().insert(attachForm);
				������� */
			}
			/***** ���� ÷�� ���� *****/			

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * �߰� ���⹰ ���� ���μ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertAditionalOutput3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectEndingForm projectEndingForm = (ProjectEndingForm) form;

		String writerSsn = ServletRequestUtils.getStringParameter(request, "pmSsn", SessionUtils.getUsername(request));
		String writeDate = DateTime.getDateString();

		projectEndingForm.setWriterSsn(writerSsn);
		projectEndingForm.setWriteDate(writeDate);

		try {
			/***** ���� ÷�� ���� *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("chk_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// ���� ���� ������Ʈ �� �ű� ���� �߰�
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// �������
				
				/* ���� ���� ������Ʈ �� �ű� ���� �߰�
				this.getAttachManager().insert(attachForm);
				������� */
			}
			/***** ���� ÷�� ���� *****/			

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}


	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ProjectEndingManager getProjectEndingManager() {
		return projectEndingManager;
	}

	public void setProjectEndingManager(ProjectEndingManager projectEndingManager) {
		this.projectEndingManager = projectEndingManager;
	}

	public ProjectEndProcessManager getProjectEndProcessManager() {
		return projectEndProcessManager;
	}

	public void setProjectEndProcessManager(ProjectEndProcessManager projectEndProcessManager) {
		this.projectEndProcessManager = projectEndProcessManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProjectSummaryManager getProjectSummaryManager() {
		return projectSummaryManager;
	}

	public void setProjectSummaryManager(ProjectSummaryManager projectSummaryManager) {
		this.projectSummaryManager = projectSummaryManager;
	}

	public AttachManager getAttachManager() {
		return attachManager;
	}

	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}

	public ProjectRollingManager getProjectRollingManager() {
		return projectRollingManager;
	}

	public void setProjectRollingManager(ProjectRollingManager projectRollingManager) {
		this.projectRollingManager = projectRollingManager;
	}

	
}