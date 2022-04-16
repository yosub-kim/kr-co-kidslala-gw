/*
 * $Id: CodeEntity.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
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
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: CodeEntity.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class CodeEntity {

	private String tableName;
	private String key1;
	private String key2;
	private String key3;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private String data8;
	private String data9;
	private String data10;
	private String createUserId;
	private Date createDate;
	private String updateUserId;
	private Date updatedDate;
	private String orderInfo;

	public CodeEntity() {
		super();
	}

	public CodeEntity(String key1, String data1) {
		super();
		this.key1 = key1;
		this.data1 = data1;
	}

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
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}

	/**
	 * @param key1 the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}

	/**
	 * @return the key2
	 */
	public String getKey2() {
		return key2;
	}

	/**
	 * @param key2 the key2 to set
	 */
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	/**
	 * @return the key3
	 */
	public String getKey3() {
		return key3;
	}

	/**
	 * @param key3 the key3 to set
	 */
	public void setKey3(String key3) {
		this.key3 = key3;
	}

	/**
	 * @return the data1
	 */
	public String getData1() {
		return data1;
	}

	/**
	 * @param data1 the data1 to set
	 */
	public void setData1(String data1) {
		this.data1 = data1;
	}

	/**
	 * @return the data2
	 */
	public String getData2() {
		return data2;
	}

	/**
	 * @param data2 the data2 to set
	 */
	public void setData2(String data2) {
		this.data2 = data2;
	}

	/**
	 * @return the data3
	 */
	public String getData3() {
		return data3;
	}

	/**
	 * @param data3 the data3 to set
	 */
	public void setData3(String data3) {
		this.data3 = data3;
	}

	/**
	 * @return the data4
	 */
	public String getData4() {
		return data4;
	}

	/**
	 * @param data4 the data4 to set
	 */
	public void setData4(String data4) {
		this.data4 = data4;
	}

	/**
	 * @return the data5
	 */
	public String getData5() {
		return data5;
	}

	/**
	 * @param data5 the data5 to set
	 */
	public void setData5(String data5) {
		this.data5 = data5;
	}

	/**
	 * @return the data6
	 */
	public String getData6() {
		return data6;
	}

	/**
	 * @param data6 the data6 to set
	 */
	public void setData6(String data6) {
		this.data6 = data6;
	}

	/**
	 * @return the data7
	 */
	public String getData7() {
		return data7;
	}

	/**
	 * @param data7 the data7 to set
	 */
	public void setData7(String data7) {
		this.data7 = data7;
	}

	/**
	 * @return the data8
	 */
	public String getData8() {
		return data8;
	}

	/**
	 * @param data8 the data8 to set
	 */
	public void setData8(String data8) {
		this.data8 = data8;
	}

	/**
	 * @return the data9
	 */
	public String getData9() {
		return data9;
	}

	/**
	 * @param data9 the data9 to set
	 */
	public void setData9(String data9) {
		this.data9 = data9;
	}

	/**
	 * @return the data10
	 */
	public String getData10() {
		return data10;
	}

	/**
	 * @param data10 the data10 to set
	 */
	public void setData10(String data10) {
		this.data10 = data10;
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
	 * @return the updateUserId
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
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

	/**
	 * @return the orderInfo
	 */
	public String getOrderInfo() {
		return orderInfo;
	}

	/**
	 * @param orderInfo the orderInfo to set
	 */
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
