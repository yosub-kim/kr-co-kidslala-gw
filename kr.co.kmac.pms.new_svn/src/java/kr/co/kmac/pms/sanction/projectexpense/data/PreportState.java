/*
 * $Id: PreportState.java,v 1.1 2009/09/25 12:47:35 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : Sep 16, 2007
 * =========================================================
 * Copyright (c) 2007 ManInSoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.projectexpense.data;

public class PreportState {
	private String projectCode;
	private String ssn;
	private String assignDate;
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
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return Returns the assignDate.
	 */
	public String getAssignDate() {
		return assignDate;
	}
	/**
	 * @param assignDate The assignDate to set.
	 */
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

}
