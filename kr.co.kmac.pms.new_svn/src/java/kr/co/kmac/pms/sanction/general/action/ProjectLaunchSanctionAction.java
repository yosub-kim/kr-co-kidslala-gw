package kr.co.kmac.pms.sanction.general.action;

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
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectBudget;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.sanction.general.data.CcoTarget;
import kr.co.kmac.pms.sanction.general.data.SanctionConstants;
import kr.co.kmac.pms.sanction.general.data.SanctionDoc;
import kr.co.kmac.pms.sanction.general.form.SanctionForm;
import kr.co.kmac.pms.sanction.general.manager.CcoTargetManager;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.sanction.line.data.SanctionLine;
import kr.co.kmac.pms.sanction.line.manager.SanctionLineManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
import kr.co.kmac.pms.service.scheduler.manager.MonthlyReportScheduleManager;
import kr.co.kmac.pms.service.scheduler.manager.ProjectReportScheduleManager;
import kr.co.kmac.pms.service.scheduler.manager.WeeklyReportScheduleManager;
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
 * @struts.action name="generalSanctionAction" path="/action/ProjectLaunchSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectLaunchSanctionForm" path="/sanction/sanctionForm/projectLaunchSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="sanctionContentInfo" path="/sanction/common/sanctionContentInfo.jsp" redirect="false"
 * @struts.action-forward name="generalMobileSanctionForm" path="/m/web/sanction/sanctionDraft.jsp" redirect="false"
 */
