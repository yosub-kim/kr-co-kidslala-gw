package kr.co.kmac.pms.sanction.general.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
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
 * �Ϲ� ���ڰ��� ����
 */
/**
 * @struts.action name="generalSanctionAction" path="/action/GeneralSanctionAction" parameter="mode" scope="request"
 * @struts.action-forward name="generalSanctionForm" path="/sanction/sanctionForm/generalSanctionForm.jsp" redirect="false"
 * @struts.action-forward name="sanctionPrintForm" path="/sanction/sanctionForm/sanctionPrintForm.jsp" redirect="false"
 * @struts.action-forward name="generalMobileSanctionForm" path="/m/web/sanction/sanctionDraft.jsp" redirect="false"
 */
public class GeneralSanctionAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(GeneralSanctionAction.class);
	private SanctionTemplateManager sanctionTemplateManager;
	private SanctionDocManager sanctionDocManager;
	private WorklistManager worklistManager;
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private SanctionLineManager sanctionLineManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;

	public ActionForward loadFormGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// ������� ����
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}
		
		// ���� ���� �߰�
		if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
			sanctionTemplate.setHasCfo(true);
		}
		
		// �����޽�û, ����ī��, ��������, ���ñٹ� �� �����ڿ� COO ���� / ���� ����
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft1") || sanctionTemplate.getApprovalType().equals("draft2") || sanctionTemplate.getApprovalType().equals("S")
				 || sanctionTemplate.getApprovalType().equals("draft25"))) 
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
		
		// �ް���, ���������� ������ coo ����
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft4") || sanctionTemplate.getApprovalType().equals("draft10"))) 
		{
			// �濵��ȹ���� �ް���, �������� ���� �߰�
			if(expertPool.getDept().equals("9381") || expertPool.getDept().equals("9383") || expertPool.getDept().equals("9384")){
				sanctionTemplate.setHasTeamManager(true);
				sanctionDoc.setTeamManagerSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setTeamManagerName(sanctionLine.getTeamManagerName());
				sanctionDoc.setTeamManagerDept(sanctionLine.getTeamManagerDept());
			}else if ((sanctionLine.getCfoSsn() != null && sanctionTemplate != null ) || (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals(""))) {
				sanctionTemplate.setHasCfo(false);
				sanctionDoc.setCioSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setCioName(sanctionLine.getTeamManagerName());
				sanctionDoc.setCioDept(sanctionLine.getTeamManagerDept());
			}
		}
		
		// ������ �����ڸ� �������� �߰�
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
		
		// �系��ȣȸ ��û 
		if (sanctionTemplate != null && (sanctionTemplate.getApprovalType().equals("draft24"))) 
		{
			// COO�� �����ϴ� ��쿡�� ����, COO ������ ���� ���� CBO�� ������ �Ⱓ�� ���ɼ� ����
			sanctionDoc.setCioSsn("A000038");
			sanctionDoc.setCioName("�۱�ȣ");
			sanctionDoc.setCioDept("9371");
		}
		
		// mou ǰ�� (���� 1, ���� 2)
		if (sanctionTemplate != null && (sanctionTemplate.getApprovalType().equals("draft20"))) 
		{
			// ���� 1 : �濵��ȹ������
			sanctionDoc.setAssistant1Ssn("A000038");
			sanctionDoc.setAssistant1Name("�۱�ȣ");
			sanctionDoc.setAssistant1Dept("9371");
			
			// ���� 2 : CSO
			sanctionDoc.setAssistant2Ssn("A000007");
			sanctionDoc.setAssistant2Name("�ֵ���");
			sanctionDoc.setAssistant2Dept("2040");
		}
		
		// ������ (���, ra), ���˿� ������ ������, ������ �߰�
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft5") || sanctionTemplate.getApprovalType().equals("draft23") || sanctionTemplate.getApprovalType().equals("draft15"))) 
		{
			sanctionTemplate.setRefMemberSsns("A000038" + ", " + "G000840" + ", " + "A000106");
			sanctionTemplate.setRefMemberNames("�۱�ȣ" + ", " + "������" + ", " + "������");
		}
		
		// �̵��� CEO �ֵ���󹫴�
		if(sanctionTemplate != null && expertPool.getDept().equals("9361")){
			sanctionTemplate.setCeoSsn("A000007");
			sanctionTemplate.setCeoName("�ֵ���");
			sanctionTemplate.setCeoDept("2040");
		}
		
		//���ñٹ� ���� ���� �� coo, cbo ���� ����
		if(sanctionTemplate != null && sanctionTemplate.getApprovalType().equals("draft26")){
			//cfo, team �Ѵ� �ִ°�� - ���� ��� coo����ó��
			if((sanctionLine.getCfoSsn() != null && !sanctionLine.getCfoSsn().equals("") && sanctionTemplate != null ) && (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals(""))){
				sanctionTemplate.setHasCfo(false);
				sanctionDoc.setCioSsn(sanctionLine.getCfoSsn());
				sanctionDoc.setCioName(sanctionLine.getCfoName());
				sanctionDoc.setCioDept(sanctionLine.getCfoDept());
			}
			//cfo���� team�� �ִ°�� - �Ϲݺμ� ����, �ι��� ����(��3),�濵��ȹ ����(coo����)
			else if(((sanctionLine.getCfoSsn() == null || sanctionLine.getCfoSsn().equals("")) && sanctionTemplate != null ) && (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals(""))){
				String tempdept = sanctionLine.getTeamManagerDept();
				if(!tempdept.equals("9323") && !tempdept.equals("9381")){
					sanctionTemplate.setHasCfo(false);
					sanctionDoc.setCioSsn(sanctionLine.getTeamManagerSsn());
					sanctionDoc.setCioName(sanctionLine.getTeamManagerName());
					sanctionDoc.setCioDept(sanctionLine.getTeamManagerDept());
				}
			}
			//coo-cfo,team �Ѵپ��� (�ι��� ����)
			else{
				sanctionTemplate.setHasCfo(false);
				sanctionDoc.setCioSsn(sanctionLine.getCioSsn());
				sanctionDoc.setCioName(sanctionLine.getCioName());
				sanctionDoc.setCioDept(sanctionLine.getCioDept());
			}		
			
			int temp = Integer.parseInt(expertPool.getCompanyPosition().substring(0, 2));
			if(temp < 07){
				sanctionDoc.setCioSsn(expertPool.getSsn());
				sanctionDoc.setCioName(expertPool.getName());
				sanctionDoc.setCioDept(expertPool.getDept());
			}
		}

		sanctionDoc.setProjectCode(projectCode.equals("") ? "8888888" : projectCode);
		if (!projectCode.equals("") && !projectCode.equals("8888888")) {
			Project project = this.getProjectMasterInfoManager().getProject(projectCode);
			request.setAttribute("projectCode", project.getProjectCode());
			request.setAttribute("projectName", project.getProjectName());
			// ����ī�� ��û�� ��� �����ڸ� �ڵ� ���� ��(������: ����, PM)
			if (sanctionDoc.getApprovalType() != null && sanctionDoc.getApprovalType().equals("draft2")) {
				ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
				// ������ PM�� ���� ��� �����ڸ� �� ���� ������
				if (projectSimpleInfo.getArSsn().equals(projectSimpleInfo.getPmSsn())) {
					sanctionTemplate.setRefMemberSsns(projectSimpleInfo.getArSsn());
					sanctionTemplate.setRefMemberNames(projectSimpleInfo.getArName());
				} else {
					sanctionTemplate.setRefMemberSsns(projectSimpleInfo.getArSsn() + ", " + projectSimpleInfo.getPmSsn());
					sanctionTemplate.setRefMemberNames(projectSimpleInfo.getArName() + ", " + projectSimpleInfo.getPmName());
				}
			}
		}
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		// ----���ϰ��� ����
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute(
				"attachOutputType",
				this.getAttachTemplateManager()
						.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
						.getList());
		request.setAttribute("attachBusType", "BT4");
		// ----���ϰ��� ��

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true, "KMAC"));
		// userdept, username �ҷ�����
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());

		return mapping.findForward("generalSanctionForm");
	}

	public ActionForward loadFormGeneralSanctionForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// ������� ����
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}
		
		// ���� ���� �߰�
		if(sanctionLine.getCfoSsn() != null && sanctionTemplate != null ){			
			sanctionTemplate.setHasCfo(true);
		}
		
		// �����޽�û, ����ī��, ��������, ���ñٹ� �� �����ڿ� COO ���� / ���� ����
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft1") || sanctionTemplate.getApprovalType().equals("draft2") || sanctionTemplate.getApprovalType().equals("S")
				 || sanctionTemplate.getApprovalType().equals("draft25"))) 
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
		
		// �ް���, ���������� ������ coo ����
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft4") || sanctionTemplate.getApprovalType().equals("draft10"))) 
		{
			// �濵��ȹ���� �ް���, �������� ���� �߰�
			if(expertPool.getDept().equals("9381") || expertPool.getDept().equals("9383") || expertPool.getDept().equals("9384")){
				sanctionTemplate.setHasTeamManager(true);
				sanctionDoc.setTeamManagerSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setTeamManagerName(sanctionLine.getTeamManagerName());
				sanctionDoc.setTeamManagerDept(sanctionLine.getTeamManagerDept());
			}else if ((sanctionLine.getCfoSsn() != null && sanctionTemplate != null ) || (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals(""))) {
				sanctionTemplate.setHasCfo(false);
				sanctionDoc.setCioSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setCioName(sanctionLine.getTeamManagerName());
				sanctionDoc.setCioDept(sanctionLine.getTeamManagerDept());
			}
		}
		
		// ������ �����ڸ� �������� �߰�
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
		
		// �系��ȣȸ ��û 
		if (sanctionTemplate != null && (sanctionTemplate.getApprovalType().equals("draft24"))) 
		{
			// COO�� �����ϴ� ��쿡�� ����, COO ������ ���� ���� CBO�� ������ �Ⱓ�� ���ɼ� ����
			sanctionDoc.setCioSsn("A000038");
			sanctionDoc.setCioName("�۱�ȣ");
			sanctionDoc.setCioDept("9371");
		}
		
		// mou ǰ�� (���� 1, ���� 2)
		if (sanctionTemplate != null && (sanctionTemplate.getApprovalType().equals("draft20"))) 
		{
			// ���� 1 : �濵��ȹ������
			sanctionDoc.setAssistant1Ssn("A000038");
			sanctionDoc.setAssistant1Name("�۱�ȣ");
			sanctionDoc.setAssistant1Dept("9371");
			
			// ���� 2 : CSO
			sanctionDoc.setAssistant2Ssn("A000007");
			sanctionDoc.setAssistant2Name("�ֵ���");
			sanctionDoc.setAssistant2Dept("2040");
		}
		
		// ������ (���, ra), ���˿� ������ ������, ������ �߰�
		if (sanctionTemplate != null && 
				(sanctionTemplate.getApprovalType().equals("draft5") || sanctionTemplate.getApprovalType().equals("draft23") || sanctionTemplate.getApprovalType().equals("draft15"))) 
		{
			sanctionTemplate.setRefMemberSsns("A000038" + ", " + "G000840" + ", " + "A000106");
			sanctionTemplate.setRefMemberNames("�۱�ȣ" + ", " + "������" + ", " + "������");
		}
		
		// �̵��� CEO �ֵ���󹫴�
		if(sanctionTemplate != null && expertPool.getDept().equals("9361")){
			sanctionTemplate.setCeoSsn("A000007");
			sanctionTemplate.setCeoName("�ֵ���");
			sanctionTemplate.setCeoDept("2040");
		}
		
		//���ñٹ� ���� ���� �� coo, cbo ���� ����
		if(sanctionTemplate != null && sanctionTemplate.getApprovalType().equals("draft26")){
			//������� ����
			if((sanctionLine.getCfoSsn() != null && sanctionTemplate != null ) || (sanctionLine.getTeamManagerSsn() != null && !sanctionLine.getTeamManagerSsn().equals(""))){
				sanctionTemplate.setHasCfo(false);
				sanctionDoc.setCioSsn(sanctionLine.getTeamManagerSsn());
				sanctionDoc.setCioName(sanctionLine.getTeamManagerName());
				sanctionDoc.setCioDept(sanctionLine.getTeamManagerDept());
			}
			// coo�̻� ǰ�� ����
			int temp = Integer.parseInt(expertPool.getCompanyPosition().substring(0, 2));
			if(temp < 10){
				sanctionDoc.setCioSsn(expertPool.getSsn());
				sanctionDoc.setCioName(expertPool.getName());
				sanctionDoc.setCioDept(expertPool.getDept());
			}
		}

		sanctionDoc.setProjectCode(projectCode.equals("") ? "8888888" : projectCode);
		if (!projectCode.equals("") && !projectCode.equals("8888888")) {
			Project project = this.getProjectMasterInfoManager().getProject(projectCode);
			request.setAttribute("projectCode", project.getProjectCode());
			request.setAttribute("projectName", project.getProjectName());
			// ����ī�� ��û�� ��� �����ڸ� �ڵ� ���� ��(������: ����, PM)
			if (sanctionDoc.getApprovalType() != null && sanctionDoc.getApprovalType().equals("draft2")) {
				ProjectSimpleInfo projectSimpleInfo = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
				// ������ PM�� ���� ��� �����ڸ� �� ���� ������
				if (projectSimpleInfo.getArSsn().equals(projectSimpleInfo.getPmSsn())) {
					sanctionTemplate.setRefMemberSsns(projectSimpleInfo.getArSsn());
					sanctionTemplate.setRefMemberNames(projectSimpleInfo.getArName());
				} else {
					sanctionTemplate.setRefMemberSsns(projectSimpleInfo.getArSsn() + ", " + projectSimpleInfo.getPmSsn());
					sanctionTemplate.setRefMemberNames(projectSimpleInfo.getArName() + ", " + projectSimpleInfo.getPmName());
				}
			}
		}
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		// ----���ϰ��� ����
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute(
				"attachOutputType",
				this.getAttachTemplateManager()
						.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
						.getList());
		request.setAttribute("attachBusType", "BT4");
		// ----���ϰ��� ��

		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true, "KMAC"));
		// userdept, username �ҷ�����
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());

		return mapping.findForward("generalMobileSanctionForm");
	}

	public ActionForward loadFormCallCenterSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType");

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// ������� ����
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}

		sanctionDoc.setProjectCode("8888888");
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		// ----���ϰ��� ����
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute(
				"attachOutputType",
				this.getAttachTemplateManager()
						.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
						.getList());
		request.setAttribute("attachBusType", "BT4");
		// ----���ϰ��� ��
		request.setAttribute("callCenterSanction", true);
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true, "CC"));

		return mapping.findForward("generalSanctionForm");
	}

	public ActionForward loadFormGeneralForReSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType");
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String eduCourseCode = ServletRequestUtils.getStringParameter(request, "eduCourseCode", "");

		SanctionDoc sanctionDoc = new SanctionDoc();
		sanctionDoc.setApprovalType(approvalType);

		// ������� ����
		SanctionLine sanctionLine = this.getSanctionLineManager().selectSanctionLine(SessionUtils.getUsername(request));
		SanctionTemplate sanctionTemplate = this.getSanctionTemplateManager().getSanctionTemplate(approvalType);
		if (sanctionLine != null) {
			setSanctionList(sanctionDoc, sanctionLine, sanctionTemplate);
		} else {
			ExpertPool expertPool = getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
			sanctionDoc.setRegisterSsn(expertPool.getSsn());
			sanctionDoc.setRegisterDept(expertPool.getDept());
		}
		
		// ������ �����ڸ� �������� �߰�
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

		sanctionDoc.setProjectCode(projectCode.equals("") ? "8888888" : projectCode);
		if (!projectCode.equals("") && !projectCode.equals("8888888")) {
			Project project = this.getProjectMasterInfoManager().getProject(projectCode);
			request.setAttribute("projectCode", project.getProjectCode());
			request.setAttribute("projectName", project.getProjectName());
		}
		sanctionDoc.setRegisterDate(new Date());
		sanctionDoc.setReject("N");
		sanctionDoc.setState(SanctionConstants.SANCTION_STATE_DRAFT);

		// ----���ϰ��� ����
		request.setAttribute("fileMode", "WRITE");
		request.setAttribute("viewMode", "SIMPLE");
		request.setAttribute(
				"attachOutputType",
				this.getAttachTemplateManager()
						.selectOutputTypeCodeList(WebApplicationContextUtils.getWebApplicationContext(super.getServletContext()), null, "BT4")
						.getList());
		request.setAttribute("attachBusType", "BT4");
		// ----���ϰ��� ��

		request.setAttribute("isReSanction", true);
		request.setAttribute("sanctionDoc", sanctionDoc);
		request.setAttribute("sanctionTemplate", sanctionTemplate);
		request.setAttribute("eduCourseCode", eduCourseCode);

		SanctionTemplate c = this.getSanctionTemplateManager().getSanctionTemplate("C");
		SanctionTemplate r = this.getSanctionTemplateManager().getSanctionTemplate("R");
		List<SanctionTemplate> sanctionTemplateList = new ArrayList<SanctionTemplate>();
		sanctionTemplateList.add(c);
		sanctionTemplateList.add(r);

		request.setAttribute("sanctionTemplateList", sanctionTemplateList);

		return mapping.findForward("generalSanctionForm");
	}

	public ActionForward getGeneralSanctionData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);
		boolean isPrint = ServletRequestUtils.getBooleanParameter(request, "isPrint", false);

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());

			if (!sanctionDoc.getProjectCode().equals("8888888")) {
				Project project = this.getProjectMasterInfoManager().getProject(sanctionDoc.getProjectCode());
				request.setAttribute("projectCode", project.getProjectCode());
				request.setAttribute("projectName", project.getProjectName());
				request.setAttribute("eduCourseCode", sanctionDoc.getEduCourseCode());
			}
			
			/* ���ڰ��� �α� ���� �߰� */
			String ssn = SessionUtils.getUsername(request); //���� ���
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
			System.out.println(workId);
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate2(sanctionDoc.getApprovalType(), workId));
			/*request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));*/
			request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate());	//	jobDate: 2015-12-08	description: remove true param
			request.setAttribute("readonly", readonly);
			request.setAttribute("isRefWork", isRefWork);
			// userdept, username �ҷ�����
			ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
			request.setAttribute("userDept", ex.getDeptName());
			request.setAttribute("userName", ex.getName());
			request.setAttribute("companyPositionName", ex.getCompanyPositionName());
			
			if (isPrint) {
				return mapping.findForward("sanctionPrintForm");
			} else {
				return mapping.findForward("generalSanctionForm");
			}
		} else {
			return getGeneralSanctionData1(mapping, form, request, response);
		}
	}

	public ActionForward getGeneralSanctionDataForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", false);
		boolean isRefWork = ServletRequestUtils.getBooleanParameter(request, "isRefWork", false);

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);

			SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(work.getRefWorkId1(), work.getRefWorkId2(),
					work.getRefWorkId3());
			sanctionDoc.setTaskId(work.getId());

			if (!sanctionDoc.getProjectCode().equals("8888888")) {
				Project project = this.getProjectMasterInfoManager().getProject(sanctionDoc.getProjectCode());
				request.setAttribute("projectCode", project.getProjectCode());
				request.setAttribute("projectName", project.getProjectName());
			}
			
			/* ���ڰ��� �α� ���� �߰� */
			String ssn = SessionUtils.getUsername(request); //���� ���
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
			request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
			request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true));
			request.setAttribute("readonly", readonly);
			request.setAttribute("isRefWork", isRefWork);
			return mapping.findForward("generalMobileSanctionForm");
		} else {
			return getGeneralSanctionData1ForMobile(mapping, form, request, response);
		}
	}

	private ActionForward getGeneralSanctionData1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);
		boolean isPrint = ServletRequestUtils.getBooleanParameter(request, "isPrint", false);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		if (!sanctionDoc.getProjectCode().equals("8888888")) {
			Project project = this.getProjectMasterInfoManager().getProject(sanctionDoc.getProjectCode());
			request.setAttribute("projectCode", project.getProjectCode());
			request.setAttribute("projectName", project.getProjectName());
		}
		
		/* ���ڰ��� �α� ���� �߰� */
			String ssn = SessionUtils.getUsername(request); //���� ���
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
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate()); // jobDate: 2015-12-08	description: remove true param
		// userdept, username �ҷ�����
		ExpertPool ex = getExpertPoolManager().retrieve(sanctionDoc.getRegisterSsn());
		request.setAttribute("userDept", ex.getDeptName());
		request.setAttribute("userName", ex.getName());
		request.setAttribute("companyPositionName", ex.getCompanyPositionName());
		if (isPrint) {
			return mapping.findForward("sanctionPrintForm");
		} else {
			return mapping.findForward("generalSanctionForm");
		}		
	}

	private ActionForward getGeneralSanctionData1ForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getStringParameter(request, "projectCode", "");
		String approvalType = ServletRequestUtils.getStringParameter(request, "approvalType", "");
		String seq = ServletRequestUtils.getStringParameter(request, "seq", "");
		boolean readonly = ServletRequestUtils.getBooleanParameter(request, "readonly", true);

		SanctionDoc sanctionDoc = this.getSanctionDocManager().getGeneralSanctionDoc(projectCode, approvalType, seq);

		if (!sanctionDoc.getProjectCode().equals("8888888")) {
			Project project = this.getProjectMasterInfoManager().getProject(sanctionDoc.getProjectCode());
			request.setAttribute("projectCode", project.getProjectCode());
			request.setAttribute("projectName", project.getProjectName());
		}
		
		/* ���ڰ��� �α� ���� �߰� */
		String ssn = SessionUtils.getUsername(request); //���� ���
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
		request.setAttribute("sanctionTemplate", this.getSanctionTemplateManager().getSanctionTemplate(sanctionDoc.getApprovalType()));
		request.setAttribute("sanctionTemplateList", this.getSanctionTemplateManager().getSanctionTemplate(true));

		return mapping.findForward("generalMobileSanctionForm");
	}

	public void executeGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				this.getAttachManager().deleteAllInTask("businessApproval_" + sanctionDoc.getSeq());
				attachForm.setTaskId("businessApproval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
			map = this.getSanctionDocManager().executeGeneralSanctionWork(sanctionDoc);

			// Job Date: 2010-02-05 Author: yhyim Description: ��ȹ�����Ȳ���� ǰ�Ǵ���� ������Ʈ ó��
			String state = map.get("state");
			if (state != null && state.equals(SanctionConstants.SANCTION_STATE_COMPLETE)) {
				if (sanctionDoc.getApprovalType().equals("R")) { // ��ȹ��� ��ǰ��
					this.getProjectMasterInfoManager().updateEduStateAgain(sanctionForm.getEduCourseCode());
				} else if (sanctionDoc.getApprovalType().equals("C")) { // ��ȹ��� ���ǰ��
					this.getProjectMasterInfoManager().updateEduStateCancle(sanctionForm.getProjectCode(), sanctionForm.getEduCourseCode());
				} else if (sanctionDoc.getApprovalType().equals("PC")) { // ������Ʈ ���ǰ��
					this.getProjectMasterInfoManager().updateProjectState(sanctionForm.getProjectCode(), "7");
				}
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void executeRefWork(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			map = this.getSanctionDocManager().executeRefWork(sanctionDoc, SessionUtils.getUsername(request));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void draftSaveGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			map = this.getSanctionDocManager().draftSaveGeneralSanction("S018809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("businessApproval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			
			// Job Date: 2017-09-13 Author: yhyim Description: ��ȹ�����Ȳ���� ��ǰ�Ǵ���� ������Ʈ ó��(��ǰ�������� ���� ����)
			if (sanctionDoc.getApprovalType().equals("R") || sanctionDoc.getApprovalType().equals("C")) {
				if (!sanctionForm.getEduCourseCode().equals(""))
					this.getProjectMasterInfoManager().updateEduStateResanction(sanctionForm.getEduCourseCode());
			}
			
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void createGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			map = this.getSanctionDocManager().createGeneralSanctionWork("S018809e0a4c4436010a4ead57e4001f", sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("businessApproval_" + map.get("seq"));
				this.getAttachManager().insert(attachForm);
			}
			
			// Job Date: 2015-07-15 Author: yhyim Description: ��ȹ�����Ȳ���� ��ǰ�Ǵ���� ������Ʈ ó��(��ǰ�������� ���� ����)
			if (sanctionDoc.getApprovalType().equals("R") || sanctionDoc.getApprovalType().equals("C")) {
				if (!sanctionForm.getEduCourseCode().equals(""))
					this.getProjectMasterInfoManager().updateEduStateResanction(sanctionForm.getEduCourseCode());
			}
			
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void deleteGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;
		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().deleteGeneralSanctionDoc(sanctionDoc);
			
			// Job Date: 2015-07-21 Author: yhyim Description: ��ȹ��� ��ǰ�� �Ǵ� ���ǰ�� ���� �� ��ȹ�����Ȳ�� �ʱ�ȭ ��
			if (sanctionDoc.getApprovalType().equals("R") || sanctionDoc.getApprovalType().equals("C")) {
				if (!sanctionForm.getEduCourseCode().equals(""))
					this.getProjectMasterInfoManager().restoreEduStateResanction(sanctionForm.getEduCourseCode());
			}
			
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void saveGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().saveGeneralSanctionDoc(sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("businessApproval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void entrustGeneralSanction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SanctionForm sanctionForm = (SanctionForm) form;

		try {
			SanctionDoc sanctionDoc = SanctionDoc.valueOf(sanctionForm);
			this.getSanctionDocManager().entrustGeneralSanctionDoc(sanctionDoc);

			AttachForm attachForm = (AttachForm) sanctionForm;
			if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId().length > 0) {
				attachForm.setTaskId("businessApproval_" + sanctionDoc.getSeq());
				this.getAttachManager().insert(attachForm);
			}
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

	public SanctionLineManager getSanctionLineManager() {
		return sanctionLineManager;
	}

	public void setSanctionLineManager(SanctionLineManager sanctionLineManager) {
		this.sanctionLineManager = sanctionLineManager;
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

}
