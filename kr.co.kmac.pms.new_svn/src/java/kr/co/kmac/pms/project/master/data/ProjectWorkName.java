/*
 * $Id: ProjectWorkName.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 3. 24.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.master.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: ProjectWorkName.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 */
public class ProjectWorkName {

	private String workSeq;// 업무순서
	private String workName;// 업무명
	private String outputName;// 산출물명
	private String startDate;// 산출물명
	private String endDate;// 산출물명

	public String getOutputName() {
		return this.outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkSeq() {
		return this.workSeq;
	}

	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
