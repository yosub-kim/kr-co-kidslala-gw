/*
 * $Id: BoardIndex.java,v 1.2 2012/11/11 10:51:28 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.data;

/**
 * @struts:form name="boardAction"
 */
public class BoardIndex extends BoardDataForList {
	private static final long serialVersionUID = -8318643141347109714L;
	private String bbsId;
	private String bbsName;
	private String recentCnt;
	private String totalCnt;

	/**
	 * @return the bbsId
	 */
	public String getBbsId() {
		return bbsId;
	}

	/**
	 * @param bbsId the bbsId to set
	 */
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	/**
	 * @return the bbsName
	 */
	public String getBbsName() {
		return bbsName;
	}

	/**
	 * @param bbsName the bbsName to set
	 */
	public void setBbsName(String bbsName) {
		this.bbsName = bbsName;
	}

	/**
	 * @return the recentCnt
	 */
	public String getRecentCnt() {
		return recentCnt;
	}

	/**
	 * @param recentCnt the recentCnt to set
	 */
	public void setRecentCnt(String recentCnt) {
		this.recentCnt = recentCnt;
	}

	public String getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}

}
