/*
 * $Id: SmsCommonAction.java,v 1.1 2018/11/05 20:47:25 cvs Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 2. 24.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.mailsender.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSenderManager;
import kr.co.kmac.pms.service.mailsender.manager.PmsInfoSMSSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

/**
 * @struts.action name="smsCommonAction" path="/action/SmsCommonAction" parameter="mode" scope="request"
 */
public class SmsCommonAction extends DispatchActionSupport {

	private static final Log logger = LogFactory.getLog(SmsCommonAction.class);
	private ExpertPoolManager expertPoolManager;
	private PmsInfoSMSSender pmsInfoSMSSender;
	private PmsInfoMailSenderManager pmsInfoMailSenderManager;
	
	public void sendProjectDelaySmS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String projectCodeStr = ServletRequestUtils.getRequiredStringParameter(request, "projectCodeStr");
		String smsMessage = ServletRequestUtils.getRequiredStringParameter(request, "smsMessage");
		String callbackNo = ServletRequestUtils.getRequiredStringParameter(request, "callbackNo");
		
		try {
			String projectCode[] = projectCodeStr.split(";");
			if(projectCode != null && projectCode.length > 0){
				List<String> mobileNo = pmsInfoMailSenderManager.selectMobilePhoneNo(projectCode);
				for (String phoneNo : mobileNo) {
					getPmsInfoSMSSender().sendSMS(phoneNo, callbackNo, smsMessage);
				}
			} 			
			AjaxUtil.successWrite(response, map);

		} catch (Exception e) {
			AjaxUtil.failWrite(response);
			logger.error(e.getMessage(), e);
		}
	}

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public PmsInfoSMSSender getPmsInfoSMSSender() {
		return pmsInfoSMSSender;
	}

	public void setPmsInfoSMSSender(PmsInfoSMSSender pmsInfoSMSSender) {
		this.pmsInfoSMSSender = pmsInfoSMSSender;
	}

	public PmsInfoMailSenderManager getPmsInfoMailSenderManager() {
		return pmsInfoMailSenderManager;
	}

	public void setPmsInfoMailSenderManager(
			PmsInfoMailSenderManager pmsInfoMailSenderManager) {
		this.pmsInfoMailSenderManager = pmsInfoMailSenderManager;
	}

	
	
}
