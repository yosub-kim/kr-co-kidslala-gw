/*
 * $Id: ConnectCustomerForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.data;

import org.apache.struts.action.ActionForm;

/**
 * TODO Provide description for "CustomerForm"
 * @author halberd
 * @version $Id: ConnectCustomerForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 */
/**
 * @struts:form name="connectCustomerManagerAction"
 */
public class ConnectCustomerForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766929L;

	private String connProjectCode;
	private String connProjectName;
	private String connIndustryTypeCode;
	private String connBusinessTypeCode;
	private String connCustomerCode;
	private String connCustomerName;
	private String[] select;
	private String[] idx;
	/**
	 * @return Returns the connBusinessTypeCode.
	 */
	public String getConnBusinessTypeCode() {
		return connBusinessTypeCode;
	}
	/**
	 * @param connBusinessTypeCode The connBusinessTypeCode to set.
	 */
	public void setConnBusinessTypeCode(String connBusinessTypeCode) {
		this.connBusinessTypeCode = connBusinessTypeCode;
	}
	/**
	 * @return Returns the connCustomerCode.
	 */
	public String getConnCustomerCode() {
		return connCustomerCode;
	}
	/**
	 * @param connCustomerCode The connCustomerCode to set.
	 */
	public void setConnCustomerCode(String connCustomerCode) {
		this.connCustomerCode = connCustomerCode;
	}
	/**
	 * @return Returns the connCustomerName.
	 */
	public String getConnCustomerName() {
		return connCustomerName;
	}
	/**
	 * @param connCustomerName The connCustomerName to set.
	 */
	public void setConnCustomerName(String connCustomerName) {
		this.connCustomerName = connCustomerName;
	}
	/**
	 * @return Returns the connIndustryTypeCode.
	 */
	public String getConnIndustryTypeCode() {
		return connIndustryTypeCode;
	}
	/**
	 * @param connIndustryTypeCode The connIndustryTypeCode to set.
	 */
	public void setConnIndustryTypeCode(String connIndustryTypeCode) {
		this.connIndustryTypeCode = connIndustryTypeCode;
	}
	/**
	 * @return Returns the connProjectCode.
	 */
	public String getConnProjectCode() {
		return connProjectCode;
	}
	/**
	 * @param connProjectCode The connProjectCode to set.
	 */
	public void setConnProjectCode(String connProjectCode) {
		this.connProjectCode = connProjectCode;
	}
	/**
	 * @return Returns the connProjectName.
	 */
	public String getConnProjectName() {
		return connProjectName;
	}
	/**
	 * @param connProjectName The connProjectName to set.
	 */
	public void setConnProjectName(String connProjectName) {
		this.connProjectName = connProjectName;
	}
	/**
	 * @return Returns the idx.
	 */
	public String[] getIdx() {
		return idx;
	}
	/**
	 * @param idx The idx to set.
	 */
	public void setIdx(String[] idx) {
		this.idx = idx;
	}
	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return select;
	}
	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}
}
