/*
 * $Id: BoardDataForList.java,v 1.5 2012/11/11 05:06:00 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.data;

import java.util.Calendar;

import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;

public class BoardDataForList extends AttachForm {
	private static final long serialVersionUID = -2772287027938210509L;
	private String lev;
	private String ref;
	private String bbsId; // Job Date: 2008-03-24
	private String subject;
	private String maskName;
	private String writer;
	private String wtime;
	private String readCnt;
	private String writerId;
	private String email;
	private String seq;
	private int fileCnt;
	private String topArticle;
	private boolean recent;
	private String commentCnt; 
	private String likeCnt;
	private String prjType;
	
	public String getPrjType() {
		return prjType;
	}

	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	public String getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(String likeCnt) {
		this.likeCnt = likeCnt;
	}

	/**
	 * @return the lev
	 */
	public String getLev() {
		return lev;
	}

	/**
	 * @return the recent
	 */
	public boolean isRecent() {
		Calendar todate = Calendar.getInstance();
		Calendar writeDate = Calendar.getInstance();
		writeDate.setTimeInMillis(DateUtil.getDateTime(this.wtime).getTime());

		this.recent = DateUtil.getDifferDays(writeDate, todate) >= 0 && DateUtil.getDifferDays(writeDate, todate) < 2;
		return this.recent;
	}

	/**
	 * @param recent the recent to set
	 */
	public void setRecent(boolean recent) {
		this.recent = recent;
	}

	/**
	 * @param lev the lev to set
	 */
	public void setLev(String lev) {
		this.lev = lev;
	}

	/**
	 * @return the ref
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref the ref to set
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	public String getSubjectNoTag() {
		return StringUtil.delHtmlTag(subject);
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the maskName
	 */
	public String getMaskName() {
		return maskName;
	}

	/**
	 * @param maskName the maskName to set
	 */
	public void setMaskName(String maskName) {
		this.maskName = maskName;
	}

	/**
	 * @return the writer
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return the wtime
	 */
	public String getWtime() {
		return wtime;
	}

	/**
	 * @param wtime the wtime to set
	 */
	public void setWtime(String wtime) {
		this.wtime = wtime;
	}

	/**
	 * @return the readCnt
	 */
	public String getReadCnt() {
		return readCnt;
	}

	/**
	 * @param readCnt the readCnt to set
	 */
	public void setReadCnt(String readCnt) {
		this.readCnt = readCnt;
	}

	/**
	 * @return the writerId
	 */
	public String getWriterId() {
		return writerId;
	}

	/**
	 * @param writerId the writerId to set
	 */
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	public int getFileCnt() {
		return fileCnt;
	}

	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
	}

	public String getTopArticle() {
		return topArticle;
	}

	public void setTopArticle(String topArticle) {
		this.topArticle = topArticle;
	}

	public String getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(String commentCnt) {
		this.commentCnt = commentCnt;
	}

}
