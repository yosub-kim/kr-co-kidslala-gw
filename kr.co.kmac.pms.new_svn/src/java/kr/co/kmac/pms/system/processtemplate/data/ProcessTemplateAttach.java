package kr.co.kmac.pms.system.processtemplate.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProcessTemplateAttach {
	private String processTemplateCode;
	private int workSeq;
	private int attachSeq;
	private String outputName;
	private String outputType;
	private String bizType;
	private boolean necessary;

	public ProcessTemplateAttach() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessTemplateAttach(String processTemplateCode, int workSeq, int attachSeq, String outputName, String outputType, String bizType,
			boolean necessary) {
		super();
		this.processTemplateCode = processTemplateCode;
		this.workSeq = workSeq;
		this.attachSeq = attachSeq;
		this.outputName = outputName;
		this.outputType = outputType;
		this.bizType = bizType;
		this.necessary = necessary;
	}

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
	 * @return the workSeq
	 */
	public int getWorkSeq() {
		return workSeq;
	}

	/**
	 * @param workSeq the workSeq to set
	 */
	public void setWorkSeq(int workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return the attachSeq
	 */
	public int getAttachSeq() {
		return attachSeq;
	}

	/**
	 * @param attachSeq the attachSeq to set
	 */
	public void setAttachSeq(int attachSeq) {
		this.attachSeq = attachSeq;
	}

	/**
	 * @return the outputName
	 */
	public String getOutputName() {
		return outputName;
	}

	/**
	 * @param outputName the outputName to set
	 */
	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	/**
	 * @return the outputType
	 */
	public String getOutputType() {
		return outputType;
	}

	/**
	 * @param outputType the outputType to set
	 */
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	/**
	 * @return the bizType
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * @return the necessary
	 */
	public boolean isNecessary() {
		return necessary;
	}

	/**
	 * @param necessary the necessary to set
	 */
	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
