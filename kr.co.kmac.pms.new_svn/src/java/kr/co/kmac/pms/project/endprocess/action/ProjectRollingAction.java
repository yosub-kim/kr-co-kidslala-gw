/*
 * $Id: ProjectRollingAction.java,v 1.13 2018/06/29 04:42:52 cvs Exp $	
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.worklist.data.Work;
import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.endprocess.form.ProjectRollingForm;
import kr.co.kmac.pms.project.endprocess.manager.ProjectRollingManager;
import kr.co.kmac.pms.project.exception.ProjectException;
import kr.co.kmac.pms.project.master.data.ProjectSimpleInfo;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;
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
 * 프로젝트 롤링 처리
 * @version $Id: ProjectRollingAction.java,v 1.13 2018/06/29 04:42:52 cvs Exp $
 */
/**
 * @struts.action name="projectRollingForm" path="/action/ProjectRollingAction" parameter="mode" scope="request"
 * @struts.action-forward name="rollingE" path="/project/endProcess/rollingE.jsp" redirect="false"
 * @struts.action-forward name="rollingT" path="/project/endProcess/rollingT.jsp" redirect="false"
 * @struts.action-forward name="projectRollingMonitorList" path="/project/endProcess/projectRollingMonitorList.jsp" redirect="false"
 * @struts.action-forward name="projectRollingPersonalMonitorList" path="/project/endProcess/projectRollingPersonalMonitorList.jsp" redirect="false"
 */
