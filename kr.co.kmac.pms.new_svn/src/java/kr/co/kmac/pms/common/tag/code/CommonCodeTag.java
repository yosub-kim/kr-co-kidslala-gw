/*
 * $Id: CommonCodeTag.java,v 1.2 2009/11/01 09:31:41 cvs1 Exp $
 * created by    : 남상혁
 * creation-date : 2006. 1. 5.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.code;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.code.data.CodeEntity;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 기준코드를 JSP에서 가져오기 위한 태그라이브러리 중의 하나로, 기준 코드명을 가져올 수 있다.<br>
 * <br>
 * 예를 들어, 코드를 알고있으면 아래와 같이 코드의 이름을 가져올 수 있다. <br>
 * 
 * Prefix를 code, Tag를 code 라고 가정하면,<br>
 * 
 * <code:code code="A01" tableName="BUSINESS_TYPE_CODE"/> : 코드가 A01 인 기준정보의 코드명을 반환한다.<br>
 * tableName은 optional 이다. <br>
 * 
 * @author jiwoongLee
 * @version $Id: CommonCodeTag.java,v 1.2 2009/11/01 09:31:41 cvs1 Exp $
 */
public class CommonCodeTag extends TagSupport {

	private static final long serialVersionUID = 8567890157891127900L;

	private String code = null;
	private String key1 = null;
	private String key2 = null;
	private String tableName = null;
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
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		try {
			this.key1 = (String) ExpressionEvaluatorManager.evaluate("key1", key1, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		try {
			this.key2 = (String) ExpressionEvaluatorManager.evaluate("key2", key2, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		try {
			this.tableName = (String) ExpressionEvaluatorManager.evaluate("tableName", tableName, java.lang.String.class, this, pageContext);
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
			CodeEntity codeEntity = null;
			if (this.getTableName() == null) {
				codeEntity = getCommonCodeManager().getCodeEntity(code);
			} else {
				if (key1 != null) {
					codeEntity = getCommonCodeManager().getCodeEntity(tableName, code, key1);
				} else {
					codeEntity = getCommonCodeManager().getCodeEntity(tableName, code);
				}
			}
			if (codeEntity == null)
				return SKIP_BODY;

			JspWriter out = pageContext.getOut();
			if (codeEntity != null)
				out.print(codeEntity.getData1());
		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

}
