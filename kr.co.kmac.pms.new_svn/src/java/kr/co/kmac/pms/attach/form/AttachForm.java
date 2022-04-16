/*
 * $Id: AttachForm.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.form;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionForm;

/**
 * TODO Provide description for "AttachForm"
 * @author halberd
 * @version $Id: AttachForm.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 */
/**
 * @struts:form name="attachAction"
 */
public class AttachForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766929L;
	private String taskId;
	private String projectCode;
	private String taskFormTypeId;
	private String[] f_seq;
	private String[] f_taskId;
	private String[] attachCreateDate;
	private String[] attachCreatorId;
	private String[] attachSeq;
	private String[] attachWorkName;
	private String[] attachOutputName;
	private String[] attachFileId;
	private String[] attachIsEssential;
	private String[] attachFileName;
	private String[] attachOutputType;
	private String[] attachOutputBusType;
	private String[] attachFilePath;
	private String[] attachCount;

	/**
	 * @return the f_taskId
	 */
	public String[] getF_taskId() {
		return f_taskId;
	}

	/**
	 * @param f_taskId the f_taskId to set
	 */
	public void setF_taskId(String[] f_taskId) {
		this.f_taskId = f_taskId;
	}

	/**
	 * @return the f_seq
	 */
	public String[] getF_seq() {
		return f_seq;
	}

	/**
	 * @param f_seq the f_seq to set
	 */
	public void setF_seq(String[] f_seq) {
		this.f_seq = f_seq;
	}

	/**
	 * @return Returns the attachCount.
	 */
	public String[] getAttachCount() {
		return attachCount;
	}

	/**
	 * @param attachCount The attachCount to set.
	 */
	public void setAttachCount(String[] attachCount) {
		this.attachCount = attachCount;
	}

	/**
	 * @return Returns the attachCreateDate.
	 */
	public String[] getAttachCreateDate() {
		return attachCreateDate;
	}

	/**
	 * @param attachCreateDate The attachCreateDate to set.
	 */
	public void setAttachCreateDate(String[] attachCreateDate) {
		this.attachCreateDate = attachCreateDate;
	}

	/**
	 * @return Returns the attachCreatorId.
	 */
	public String[] getAttachCreatorId() {
		return attachCreatorId;
	}

	/**
	 * @param attachCreatorId The attachCreatorId to set.
	 */
	public void setAttachCreatorId(String[] attachCreatorId) {
		this.attachCreatorId = attachCreatorId;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the taskFormTypeId.
	 */
	public String getTaskFormTypeId() {
		return taskFormTypeId;
	}

	/**
	 * @param taskFormTypeId The taskFormTypeId to set.
	 */
	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	/**
	 * @return Returns the taskId.
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return Returns the attachFileId.
	 */
	public String[] getAttachFileId() {
		return attachFileId;
	}

	/**
	 * @param attachFileId The attachFileId to set.
	 */
	public void setAttachFileId(String[] attachFileId) {
		this.attachFileId = attachFileId;
	}

	/**
	 * @return Returns the attachFileName.
	 */
	public String[] getAttachFileName() {
		return attachFileName;
	}

	/**
	 * @param attachFileName The attachFileName to set.
	 */
	public void setAttachFileName(String[] attachFileName) {
		this.attachFileName = attachFileName;
	}

	/**
	 * @return Returns the attachFilePath.
	 */
	public String[] getAttachFilePath() {
		return attachFilePath;
	}

	/**
	 * @param attachFilePath The attachFilePath to set.
	 */
	public void setAttachFilePath(String[] attachFilePath) {
		this.attachFilePath = attachFilePath;
	}

	/**
	 * @return Returns the attachIsEssential.
	 */
	public String[] getAttachIsEssential() {
		return attachIsEssential;
	}

	/**
	 * @param attachIsEssential The attachIsEssential to set.
	 */
	public void setAttachIsEssential(String[] attachIsEssential) {
		this.attachIsEssential = attachIsEssential;
	}

	/**
	 * @return Returns the attachOutputBusType.
	 */
	public String[] getAttachOutputBusType() {
		return attachOutputBusType;
	}

	/**
	 * @param attachOutputBusType The attachOutputBusType to set.
	 */
	public void setAttachOutputBusType(String[] attachOutputBusType) {
		this.attachOutputBusType = attachOutputBusType;
	}

	/**
	 * @return Returns the attachOutputName.
	 */
	public String[] getAttachOutputName() {
		return attachOutputName;
	}

	/**
	 * @param attachOutputName The attachOutputName to set.
	 */
	public void setAttachOutputName(String[] attachOutputName) {
		this.attachOutputName = attachOutputName;
	}

	/**
	 * @return Returns the attachOutputType.
	 */
	public String[] getAttachOutputType() {
		return attachOutputType;
	}

	/**
	 * @param attachOutputType The attachOutputType to set.
	 */
	public void setAttachOutputType(String[] attachOutputType) {
		this.attachOutputType = attachOutputType;
	}

	/**
	 * @return Returns the attachSeq.
	 */
	public String[] getAttachSeq() {
		return attachSeq;
	}

	/**
	 * @param attachSeq The attachSeq to set.
	 */
	public void setAttachSeq(String[] attachSeq) {
		this.attachSeq = attachSeq;
	}

	/**
	 * @return Returns the attachWorkName.
	 */
	public String[] getAttachWorkName() {
		return attachWorkName;
	}

	/**
	 * @param attachWorkName The attachWorkName to set.
	 */
	public void setAttachWorkName(String[] attachWorkName) {
		this.attachWorkName = attachWorkName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
