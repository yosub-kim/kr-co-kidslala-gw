/*
 * $Id: TableNameCodeTag.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * created by    : 안성호
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.code;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import kr.co.kmac.pms.common.ajax.JSONWriter;
import kr.co.kmac.pms.common.code.data.Code;
import kr.co.kmac.pms.common.code.manager.CommonCodeManager;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TableNameCodeTag extends TagSupport {
	private static final long serialVersionUID = -7652820146661824773L;
	private String tableName = null;
	private String all = null;
	private String isSelectBox = null;
	private String attribute = null;
	private String selectedInfo = null;

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
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName(String tableName) {
		try {
			this.tableName = (String) ExpressionEvaluatorManager.evaluate("tableName", tableName, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the isSelectBox
	 */
	public String getIsSelectBox() {
		return isSelectBox;
	}

	/**
	 * @param isSelectBox the isSelectBox to set
	 */
	public void setIsSelectBox(String isSelectBox) {
		this.isSelectBox = isSelectBox;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		try {
			this.attribute = (String) ExpressionEvaluatorManager.evaluate("attribute", attribute, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the selectedInfo
	 */
	public String getSelectedInfo() {
		return selectedInfo;
	}

	/**
	 * @param selectedInfo the selectedInfo to set
	 */
	public void setSelectedInfo(String selectedInfo) {
		try {
			this.selectedInfo = (String) ExpressionEvaluatorManager.evaluate("selectedInfo", selectedInfo, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		try {
			this.all = (String) ExpressionEvaluatorManager.evaluate("all", all, java.lang.String.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		}
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			List<Code> list = getCommonCodeManager().getCodeEntitySimpleList(this.tableName);
			if (list != null) {
				if ("Y".equals(this.all)) {
					List<Code> allList = new ArrayList<Code>();
					allList.add(new Code(this.tableName, "", "전체"));
					allList.addAll(list);
					list = allList;
				}
				if (isSelectBox != null && isSelectBox.equals("Y")) {
					StringBuffer sb = new StringBuffer();
					for (Code code : list) {
						if (selectedInfo.equals(code.getKey())) {
							sb.append("<option value='" + code.getKey() + "' selected >" + code.getData() + "</option>");
						} else {
							sb.append("<option value='" + code.getKey() + "'  >" + code.getData() + "</option>");
						}
					}
					String html = "<SELECT " + attribute + ">" + sb.toString() + "</SELECT>";
					out.print(html);
				} else {
					JSONWriter writer = new JSONWriter();
					out.print(writer.write(list));
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // TODO LOG 처리
			// throw new JspException(e);
		}

		return SKIP_BODY;
	}
}
