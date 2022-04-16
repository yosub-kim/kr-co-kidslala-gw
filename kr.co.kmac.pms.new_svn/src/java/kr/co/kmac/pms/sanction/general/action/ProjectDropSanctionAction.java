package kr.co.kmac.pms.sanction.general.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.form.SanctionForm;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * 프로젝트 전자결재 업무함
 */
/**
 * @struts.action name="generalSanctionAction" path="/action/ProjectDropSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectDropSanctionForm" path="/sanction/sanctionForm/projectDropSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="sanctionContentInfo" path="/sanction/common/sanctionContentInfo.jsp" redirect="false"
 * @struts.action-forward name="generalMobileSanctionForm" path="/m/web/sanction/sanctionDraft.jsp" redirect="false"
 */
public class ProjectDropSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectRunningSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;
	private SanctionLineManager sanctionLineManager;

	public ActionForward loadFormProjectDropSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		// String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// 결재라인 세팅
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}
	
		// 팀장 권한 추가
		if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
			sanctionTemplate.setHasCfo(true);
		}
		
		// 없데이 보직자를 참조란에 추가
		List<ExpertPool> updayOfficer = this.getSanctionLineManager().selectUpdayOfficer(SessionUtils.getUsername(request));
		if (sanctionTemplate != null && !updayOfficer.isEmpty()) {
			if (updayOfficer.size() > 1) {
				sanctionTemplate.setRefMemberSsns(sanctionTemplate.getRefMemberSsns() + ", " + updayOfficer.get(0).getSsn() + ", " + updayOfficer.get(1).getSsn());
				sanctionTemplate.setRefMemberNames(sanctionTemplate.getRefMemberNames() + ", " + updayOfficer.get(0).getName() + ", " + updayOfficer.get(1).getName());
			} else {
				sanctionTemplate.setRefMemberSsns(sanctionTemplate.getRefMemberSsns() + ", " + updayOfficer.get(0).getSsn());
				sanctionTemplate.setRefMemberNames(sanctionTemplate.getRefMemberNames() + ", " + updayOfficer.get(0).getName());
				
			}
		}

		sanctionDoc.setProjectCode(projectCode);
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		// ----파일관련 시작
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("attachBusType", "BT3");
		// ----파일관련 끝

		// request.setAttribute("viewMode", viewMode);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);

		return mapping.findForward("projectDropSanctionForm");
	}

	public ActionForward getProjectDropSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			request.setAttribute("viewMode", "SIMPLE");

			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
			request.setAttribute("attachBusType", "BT3");

			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));

			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate2(sanctionDoc.getApprovalType(), workId));
			/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
			request.setAttribute("readonly", readonly);
			if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
					|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
				request.setAttribute("subinfoReadonly", false);
			} else {
				request.setAttribute("subinfoReadonly", true);
			}
			request.setAttribute("isRefWork", isRefWork);
			return mapping.findForward("projectDropSanctionForm");
		} else {
			return this.getProjectDropSanctionData1(mapping, form, request, response);
		}
	}

	public ActionForward getProjectDropSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			
			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());
			
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			
			request.setAttribute("viewMode", "SIMPLE");
			
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
			request.setAttribute("attachBusType", "BT3");
			
			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));
			
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate2(sanctionDoc.getApprovalType(), workId));
			/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
			request.setAttribute("readonly", readonly);
			if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
					|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
				request.setAttribute("subinfoReadonly", false);
			} else {
				request.setAttribute("subinfoReadonly", true);
			}
			request.setAttribute("isRefWork", isRefWork);
			return mapping.findForward("generalMobileSanctionForm");
		} else {
			return this.getProjectDropSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getProjectDropSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		request.setAttribute("readonly", readonly);
		request.setAttribute("subinfoReadonly", readonly);

		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/

		return mapping.findForward("projectDropSanctionForm");
	}

	private ActionForward getProjectDropSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);
		
		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		
		request.setAttribute("readonly", readonly);
		request.setAttribute("subinfoReadonly", readonly);
		
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
		
		return mapping.findForward("generalMobileSanctionForm");
	}
	
	public ActionForward getProjectDropSanctionContentData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");

		SanctionDoc sanctionDoc = null;
		if (workId.equals("")) {
			String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
			String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
			String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
			boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);
			if (seq.equals("0")) {
				sanctionDoc = new SanctionDoc();
				sanctionDoc.setApprovalType("projectDropSanction");
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
			} else {
				sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);
				request.setAttribute("readonly", readonly);
			}
		} else {
			Work work = this.getWorklistManager().getWork(workId);
			sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
		}

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
		return mapping.findForward("sanctionContentInfo");
	}

	public void executeProjectDropSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			Assert.hasLength(sanctionForm.getContent());
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("approval_" + sanctionDoc.getSeq());
				attachForm.setTaskId("approval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			String state = map.get("state");
			if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				// PMS 프로젝트를 '취소' 상태로 변경 
				this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "7");
				List<Work> works = this.getWorklistManager().getWorkList(sanctionDoc.getProjectCode());
				for (Work work : works) {
					if(work.getState().equals("WORK_STATE_READY")){
						this.getWorklistManager().terminatWork(work.getId());
					}
				}
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createProjectDropSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			map = this.getSanctionDocManager().createGeneralSanctionWork("S068809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("approval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteProjectDropSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectMasterInfoManager().deleteProject(sanctionDoc.getProjectCode());
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveProjectDropSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("approval_" + sanctionDoc.getSeq());
				attachForm.setTaskId("approval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}

			this.getSanctionDocManager().saveGeneralSanctionDoc(sanctionDoc);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	private void setSanctionList(SanctionDoc sanctionDoc, SanctionLine sanctionLine, SanctionTemplate sanctionTemplate) {
		sanctionDoc.setRegisterSsn(sanctionLine.getRegisterSsn());
		sanctionDoc.setRegisterName(sanctionLine.getRegisterName());
		sanctionDoc.setRegisterDept(sanctionLine.getRegisterDept());
		sanctionDoc.setTeamManagerSsn(sanctionLine.getTeamManagerSsn());
		sanctionDoc.setTeamManagerName(sanctionLine.getTeamManagerName());
		sanctionDoc.setTeamManagerDept(sanctionLine.getTeamManagerDept());
		sanctionDoc.setCfoSsn(sanctionLine.getCfoSsn());
		sanctionDoc.setCfoName(sanctionLine.getCfoName());
		sanctionDoc.setCfoDept(sanctionLine.getCfoDept());
		sanctionDoc.setCioSsn(sanctionLine.getCioSsn());
		sanctionDoc.setCioName(sanctionLine.getCioName());
		sanctionDoc.setCioDept(sanctionLine.getCioDept());
		if (sanctionTemplate != null && sanctionTemplate.isHasAssistMember()) {
			if (sanctionTemplate.getAssistMemberCnt() == 1) {
				sanctionDoc.setAssistant1Ssn(sanctionLine.getAssistant1Ssn());
				sanctionDoc.setAssistant1Name(sanctionLine.getAssistant1Name());
				sanctionDoc.setAssistant1Dept(sanctionLine.getAssistant1Dept());
			} else if (sanctionTemplate.getAssistMemberCnt() == 2) {
				sanctionDoc.setAssistant1Ssn(sanctionLine.getAssistant1Ssn());
				sanctionDoc.setAssistant1Name(sanctionLine.getAssistant1Name());
				sanctionDoc.setAssistant1Dept(sanctionLine.getAssistant1Dept());
				sanctionDoc.setAssistant2Ssn(sanctionLine.getAssistant2Ssn());
				sanctionDoc.setAssistant2Name(sanctionLine.getAssistant2Name());
				sanctionDoc.setAssistant2Dept(sanctionLine.getAssistant2Dept());
			}
		}
	}

	public SanctionTemplateManager getSanctionTemplateManager() {
		return sanctionTemplateManager;
	}

	public void setSanctionTemplateManager(SanctionTemplateManager sanctionTemplateManager) {
		this.sanctionTemplateManager = sanctionTemplateManager;
	}

	public SanctionDocManager getSanctionDocManager() {
		return sanctionDocManager;
	}

	public void setSanctionDocManager(SanctionDocManager sanctionDocManager) {
		this.sanctionDocManager = sanctionDocManager;
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
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

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public SanctionLineManager getSanctionLineManager() {
		return sanctionLineManager;
	}

	public void setSanctionLineManager(SanctionLineManager sanctionLineManager) {
		this.sanctionLineManager = sanctionLineManager;
	}

}
