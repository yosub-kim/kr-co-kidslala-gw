/*
 * $Id: ExpertPoolForm.java,v 1.6 2018/11/05 20:37:41 cvs Exp $ created by :
 * Administrator creation-date : 2006. 1. 17
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.form;

import java.util.ArrayList;
import java.util.List;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.expertpool.data.ExpertPool;
import kr.co.kmac.pms.expertpool.data.ExpertPoolCareerHst;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSchoolHst;
import kr.co.kmac.pms.expertpool.data.ExpertPoolSpecialField;

import org.apache.struts.action.ActionForm;

/**
 * TODO 클래스 설명
 * 
 * @author Administrator
 * @version $Id: ExpertPoolForm.java,v 1.6 2018/11/05 20:37:41 cvs Exp $
 */
/**
 * @struts:form name="expertPoolManagerAction"
 */
public class ExpertPoolForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766929L;

	private String ssn;
	private String uid;
	private String name;
	private String chnName;
	private String engName;
	private String password;
	private String enable;
	private String absence;
	private String gender;
	private String nationality;
	private String telNo;
	private String mobileNo;
	private String zipCode;
	private String address1;
	private String address2;
	private String companyId;
	private String company;
	private String Dept;
	private String deptName;
	private String companyPosition;
	private String companyPositionName;
	private String jobClass;
	private String companyZipcode;
	private String companyAddress1;
	private String companyAddress2;
	private String companyTelNo;
	private String companyFaxNo;
	private String email;
	private String photo;

	private String indField;
	private String bizField;
	private String funcField;
	private String consultingMajor;
	private String consultingMinor;
	private String consultingDetail;
	private String researchEassy;
	private String publishedBook;

	private String finalMajor;
	private String essayTitle;
	private String schoolFinalMajor;
	private String langSkill1;
	private String langSkill1Level;
	private String langSkill2;
	private String langSkill2Level;
	private String langSkill3;
	private String langSkill3Level;

	// 이력서
	private String modifiedDate;
	private String modifierId;
	private String resume;

	private String remark;

	private String rate;

	private String identify;
	
	private String salaryMailingYN;

	private String[] careerSeq;
	private String[] careerTerm;
	private String[] careercompanyName;
	private String[] careerWorkingDept;
	private String[] careerPosition;
	private String[] careerWorkingDetail;
	private String[] careerRemark;

	private String[] schoolSeq;
	private String[] schoolTerm;
	private String[] schoolName;
	private String[] schoolMajor;
	private String[] schoolGraduationState;
	private String[] schoolGpa;
	private String[] schoolLocation;
	private String[] schoolRemark;

	private String[] specialId;
	private String[] specialName;

	public String[] getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String[] specialId) {
		this.specialId = specialId;
	}

	public String[] getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String[] specialName) {
		this.specialName = specialName;
	}

	/**
	 * @return Returns the ssn.
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}	

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the chnName
	 */
	public String getChnName() {
		return chnName;
	}

	/**
	 * @param chnName the chnName to set
	 */
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	/**
	 * @return the engName
	 */
	public String getEngName() {
		return engName;
	}

	/**
	 * @param engName the engName to set
	 */
	public void setEngName(String engName) {
		this.engName = engName;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the enable.
	 */
	public String getEnable() {
		return enable;
	}

	/**
	 * @param enable The enable to set.
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}

	/**
	 * @return Returns the absence.
	 */
	public String getAbsence() {
		return absence;
	}

	/**
	 * @param absence The absence to set.
	 */
	public void setAbsence(String absence) {
		this.absence = absence;
	}

	/**
	 * @return Returns the gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender The gender to set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
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
	 * @return Returns the mobileNo.
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo The mobileNo to set.
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
	 * @return Returns the companyId.
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId The companyId to set.
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return Returns the company.
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company The company to set.
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return Returns the dept.
	 */
	public String getDept() {
		return Dept;
	}

	/**
	 * @param dept The dept to set.
	 */
	public void setDept(String dept) {
		Dept = dept;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return Returns the companyPosition.
	 */
	public String getCompanyPosition() {
		return companyPosition;
	}

	/**
	 * @param companyPosition The companyPosition to set.
	 */
	public void setCompanyPosition(String companyPosition) {
		this.companyPosition = companyPosition;
	}

	public String getCompanyPositionName() {
		return companyPositionName;
	}

	public void setCompanyPositionName(String companyPositionName) {
		this.companyPositionName = companyPositionName;
	}

	/**
	 * @return Returns the jobClass.
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass The jobClass to set.
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return Returns the companyZipcode.
	 */
	public String getCompanyZipcode() {
		return companyZipcode;
	}

	/**
	 * @param companyZipcode The companyZipcode to set.
	 */
	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}

	/**
	 * @return Returns the companyAddress1.
	 */
	public String getCompanyAddress1() {
		return companyAddress1;
	}

	/**
	 * @param companyAddress1 The companyAddress1 to set.
	 */
	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	/**
	 * @return Returns the companyAddress2.
	 */
	public String getCompanyAddress2() {
		return companyAddress2;
	}

	/**
	 * @param companyAddress2 The companyAddress2 to set.
	 */
	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	/**
	 * @return Returns the companyTelNo.
	 */
	public String getCompanyTelNo() {
		return companyTelNo;
	}

	/**
	 * @param companyTelNo The companyTelNo to set.
	 */
	public void setCompanyTelNo(String companyTelNo) {
		this.companyTelNo = companyTelNo;
	}

	/**
	 * @return Returns the companyFaxNo.
	 */
	public String getCompanyFaxNo() {
		return companyFaxNo;
	}

	/**
	 * @param companyFaxNo The companyFaxNo to set.
	 */
	public void setCompanyFaxNo(String companyFaxNo) {
		this.companyFaxNo = companyFaxNo;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the photo.
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo The photo to set.
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return Returns the indField.
	 */
	public String getIndField() {
		return indField;
	}

	/**
	 * @param indField The indField to set.
	 */
	public void setIndField(String indField) {
		this.indField = indField;
	}

	/**
	 * @return Returns the bizField.
	 */
	public String getBizField() {
		return bizField;
	}

	/**
	 * @param bizField The bizField to set.
	 */
	public void setBizField(String bizField) {
		this.bizField = bizField;
	}

	/**
	 * @return Returns the funcField.
	 */
	public String getFuncField() {
		return funcField;
	}

	/**
	 * @param funcField The funcField to set.
	 */
	public void setFuncField(String funcField) {
		this.funcField = funcField;
	}

	/**
	 * @return Returns the consultingMajor.
	 */
	public String getConsultingMajor() {
		return consultingMajor;
	}

	/**
	 * @param consultingMajor The consultingMajor to set.
	 */
	public void setConsultingMajor(String consultingMajor) {
		this.consultingMajor = consultingMajor;
	}

	/**
	 * @return Returns the consultingMinor.
	 */
	public String getConsultingMinor() {
		return consultingMinor;
	}

	/**
	 * @param consultingMinor The consultingMinor to set.
	 */
	public void setConsultingMinor(String consultingMinor) {
		this.consultingMinor = consultingMinor;
	}

	/**
	 * @return Returns the consultingDetail.
	 */
	public String getConsultingDetail() {
		return consultingDetail;
	}

	/**
	 * @param consultingDetail The consultingDetail to set.
	 */
	public void setConsultingDetail(String consultingDetail) {
		this.consultingDetail = consultingDetail;
	}

	/**
	 * @return Returns the researchEassy.
	 */
	public String getResearchEassy() {
		return researchEassy;
	}

	/**
	 * @param researchEassy The researchEassy to set.
	 */
	public void setResearchEassy(String researchEassy) {
		this.researchEassy = researchEassy;
	}

	/**
	 * @return Returns the publishedBook.
	 */
	public String getPublishedBook() {
		return publishedBook;
	}

	/**
	 * @param publishedBook The publishedBook to set.
	 */
	public void setPublishedBook(String publishedBook) {
		this.publishedBook = publishedBook;
	}

	/**
	 * @return Returns the finalMajor.
	 */
	public String getFinalMajor() {
		return finalMajor;
	}

	/**
	 * @param finalMajor The finalMajor to set.
	 */
	public void setFinalMajor(String finalMajor) {
		this.finalMajor = finalMajor;
	}

	/**
	 * @return Returns the essayTitle.
	 */
	public String getEssayTitle() {
		return essayTitle;
	}

	/**
	 * @param essayTitle The essayTitle to set.
	 */
	public void setEssayTitle(String essayTitle) {
		this.essayTitle = essayTitle;
	}

	/**
	 * @return Returns the schoolFinalMajor.
	 */
	public String getSchoolFinalMajor() {
		return schoolFinalMajor;
	}

	/**
	 * @param schoolFinalMajor The schoolFinalMajor to set.
	 */
	public void setSchoolFinalMajor(String schoolFinalMajor) {
		this.schoolFinalMajor = schoolFinalMajor;
	}

	/**
	 * @return Returns the langSkill1.
	 */
	public String getLangSkill1() {
		return langSkill1;
	}

	/**
	 * @param langSkill1 The langSkill1 to set.
	 */
	public void setLangSkill1(String langSkill1) {
		this.langSkill1 = langSkill1;
	}

	/**
	 * @return Returns the langSkill1Level.
	 */
	public String getLangSkill1Level() {
		return langSkill1Level;
	}

	/**
	 * @param langSkill1Level The langSkill1Level to set.
	 */
	public void setLangSkill1Level(String langSkill1Level) {
		this.langSkill1Level = langSkill1Level;
	}

	/**
	 * @return Returns the langSkill2.
	 */
	public String getLangSkill2() {
		return langSkill2;
	}

	/**
	 * @param langSkill2 The langSkill2 to set.
	 */
	public void setLangSkill2(String langSkill2) {
		this.langSkill2 = langSkill2;
	}

	/**
	 * @return Returns the langSkill2Level.
	 */
	public String getLangSkill2Level() {
		return langSkill2Level;
	}

	/**
	 * @param langSkill2Level The langSkill2Level to set.
	 */
	public void setLangSkill2Level(String langSkill2Level) {
		this.langSkill2Level = langSkill2Level;
	}

	/**
	 * @return Returns the langSkill3.
	 */
	public String getLangSkill3() {
		return langSkill3;
	}

	/**
	 * @param langSkill3 The langSkill3 to set.
	 */
	public void setLangSkill3(String langSkill3) {
		this.langSkill3 = langSkill3;
	}

	/**
	 * @return Returns the langSkill3Level.
	 */
	public String getLangSkill3Level() {
		return langSkill3Level;
	}

	/**
	 * @param langSkill3Level The langSkill3Level to set.
	 */
	public void setLangSkill3Level(String langSkill3Level) {
		this.langSkill3Level = langSkill3Level;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the rate.
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the identify.
	 */
	public String getIdentify() {
		return identify;
	}

	/**
	 * @param identify The identify to set.
	 */
	public void setIdentify(String identify) {
		this.identify = identify;
	}

	/**
	 * @return the salaryMailingYN
	 */
	public String getSalaryMailingYN() {
		return salaryMailingYN;
	}

	/**
	 * @param salaryMailing the salaryMailing to set
	 */
	public void setSalaryMailingYN(String salaryMailingYN) {
		this.salaryMailingYN = salaryMailingYN;
	}

	/**
	 * @param modifiedDate the modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the modifierId.
	 */
	public String getModifierId() {
		return modifierId;
	}

	/**
	 * @param modifiedDate the modifiedDate to set.
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * @return Returns the resume.
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * @param resume the resume to set.
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}

	/**
	 * @return Returns the careerSeq.
	 */
	public String[] getCareerSeq() {
		return careerSeq;
	}

	/**
	 * @param careerSeq The careerSeq to set.
	 */
	public void setCareerSeq(String[] careerSeq) {
		this.careerSeq = careerSeq;
	}

	/**
	 * @return Returns the careerTerm.
	 */
	public String[] getCareerTerm() {
		return careerTerm;
	}

	/**
	 * @param careerTerm The careerTerm to set.
	 */
	public void setCareerTerm(String[] careerTerm) {
		this.careerTerm = careerTerm;
	}

	/**
	 * @return Returns the careercompanyName.
	 */
	public String[] getCareercompanyName() {
		return careercompanyName;
	}

	/**
	 * @param careercompanyName The careercompanyName to set.
	 */
	public void setCareercompanyName(String[] careercompanyName) {
		this.careercompanyName = careercompanyName;
	}

	/**
	 * @return Returns the careerWorkingDept.
	 */
	public String[] getCareerWorkingDept() {
		return careerWorkingDept;
	}

	/**
	 * @param careerWorkingDept The careerWorkingDept to set.
	 */
	public void setCareerWorkingDept(String[] careerWorkingDept) {
		this.careerWorkingDept = careerWorkingDept;
	}

	/**
	 * @return Returns the careerPosition.
	 */
	public String[] getCareerPosition() {
		return careerPosition;
	}

	/**
	 * @param careerPosition The careerPosition to set.
	 */
	public void setCareerPosition(String[] careerPosition) {
		this.careerPosition = careerPosition;
	}

	/**
	 * @return Returns the careerWorkingDetail.
	 */
	public String[] getCareerWorkingDetail() {
		return careerWorkingDetail;
	}

	/**
	 * @param careerWorkingDetail The careerWorkingDetail to set.
	 */
	public void setCareerWorkingDetail(String[] careerWorkingDetail) {
		this.careerWorkingDetail = careerWorkingDetail;
	}

	/**
	 * @return Returns the careerRemark.
	 */
	public String[] getCareerRemark() {
		return careerRemark;
	}

	/**
	 * @param careerRemark The careerRemark to set.
	 */
	public void setCareerRemark(String[] careerRemark) {
		this.careerRemark = careerRemark;
	}

	/**
	 * @return Returns the schoolSeq.
	 */
	public String[] getSchoolSeq() {
		return schoolSeq;
	}

	/**
	 * @param schoolSeq The schoolSeq to set.
	 */
	public void setSchoolSeq(String[] schoolSeq) {
		this.schoolSeq = schoolSeq;
	}

	/**
	 * @return Returns the schoolTerm.
	 */
	public String[] getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 * @param schoolTerm The schoolTerm to set.
	 */
	public void setSchoolTerm(String[] schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 * @return Returns the schoolName.
	 */
	public String[] getSchoolName() {
		return schoolName;
	}

	/**
	 * @param schoolName The schoolName to set.
	 */
	public void setSchoolName(String[] schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * @return Returns the schoolMajor.
	 */
	public String[] getSchoolMajor() {
		return schoolMajor;
	}

	/**
	 * @param schoolMajor The schoolMajor to set.
	 */
	public void setSchoolMajor(String[] schoolMajor) {
		this.schoolMajor = schoolMajor;
	}

	/**
	 * @return Returns the schoolGraduationState.
	 */
	public String[] getSchoolGraduationState() {
		return schoolGraduationState;
	}

	/**
	 * @param schoolGraduationState The schoolGraduationState to set.
	 */
	public void setSchoolGraduationState(String[] schoolGraduationState) {
		this.schoolGraduationState = schoolGraduationState;
	}

	/**
	 * @return Returns the schoolGpa.
	 */
	public String[] getSchoolGpa() {
		return schoolGpa;
	}

	/**
	 * @param schoolGpa The schoolGpa to set.
	 */
	public void setSchoolGpa(String[] schoolGpa) {
		this.schoolGpa = schoolGpa;
	}

	/**
	 * @return Returns the schoolLocation.
	 */
	public String[] getSchoolLocation() {
		return schoolLocation;
	}

	/**
	 * @param schoolLocation The schoolLocation to set.
	 */
	public void setSchoolLocation(String[] schoolLocation) {
		this.schoolLocation = schoolLocation;
	}

	/**
	 * @return Returns the schoolRemark.
	 */
	public String[] getSchoolRemark() {
		return schoolRemark;
	}

	/**
	 * @param schoolRemark The schoolRemark to set.
	 */
	public void setSchoolRemark(String[] schoolRemark) {
		this.schoolRemark = schoolRemark;
	}

	public static ExpertPool valueOf(ExpertPool expertPool, ExpertPoolForm form) {

		expertPool.setSsn(form.getSsn());
		expertPool.setUid(form.getUid());
		expertPool.setName(form.getName());
		String identify = StringUtil.isNull(form.getIdentify(), "");
		if (!"".equals(identify)) {
			expertPool.setUserId(identify);
		}
		String passWord = StringUtil.isNull(form.getPassword(), "");
		if (!"".equals(passWord)) {
			expertPool.setPassword(passWord);
		}
		expertPool.setGender(form.getGender());
		expertPool.setNationality(form.getNationality());
		expertPool.setTelNo(form.getTelNo());
		expertPool.setMobileNo(form.getMobileNo());
		expertPool.setZipCode(form.getZipCode());
		expertPool.setAddress1(form.getAddress1());
		expertPool.setAddress2(form.getAddress2());
		expertPool.setCompanyId(form.getCompanyId());
		expertPool.setCompany(form.getCompany());
		expertPool.setDept(form.getDept());
		expertPool.setDeptName(StringUtil.replace(form.getDeptName(), " ", "")); // 부서명
																					// 공백
																					// 제거
		expertPool.setCompanyPosition(form.getCompanyPosition());
		expertPool.setCompanyPositionName(form.getCompanyPositionName());
		expertPool.setJobClass(form.getJobClass());
		expertPool.setCompanyZipcode(form.getCompanyZipcode());
		expertPool.setCompanyAddress1(form.getCompanyAddress1());
		expertPool.setCompanyAddress2(form.getCompanyAddress2());
		expertPool.setCompanyTelNo(form.getCompanyTelNo());
		expertPool.setCompanyFaxNo(form.getCompanyFaxNo());
		expertPool.setEmail(form.getEmail());
		expertPool.setPhoto(form.getPhoto());
		expertPool.setFinalMajor(form.getFinalMajor());
		expertPool.setEssayTitle(form.getEssayTitle());
		expertPool.setIndField(form.getIndField());
		expertPool.setBizField(form.getBizField());
		expertPool.setFuncField(form.getFuncField());
		expertPool.setConsultingMajor(form.getConsultingMajor());
		expertPool.setConsultingMinor(form.getConsultingMinor());
		expertPool.setConsultingDetail(form.getConsultingDetail());
		expertPool.setResearchEassy(form.getResearchEassy());
		expertPool.setPublishedBook(form.getPublishedBook());
		expertPool.setRemark(form.getRemark());
		expertPool.setRate(form.getRate());
		expertPool.setResume(form.getResume());
		expertPool.setSalaryMailingYN(form.getSalaryMailingYN());

		List<ExpertPoolCareerHst> careerHstList = new ArrayList<ExpertPoolCareerHst>();
		if (form.getCareerSeq() != null) {
			for (int i = 0; i < form.getCareerSeq().length; i++) {
				if (!form.getCareercompanyName()[i].equals("")) {
					ExpertPoolCareerHst careerHst = new ExpertPoolCareerHst();
					careerHst.setSsn(form.getSsn());
					careerHst.setSeqNo((i + 1) + "");
					careerHst.setTerm(form.getCareerTerm()[i]);
					careerHst.setCompanyName(form.getCareercompanyName()[i]);
					careerHst.setWorkingDept(form.getCareerWorkingDept()[i]);
					careerHst.setCompanyPosition(form.getCareerPosition()[i]);
					careerHst.setWorkingDetail(form.getCareerWorkingDetail()[i]);
					careerHst.setRemark(form.getCareerRemark()[i]);
					careerHstList.add(i, careerHst);
				}
			}
		}
		expertPool.setExpertPoolCareerHst(careerHstList);

		List<ExpertPoolSchoolHst> schoolHistList = new ArrayList<ExpertPoolSchoolHst>();
		if (form.getSchoolSeq() != null) {
			for (int i = 0; i < form.getSchoolSeq().length; i++) {
				if (!form.getSchoolName()[i].equals("")) {
					ExpertPoolSchoolHst expertPoolSchoolHst = new ExpertPoolSchoolHst();
					expertPoolSchoolHst.setSsn(form.getSsn());
					expertPoolSchoolHst.setSeq((i + 1) + "");
					expertPoolSchoolHst.setTerm(form.getSchoolTerm()[i]);
					expertPoolSchoolHst.setSchoolName(form.getSchoolName()[i]);
					expertPoolSchoolHst.setMajor(form.getSchoolMajor()[i]);
					expertPoolSchoolHst.setGraduationState(form.getSchoolGraduationState()[i]);
					// expertPoolSchoolHst.setGpa(form.getSchoolGpa()[i]);
					expertPoolSchoolHst.setGpa("");
					expertPoolSchoolHst.setLocation(form.getSchoolLocation()[i]);
					expertPoolSchoolHst.setRemark(form.getSchoolRemark()[i]);
					schoolHistList.add(i, expertPoolSchoolHst);
				}
			}
		}
		expertPool.setExpertPoolSchoolHst(schoolHistList);

		List<ExpertPoolSpecialField> specialFieldList = new ArrayList<ExpertPoolSpecialField>();
		if (form.getSpecialId() != null) {
			for (int i = 0; i < form.getSpecialId().length; i++) {
				if (!form.getSpecialId()[i].equals("")) {
					ExpertPoolSpecialField specialField = new ExpertPoolSpecialField();
					specialField.setSsn(form.getSsn());
					specialField.setSpecialId(form.getSpecialId()[i]);
					specialField.setSpecialName(form.getSpecialName()[i]);
					specialFieldList.add(i, specialField);
				}
			}
		}
		expertPool.setExpertPoolSpecialFields(specialFieldList);
		
		return expertPool;
	}
}