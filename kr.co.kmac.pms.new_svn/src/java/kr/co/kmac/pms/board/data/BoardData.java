/*
 * $Id: BoardData.java,v 1.3 2016/09/29 23:25:47 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.data;

/**
 * @struts:form name="boardAction"
 */
public class BoardData extends BoardDataForList {
	private static final long serialVersionUID = -8318643141347109714L;
	private String bbsId;
	private String step;
	private String content;
	private String fileName;
	private String fileSize;
	private String download;
	private String ip;
	private String readIp;
	private String prjType;
	private String updateTag;
	private String topArticle;
	private String reader;
	private String refSchedule;
	private String workDate;
	private String email;
	private String jobClass;
	
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	/**
	 * @return the prjType
	 */
	public String getPrjType() {
		return prjType;
	}

	/**
	 * @param prjType the prjType to set
	 */
	public void setPrjType(String prjType) {
		this.prjType = prjType;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the download.
	 */
	public String getDownload() {
		return download;
	}

	/**
	 * @param download The download to set.
	 */
	public void setDownload(String download) {
		this.download = download;
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
	 * @return Returns the fileSize.
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize The fileSize to set.
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getReadIp() {
		return readIp;
	}

	public void setReadIp(String readIp) {
		this.readIp = readIp;
	}

	/**
	 * @return Returns the step.
	 */
	public String getStep() {
		return step;
	}

	/**
	 * @param step The step to set.
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * @return Returns the bbsId.
	 */
	public String getBbsId() {
		return bbsId;
	}

	/**
	 * @param bbsId The bbsId to set.
	 */
	public void setBbsId(String bbsId) {
		this.bbsId = bbsId;
	}

	/**
	 * @return the updateTag
	 */
	public String getUpdateTag() {
		return updateTag;
	}

	/**
	 * @param updateTag the updateTag to set
	 */
	public void setUpdateTag(String updateTag) {
		this.updateTag = updateTag;
	}

	public String getTopArticle() {
		return topArticle;
	}

	public void setTopArticle(String topArticle) {
		this.topArticle = topArticle;
	}

	public String getRefSchedule() {
		return refSchedule;
	}

	public void setRefSchedule(String refSchedule) {
		this.refSchedule = refSchedule;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
}
