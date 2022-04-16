package kr.co.kmac.pms.project.master.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.IdGenerator;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectCsrInfo;
import kr.co.kmac.pms.project.master.data.ProjectCustomerInfo;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.master.data.ProjectOrgdb;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.form.ProjectMasterInfoForm;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.project.master.manager.ProjectOrgdbManager;
import kr.co.kmac.pms.project.preport.manager.ProjectReportInfoManager;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;
import kr.co.kmac.pms.project.progress.manager.ProjectProgressManager;
import kr.co.kmac.pms.project.search.manager.HashTagManager;
import kr.co.kmac.pms.sanction.general.manager.SanctionDocManager;
import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;
import kr.co.kmac.pms.system.processcategory.manager.ProcessCategoryManager;
import kr.co.kmac.pms.system.processtemplate.data.ProcessTemplateDetail;
import kr.co.kmac.pms.system.processtemplate.manager.ProcessTemplateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="projectMasterInfoAction" path="/action/ProjectMasterInfoAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectMasterInfo" path="/project/master/projectMasterInfo.jsp" redirect="false"
 * @struts.action-forward name="projectCustomerInfoList" path="/project/master/projectCustomerInfoList.jsp" redirect="false"
 */
public class ProjectMasterInfoAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectMasterInfoAction.class);
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ProcessCategoryManager processCategoryManager;
	private ProjectProgressManager projectProgressManager;
	private ProcessTemplateManager processTemplateManager;
	private ProjectOrgdbManager projectOrgdbManager;
	private ProjectReportInfoManager projectReportInfoManager;
	private SanctionDocManager sanctionDocManager;
	private CommonCodeManager commonCodeManager;
	private HashTagManager hashTagManager;

	public ActionForward getProjectMasterInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String viewMode = ServletRequestUtils.getRequiredStringParameter(request, "viewMode");
		String projectRole = ServletRequestUtils.getRequiredStringParameter(request, "projectRole");
		boolean isRefresh = ServletRequestUtils.getBooleanParameter(request, "isRefresh", false);
		
		Project project = null;
		if (!isRefresh && this.getProjectMasterInfoManager().isRegisteredProject(projectCode)) {
			project = this.getProjectMasterInfoManager().getProject(projectCode);
		} else {
			project = this.getProjectMasterInfoManager().getErpProject(projectCode);
			// 예산 동기화 시 프로젝트 상태 1로 변경
			// Project oldProject = this.getProjectMasterInfoManager().getProject(projectCode);
			// if (oldProject != null) {
			// project.setProjectState(oldProject.getProjectState());
			// }
		}
		
		List<ProcessCategory> processCategoryList = this.getProcessCategoryManager().getProcessCatogoryList(null, project.getBusinessTypeCode(),
				project.getRunningDeptCode(), null, null);
		
		List<CodeEntity> businessFunctionTypeList = this.getCommonCodeManager().getCodeEntityList("BUSINESS_FUNCTION_TYPE");
		
		List<ProjectOrgdb> projectOrgdbList = this.getProjectOrgdbManager().getProjectOrgdb(projectCode);
		if (projectOrgdbList != null) {
			String orgdbCode = "";
			String orgdbName = "";
			for (ProjectOrgdb projectOrgdb : projectOrgdbList) {
				orgdbCode = orgdbCode + ", " + projectOrgdb.getOrgCode();
				orgdbName = orgdbName + ", " + projectOrgdb.getOrgName();
			}
			if (orgdbCode.startsWith(",")) {
				orgdbCode = orgdbCode.substring(1).trim();
			}
			if (orgdbName.startsWith(",")) {
				orgdbName = orgdbName.substring(1).trim();
		
			}
			request.setAttribute("orgdbCodes", orgdbCode);
			request.setAttribute("orgdbNames", orgdbName);
		}
		try {
			request.setAttribute("sanctionDoc", sanctionDocManager.getGeneralSanctionDoc(projectCode));
		} catch (Exception e) {
		}
		
		// Job Date: 2019-09-19 Author: yskim	Description: SALARY_CHANGE_DATE 요율,금액 변경 제어 추가 
		CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("SALARY_CHANGE_DATE", "date");
		request.setAttribute("canSanction", codeEntity);
		
		request.setAttribute("project", project);
		request.setAttribute("processCategoryList", processCategoryList);
		request.setAttribute("businessFunctionTypeList", businessFunctionTypeList);
		request.setAttribute("projectCode", projectCode);
		request.setAttribute("viewMode", viewMode);
		request.setAttribute("projectRole", projectRole);
		
		request.setAttribute("isRefresh", isRefresh);
		request.setAttribute("isRegisteredProject", this.getProjectMasterInfoManager().isRegisteredProject(projectCode));
		request.setAttribute("hashTagList", this.getHashTagManager().getHashTag(projectCode, "SHOW"));
		
		return mapping.findForward("projectMasterInfo");
	}

	public void createProjectMasterInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectMasterInfoForm projectMasterInfoForm = (ProjectMasterInfoForm) form;
		Project project = null;
		Project projectBp = null;
		boolean isRegisteredProject = this.getProjectMasterInfoManager().isRegisteredProject(projectMasterInfoForm.getProjectCode());
		if (isRegisteredProject && !projectMasterInfoForm.getIsRefresh().equals("true")) {
			project = this.getProjectMasterInfoManager().getProject(projectMasterInfoForm.getProjectCode());
		} else {
			project = this.getProjectMasterInfoManager().getErpProject(projectMasterInfoForm.getProjectCode());
		}

		List<ProcessTemplateDetail> processTemplateDetailList = null;
		if (project.getProjectDetailCode() == null || !project.getProjectDetailCode().equals(projectMasterInfoForm.getProjectDetailCode())) {
			ProcessCategory processCategory = this.getProcessCategoryManager().getProcessCategory(projectMasterInfoForm.getProjectDetailCode());
			processTemplateDetailList = this.getProcessTemplateManager().getProcessTemplateDetail(processCategory.getProcessTemplateCode());
			project.setProcessTypeCode(processCategory.getProcessTemplateCode());
		}
		project.setEntNo(projectMasterInfoForm.getEntNo());
		project.setProjectMemberList(ProjectMember.valueOf(projectMasterInfoForm));
		project.setProjectCsrInfoList(ProjectCsrInfo.valueOf(projectMasterInfoForm));
		project.setProjectDetailCode(projectMasterInfoForm.getProjectDetailCode());
		project.setBusinessFunctionType(projectMasterInfoForm.getBusinessFunctionType());
		project.setIsVoc(projectMasterInfoForm.getIsVoc());
		project.setCustomerWorkPEmail(projectMasterInfoForm.getCustomerWorkPEmail());
		project.setCustomerWorkPName(projectMasterInfoForm.getCustomerWorkPName());
		project.setCustomerWorkPPhone(projectMasterInfoForm.getCustomerWorkPPhone());
		project.setCustomerContPEmail(projectMasterInfoForm.getCustomerContPEmail());
		project.setCustomerContPName(projectMasterInfoForm.getCustomerContPName());
		project.setCustomerContPPhone(projectMasterInfoForm.getCustomerContPPhone());
		project.setProjectState(projectMasterInfoForm.getProjectState());
		project.setIsEvaluate(projectMasterInfoForm.getIsEvaluate());
		project.setLang(projectMasterInfoForm.getLang());
		project.setProjectSubName(projectMasterInfoForm.getProjectSubName());
		project.setIsEduConnected(projectMasterInfoForm.getIsEduConnected());
		
		/* By-Pass 추가 */
		if("Y".equals(project.getFunc())){
			project.setAttach(project.getFunc());
			project.setParentProjectCode(project.getBp_projId());
		} else if("Y".equals(project.getAttach())) {
			project.setFunc(project.getAttach());
			project.setBp_projId(project.getParentProjectCode());
		} else { System.out.println("Not By-Pass 1");}
		
		/* By-Pass 추가 */
		if("Y".equals(project.getFunc())){
			projectMasterInfoForm.setProjectCode(project.getBp_projId());
			if (isRegisteredProject && !projectMasterInfoForm.getIsRefresh().equals("true")) {
				projectBp = this.getProjectMasterInfoManager().getProject(project.getProjectCode());
			} else {
				projectBp = this.getProjectMasterInfoManager().getErpProject(project.getProjectCode());
			}

			if (projectBp.getProjectDetailCode() == null || !projectBp.getProjectDetailCode().equals(projectMasterInfoForm.getProjectDetailCode())) {
				ProcessCategory processCategory = this.getProcessCategoryManager().getProcessCategory(projectMasterInfoForm.getProjectDetailCode());
				processTemplateDetailList = this.getProcessTemplateManager().getProcessTemplateDetail(processCategory.getProcessTemplateCode());
				projectBp.setProcessTypeCode(processCategory.getProcessTemplateCode());
			}
			projectBp.setProjectCode(project.getBp_projId());
			projectBp.setProjectName("B_"+project.getProjectName());
			projectBp.setProjectTypeCode("ND");
			projectBp.setEntNo(project.getBp_entNo());
			/*projectBp.setEntNo(projectMasterInfoForm.getEntNo());*/
			projectBp.setProjectMemberList(ProjectMember.valueOf(projectMasterInfoForm));
			projectBp.setProjectCsrInfoList(ProjectCsrInfo.valueOf(projectMasterInfoForm));
			projectBp.setProjectDetailCode(projectMasterInfoForm.getProjectDetailCode());
			projectBp.setBusinessFunctionType(projectMasterInfoForm.getBusinessFunctionType());
			projectBp.setIsVoc(projectMasterInfoForm.getIsVoc());
			projectBp.setCustomerWorkPEmail(projectMasterInfoForm.getCustomerWorkPEmail());
			projectBp.setCustomerWorkPName(projectMasterInfoForm.getCustomerWorkPName());
			projectBp.setCustomerWorkPPhone(projectMasterInfoForm.getCustomerWorkPPhone());
			projectBp.setCustomerContPEmail(projectMasterInfoForm.getCustomerContPEmail());
			projectBp.setCustomerContPName(projectMasterInfoForm.getCustomerContPName());
			projectBp.setCustomerContPPhone(projectMasterInfoForm.getCustomerContPPhone());
			projectBp.setProjectState(projectMasterInfoForm.getProjectState());
			projectBp.setIsEvaluate(projectMasterInfoForm.getIsEvaluate());
			projectBp.setLang(projectMasterInfoForm.getLang());
			projectBp.setProjectSubName(projectMasterInfoForm.getProjectSubName());
			projectBp.setIsEduConnected(projectMasterInfoForm.getIsEduConnected());	
		} else { System.out.println("By-Pass Error 1"); }

		try {
			if (isRegisteredProject && !projectMasterInfoForm.getIsRefresh().equals("true")) {
				project.setModifyDate(new Date());
				project.setModifySsn(SessionUtils.getUsername(request));
				this.getProjectMasterInfoManager().updateProject(project);// 프로젝트 기본정보 수정
			} else {
				project.setCreateDate(new Date());
				project.setCreaterSsn(SessionUtils.getUsername(request));
				project.setModifyDate(new Date());
				project.setModifySsn(SessionUtils.getUsername(request));
				this.getProjectMasterInfoManager().createProject(project);// 프로젝트 기본정보 등록
			}
			
			/* By-Pass 추가 */
			if("Y".equals(project.getFunc())){
				if (isRegisteredProject && !projectMasterInfoForm.getIsRefresh().equals("true")) {
					projectBp.setModifyDate(new Date());
					projectBp.setModifySsn(SessionUtils.getUsername(request));
					this.getProjectMasterInfoManager().updateProject(projectBp);// 프로젝트 기본정보 수정
				} else {
					projectBp.setCreateDate(new Date());
					projectBp.setCreaterSsn(SessionUtils.getUsername(request));
					projectBp.setModifyDate(new Date());
					projectBp.setModifySsn(SessionUtils.getUsername(request));
					this.getProjectMasterInfoManager().createProject(projectBp);// 프로젝트 기본정보 등록_ByPass
				}
			}else if("Y".equals(project.getAttach())){
				if (isRegisteredProject && !projectMasterInfoForm.getIsRefresh().equals("true")) {
					projectBp.setModifyDate(new Date());
					projectBp.setModifySsn(SessionUtils.getUsername(request));
					this.getProjectMasterInfoManager().updateProject(projectBp);// 프로젝트 기본정보 수정
				} else {
					projectBp.setCreateDate(new Date());
					projectBp.setCreaterSsn(SessionUtils.getUsername(request));
					projectBp.setModifyDate(new Date());
					projectBp.setModifySsn(SessionUtils.getUsername(request));
					this.getProjectMasterInfoManager().createProject(projectBp);// 프로젝트 기본정보 등록_ByPass
				}
			}else {
				System.out.println("By-Pass Error 2");
			}

			if (processTemplateDetailList != null && processTemplateDetailList.size() > 0) {
				this.getProjectProgressManager().deleteProjectProgress(project.getProjectCode());
				// 프로세스 변경 시 지도 일정 , 지도일지 삭제
				this.getProjectReportInfoManager().deleteProjectReportInfo(project.getProjectCode());
				ProjectProgress projectProgress = null;
				int i = 0;
				for (ProcessTemplateDetail processTemplateDetail : processTemplateDetailList) {
					projectProgress = new ProjectProgress();
					projectProgress.setProjectCode(project.getProjectCode());
					projectProgress.setWorkName(processTemplateDetail.getActivityName());
					projectProgress.setWorkSeq(processTemplateDetail.getWorkSeq());
					projectProgress.setLevel(processTemplateDetail.getLevel());
					projectProgress.setParantWorkSeq(processTemplateDetail.getParentWorkSeq());
					projectProgress.setWorkType(processTemplateDetail.getWorkType());
					projectProgress.setOrderSeq(i);
					projectProgress.setContentId("TASK" + IdGenerator.generate(""));
					this.getProjectProgressManager().storeProjectProgressExe(projectProgress);
					i++;
				}
			}

			if (projectMasterInfoForm.getOrgCodes().startsWith(",")) {
				projectMasterInfoForm.setOrgCodes(projectMasterInfoForm.getOrgCodes().substring(1).trim());
				projectMasterInfoForm.setOrgNames(projectMasterInfoForm.getOrgNames().substring(1).trim());
			}
			String[] orgCodes = projectMasterInfoForm.getOrgCodes().split(",");
			String[] orgNames = projectMasterInfoForm.getOrgNames().split(",");
			if (orgCodes != null && orgNames != null && orgCodes.length == orgNames.length) {
				this.getProjectOrgdbManager().setProjectOrgdb(projectMasterInfoForm.getProjectCode(), orgCodes, orgNames);
			}

			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void updateProjectMasterInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		request.setAttribute("projectCode", projectCode);
	}

	public void deleteProjectMasterInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String func = ServletRequestUtils.getRequiredStringParameter(request, "func");
		String projectCodeBp = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeBp");
		
		System.out.println(projectCode + " : delete projectcode");
		System.out.println(func + " : delete func");
		System.out.println(projectCodeBp + " : delete projectCodeBp");
		
		try {
			this.getProjectMasterInfoManager().deleteProject(projectCode);
			
			/* By-Pass 추가 */
			if("Y".equals(func)){
				this.getProjectMasterInfoManager().deleteProject(projectCodeBp);
			}
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public void getProjectName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		Project project = null;
		if (this.getProjectMasterInfoManager().isRegisteredProject(projectCode)) {
			project = this.getProjectMasterInfoManager().getProject(projectCode);
		} else {
			project = this.getProjectMasterInfoManager().getErpProject(projectCode);
		}
		map.put("projectName", project.getProjectName());
		AjaxUtil.successWrite(response, map);
	}

	public ActionForward getProjectCustomerInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String isAdmin = ServletRequestUtils.getStringParameter(request, "isAdmin", "");

		ProjectSimpleInfo project = this.getProjectMasterInfoManager().getProjectSimpleInfo(projectCode);
		List<ProjectCustomerInfo> projectCustomerInfoList = this.getProjectMasterInfoManager().getProjectCustomerInfo(projectCode);

		request.setAttribute("projectCode", projectCode);
		request.setAttribute("project", project);
		request.setAttribute("projectCustomerInfoList", projectCustomerInfoList);
		request.setAttribute("isAdmin", isAdmin);

		return mapping.findForward("projectCustomerInfoList");
	}

	public ActionForward deleteProjectCustomerInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		int[] seqs = ServletRequestUtils.getRequiredIntParameters(request, "seq");

		this.getProjectMasterInfoManager().deleteProjectCustomerInfo(projectCode, seqs);

		return this.getProjectCustomerInfo(mapping, form, request, response);
	}

	public ActionForward createProjectCustomerInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		String[] customerInfoIdxs = ServletRequestUtils.getRequiredStringParameter(request, "customerInfoIdx").split(";");

		List<ProjectCustomerInfo> projectCustomerInfos = new ArrayList<ProjectCustomerInfo>();
		for (String customerInfoIdx : customerInfoIdxs) {
			projectCustomerInfos.add(new ProjectCustomerInfo(projectCode, customerInfoIdx, SessionUtils.getUsername(request)));
		}
		this.getProjectMasterInfoManager().createProjectCustomerInfo(projectCode, projectCustomerInfos);

		return this.getProjectCustomerInfo(mapping, form, request, response);
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public ProcessCategoryManager getProcessCategoryManager() {
		return processCategoryManager;
	}

	public void setProcessCategoryManager(ProcessCategoryManager processCategoryManager) {
		this.processCategoryManager = processCategoryManager;
	}

	public ProjectProgressManager getProjectProgressManager() {
		return projectProgressManager;
	}

	public void setProjectProgressManager(ProjectProgressManager projectProgressManager) {
		this.projectProgressManager = projectProgressManager;
	}

	public ProcessTemplateManager getProcessTemplateManager() {
		return processTemplateManager;
	}

	public void setProcessTemplateManager(ProcessTemplateManager processTemplateManager) {
		this.processTemplateManager = processTemplateManager;
	}

	public ProjectOrgdbManager getProjectOrgdbManager() {
		return projectOrgdbManager;
	}

	public void setProjectOrgdbManager(ProjectOrgdbManager projectOrgdbManager) {
		this.projectOrgdbManager = projectOrgdbManager;
	}

	public ProjectReportInfoManager getProjectReportInfoManager() {
		return projectReportInfoManager;
	}

	public void setProjectReportInfoManager(ProjectReportInfoManager projectReportInfoManager) {
		this.projectReportInfoManager = projectReportInfoManager;
	}

	public SanctionDocManager getSanctionDocManager() {
		return sanctionDocManager;
	}

	public void setSanctionDocManager(SanctionDocManager sanctionDocManager) {
		this.sanctionDocManager = sanctionDocManager;
	}

	public CommonCodeManager getCommonCodeManager() {
		return commonCodeManager;
	}

	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public HashTagManager getHashTagManager() {
		return hashTagManager;
	}

	public void setHashTagManager(HashTagManager hashTagManager) {
		this.hashTagManager = hashTagManager;
	}
	
	

}
