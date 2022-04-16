/*
 * $Id: DateYearTag.java,v 1.2 2011/07/15 01:10:12 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 3. 31.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.tag.date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import kr.co.kmac.pms.common.util.DateTime;

public class DateYearTag extends TagSupport {
	private static final long serialVersionUID = -4894922453766868858L;

	private String attribute = null;
	private String selectedInfo = null;
	private int beforeYears = 0;
	private int afterYears = 0;
	private String hasAll;

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
		this.attribute = attribute;
	}
	
	/**
	 * @return the hasAll
	 */
	public String getHasAll() {
		return hasAll;
	}

	/**
	 * @param hasAll the hasAll to set
	 */
	public void setHasAll(String hasAll) {
		try {
			this.hasAll = (String) ExpressionEvaluatorManager.evaluate("hasAll", hasAll, java.lang.String.class, this, pageContext);
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

	/**
	 * @return the beforeYears
	 */
	public int getBeforeYears() {
		return beforeYears;
	}

	/**
	 * @param beforeYears the beforeYears to set
	 */
	public void setBeforeYears(int beforeYears) {
		this.beforeYears = beforeYears;
	}

	/**
	 * @return the afterYears
	 */
	public int getAfterYears() {
		return afterYears;
	}

	/**
	 * @param afterYears the afterYears to set
	 */
	public void setAfterYears(int afterYears) {
		this.afterYears = afterYears;
	}

	/**
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			int thisYear = Integer.parseInt(DateTime.getYear());
			int startYear = thisYear - beforeYears;
			int fromYear = thisYear + afterYears;
			StringBuffer sb = new StringBuffer();
			String selectedYear = "";
			if ("this".equals(selectedInfo) || selectedInfo == null) {
				selectedYear = thisYear + "";
			} else {
				selectedYear = selectedInfo;
			}

			if(hasAll != null && hasAll.equals("Y")){
				sb.append("<option value=''>전체</option>");
			} 
			for (int i = fromYear; i >= startYear; i--) {
				if (selectedYear.equals(i + "")) {
					sb.append("<option value='" + i + "' selected >" + i + "</option>");
				} else {
					sb.append("<option value='" + i + "' >" + i + "</option>");
				}
			}

			String html = "<SELECT " + attribute + ">" + sb.toString() + "</SELECT>";
			out.print(html);
		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

}
