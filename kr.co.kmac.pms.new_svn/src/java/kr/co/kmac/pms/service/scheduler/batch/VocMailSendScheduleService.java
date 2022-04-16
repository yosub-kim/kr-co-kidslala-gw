/*
 * $Id: VocMailSendScheduleService.java,v 1.1 2011/06/03 09:45:22 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 8.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.batch;

import kr.co.kmac.pms.service.mailsender.manager.PmsInfoMailSender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: VocMailSendScheduleService.java,v 1.1 2011/06/03 09:45:22 cvs Exp $
 */
public class VocMailSendScheduleService {
	private static final Log log = LogFactory.getLog(VocMailSendScheduleService.class);
	private PmsInfoMailSender pmsInfoMailSender;

	public void vocMailSend() throws Exception {
		pmsInfoMailSender.sendProjectVOCMail();
	}

	/**
	 * @return the pmsInfoMailSender
	 */
	public PmsInfoMailSender getPmsInfoMailSender() {
		return pmsInfoMailSender;
	}

	/**
	 * @param pmsInfoMailSender the pmsInfoMailSender to set
	 */
	public void setPmsInfoMailSender(PmsInfoMailSender pmsInfoMailSender) {
		this.pmsInfoMailSender = pmsInfoMailSender;
	}

}
