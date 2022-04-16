/*
 * $Id: EndProcessCheck.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: EndProcessCheck.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 */
public class EndProcessCheck {

	private String projectCode;
	private String taskId;
	private String isExecuted;

	/**
	 * @return Returns the isExecuted.
	 */
	public String getIsExecuted() {
		return this.isExecuted;
	}

	/**
	 * @param isExecuted The isExecuted to set.
	 */
	public void setIsExecuted(String isExecuted) {
		this.isExecuted = isExecuted;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return this.projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the taskId.
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
