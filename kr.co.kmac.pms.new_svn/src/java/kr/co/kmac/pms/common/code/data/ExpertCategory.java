/*
 * $Id: ExpertCategory.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 2. 23.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.code.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 상품명 코드
 * 
 * @author jiwoongLee
 * @version $Id: ExpertCategory.java,v 1.1 2009/09/19 11:15:36 cvs3 Exp $
 */
public class ExpertCategory extends Code {

	private String detail1;
	private String detail2;

	/**
	 * @return the detail1
	 */
	public String getDetail1() {
		return detail1;
	}

	/**
	 * @param detail1 the detail1 to set
	 */
	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}

	/**
	 * @return the detail2
	 */
	public String getDetail2() {
		return detail2;
	}

	/**
	 * @param detail2 the detail2 to set
	 */
	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
