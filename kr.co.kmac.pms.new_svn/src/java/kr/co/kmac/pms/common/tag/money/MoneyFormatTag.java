package kr.co.kmac.pms.common.tag.money;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import kr.co.kmac.pms.common.util.DateTime;
import kr.co.kmac.pms.common.util.StringUtil;

public class MoneyFormatTag extends TagSupport {
	private static final long serialVersionUID = -4894922453766868858L;

	private String value = null;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		try {
			this.value = (String) ExpressionEvaluatorManager.evaluate("value", value, java.lang.String.class, this, pageContext);
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
			String html = "";
			try {
				if (value != null && value.length() > 0)
					html = StringUtil.longt2Money(Long.parseLong(value));
			} catch (Exception e) {
				html = "0";
			}
			out.print(html);

		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

}
