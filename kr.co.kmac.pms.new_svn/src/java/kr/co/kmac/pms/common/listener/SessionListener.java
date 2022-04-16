/*
 * $Id: SessionListener.java,v 1.2 2010/12/02 16:59:07 cvs1 Exp $
 * created by    : �ȼ�ȣ
 * creation-date : 2006. 6. 20.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.listener;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ���� ������ ����. ���� �����ʴ� Servlet 2.3���� �����Ѵ�.
 * �� Ŭ������ ���� ������ ����ϰ� ������ ī��Ʈ �ϴ� ������ �����ش�.
 * @author ����ȣ
 * @version $Id: SessionListener.java,v 1.2 2010/12/02 16:59:07 cvs1 Exp $
 */ 
public class SessionListener implements HttpSessionListener {
	private static int sessionCount;
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		sessionCount++;
		//System.out.println("==================================================================");
		//System.out.println("\nSession Created. sessionCount=" + sessionCount + "\n");
		//System.out.println(getSessionInfo(event.getSession()));
		//System.out.println("==================================================================");
	}
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		sessionCount--;
		//System.out.println("==================================================================");
		//System.out.println("\nSession Destroyed. sessionCount=" + sessionCount + "\n");
		//System.out.println(getSessionInfo(event.getSession()));
		//System.out.println("==================================================================");
	}

	@SuppressWarnings("unchecked")
	private String getSessionInfo(HttpSession session) {
		StringBuffer sb = new StringBuffer();
		sb.append("\nsession id : " + session.getId() + "\n");
		sb.append("created time : " + new Date(session.getCreationTime()) + "\n");
		sb.append("last accessed time : " + new Date(session.getLastAccessedTime()) + "\n");
		sb.append("maximum inactive interval : " + session.getMaxInactiveInterval() + "\n");
		sb.append("attributes : \n");
		for (Enumeration<String> names = session.getAttributeNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			sb.append("\tname=" + name);
			sb.append(" value=" + session.getAttribute(name) + "\n");
		}
		return sb.toString();
	}
}
