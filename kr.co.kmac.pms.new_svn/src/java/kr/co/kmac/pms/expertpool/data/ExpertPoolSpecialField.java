/*
 * $Id: ExpertPoolSpecialField.java,v 1.2 2010/12/10 09:08:58 cvs3 Exp $ created
 * by : jiwoongLee creation-date : 2006. 2. 27.
 * ========================================================= Copyright (c) 2006
 * Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.expertpool.data;

import java.util.Date;

/**
 * 전문가 경력 모델
 * @author jiwoongLee
 * @version $Id: ExpertPoolSpecialField.java,v 1.2 2010/12/10 09:08:58 cvs3 Exp $
 */
public class ExpertPoolSpecialField {

	private String ssn;
	private String specialBizId;
	private String specialBizName;
	private String specialFunctionId;
	private String specialFunctionName;
	private String specialId;
	private String specialName;
	private Date createdDate;
	private Date modifiedDate;

	public ExpertPoolSpecialField() {
		super();
	}

	public ExpertPoolSpecialField(String ssn, String specialId, String specialName) {
		super();
		this.ssn = ssn;
		this.specialId = specialId;
		this.specialName = specialName;
	}

	public String getSpecialBizId() {
		return specialBizId;
	}

	public void setSpecialBizId(String specialBizId) {
		this.specialBizId = specialBizId;
	}

	public String getSpecialBizName() {
		return specialBizName;
	}

	public void setSpecialBizName(String specialBizName) {
		this.specialBizName = specialBizName;
	}

	public String getSpecialFunctionId() {
		return specialFunctionId;
	}

	public void setSpecialFunctionId(String specialFunctionId) {
		this.specialFunctionId = specialFunctionId;
	}

	public String getSpecialFunctionName() {
		return specialFunctionName;
	}

	public void setSpecialFunctionName(String specialFunctionName) {
		this.specialFunctionName = specialFunctionName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
