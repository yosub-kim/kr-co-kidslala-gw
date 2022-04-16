/*
 * $Id: ScheduleChangeSanctionAction.java,v 1.6 2018/06/12 12:31:15 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 24.
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

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.common.org.data.User;
import kr.co.kmac.pms.common.org.manager.IOrgUnitManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.sanction.projectchange.data.ScheduleChange;
import kr.co.kmac.pms.sanction.projectchange.form.ScheduleChangeSanctionForm;
import kr.co.kmac.pms.sanction.projectchange.manager.ProjectChangeManager;
import kr.co.kmac.pms.system.sanction.data.SanctionTemplate;
import kr.co.kmac.pms.system.sanction.manager.SanctionTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="scheduleChangeSanctionAction" path="/action/ScheduleChangeSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="scheduleChangeSanctionForm" path="/sanction/sanctionForm/scheduleChangeSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="sanctionContentInfo" path="/sanction/common/sanctionContentInfo.jsp" redirect="false"
 */
public class ScheduleChangeSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ScheduleChangeSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProjectChangeManager projectChangeManager;
	private IOrgUnitManager orgUnitManager;
	private SanctionLineManager sanctionLineManager;
	private ExpertPoolManager expertPoolManager;

	@SuppressWarnings("unchecked")
	public ActionForward loadFormScheduleChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		if (sanctionTemplate.getApprovalType().equals("S"))
		{
			if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null )
			{	
				sanctionDoc.setCfoSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setCfoName(sanctionLine.getTeamManagerName());
				sanctionDoc.setCfoDept(sanctionLine.getTeamManagerDept());
				sanctionDoc.setCioSsn(sanctionLine.getCfoSsn());
				sanctionDoc.setCioName(sanctionLine.getCfoName());
				sanctionDoc.setCioDept(sanctionLine.getCfoDept());
			}
			else if (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals("")) 
			{
				sanctionDoc.setCioSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setCioName(sanctionLine.getTeamManagerName());
				sanctionDoc.setCioDept(sanctionLine.getTeamManagerDept());
			}
		}
		
		List<ExpertPool> updayOfficer = this.getSanctionLineManager().selectUpdayOfficer(SessionUtils.getUsername(request));		
		if (sanctionTemplate != null && !updayOfficer.isEmpty()) {
			if (updayOfficer.size() > 1) {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn() + ", " + updayOfficer.get(1).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName() + ", " + updayOfficer.get(1).getName());
			} else {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName());
				
			}
		}

		sanctionDoc.setProjectCode(projectCode);
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("sanctionDoc", sanctionDoc);
		//request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(approvalType));
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		
		/*sanction infomation*/
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());

		return mapping.findForward("scheduleChangeSanctionForm");
	}

	public ActionForward getScheduleChangeSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
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

			request.setAttribute("scheduleChange", this.getProjectChangeManager().selectScheduleChange(work.getRefWorkId1(), work.getRefWorkId3()));
			
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
			
			return mapping.findForward("scheduleChangeSanctionForm");
		} else {
			return getScheduleChangeSanctionData1(mapping, form, request, response);
		}
	}

	private ActionForward getScheduleChangeSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
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

		request.setAttribute("readonly", readonly);
		
		request.setAttribute("scheduleChange", this.getProjectChangeManager().selectScheduleChange(projectCode, seq));
		
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
		
		return mapping.findForward("scheduleChangeSanctionForm");
	}
	
	public ActionForward getScheduleChangeSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
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

			request.setAttribute("scheduleChange", this.getProjectChangeManager().selectScheduleChange(work.getRefWorkId1(), work.getRefWorkId3()));
			
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
			
			return mapping.findForward("scheduleChangeSanctionForm");
		} else {
			return getScheduleChangeSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getScheduleChangeSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
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

		request.setAttribute("readonly", readonly);
		
		request.setAttribute("scheduleChange", this.getProjectChangeManager().selectScheduleChange(projectCode, seq));
		
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
		
		return mapping.findForward("scheduleChangeSanctionForm");
	}

	public void executeScheduleChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ScheduleChangeSanctionForm scheduleChangeSanctionForm = (ScheduleChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(scheduleChangeSanctionForm);
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			String state = map.get("state");
			if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				this.getProjectChangeManager().finishScheduleChange(scheduleChangeSanctionForm.getProjectCode(),
						String.valueOf(scheduleChangeSanctionForm.getScheduleChangeSeq()));
			} else {
				this.getProjectChangeManager().updateScheduleChange((ScheduleChange) scheduleChangeSanctionForm);
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createScheduleChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		ScheduleChangeSanctionForm scheduleChangeSanctionForm = (ScheduleChangeSanctionForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(scheduleChangeSanctionForm);
			map = this.getSanctionDocManager().createGeneralSanctionWork("S048809e0a4c4436010a4ead57e4001f", sanctionDoc);

			scheduleChangeSanctionForm.setScheduleChangeSeq(Integer.parseInt(map.get("seq")));
			this.getProjectChangeManager().insertScheduleChange((ScheduleChange) scheduleChangeSanctionForm);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteScheduleChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ScheduleChangeSanctionForm scheduleChangeSanctionForm = (ScheduleChangeSanctionForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(scheduleChangeSanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectChangeManager().deleteScheduleChange(scheduleChangeSanctionForm.getProjectCode(),
					String.valueOf(scheduleChangeSanctionForm.getScheduleChangeSeq()));
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveScheduleChangeSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ScheduleChangeSanctionForm scheduleChangeSanctionForm = (ScheduleChangeSanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(scheduleChangeSanctionForm);
			this.getSanctionDocManager().saveGeneralSanctionDoc(sanctionDoc);

			this.getProjectChangeManager().updateScheduleChange((ScheduleChange) scheduleChangeSanctionForm);
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
		sanctionDoc.setCioSsn(sanctionLine.getCioSsn());
		sanctionDoc.setCioName(sanctionLine.getCioName());
		sanctionDoc.setCioDept(sanctionLine.getCioDept());
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

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
}
