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
 * 첨부 파일 객체
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
	 * 파일 그룹 아이디
	 */
	private String groupId;
	/**
	 * 파일 아이디
	 */
	private String fileId;
	/**
	 * 파일의 인풋 이름 ex. <input type="file" name="file3"/> 일 경우 인풋 이름은 file3 이다.
	 */
	private String fileInputName;
	/**
	 * 실제 파일 명
	 */
	private String orginalFileName;
	/**
	 * 서버에 저장된 파일의 경로
	 */
	private String filePath;
	/**
	 * 다운로드 할 수 있는 url
	 */
	private String downloadUrl;
	/**
	 * 파일 크기
	 */
	private long fileSize;
	/**
	 * 컨텐츠 타입
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
