/*
 * $Id: ProjectCustomerInfo.java,v 1.1 2012/01/25 14:35:20 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.master.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 고객정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: ProjectCustomerInfo.java,v 1.1 2012/01/25 14:35:20 cvs Exp $
 */
public class ProjectCustomerInfo {

	private int seq;
	private String projectCode;
	private String customerInfoIdx;
	private String customerInfoType;
	private String subject;
	private String writer;
	private Date regDate;
	private String createUserSsn;
	private String createUserName;
	private Date createDate;

	public ProjectCustomerInfo() {
		super();
	}

	public ProjectCustomerInfo(String projectCode, String customerInfoIdx, String createUserSsn) {
		super();
		this.projectCode = projectCode;
		this.customerInfoIdx = customerInfoIdx;
		this.createUserSsn = createUserSsn;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
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
	 * @return the customerInfoIdx
	 */
	public String getCustomerInfoIdx() {
		return customerInfoIdx;
	}

	/**
	 * @param customerInfoIdx the customerInfoIdx to set
	 */
	public void setCustomerInfoIdx(String customerInfoIdx) {
		this.customerInfoIdx = customerInfoIdx;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the writer
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the createUserSsn
	 */
	public String getCreateUserSsn() {
		return createUserSsn;
	}

	/**
	 * @param createUserSsn the createUserSsn to set
	 */
	public void setCreateUserSsn(String createUserSsn) {
		this.createUserSsn = createUserSsn;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the customerInfoType
	 */
	public String getCustomerInfoType() {
		return customerInfoType;
	}

	/**
	 * @param customerInfoType the customerInfoType to set
	 */
	public void setCustomerInfoType(String customerInfoType) {
		this.customerInfoType = customerInfoType;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
