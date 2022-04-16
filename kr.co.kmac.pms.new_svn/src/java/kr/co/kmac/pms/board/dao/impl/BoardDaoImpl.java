package kr.co.kmac.pms.board.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.board.dao.BoardDao;
import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.data.BoardDataForList;
import kr.co.kmac.pms.board.data.PrevNext;
import kr.co.kmac.pms.board.exception.BoardException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.orm.ObjectRetrievalFailureException;

public class BoardDaoImpl extends JdbcDaoSupport implements BoardDao {

	private BoardDataInsertQuery boardDataInsertQuery;
	private BoardDataReadLogInsertQuery boardDataReadLogInsertQuery;
	private BoardDataSelectQuery boardDataSelectQuery;
	private BoardDataSelectNextQuery boardDataSelectNextQuery;
	private BoardDataSelectPrevQuery boardDataSelectPrevQuery;
	private BoardDataSelectNextQuery2 boardDataSelectNextQuery2;
	private BoardDataSelectPrevQuery2 boardDataSelectPrevQuery2;
	private BoardDataSelectNextQuery3 boardDataSelectNextQuery3;
	private BoardDataSelectPrevQuery3 boardDataSelectPrevQuery3;
	private BoardDataDeleteQuery baordDataDeleteQuery;
	private BoardDataUpdateQuery boardDataUpdateQuery;
	private BoardDataReplyQuery boardDataReplyQuery;
	private BoardSelectReplysQuery boardSelectReplysQuery;
	private BoardCommentDataInsertQuery boardCommentDataInsertQuery;
	private BoardCommentDataSelectQuery boardCommentDataSelectQuery;
	private BoardCommentDataDeleteQuery boardCommentDataDeleteQuery;

	protected void initDao() throws Exception {
		this.boardDataInsertQuery = new BoardDataInsertQuery(getDataSource());
		this.boardDataReadLogInsertQuery = new BoardDataReadLogInsertQuery(getDataSource());
		this.boardDataSelectQuery = new BoardDataSelectQuery(getDataSource());
		this.boardDataSelectNextQuery = new BoardDataSelectNextQuery(getDataSource());
		this.boardDataSelectPrevQuery = new BoardDataSelectPrevQuery(getDataSource());
		this.boardDataSelectNextQuery2 = new BoardDataSelectNextQuery2(getDataSource());
		this.boardDataSelectPrevQuery2 = new BoardDataSelectPrevQuery2(getDataSource());
		this.boardDataSelectNextQuery3 = new BoardDataSelectNextQuery3(getDataSource());
		this.boardDataSelectPrevQuery3 = new BoardDataSelectPrevQuery3(getDataSource());
		this.baordDataDeleteQuery = new BoardDataDeleteQuery(getDataSource());
		this.boardDataUpdateQuery = new BoardDataUpdateQuery(getDataSource());
		this.boardDataReplyQuery = new BoardDataReplyQuery(getDataSource());
		this.boardSelectReplysQuery = new BoardSelectReplysQuery(getDataSource());
		this.boardCommentDataInsertQuery = new BoardCommentDataInsertQuery(getDataSource());
		this.boardCommentDataSelectQuery = new BoardCommentDataSelectQuery(getDataSource());
		this.boardCommentDataDeleteQuery = new BoardCommentDataDeleteQuery(getDataSource());
	}

	@Override
	public String select(String seq) throws BoardException {
		String bbsid = (String) getJdbcTemplate().queryForObject("select bbsId from Standardbbs where bbsId like 'management%' and seq=" + seq,
				String.class);
		return bbsid;
	}
	
	@Override
	public String select2(String ssn) throws BoardException {
		String jobClass = (String) getJdbcTemplate().queryForObject("select jobClass from expertPool where ssn='" + ssn + "'", String.class);
		return jobClass;
	}
	
	@Override
	public String select3(String ssn) throws BoardException {
		String dept = (String) getJdbcTemplate().queryForObject("select dept from expertPool where ssn='" + ssn + "'", String.class);
		return dept;
	}
	
