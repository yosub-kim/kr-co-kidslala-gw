package kr.co.kmac.pms.system.processtemplate.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProcessTemplateDetail {
	private String processTemplateCode;
	private String processTemplateName;

	private int workSeq;
	private String activityName;
	private int level;
	private int parentWorkSeq;
	private int workType;
	private boolean editable;
	private boolean necessary;

	public ProcessTemplateDetail() {
		super();
	}

	public ProcessTemplateDetail(String processTemplateCode, String processTemplateName, int workSeq, String activityName, int level,
			int parentWorkSeq, int workType, boolean editable, boolean necessary) {
		super();
		this.processTemplateCode = processTemplateCode;
		this.processTemplateName = processTemplateName;
		this.workSeq = workSeq;
		this.activityName = activityName;
		this.level = level;
		this.parentWorkSeq = parentWorkSeq;
		this.workType = workType;
		this.editable = editable;
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
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the workType
	 */
	public int getWorkType() {
		return workType;
	}

	/**
	 * @param workType the workType to set
	 */
	public void setWorkType(int workType) {
		this.workType = workType;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
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

	/**
	 * @return the parentWorkSeq
	 */
	public int getParentWorkSeq() {
		if (parentWorkSeq > 0) {
			return parentWorkSeq;
		} else {
			return -1;
		}
	}

	/**
	 * @param parentWorkSeq the parentWorkSeq to set
	 */
	public void setParentWorkSeq(int parentWorkSeq) {
		this.parentWorkSeq = parentWorkSeq;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
