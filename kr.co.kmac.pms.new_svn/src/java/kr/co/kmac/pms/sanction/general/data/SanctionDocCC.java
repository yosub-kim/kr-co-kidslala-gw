/*
 * $Id: SanctionDocCC.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 3. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.sanction.general.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: SanctionDocCC.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class SanctionDocCC {

	private int seq;
	private String refMemberSsn;
	private String refMemberName;
	private boolean draftRefMailSended;
	private boolean approveRefMailSended;
	private Date approveCheckedDate;

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the refMemberSsn
	 */
	public String getRefMemberSsn() {
		return refMemberSsn;
	}

	/**
	 * @param refMemberSsn the refMemberSsn to set
	 */
	public void setRefMemberSsn(String refMemberSsn) {
		this.refMemberSsn = refMemberSsn;
	}

	/**
	 * @return the refMemberName
	 */
	public String getRefMemberName() {
		return refMemberName;
	}

	/**
	 * @param refMemberName the refMemberName to set
	 */
	public void setRefMemberName(String refMemberName) {
		this.refMemberName = refMemberName;
	}

	/**
	 * @return the draftRefMailSended
	 */
	public boolean isDraftRefMailSended() {
		return draftRefMailSended;
	}

	/**
	 * @param draftRefMailSended the draftRefMailSended to set
	 */
	public void setDraftRefMailSended(boolean draftRefMailSended) {
		this.draftRefMailSended = draftRefMailSended;
	}

	/**
	 * @return the approveRefMailSended
	 */
	public boolean isApproveRefMailSended() {
		return approveRefMailSended;
	}

	/**
	 * @param approveRefMailSended the approveRefMailSended to set
	 */
	public void setApproveRefMailSended(boolean approveRefMailSended) {
		this.approveRefMailSended = approveRefMailSended;
	}

	/**
	 * @return the approveCheckedDate
	 */
	public Date getApproveCheckedDate() {
		return approveCheckedDate;
	}

	/**
	 * @param approveCheckedDate the approveCheckedDate to set
	 */
	public void setApproveCheckedDate(Date approveCheckedDate) {
		this.approveCheckedDate = approveCheckedDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