	@Override
	public String selectRecommendCnt(String bbsId, String seq) throws BoardException {
		String recCnt = (String) getJdbcTemplate().queryForObject("select count(likeYN) as recCnt from recommendCnt where bbsid='" + bbsId + "' and seq = '" + seq + "'", String.class);
		
		return recCnt;
	
	}
	

	@Override
	public String selectRecommendCnt2(String bbsId, String seq, String ssn) throws BoardException {
		String recCnt = (String) getJdbcTemplate().queryForObject("select count(likeYN) as recCnt from recommendCnt where bbsid='" + bbsId + "' and seq = '" + seq + "' and likeSsn = '" + ssn + "'", String.class);
		
		return recCnt;
	
	}
	
	@Override
	public String selectRecommendContent(String bbsId, String seq) throws BoardException {
		String recCnt = "";
		try{
			recCnt = (String) getJdbcTemplate().queryForObject("select top 1 dbo.getexpertpoolname(likeSsn) as recCnt from recommendCnt where bbsid='" + bbsId + "' and seq = '" + seq + "' order by newid()", String.class);
		}catch(DataAccessException e){
			recCnt = "";
		}
		return recCnt;
	
	}

	public BoardData select(String bbsId, String seq) throws BoardException {
		getJdbcTemplate().update(" UPDATE Standardbbs SET readcnt = readcnt + 1 WHERE bbsid = '" + bbsId + "' AND seq = " + seq);
		Object object = this.boardDataSelectQuery.findObject(new Object[] { bbsId, seq });
		if (object == null) {
			throw new ObjectRetrievalFailureException(BoardData.class, seq);
		}
		return (BoardData) object;
	}

	public String insert(BoardData boardData) throws BoardException {
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM Standardbbs  WITH(NOLOCK) ");
		int maxRef = getJdbcTemplate().queryForInt(
				"SELECT ISNULL(MAX(ref),0)+1 FROM Standardbbs  WITH(NOLOCK)  WHERE bbsid='" + boardData.getBbsId() + "'");
		boardData.setSeq(Integer.toString(maxSeq));
		boardData.setRef(Integer.toString(maxRef));
		this.boardDataInsertQuery.insert(boardData);

		return boardData.getSeq();
	}

	public String getBbsName(String bbsId) throws BoardException {
		List bbsLst = getJdbcTemplate().queryForList(
				"SELECT ISNULL(BBSNAME,'NotRegistered') AS BBSNAME FROM StandardBBSMaster  WITH(NOLOCK) WHERE bbsid = '" + bbsId + "'");
		ListOrderedMap map = (ListOrderedMap) bbsLst.get(0);
		return (String) map.getValue(0);

	}

	public void delete(String bbsId, String seq) throws BoardException {
		this.baordDataDeleteQuery.delete(bbsId, seq);
	}

	public String update(BoardData boardData) throws BoardException {
		this.boardDataUpdateQuery.update(boardData);

		return boardData.getSeq();
	}

	public String reply(BoardData boardData) throws BoardException {
		int iStep = (boardData.getStep().equals("")) ? 0 : Integer.parseInt(boardData.getStep());
		int iLev = (boardData.getLev().equals("")) ? 0 : Integer.parseInt(boardData.getLev());
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM Standardbbs");
		getJdbcTemplate().update(
				"UPDATE Standardbbs SET step = step + 1 WHERE bbsid = '" + boardData.getBbsId() + "' and ref = " + boardData.getRef()
						+ " and step > " + iStep);
		iStep++;
		iLev++;
		boardData.setSeq(Integer.toString(maxSeq));
		boardData.setStep(Integer.toString(iStep));
		boardData.setLev(Integer.toString(iLev));
		this.boardDataReplyQuery.reply(boardData);

		return boardData.getSeq();
	}

	public PrevNext selectNext(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectNextQuery.findObject(new Object[] { bbsId, ref });
	}

	public PrevNext selectPrev(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectPrevQuery.findObject(new Object[] { bbsId, ref });
	}
	
	public PrevNext selectNext2(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectNextQuery2.findObject(new Object[] { bbsId, ref });
	}

	public PrevNext selectPrev2(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectPrevQuery2.findObject(new Object[] { bbsId, ref });
	}
	
