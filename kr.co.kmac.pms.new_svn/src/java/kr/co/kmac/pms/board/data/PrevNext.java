/*
 * $Id: PrevNext.java,v 1.1 2009/09/19 11:15:25 cvs3 Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.data;

public class PrevNext {
	private String Subject;
	private String Seq;
	/**
	 * @return Returns the seq.
	 */
	public String getSeq() {
		return Seq;
	}
	/**
	 * @param seq The seq to set.
	 */
	public void setSeq(String seq) {
		Seq = seq;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return Subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		Subject = subject;
	}
}
