/*
 * $Id: ExpenseErpData.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : Aug 19, 2007
 * =========================================================
 * Copyright (c) 2007 ManInSoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExpenseErpData {
	private String projectCode;
	private String acctCode;
	private String planedAmount;;
	private String exeAmount;
	private String diffAmount;

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
	 * @return Returns the acctCode.
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @param acctCode The acctCode to set.
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	/**
	 * @return Returns the planedAmount.
	 */
	public String getPlanedAmount() {
		return planedAmount;
	}

	/**
	 * @param planedAmount The planedAmount to set.
	 */
	public void setPlanedAmount(String planedAmount) {
		this.planedAmount = planedAmount;
	}

	/**
	 * @return Returns the exeAmount.
	 */
	public String getExeAmount() {
		return exeAmount;
	}

	/**
	 * @param exeAmount The exeAmount to set.
	 */
	public void setExeAmount(String exeAmount) {
		this.exeAmount = exeAmount;
	}

	/**
	 * @return Returns the diffAmount.
	 */
	public String getDiffAmount() {
		return diffAmount;
	}

	/**
	 * @param diffAmount The diffAmount to set.
	 */
	public void setDiffAmount(String diffAmount) {
		this.diffAmount = diffAmount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
