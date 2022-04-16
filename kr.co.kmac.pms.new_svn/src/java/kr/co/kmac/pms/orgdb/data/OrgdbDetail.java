/*
 * $Id: OrgdbDetail.java,v 1.1 2009/09/19 11:15:30 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : May 20, 2008
 * =========================================================
 * Copyright (c) 2008 ManInSoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.orgdb.data;

import java.util.List;

public class OrgdbDetail{
	
	
	private String orgCode;
	private String orgName;
	private String pmName;
	private String oldName;
	private String nationality;
	private String businessType;
	private String relWithkmac1;
	private String relWithkmac2;
	private String relWithkmac3;
	private String relWithkmac4;
	private String specialField1;
	private String specialField2;
	private String specialField3;
	private String specialField4;
	private String repName;
	private String businessCatogory;
	private String salesAmount;
	private String estYear;
	private String telNo;
	private String employeeCnt;
	private String homepage;
	private String faxNo;
	private String zipCode;
	private String address1;
	private String address2;
	private String workComment;
	private List contactList;
	private String createDate;
	private String creator;
	private String modifyDate;
	private String modifier;
	/**
	 * @return Returns the orgCode.
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode The orgCode to set.
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return Returns the orgName.
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName The orgName to set.
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return Returns the pmName.
	 */
	public String getPmName() {
		return pmName;
	}
	/**
	 * @param pmName The pmName to set.
	 */
	public void setPmName(String pmName) {
		this.pmName = pmName;
	}
	/**
	 * @return Returns the oldName.
	 */
	public String getOldName() {
		return oldName;
	}
	/**
	 * @param oldName The oldName to set.
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	/**
	 * @return Returns the nationality.
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality The nationality to set.
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return Returns the businessType.
	 */
	public String getBusinessType() {
		return businessType;
	}
	public String[] getBusinessTypeArray() {
		if (businessType == null)
			return null;
		else
			return businessType.split("[|]");
	}
	/**
	 * @param businessType The businessType to set.
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * @return Returns the relWithkmac1.
	 */
	public String getRelWithkmac1() {
		return relWithkmac1;
	}
	/**
	 * @param relWithkmac1 The relWithkmac1 to set.
	 */
	public void setRelWithkmac1(String relWithkmac1) {
		this.relWithkmac1 = relWithkmac1;
	}
	/**
	 * @return Returns the relWithkmac2.
	 */
	public String getRelWithkmac2() {
		return relWithkmac2;
	}
	/**
	 * @param relWithkmac2 The relWithkmac2 to set.
	 */
	public void setRelWithkmac2(String relWithkmac2) {
		this.relWithkmac2 = relWithkmac2;
	}
	/**
	 * @return Returns the relWithkmac3.
	 */
	public String getRelWithkmac3() {
		return relWithkmac3;
	}
	/**
	 * @param relWithkmac3 The relWithkmac3 to set.
	 */
	public void setRelWithkmac3(String relWithkmac3) {
		this.relWithkmac3 = relWithkmac3;
	}
	/**
	 * @return Returns the relWithkmac4.
	 */
	public String getRelWithkmac4() {
		return relWithkmac4;
	}
	/**
	 * @param relWithkmac4 The relWithkmac4 to set.
	 */
	public void setRelWithkmac4(String relWithkmac4) {
		this.relWithkmac4 = relWithkmac4;
	}
	/**
	 * @return Returns the specialField1.
	 */
	public String getSpecialField1() {
		return specialField1;
	}
	/**
	 * @param specialField1 The specialField1 to set.
	 */
	public void setSpecialField1(String specialField1) {
		this.specialField1 = specialField1;
	}
	/**
	 * @return Returns the specialField2.
	 */
	public String getSpecialField2() {
		return specialField2;
	}
	/**
	 * @param specialField2 The specialField2 to set.
	 */
	public void setSpecialField2(String specialField2) {
		this.specialField2 = specialField2;
	}
	/**
	 * @return Returns the specialField3.
	 */
	public String getSpecialField3() {
		return specialField3;
	}
	/**
	 * @param specialField3 The specialField3 to set.
	 */
	public void setSpecialField3(String specialField3) {
		this.specialField3 = specialField3;
	}
	/**
	 * @return Returns the specialField4.
	 */
	public String getSpecialField4() {
		return specialField4;
	}
	/**
	 * @param specialField4 The specialField4 to set.
	 */
	public void setSpecialField4(String specialField4) {
		this.specialField4 = specialField4;
	}
	/**
	 * @return Returns the repName.
	 */
	public String getRepName() {
		return repName;
	}
	/**
	 * @param repName The repName to set.
	 */
	public void setRepName(String repName) {
		this.repName = repName;
	}
	/**
	 * @return Returns the businessCatogory.
	 */
	public String getBusinessCatogory() {
		return businessCatogory;
	}
	/**
	 * @param businessCatogory The businessCatogory to set.
	 */
	public void setBusinessCatogory(String businessCatogory) {
		this.businessCatogory = businessCatogory;
	}
	/**
	 * @return Returns the salesAmount.
	 */
	public String getSalesAmount() {
		return salesAmount;
	}
	/**
	 * @param salesAmount The salesAmount to set.
	 */
	public void setSalesAmount(String salesAmount) {
		this.salesAmount = salesAmount;
	}
	/**
	 * @return Returns the estYear.
	 */
	public String getEstYear() {
		return estYear;
	}
	/**
	 * @param estYear The estYear to set.
	 */
	public void setEstYear(String estYear) {
		this.estYear = estYear;
	}
	/**
	 * @return Returns the telNo.
	 */
	public String getTelNo() {
		return telNo;
	}
	/**
	 * @param telNo The telNo to set.
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	/**
	 * @return Returns the employeeCnt.
	 */
	public String getEmployeeCnt() {
		return employeeCnt;
	}
	/**
	 * @param employeeCnt The employeeCnt to set.
	 */
	public void setEmployeeCnt(String employeeCnt) {
		this.employeeCnt = employeeCnt;
	}
	/**
	 * @return Returns the homepage.
	 */
	public String getHomepage() {
		return homepage;
	}
	/**
	 * @param homepage The homepage to set.
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	/**
	 * @return Returns the faxNo.
	 */
	public String getFaxNo() {
		return faxNo;
	}
	/**
	 * @param faxNo The faxNo to set.
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	/**
	 * @return Returns the zipCode.
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode The zipCode to set.
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return Returns the workComment.
	 */
	public String getWorkComment() {
		return workComment;
	}
	/**
	 * @param workComment The workComment to set.
	 */
	public void setWorkComment(String workComment) {
		this.workComment = workComment;
	}
	/**
	 * @return Returns the contactList.
	 */
	public List getContactList() {
		return contactList;
	}
	/**
	 * @param contactList The contactList to set.
	 */
	public void setContactList(List contactList) {
		this.contactList = contactList;
	}
	/**
	 * @return Returns the createDate.
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the creator.
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator The creator to set.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return Returns the modifyDate.
	 */
	public String getModifyDate() {
		return modifyDate;
	}
	/**
	 * @param modifyDate The modifyDate to set.
	 */
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * @return Returns the modifier.
	 */
	public String getModifier() {
		return modifier;
	}
	/**
	 * @param modifier The modifier to set.
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

}
