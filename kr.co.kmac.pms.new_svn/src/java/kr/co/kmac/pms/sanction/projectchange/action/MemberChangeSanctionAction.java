/*
 * $Id: MemberChangeSanctionAction.java,v 1.10 2019/02/09 05:47:26 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
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
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectRelatedEntNo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.sanction.projectchange.data.AddMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChangeArray;
import kr.co.kmac.pms.sanction.projectchange.form.MemberChangeSanctionForm;
import kr.co.kmac.pms.sanction.projectchange.manager.ProjectChangeManager;
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
 * @struts.action name="memberChangeSanctionAction" path="/action/MemberChangeSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="memberChangeSanctionForm" path="/sanction/sanctionForm/memberChangeSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="memberChangeSanctionAlert" path="/sanction/sanctionForm/memberChangeSanctionAlert.jsp" redirect="false"
 * @struts.action-forward name="sanctionContentInfo" path="/sanction/common/sanctionContentInfo.jsp" redirect="false"
 */
public class MemberChangeSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(MemberChangeSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectChangeManager projectChangeManager;
	private IOrgUnitManager orgUnitManager;
	private SanctionLineManager sanctionLineManager;
	private AttachTemplateManager attachTemplateManager;
	private AttachManager attachManager;
	private ExpertPoolManager expertPoolManager;

	@SuppressWarnings("unchecked")
	public ActionForward loadFormMemberChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");//M:상근/전문가/RA, ME:엑스퍼트/강사/교수
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		if(this.getSanctionDocManager().isIngSanctionExist(projectCode, approvalType)){
			return mapping.findForward("memberChangeSanctionAlert");
		}
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
	/*	List<ExpertPool> updayOfficer = this.getSanctionLineManager().selectUpdayOfficer(SessionUtils.getUsername(request));		
		if (sanctionTemplate != null && !updayOfficer.isEmpty()) {
			if (updayOfficer.size() > 1) {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn() + ", " + updayOfficer.get(1).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName() + ", " + updayOfficer.get(1).getName());
			} else {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName());
				
			}
		}*/

		sanctionDoc.setProjectCode(projectCode);
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
		
		Project project = this.getProjectMasterInfoManager().getProject(projectCode);
		// 인력변경용 예산 생성 여부 확인
		String entNo = StringUtil.isNull(this.getProjectMasterInfoManager().getProjectEntNoForMemberChange(projectCode), "");
		if (!entNo.equals(""))
			request.setAttribute("projectMemberListFromEntNo", this.getProjectMasterInfoManager().getProjectMemberFromEntNo(entNo));
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", project);
		request.setAttribute("projectMemberList", this.getProjectMasterInfoManager().getProjectAllMember(projectCode));
		
		// ----파일관련 시작
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute(
				"attachOutputType",
				this.getAttachTemplateManager()
						.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
						.getList());
		request.setAttribute("attachBusType", "BT4");
		// ----파일관련 끝

		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
	
		request.setAttribute("sanctionDoc", sanctionDoc);
		//request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(approvalType));
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("budgetEntNo", entNo);
		return mapping.findForward("memberChangeSanctionForm");
	}

	public ActionForward getMemberChangeSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);

			request.setAttribute("addMemberList", this.getProjectChangeManager().selectAddMemberChange(work.getRefWorkId1(), work.getRefWorkId3()));
			request.setAttribute("runningMemberList", this.getProjectChangeManager().selectRunningMemberChange(work.getRefWorkId1(), work.getRefWorkId3()));
			
			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));
			
			try {
				request.setAttribute("budgetEntNo", this.getProjectMasterInfoManager().getProjectRelatedEntNoForMemberChange(String.valueOf(sanctionDoc.getSeq())).getNewEntNo());
			} catch (Exception e) {
				request.setAttribute("budgetEntNo", "");
			}
			
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

			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "businessApproval_" + sanctionDoc.getSeq(), null, null)
					.getList());
			request.setAttribute("attachBusType", "BT4");

			
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
			
			return mapping.findForward("memberChangeSanctionForm");
		} else {
			return getMemberChangeSanctionData1(mapping, form, request, response);
		}
	}

	private ActionForward getMemberChangeSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		request.setAttribute("readonly", readonly);
		
		request.setAttribute("addMemberList", this.getProjectChangeManager().selectAddMemberChange(projectCode, seq));
		request.setAttribute("runningMemberList", this.getProjectChangeManager().selectRunningMemberChange(projectCode, seq));
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		try {
			request.setAttribute("budgetEntNo", this.getProjectMasterInfoManager().getProjectRelatedEntNoForMemberChange(String.valueOf(sanctionDoc.getSeq())).getNewEntNo());
		} catch (Exception e) {
			request.setAttribute("budgetEntNo", "");
		}
		
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

		request.setAttribute("fileMode", "READ");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "businessApproval_" + sanctionDoc.getSeq(), null, null)
				.getList());

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
		
		return mapping.findForward("memberChangeSanctionForm");
	}
	
	public ActionForward getMemberChangeSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);

			request.setAttribute("addMemberList", this.getProjectChangeManager().selectAddMemberChange(work.getRefWorkId1(), work.getRefWorkId3()));
			request.setAttribute("runningMemberList", this.getProjectChangeManager().selectRunningMemberChange(work.getRefWorkId1(), work.getRefWorkId3()));
			
			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));
			
			try {
				request.setAttribute("budgetEntNo", this.getProjectMasterInfoManager().getProjectRelatedEntNoForMemberChange(String.valueOf(sanctionDoc.getSeq())).getNewEntNo());
			} catch (Exception e) {
				request.setAttribute("budgetEntNo", "");
			}
			
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

			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("viewMode", "SIMPLE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "businessApproval_" + sanctionDoc.getSeq(), null, null)
					.getList());
			request.setAttribute("attachBusType", "BT4");

			
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
			
			return mapping.findForward("memberChangeSanctionForm");
		} else {
			return getMemberChangeSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getMemberChangeSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		request.setAttribute("readonly", readonly);
		
		request.setAttribute("addMemberList", this.getProjectChangeManager().selectAddMemberChange(projectCode, seq));
		request.setAttribute("runningMemberList", this.getProjectChangeManager().selectRunningMemberChange(projectCode, seq));
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		try {
			request.setAttribute("budgetEntNo", this.getProjectMasterInfoManager().getProjectRelatedEntNoForMemberChange(String.valueOf(sanctionDoc.getSeq())).getNewEntNo());
		} catch (Exception e) {
			request.setAttribute("budgetEntNo", "");
		}
		
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

		request.setAttribute("fileMode", "READ");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT4").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "businessApproval_" + sanctionDoc.getSeq(), null, null)
				.getList());

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
		
		return mapping.findForward("memberChangeSanctionForm");
	}

	public void executeMemberChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MemberChangeSanctionForm memberChangeSanctionForm = (MemberChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(memberChangeSanctionForm);
			
			AttachForm attachForm = (AttachForm) memberChangeSanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("businessApproval_" + sanctionDoc.getSeq());
				attachForm.setTaskId("businessApproval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			String state = map.get("state");
			if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				this.getProjectChangeManager().finishMemberChange(memberChangeSanctionForm.getProjectCode(),
						String.valueOf(memberChangeSanctionForm.getMemberChangeSeq()));				
				if (!memberChangeSanctionForm.getBudgetEntNo().equals("")) {
					// Project MM Plan 정보 리셋
					this.getProjectMasterInfoManager().updateProjectMemberMMInfoForMemberChange(memberChangeSanctionForm.getProjectCode(), memberChangeSanctionForm.getMemberChangeSeq());
					// 인력변경용 온라인 예산정보 '승인' 상태로 변경
					this.getProjectMasterInfoManager().updateBudgetStateForMemberChange(memberChangeSanctionForm.getProjectCode(), memberChangeSanctionForm.getMemberChangeSeq());
				} else {
					// Project MM Plan 정보 업데이트 - 제외 인력
					this.getProjectChangeManager().updateMemberMMPlan((RunningMemberChangeArray) memberChangeSanctionForm);
				}
			} else {
				this.getProjectChangeManager().updateMemberChange((RunningMemberChangeArray) memberChangeSanctionForm,
						(AddMemberChangeArray) memberChangeSanctionForm);
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createMemberChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MemberChangeSanctionForm memberChangeSanctionForm = (MemberChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(memberChangeSanctionForm);
			map = this.getSanctionDocManager().createGeneralSanctionWork("S058809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) memberChangeSanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("businessApproval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			
			memberChangeSanctionForm.setMemberChangeSeq(Integer.parseInt(map.get("seq")));
			this.getProjectChangeManager().insertMemberChange((RunningMemberChangeArray) memberChangeSanctionForm,
					(AddMemberChangeArray) memberChangeSanctionForm);
			// 인력변경 예산서 정보 저장
			if (!memberChangeSanctionForm.getBudgetEntNo().equals("")) {
				ProjectRelatedEntNo projectRelatedEntNo = new ProjectRelatedEntNo();
				projectRelatedEntNo.setNewEntNo(memberChangeSanctionForm.getBudgetEntNo());
				projectRelatedEntNo.setSeq(map.get("seq"));
				projectRelatedEntNo.setProjectCode(memberChangeSanctionForm.getProjectCode());
				this.getProjectMasterInfoManager().insertProjectRelatedEntNoForMemberChange(projectRelatedEntNo);
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteMemberChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberChangeSanctionForm memberChangeSanctionForm = (MemberChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(memberChangeSanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectChangeManager().deleteMemberChange(memberChangeSanctionForm.getProjectCode(),
					String.valueOf(memberChangeSanctionForm.getMemberChangeSeq()));
			
			// 인력변경 예산서 정보 삭제
			if (!memberChangeSanctionForm.getBudgetEntNo().equals("")) {
				this.getProjectMasterInfoManager().deleteProjectRelatedEntNoForMemberChange(memberChangeSanctionForm.getMemberChangeSeq());
			}
			
			AttachForm attachForm = (AttachForm) memberChangeSanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("businessApproval_" + sanctionDoc.getSeq());			
			}
			
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveMemberChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MemberChangeSanctionForm memberChangeSanctionForm = (MemberChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(memberChangeSanctionForm);
			this.getSanctionDocManager().saveGeneralSanctionDoc(sanctionDoc);

			this.getProjectChangeManager().insertMemberChange((RunningMemberChangeArray) memberChangeSanctionForm,
					(AddMemberChangeArray) memberChangeSanctionForm);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void getErpMemberChangeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			List<ProjectMember> projectMemberList = this.getProjectMasterInfoManager().getErpProjectMember(projectCode);
			map.put("projectMemberList", projectMemberList);
			AjaxUtil.successWrite(response, map);
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
		sanctionDoc.setCioSsn(sanctionLine.getCioSsn());
		sanctionDoc.setCioName(sanctionLine.getCioName());
		sanctionDoc.setCioDept(sanctionLine.getCioDept());
		sanctionDoc.setCfoSsn(sanctionLine.getCfoSsn());
		sanctionDoc.setCfoDept(sanctionLine.getCfoDept());
		sanctionDoc.setCfoName(sanctionLine.getCfoName());
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

	public ProjectChangeManager getProjectChangeManager() {
		return projectChangeManager;
	}

	public void setProjectChangeManager(ProjectChangeManager projectChangeManager) {
		this.projectChangeManager = projectChangeManager;
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

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
}
