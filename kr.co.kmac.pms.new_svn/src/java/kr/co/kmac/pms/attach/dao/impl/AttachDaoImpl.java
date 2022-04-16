/*
 * $Id: AttachDaoImpl.java,v 1.4 2012/01/01 13:33:01 cvs Exp $
 * creation-date : 2006. 4. 7.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.attach.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.attach.dao.AttachDao;
import kr.co.kmac.pms.attach.exception.AttachException;
import kr.co.kmac.pms.attach.form.AttachForm;
import kr.co.kmac.pms.board.data.BoardCommentData;
import kr.co.kmac.pms.board.data.BoardData;
import kr.co.kmac.pms.board.exception.BoardException;
import kr.co.kmac.pms.common.util.DateUtil;
import kr.co.kmac.pms.common.util.StringUtil;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO Provide description for "AttachDaoImpl"
 * 
 * @author halberd
 * @version $Id: AttachDaoImpl.java,v 1.4 2012/01/01 13:33:01 cvs Exp $
 */
public class AttachDaoImpl extends JdbcDaoSupport implements AttachDao {

	private AttachInsertQuery attachInsertQuery;
	private AttachUpdateQuery attachUpdateQuery;
	private AttachDeleteQuery attachDeleteQuery;
	private AttachDeleteAllInProjectQuery attachDeleteAllInProjectQuery;
	private AttachDeleteAllInTaskQuery attachDeleteAllInTaskQuery;
	private BoardCommentDataSelectQuery boardCommentDataSelectQuery;

