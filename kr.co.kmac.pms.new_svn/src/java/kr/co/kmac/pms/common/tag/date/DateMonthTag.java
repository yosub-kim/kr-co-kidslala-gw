package kr.co.kmac.pms.common.tag.date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import kr.co.kmac.pms.common.util.DateTime;

public class DateMonthTag extends TagSupport {
	private static final long serialVersionUID = -4894922453766868858L;

	private String attribute = null;
	private String selectedInfo = null;
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
	 * doStartTag is called by the JSP container when the tag is encountered
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			String selectedMonth = "";
			if ("this".equals(selectedInfo) || selectedInfo == null) {
				selectedMonth = DateTime.getMonth();
			} else {
				selectedMonth = selectedInfo;
			}
			StringBuffer sb = new StringBuffer();

			if (hasAll != null && hasAll.equals("Y")) {
				sb.append("<option value=''>ÀüÃ¼</option>");
			}

			String month = "";
			for (int i = 1; i <= 12; i++) {
				if (i < 10) {
					month = "0" + i;
				} else {
					month = i + "";
				}

				if (selectedMonth.equals(month)) {
					sb.append("<option value='" + month + "' selected >" + month + "</option>");
				} else {
					sb.append("<option value='" + month + "'  >" + month + "</option>");
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