public class ProjectRollingAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ProjectRollingAction.class);	
	private ProjectRollingManager projectRollingManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private WorklistManager worklistManager;
	private PmsInfoMailSender pmsInfoMailSender;
	private ExpertPoolManager expertPoolManager;

	/**
	 * 프로젝트 롤링 등록(컨설팅)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRollingC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");

		try {
			getProjectRollingManager().insertRollingC(projectCode);
			AjaxUtil.successWrite(response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response);
		}
	}

	/**
	 * 
	 * 프로젝트 롤링 등록(교육)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRollingE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectRollingForm projectRollingForm = (ProjectRollingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectRollingForm.setWriterSsn(writerSsn);
		projectRollingForm.setWriteDate(writeDate);

		try {
			getProjectRollingManager().insertRollingE(projectRollingForm);

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 
	 * 프로젝트 롤링 등록(연수)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void insertRollingT(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ProjectRollingForm projectRollingForm = (ProjectRollingForm) form;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String writerSsn = SessionUtils.getUsername(request);
		String writeDate = DateTime.getDateString();

		projectRollingForm.setWriterSsn(writerSsn);
		projectRollingForm.setWriteDate(writeDate);

		try {
			getProjectRollingManager().insertRollingT(projectRollingForm);

			AjaxUtil.successWrite(response, map);
		} catch (ProjectException e) {
			logger.error(e.getMessage(), e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 
	 * 평가화면(교육만족도)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadRollingFormE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";
		try {
			if (!workId.equals("")) {
				Work work = this.getWorklistManager().getWork(workId);
				projectCode = work.getRefWorkId1();
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

			info.setFilters(filters);

			ValueList valueList = vlh.getValueList("getValueCustomerSatisfactionReportDetail", info);
			request.setAttribute("result", valueList);
			ValueList valueList2 = vlh.getValueList("getValueCustomerSatisfactionReportDetail2", info);
			request.setAttribute("result2", valueList2);
			request.setAttribute("taskId", workId);
			request.setAttribute("projectCode", projectCode);
			request.setAttribute("project", getProjectMasterInfoManager().getProject(projectCode));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return mapping.findForward("rollingE");
	}

	/**
	 * 
	 * 평가화면(연수만족도)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadRollingFormT(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String workId = ServletRequestUtils.getStringParameter(request, "workId", "");
		String projectCode = "";

		if (!workId.equals("")) {
			Work work = this.getWorklistManager().getWork(workId);
			projectCode = work.getRefWorkId1();
		} else {
			projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
		}

		request.setAttribute("taskId", workId);
		request.setAttribute("project", this.getProjectMasterInfoManager().getProject(projectCode));

		return mapping.findForward("rollingT");
	}

	public void sendProjectCSMailAgain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectCode = ServletRequestUtils.getRequiredStringParameter(request, "projectCode");
				
		int customerSeq = ServletRequestUtils.getRequiredIntParameter(request, "customerSeq");
		
		try {
			
			if (this.getProjectMasterInfoManager().getProject(projectCode).getCoGRP().equals("G")) {
				this.getPmsInfoMailSender().sendKCSMAProjectCSMailAgain(projectCode, customerSeq);				
			} else {
				this.getPmsInfoMailSender().sendProjectCSMailAgain(projectCode, customerSeq);				
			}					
			AjaxUtil.successWrite(response);
			
		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}		
	}


	public ActionForward getProjectRollingMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "ALL");
		String runningDivCode = ServletRequestUtils.getStringParameter(request, "runningDivCode", "");
		String runningDeptCode = ServletRequestUtils.getStringParameter(request, "runningDeptCode", "");
		String year = ServletRequestUtils.getStringParameter(request, "year", DateTime.getYear());
		String month = ServletRequestUtils.getStringParameter(request, "month", DateTime.getMonth());
		String isFinish = ServletRequestUtils.getStringParameter(request, "isFinish", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");

		WebApplicationContext wc;
		try {
			wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			// filters.put(ValueListInfo.PAGING_PAGE, "1");
			// filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (!businessTypeCode.equals("ALL")) {
				if (businessTypeCode.startsWith("BT")) {
					filters.put("businessTypeCode", businessTypeCode);
				} else {
					filters.put("processTypeCode", businessTypeCode);
				}
				request.setAttribute("businessTypeCode", businessTypeCode);
			} else {
				filters.put("businessTypeAll", "");
				request.setAttribute("businessTypeCode", businessTypeCode);
			}
			if (!runningDivCode.equals("")) {
				filters.put("runningDivCode", runningDivCode);
				request.setAttribute("runningDivCode", runningDivCode);
			}
			if (!runningDeptCode.equals("")) {
				filters.put("runningDeptCode", runningDeptCode);
				request.setAttribute("runningDeptCode", runningDeptCode);
			}			
			if (!year.equals("") && !month.equals("")) {
				String sMonth = month;
				if (month.length() == 1)
					sMonth = "0" + month;
				filters.put("year", year);
				filters.put("month", sMonth);
				request.setAttribute("year", year);
				request.setAttribute("month", month);
			}
			if (!isFinish.equals("")) {
				if (isFinish.equals("YES")) {
					filters.put("isFinishYes", isFinish);
				} else {
					filters.put("isFinishNo", isFinish);
				}
				request.setAttribute("isFinish", isFinish);
			}
			if (!projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
				request.setAttribute("projectName", projectName);
			}

			info.setFilters(filters);

			ValueList result = vlh.getValueList("getProjectRollingMonitor", info);
			request.setAttribute("projectRollingMonitorList", result);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectRollingMonitorList");
	}

	public ActionForward getProjectRollingPersonalMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		String businessTypeCode = ServletRequestUtils.getStringParameter(request, "businessTypeCode", "ALL");
		String year = ServletRequestUtils.getStringParameter(request, "year", "");
		String month = ServletRequestUtils.getStringParameter(request, "month", "");
		String isFinish = ServletRequestUtils.getStringParameter(request, "isFinish", "");
		String projectName = ServletRequestUtils.getStringParameter(request, "projectName", "");
 
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));

		try {
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
			ValueListHandler vlh = (ValueListHandler) wc.getBean("projectValueList", ValueListHandler.class);

			ValueListInfo info = new ValueListInfo();
			Map<String, String> filters = new HashMap<String, String>();
			// filters.put(ValueListInfo.PAGING_PAGE, "1");
			// filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(Integer.MAX_VALUE));
			filters.put(ValueListInfo.PAGING_PAGE, pageNo);
			filters.put(ValueListInfo.PAGING_NUMBER_PER, pageSize);
			if (!businessTypeCode.equals("ALL")) {
				if (businessTypeCode.startsWith("BT")) {
					filters.put("businessTypeCode", businessTypeCode);
				} else {
					filters.put("processTypeCode", businessTypeCode);
				}
				request.setAttribute("businessTypeCode", businessTypeCode);
			} else {
				filters.put("businessTypeAll", "");
				request.setAttribute("businessTypeCode", businessTypeCode);
			}

			if (expertPool.getCompanyPosition().equals("09CJ") 
					|| expertPool.getCompanyPosition().equals("08CF") ) {	// COO (본부장, 센터장)
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
				filters.put("runningDeptCode", expertPool.getDept());
			} else if (expertPool.getCompanyPosition().equals("06CB")) {	// CBO
				filters.put("AR", SessionUtils.getUsername(request));
				filters.put("runningDivCode", expertPool.getDept().substring(0, 3) + "0");
			} else if (expertPool.getCompanyPosition().equals("05CC")) {	// CCO
				filters.put("AR", SessionUtils.getUsername(request));
				if (year.equals("") && month.equals("")) {
					year = DateTime.getYear();
					month = DateTime.getMonth();
					String sMonth = month;
					if (month.length() == 1) {
						sMonth = "0" + month; 
					}
					filters.put("year", year);
					filters.put("month", sMonth);
					request.setAttribute("year", year);
					request.setAttribute("month", month);
				}
			} else {
				filters.put("PM", SessionUtils.getUsername(request));
				filters.put("ssn", SessionUtils.getUsername(request));
			}

			if (!year.equals("") && !month.equals("")) {
				String sMonth = month;
				if (month.length() == 1)
					sMonth = "0" + month;
				filters.put("year", year);
				filters.put("month", sMonth);
				request.setAttribute("year", year);
				request.setAttribute("month", month);
			}
		
			if (!isFinish.equals("")) {
				if (isFinish.equals("YES")) {
					filters.put("isFinishYes", isFinish);
				} else {
					filters.put("isFinishNo", isFinish);
				}
				request.setAttribute("isFinish", isFinish);
			}
			if (!projectName.equals("")) {
				filters.put("projectName", "%" + projectName + "%");
				request.setAttribute("projectName", projectName);
			}

			info.setFilters(filters);

			ValueList result = vlh.getValueList("getProjectRollingPersonalMonitor", info);
			request.setAttribute("projectRollingPersonalMonitorList", result);
			request.setAttribute("role", expertPool.getCompanyPosition());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("projectRollingPersonalMonitorList");
	}

	public WorklistManager getWorklistManager() {
		return worklistManager;
	}

	public void setWorklistManager(WorklistManager worklistManager) {
		this.worklistManager = worklistManager;
	}

	public ProjectRollingManager getProjectRollingManager() {
		return this.projectRollingManager;
	}

	public void setProjectRollingManager(ProjectRollingManager projectRollingManager) {
		this.projectRollingManager = projectRollingManager;
	}

	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}
}
