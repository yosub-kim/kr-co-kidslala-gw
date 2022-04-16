/*
 * $Id: ProjectCsrInfo.java,v 1.3 2011/07/15 01:10:13 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.master.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.kmac.pms.project.master.form.ProjectMasterInfoForm;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로젝트 고객사 정보를 가지고 있는 model Class
 * 
 * @author jiwoongLee
 * @version $Id: ProjectCsrInfo.java,v 1.3 2011/07/15 01:10:13 cvs Exp $
 */
public class ProjectCsrInfo {

	private int seq;
	private String isRep;
	private String projectCode;
	private String projectName;
	private String runningDivCode;
	private String runningDivName;
	private String customerName;
	private String customerCode;
	private String customerWorkPName;
	private String customerWorkPEmail;
	private String customerWorkPPhone;
	private String customerContPName;
	private String customerContPEmail;
	private String customerContPPhone;
	private String isEvaluatel;
	private String isVoc;
	private Date sendDate;
	private Date receiveDate;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the isRep
	 */
	public String getIsRep() {
		return isRep;
	}

	/**
	 * @param isRep the isRep to set
	 */
	public void setIsRep(String isRep) {
		this.isRep = isRep;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerWorkPName() {
		return customerWorkPName;
	}

	public void setCustomerWorkPName(String customerWorkPName) {
		this.customerWorkPName = customerWorkPName;
	}

	public String getCustomerWorkPEmail() {
		return customerWorkPEmail;
	}

	public void setCustomerWorkPEmail(String customerWorkPEmail) {
		this.customerWorkPEmail = customerWorkPEmail;
	}

	public String getCustomerWorkPPhone() {
		return customerWorkPPhone;
	}

	public void setCustomerWorkPPhone(String customerWorkPPhone) {
		this.customerWorkPPhone = customerWorkPPhone;
	}

	public String getCustomerContPName() {
		return customerContPName;
	}

	public void setCustomerContPName(String customerContPName) {
		this.customerContPName = customerContPName;
	}

	public String getCustomerContPEmail() {
		return customerContPEmail;
	}

	public void setCustomerContPEmail(String customerContPEmail) {
		this.customerContPEmail = customerContPEmail;
	}

	public String getCustomerContPPhone() {
		return customerContPPhone;
	}

	public void setCustomerContPPhone(String customerContPPhone) {
		this.customerContPPhone = customerContPPhone;
	}

	public String getIsEvaluatel() {
		return isEvaluatel;
	}

	public void setIsEvaluatel(String isEvaluatel) {
		this.isEvaluatel = isEvaluatel;
	}

	public String getIsVoc() {
		return isVoc;
	}

	public void setIsVoc(String isVoc) {
		this.isVoc = isVoc;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getRunningDivCode() {
		return runningDivCode;
	}

	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	public String getRunningDivName() {
		return runningDivName;
	}

	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}

	public static List<ProjectCsrInfo> valueOf(ProjectMasterInfoForm form) {
		List<ProjectCsrInfo> list = new ArrayList<ProjectCsrInfo>();
		ProjectCsrInfo info = new ProjectCsrInfo();
		info.setSeq(1);
		info.setIsRep("Y");
		info.setProjectCode(form.getProjectCode());
		// info.setCustomerName(customerName)
		// info.setCustomerCode(customerCode)
		info.setCustomerWorkPName(form.getCustomerWorkPName());
		info.setCustomerWorkPEmail(form.getCustomerWorkPEmail());
		info.setCustomerWorkPPhone(form.getCustomerWorkPPhone());
		info.setCustomerContPName(form.getCustomerContPName());
		info.setCustomerContPEmail(form.getCustomerContPEmail());
		info.setCustomerContPPhone(form.getCustomerContPPhone());
		// info.setIsEvaluatel(isEvaluatel);
		// info.setIsVoc(isVoc);

		list.add(info);
		return list;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
