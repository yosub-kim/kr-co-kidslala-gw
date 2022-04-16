package kr.co.kmac.pms.system.processtemplate.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProcessTemplate {

	private String processTemplateCode;
	private String processTemplateName;

	/**
	 * @return the processTemplateCode
	 */
	public String getProcessTemplateCode() {
		return processTemplateCode;
	}

	/**
	 * @param processTemplateCode the processTemplateCode to set
	 */
	public void setProcessTemplateCode(String processTemplateCode) {
		this.processTemplateCode = processTemplateCode;
	}

	/**
	 * @return the processTemplateName
	 */
	public String getProcessTemplateName() {
		return processTemplateName;
	}

	/**
	 * @param processTemplateName the processTemplateName to set
	 */
	public void setProcessTemplateName(String processTemplateName) {
		this.processTemplateName = processTemplateName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
