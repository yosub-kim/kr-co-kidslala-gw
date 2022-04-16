/*
 * $Id: WorkDao.java,v 1.2 2013/10/17 13:07:49 cvs Exp $
 * created by    : jiwoong
 * creation-date : 2008. 9. 17.
 * =========================================================
 * Copyright (c) 2008 Maninsoft, Inc. All rights reserved.
 */
package kr.co.kmac.pms.common.worklist.dao;

import java.util.List;

import kr.co.kmac.pms.common.worklist.data.Work;

import org.springframework.dao.DataAccessException;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoong
 * @version $Id: WorkDao.java,v 1.2 2013/10/17 13:07:49 cvs Exp $
 */
public interface WorkDao {

	public boolean createWork(Work work) throws DataAccessException;

	public boolean deleteWork(String id) throws DataAccessException;

	public boolean updateWork(Work work) throws DataAccessException;

	public boolean updateDraftDate(String id) throws DataAccessException;

	public Work getWork(String id) throws DataAccessException;

	public List<Work> getWorkList(String refWorkId1) throws DataAccessException;

	public List<Work> getWorkList(String refWorkId1, String refWorkId2) throws DataAccessException;

	public Work getWorkList(String refWorkId1, String refWorkId2, String refWorkId3) throws DataAccessException;

}
