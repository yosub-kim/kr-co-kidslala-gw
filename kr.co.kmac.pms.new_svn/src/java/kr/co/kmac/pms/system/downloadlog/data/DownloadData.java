/*
 * $Id: DownloadData.java,v 1.2 2012/07/16 11:43:41 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 30.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.downloadlog.data;

public class DownloadData {
	private String ssn;
	private String name;
	private String company;
	private String cnt;
	private String date;
	private String dept;
	private String position;
	private String jobclass;
	private String isDailyDownloadLimit;
	/**
	 * @return Returns the cnt.
	 */
	public String getCnt() {
		return this.cnt;
	}
	/**
	 * @param cnt The cnt to set.
	 */
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return this.company;
	}
	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return this.date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the dept.
	 */
	public String getDept() {
		return this.dept;
	}
	/**
	 * @param dept The dept to set.
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * @return Returns the jobclass.
	 */
	public String getJobclass() {
		return this.jobclass;
	}
	/**
	 * @param jobclass The jobclass to set.
	 */
	public void setJobclass(String jobclass) {
		this.jobclass = jobclass;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return this.position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return this.ssn;
	}
	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	/**
	 * @return the isDailyDownloadLimit
	 */
	public String getIsDailyDownloadLimit() {
		return isDailyDownloadLimit;
	}
	/**
	 * @param isDailyDownloadLimit the isDailyDownloadLimit to set
	 */
	public void setIsDailyDownloadLimit(String isDailyDownloadLimit) {
		this.isDailyDownloadLimit = isDailyDownloadLimit;
	}
}