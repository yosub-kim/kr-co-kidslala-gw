package kr.co.kmac.pms.board.manager;

import java.util.List;

import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.data.BoardDataForSelect;
import kr.co.kmac.pms.board.exception.BoardException;

public interface BoardManager {

	public String select(String seq) throws BoardException;
	
	public String select2(String ssn) throws BoardException;
	
	public String select3(String ssn) throws BoardException;

	public BoardDataForSelect select(String bbsId, String seq) throws BoardException;
	
	public BoardDataForSelect select2(String bbsId, String seq) throws BoardException;
	
	public BoardDataForSelect select3(String bbsId, String seq) throws BoardException;

	public String insert(BoardData boardData) throws BoardException;

	public void delete(String bbsId, String seq) throws BoardException;

	public String update(BoardData boardData) throws BoardException;

	public String reply(BoardData boardData) throws BoardException;

	public String getBbsNameById(String bbsId) throws BoardException;

	public void insertReadLog(BoardData boardData) throws BoardException;

	public void insertBoardCommentData(BoardCommentData boardCommentData) throws BoardException;

	public void deleteBoardCommentData(String bbsId, String seq, String commentSeq) throws BoardException;
	
	public String selectRecommendCnt(String bbsId, String seq) throws BoardException;
	
	public String selectRecommendCnt2(String bbsId, String seq, String ssn) throws BoardException;
	
	public String selectRecommendContent(String bbsId, String seq) throws BoardException;
}
