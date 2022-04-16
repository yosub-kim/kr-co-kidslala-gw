/*
 * $Id: AttachDao.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.dao;

import java.util.List;

import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.exception.BoardException;

/**
 * TODO Provide description for "AttachDao"
 * @author halberd
 * @version $Id: AttachDao.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 */
public interface AttachDao {
	public void insertFiles(AttachForm attachForm) throws AttachException;

	public void insertFilesEachTaskId(AttachForm attachForm) throws AttachException;
	
	public void delete(String seq) throws AttachException;
	
	public void deleteAllInProject(String projectCode) throws AttachException;
	
	public void deleteAllInTask(String taskId) throws AttachException;

	public void updateFiles(String projectCode, String connTaskId);
	
	public BoardData select(String bbsId, String seq) throws BoardException;
	
	public List<BoardCommentData> selectBoardCommentData(String bbsId, String seq) throws BoardException;

}
