/*
 * $Id: ExpertPoolSpecialFieldAction.java,v 1.5 2016/01/27 08:25:18 cvs Exp $ created
 * by : ¾È¼ºÈ£ creation-date : 2006. 7. 6.
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.org.data.Group;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="expertPoolSpecialFieldAction" path="/action/ExpertPoolSpecialFieldAction" parameter="mode" scope="request"
 * @struts.action-forward name="expertPoolSpecialField" path="/expertPool/expertPoolSpecialField.jsp" redirect="false"
 * @struts.action-forward name="expertPoolSpecialFieldPopUp" path="/expertPool/expertPoolSpecialFieldPopUp.jsp" redirect="false"
 */
public class ExpertPoolSpecialFieldAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ExpertPoolSpecialFieldAction.class);
	private ExpertPoolManager expertPoolManager;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public ActionForward selectSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Group> groupList = this.getExpertPoolManager().getExpertPoolFunctionLIst();
		request.setAttribute("groupList", groupList);

		return mapping.findForward("expertPoolSpecialFieldPopUp");
	}

	public ActionForward loadSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ssn = ServletRequestUtils.getRequiredStringParameter(request, "ssn");

		List<ExpertPoolSpecialField> list = this.getExpertPoolManager().retrieveExpertPoolSpecialField(ssn);
		//List<Group> groupList = this.getExpertPoolManager().getDeptLIst();
		List<Group> groupList = this.getExpertPoolManager().getExpertPoolFunctionLIst();

		request.setAttribute("ssn", ssn);
		request.setAttribute("groupList", groupList);
		request.setAttribute("expertSecialFieldList", list);

		return mapping.findForward("expertPoolSpecialField");
	}

	public void searchSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String deptId = request.getParameter("deptId");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<ExpertPoolSpecialField> list = this.getExpertPoolManager().getExpertPoolSpecialField(deptId);
			map.put("specialField", list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void retrieveSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ssn = request.getParameter("ssn");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<ExpertPoolSpecialField> list = this.getExpertPoolManager().retrieveExpertPoolSpecialField(ssn);
			map.put("expertPoolSpecialField", list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void removeSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ssn = request.getParameter("ssn");
		String specialId = ServletRequestUtils.getRequiredStringParameter(request, "specialId");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			this.getExpertPoolManager().removeExpertPoolSpecialField(ssn, specialId);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void storeSpecialField(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String ssn = request.getParameter("ssn");
		String[] specialIds = ServletRequestUtils.getRequiredStringParameters(request, "specialId");
		String[] specialNames = ServletRequestUtils.getRequiredStringParameters(request, "specialName");

		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<ExpertPoolSpecialField> list = new ArrayList<ExpertPoolSpecialField>();
			for (int i = 0; i < specialIds.length; i++) {
				list.add(new ExpertPoolSpecialField(ssn, specialIds[i], specialNames[i]));
			}
			this.getExpertPoolManager().createExpertPoolSpecialField(ssn, list);
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

}
