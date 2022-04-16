/*
 * $Id: AttachManagerImpl.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.manager.impl;

import java.util.HashMap;
import java.util.Map;

import kr.co.kmac.pms.attach.dao.AttachDao;
import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.attach.manager.AttachManager;
import kr.co.kmac.pms.board.dao.BoardDao;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

/**
 * TODO Provide description for "AttachManagerImpl"
 * 
 * @author halberd
 * @version $Id: AttachManagerImpl.java,v 1.2 2012/01/01 13:33:01 cvs Exp $
 */
public class AttachManagerImpl implements AttachManager {
	private static final Log logger = LogFactory.getLog(AttachManagerImpl.class);
	private AttachDao attachDao;


	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#connect(java.lang.String, java.lang.String)
	 */
	public void connect(String projectCode, String connTaskId) throws AttachException {
		getAttachDao().updateFiles(projectCode, connTaskId);
	}

	/**
	 * @return Returns the attachDao.
	 */
	public AttachDao getAttachDao() {
		return attachDao;
	}

	/**
	 * @param attachDao The attachDao to set.
	 */
	public void setAttachDao(AttachDao attachDao) {
		this.attachDao = attachDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#delete(java.lang.String)
	 */
	public void delete(String seq) throws AttachException {
		getAttachDao().delete(seq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#deleteAllInProject(java.lang.String)
	 */
	public void deleteAllInProject(String projectCode) throws AttachException {
		getAttachDao().deleteAllInProject(projectCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#deleteAllInTask(java.lang.String)
	 */
	public void deleteAllInTask(String taskId) throws AttachException {
		getAttachDao().deleteAllInTask(taskId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#insert(kr.co.kmac.pms.attach.data.AttachForm)
	 */
	public void insert(AttachForm attachForm) throws AttachException {
		getAttachDao().insertFiles(attachForm);
	}
	

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#insertEnding(kr.co.kmac.pms.attach.form.AttachForm)
	 */
	@Override
	public void insertFilesEachTaskId(AttachForm attachForm) throws AttachException {
		getAttachDao().insertFilesEachTaskId(attachForm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#selectList(org.springframework.web.context.WebApplicationContext, java.lang.String)
	 */
	public ValueList selectList(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));

		if (attachForm.getTaskId() != null && !attachForm.getTaskId().equals(""))
			filters.put("taskId", attachForm.getTaskId());
		if (attachForm.getProjectCode() != null && !attachForm.getProjectCode().equals(""))
			filters.put("projectCode", attachForm.getProjectCode());
		if (attachForm.getTaskFormTypeId() != null && !attachForm.getTaskFormTypeId().equals(""))
			filters.put("taskFormTypeId", attachForm.getTaskFormTypeId());
		if (attachForm.getAttachCreateDate() != null && attachForm.getAttachCreateDate()[0] != null
				&& !attachForm.getAttachCreateDate()[0].equals(""))
			filters.put("attachCreateDate", attachForm.getAttachCreateDate()[0]);
		if (attachForm.getAttachCreatorId() != null && attachForm.getAttachCreatorId()[0] != null && !attachForm.getAttachCreatorId()[0].equals(""))
			filters.put("attachCreatorId", attachForm.getAttachCreatorId()[0]);
		if (attachForm.getAttachSeq() != null && attachForm.getAttachSeq()[0] != null && !attachForm.getAttachSeq()[0].equals(""))
			filters.put("attachSeq", attachForm.getAttachSeq()[0]);
		if (attachForm.getAttachWorkName() != null && attachForm.getAttachWorkName()[0] != null && !attachForm.getAttachWorkName()[0].equals(""))
			filters.put("attachWorkName", "%" + attachForm.getAttachWorkName()[0] + "%");
		if (attachForm.getAttachOutputName() != null && attachForm.getAttachOutputName()[0] != null
				&& !attachForm.getAttachOutputName()[0].equals(""))
			filters.put("attachOutputName", "%" + attachForm.getAttachOutputName()[0] + "%");
		if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId()[0] != null && !attachForm.getAttachFileId()[0].equals(""))
			filters.put("attachFileId", attachForm.getAttachFileId()[0]);
		if (attachForm.getAttachIsEssential() != null && attachForm.getAttachIsEssential()[0] != null
				&& !attachForm.getAttachIsEssential()[0].equals(""))
			filters.put("attachIsEssential", attachForm.getAttachIsEssential()[0]);
		if (attachForm.getAttachFileName() != null && attachForm.getAttachFileName()[0] != null && !attachForm.getAttachFileName()[0].equals(""))
			filters.put("attachFileName", "%" + attachForm.getAttachFileName()[0] + "%");
		if (attachForm.getAttachOutputType() != null && attachForm.getAttachOutputType()[0] != null
				&& !attachForm.getAttachOutputType()[0].equals(""))
			filters.put("attachOutputType", attachForm.getAttachOutputType()[0]);
		if (attachForm.getAttachOutputBusType() != null && attachForm.getAttachOutputBusType()[0] != null
				&& !attachForm.getAttachOutputBusType()[0].equals("")) {
			if (attachForm.getAttachOutputBusType()[0].equals("OTHER"))
				filters.put("attachOutputBusTypeOther", attachForm.getAttachOutputBusType()[0]);
			else if (attachForm.getAttachOutputBusType()[0].equals("ATTACH"))
				filters.put("attachOutputBusTypeAttach", attachForm.getAttachOutputBusType()[0]);
			else
				filters.put("attachOutputBusType", attachForm.getAttachOutputBusType()[0]);
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#selectList(org.springframework.web.context.WebApplicationContext, java.lang.String)
	 */
	public ValueList selectList_exc(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));

		if (attachForm.getTaskId() != null && !attachForm.getTaskId().equals(""))
			filters.put("taskId", attachForm.getTaskId());
		if (attachForm.getProjectCode() != null && !attachForm.getProjectCode().equals(""))
			filters.put("projectCode", attachForm.getProjectCode());
		if (attachForm.getTaskFormTypeId() != null && !attachForm.getTaskFormTypeId().equals(""))
			filters.put("taskFormTypeId", attachForm.getTaskFormTypeId());
		if (attachForm.getAttachCreateDate() != null && attachForm.getAttachCreateDate()[0] != null
				&& !attachForm.getAttachCreateDate()[0].equals(""))
			filters.put("attachCreateDate", attachForm.getAttachCreateDate()[0]);
		if (attachForm.getAttachCreatorId() != null && attachForm.getAttachCreatorId()[0] != null && !attachForm.getAttachCreatorId()[0].equals(""))
			filters.put("attachCreatorId", attachForm.getAttachCreatorId()[0]);
		if (attachForm.getAttachSeq() != null && attachForm.getAttachSeq()[0] != null && !attachForm.getAttachSeq()[0].equals(""))
			filters.put("attachSeq", attachForm.getAttachSeq()[0]);
		if (attachForm.getAttachWorkName() != null && attachForm.getAttachWorkName()[0] != null && !attachForm.getAttachWorkName()[0].equals(""))
			filters.put("attachWorkName", "%" + attachForm.getAttachWorkName()[0] + "%");
		if (attachForm.getAttachOutputName() != null && attachForm.getAttachOutputName()[0] != null
				&& !attachForm.getAttachOutputName()[0].equals(""))
			filters.put("attachOutputName", "%" + attachForm.getAttachOutputName()[0] + "%");
		if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId()[0] != null && !attachForm.getAttachFileId()[0].equals(""))
			filters.put("attachFileId", attachForm.getAttachFileId()[0]);
		if (attachForm.getAttachIsEssential() != null && attachForm.getAttachIsEssential()[0] != null
				&& !attachForm.getAttachIsEssential()[0].equals(""))
			filters.put("attachIsEssential", attachForm.getAttachIsEssential()[0]);
		if (attachForm.getAttachFileName() != null && attachForm.getAttachFileName()[0] != null && !attachForm.getAttachFileName()[0].equals(""))
			filters.put("attachFileName", "%" + attachForm.getAttachFileName()[0] + "%");
		if (attachForm.getAttachOutputType() != null && attachForm.getAttachOutputType()[0] != null
				&& !attachForm.getAttachOutputType()[0].equals(""))
			filters.put("attachOutputType", attachForm.getAttachOutputType()[0]);
		if (attachForm.getAttachOutputBusType() != null && attachForm.getAttachOutputBusType()[0] != null
				&& !attachForm.getAttachOutputBusType()[0].equals("")) {
			if (attachForm.getAttachOutputBusType()[0].equals("OTHER"))
				filters.put("attachOutputBusTypeOther", attachForm.getAttachOutputBusType()[0]);
			else if (attachForm.getAttachOutputBusType()[0].equals("ATTACH"))
				filters.put("attachOutputBusTypeAttach", attachForm.getAttachOutputBusType()[0]);
			else
				filters.put("attachOutputBusType", attachForm.getAttachOutputBusType()[0]);
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry_exc", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#selectList(org.springframework.web.context.WebApplicationContext, java.lang.String)
	 */
	public ValueList selectList2(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));

		if (attachForm.getTaskId() != null && !attachForm.getTaskId().equals(""))
			filters.put("taskId", attachForm.getTaskId());
		if (attachForm.getProjectCode() != null && !attachForm.getProjectCode().equals(""))
			filters.put("projectCode", attachForm.getProjectCode());
		if (attachForm.getTaskFormTypeId() != null && !attachForm.getTaskFormTypeId().equals(""))
			filters.put("taskFormTypeId", attachForm.getTaskFormTypeId());
		if (attachForm.getAttachCreateDate() != null && attachForm.getAttachCreateDate()[0] != null
				&& !attachForm.getAttachCreateDate()[0].equals(""))
			filters.put("attachCreateDate", attachForm.getAttachCreateDate()[0]);
		if (attachForm.getAttachCreatorId() != null && attachForm.getAttachCreatorId()[0] != null && !attachForm.getAttachCreatorId()[0].equals(""))
			filters.put("attachCreatorId", attachForm.getAttachCreatorId()[0]);
		if (attachForm.getAttachSeq() != null && attachForm.getAttachSeq()[0] != null && !attachForm.getAttachSeq()[0].equals(""))
			filters.put("attachSeq", attachForm.getAttachSeq()[0]);
		if (attachForm.getAttachWorkName() != null && attachForm.getAttachWorkName()[0] != null && !attachForm.getAttachWorkName()[0].equals(""))
			filters.put("attachWorkName", "%" + attachForm.getAttachWorkName()[0] + "%");
		if (attachForm.getAttachOutputName() != null && attachForm.getAttachOutputName()[0] != null
				&& !attachForm.getAttachOutputName()[0].equals(""))
			filters.put("attachOutputName", "%" + attachForm.getAttachOutputName()[0] + "%");
		if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId()[0] != null && !attachForm.getAttachFileId()[0].equals(""))
			filters.put("attachFileId", attachForm.getAttachFileId()[0]);
		if (attachForm.getAttachIsEssential() != null && attachForm.getAttachIsEssential()[0] != null
				&& !attachForm.getAttachIsEssential()[0].equals(""))
			filters.put("attachIsEssential", attachForm.getAttachIsEssential()[0]);
		if (attachForm.getAttachFileName() != null && attachForm.getAttachFileName()[0] != null && !attachForm.getAttachFileName()[0].equals(""))
			filters.put("attachFileName", "%" + attachForm.getAttachFileName()[0] + "%");
		if (attachForm.getAttachOutputType() != null && attachForm.getAttachOutputType()[0] != null
				&& !attachForm.getAttachOutputType()[0].equals(""))
			filters.put("attachOutputType", attachForm.getAttachOutputType()[0]);
		if (attachForm.getAttachOutputBusType() != null && attachForm.getAttachOutputBusType()[0] != null
				&& !attachForm.getAttachOutputBusType()[0].equals("")) {
			if (attachForm.getAttachOutputBusType()[0].equals("OTHER"))
				filters.put("attachOutputBusTypeOther", attachForm.getAttachOutputBusType()[0]);
			else if (attachForm.getAttachOutputBusType()[0].equals("ATTACH"))
				filters.put("attachOutputBusTypeAttach", attachForm.getAttachOutputBusType()[0]);
			else
				filters.put("attachOutputBusType", attachForm.getAttachOutputBusType()[0]);
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry2", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.manager.AttachManager#selectList(org.springframework.web.context.WebApplicationContext, java.lang.String)
	 */
	public ValueList selectList3(WebApplicationContext wc, AttachForm attachForm, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));

		if (attachForm.getTaskId() != null && !attachForm.getTaskId().equals(""))
			filters.put("taskId", attachForm.getTaskId());
		if (attachForm.getProjectCode() != null && !attachForm.getProjectCode().equals(""))
			filters.put("projectCode", attachForm.getProjectCode());
		if (attachForm.getTaskFormTypeId() != null && !attachForm.getTaskFormTypeId().equals(""))
			filters.put("taskFormTypeId", attachForm.getTaskFormTypeId());
		if (attachForm.getAttachCreateDate() != null && attachForm.getAttachCreateDate()[0] != null
				&& !attachForm.getAttachCreateDate()[0].equals(""))
			filters.put("attachCreateDate", attachForm.getAttachCreateDate()[0]);
		if (attachForm.getAttachCreatorId() != null && attachForm.getAttachCreatorId()[0] != null && !attachForm.getAttachCreatorId()[0].equals(""))
			filters.put("attachCreatorId", attachForm.getAttachCreatorId()[0]);
		if (attachForm.getAttachSeq() != null && attachForm.getAttachSeq()[0] != null && !attachForm.getAttachSeq()[0].equals(""))
			filters.put("attachSeq", attachForm.getAttachSeq()[0]);
		if (attachForm.getAttachWorkName() != null && attachForm.getAttachWorkName()[0] != null && !attachForm.getAttachWorkName()[0].equals(""))
			filters.put("attachWorkName", "%" + attachForm.getAttachWorkName()[0] + "%");
		if (attachForm.getAttachOutputName() != null && attachForm.getAttachOutputName()[0] != null
				&& !attachForm.getAttachOutputName()[0].equals(""))
			filters.put("attachOutputName", "%" + attachForm.getAttachOutputName()[0] + "%");
		if (attachForm.getAttachFileId() != null && attachForm.getAttachFileId()[0] != null && !attachForm.getAttachFileId()[0].equals(""))
			filters.put("attachFileId", attachForm.getAttachFileId()[0]);
		if (attachForm.getAttachIsEssential() != null && attachForm.getAttachIsEssential()[0] != null
				&& !attachForm.getAttachIsEssential()[0].equals(""))
			filters.put("attachIsEssential", attachForm.getAttachIsEssential()[0]);
		if (attachForm.getAttachFileName() != null && attachForm.getAttachFileName()[0] != null && !attachForm.getAttachFileName()[0].equals(""))
			filters.put("attachFileName", "%" + attachForm.getAttachFileName()[0] + "%");
		if (attachForm.getAttachOutputType() != null && attachForm.getAttachOutputType()[0] != null
				&& !attachForm.getAttachOutputType()[0].equals(""))
			filters.put("attachOutputType", attachForm.getAttachOutputType()[0]);
		if (attachForm.getAttachOutputBusType() != null && attachForm.getAttachOutputBusType()[0] != null
				&& !attachForm.getAttachOutputBusType()[0].equals("")) {
			if (attachForm.getAttachOutputBusType()[0].equals("OTHER"))
				filters.put("attachOutputBusTypeOther", attachForm.getAttachOutputBusType()[0]);
			else if (attachForm.getAttachOutputBusType()[0].equals("ATTACH"))
				filters.put("attachOutputBusTypeAttach", attachForm.getAttachOutputBusType()[0]);
			else
				filters.put("attachOutputBusType", attachForm.getAttachOutputBusType()[0]);
		}

		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry3", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}

	public ValueList selectListForTask(WebApplicationContext wc, String taskId, String pg, String pageSize) throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		if (taskId != null && !taskId.equals(""))
			filters.put("taskId", taskId);
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}

	public BoardDataForSelect select2(String bbsId, String seq) throws BoardException {
		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();

		//boardDataForSelect.setBoardData(getBoardDao().select(bbsId, seq));
		//boardDataForSelect.setNext(getBoardDao().selectNext(bbsId, boardDataForSelect.getBoardData().getRef()));

		//boardDataForSelect.setPrev(getBoardDao().selectPrev(bbsId, boardDataForSelect.getBoardData().getRef()));
		// boardDataForSelect.setPrev(getBoardDao().selectPrev)
		//boardDataForSelect.setSelectReplys(getBoardDao().selectReplys(bbsId, boardDataForSelect.getBoardData().getRef()));
		boardDataForSelect.setBoardCommentDataList(getAttachDao().selectBoardCommentData(bbsId, seq));
		System.out.println(bbsId);
		return boardDataForSelect;
	}
	
	public ValueList selectListForTask(WebApplicationContext wc, String taskId, String pg, String pageSize, String projectCode, String taskFormTypeId)
			throws AttachException {
		Map<String, String> filters = new HashMap<String, String>();
		ValueList valueList = null;

		if (pg != null && !pg.equals(""))
			filters.put(ValueListInfo.PAGING_PAGE, String.valueOf(pg));
		if (pageSize != null && !pageSize.equals(""))
			filters.put(ValueListInfo.PAGING_NUMBER_PER, String.valueOf(pageSize));
		if (taskId != null && !taskId.equals(""))
			filters.put("taskId", taskId);
		if (projectCode != null && !projectCode.equals(""))
			filters.put("projectCode", projectCode);
		if (taskFormTypeId != null && !taskFormTypeId.equals(""))
			filters.put("taskFormTypeId", taskFormTypeId);
		try {
			ValueListHandler vlh = (ValueListHandler) wc.getBean("attachDataSelectListBean", ValueListHandler.class);
			ValueListInfo info = new ValueListInfo("seq", ValueListInfo.ASCENDING);
			info.setFilters(filters);
			valueList = vlh.getValueList("attachDataSelectListEntry", info);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return valueList;
	}
}
