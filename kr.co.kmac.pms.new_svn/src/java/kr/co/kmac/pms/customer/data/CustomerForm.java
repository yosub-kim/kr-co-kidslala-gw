/*
 * $Id: CustomerForm.java,v 1.4 2017/09/11 00:37:25 cvs Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.data;

import kr.co.kmac.pms.attach.form.AttachForm;

;

/**
 * TODO Provide description for "CustomerForm"
 * @author halberd
 * @version $Id: CustomerForm.java,v 1.4 2017/09/11 00:37:25 cvs Exp $
 */
/**
 * @struts:form name="customerInfoMobileAction"
 */
public class CustomerForm extends AttachForm {
	static final long serialVersionUID = -7034897190745766929L;

	private String idx;
	private String master_idx;
	private String com_idx;
	private String embbs_idx;
	private String embbsName;
	private String embbsCompany;
	private String embbsDept;
	private String embbsStatus;
	private String embbsPhone;
	private String embbsEmail;
	private String embbsGather;
	private String embbsMethod;
	private String subject;
	private String writer;
	private String writerSsn;
	private String content;
	private String opinion;
	private String info_date;
	private String info_dateYyyy;
	private String info_dateMm;
	private String info_dateDd;
	private String regdate;
	private String sanupgubun;
	private String readCnt;
	private String ip;
	private String customerInfoType;
	private String receiveGrade;
	private String projectName;
	private String projectCode;
	private String function;
	private String writerDept;
	private String writerTeam;
	private String companyPosition;
	private String jobclass;
	private String customerName;
	private String customerCode;
	private String industryTypeCode;
	private String businessTypeCode;
	private String[] businessTypeCodes;
	private String pmSsn;
	private String from;
	private String to;
	private String isOrg;
	private String valueType;
	private String comcode;
	private String refer_dept;
	
	private String[] picker_ssn; 

	
	

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getMaster_idx() {
		return master_idx;
	}

	public void setMaster_idx(String master_idx) {
		this.master_idx = master_idx;
	}

	public String getCom_idx() {
		return com_idx;
	}

	public void setCom_idx(String com_idx) {
		this.com_idx = com_idx;
	}

	public String getEmbbs_idx() {
		return embbs_idx;
	}

	public void setEmbbs_idx(String embbs_idx) {
		this.embbs_idx = embbs_idx;
	}

	public String getEmbbsName() {
		return embbsName;
	}

	public void setEmbbsName(String embbsName) {
		this.embbsName = embbsName;
	}

	public String getEmbbsCompany() {
		return embbsCompany;
	}

	public void setEmbbsCompany(String embbsCompany) {
		this.embbsCompany = embbsCompany;
	}

	public String getEmbbsDept() {
		return embbsDept;
	}

	public void setEmbbsDept(String embbsDept) {
		this.embbsDept = embbsDept;
	}

	public String getEmbbsStatus() {
		return embbsStatus;
	}

	public void setEmbbsStatus(String embbsStatus) {
		this.embbsStatus = embbsStatus;
	}

	public String getEmbbsPhone() {
		return embbsPhone;
	}

	public void setEmbbsPhone(String embbsPhone) {
		this.embbsPhone = embbsPhone;
	}

	public String getEmbbsEmail() {
		return embbsEmail;
	}

	public void setEmbbsEmail(String embbsEmail) {
		this.embbsEmail = embbsEmail;
	}

	public String getEmbbsGather() {
		return embbsGather;
	}

	public void setEmbbsGather(String embbsGather) {
		this.embbsGather = embbsGather;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriterSsn() {
		return writerSsn;
	}

	public void setWriterSsn(String writerSsn) {
		this.writerSsn = writerSsn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getInfo_date() {
		return info_date;
	}

	public void setInfo_date(String info_date) {
		this.info_date = info_date;
	}

	public String getInfo_dateYyyy() {
		return info_dateYyyy;
	}

	public void setInfo_dateYyyy(String info_dateYyyy) {
		this.info_dateYyyy = info_dateYyyy;
	}

	public String getInfo_dateMm() {
		return info_dateMm;
	}

	public void setInfo_dateMm(String info_dateMm) {
		this.info_dateMm = info_dateMm;
	}

	public String getInfo_dateDd() {
		return info_dateDd;
	}

	public void setInfo_dateDd(String info_dateDd) {
		this.info_dateDd = info_dateDd;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getSanupgubun() {
		return sanupgubun;
	}

	public void setSanupgubun(String sanupgubun) {
		this.sanupgubun = sanupgubun;
	}

	public String getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(String readCnt) {
		this.readCnt = readCnt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomerInfoType() {
		return customerInfoType;
	}

	public void setCustomerInfoType(String customerInfoType) {
		this.customerInfoType = customerInfoType;
	}

	public String getReceiveGrade() {
		return receiveGrade;
	}

	public void setReceiveGrade(String receiveGrade) {
		this.receiveGrade = receiveGrade;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getWriterDept() {
		return writerDept;
	}

	public void setWriterDept(String writerDept) {
		this.writerDept = writerDept;
	}

	public String getWriterTeam() {
		return writerTeam;
	}

	public void setWriterTeam(String writerTeam) {
		this.writerTeam = writerTeam;
	}

	public String getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	public String getJobclass() {
		return jobclass;
	}

	public void setJobclass(String jobclass) {
		this.jobclass = jobclass;
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

	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	public String[] getBusinessTypeCodes() {
		return businessTypeCodes;
	}

	public void setBusinessTypeCodes(String[] businessTypeCodes) {
		this.businessTypeCodes = businessTypeCodes;
	}

	public String getBusinessTypeCodeList() {
		String res = "";
		if (this.getBusinessTypeCodes() != null && this.getBusinessTypeCodes().length > 0) {
			String[] values = this.getBusinessTypeCodes();
			for (String val : values) {
				res += val + "|";
			}
			res = res.substring(0, res.length() - 1);
		}
		return res;
	}

	public String getPmSsn() {
		return pmSsn;
	}

	public void setPmSsn(String pmSsn) {
		this.pmSsn = pmSsn;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getIsOrg() {
		return isOrg;
	}

	public void setIsOrg(String isOrg) {
		this.isOrg = isOrg;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getRefer_dept() {
		return refer_dept;
	}

	public void setRefer_dept(String refer_dept) {
		this.refer_dept = refer_dept;
	}

	/**
	 * @return the embbsMethod
	 */
	public String getEmbbsMethod() {
		return embbsMethod;
	}

	/**
	 * @param embbsMethod
	 *            the embbsMethod to set
	 */
	public void setEmbbsMethod(String embbsMethod) {
		this.embbsMethod = embbsMethod;
	}

	/**
	 * @return the picker_ssn
	 */
	public String[] getPicker_ssn() {
		return picker_ssn;
	}

	/**
	 * @param picker_ssn the picker_ssn to set
	 */
	public void setPicker_ssn(String[] picker_ssn) {
		this.picker_ssn = picker_ssn;
	}

	
	

}
