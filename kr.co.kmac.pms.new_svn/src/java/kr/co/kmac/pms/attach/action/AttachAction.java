/*
 * $Id: AttachAction.java,v 1.4 2016/09/09 06:26:38 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.attach.manager.AttachTemplateManager;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.project.master.manager.ProjectMasterInfoManager;

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
 * TODO Provide description for "AttachAction"
 * @author halberd
 * @version $Id: AttachAction.java,v 1.4 2016/09/09 06:26:38 cvs Exp $
 */
/**
 * @struts.action name="attachAction" path="/action/AttachAction" parameter="mode" scope="request"
 * @struts.action-forward name="projectAttachList" path="/project/attach/projectAttachList.jsp" redirect="false"
 */
public class AttachAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(AttachAction.class);
	private AttachManager attachManager;
	private AttachTemplateManager attachTemplateManager;
	private ProjectMasterInfoManager projectMasterInfoManager;
	private ExpertPoolManager expertPoolManager;
	/**
	 * @return Returns the attachManager.
	 */
	public AttachManager getAttachManager() {
		return attachManager;
	}
	
	public AttachTemplateManager getAttachTemplateManager() {
		return attachTemplateManager;
	}

	public void setAttachTemplateManager(AttachTemplateManager attachTemplateManager) {
		this.attachTemplateManager = attachTemplateManager;
	}
	
	/**
	 * @return the projectMasterInfoManager
	 */
	public ProjectMasterInfoManager getProjectMasterInfoManager() {
		return projectMasterInfoManager;
	}

	/**
	 * @param projectMasterInfoManager the projectMasterInfoManager to set
	 */
	public void setProjectMasterInfoManager(ProjectMasterInfoManager projectMasterInfoManager) {
		this.projectMasterInfoManager = projectMasterInfoManager;
	}

	/**
	 * @param attachManager The attachManager to set.
	 */
	public void setAttachManager(AttachManager attachManager) {
		this.attachManager = attachManager;
	}

	public ActionForward selectList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttachForm attachForm = (AttachForm) form;
		String pageSize = ServletRequestUtils.getStringParameter(request, "pageSize", "10");
		String pageNo = ServletRequestUtils.getStringParameter(request, "pageNo", "1");
		ExpertPool expertPool = this.getExpertPoolManager().retrieve(SessionUtils.getUsername(request));
		String jobclass = expertPool.getJobClass();
		
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		try {
			if("J".equals(jobclass) || "N".equals(jobclass))
				request.setAttribute("list", getAttachManager().selectList_exc(wc, attachForm, pageNo, pageSize));
			else
				request.setAttribute("list", getAttachManager().selectList(wc, attachForm, pageNo, pageSize));
			
			List attachOutputTypeList = new ArrayList();
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BT3").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, "BTZ").getList());
			attachOutputTypeList.addAll(this.getAttachTemplateManager().selectOutputTypeCodeList(wc, null, projectMasterInfoManager.getProjectBusinessTypeCode(attachForm.getProjectCode())).getList());
			request.setAttribute("attachOutputTypeList", attachOutputTypeList);
			if (attachForm.getAttachFileName() != null && attachForm.getAttachFileName()[0] != null && !attachForm.getAttachFileName()[0].equals(""))
				request.setAttribute("fileName", attachForm.getAttachFileName()[0]);
			if (attachForm.getAttachOutputType() != null && attachForm.getAttachOutputType()[0] != null && !attachForm.getAttachOutputType()[0].equals(""))
				request.setAttribute("outputType", attachForm.getAttachOutputType()[0]);

		} catch (AttachException e) {
			logger.error(e.getMessage(), e);

		}
		return mapping.findForward("projectAttachList");
	}

	public ActionForward seleceListForTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AttachForm attachForm = (AttachForm) form;
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		try {
			request.setAttribute("result", getAttachManager().selectListForTask(wc, attachForm.getTaskId(), null, null));
		} catch (AttachException e) {
			logger.error(e.getMessage(), e);

		}
		return mapping.findForward("attachSelectListForTaskResult");
	}

	public ActionForward selectListSplit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String pageSize = request.getParameter("pageSize");
		// String pg = request.getParameter("pg");
		String projectCode = request.getParameter("projectCode");

		AttachForm resultCustomer = new AttachForm();
		AttachForm resultPreport = new AttachForm();
		AttachForm resultAttach = new AttachForm();

		resultCustomer.setProjectCode(projectCode);
		resultPreport.setProjectCode(projectCode);
		resultAttach.setProjectCode(projectCode);

		resultCustomer.setAttachOutputBusType(new String[] { "BT1" });
		resultPreport.setAttachOutputBusType(new String[] { "BTZ" });
		resultAttach.setAttachOutputBusType(new String[] { "OTHER" });

		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());

		try {
			request.setAttribute("resultCustomer", getAttachManager().selectList(wc, resultCustomer, null, null));
			request.setAttribute("resultPreport", getAttachManager().selectList(wc, resultPreport, null, null));
			request.setAttribute("resultAttach", getAttachManager().selectList(wc, resultAttach, null, null));

		} catch (AttachException e) {
			logger.error(e.getMessage(), e);

		}
		return mapping.findForward("attachSelectListResultSplit");
	}
	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}
	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

}
