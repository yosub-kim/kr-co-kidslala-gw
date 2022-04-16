package kr.co.kmac.pms.service.scheduler.data.bizplay.empl;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BizplayExpertPoolInfo { 
	private String emplNo;
	private String email;
	private String name;
	private String mobileNo;
	private String dept;
	private String deptName;
	private String companyPositionName;
	private String compnayCode;
	private String bsnnNo;
	private String enable;

	public String getEmplNo() {
		return emplNo;
	}

	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCompanyPositionName() {
		return companyPositionName;
	}

	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}

	public String getCompnayCode() {
		return compnayCode;
	}

	public void setCompnayCode(String compnayCode) {
		this.compnayCode = compnayCode;
	}

	public String getBsnnNo() {
		return bsnnNo;
	}

	public void setBsnnNo(String bsnnNo) {
		this.bsnnNo = bsnnNo;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
