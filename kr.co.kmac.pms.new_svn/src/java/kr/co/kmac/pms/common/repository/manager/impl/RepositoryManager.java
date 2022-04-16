/*
 * $Id: RepositoryManager.java,v 1.1 2009/09/19 11:15:33 cvs3 Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 10.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.repository.manager.impl;

import java.util.List;

import kr.co.kmac.pms.common.repository.dao.IRepositoryDao;
import kr.co.kmac.pms.common.repository.data.UploadFile;
import kr.co.kmac.pms.common.repository.exception.RepositoryException;
import kr.co.kmac.pms.common.repository.manager.IRepositoryManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO 클래스 설명
 * 
 * @author Administrator
 * @version $Id: RepositoryManager.java,v 1.1 2009/09/19 11:15:33 cvs3 Exp $
 */
public class RepositoryManager implements IRepositoryManager {
	private static final Log logger = LogFactory.getLog(RepositoryManager.class);
	private IRepositoryDao repositoryDao;

	public int deleteFile(String fileId) throws RepositoryException {
		logger.debug("파일 삭제");
		return this.getRepositoryDao().deleteFile(fileId);
	}
	
	public int deleteProjectTaskFormAttachData (String fileId) throws RepositoryException {
		logger.debug("파일 삭제");
		return this.getRepositoryDao().deleteProjectTaskFormAttachData(fileId);
	}

	public int deleteFileGroup(String groupId) throws RepositoryException {
		logger.debug("파일 그룹 삭제");
		return this.getRepositoryDao().deleteFileList(groupId);
	}

	public UploadFile getFileByFileId(String fileId) throws RepositoryException {
		return this.getRepositoryDao().getFileByFileId(fileId);
	}

	public List<UploadFile> getFileByFileName(String fileName) throws RepositoryException {
		return this.getRepositoryDao().getFileByFileName(fileName);
	}

	public List<UploadFile> getFileGroup(String groupId) throws RepositoryException {
		return this.getRepositoryDao().getFileList(groupId);
	}

	public String saveFile(List<UploadFile> list, String[] deleteFiles) throws RepositoryException {
		logger.debug("파일 그룹 저장");
		if(deleteFiles != null)
		for (int i = 0; i < deleteFiles.length; i++) {
			this.getRepositoryDao().deleteFile(deleteFiles[i]);
		}
		return this.getRepositoryDao().saveFile(list);
	}

	public String saveFile(List<UploadFile> list) throws RepositoryException {
		logger.debug("파일 저장");
		return this.getRepositoryDao().saveFile(list);
	}

	public String saveFile(UploadFile uploadFile) throws RepositoryException {
		logger.debug("파일 저장");
		return this.getRepositoryDao().saveFile(uploadFile);
	}

	/**
	 * @return Returns the repositoryDao.
	 */
	public IRepositoryDao getRepositoryDao() {
		return repositoryDao;
	}

	/**
	 * @param repositoryDao
	 *            The repositoryDao to set.
	 */
	public void setRepositoryDao(IRepositoryDao repositoryDao) {
		this.repositoryDao = repositoryDao;
	}

}
