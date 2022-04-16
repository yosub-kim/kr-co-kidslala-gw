/*
 * $Id: AttachOutputType.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 * creation-date : 2006. 4. 5.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO Provide description for "OutputTypeCode"
 * 
 * @author halberd
 * @version $Id: AttachOutputType.java,v 1.1 2009/09/19 11:15:38 cvs3 Exp $
 */
public class AttachOutputType {
	private String codeSetName;
	private String key;
	private String data;
	private String detail1;
	private String detail2;

	public String getCodeSetName() {
		return codeSetName;
	}

	public void setCodeSetName(String codeSetName) {
		this.codeSetName = codeSetName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDetail1() {
		return detail1;
	}

	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}

	public String getDetail2() {
		return detail2;
	}

	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
