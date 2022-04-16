/*
 * $Id: CountTag.java,v 1.1 2009/09/19 11:15:45 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.count;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.worklist.manager.WorklistManager;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.manager.ExpertPoolManager;
import kr.co.kmac.pms.schedule.manager.ScheduleManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CountTag extends TagSupport {
	// private static final long serialVersionUID = -4894922453766868858L;
	//
	// private String ssn = null;
	//
	// private WorklistManager worklistManager;
	// private ScheduleManager scheduleManager;
	//	
	//
	// /**
	// * @return Returns the codeManager.
	// */
	// public ExpertPoolManager getExpertPoolManager() {
	// WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
	// if (expertPoolManager == null) {
	// if (wc != null) {
	// expertPoolManager = (ExpertPoolManager) wc.getBean("expertPoolManager");
	// }
	// }
	// return expertPoolManager;
	// }
	//
	// /**
	// * @param codeManager The codeManager to set.
	// */
	// public void setExpertPoolManager(ExpertPoolManager expertPoolManager) {
	// this.expertPoolManager = expertPoolManager;
	// }
	//
	// /**
	// * @return Returns the ssn.
	// */
	// public String getSsn() {
	// return this.ssn;
	// }
	//
	// /**
	// * @param ssn The ssn to set.
	// */
	// public void setSsn(String ssn) {
	// try {
	// this.ssn = (String) ExpressionEvaluatorManager.evaluate("ssn", ssn, java.lang.String.class, this, pageContext);
	// if (this.ssn != null) {
	// this.ssn = this.ssn.trim();
	// }
	// } catch (JspException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * doStartTag is called by the JSP container when the tag is encountered
	// */
	// public int doStartTag() throws JspException {
	// if (ssn == null || ssn.equals(""))
	// return SKIP_BODY;
	// try {
	// ExpertPool expertPool = this.getExpertPoolManager().retrieve(ssn);
	// JspWriter out = pageContext.getOut();
	// out.print(expertPool.getName());
	// } catch (Exception e) {
	// throw new JspException(e);
	// }
	//
	// return SKIP_BODY;
	// }

}
