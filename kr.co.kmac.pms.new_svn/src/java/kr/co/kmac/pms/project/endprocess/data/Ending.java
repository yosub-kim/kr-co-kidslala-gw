/*
 * $Id: Ending.java,v 1.2 2009/11/01 05:04:38 cvs1 Exp $
 * created by    : CHO DAE HYON
 * creation-date : 2006. 4. 20.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.project.endprocess.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * TODO 클래스 설명
 * 
 * @author CHO DAE HYON
 * @version $Id: Ending.java,v 1.2 2009/11/01 05:04:38 cvs1 Exp $
 */
public class Ending {

	private String businessTypeCode;
	private String processTypeCode;
	private String endGubun;
	private String endBackground;
	private String endResult;
	private String endReview;
	private String endReason;
	private String endSuggestion;
	private String endRate;
	private String groupComment;
	private String cfoComment;
	private String cboComment;
	private String endTaskState;

	/**
	 * @return the groupComment
	 */
	public String getGroupComment() {
		return groupComment;
	}

	/**
	 * @param groupComment the groupComment to set
	 */
	public void setGroupComment(String groupComment) {
		this.groupComment = groupComment;
	}

	/**
	 * @return Returns the businessTypeCode.
	 */
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}

	/**
	 * @param businessTypeCode The businessTypeCode to set.
	 */
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}

	/**
	 * @return Returns the processTypeCode.
	 */
	public String getProcessTypeCode() {
		return processTypeCode;
	}

	/**
	 * @param processTypeCode The processTypeCode to set.
	 */
	public void setProcessTypeCode(String processTypeCode) {
		this.processTypeCode = processTypeCode;
	}

	/**
	 * @return Returns the endGubun.
	 */
	public String getEndGubun() {
		return endGubun;
	}

	/**
	 * @param endGubun The endGubun to set.
	 */
	public void setEndGubun(String endGubun) {
		this.endGubun = endGubun;
	}

	/**
	 * @return Returns the endBackground.
	 */
	public String getEndBackground() {
		return endBackground;
	}

	/**
	 * @param endBackground The endBackground to set.
	 */
	public void setEndBackground(String endBackground) {
		this.endBackground = endBackground;
	}

	/**
	 * @return Returns the endResult.
	 */
	public String getEndResult() {
		return endResult;
	}

	/**
	 * @param endResult The endResult to set.
	 */
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}

	/**
	 * @return Returns the endReview.
	 */
	public String getEndReview() {
		return endReview;
	}

	/**
	 * @param endReview The endReview to set.
	 */
	public void setEndReview(String endReview) {
		this.endReview = endReview;
	}

	/**
	 * @return Returns the endReason.
	 */
	public String getEndReason() {
		return endReason;
	}

	/**
	 * @param endReason The endReason to set.
	 */
	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}

	/**
	 * @return Returns the endSuggestion.
	 */
	public String getEndSuggestion() {
		return endSuggestion;
	}

	/**
	 * @param endSuggestion The endSuggestion to set.
	 */
	public void setEndSuggestion(String endSuggestion) {
		this.endSuggestion = endSuggestion;
	}

	/**
	 * @return Returns the endRate.
	 */
	public String getEndRate() {
		return endRate;
	}

	/**
	 * @param endRate The endRate to set.
	 */
	public void setEndRate(String endRate) {
		this.endRate = endRate;
	}

	/**
	 * @return Returns the cfoComment.
	 */
	public String getCfoComment() {
		return cfoComment;
	}

	/**
	 * @param cfoComment The cfoComment to set.
	 */
	public void setCfoComment(String cfoComment) {
		this.cfoComment = cfoComment;
	}

	/**
	 * @return Returns the cboComment.
	 */
	public String getCboComment() {
		return cboComment;
	}

	/**
	 * @param cboComment The cboComment to set.
	 */
	public void setCboComment(String cboComment) {
		this.cboComment = cboComment;
	}

	public String getEndTaskState() {
		return endTaskState;
	}

	public void setEndTaskState(String endTaskState) {
		this.endTaskState = endTaskState;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
