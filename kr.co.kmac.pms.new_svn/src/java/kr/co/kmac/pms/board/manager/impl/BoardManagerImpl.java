/*
 * $Id: BoardManagerImpl.java,v 1.7 2015/05/18 00:53:03 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.manager.impl;

import java.util.List;

import kr.co.kmac.pms.board.dao.BoardDao;
import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.board.manager.BoardManager;

public class BoardManagerImpl implements BoardManager {
	private BoardDao boardDao;

	public BoardDao getBoardDao() {
		return this.boardDao;
	}

	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public String select(String seq) throws BoardException {
		return getBoardDao().select(seq);
	}
	
	@Override
	public String select2(String ssn) throws BoardException {
		return getBoardDao().select2(ssn);
	}
	
	@Override
	public String select3(String ssn) throws BoardException {
		return getBoardDao().select3(ssn);
	}
	
	@Override
	public String selectRecommendCnt(String bbsId, String seq) throws BoardException {
		return getBoardDao().selectRecommendCnt(bbsId, seq);
	}
	
	@Override
	public String selectRecommendCnt2(String bbsId, String seq, String ssn) throws BoardException {
		return getBoardDao().selectRecommendCnt2(bbsId, seq, ssn);
	}
	
	public String selectRecommendContent(String bbsId, String seq) throws BoardException {
		return getBoardDao().selectRecommendContent(bbsId, seq);
	}

	@Override
	public BoardDataForSelect select(String bbsId, String seq) throws BoardException {
		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		boardDataForSelect.setBoardData(getBoardDao().select(bbsId, seq));
		boardDataForSelect.setNext(getBoardDao().selectNext(bbsId, boardDataForSelect.getBoardData().getRef()));

		boardDataForSelect.setPrev(getBoardDao().selectPrev(bbsId, boardDataForSelect.getBoardData().getRef()));
		// boardDataForSelect.setPrev(getBoardDao().selectPrev)
		boardDataForSelect.setSelectReplys(getBoardDao().selectReplys(bbsId, boardDataForSelect.getBoardData().getRef()));
		boardDataForSelect.setBoardCommentDataList(getBoardDao().selectBoardCommentData(bbsId, seq));
		return boardDataForSelect;
	}
	
	@Override
	public BoardDataForSelect select2(String bbsId, String seq) throws BoardException {
		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		boardDataForSelect.setBoardData(getBoardDao().select(bbsId, seq));
		boardDataForSelect.setNext(getBoardDao().selectNext2(bbsId, boardDataForSelect.getBoardData().getRef()));

		boardDataForSelect.setPrev(getBoardDao().selectPrev2(bbsId, boardDataForSelect.getBoardData().getRef()));
		// boardDataForSelect.setPrev(getBoardDao().selectPrev)
		boardDataForSelect.setSelectReplys(getBoardDao().selectReplys(bbsId, boardDataForSelect.getBoardData().getRef()));
		boardDataForSelect.setBoardCommentDataList(getBoardDao().selectBoardCommentData(bbsId, seq));
		return boardDataForSelect;
	}
	
	@Override
	public BoardDataForSelect select3(String bbsId, String seq) throws BoardException {
		BoardDataForSelect boardDataForSelect = new BoardDataForSelect();
		boardDataForSelect.setBoardData(getBoardDao().select(bbsId, seq));
		boardDataForSelect.setNext(getBoardDao().selectNext3(bbsId, boardDataForSelect.getBoardData().getRef()));

		boardDataForSelect.setPrev(getBoardDao().selectPrev3(bbsId, boardDataForSelect.getBoardData().getRef()));
		// boardDataForSelect.setPrev(getBoardDao().selectPrev)
		boardDataForSelect.setSelectReplys(getBoardDao().selectReplys(bbsId, boardDataForSelect.getBoardData().getRef()));
		boardDataForSelect.setBoardCommentDataList(getBoardDao().selectBoardCommentData(bbsId, seq));
		return boardDataForSelect;
	}

	@Override
	public String insert(BoardData boardData) throws BoardException {
		return getBoardDao().insert(boardData);
	}

	public void delete(String bbsId, String seq) throws BoardException {
		getBoardDao().delete(bbsId, seq);
	}

	public String update(BoardData boardData) throws BoardException {
		return getBoardDao().update(boardData);
	}

	public String reply(BoardData boardData) throws BoardException {
		return getBoardDao().reply(boardData);
	}

	public String getBbsNameById(String bbsId) throws BoardException {
		return getBoardDao().getBbsName(bbsId);
	}

	public void insertReadLog(BoardData boardData) throws BoardException {
		this.getBoardDao().insertReadLog(boardData);
	}

	@Override
	public void insertBoardCommentData(BoardCommentData boardCommentData) throws BoardException {
		this.getBoardDao().insertBoardCommentData(boardCommentData);
	}

	@Override
	public void deleteBoardCommentData(String bbsId, String seq, String commentSeq) throws BoardException {
		this.getBoardDao().deleteBoardCommentData(bbsId, seq, commentSeq);
	}
}
