package kr.co.kmac.pms.common.worklist.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.worklist.dao.WorkDao;
import kr.co.kmac.pms.common.worklist.data.Work;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class WorkDaoImpl extends JdbcDaoSupport implements WorkDao {
	private WorkInsertQuery workInsertQuery;
	private WorkUpdateQuery workUpdateQuery;
	private WorkUpdateQuery1 workUpdateQuery1;
	private WorkSelectQuery workSelectQuery;
	private WorkSelectQuery1 workSelectQuery1;
	private WorkSelectQuery2 workSelectQuery2;
	private WorkSelectQuery3 workSelectQuery3;
	private WorkDeleteQuery workDeleteQuery;

	@Override
	protected void initDao() throws Exception {
		this.workInsertQuery = new WorkInsertQuery(getDataSource());
		this.workUpdateQuery = new WorkUpdateQuery(getDataSource());
		this.workUpdateQuery1 = new WorkUpdateQuery1(getDataSource());
		this.workSelectQuery = new WorkSelectQuery(getDataSource());
		this.workSelectQuery1 = new WorkSelectQuery1(getDataSource());
		this.workSelectQuery2 = new WorkSelectQuery2(getDataSource());
		this.workSelectQuery3 = new WorkSelectQuery3(getDataSource());
		this.workDeleteQuery = new WorkDeleteQuery(getDataSource());
	}

	protected class WorkInsertQuery extends SqlUpdate {
		protected WorkInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO Work (id, title, assigneeId, assignerId, state, type, level, refWorkId1, refWorkId2, refWorkId3,  "
					+ "                   document, draftUserId, draftUserDept, draftDate, createDate, sourceIp) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(), ?)");
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
			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(Work work) throws DataAccessException, UnknownHostException {
			return this.update(new Object[] { work.getId(), work.getTitle(), work.getAssigneeId(), work.getAssignerId(), work.getState(),
					work.getType(), work.getLevel(), work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3(), work.getDocument(),
					work.getDraftUserId(), work.getDraftUserDept(), work.getDraftDate(), InetAddress.getLocalHost().getHostAddress() });
		}
	}

	protected class WorkUpdateQuery extends SqlUpdate {
		protected WorkUpdateQuery(DataSource ds) {
			super(ds, " UPDATE Work SET title=?, assigneeId=?, assignerId=?, state=?, type=?, "
					+ "					level=?, refWorkId1=?, refWorkId2=?, refWorkId3=?,    "
					+ "                 document=?, openDate=?, executeDate=getdate(), isEntrust=?	"
					+ " WHERE  id=?                                                           ");
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
			declareParameter(new SqlParameter(Types.DATE));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(Work work) throws DataAccessException {
			return this.update(new Object[] { work.getTitle(), work.getAssigneeId(), work.getAssignerId(), work.getState(), work.getType(),
					work.getLevel(), work.getRefWorkId1(), work.getRefWorkId2(), work.getRefWorkId3(), work.getDocument(), work.getOpenDate(),
					work.getIsEntrust(), work.getId() });
		}
	}

	protected class WorkUpdateQuery1 extends SqlUpdate {
		protected WorkUpdateQuery1(DataSource ds) {
			super(ds, " UPDATE Work SET draftDate=getdate() WHERE  id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(Work work) throws DataAccessException {
			return this.update(new Object[] { work.getId() });
		}
	}

	protected class WorkDeleteQuery extends SqlUpdate {
		protected WorkDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM Work WITH(NOLOCK) WHERE  id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String id) throws DataAccessException {
			return this.update(new Object[] { id });
		}
	}

	protected class WorkSelectQuery extends MappingSqlQuery {
		protected WorkSelectQuery(DataSource ds) {
			super(ds, "	SELECT 	* FROM Work WITH(NOLOCK) WHERE id=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected WorkSelectQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Work work = new Work();
			work.setId(rs.getString("id"));
			work.setTitle(rs.getString("title"));
			work.setAssigneeId(rs.getString("assigneeId"));
			work.setAssignerId(rs.getString("assignerId"));
			work.setState(rs.getString("state"));
			work.setType(rs.getString("type"));
			work.setLevel(rs.getString("level"));
			work.setRefWorkId1(rs.getString("refWorkId1"));
			work.setRefWorkId2(rs.getString("refWorkId2"));
			work.setRefWorkId3(rs.getString("refWorkId3"));
			work.setDocument(rs.getString("document"));
			work.setDraftUserId(rs.getString("draftUserId"));
			work.setDraftUserDept(rs.getString("draftUserDept"));
			work.setDraftDate(rs.getDate("draftDate"));
			work.setCreateDate(rs.getDate("createDate"));
			work.setOpenDate(rs.getDate("openDate"));
			work.setExecuteDate(rs.getDate("executeDate"));
			return work;
		}
	}

	protected class WorkSelectQuery1 extends WorkSelectQuery {
		protected WorkSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	* FROM Work WITH(NOLOCK) WHERE refWorkId1=? AND state <> 'WORK_STATE_TERMINATED'");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class WorkSelectQuery2 extends WorkSelectQuery {
		protected WorkSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	* FROM Work WITH(NOLOCK) WHERE refWorkId1=? AND refWorkId2=? AND state <> 'WORK_STATE_TERMINATED'");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class WorkSelectQuery3 extends WorkSelectQuery {
		protected WorkSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	* FROM Work WITH(NOLOCK) WHERE refWorkId1=? AND refWorkId2=? AND refWorkId3=? AND state = 'WORK_STATE_READY' ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public boolean createWork(Work work) throws DataAccessException {
		int resValue;
		try {
			resValue = this.workInsertQuery.insert(work);
			return resValue > 0;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteWork(String id) throws DataAccessException {
		int resValue = this.workDeleteQuery.delete(id);
		return resValue > 0;
	}

	@Override
	public boolean updateWork(Work work) throws DataAccessException {
		int resValue = this.workUpdateQuery.update(work);
		return resValue > 0;
	}

	@Override
	public boolean updateDraftDate(String id) throws DataAccessException {
		int resValue = this.workUpdateQuery1.update(id);
		return resValue > 0;
	}

	@Override
	public Work getWork(String id) throws DataAccessException {
		return (Work) this.workSelectQuery.findObject(new Object[] { id });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Work> getWorkList(String refWorkId1) throws DataAccessException {
		return this.workSelectQuery1.execute(new Object[] { refWorkId1 });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Work> getWorkList(String refWorkId1, String refWorkId2) throws DataAccessException {
		return this.workSelectQuery2.execute(new Object[] { refWorkId1, refWorkId2 });
	}

	@Override
	public Work getWorkList(String refWorkId1, String refWorkId2, String refWorkId3) throws DataAccessException {
		return (Work) this.workSelectQuery3.findObject(new Object[] { refWorkId1, refWorkId2, refWorkId3 });
	}

}
