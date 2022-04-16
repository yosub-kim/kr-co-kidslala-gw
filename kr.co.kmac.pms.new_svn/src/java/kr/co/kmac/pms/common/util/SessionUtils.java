/*
 * $Id: SessionUtils.java,v 1.2 2010/12/02 16:59:19 cvs1 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 30.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import kr.co.kmac.pms.common.org.data.User;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.HttpSessionContextIntegrationFilter;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;

/**
 * 세션 관련 유틸리티 클래스
 * 
 * @version $Id: SessionUtils.java,v 1.2 2010/12/02 16:59:19 cvs1 Exp $
 */
public abstract class SessionUtils {

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static String getUsername(HttpServletRequest request) {
		SecurityContext context = (SecurityContext) request.getSession().getAttribute(HttpSessionContextIntegrationFilter.ACEGI_SECURITY_CONTEXT_KEY);
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				return auth.getName();
			}
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}

	public static User getUserObjext() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (User) obj;
	}

	public static SecurityContext getUserContext() {
		SecurityContext obj = SecurityContextHolder.getContext();
		return obj;
	}

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
