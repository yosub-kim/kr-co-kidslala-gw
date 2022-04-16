/*
 * $Id: CustomerCommentForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 * creation-date : 2006. 4. 14.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.customer.data;

import org.apache.struts.action.ActionForm;

/**
 * TODO Provide description for "CustomerForm"
 * @author halberd
 * @version $Id: CustomerCommentForm.java,v 1.1 2009/09/19 11:15:29 cvs3 Exp $
 */
/**
 * @struts:form name="customerCommentManagerAction"
 */
public class CustomerCommentForm extends ActionForm {
	static final long serialVersionUID = -7034897190745766929L;

	private String idx;
	private String parent_idx;
	private String writer;
	private String writerId;
	private String comment;
	private String fileName;
	private String upFile;
	private String regdate;
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return Returns the idx.
	 */
	public String getIdx() {
		return idx;
	}
	/**
	 * @param idx The idx to set.
	 */
	public void setIdx(String idx) {
		this.idx = idx;
	}
	/**
	 * @return Returns the parent_idx.
	 */
	public String getParent_idx() {
		return parent_idx;
	}
	/**
	 * @param parent_idx The parent_idx to set.
	 */
	public void setParent_idx(String parent_idx) {
		this.parent_idx = parent_idx;
	}
	/**
	 * @return Returns the regdate.
	 */
	public String getRegdate() {
		return regdate;
	}
	/**
	 * @param regdate The regdate to set.
	 */
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	/**
	 * @return Returns the upFile.
	 */
	public String getUpFile() {
		return upFile;
	}
	/**
	 * @param upFile The upFile to set.
	 */
	public void setUpFile(String upFile) {
		this.upFile = upFile;
	}
	/**
	 * @return Returns the writer.
	 */
	public String getWriter() {
		return writer;
	}
	/**
	 * @param writer The writer to set.
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	/**
	 * @return Returns the writerId.
	 */
	public String getWriterId() {
		return writerId;
	}
	/**
	 * @param writerId The writerId to set.
	 */
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

}
