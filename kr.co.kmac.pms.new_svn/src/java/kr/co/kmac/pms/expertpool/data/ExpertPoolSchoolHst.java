/*
 * $Id: ExpertPoolSchoolHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: ExpertPoolSchoolHst.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
public class ExpertPoolSchoolHst {

	// 출신 학교관련 정보
	private String ssn;
	private String seq;
	private String term;
	private String schoolName;
	private String major;
	private String graduationState;
	private String gpa;
	private String location;
	private String remark;
	/**
	 * @return Returns the gpa.
	 */
	public String getGpa() {
		return this.gpa;
	}
	/**
	 * @param gpa The gpa to set.
	 */
	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	/**
	 * @return Returns the graduationState.
	 */
	public String getGraduationState() {
		return this.graduationState;
	}
	/**
	 * @param graduationState The graduationState to set.
	 */
	public void setGraduationState(String graduationState) {
		this.graduationState = graduationState;
	}
	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return this.location;
	}
	/**
	 * @param location The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return Returns the major.
	 */
	public String getMajor() {
		return this.major;
	}
	/**
	 * @param major The major to set.
	 */
	public void setMajor(String major) {
		this.major = major;
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
	 * @return Returns the schoolName.
	 */
	public String getSchoolName() {
		return this.schoolName;
	}
	/**
	 * @param schoolName The schoolName to set.
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * @return Returns the seq.
	 */
	public String getSeq() {
		return this.seq;
	}
	/**
	 * @param seq The seq to set.
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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

}