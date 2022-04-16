/*
 * $Id: ProjectMonthlyReportData.java,v 1.2 2010/05/12 09:35:42 cvs2 Exp $
 * created by    : ¿”øµ»∆
 * creation-date : 2010. 5. 11.
 * =========================================================
 * Copyright (c) 2010 KMAC All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

public class ProjectMonthlyReportData {
	private String state;
	private String projectCode;
	private String projectName;
	private String businessTypeCode;
	private String businessTypeName;
	private String assignDate;
	private String assignee;
	private String writerSsn;
	private String writerName;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
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
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getWriterSsn() {
		return writerSsn;
	}
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	
}
