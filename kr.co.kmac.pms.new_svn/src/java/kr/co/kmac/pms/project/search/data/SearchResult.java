/*
 * $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.search.data;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: Project.java,v 1.9 2018/05/28 06:24:22 cvs Exp $
 */
/**
 * @author yhyim
 *
 */
public class SearchResult {

	private String projectCode;
	private String projectName;
	private String customerName;
	private String attachFileId;
	private String attachFileName;
	private String attachOutputName;
	private String taskId;
	private String taskFormTypeId;
	private String taskFormTypeName;
	private String attachCreateDate;
	private String attachCreatorName;
	private String attachOutputType;
	private String attachOUtputTypeName;
	private String attachOutputBusType;
	private String attachOutputBusTypeName;
	private List<String> hashTag;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAttachFileId() {
		return attachFileId;
	}

	public void setAttachFileId(String attachFileId) {
		this.attachFileId = attachFileId;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public String getAttachOutputName() {
		return attachOutputName;
	}

	public void setAttachOutputName(String attachOutputName) {
		this.attachOutputName = attachOutputName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskFormTypeId() {
		return taskFormTypeId;
	}

	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	public String getTaskFormTypeName() {
		return taskFormTypeName;
	}

	public void setTaskFormTypeName(String taskFormTypeName) {
		this.taskFormTypeName = taskFormTypeName;
	}

	public String getAttachCreateDate() {
		return attachCreateDate;
	}

	public void setAttachCreateDate(String attachCreateDate) {
		this.attachCreateDate = attachCreateDate;
	}

	public String getAttachCreatorName() {
		return attachCreatorName;
	}

	public void setAttachCreatorName(String attachCreatorName) {
		this.attachCreatorName = attachCreatorName;
	}

	public String getAttachOutputType() {
		return attachOutputType;
	}

	public void setAttachOutputType(String attachOutputType) {
		this.attachOutputType = attachOutputType;
	}

	public String getAttachOUtputTypeName() {
		return attachOUtputTypeName;
	}

	public void setAttachOUtputTypeName(String attachOUtputTypeName) {
		this.attachOUtputTypeName = attachOUtputTypeName;
	}

	public String getAttachOutputBusType() {
		return attachOutputBusType;
	}

	public void setAttachOutputBusType(String attachOutputBusType) {
		this.attachOutputBusType = attachOutputBusType;
	}

	public String getAttachOutputBusTypeName() {
		return attachOutputBusTypeName;
	}

	public void setAttachOutputBusTypeName(String attachOutputBusTypeName) {
		this.attachOutputBusTypeName = attachOutputBusTypeName;
	}

	public List<String> getHashTag() {
		return hashTag;
	}

	public void setHashTag(List<String> hashTag) {
		this.hashTag = hashTag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
