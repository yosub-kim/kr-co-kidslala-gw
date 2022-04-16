/*
 * $Id: EmergencyConnectionData.java,v 1.2 2011/09/06 11:35:13 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.emergencyConnection.data;

public class EmergencyConnectionData {
	private String ssn;
	private String dept;
	private String companyPosition;
	private String name;
	private String email;
	private String companyTelNo;
	private String mobileNo;
	private String address1;
	private String companyPositionId;
	private String deptId;
	private String rowCnt;

	public String getAddress1() {

		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	public String getCompanyTelNo() {
		return companyTelNo;
	}

	public void setCompanyTelNo(String companyTelNo) {
		this.companyTelNo = companyTelNo;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getCompanyPositionId() {
		return companyPositionId;
	}

	public void setCompanyPositionId(String companyPositionId) {
		this.companyPositionId = companyPositionId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getRowCnt() {
		return rowCnt;
	}

	public void setRowCnt(String rowCnt) {
		this.rowCnt = rowCnt;
	}

}
