/*
 * $Id: CustomerData.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.data;

/**
 * TODO Provide description for "CustomerData"
 * @author halberd
 * @version $Id: CustomerData.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 */
public class CustomerData {
	private String idx;
	private String master_idx;
	private String com_idx;
	private String embbs_idx;
	private String embbsName;
	private String embbsCompany;
	private String embbsDept;
	private String embbsPhone;
	private String embbsEmail;
	private String embbsGather;
	private String subject;
	private String writer;
	private String writerSsn;
	private String content;
	private String opinion;
	private String info_date;
	private String regdate;
	private String sanupgubun;
	private String readCnt;
	private String ip;
	private String customerInfoType;
	private String receiveGrade;
	private String projectName;
	private String projectCode;
	private String writerDept;
	private String customerName;
	private String customerCode;
	private String industryTypeCode;
	private String businessTypeCode;
	private String pmSsn;
	private String isOrg;
	
	private String customerInfoName;
	private String writerDeptName;
	private String businessTypeName;
	
	
	public String getBusinessTypeName() {
		return this.businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getCustomerInfoName() {
		return this.customerInfoName;
	}
	public void setCustomerInfoName(String customerInfoName) {
		this.customerInfoName = customerInfoName;
	}
	public String getWriterDeptName() {
		return this.writerDeptName;
	}
	public void setWriterDeptName(String writerDeptName) {
		this.writerDeptName = writerDeptName;
	}
	/**
	 * @return Returns the businessTypeCode.
	 */
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	/**
	 * @param businessTypeCode The businessTypeCode to set.
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	/**
	 * @return Returns the com_idx.
	 */
	public String getCom_idx() {
		return com_idx;
	}
	/**
	 * @param com_idx The com_idx to set.
	 */
	public void setCom_idx(String com_idx) {
		this.com_idx = com_idx;
	}
	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the customerInfoType.
	 */
	public String getCustomerInfoType() {
		return customerInfoType;
	}
	/**
	 * @param customerInfoType The customerInfoType to set.
	 */
	public void setCustomerInfoType(String customerInfoType) {
		this.customerInfoType = customerInfoType;
	}
	/**
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return Returns the embbs_idx.
	 */
	public String getEmbbs_idx() {
		return embbs_idx;
	}
	/**
	 * @param embbs_idx The embbs_idx to set.
	 */
	public void setEmbbs_idx(String embbs_idx) {
		this.embbs_idx = embbs_idx;
	}
	/**
	 * @return Returns the embbsCompany.
	 */
	public String getEmbbsCompany() {
		return embbsCompany;
	}
	/**
	 * @param embbsCompany The embbsCompany to set.
	 */
	public void setEmbbsCompany(String embbsCompany) {
		this.embbsCompany = embbsCompany;
	}
	/**
	 * @return Returns the embbsDept.
	 */
	public String getEmbbsDept() {
		return embbsDept;
	}
	/**
	 * @param embbsDept The embbsDept to set.
	 */
	public void setEmbbsDept(String embbsDept) {
		this.embbsDept = embbsDept;
	}
	/**
	 * @return Returns the embbsEmail.
	 */
	public String getEmbbsEmail() {
		return embbsEmail;
	}
	/**
	 * @param embbsEmail The embbsEmail to set.
	 */
	public void setEmbbsEmail(String embbsEmail) {
		this.embbsEmail = embbsEmail;
	}
	/**
	 * @return Returns the embbsGather.
	 */
	public String getEmbbsGather() {
		return embbsGather;
	}
	/**
	 * @param embbsGather The embbsGather to set.
	 */
	public void setEmbbsGather(String embbsGather) {
		this.embbsGather = embbsGather;
	}
	/**
	 * @return Returns the embbsName.
	 */
	public String getEmbbsName() {
		return embbsName;
	}
	/**
	 * @param embbsName The embbsName to set.
	 */
	public void setEmbbsName(String embbsName) {
		this.embbsName = embbsName;
	}
	/**
	 * @return Returns the embbsPhone.
	 */
	public String getEmbbsPhone() {
		return embbsPhone;
	}
	/**
	 * @param embbsPhone The embbsPhone to set.
	 */
	public void setEmbbsPhone(String embbsPhone) {
		this.embbsPhone = embbsPhone;
	}
	/**
	 * @return Returns the idx.
	 */
	public String getIdx() {
		return idx;
	}
	/**
	 * @param idx The idx to set.
	 */
	public void setIdx(String idx) {
		this.idx = idx;
	}
	/**
	 * @return Returns the industryTypeCode.
	 */
	public String getIndustryTypeCode() {
		return industryTypeCode;
	}
	/**
	 * @param industryTypeCode The industryTypeCode to set.
	 */
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}
	/**
	 * @return Returns the info_date.
	 */
	public String getInfo_date() {
		return info_date;
	}
	/**
	 * @param info_date The info_date to set.
	 */
	public void setInfo_date(String info_date) {
		this.info_date = info_date;
	}
	/**
	 * @return Returns the ip.
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip The ip to set.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return Returns the master_idx.
	 */
	public String getMaster_idx() {
		return master_idx;
	}
	/**
	 * @param master_idx The master_idx to set.
	 */
	public void setMaster_idx(String master_idx) {
		this.master_idx = master_idx;
	}
	/**
	 * @return Returns the opinion.
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 * @param opinion The opinion to set.
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**
	 * @return Returns the pmSsn.
	 */
	public String getPmSsn() {
		return pmSsn;
	}
	/**
	 * @param pmSsn The pmSsn to set.
	 */
	public void setPmSsn(String pmSsn) {
		this.pmSsn = pmSsn;
	}
	public String getIsOrg() {
		return isOrg;
	}
	public void setIsOrg(String isOrg) {
		this.isOrg = isOrg;
	}
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
	 * @return Returns the projectName.
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName The projectName to set.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return Returns the readCnt.
	 */
	public String getReadCnt() {
		return readCnt;
	}
	/**
	 * @param readCnt The readCnt to set.
	 */
	public void setReadCnt(String readCnt) {
		this.readCnt = readCnt;
	}
	/**
	 * @return Returns the receiveGrade.
	 */
	public String getReceiveGrade() {
		return receiveGrade;
	}
	/**
	 * @param receiveGrade The receiveGrade to set.
	 */
	public void setReceiveGrade(String receiveGrade) {
		this.receiveGrade = receiveGrade;
	}
	/**
	 * @return Returns the regdate.
	 */
	public String getRegdate() {
		return regdate;
	}
	/**
	 * @param regdate The regdate to set.
	 */
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	/**
	 * @return Returns the sanupgubun.
	 */
	public String getSanupgubun() {
		return sanupgubun;
	}
	/**
	 * @param sanupgubun The sanupgubun to set.
	 */
	public void setSanupgubun(String sanupgubun) {
		this.sanupgubun = sanupgubun;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the writer.
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * @param writer The writer to set.
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	/**
	 * @return Returns the writerDept.
	 */
	public String getWriterDept() {
		return writerDept;
	}
	/**
	 * @param writerDept The writerDept to set.
	 */
	public void setWriterDept(String writerDept) {
		this.writerDept = writerDept;
	}
	/**
	 * @return Returns the writerSsn.
	 */
	public String getWriterSsn() {
		return writerSsn;
	}
	/**
	 * @param writerSsn The writerSsn to set.
	 */
	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}
}
