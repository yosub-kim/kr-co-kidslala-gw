/*
 * $Id: CostOverData.java,v 1.1 2009/09/19 11:15:26 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 7. 11.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CostOverData {
	private String projId;

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
