/*
 * $Id: ProjectMonthlyReportDataForList.java,v 1.2 2010/05/12 09:35:42 cvs2 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

public class ProjectMonthlyReportDataForList {
	private String writer;
	private String name;
	private String deptCode;
	private String dept;
	private String bizTypeCode;
	private String bizType;
	private String isWrite;
	private String isNotWrite;
	private String isNotReview;
	private String isNotApprove;
	private String confirm;
	private String seq;
	private String total;
	private String projectName;
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}	
	public String getBizTypeCode() {
		return bizTypeCode;
	}
	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getIsWrite() {
		return isWrite;
	}
	public void setIsWrite(String isWrite) {
		this.isWrite = isWrite;
	}
	public String getIsNotWrite() {
		return isNotWrite;
	}
	public void setIsNotWrite(String isNotWrite) {
		this.isNotWrite = isNotWrite;
	}
	public String getIsNotReview() {
		return isNotReview;
	}
	public void setIsNotReview(String isNotReview) {
		this.isNotReview = isNotReview;
	}
	public String getIsNotApprove() {
		return isNotApprove;
	}
	public void setIsNotApprove(String isNotApprove) {
		this.isNotApprove = isNotApprove;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
}
