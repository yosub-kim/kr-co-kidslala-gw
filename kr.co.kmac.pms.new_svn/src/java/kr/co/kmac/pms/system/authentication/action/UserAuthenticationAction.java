/*
 * $Id: UserAuthenticationAction.java,v 1.7 2019/02/17 15:20:48 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 5. 1.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.authentication.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.kmac.pms.common.ajax.AjaxUtil;
import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.exception.ExpertPoolException;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.system.authentication.data.UserAuthentication;
import kr.co.kmac.pms.system.authentication.manager.UserAuthenticationManager;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.struts.DispatchActionSupport;

import whois.whoisSMS;

/**
 * @struts.action name="responseForm" path="/action/UserAuthenticationAction" parameter="mode" scope="request"
 * @struts.action name="responseForm" path="/noauthaction/UserAuthenticationAction" parameter="mode" scope="request"
 */
public class UserAuthenticationAction extends DispatchActionSupport {
	private static final Log logger = LogFactory.getLog(UserAuthenticationAction.class);
	private UserAuthenticationManager userAuthenticationManager;
	private ExpertPoolManager expertPoolManager;

	public ExpertPoolManager getExpertPoolManager() {
		return expertPoolManager;
	}

	public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
		this.expertPoolManager = expertPoolManager;
	}

	public UserAuthenticationManager getUserAuthenticationManager() {
		return userAuthenticationManager;
	}

	public void setUserAuthenticationManager(UserAuthenticationManager userAuthenticationManager) {
		this.userAuthenticationManager = userAuthenticationManager;
	}

	public void getAuthenticationToken(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userSsn = SessionUtils.getUsername(request);
			String phone1 = ServletRequestUtils.getRequiredStringParameter(request, "phone1");
			String phone2 = ServletRequestUtils.getRequiredStringParameter(request, "phone2");
			String phone3 = ServletRequestUtils.getRequiredStringParameter(request, "phone3");
			String device = ServletRequestUtils.getRequiredStringParameter(request, "device");
			ExpertPool expertPool = this.getExpertPoolManager().retrieve(userSsn);

			if (StringUtils.isNotEmpty(expertPool.getMobileNo())
					&& StringUtil.replace(expertPool.getMobileNo(), "-", "").equals(phone1 + phone2 + phone3)) {
				String authToken = RandomStringUtils.randomNumeric(6);

				//신규 토큰 생성
				this.getUserAuthenticationManager().insertUserAuthentication(new UserAuthentication(userSsn, authToken, device, true));

				Thread.sleep(1000);

				whoisSMS sms = new whoisSMS();
				sms.login("kmac4u", "kmac123");
				String sms_msg = "[KMAC]\n인증번호 [" + authToken + "]를 인증창에 입력해주세요.-" + device;
				//sms.setUtf8();
				sms.setParams(phone1 + phone2 + phone3, "02-3786-0642", new String(sms_msg.getBytes(), "8859_1"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "");
				sms.emmaSend();

				map.put("authToken", authToken);
				map.put("resultMsg", "1");
				AjaxUtil.successWrite(response, map);
			} else {
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);
			}

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}

	public void getAuthenticationTokenFromWeb(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");
			String phone1 = ServletRequestUtils.getRequiredStringParameter(request, "phone1");
			String phone2 = ServletRequestUtils.getRequiredStringParameter(request, "phone2");
			String phone3 = ServletRequestUtils.getRequiredStringParameter(request, "phone3");
			String authToken = "";
			ExpertPool expertPool = this.getExpertPoolManager().retrieveUserId(userId);
			
			if (StringUtils.isNotEmpty(expertPool.getMobileNo())
					&& StringUtil.replace(expertPool.getMobileNo(), "-", "").equals(phone1 + phone2 + phone3)) {
				authToken = RandomStringUtils.randomNumeric(5);
				this.getUserAuthenticationManager().insertUserAuthentication(new UserAuthentication(expertPool.getSsn(), authToken, true));
				
				Thread.sleep(1000);
				
				whoisSMS sms = new whoisSMS();
				sms.login("kmac4u", "kmac123");
				String sms_msg = "[KMAC]\n인증번호 [" + authToken + "]를 인증창에 입력해주세요.";
				//sms.setUtf8();
				sms.setParams(phone1 + phone2 + phone3, "02-3786-0642", new String(sms_msg.getBytes(), "8859_1"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "");
				sms.emmaSend();
				
				map.put("userId", userId);
				map.put("authToken", authToken);
				map.put("resultMsg", "1");
				AjaxUtil.successWrite(response, map);
			} else {
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);
			}
			
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}
	
	//Q&A게시판 등록 시 문자 전송
	public void getAuthenticationTokenFromWeb_qna(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");
			String phone1 = ServletRequestUtils.getRequiredStringParameter(request, "phone1");
			String phone2 = ServletRequestUtils.getRequiredStringParameter(request, "phone2");
			String phone3 = ServletRequestUtils.getRequiredStringParameter(request, "phone3");
			String authToken = "";
			ExpertPool expertPool = this.getExpertPoolManager().retrieveUserId(userId);
			
			if (StringUtils.isNotEmpty(expertPool.getMobileNo())
					&& StringUtil.replace(expertPool.getMobileNo(), "-", "").equals(phone1 + phone2 + phone3)) {
				authToken = RandomStringUtils.randomNumeric(5);
				this.getUserAuthenticationManager().insertUserAuthentication(new UserAuthentication(expertPool.getSsn(), authToken, true));
				
				Thread.sleep(1000);
				
				whoisSMS sms = new whoisSMS();
				sms.login("kmac4u", "kmac123");
				String sms_msg = "시스템 Q&A 게시판에 새 글이 등록 되었습니다.";
				//sms.setUtf8();
				sms.setParams(phone1 + phone2 + phone3, "02-3786-0642", new String(sms_msg.getBytes(), "8859_1"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(new Date()), "");
				sms.emmaSend();
				
				map.put("userId", userId);
				map.put("authToken", authToken);
				map.put("resultMsg", "1");
				AjaxUtil.successWrite(response, map);
			} else {
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);
			}
			
		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}

	public void authRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userSsn = SessionUtils.getUsername(request);
			String device = ServletRequestUtils.getRequiredStringParameter(request, "device");
			String authToken = ServletRequestUtils.getRequiredStringParameter(request, "authToken");
			UserAuthentication userAuthentication = this.getUserAuthenticationManager().getUserAuthentication(userSsn,device, true);

			if (userAuthentication.getAuthToken().equals(authToken)) {
				userAuthentication.setAuthValue(RandomStringUtils.randomNumeric(8));
				this.getUserAuthenticationManager().updateUserAuthentication(userAuthentication);

				// 인증성공
				// Cookie cookie = new Cookie(
				// 		device.equals("mobile") ? "m_kmacPms_auth" : "d_kmacPms_auth"
				// 			, userAuthentication.getAuthValue());
				// cookie.setMaxAge(60 * 60 * 24 * 90); // 유효시간 설정 : 60*60*24*90(초) = 90일
				// response.addCookie(cookie); // 쿠키전송

				map.put("authKey", userAuthentication.getAuthValue());
				map.put("resultMsg", "1");
				AjaxUtil.successWrite(response, map);
			} else {
				// 인증실패
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);
			}

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}


	public void authRequestFromPwFgt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");
			String device = ServletRequestUtils.getRequiredStringParameter(request, "device");
			String authToken = ServletRequestUtils.getRequiredStringParameter(request, "authToken");
			String newpwd = ServletRequestUtils.getRequiredStringParameter(request, "newpwd");
			
			ExpertPool expertPool = this.getExpertPoolManager().retrieveUserId(userId);
			String userSsn = expertPool.getSsn();
			
			UserAuthentication userAuthentication = this.getUserAuthenticationManager().getUserAuthentication(userSsn, "empty", true);
			if (userAuthentication.getAuthToken().equals(authToken)) {
				userAuthentication.setAuthValue(RandomStringUtils.randomNumeric(8));
				this.getUserAuthenticationManager().updateUserAuthentication(userAuthentication);

				this.getExpertPoolManager().accountReset(userSsn);
				this.getExpertPoolManager().passwordReset(userSsn, newpwd);

				map.put("authKey", userAuthentication.getAuthValue());
				map.put("resultMsg", "1");
				AjaxUtil.successWrite(response, map);
			} else {
				// 인증실패
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);
			}

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}

	public void authRequestFromPwExprd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			String userId = ServletRequestUtils.getRequiredStringParameter(request, "userId");
			String oldpwd = ServletRequestUtils.getRequiredStringParameter(request, "oldpwd");
			String newpwd = ServletRequestUtils.getRequiredStringParameter(request, "newpwd");
			
			ExpertPool expertPool = this.getExpertPoolManager().retrieveUserId(userId);
			String userSsn = expertPool.getSsn();
			
			if(expertPool.getPassword().equals(getExpertPoolManager().getEncPassword(oldpwd))){
				this.getExpertPoolManager().accountReset(userSsn);
				this.getExpertPoolManager().passwordReset(userSsn, newpwd);

				map.put("resultMsg", "1");
				map.put("encPwd", getExpertPoolManager().getEncPassword(newpwd));
				map.put("emailAddr", getExpertPoolManager().getEmail(userSsn));
				AjaxUtil.successWrite(response, map);				
			} else {
				// 인증실패
				map.put("resultMsg", "2");
				AjaxUtil.failWrite(response, map);				
			}

		} catch (ExpertPoolException e) {
			logger.info(e.getMessage(), e);
			map.put("resultMsg", "3");
			AjaxUtil.failWrite(response, map);
		}
	}
}
