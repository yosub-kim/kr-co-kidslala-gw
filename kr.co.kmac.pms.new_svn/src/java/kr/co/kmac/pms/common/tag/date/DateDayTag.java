package kr.co.kmac.pms.common.tag.date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import kr.co.kmac.pms.common.util.DateTime;

public class DateDayTag extends TagSupport {
	private static final long serialVersionUID = -4894922453766868858L;

	private String attribute = null;
	private String selectedInfo = null;

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
			String selectedDay = "";
			if ("this".equals(selectedInfo) || selectedInfo == null) {
				selectedDay = DateTime.getMonth();
			} else {
				selectedDay = selectedInfo;
			}

			StringBuffer sb = new StringBuffer();
			String day = "";
			for (int i = 1; i <= 31; i++) {
				if (i < 10) {
					day = "0" + i;
				} else {
					day = i + "";
				}

				if (selectedDay.equals(day)) {
					sb.append("<option value='" + day + "' selected >" + day + "</option>");
				} else {
					sb.append("<option value='" + day + "'  >" + day + "</option>");
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
