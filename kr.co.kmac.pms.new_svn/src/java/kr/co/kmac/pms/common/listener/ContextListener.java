/*
 * $Id: ContextListener.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 * created by    : 최인호
 * creation-date : 2005. 11. 24.
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 컨텍스트 리스너 샘플
 * @author 최인호
 * @version $Id: ContextListener.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 */
public class ContextListener implements ServletContextListener {
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("\nContext destroyed.\n");
		getContextInfo(event.getServletContext());
		//System.out.println(contextInfo);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("\nContext initialized.\n");
		getContextInfo(event.getServletContext());
		//System.out.println(contextInfo);
	}

	/**
	 * 
	 * TODO 메소드 설명
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getContextInfo(ServletContext context) {
		StringBuffer sb = new StringBuffer();
		sb.append("\nname : " + context.getServletContextName() + "\n");
		sb.append("server info : " + context.getServerInfo() + "\n");
		sb.append("major version : " + context.getMajorVersion() + "\n");
		sb.append("minor version : " + context.getMinorVersion() + "\n");
		sb.append("init parameters :\n");
		for (Enumeration<String> names = context.getInitParameterNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			sb.append("\tname=" + name);
			sb.append(" value=" + context.getInitParameter(name) + "\n");
		}
		sb.append("attributes :\n");
		for (Enumeration<String> names = context.getAttributeNames(); names.hasMoreElements();) {
			String name = (String) names.nextElement();
			sb.append("\tname=" + name);
			sb.append(" value=" + context.getAttribute(name) + "\n");
		}

		return sb.toString();
	}

}
