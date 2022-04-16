/*
 * $Id: ExpertPoolCheckAction.java,v 1.3 2017/11/24 04:38:08 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 7. 6.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.form.ExpertPoolForm;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="expertPoolCheckAction" path="/action/ExpertPoolCheckAction" parameter="mode" scope="request"
 */
public class ExpertPoolCheckAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(ExpertPoolCheckAction.class);
	private ExpertPoolManager expertPoolManager;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	/**
	 * 로그인 시 userId를 받으면 주민번호를 리턴
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void retrieveUserIdofSsn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (this.getExpertPoolManager().isExistUserId(userId)) {
				map.put("userSsn", this.getExpertPoolManager().retrieveUserId(userId).getSsn());
			} else {
				map.put("userSsn", "");
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * 전문가 등록 확인(전문가 등록시 같은 주민번호가 있는지 확인)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void checkSsn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		int result = this.getExpertPoolManager().selectExpertPoolUidCheck(request.getParameter("ssn")).length();

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (result > 0) {
				map.put("returnValue", "false");
			} else {
				map.put("returnValue", "true");
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	/**
	 * UserId 등록 확인(UserId 등록시 같은 UserId가 있는지 확인) TODO 메소드 설명
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void checkUserId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId = request.getParameter("userId");

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (this.getExpertPoolManager().checkUserId(userId) == 0) {
				map.put("returnValue", "true");
			} else {
				map.put("returnValue", "false");
			}
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getEmailPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(request.getParameter("ssn"));
			map.put("password", expertPool.getPassword());
			map.put("id", expertPool.getUserId());
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}

	public void isPasswordValid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map.put("needToPasswordUpdate", (String)request.getSession().getAttribute("needToPasswordUpdate"));
			AjaxUtil.successWrite(response, map);
		} catch (Exception e) {
			logger.info(e);
			AjaxUtil.failWrite(response, map);
		}
	}
}
