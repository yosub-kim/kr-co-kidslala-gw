/*
 * $Id: BoardDao.java,v 1.7 2015/05/18 00:53:01 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.dao;

import java.util.List;

import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.data.BoardDataForList;
import kr.co.kmac.pms.board.data.PrevNext;
import kr.co.kmac.pms.board.exception.BoardException;

import org.springframework.dao.DataAccessException;

public interface BoardDao {
	public String select(String seq) throws BoardException;
	
	public String select2(String ssn) throws BoardException;
	
	public String select3(String ssn) throws BoardException;
	
	public String selectRecommendCnt (String bbsId, String seq) throws BoardException;
	
	public String selectRecommendCnt2 (String bbsId, String seq, String ssn) throws BoardException;
	
	public String selectRecommendContent (String bbsId, String seq) throws BoardException;

	public BoardData select(String bbsId, String seq) throws BoardException;

	public String insert(BoardData StandardbbsData) throws BoardException;

	public void delete(String bbsId, String seq) throws BoardException;

	public String update(BoardData StandardbbsData) throws BoardException;

	public String reply(BoardData StandardbbsData) throws BoardException;

	public PrevNext selectNext(String bbsId, String ref) throws BoardException;

	public PrevNext selectPrev(String bbsId, String ref) throws BoardException;
	
	public PrevNext selectNext2(String bbsId, String ref) throws BoardException;

	public PrevNext selectPrev2(String bbsId, String ref) throws BoardException;
	
	public PrevNext selectNext3(String bbsId, String ref) throws BoardException;

	public PrevNext selectPrev3(String bbsId, String ref) throws BoardException;

	public List<BoardDataForList> selectReplys(String bbsId, String ref) throws BoardException;

	public String getBbsName(String bbsId) throws BoardException;

	public void insertReadLog(BoardData boardData) throws BoardException;

	public List<BoardCommentData> selectBoardCommentData(String bbsId, String seq) throws BoardException;

	public void insertBoardCommentData(BoardCommentData boardCommentData) throws BoardException;

	public void deleteBoardCommentData(String bbsId, String seq, String commentSeq) throws BoardException;
}
