/*
 * $Id: JudgeEvalList.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 4. 13.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.statistics.data;

/**
 * TODO 클래스 설명
 * @author jiwoongLee
 * @version $Id: JudgeEvalList.java,v 1.1 2009/09/19 11:15:35 cvs3 Exp $
 */
public class JudgeEvalList {

	private String projectCode;
	private String promotionType;
	private String promotionName;
	private String year;
	private String judgeSsn;
	private String judgeName;
	private String judgeCompany;
	private String companyId;
	private String companyName;
	private String judgeRate;

	
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	
	/**
	 * @return Returns the judgeCompany.
	 */
	public String getJudgeCompany() {
		return this.judgeCompany;
	}
	/**
	 * @param judgeCompany The judgeCompany to set.
	 */
	public void setJudgeCompany(String judgeCompany) {
		this.judgeCompany = judgeCompany;
	}
	/**
	 * @return Returns the companyId.
	 */
	public String getCompanyId() {
		return this.companyId;
	}
	/**
	 * @param companyId The companyId to set.
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
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
	 * @return Returns the judgeName.
	 */
	public String getJudgeName() {
		return this.judgeName;
	}
	/**
	 * @param judgeName The judgeName to set.
	 */
	public void setJudgeName(String judgeName) {
		this.judgeName = judgeName;
	}
	/**
	 * @return Returns the judgeRate.
	 */
	public String getJudgeRate() {
		return this.judgeRate;
	}
	/**
	 * @param judgeRate The judgeRate to set.
	 */
	public void setJudgeRate(String judgeRate) {
		this.judgeRate = judgeRate;
	}
	/**
	 * @return Returns the judgeSsn.
	 */
	public String getJudgeSsn() {
		return this.judgeSsn;
	}
	/**
	 * @param judgeSsn The judgeSsn to set.
	 */
	public void setJudgeSsn(String judgeSsn) {
		this.judgeSsn = judgeSsn;
	}
	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return this.projectCode;
	}
	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * @return Returns the promotionType.
	 */
	public String getPromotionType() {
		return this.promotionType;
	}
	/**
	 * @param promotionType The promotionType to set.
	 */
	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear() {
		return this.year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}
}