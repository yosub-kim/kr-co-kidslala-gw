/*
 * $Id: CustomerHistoryForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.data;

import org.apache.struts.action.ActionForm;

/**
 * TODO Provide description for "CustomerData"
 * @author halberd
 * @version $Id: CustomerHistoryForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 */
/**
 * @struts:form name="customerHistoryManagerAction"
 */
public class CustomerHistoryForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766929L;
	private String customerName;
	private String customerCode;
	private String customerCnt;
	private String projectCnt;
	private String promotionCnt;
	private String certCnt;

	/**
	 * @return Returns the certCnt.
	 */
	public String getCertCnt() {
		return this.certCnt;
	}
	/**
	 * @param certCnt The certCnt to set.
	 */
	public void setCertCnt(String certCnt) {
		this.certCnt = certCnt;
	}
	/**
	 * @return Returns the promotionCnt.
	 */
	public String getPromotionCnt() {
		return this.promotionCnt;
	}
	/**
	 * @param promotionCnt The promotionCnt to set.
	 */
	public void setPromotionCnt(String promotionCnt) {
		this.promotionCnt = promotionCnt;
	}
	/**
	 * @return Returns the customerCnt.
	 */
	public String getCustomerCnt() {
		return customerCnt;
	}
	/**
	 * @param customerCnt The customerCnt to set.
	 */
	public void setCustomerCnt(String customerCnt) {
		this.customerCnt = customerCnt;
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
	 * @return Returns the projectCnt.
	 */
	public String getProjectCnt() {
		return projectCnt;
	}
	/**
	 * @param projectCnt The projectCnt to set.
	 */
	public void setProjectCnt(String projectCnt) {
		this.projectCnt = projectCnt;
	}
}
