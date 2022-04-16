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
	 * 컨설턴트 평가
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
	 * PL 평가
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
	 * 컨설팅, 리서치 고객만족도 현황
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
			// 업무처리 Logic 시작
			this.getWorklistManager().terminatWork(projectEndingForm.getTaskId());
			// 업무처리 Logic 종료

			AjaxUtil.successWrite(response);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * 강사 평가
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
	 * 평가화면(MB) - C
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
	 * 평가화면(PL, MB) - P
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
	 * 평가화면(MB) - E
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
	 * 프로젝트 종료처리 화면(businessTypeCode, processTypeCode)
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

			// 프로젝트 기본 데이터
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

			// ----파일관련 시작
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("isReview", "TRUE");
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectSummaryData.getBusinessTypeCode()).getList());
			request.setAttribute("attachOutputType", attachOutputTypeList);
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			//attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("fileList", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			// ----파일관련 끝



			Map<String, String> objTitle = new HashMap<String, String>();
			if (ending.getBusinessTypeCode().equals("BTE")) {

				objTitle.put("strA", "교육 시간<br>및 일정");
				objTitle.put("strB", "교육 프로그램<br>및 교재<br>(교보재)");
				objTitle.put("strC", "차기 과정<br>반영 포인트");
				objTitle.put("strD", "기타<br>(강사 등)");

			} else {

				objTitle.put("strA", "프로젝트 배경<br>(프로젝트 발주 배경<br>및 개요)");
				objTitle.put("strB", "프로젝트 성과<br>(프로젝트 목표를<br>달성함으로써 얻은<br>고객의 성과)");
				objTitle.put("strC", "프로젝트 리뷰<br>(프로젝트 준비,진행,<br>결과도출 과정에서<br>있었던 주요사항)");
				objTitle.put("strD", "시사점<br>(상품성, 시장 확대<br>가능성에 대한 내용)");

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
	 * 프로젝트 종료처리 화면(businessTypeCode, processTypeCode)
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

			// 프로젝트 기본 데이터
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

			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);

			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());

			Map<String, String> objTitle = new HashMap<String, String>();
			if (ending.getBusinessTypeCode().equals("BTA") || ending.getBusinessTypeCode().equals("BTB")
					|| ending.getBusinessTypeCode().equals("BTC") || ending.getBusinessTypeCode().equals("BTD")
					|| ending.getBusinessTypeCode().equals("BTF") || ending.getBusinessTypeCode().equals("BTG")
					|| ending.getBusinessTypeCode().equals("BTH") || ending.getBusinessTypeCode().equals("BTI")) {

				objTitle.put("strA", "프로젝트 배경<br>(프로젝트 발주 배경<br>및 개요)");
				objTitle.put("strB", "프로젝트 성과<br>(프로젝트 목표를<br>달성함으로써 얻은<br>고객의 성과)");
				objTitle.put("strC", "프로젝트 리뷰<br>(프로젝트 준비, 진행,<br>결과 도출 과정에서<br>있었던 주요 사항)");
				objTitle.put("strD", "시사점<br>(상품성, 시장 확대<br>가능성에 대한 내용)");

			} else {

				objTitle.put("strA", "교육 시간<br>및 일정");
				objTitle.put("strB", "교육 프로그램<br>및 교재<br>(교보재)");
				objTitle.put("strC", "차기 과정<br>반영 포인트");
				objTitle.put("strD", "기타<br>(강사 등)");

			}

			// request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, taskId, null, null).getList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return mapping.findForward("ending");
	}
	
	/**
	 * 
	 * 프로젝트 산출물 추가 업로딩를 위한 종료처리 화면(projectCode)
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

			// 프로젝트 기본 데이터
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
			// ----파일관련 시작
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----파일관련 끝
			*/
			
			// ----파일관련 시작
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
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("fileList1", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			
			attachForm.setAttachIsEssential(new String[] { "0" });
			request.setAttribute("fileList0", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----파일관련 끝

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

			// 프로젝트 기본 데이터
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
			// ----파일관련 시작
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----파일관련 끝
			*/
			
			// ----파일관련 시작
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
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			request.setAttribute("fileList1", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("fileList0", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList2(wc, attachForm, null, null).getList());
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("fileListSize", this.getAttachManager().selectList2(wc, attachForm, null, null).getList().size());
			// ----파일관련 끝

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

			// 프로젝트 기본 데이터
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
			// ----파일관련 시작
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(
					WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, projectSummaryData.getBusinessTypeCode())
					.getList());
			request.setAttribute("attachBusType", projectSummaryData.getBusinessTypeCode());
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			attachForm.setAttachIsEssential(new String[] { "1" });
			request.setAttribute("reqFile", this.getAttachManager().selectList(wc, attachForm, null, null).getList());
			// ----파일관련 끝
			*/
			
			// ----파일관련 시작
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
			
			// 프로젝트 산출물 리스트
			AttachForm attachForm = new AttachForm();
			attachForm.setProjectCode(projectCode);
			request.setAttribute("fileList1", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile1", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("fileList0", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("reqFile0", this.getAttachManager().selectList3(wc, attachForm, null, null).getList());
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("fileListSize", this.getAttachManager().selectList3(wc, attachForm, null, null).getList().size());
			// ----파일관련 끝

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("endingForOutputUpload3");
	}	
	

	/**
	 * 종료처리 프로세스
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
			/***** 파일 첨부 시작 *****/
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
			/***** 파일 첨부 종료 *****/
			// 저장.
			getProjectEndingManager().insertEnding(projectEndingForm);

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * 업무위임 프로세스
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
			// 업무를 위임함
			this.getProjectEndingManager().entrustEnding(projectEndingForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 추가 산출물 저장 프로세스
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
			/***** 파일 첨부 시작 *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("ending_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// 기존 파일 업데이트 및 신규 파일 추가
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// 여기까지
				
				/* 기존 파일 업데이트 및 신규 파일 추가
				this.getAttachManager().insert(attachForm);
				여기까지 */
			}
			/***** 파일 첨부 종료 *****/			

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}
	
	/**
	 * 추가 산출물 저장 프로세스
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
			/***** 파일 첨부 시작 *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("tax_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// 기존 파일 업데이트 및 신규 파일 추가
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// 여기까지
				
				/* 기존 파일 업데이트 및 신규 파일 추가
				this.getAttachManager().insert(attachForm);
				여기까지 */
			}
			/***** 파일 첨부 종료 *****/			

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 추가 산출물 저장 프로세스
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
			/***** 파일 첨부 시작 *****/
			AttachForm attachForm = (AttachForm) projectEndingForm;
			
			attachForm.setTaskId("chk_" + projectEndingForm.getProjectCode());
			attachForm.setProjectCode(projectEndingForm.getProjectCode());
			attachForm.setTaskFormTypeId("");
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				// 기존 파일 업데이트 및 신규 파일 추가
				if (attachForm.getF_seq() != null && attachForm.getF_seq().length > 0) {
					for (String f_seq: attachForm.getF_seq()) {
						this.getAttachManager().delete(f_seq);		
					}
				}
				this.getAttachManager().insertFilesEachTaskId(attachForm);
				// 여기까지
				
				/* 기존 파일 업데이트 및 신규 파일 추가
				this.getAttachManager().insert(attachForm);
				여기까지 */
			}
			/***** 파일 첨부 종료 *****/			

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