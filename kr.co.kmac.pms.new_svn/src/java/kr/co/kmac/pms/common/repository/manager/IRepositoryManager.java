/*
 * $Id: IRepositoryManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 10.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.manager;

import java.util.List;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author Administrator
 * @version $Id: IRepositoryManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 */
public interface IRepositoryManager {
	public static final Log logger = LogFactory.getLog(IRepositoryManager.class);

	/**
	 * 첨부 파일 삭제
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String fileId) throws RepositoryException;
	public int deleteProjectTaskFormAttachData (String fileId) throws RepositoryException;

	/**
	 * 첨부 파일 그룹 삭제 TODO 메소드 설명
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFileGroup(String groupId) throws RepositoryException;

	/**
	 * 첨부 파일 하나 리턴
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public UploadFile getFileByFileId(String fileId) throws RepositoryException;

	/**
	 * 첨부 파일 검색 후 리스트 리턴 (like 서치)
	 * 
	 * @param fileName
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException;

	/**
	 * 첨부파일 그룹 리턴
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileGroup(String groupId) throws RepositoryException;

	/**
	 * 파일 그룹 저장 , 삭제 할 파일 있으면 삭제됨... TODO 메소드 설명
	 * 
	 * @param list
	 * @param deleteFiles
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list, String[] deleteFiles) throws RepositoryException;

	/**
	 * 파일 저장
	 * 
	 * @param list
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * 파일 그룹 저장
	 * 
	 * @param uploadFile
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(UploadFile uploadFile) throws RepositoryException;
}
