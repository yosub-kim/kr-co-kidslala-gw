/*
 * $Id: AttachManager.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.manager;

import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;
import net.mlw.vlh.ValueList;

import org.springframework.web.context.WebApplicationContext;

/**
 * TODO Provide description for "AttachManager"
 * 
 * @author halberd
 * @version $Id: AttachManager.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 */
public interface AttachManager {

	public void insert(AttachForm attachForm) throws AttachException;

	public void insertFilesEachTaskId(AttachForm attachForm) throws AttachException;

	public void delete(String seq) throws AttachException;

	public void deleteAllInProject(String projectCode) throws AttachException;

	public void deleteAllInTask(String taskId) throws AttachException;

	public ValueList selectList(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException;
	
	public ValueList selectList_exc(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException;
	
	public ValueList selectList2(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException;
	
	public ValueList selectList3(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException;

	public ValueList selectListForTask(WebApplicationContext wc, String taskId, String pg, String pageSize) throws AttachException;

	public ValueList selectListForTask(WebApplicationContext wc, String taskId, String pg, String pageSize, String projectCode, String taskFormTypeId)
			throws AttachException;

	public void connect(String projectCode, String connTaskId) throws AttachException;
	
	public BoardDataForSelect select2(String bbsId, String seq) throws BoardException;
}