	public PrevNext selectNext3(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectNextQuery2.findObject(new Object[] { bbsId, ref });
	}

	public PrevNext selectPrev3(String bbsId, String ref) throws BoardException {
		return (PrevNext) this.boardDataSelectPrevQuery2.findObject(new Object[] { bbsId, ref });
	}

	@SuppressWarnings("unchecked")
	public List<BoardDataForList> selectReplys(String bbsId, String ref) throws BoardException {
		return this.boardSelectReplysQuery.execute(new Object[] { bbsId, ref });
	}

	protected class BoardDataSelectNextQuery extends MappingSqlQuery {
		protected BoardDataSelectNextQuery(DataSource ds) {
			super(ds, " SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref > ? ORDER BY step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectNextQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}

	protected class BoardDataSelectPrevQuery extends MappingSqlQuery {

		protected BoardDataSelectPrevQuery(DataSource ds) {
			super(ds, "SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref < ? ORDER BY ref DESC, step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectPrevQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}
	
	protected class BoardDataSelectNextQuery2 extends MappingSqlQuery {
		protected BoardDataSelectNextQuery2(DataSource ds) {
			super(ds, " SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref > ? AND isnull(prjType, '1') in ('1','3')  ORDER BY step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectNextQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}

	protected class BoardDataSelectPrevQuery2 extends MappingSqlQuery {

		protected BoardDataSelectPrevQuery2(DataSource ds) {
			super(ds, "SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref < ? AND isnull(prjType, '1') in ('1','3')  ORDER BY ref DESC, step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectPrevQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}
	
	protected class BoardDataSelectNextQuery3 extends MappingSqlQuery {
		protected BoardDataSelectNextQuery3(DataSource ds) {
			super(ds, " SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref > ? AND isnull(prjType, '1') in ('1')   ORDER BY step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectNextQuery3(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}

	protected class BoardDataSelectPrevQuery3 extends MappingSqlQuery {

		protected BoardDataSelectPrevQuery3(DataSource ds) {
			super(ds, "SELECT top 1 subject, seq FROM Standardbbs  WITH(NOLOCK) WHERE bbsid = ? AND ref < ? AND isnull(prjType, '1') in ('1')   ORDER BY ref DESC, step");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectPrevQuery3(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrevNext prevNext = new PrevNext();
			prevNext.setSeq(rs.getString("seq"));
			prevNext.setSubject(rs.getString("subject"));
			return prevNext;
		}
	}

	protected class BoardDataSelectQuery extends MappingSqlQuery {

		protected BoardDataSelectQuery(DataSource ds) {
			super(
					ds,
					" SELECT 	bbsid, seq, ref, step, lev, IsNULL((select top 1 name from expertPool  WITH(NOLOCK) where ssn = writerid),'') AS writer, writerid, subject, "
							+ "           email, content, filename, maskname, filesize, download, wtime, readcnt, ip, prjType, topArticle, refSchedule, workDate"
							+ " FROM 	Standardbbs  WITH(NOLOCK) WHERE 	bbsid = ? AND seq = ?       ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected BoardDataSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BoardData boardData = new BoardData();
			boardData.setBbsId(rs.getString("bbsid"));
			boardData.setSeq(rs.getString("seq"));
			boardData.setRef(rs.getString("ref"));
			boardData.setStep(rs.getString("step"));
			boardData.setLev(rs.getString("lev"));
			boardData.setWriter(rs.getString("writer"));
			boardData.setWriterId(rs.getString("writerid"));
			boardData.setSubject(rs.getString("subject"));
			boardData.setEmail(rs.getString("email"));
			boardData.setContent(rs.getString("content"));
			boardData.setFileName(rs.getString("filename"));
			boardData.setMaskName(rs.getString("maskname"));
			boardData.setFileSize(rs.getString("filesize"));
			boardData.setDownload(rs.getString("download"));
			boardData.setWtime(rs.getString("wtime"));
			boardData.setReadCnt(rs.getString("readcnt"));
			boardData.setIp(rs.getString("ip"));
			boardData.setPrjType(rs.getString("prjType"));
			boardData.setTopArticle(rs.getString("topArticle"));
			boardData.setRefSchedule(rs.getString("refSchedule"));
			boardData.setWorkDate(rs.getString("workDate"));
			
			return boardData;
		}

	}

	protected class BoardDataInsertQuery extends SqlUpdate {
		protected BoardDataInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO Standardbbs(bbsid, seq, ref, step, lev, writer, writerid, subject, email, content, "
					+ "							filename, maskname, filesize, download, wtime, readcnt, ip, prjType, topArticle, refSchedule, workDate) "
					+ " VALUES(?, ?, ?, 0, 0, ?, ?, ?, ?, ?, ?, ?, ?, 0, getdate(), 0, ?, ?, ?, ?, ?)");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int insert(BoardData boardData) throws DataAccessException {
			return this.update(new Object[] { boardData.getBbsId(), boardData.getSeq(), boardData.getRef(), boardData.getWriter(),
					boardData.getWriterId(), boardData.getSubject(), boardData.getEmail(), boardData.getContent(), boardData.getFileName(),
					boardData.getMaskName(), boardData.getFileSize(), boardData.getIp(), boardData.getPrjType(), boardData.getTopArticle(), 
					boardData.getRefSchedule(), boardData.getWorkDate() });
		}
	}

	protected class BoardDataReadLogInsertQuery extends SqlUpdate {
		/*
		 * create table StandardbbsReadLog (bbsid varchar(50), seq varchar(50), readerId varchar(50), ip varchar(50), readTime datetime )
		 */

		protected BoardDataReadLogInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO StandardbbsReadLog (bbsId, seq, readerId, ip, readTime) VALUES(?, ?, ?, ?, getdate())");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int insert(BoardData boardData) throws DataAccessException {
			return this.update(new Object[] { boardData.getBbsId(), boardData.getSeq(), boardData.getReader(), boardData.getReadIp() });
		}
	}

	public void insertReadLog(BoardData boardData) throws BoardException {
		this.boardDataReadLogInsertQuery.insert(boardData);
	}

	protected class BoardDataDeleteQuery extends SqlUpdate {
		protected BoardDataDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM Standardbbs WHERE bbsid = ? AND seq = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String bbsId, String seq) throws DataAccessException {
			return this.update(new Object[] { bbsId, seq });
		}
	}

	protected class BoardDataUpdateQuery extends SqlUpdate {
		protected BoardDataUpdateQuery(DataSource ds) {
			super(ds, " UPDATE Standardbbs " + " 	SET subject = ?   " + "		, email = ?" + "		, content = ?" + "		, filename = ?" + "		, maskname = ?"
					+ "		, filesize = ?" + "		, download = 0" + "		, ip = ?" + "		, prjType = ?" + "		, topArticle = ? " +"		, refSchedule = ? " 
					+ "		, workDate = ?" +" WHERE seq = ?   ");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int update(BoardData boardData) throws DataAccessException {
			return update(new Object[] { boardData.getSubject(), boardData.getEmail(), boardData.getContent(), boardData.getFileName(),
					boardData.getMaskName(), boardData.getFileSize(), boardData.getIp(), boardData.getPrjType(), boardData.getTopArticle(),
					boardData.getRefSchedule(), boardData.getWorkDate(), boardData.getSeq() });
		}
	}

	protected class BoardDataReplyQuery extends SqlUpdate {
		protected BoardDataReplyQuery(DataSource ds) {

			super(ds, " INSERT INTO Standardbbs (bbsid, seq, ref, step, lev, writer, writerid, subject, email, content, "
					+ "							filename, maskname, filesize, download, wtime, readcnt, ip, topArticle) "
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, getdate(), 0, ?, ?)");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int reply(BoardData boardData) throws DataAccessException {
			return this.update(new Object[] { boardData.getBbsId(), boardData.getSeq(), boardData.getRef(), boardData.getStep(), boardData.getLev(),
					boardData.getWriter(), boardData.getWriterId(), boardData.getSubject(), boardData.getEmail(), boardData.getContent(),
					boardData.getFileName(), boardData.getMaskName(), boardData.getFileSize(), boardData.getIp(), boardData.getTopArticle() });
		}
	}

	protected class BoardSelectReplysQuery extends MappingSqlQuery {
		protected BoardSelectReplysQuery(DataSource ds) {
			super(ds, " SELECT	lev, ref, subject, maskname, writer, wtime, readcnt, writerid, email, seq		"
					+ " FROM	Standardbbs  WITH(NOLOCK) WHERE 	bbsid = ? AND 	ref = ?                            			"
					+ " ORDER BY step                              					");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected BoardSelectReplysQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BoardDataForList boardDataForList = new BoardDataForList();

			boardDataForList.setLev(rs.getString("lev"));
			boardDataForList.setRef(rs.getString("ref"));
			boardDataForList.setSubject(rs.getString("subject"));
			boardDataForList.setMaskName(rs.getString("maskname"));
			boardDataForList.setWriter(rs.getString("writer"));
			boardDataForList.setWtime(rs.getString("wtime"));
			boardDataForList.setReadCnt(rs.getString("readcnt"));
			boardDataForList.setWriterId(rs.getString("writerid"));
			boardDataForList.setEmail(rs.getString("email"));
			boardDataForList.setSeq(rs.getString("seq"));

			return boardDataForList;
		}
	}

	protected class BoardCommentDataSelectQuery extends MappingSqlQuery {
		protected BoardCommentDataSelectQuery(DataSource ds) {
			super(ds, " SELECT	bbsid, seq, commentSeq, content, writer, writerid, ip, convert(varchar, wtime, 120) wtime	"
					+ " FROM	StandardBBSComment  WITH(NOLOCK) WHERE 	bbsid = ? AND 	seq = ?	" + " ORDER BY wtime														");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected BoardCommentDataSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			BoardCommentData boardCommentData = new BoardCommentData();

			boardCommentData.setBbsId(rs.getString("bbsid"));
			boardCommentData.setSeq(rs.getString("seq"));
			boardCommentData.setCommentSeq(rs.getString("commentSeq"));
			boardCommentData.setContent(rs.getString("content"));
			boardCommentData.setWriter(rs.getString("writer"));
			boardCommentData.setWriterId(rs.getString("writerid"));
			boardCommentData.setIp(rs.getString("ip"));
			boardCommentData.setWriteTime(rs.getString("wtime"));

			return boardCommentData;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardCommentData> selectBoardCommentData(String bbsId, String seq) throws BoardException {
		return this.boardCommentDataSelectQuery.execute(new Object[] { bbsId, seq });
	}

	protected class BoardCommentDataInsertQuery extends SqlUpdate {
		protected BoardCommentDataInsertQuery(DataSource ds) {

			super(ds, " INSERT INTO StandardBBSComment (bbsid, seq, content, writer, writerid, ip, wtime) " + " VALUES(?, ?, ?, ?, ?, ?, getdate())");

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int insert(BoardCommentData boardCommentData) throws DataAccessException {
			return this.update(new Object[] { boardCommentData.getBbsId(), boardCommentData.getSeq(), boardCommentData.getContent(),

			boardCommentData.getWriter(), boardCommentData.getWriterId(), boardCommentData.getIp() });
		}
	}

	@Override
	public void insertBoardCommentData(BoardCommentData boardCommentData) throws BoardException {
		this.boardCommentDataInsertQuery.insert(boardCommentData);
	}

	protected class BoardCommentDataDeleteQuery extends SqlUpdate {
		protected BoardCommentDataDeleteQuery(DataSource ds) {

			super(ds, " DELETE FROM StandardBBSComment  WHERE bbsId = ? and seq = ? and commentSeq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected int delete(String bbsId, String seq, String commentSeq) throws DataAccessException {
			return this.update(new Object[] { bbsId, seq, commentSeq });
		}
	}

	@Override
	public void deleteBoardCommentData(String bbsId, String seq, String commentSeq) throws BoardException {
		this.boardCommentDataDeleteQuery.delete(bbsId, seq, commentSeq);
	}
}
