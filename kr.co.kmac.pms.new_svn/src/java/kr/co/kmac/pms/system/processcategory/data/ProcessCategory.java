/*
 * $Id: ProcessCategory.java,v 1.2 2013/12/31 05:48:30 cvs Exp $
 * created by    : 안성호
 * creation-date : 2006. 4. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.processcategory.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 프로세스 분류
 * 
 * @author Jiwoong Lee
 * 
 */
public class ProcessCategory {
	private String processCategoryCode;
	private String processCategoryName;
	private String businessTypeCode;
	private String businessTypeName;
	private String runningDivCode;
	private String runningDivName;
	private String projectType;
	private String projectTypeName;
	private String processTemplateCode;
	private String processTemplateName;
	private String businessFunctionType;
	private String businessFunctionName;
	private String tableName;

	/**
	 * @return the processCategoryCode
	 */
	public String getprocessCategoryCode() {
		return processCategoryCode;
	}

	/**
	 * @param processCategoryCode the processCategoryCode to set
	 */
	public void setprocessCategoryCode(String processCategoryCode) {
		this.processCategoryCode = processCategoryCode;
	}

	/**
	 * @return the processCategoryName
	 */
	public String getProcessCategoryName() {
		return processCategoryName;
	}

	/**
	 * @param processCategoryName the processCategoryName to set
	 */
	public void setProcessCategoryName(String processCategoryName) {
		this.processCategoryName = processCategoryName;
	}

	/**
	 * @return the businessTypeCode
	 */
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	/**
	 * @param businessTypeCode the businessTypeCode to set
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	/**
	 * @param businessTypeName the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return the runningDivCode
	 */
	public String getRunningDivCode() {
		return runningDivCode;
	}

	/**
	 * @param runningDivCode the runningDivCode to set
	 */
	public void setRunningDivCode(String runningDivCode) {
		this.runningDivCode = runningDivCode;
	}

	/**
	 * @return the runningDivName
	 */
	public String getRunningDivName() {
		return runningDivName;
	}

	/**
	 * @param runningDivName the runningDivName to set
	 */
	public void setRunningDivName(String runningDivName) {
		this.runningDivName = runningDivName;
	}

	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the projectTypeName
	 */
	public String getProjectTypeName() {
		return projectTypeName;
	}

	/**
	 * @param projectTypeName the projectTypeName to set
	 */
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	/**
	 * @return the processTemplateCode
	 */
	public String getProcessTemplateCode() {
		return processTemplateCode;
	}

	/**
	 * @param processTemplateCode the processTemplateCode to set
	 */
	public void setProcessTemplateCode(String processTemplateCode) {
		this.processTemplateCode = processTemplateCode;
	}

	/**
	 * @return the processTemplateName
	 */
	public String getProcessTemplateName() {
		return processTemplateName;
	}

	/**
	 * @param processTemplateName the processTemplateName to set
	 */
	public void setProcessTemplateName(String processTemplateName) {
		this.processTemplateName = processTemplateName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		this.tableName = "PROCESS_PRODUCT_CODE";
		return this.tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the businessFunctionType
	 */
	public String getBusinessFunctionType() {
		return businessFunctionType;
	}

	/**
	 * @param businessFunctionType the businessFunctionType to set
	 */
	public void setBusinessFunctionType(String businessFunctionType) {
		this.businessFunctionType = businessFunctionType;
	}

	/**
	 * @return the businessFunctionName
	 */
	public String getBusinessFunctionName() {
		return businessFunctionName;
	}

	/**
	 * @param businessFunctionName the businessFunctionName to set
	 */
	public void setBusinessFunctionName(String businessFunctionName) {
		this.businessFunctionName = businessFunctionName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
