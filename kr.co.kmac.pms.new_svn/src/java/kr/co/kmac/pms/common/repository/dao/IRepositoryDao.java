/*
 * $Id: IRepositoryDao.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 10.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.dao;

import java.util.List;

import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;

/**
 * TODO 클래스 설명
 * 
 * @author Administrator
 * @version $Id: IRepositoryDao.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 */
public interface IRepositoryDao {

	/**
	 * 파일 그룹 검색
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileList(String groupId) throws RepositoryException;

	/**
	 * 아이디로 파일 하나 찾기
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public UploadFile getFileByFileId(String fileId) throws RepositoryException;

	/**
	 * 파일명으로 파일 리스트 찾기
	 * 
	 * @param groupId
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException;

	/**
	 * 파일 하나 저장하고 파일그룹 아이디 리턴
	 * 
	 * @param uploadFile
	 * @return 파일 그룹 아이디
	 * @throws RepositoryException
	 */
	public String saveFile(UploadFile uploadFile) throws RepositoryException;

	/**
	 * 파일 여러 개 저장하고 파일그룹 아이디 리턴
	 * 
	 * @param uploadFile
	 * @return 파일 그룹 아이디
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * 업데이트
	 * 
	 * @param uploadFile
	 * @return 파일 그룹 아이디
	 * @throws RepositoryException
	 */
	public String updateFile(UploadFile uploadFile) throws RepositoryException;

	/**
	 * 업데이트 파일 리스트
	 * 
	 * @param uploadFile
	 * @return 파일 그룹 아이디
	 * @throws RepositoryException
	 */
	public String updateFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * 파일 그룹 삭제
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFileList(String groupId) throws RepositoryException;

	/**
	 * 파일 하나 삭제
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String fileId) throws RepositoryException;
	public int deleteProjectTaskFormAttachData (String fileId) throws RepositoryException;

	/**
	 * 파일 하나 삭제
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String groupId, String fileId) throws RepositoryException;

}