	protected void initDao() throws Exception {

		this.attachInsertQuery = new AttachInsertQuery(getDataSource());
		this.attachUpdateQuery = new AttachUpdateQuery(getDataSource());
		this.attachDeleteQuery = new AttachDeleteQuery(getDataSource());
		this.attachDeleteAllInProjectQuery = new AttachDeleteAllInProjectQuery(getDataSource());
		this.attachDeleteAllInTaskQuery = new AttachDeleteAllInTaskQuery(getDataSource());
		this.boardCommentDataSelectQuery = new BoardCommentDataSelectQuery(getDataSource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#delete(java.lang.String)
	 */
	public void delete(String seq) throws AttachException {
		// TODO Auto-generated method stub
		this.attachDeleteQuery.delete(seq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#deleteAllInProject(java.lang.String)
	 */
	public void deleteAllInProject(String projectCode) throws AttachException {
		// TODO Auto-generated method stub
		this.attachDeleteAllInProjectQuery.delete(projectCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#deleteAllInTask(java.lang.String)
	 */
	public void deleteAllInTask(String taskId) throws AttachException {
		// TODO Auto-generated method stub
		this.attachDeleteAllInTaskQuery.delete(taskId);
	}

	/* (non-Javadoc)
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#insertFilesEachTaskId(kr.co.kmac.pms.attach.form.AttachForm)
	 */
	@Override
	public void insertFilesEachTaskId(AttachForm attachForm) throws AttachException {
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM ProjectTaskFormAttachData WITH(NOLOCK)");
		this.attachInsertQuery.insertFilesEachTaskId(attachForm, maxSeq);		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BoardCommentData> selectBoardCommentData(String bbsId, String seq) throws BoardException {
		return this.boardCommentDataSelectQuery.execute(new Object[] { bbsId, seq });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#insert(kr.co.kmac.pms.attach.data.AttachForm)
	 */
	public void insertFiles(AttachForm attachForm) throws AttachException {
		int maxSeq = getJdbcTemplate().queryForInt("SELECT ISNULL(MAX(seq),0)+1 FROM ProjectTaskFormAttachData WITH(NOLOCK)");
		this.attachInsertQuery.insertFiles(attachForm, maxSeq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.kmac.pms.attach.dao.AttachDao#updateFiles(java.lang.String, java.lang.String)
	 */
	public void updateFiles(String projectCode, String connTaskId) {
		// TODO Auto-generated method stub
		this.attachUpdateQuery.updateFiles(projectCode, connTaskId);
	}

	protected class AttachUpdateQuery extends SqlUpdate {
		protected AttachUpdateQuery(DataSource ds) {
			super(ds, "UPDATE ProjectTaskFormAttachData SET projectCode=? WHERE taskId =?");
			// 2°³
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected void updateFiles(String projectCode, String connTaskId) throws DataAccessException {
			this.update(new Object[] { projectCode, connTaskId });
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

	protected class AttachInsertQuery extends BatchSqlUpdate {
		protected AttachInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectTaskFormAttachData (								           "
					+ "				seq, taskId, projectCode, taskFormTypeId,                          "
					+ "				attachCreateDate, attachCreatorId, attachSeq, attachWorkName,      "
					+ "				attachOutputName, attachFileId, attachIsEssential, attachFileName, "
					+ "				attachOutputType, attachOutputBusType, attachCount               ) "
					+ "	VALUES	(?, ?, ?, ?,      ?, ?, ?, ?,      ?, ?, ?, ?,      ?, ?, '0'        ) "

			);
			// 18°³
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

		protected void insertFiles(AttachForm attachForm, int maxSeq) throws DataAccessException {
			for (int i = 0; attachForm.getAttachFileId() != null && i < attachForm.getAttachFileId().length; i++) {
				if (attachForm.getAttachFileId()[i] != null && !attachForm.getAttachFileId()[i].equals("")) {
					this.update(new Object[] { Integer.toString(maxSeq + i), 
							attachForm.getTaskId(), 
							attachForm.getProjectCode(),
							attachForm.getTaskFormTypeId(), StringUtil.isNull(attachForm.getAttachCreateDate()[i], DateUtil.getCurrentYyyymmdd()),
							attachForm.getAttachCreatorId()[i], i, attachForm.getAttachWorkName()[i], attachForm.getAttachOutputName()[i],
							attachForm.getAttachFileId()[i], attachForm.getAttachIsEssential()[i], attachForm.getAttachFileName()[i],
							attachForm.getAttachOutputType()[i], attachForm.getAttachOutputBusType()[i] });
				}
			}
			this.flush();
		}
		protected void insertFilesEachTaskId(AttachForm attachForm, int maxSeq) throws DataAccessException {
			for (int i = 0; attachForm.getAttachFileId() != null && i < attachForm.getAttachFileId().length; i++) {
				if (attachForm.getAttachFileId()[i] != null && !attachForm.getAttachFileId()[i].equals("")) {
					this.update(new Object[] { Integer.toString(maxSeq + i), 
							(attachForm.getF_taskId() != null && attachForm.getF_taskId()[i] != null && !attachForm.getF_taskId()[i].equals("") 
									? attachForm.getF_taskId()[i] : attachForm.getTaskId()), 
							attachForm.getProjectCode(),
							attachForm.getTaskFormTypeId(), StringUtil.isNull(attachForm.getAttachCreateDate()[i], DateUtil.getCurrentYyyymmdd()),
							attachForm.getAttachCreatorId()[i], i, attachForm.getAttachWorkName()[i], attachForm.getAttachOutputName()[i],
							attachForm.getAttachFileId()[i], attachForm.getAttachIsEssential()[i], attachForm.getAttachFileName()[i],
							attachForm.getAttachOutputType()[i], attachForm.getAttachOutputBusType()[i] });
				}
			}
			this.flush();
		}
	}

	protected class AttachDeleteQuery extends SqlUpdate {
		protected AttachDeleteQuery(DataSource ds) {
			super(ds, "DELETE FROM ProjectTaskFormAttachData WHERE seq = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String seq) throws DataAccessException {
			return this.update(new Object[] { seq });
		}
	}

	protected class AttachDeleteAllInProjectQuery extends SqlUpdate {
		protected AttachDeleteAllInProjectQuery(DataSource ds) {
			super(ds, "DELETE FROM ProjectTaskFormAttachData WHERE projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class AttachDeleteAllInTaskQuery extends SqlUpdate {
		protected AttachDeleteAllInTaskQuery(DataSource ds) {
			super(ds, "DELETE FROM ProjectTaskFormAttachData WHERE taskId = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
		}

		protected int delete(String taskId) throws DataAccessException {
			return this.update(new Object[] { taskId });
		}
	}

	@Override
	public BoardData select(String bbsId, String seq) throws BoardException {
		// TODO Auto-generated method stub
		return null;
	}
}
