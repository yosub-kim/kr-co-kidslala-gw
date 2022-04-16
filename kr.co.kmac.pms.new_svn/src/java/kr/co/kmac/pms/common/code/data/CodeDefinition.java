/*
 * $Id: CodeDefinition.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2005. 8. 2
 * =========================================================
 * Copyright (c) 2005 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 코드 그룹 정의 객체
 * 
 * @author jiwoongLee
 * @version $Id: CodeDefinition.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class CodeDefinition {

	private String tableName;
	private String tableDesc;
	private String key1Label;
	private String key2Label;
	private String key3Label;
	private String data1Label;
	private String data2Label;
	private String data3Label;
	private String data4Label;
	private String data5Label;
	private String data6Label;
	private String data7Label;
	private String data8Label;
	private String data9Label;
	private String data10Label;
	private String createUserId;
	private Date createDate;
	private String updatedUserId;
	private Date updatedDate;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the tableDesc
	 */
	public String getTableDesc() {
		return tableDesc;
	}

	/**
	 * @param tableDesc the tableDesc to set
	 */
	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	/**
	 * @return the key1Label
	 */
	public String getKey1Label() {
		return key1Label;
	}

	/**
	 * @param key1Label the key1Label to set
	 */
	public void setKey1Label(String key1Label) {
		this.key1Label = key1Label;
	}

	/**
	 * @return the key2Label
	 */
	public String getKey2Label() {
		return key2Label;
	}

	/**
	 * @param key2Label the key2Label to set
	 */
	public void setKey2Label(String key2Label) {
		this.key2Label = key2Label;
	}

	/**
	 * @return the key3Label
	 */
	public String getKey3Label() {
		return key3Label;
	}

	/**
	 * @param key3Label the key3Label to set
	 */
	public void setKey3Label(String key3Label) {
		this.key3Label = key3Label;
	}

	/**
	 * @return the data1Label
	 */
	public String getData1Label() {
		return data1Label;
	}

	/**
	 * @param data1Label the data1Label to set
	 */
	public void setData1Label(String data1Label) {
		this.data1Label = data1Label;
	}

	/**
	 * @return the data2Label
	 */
	public String getData2Label() {
		return data2Label;
	}

	/**
	 * @param data2Label the data2Label to set
	 */
	public void setData2Label(String data2Label) {
		this.data2Label = data2Label;
	}

	/**
	 * @return the data3Label
	 */
	public String getData3Label() {
		return data3Label;
	}

	/**
	 * @param data3Label the data3Label to set
	 */
	public void setData3Label(String data3Label) {
		this.data3Label = data3Label;
	}

	/**
	 * @return the data4Label
	 */
	public String getData4Label() {
		return data4Label;
	}

	/**
	 * @param data4Label the data4Label to set
	 */
	public void setData4Label(String data4Label) {
		this.data4Label = data4Label;
	}

	/**
	 * @return the data5Label
	 */
	public String getData5Label() {
		return data5Label;
	}

	/**
	 * @param data5Label the data5Label to set
	 */
	public void setData5Label(String data5Label) {
		this.data5Label = data5Label;
	}

	/**
	 * @return the data6Label
	 */
	public String getData6Label() {
		return data6Label;
	}

	/**
	 * @param data6Label the data6Label to set
	 */
	public void setData6Label(String data6Label) {
		this.data6Label = data6Label;
	}

	/**
	 * @return the data7Label
	 */
	public String getData7Label() {
		return data7Label;
	}

	/**
	 * @param data7Label the data7Label to set
	 */
	public void setData7Label(String data7Label) {
		this.data7Label = data7Label;
	}

	/**
	 * @return the data8Label
	 */
	public String getData8Label() {
		return data8Label;
	}

	/**
	 * @param data8Label the data8Label to set
	 */
	public void setData8Label(String data8Label) {
		this.data8Label = data8Label;
	}

	/**
	 * @return the data9Label
	 */
	public String getData9Label() {
		return data9Label;
	}

	/**
	 * @param data9Label the data9Label to set
	 */
	public void setData9Label(String data9Label) {
		this.data9Label = data9Label;
	}

	/**
	 * @return the data10Label
	 */
	public String getData10Label() {
		return data10Label;
	}

	/**
	 * @param data10Label the data10Label to set
	 */
	public void setData10Label(String data10Label) {
		this.data10Label = data10Label;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updatedUserId
	 */
	public String getUpdatedUserId() {
		return updatedUserId;
	}

	/**
	 * @param updatedUserId the updatedUserId to set
	 */
	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
