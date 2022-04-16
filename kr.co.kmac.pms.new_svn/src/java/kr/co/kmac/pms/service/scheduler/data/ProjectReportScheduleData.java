/*
 * $Id: ProjectReportScheduleData.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 3.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 지도일지 할당을 위한 데이터 객체
 * 
 * @author jiwoongLee
 * @version $Id: ProjectReportScheduleData.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 */
public class ProjectReportScheduleData {

	private String projectCode;
	private String year;
	private String month;
	private String day;
	private String workSeq;
	private String chargeSsn;
	private String processTypeCode;
	private String taskFormTypeId;
	private String taskFormTypeName;
	private String businessTypeCode;

	/**
	 * @return Returns the businessTypeCode.
	 */
	public String getBusinessTypeCode() {
		return this.businessTypeCode;
	}

	/**
	 * @param businessTypeCode The businessTypeCode to set.
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return Returns the chargeSsn.
	 */
	public String getChargeSsn() {
		return this.chargeSsn;
	}

	/**
	 * @param chargeSsn The chargeSsn to set.
	 */
	public void setChargeSsn(String chargeSsn) {
		this.chargeSsn = chargeSsn;
	}

	/**
	 * @return Returns the day.
	 */
	public String getDay() {
		return this.day;
	}

	/**
	 * @param day The day to set.
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return Returns the month.
	 */
	public String getMonth() {
		return this.month;
	}

	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return Returns the processTypeCode.
	 */
	public String getProcessTypeCode() {
		return this.processTypeCode;
	}

	/**
	 * @param processTypeCode The processTypeCode to set.
	 */
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
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
	 * @return Returns the taskFormTypeId.
	 */
	public String getTaskFormTypeId() {
		return this.taskFormTypeId;
	}

	/**
	 * @param taskFormTypeId The taskFormTypeId to set.
	 */
	public void setTaskFormTypeId(String taskFormTypeId) {
		this.taskFormTypeId = taskFormTypeId;
	}

	/**
	 * @return Returns the taskFormTypeName.
	 */
	public String getTaskFormTypeName() {
		return this.taskFormTypeName;
	}

	/**
	 * @param taskFormTypeName The taskFormTypeName to set.
	 */
	public void setTaskFormTypeName(String taskFormTypeName) {
		this.taskFormTypeName = taskFormTypeName;
	}

	/**
	 * @return Returns the workSeq.
	 */
	public String getWorkSeq() {
		return this.workSeq;
	}

	/**
	 * @param workSeq The workSeq to set.
	 */
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return this.year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}