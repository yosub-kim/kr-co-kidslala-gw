/*
 * $Id: ExportMajorFieldTag.java,v 1.1 2009/09/19 11:15:42 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.expertpool;

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ExportMajorFieldTag extends TagSupport {
	private static final long serialVersionUID = -4894922453766868858L;

	private String code = null;

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

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		try {
			this.code = (String) ExpressionEvaluatorManager.evaluate("code", code, java.lang.String.class, this, pageContext);
			if (this.code != null) {
				this.code = this.code.trim();
			}
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		if (code == null || code.equals(""))
			return SKIP_BODY;
		try {
			StringBuffer buff = new StringBuffer();
			StringTokenizer st = new StringTokenizer(code, "¢Ì");
			while (st.hasMoreElements()) {
				CodeEntity codeEntity = this.getCommonCodeManager().getCodeEntity("EXPERT_CATEGORY_CODE", st.nextToken());
				if (buff.length() > 0) {
					buff.append(",");
				}
				buff.append(codeEntity.getData1());
			}

			JspWriter out = pageContext.getOut();
			out.print(buff.toString());
		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

}
