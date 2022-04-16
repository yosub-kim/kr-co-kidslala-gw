/*
 * $Id: AdminTag.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : 안성호
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.org;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;
import kr.co.kmac.pms.common.util.SessionUtils;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AdminTag extends TagSupport {
	private static final long serialVersionUID = -7652820146661824773L;
	private String html = null;

	private CommonCodeManager commonCodeManager;

	/**
	 * @return Returns the codeManager.
	 */
	public CommonCodeManager getCommonCodeManager() {
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		if (commonCodeManager == null) {
			if (wc != null) {
				commonCodeManager = (CommonCodeManager) wc.getBean("commonCodeManager");
			}
		}
		return commonCodeManager;
	}

	/**
	 * @param codeManager The codeManager to set.
	 */
	public void setCommonCodeManager(CommonCodeManager commonCodeManager) {
		this.commonCodeManager = commonCodeManager;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		try {
			this.html = (String) ExpressionEvaluatorManager.evaluate("html", html, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		try {
			String ssn = SessionUtils.getUserObjext().getId();
			JspWriter out = pageContext.getOut();
			List<Code> list = getCommonCodeManager().getCodeEntitySimpleList("SYSTEM_ADMIN");
			if (list != null) {
				for (Code code : list) {
					if (code.getKey().equals(ssn)) {
						out.print(html);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // TODO LOG 처리
			// throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
