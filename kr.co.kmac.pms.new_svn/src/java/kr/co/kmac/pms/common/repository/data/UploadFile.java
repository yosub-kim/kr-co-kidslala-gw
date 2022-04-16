/*
 * $Id: UploadFile.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 7. 30.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.data;

import java.io.Serializable;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ÷�� ���� ��ü
 * 
 * @author jiwoong
 * @version $Id: UploadFile.java,v 1.1 2009/09/19 11:15:39 cvs3 Exp $
 */
public class UploadFile implements Serializable {

	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 657445432886875234L;

	/**
	 * ���� �׷� ���̵�
	 */
	private String groupId;
	/**
	 * ���� ���̵�
	 */
	private String fileId;
	/**
	 * ������ ��ǲ �̸� ex. <input type="file" name="file3"/> �� ��� ��ǲ �̸��� file3 �̴�.
	 */
	private String fileInputName;
	/**
	 * ���� ���� ��
	 */
	private String orginalFileName;
	/**
	 * ������ ����� ������ ���
	 */
	private String filePath;
	/**
	 * �ٿ�ε� �� �� �ִ� url
	 */
	private String downloadUrl;
	/**
	 * ���� ũ��
	 */
	private long fileSize;
	/**
	 * ������ Ÿ��
	 */
	private String contentType;
	private InputStream inputStream;
	private String userId;
	private String userName;
	private Date createDate;

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileInputName
	 */
	public String getFileInputName() {
		return fileInputName;
	}

	/**
	 * @param fileInputName the fileInputName to set
	 */
	public void setFileInputName(String fileInputName) {
		this.fileInputName = fileInputName;
	}

	/**
	 * @return the orginalFileName
	 */
	public String getOrginalFileName() {
		return orginalFileName;
	}

	/**
	 * @param orginalFileName the orginalFileName to set
	 */
	public void setOrginalFileName(String orginalFileName) {
		this.orginalFileName = orginalFileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
