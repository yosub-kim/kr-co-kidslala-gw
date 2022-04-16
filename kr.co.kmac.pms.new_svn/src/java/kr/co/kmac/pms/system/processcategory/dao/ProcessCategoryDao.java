/*
 * $Id: ProcessCategoryDao.java,v 1.2 2013/12/31 05:48:30 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 4. 25.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.system.processcategory.dao;

import java.util.List;

import kr.co.kmac.pms.system.processcategory.data.ProcessCategory;

import org.springframework.dao.DataAccessException;

public interface ProcessCategoryDao {
	public ProcessCategory getProcessCatogory(String id) throws DataAccessException;

	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName) throws DataAccessException;

	public List<ProcessCategory> getProcessCatogoryList(String processCategoryName, String businessTypeCode, String runningDivCode,
			String businessFunctionType, String processCode) throws DataAccessException;

	public void createProcessCatogory(ProcessCategory processCategory) throws DataAccessException;

	public void updateProcessCatogory(ProcessCategory processCategory) throws DataAccessException;

	public void deleteProcessCatogory(String id) throws DataAccessException;
}
