/*
 * $Id: PmsInfoSMSSender.java,v 1.2 2018/11/13 01:32:48 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 10. 8.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.mailsender.manager;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.co.kmac.pms.sanction.general.action.GeneralSanctionAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
// JobDate: 2012-01-10


import whois.whoisSMS;

public class PmsInfoSMSSender {

	private static final Log logger = LogFactory.getLog(GeneralSanctionAction.class);

	public void sendSMS(String phoneNo, String callbackNo, String message) throws UnsupportedEncodingException {
		String sms_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date());
		String sms_msg = new String(message.getBytes(), "8859_1");
		String sms_type = sms_msg.length() > 80 ? "L" : null;

		whoisSMS sms = new whoisSMS();
		sms.login("kmac4u", "kmac123");
		sms.setParams(phoneNo, callbackNo, sms_msg, sms_date, sms_type);
		sms.emmaSend();
		
		int retCode = sms.getRetCode();
		String retMessage = sms.getRetMessage();
		int retLastPoint = sms.getLastPoint();
		
		logger.info(retCode + ":" + retMessage + ":" + retLastPoint);
	}
	
	
}