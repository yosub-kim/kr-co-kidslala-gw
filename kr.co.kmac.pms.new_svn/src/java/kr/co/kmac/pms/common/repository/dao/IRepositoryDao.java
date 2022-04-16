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
 * TODO Ŭ���� ����
 * 
 * @author Administrator
 * @version $Id: IRepositoryDao.java,v 1.1 2009/09/19 11:15:44 cvs3 Exp $
 */
public interface IRepositoryDao {

	/**
	 * ���� �׷� �˻�
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileList(String groupId) throws RepositoryException;

	/**
	 * ���̵�� ���� �ϳ� ã��
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public UploadFile getFileByFileId(String fileId) throws RepositoryException;

	/**
	 * ���ϸ����� ���� ����Ʈ ã��
	 * 
	 * @param groupId
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException;

	/**
	 * ���� �ϳ� �����ϰ� ���ϱ׷� ���̵� ����
	 * 
	 * @param uploadFile
	 * @return ���� �׷� ���̵�
	 * @throws RepositoryException
	 */
	public String saveFile(UploadFile uploadFile) throws RepositoryException;

	/**
	 * ���� ���� �� �����ϰ� ���ϱ׷� ���̵� ����
	 * 
	 * @param uploadFile
	 * @return ���� �׷� ���̵�
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * ������Ʈ
	 * 
	 * @param uploadFile
	 * @return ���� �׷� ���̵�
	 * @throws RepositoryException
	 */
	public String updateFile(UploadFile uploadFile) throws RepositoryException;

	/**
	 * ������Ʈ ���� ����Ʈ
	 * 
	 * @param uploadFile
	 * @return ���� �׷� ���̵�
	 * @throws RepositoryException
	 */
	public String updateFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * ���� �׷� ����
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFileList(String groupId) throws RepositoryException;

	/**
	 * ���� �ϳ� ����
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String fileId) throws RepositoryException;
	public int deleteProjectTaskFormAttachData (String fileId) throws RepositoryException;

	/**
	 * ���� �ϳ� ����
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String groupId, String fileId) throws RepositoryException;

}
