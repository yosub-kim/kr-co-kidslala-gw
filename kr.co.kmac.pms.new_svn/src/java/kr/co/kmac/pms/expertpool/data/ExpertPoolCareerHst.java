/*
 * $Id: ExpertPoolCareerHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 27.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

/**
 * 전문가 경력 모델
 * @author jiwoongLee
 * @version $Id: ExpertPoolCareerHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class ExpertPoolCareerHst {

	private String ssn;
	private String seqNo;
	private String term;
	private String companyName;
	private String workingDept;
	private String companyPosition;
	private String workingDetail;
	private String remark;
	private String createdDate;
	private String createrId;
	private String modifiedDate;
	private String modifierId;

	/**
	 * @return Returns the companyName.
	 */
	public String getCompanyName() {
		return this.companyName;
	}
	/**
	 * @param companyName The companyName to set.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return Returns the createdDate.
	 */
	public String getCreatedDate() {
		return this.createdDate;
	}
	/**
	 * @param createdDate The createdDate to set.
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return Returns the createrId.
	 */
	public String getCreaterId() {
		return this.createrId;
	}
	/**
	 * @param createrId The createrId to set.
	 */
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return this.modifiedDate;
	}
	/**
	 * @param modifiedDate The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return Returns the modifierId.
	 */
	public String getModifierId() {
		return this.modifierId;
	}
	/**
	 * @param modifierId The modifierId to set.
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}
	/**
	 * @return Returns the companyPosition.
	 */
	public String getCompanyPosition() {
		return this.companyPosition;
	}
	/**
	 * @param companyPosition The companyPosition to set.
	 */
	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return this.remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return this.seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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
	 * @return Returns the term.
	 */
	public String getTerm() {
		return this.term;
	}
	/**
	 * @param term The term to set.
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	/**
	 * @return Returns the workingDept.
	 */
	public String getWorkingDept() {
		return this.workingDept;
	}
	/**
	 * @param workingDept The workingDept to set.
	 */
	public void setWorkingDept(String workingDept) {
		this.workingDept = workingDept;
	}
	/**
	 * @return Returns the workingDetail.
	 */
	public String getWorkingDetail() {
		return this.workingDetail;
	}
	/**
	 * @param workingDetail The workingDetail to set.
	 */
	public void setWorkingDetail(String workingDetail) {
		this.workingDetail = workingDetail;
	}

}
