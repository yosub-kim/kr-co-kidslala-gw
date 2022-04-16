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
 * TODO Ŭ���� ����
 * 
 * @author Administrator
 * @version $Id: IRepositoryManager.java,v 1.1 2009/09/19 11:15:41 cvs3 Exp $
 */
public interface IRepositoryManager {
	public static final Log logger = LogFactory.getLog(IRepositoryManager.class);

	/**
	 * ÷�� ���� ����
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFile(String fileId) throws RepositoryException;
	public int deleteProjectTaskFormAttachData (String fileId) throws RepositoryException;

	/**
	 * ÷�� ���� �׷� ���� TODO �޼ҵ� ����
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public int deleteFileGroup(String groupId) throws RepositoryException;

	/**
	 * ÷�� ���� �ϳ� ����
	 * 
	 * @param fileId
	 * @return
	 * @throws RepositoryException
	 */
	public UploadFile getFileByFileId(String fileId) throws RepositoryException;

	/**
	 * ÷�� ���� �˻� �� ����Ʈ ���� (like ��ġ)
	 * 
	 * @param fileName
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException;

	/**
	 * ÷������ �׷� ����
	 * 
	 * @param groupId
	 * @return
	 * @throws RepositoryException
	 */
	public List<UploadFile> getFileGroup(String groupId) throws RepositoryException;

	/**
	 * ���� �׷� ���� , ���� �� ���� ������ ������... TODO �޼ҵ� ����
	 * 
	 * @param list
	 * @param deleteFiles
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list, String[] deleteFiles) throws RepositoryException;

	/**
	 * ���� ����
	 * 
	 * @param list
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(List<UploadFile> list) throws RepositoryException;

	/**
	 * ���� �׷� ����
	 * 
	 * @param uploadFile
	 * @return
	 * @throws RepositoryException
	 */
	public String saveFile(UploadFile uploadFile) throws RepositoryException;
}
