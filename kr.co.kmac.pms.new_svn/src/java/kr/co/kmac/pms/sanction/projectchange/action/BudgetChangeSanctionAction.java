package kr.co.kmac.pms.sanction.projectchange.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;
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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="budgetChangeSanctionAction" path="/action/BudgetChangeSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="budgetChangeSanctionForm" path="/sanction/sanctionForm/budgetChangeSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="sanctionContentInfo" path="/sanction/common/sanctionContentInfo.jsp" redirect="false"
 */
public class BudgetChangeSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(BudgetChangeSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private IOrgUnitManager orgUnitManager;
	private SanctionLineManager sanctionLineManager;
	private ExpertPoolManager expertPoolManager;

	@SuppressWarnings("unchecked")
	public ActionForward loadFormBudgetChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// 결재라인 세팅
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			User user = SessionUtils.getUserObjext();
			sanctionDoc.setRegisterSsn(user.getId());
			orgUnitManager.populateGroup(user);
			List<Group> groupList = user.getGroupList();
			if (groupList.size() > 0) {
				sanctionDoc.setRegisterDept(((Group) (user.getGroupList().get(0))).getId());
			}
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

		String[] entNos = projectMasterInfoManager.getProjectEntNo(projectCode);
		ProjectRelatedEntNo projectRelatedEntNo = new ProjectRelatedEntNo();
		projectRelatedEntNo.setNewEntNo(entNos[0]);
		projectRelatedEntNo.setOldEntNo(entNos[1]);
		projectRelatedEntNo.setProjectCode(projectCode);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		// ----파일관련 시작
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("attachBusType", "BT3");
		// ----파일관련 끝
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("projectRelatedentNo", projectRelatedEntNo);
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());

		return mapping.findForward("budgetChangeSanctionForm");
	}

	public ActionForward getBudgetChangeSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
			
			/* 전자결재 로그 내용 추가 */
			String ssn = SessionUtils.getUsername(request); //읽은 사람
			SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String viewDate = date.format(System.currentTimeMillis());
			SanctionDoc sanctionDoc2 = new SanctionDoc();
			
			sanctionDoc2.setViewSsn(ssn);
			sanctionDoc2.setViewDate(viewDate);
			sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
			if(sanctionDoc.getRegisterDate() == null){
				sanctionDoc2.setSanctionDocDate(viewDate);
			}else{
				sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
			}
			sanctionDoc2.setSeq(sanctionDoc.getSeq());
			
			this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			request.setAttribute("viewMode", "SIMPLE");

			request.setAttribute("projectRelatedentNo", this.getProjectMasterInfoManager().getProjectRelatedEntNo(String.valueOf(sanctionDoc.getSeq())));
			
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
			request.setAttribute("isRefWork", isRefWork);
			
			/*sanction infomation*/
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
			request.setAttribute("userDept", ex.getDeptName());
			request.setAttribute("userName", ex.getName());
			request.setAttribute("companyPositionName", ex.getCompanyPositionName());
			
			return mapping.findForward("budgetChangeSanctionForm");
		} else {
			return getBudgetChangeSanctionData1(mapping, form, request, response);
		}
	}

	private ActionForward getBudgetChangeSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		/* 전자결재 로그 내용 추가 */
		String ssn = SessionUtils.getUsername(request); //읽은 사람
		SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String viewDate = date.format(System.currentTimeMillis());
		SanctionDoc sanctionDoc2 = new SanctionDoc();
		
		sanctionDoc2.setViewSsn(ssn);
		sanctionDoc2.setViewDate(viewDate);
		sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
		if(sanctionDoc.getRegisterDate() == null){
			sanctionDoc2.setSanctionDocDate(viewDate);
		}else{
			sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
		}
		sanctionDoc2.setSeq(sanctionDoc.getSeq());
		
		this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		request.setAttribute("readonly", readonly);

		request.setAttribute("projectRelatedentNo", this.getProjectMasterInfoManager().getProjectRelatedEntNo(seq));
		
		request.setAttribute("fileMode", "READ");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
		
		return mapping.findForward("budgetChangeSanctionForm");
	}
	
	public ActionForward getBudgetChangeSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
			
			/* 전자결재 로그 내용 추가 */
			String ssn = SessionUtils.getUsername(request); //읽은 사람
			SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String viewDate = date.format(System.currentTimeMillis());
			SanctionDoc sanctionDoc2 = new SanctionDoc();
			
			sanctionDoc2.setViewSsn(ssn);
			sanctionDoc2.setViewDate(viewDate);
			sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
			if(sanctionDoc.getRegisterDate() == null){
				sanctionDoc2.setSanctionDocDate(viewDate);
			}else{
				sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
			}
			sanctionDoc2.setSeq(sanctionDoc.getSeq());
			
			this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			request.setAttribute("viewMode", "SIMPLE");

			request.setAttribute("projectRelatedentNo", this.getProjectMasterInfoManager().getProjectRelatedEntNo(String.valueOf(sanctionDoc.getSeq())));
			
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
			request.setAttribute("isRefWork", isRefWork);
			
			/*sanction infomation*/
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
			request.setAttribute("userDept", ex.getDeptName());
			request.setAttribute("userName", ex.getName());
			request.setAttribute("companyPositionName", ex.getCompanyPositionName());
			
			return mapping.findForward("budgetChangeSanctionForm");
		} else {
			return getBudgetChangeSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getBudgetChangeSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		/* 전자결재 로그 내용 추가 */
		String ssn = SessionUtils.getUsername(request); //읽은 사람
		SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String viewDate = date.format(System.currentTimeMillis());
		SanctionDoc sanctionDoc2 = new SanctionDoc();
		
		sanctionDoc2.setViewSsn(ssn);
		sanctionDoc2.setViewDate(viewDate);
		sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
		if(sanctionDoc.getRegisterDate() == null){
			sanctionDoc2.setSanctionDocDate(viewDate);
		}else{
			sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
		}
		sanctionDoc2.setSeq(sanctionDoc.getSeq());
		
		this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		request.setAttribute("readonly", readonly);

		request.setAttribute("projectRelatedentNo", this.getProjectMasterInfoManager().getProjectRelatedEntNo(seq));
		
		request.setAttribute("fileMode", "READ");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
		
		return mapping.findForward("budgetChangeSanctionForm");
	}

	public void executeBudgetChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
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
				// 온라인 예산정보 '승인' 상태로 변경
				this.getProjectMasterInfoManager().updateBudgetState(sanctionForm.getProjectCode(), sanctionForm.getSeq());
				// 온라인 예산정보 회계로 전송
				this.getProjectMasterInfoManager().sendBudgetState(sanctionForm.getProjectCode(), sanctionForm.getSeq());
				// Project MM Plan 정보 리셋
				this.getProjectMasterInfoManager().updateProjectMemberMMInfo(sanctionForm.getProjectCode(), sanctionForm.getSeq());
				
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createBudgetChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;
		String oldEntNo = ServletRequestUtils.getRequiredStringParameter(request, "oldEntNo");
		String newEntNo = ServletRequestUtils.getRequiredStringParameter(request, "newEntNo");
		

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			map = this.getSanctionDocManager().createGeneralSanctionWork("S088809e0a4c4436010a4ead57e4001f", sanctionDoc);

			ProjectRelatedEntNo projectRelatedEntNo = new ProjectRelatedEntNo();
			projectRelatedEntNo.setNewEntNo(newEntNo);
			projectRelatedEntNo.setOldEntNo(oldEntNo);
			projectRelatedEntNo.setSeq(map.get("seq"));
			projectRelatedEntNo.setProjectCode(sanctionForm.getProjectCode());
			this.getProjectMasterInfoManager().insertProjectRelatedEntNo(projectRelatedEntNo);
			
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

	public void deleteBudgetChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectMasterInfoManager().deleteProjectRelatedEntNo(String.valueOf(sanctionDoc.getSeq()));

			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveBudgetChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public IOrgUnitManager getOrgUnitManager() {
		return orgUnitManager;
	}

	public void setOrgUnitManager(IOrgUnitManager orgUnitManager) {
		this.orgUnitManager = orgUnitManager;
	}

	public SanctionLineManager getSanctionLineManager() {
		return sanctionLineManager;
	}

	public void setSanctionLineManager(SanctionLineManager sanctionLineManager) {
		this.sanctionLineManager = sanctionLineManager;
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

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
	
}