public class ProjectLaunchSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectRunningSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;
	private SanctionLineManager sanctionLineManager;
	private PmsInfoMailSender pmsInfoMailSender;
	private ProjectReportScheduleManager projectReportScheduleManager;
	private CcoTargetManager ccoTargetManager;
	private WeeklyReportScheduleManager weeklyReportScheduleManager;
	private MonthlyReportScheduleManager monthlyReportScheduleManager;
	
	public ActionForward loadFormProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String approvalType = ServletRequestUtils.getRequiredStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);
		sanctionDoc.setProjectCodeBp(projectCodeBp);
		sanctionDoc.setFunc(func);
		CcoTarget ccoTarget = new CcoTarget();
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		
		// 결재 라인 세팅
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}
		
		// 팀장 결재 라인
		if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
			sanctionTemplate.setHasCfo(true);
		}
		
		// 없데이 보직자를 참조란에 추가 (사용안함)
		/*List<ExpertPool> updayOfficer = this.getSanctionLineManager().selectUpdayOfficer(SessionUtils.getUsername(request));
		if (sanctionTemplate != null && !updayOfficer.isEmpty()) {
			if (updayOfficer.size() > 1) {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn() + ", " + updayOfficer.get(1).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName() + ", " + updayOfficer.get(1).getName());
			} else {
				sanctionTemplate.setRefMemberSsns(updayOfficer.get(0).getSsn());
				sanctionTemplate.setRefMemberNames(updayOfficer.get(0).getName());
			}
		}*/
		
		// 실행 품의 (매출액 기준) UPDATE : 2021.10.05
		Project project = null;
		project = this.getProjectMasterInfoManager().getErpProject2(projectCode);
		
		if((sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 100000000 && project.getBusinessTypeCode().equals("BTA"))
			||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTD"))
			||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE"))
			||(sanctionTemplate.getApprovalType().equals("PA") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE")))
		{
			sanctionTemplate.setHasSptTeamMng(false);
			sanctionTemplate.setHasCeo(false);
			
			sanctionTemplate.setRefMemberSsns("A000006" + ", " + "A000031");
			sanctionTemplate.setRefMemberNames("한수희" + ", " + "김기철");
		}
		sanctionDoc.setProjectCode(projectCode);
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
		
		
		// CCO 참조 추가
		//민간
		if(sanctionTemplate.getApprovalType().equals("A") && ( expertPool.getDept().equals("9302") || expertPool.getDept().equals("9303")
				 || expertPool.getDept().equals("9312") || expertPool.getDept().equals("9352") || expertPool.getDept().equals("9353")
				 || expertPool.getDept().equals("9361"))){
			sanctionTemplate.setRefMemberSsns("A000013");
			sanctionTemplate.setRefMemberNames("이립");
		}
		// 공공
		else if(sanctionTemplate.getApprovalType().equals("A") && ( expertPool.getDept().equals("9314") || expertPool.getDept().equals("9315") || expertPool.getDept().equals("9322")
				 || expertPool.getDept().equals("9323") || expertPool.getDept().equals("9324") || expertPool.getDept().equals("9332")
				 || expertPool.getDept().equals("9333") || expertPool.getDept().equals("9334") || expertPool.getDept().equals("9335")
				 || expertPool.getDept().equals("9354") || expertPool.getDept().equals("9356"))){
			sanctionTemplate.setRefMemberSsns("A000008");
			sanctionTemplate.setRefMemberNames("한상록");
		}
		// 민간/공공
		else{
			sanctionTemplate.setRefMemberSsns("A000008" + ", " + "A000013");
			sanctionTemplate.setRefMemberNames("한상록" + ", " + "이립");
		}
		// CCO 담당여부 확인 : 2013.03.15 (사용안함)
		if (this.getCcoTargetManager()
				.isCcoTargetExist(this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode).getCustomerCompanyCode())) {
			ccoTarget = getCcoTargetManager().getCcoTarget(
					this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode).getCustomerCompanyCode());
			request.setAttribute("ccoTarget", ccoTarget.getCcoGubun());
		} else {
			request.setAttribute("ccoTarget", "");
		}
		
		// 미디어센터 CEO 최돈모상무님
		if(sanctionTemplate != null && expertPool.getDept().equals("9361")){
			sanctionTemplate.setCeoSsn("A000007");
			sanctionTemplate.setCeoName("최돈모");
			sanctionTemplate.setCeoDept("2040");
		}

		// 첨부파일 관련 항목
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("attachBusType", "BT3");
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());

		request.setAttribute("viewMode", viewMode);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);

		return mapping.findForward("projectLaunchSanctionForm");
	}

	public ActionForward getProjectLaunchSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		
		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());
			
			// By-Pass (사용안함)
			sanctionDoc.setFunc(sanctionDoc.getAssistant4Ssn());
			sanctionDoc.setProjectCodeBp(sanctionDoc.getAssistant4Dept());

			// sanctiondoc log
			String ssn = SessionUtils.getUsername(request);
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
			
			// 실행 품의 (매출액 기준) UPDATE : 2021.10.05
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType());
			Project project = null;
			project = this.getProjectMasterInfoManager().getErpProject2(sanctionDoc.getProjectCode());
			
			if((sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 100000000 && project.getBusinessTypeCode().equals("BTA"))
					||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTD"))
					||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE"))
					||(sanctionTemplate.getApprovalType().equals("PA") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE")))
			{
				sanctionTemplate.setHasSptTeamMng(false);
				sanctionTemplate.setHasCeo(false);
				
				sanctionTemplate.setRefMemberSsns("A000006" + ", " + "A000031");
				sanctionTemplate.setRefMemberNames("한수희" + ", " + "김기철");
			}
			
			// 첨부파일 관련 항목
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());			
			request.setAttribute("viewMode", viewMode);
			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
			request.setAttribute("attachBusType", "BT3");
			request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));
			request.setAttribute("sanctionDoc", sanctionDoc);
			request.setAttribute("readonly", readonly);
			
			// 스마트교육본부 전자결재 품의 일시적
			if("WORK17F1F294A5A".equals(workId) || "WORK17F253DFC80".equals(workId)){
				sanctionTemplate.setHasAssistMember(true);
				sanctionTemplate.setAssistMemberCnt(1);
			}
			
			// 임시 저장 시 결재 라인
			if(sanctionDoc.getRegisterDate() == null){
				// 팀장 체크
				SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
				if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
					sanctionTemplate.setHasCfo(true);
				}
				request.setAttribute("sanctionTemplate", sanctionTemplate);
			} else{
				// 반려 일시 (팀장 체크), workId 체크
				request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate2(sanctionDoc.getApprovalType(), workId));
				/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
			}
			
			// 반려 일시
			if (sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_DRAFT)
					|| sanctionDoc.getState().equals(SanctionConstants.SANCTION_STATE_REJECT_DRAFT)) {
				request.setAttribute("subinfoReadonly", false);
			} else {
				request.setAttribute("subinfoReadonly", true);
			}
			request.setAttribute("isRefWork", isRefWork);
			
			return mapping.findForward("projectLaunchSanctionForm");
		} else {
			return this.getProjectLaunchSanctionData1(mapping, form, request, response);
		}
	}

	public ActionForward getProjectLaunchSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanction");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());
			
			/* 전자결재 로그 내용 추가 */
			String ssn = SessionUtils.getUsername(request); //읽은 사람
			SimpleDateFormat date = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			String viewDate = date.format(System.currentTimeMillis());
			SanctionDoc sanctionDoc2 = new SanctionDoc();
			
			sanctionDoc2.setViewSsn(ssn);
			sanctionDoc2.setViewDate(viewDate);
			sanctionDoc2.setSanctionDocSsn(sanctionDoc.getRegisterSsn());
			sanctionDoc2.setSanctionDocDate(String.valueOf(sanctionDoc.getRegisterDate()));
			sanctionDoc2.setSeq(sanctionDoc.getSeq());
			
			//this.getSanctionDocManager().insertGeneralSanctionDocLog(sanctionDoc2);

			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

			request.setAttribute("viewMode", viewMode);

			request.setAttribute("fileMode", "WRITE");
			request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
			request.setAttribute("attachBusType", "BT3");
			request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());

			request.setAttribute("projectCode", work.getRefWorkId1());
			request.setAttribute("project", this.getProjectMasterInfoManager().getProject(work.getRefWorkId1()));

			request.setAttribute("sanctionDoc", sanctionDoc);
			// 실행 품의 (매출액 기준) 조건 : 210205
			SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType());
			Project project = null;
			project = this.getProjectMasterInfoManager().getErpProject2(sanctionDoc.getProjectCode());
			
			if((sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 100000000 && project.getBusinessTypeCode().equals("BTA"))
					||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTD"))
					||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE")))
			{
				sanctionTemplate.setHasSptTeamMng(false);
				sanctionTemplate.setHasCeo(false);
				
				sanctionTemplate.setRefMemberSsns("A000006" + ", " + "A000031");
				sanctionTemplate.setRefMemberNames("한수희" + ", " + "김기철");
			}
			
			// 임시 저장 시 탬플릿 수정
			if(sanctionDoc.getRegisterDate() == null){
				// 팀장 권한 추가
				SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
				if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
					sanctionTemplate.setHasCfo(true);
				}
				// 개인 탬플릿
				request.setAttribute("sanctionTemplate", sanctionTemplate);
			} else{
				// 결제 탬플릿
				request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate2(sanctionDoc.getApprovalType(), workId));
				/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
			}
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
			return this.getProjectLaunchSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getProjectLaunchSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		// By-Pass (사용안함)
		sanctionDoc.setFunc(sanctionDoc.getAssistant4Ssn());
		sanctionDoc.setProjectCodeBp(sanctionDoc.getAssistant4Dept());
		
		// sanctiondoc log
		String ssn = SessionUtils.getUsername(request);
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
		
		
		// 첨부파일 관련 항목
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("readonly", readonly);
		request.setAttribute("subinfoReadonly", readonly);
		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));
		request.setAttribute("sanctionDoc", sanctionDoc);
		
		// 실행 품의 (매출액 기준) 조건 : 210205
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType());
		Project project = null;
		project = this.getProjectMasterInfoManager().getErpProject2(sanctionDoc.getProjectCode());		
		if((sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 100000000 && project.getBusinessTypeCode().equals("BTA"))
				||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTD"))
				||(sanctionTemplate.getApprovalType().equals("A") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE"))
				||(sanctionTemplate.getApprovalType().equals("PA") && Double.parseDouble(project.getSalesAmt()) < 50000000 && project.getBusinessTypeCode().equals("BTE")))
		{
			sanctionTemplate.setHasSptTeamMng(false);
			sanctionTemplate.setHasCeo(false);
			
			sanctionTemplate.setRefMemberSsns("A000006" + ", " + "A000031");
			sanctionTemplate.setRefMemberNames("한수희" + ", " + "김기철");
		}
		
		//팀장 체크 (seq)
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/

		return mapping.findForward("projectLaunchSanctionForm");
	}

	private ActionForward getProjectLaunchSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		request.setAttribute("subinfoReadonly", readonly);

		request.setAttribute("fileMode", "READ");
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate3(sanctionDoc.getApprovalType(), seq));
		/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/


		return mapping.findForward("generalMobileSanctionForm");
	}

	public ActionForward getProjectLaunchSanctionContentData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String viewMode = ServletRequestUtils.getStringParameter(request, "viewMode", "lSanctionView");
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

		SanctionDoc sanctionDoc = null;
		if (workId.equals("")) {
			String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
			String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
			boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);
			if (seq.equals("0")) {
				sanctionDoc = new SanctionDoc();
				sanctionDoc.setApprovalType(approvalType);
				sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);
				sanctionDoc.setContent(this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()).getTemplateText());
				expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
				request.setAttribute("fileMode", "WRITE");
			} else {
				sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);
				request.setAttribute("readonly", readonly);
				request.setAttribute("fileMode", "READ");
				expertPool = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
			}
		} else {
			Work work = this.getWorklistManager().getWork(workId);
			sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3());
			sanctionDoc.setTaskId(workId);
			
			expertPool = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
			request.setAttribute("fileMode", "WRITE");
		}
		
		/* 프로젝트 */
		Project project = null;
		project = this.getProjectMasterInfoManager().getErpProject2(projectCode);
		request.setAttribute("project", project);
		
		/* 예산 시스템 */
		ProjectBudget projectBudget = null;
		projectBudget = this.getProjectMasterInfoManager().getProjectBudget(projectCode);
		request.setAttribute("projectBudget", projectBudget);
		
		/* 프로젝트 멤버 호출 */
		List<ProjectMember> projectMember = null;
		projectMember = this.getProjectMasterInfoManager().getProjectMember(projectCode);
		request.setAttribute("projectMember", projectMember);

		/* 첨부파일 업로드 */
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		request.setAttribute("attachOutputType", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
		request.setAttribute("fileList", this.getAttachManager().selectListForTask(wc, "approval_" + sanctionDoc.getSeq(), null, null).getList());
		request.setAttribute("attachOutputName", this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT5").getList());
		request.setAttribute("viewMode", viewMode);
		
		request.setAttribute("expertPool", expertPool);
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
		return mapping.findForward("sanctionContentInfo");
	}

	public void executeProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;
		
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");

		try {
			Assert.hasLength(sanctionForm.getContent());
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			
			/* By-Pass 추가 */
			if("Y".equals(func)){
				sanctionDoc.setProjectCodeBp(projectCodeBp);
				sanctionDoc.setFunc(func);	
			}else{
				System.out.println("Not By-Pass 4");
				sanctionDoc.setProjectCodeBp(null);
				sanctionDoc.setFunc(null);
			}

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("approval_" + sanctionDoc.getSeq());
				attachForm.setTaskId("approval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			String state = map.get("state");

			/* By-Pass 추가 */
			if(state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE) && ("Y".equals(func))){
				// PMS 프로젝트를 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "3");
				// 온라인 예산정보 '승인' 상태로 변경
				this.getProjectMasterInfoManager().updateBudgetState(sanctionForm.getProjectCode());
				// 온라인 예산정보 회계로 전송
				this.getProjectMasterInfoManager().sendBudgetState(sanctionForm.getProjectCode());
				// 회계 프로젝트 예산 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateErpState(sanctionForm.getProjectCode());
				// 기획사업 상태 변경(ceo confirm 'Y', chkVal 'N')
				this.getProjectMasterInfoManager().updateEduState(sanctionForm.getProjectCode());
				// 고객 땡큐 메일 전송
				//this.getPmsInfoMailSender().sendCustomerThx(sanctionForm.getProjectCode());
				// 지도일지 생성
				this.getProjectReportScheduleManager().assignProjectReportUntilApproveDate(sanctionForm.getProjectCode(), StringUtil.getCurr("yyyyMMdd"));
				// 프로젝트 MM 정보 생성
				this.getProjectMasterInfoManager().createProjectMemberMMInfo(sanctionForm.getProjectCode());
				//주간보고 생성 , from:계획 시작일 ~ to:현재일까지
				this.getWeeklyReportScheduleManager().assignWeeklyReportWhenApproved(sanctionForm.getProjectCode());
				
				/* By-Pass 추가 */
				// PMS 프로젝트를 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateProjectState(projectCodeBp, "3");
				// 온라인 예산정보 '승인' 상태로 변경
				//this.getProjectMasterInfoManager().updateBudgetState(projectCodeBp);
				// 온라인 예산정보 회계로 전송
				this.getProjectMasterInfoManager().sendBudgetState(projectCodeBp);
				// 회계 프로젝트 예산 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateErpState(projectCodeBp);
				// 기획사업 상태 변경(ceo confirm 'Y', chkVal 'N')
				this.getProjectMasterInfoManager().updateEduState(projectCodeBp);
				
			}else if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				// PMS 프로젝트를 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "3");
				// 온라인 예산정보 '승인' 상태로 변경
				this.getProjectMasterInfoManager().updateBudgetState(sanctionForm.getProjectCode());
				// 온라인 예산정보 회계로 전송
				this.getProjectMasterInfoManager().sendBudgetState(sanctionForm.getProjectCode());
				// 회계 프로젝트 예산 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateErpState(sanctionForm.getProjectCode());
				// 기획사업 상태 변경(ceo confirm 'Y', chkVal 'N')
				this.getProjectMasterInfoManager().updateEduState(sanctionForm.getProjectCode());
				// 고객 땡큐 메일 전송
				//this.getPmsInfoMailSender().sendCustomerThx(sanctionForm.getProjectCode());
				// 지도일지 생성
				this.getProjectReportScheduleManager().assignProjectReportUntilApproveDate(sanctionForm.getProjectCode(), StringUtil.getCurr("yyyyMMdd"));
				// 프로젝트 MM 정보 생성
				this.getProjectMasterInfoManager().createProjectMemberMMInfo(sanctionForm.getProjectCode());
				//주간보고 생성 , from:계획 시작일 ~ to:현재일까지
				this.getWeeklyReportScheduleManager().assignWeeklyReportWhenApproved(sanctionForm.getProjectCode());
			} else {
				this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "2");
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}
	
	public void executeProjectLaunchSanction_batch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		
		/* 프로젝트 코드 담기 */
		List<Project> projectInfo = null;
		projectInfo = this.getProjectMasterInfoManager().getProjectCodeInfo(SessionUtils.getUsername(request));
		
		try {
			/* loop */
			for(int i = 0; i < projectInfo.size(); i++){
				System.out.println(projectInfo.get(i).getProjectCode());
				// PMS 프로젝트를 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateProjectState(projectInfo.get(i).getProjectCode(), "3");
				// 온라인 예산정보 '승인' 상태로 변경
				this.getProjectMasterInfoManager().updateBudgetState(projectInfo.get(i).getProjectCode());
				// 온라인 예산정보 회계로 전송
				this.getProjectMasterInfoManager().sendBudgetState(projectInfo.get(i).getProjectCode());
				// 회계 프로젝트 예산 '진행' 상태로 변경
				this.getProjectMasterInfoManager().updateErpState(projectInfo.get(i).getProjectCode());
				// 기획사업 상태 변경(ceo confirm 'Y', chkVal 'N')
				this.getProjectMasterInfoManager().updateEduState(projectInfo.get(i).getProjectCode());
				// 고객 땡큐 메일 전송
				//this.getPmsInfoMailSender().sendCustomerThx(sanctionForm.getProjectCode());
				// 지도일지 생성
				this.getProjectReportScheduleManager().assignProjectReportUntilApproveDate(projectInfo.get(i).getProjectCode(), StringUtil.getCurr("yyyyMMdd"));
				// 프로젝트 MM 정보 생성
				this.getProjectMasterInfoManager().createProjectMemberMMInfo(projectInfo.get(i).getProjectCode());
				//주간보고 생성 , from:계획 시작일 ~ to:현재일까지
				this.getWeeklyReportScheduleManager().assignWeeklyReportWhenApproved(projectInfo.get(i).getProjectCode());
			}
			
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;
		
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");
		System.out.println(func);
		System.out.println(projectCodeBp);

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			
			/*By-Pass 코드 추가*/
			if("Y".equals(func)){
				sanctionDoc.setProjectCodeBp(projectCodeBp);
				sanctionDoc.setFunc(func);	
			}else{
				sanctionDoc.setProjectCodeBp(null);
				sanctionDoc.setFunc(null);
			}
			
			map = this.getSanctionDocManager().createGeneralSanctionWork("S028809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("approval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "2");
			
			/* By-Pass 추가 */
			if("Y".equals(func)){
				this.getProjectMasterInfoManager().updateProjectState(projectCodeBp, "2");
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;
		
		// By-Pass
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");
		
		System.out.println(func);
		System.out.println(projectCodeBp);
		
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			this.getProjectMasterInfoManager().deleteProject(sanctionDoc.getProjectCode());
			
			// By-Pass
			if("Y".equals(func)){
				this.getProjectMasterInfoManager().deleteProject(projectCodeBp);
			}else { System.out.println("Not By-Pass 5"); }
			
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void draftProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");
		System.out.println(func);
		System.out.println(projectCodeBp);

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			
			if("Y".equals(func)){
				sanctionDoc.setProjectCodeBp(projectCodeBp);
				sanctionDoc.setFunc(func);	
			}else{
				sanctionDoc.setProjectCodeBp(null);
				sanctionDoc.setFunc(null);
			}
			map = this.getSanctionDocManager().draftSaveGeneralSanction("S028809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("approval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "2");
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveProjectLaunchSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");
		System.out.println(func);
		System.out.println(projectCodeBp);
		
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			
			if("Y".equals(func)){
				sanctionDoc.setProjectCodeBp(projectCodeBp);
				sanctionDoc.setFunc(func);	
			}else{
				sanctionDoc.setProjectCodeBp(null);
				sanctionDoc.setFunc(null);
			}

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

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

	public ProjectReportScheduleManager getProjectReportScheduleManager() {
		return projectReportScheduleManager;
	}

	public void setProjectReportScheduleManager(ProjectReportScheduleManager projectReportScheduleManager) {
		this.projectReportScheduleManager = projectReportScheduleManager;
	}

	public CcoTargetManager getCcoTargetManager() {
		return ccoTargetManager;
	}

	public void setCcoTargetManager(CcoTargetManager ccoTargetManager) {
		this.ccoTargetManager = ccoTargetManager;
	}

	public WeeklyReportScheduleManager getWeeklyReportScheduleManager() {
		return weeklyReportScheduleManager;
	}

	public void setWeeklyReportScheduleManager(
			WeeklyReportScheduleManager weeklyReportScheduleManager) {
		this.weeklyReportScheduleManager = weeklyReportScheduleManager;
	}

	public MonthlyReportScheduleManager getMonthlyReportScheduleManager() {
		return monthlyReportScheduleManager;
	}

	public void setMonthlyReportScheduleManager(MonthlyReportScheduleManager monthlyReportScheduleManager) {
		this.monthlyReportScheduleManager = monthlyReportScheduleManager;
	}

	
}
