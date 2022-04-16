/*
 * $Id: BoardDataForSelect.java,v 1.2 2012/06/29 08:37:00 cvs Exp $
 * created by    : ¾È¼ºÈ£
 * creation-date : 2006. 5. 18.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.board.data;

import java.util.ArrayList;
import java.util.List;

public class BoardDataForSelect {
	private String seq;
	private BoardData boardData;
	private PrevNext prev;
	private PrevNext next;
	private List<BoardCommentData> boardCommentDataList;
	private List<BoardDataForList> selectReplys;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public BoardDataForSelect() {
		selectReplys = new ArrayList<BoardDataForList>();
	}

	/**
	 * @return Returns the next.
	 */
	public PrevNext getNext() {
		return this.next;
	}

	/**
	 * @param next The next to set.
	 */
	public void setNext(PrevNext next) {
		this.next = next;
	}

	/**
	 * @return Returns the prev.
	 */
	public PrevNext getPrev() {
		return this.prev;
	}

	/**
	 * @param prev The prev to set.
	 */
	public void setPrev(PrevNext prev) {
		this.prev = prev;
	}

	/**
	 * @return Returns the standardbbsData.
	 */
	public BoardData getBoardData() {
		return this.boardData;
	}

	/**
	 * @param boardData The standardbbsData to set.
	 */
	public void setBoardData(BoardData boardData) {
		this.boardData = boardData;
	}

	public List<BoardDataForList> getSelectReplys() {
		return selectReplys;
	}

	public void setSelectReplys(List<BoardDataForList> selectReplys) {
		this.selectReplys = selectReplys;
	}

	public List<BoardCommentData> getBoardCommentDataList() {
		return boardCommentDataList;
	}

	public int getBoardCommentDataListSize() {
		return boardCommentDataList.size();
	}

	public void setBoardCommentDataList(List<BoardCommentData> boardCommentDataList) {
		this.boardCommentDataList = boardCommentDataList;
	}

}
