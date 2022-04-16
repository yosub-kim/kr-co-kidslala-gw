/*
 * $Id: Code.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * code 최상위 상위객체
 * 
 * @author jiwoongLee
 * @version $Id: Code.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class Code {

	private String codeSetName;
	private String key;
	private String data;

	public Code() {
		super();
	}

	public Code(String codeSetName, String key, String data) {
		super();
		this.codeSetName = codeSetName;
		this.key = key;
		this.data = data;
	}

	/**
	 * @return the codeSetName
	 */
	public String getCodeSetName() {
		return codeSetName;
	}

	/**
	 * @param codeSetName the codeSetName to set
	 */
	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public static Code valueOf(CodeEntity entity) {
		Code code = new Code();
		code.setCodeSetName(entity.getTableName());
		code.setKey(entity.getKey1());
		code.setData(entity.getData1());
		return code;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
