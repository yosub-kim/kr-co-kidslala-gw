/*
 * $Id: AttachData.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * creation-date : 2006. 3. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO Provide description for "AttachData"
 * 
 * @author halberd
 * @version $Id: AttachData.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
public class AttachData {
	private String seq;
	private String taskId;
	private String projectCode;
	private String taskFormTypeId;
	private String attachCreateDate;
	private String attachCreatorId;
	private String attachCreatorName;
	private String attachSeq;
	private String attachWorkName;
	private String attachOutputName;
	private String attachFileId;
	private String attachIsEssential;
	private String attachFileName;
	private String attachOutputType;
	private String attachOutputTypeName;
	private String attachOutputBusType;
	private String attachOutputBusTypeName;

	/**
	 * @return the attachCreatorName
	 */
	public String getAttachCreatorName() {
		return attachCreatorName;
	}

	/**
	 * @param attachCreatorName the attachCreatorName to set
	 */
	public void setAttachCreatorName(String attachCreatorName) {
		this.attachCreatorName = attachCreatorName;
	}

	/**
	 * @return the attachOutputTypeName
	 */
	public String getAttachOutputTypeName() {
		return attachOutputTypeName;
	}

	/**
	 * @param attachOutputTypeName the attachOutputTypeName to set
	 */
	public void setAttachOutputTypeName(String attachOutputTypeName) {
		this.attachOutputTypeName = attachOutputTypeName;
	}

	/**
	 * @return the attachOutputBusTypeName
	 */
	public String getAttachOutputBusTypeName() {
		return attachOutputBusTypeName;
	}

	/**
	 * @param attachOutputBusTypeName the attachOutputBusTypeName to set
	 */
	public void setAttachOutputBusTypeName(String attachOutputBusTypeName) {
		this.attachOutputBusTypeName = attachOutputBusTypeName;
	}

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return the taskFormTypeId
	 */
	public String getTaskFormTypeId() {
		return taskFormTypeId;
	}

	/**
	 * @param taskFormTypeId the taskFormTypeId to set
	 */
	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	/**
	 * @return the attachCreateDate
	 */
	public String getAttachCreateDate() {
		return attachCreateDate;
	}

	/**
	 * @param attachCreateDate the attachCreateDate to set
	 */
	public void setAttachCreateDate(String attachCreateDate) {
		this.attachCreateDate = attachCreateDate;
	}

	/**
	 * @return the attachCreatorId
	 */
	public String getAttachCreatorId() {
		return attachCreatorId;
	}

	/**
	 * @param attachCreatorId the attachCreatorId to set
	 */
	public void setAttachCreatorId(String attachCreatorId) {
		this.attachCreatorId = attachCreatorId;
	}

	/**
	 * @return the attachSeq
	 */
	public String getAttachSeq() {
		return attachSeq;
	}

	/**
	 * @param attachSeq the attachSeq to set
	 */
	public void setAttachSeq(String attachSeq) {
		this.attachSeq = attachSeq;
	}

	/**
	 * @return the attachWorkName
	 */
	public String getAttachWorkName() {
		return attachWorkName;
	}

	/**
	 * @param attachWorkName the attachWorkName to set
	 */
	public void setAttachWorkName(String attachWorkName) {
		this.attachWorkName = attachWorkName;
	}

	/**
	 * @return the attachOutputName
	 */
	public String getAttachOutputName() {
		return attachOutputName;
	}

	/**
	 * @param attachOutputName the attachOutputName to set
	 */
	public void setAttachOutputName(String attachOutputName) {
		this.attachOutputName = attachOutputName;
	}

	/**
	 * @return the attachFileId
	 */
	public String getAttachFileId() {
		return attachFileId;
	}

	/**
	 * @param attachFileId the attachFileId to set
	 */
	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}

	/**
	 * @return the attachIsEssential
	 */
	public String getAttachIsEssential() {
		return attachIsEssential;
	}

	/**
	 * @param attachIsEssential the attachIsEssential to set
	 */
	public void setAttachIsEssential(String attachIsEssential) {
		this.attachIsEssential = attachIsEssential;
	}

	/**
	 * @return the attachFileName
	 */
	public String getAttachFileName() {
		return attachFileName;
	}

	/**
	 * @param attachFileName the attachFileName to set
	 */
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	/**
	 * @return the attachOutputType
	 */
	public String getAttachOutputType() {
		return attachOutputType;
	}

	/**
	 * @param attachOutputType the attachOutputType to set
	 */
	public void setAttachOutputType(String attachOutputType) {
		this.attachOutputType = attachOutputType;
	}

	/**
	 * @return the attachOutputBusType
	 */
	public String getAttachOutputBusType() {
		return attachOutputBusType;
	}

	/**
	 * @param attachOutputBusType the attachOutputBusType to set
	 */
	public void setAttachOutputBusType(String attachOutputBusType) {
		this.attachOutputBusType = attachOutputBusType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
