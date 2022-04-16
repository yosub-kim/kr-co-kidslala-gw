package kr.co.kmac.pms.project.progress.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.dao.ProjectProgressExeDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectProgressExeDaoImpl extends JdbcDaoSupport implements ProjectProgressExeDao {
	private ProjectProgressExeInsertQuery projectProgressExeInsertQuery;
	private ProjectProgressExeSelectQuery1 projectProgressExeSelectQuery1;
	private ProjectProgressExeSelectQuery2 projectProgressExeSelectQuery2;
	private ProjectProgressExeDeleteQuery1 projectProgressExeDeleteQuery1;
	private ProjectProgressExeDeleteQuery2 projectProgressExeDeleteQuery2;
	
	private ProjectProgressFinishQuery projectProgressFinishQuery;
	private MaxWorkSeqQuery maxWorkSeqQuery;

	@Override
	protected void initDao() throws Exception {
		this.projectProgressExeInsertQuery = new ProjectProgressExeInsertQuery(getDataSource());
		this.projectProgressExeSelectQuery1 = new ProjectProgressExeSelectQuery1(getDataSource());
		this.projectProgressExeSelectQuery2 = new ProjectProgressExeSelectQuery2(getDataSource());
		this.projectProgressExeDeleteQuery1 = new ProjectProgressExeDeleteQuery1(getDataSource());
		this.projectProgressExeDeleteQuery2 = new ProjectProgressExeDeleteQuery2(getDataSource());
		this.projectProgressFinishQuery = new ProjectProgressFinishQuery(getDataSource());
		this.maxWorkSeqQuery = new MaxWorkSeqQuery(getDataSource());
	}

	protected class ProjectProgressExeInsertQuery extends SqlUpdate {
		protected ProjectProgressExeInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectScheduleM ( projectCode, workSeq, workName, level, parentWorkSeq, startDate, "
					+ "                                endDate, realEndDate, outputName, workType, orderSeq, contentId) "
					+ " VALUES (?,  ?,  ?,  ?,  ?,  ?,     ?,  ?,  ?,  ?,  ?,  ? )                                      ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));

			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectProgress projectProgress) throws DataAccessException {
			return update(new Object[] { projectProgress.getProjectCode(), projectProgress.getWorkSeq(), projectProgress.getWorkName(),
					projectProgress.getLevel(), projectProgress.getParantWorkSeq(), projectProgress.getStartDate(), projectProgress.getEndDate(),
					projectProgress.getRealEndDate(), projectProgress.getOutputName(), projectProgress.getWorkType(), projectProgress.getOrderSeq(),
					projectProgress.getContentId() });
		}
	}

	protected class ProjectProgressExeDeleteQuery1 extends SqlUpdate {
		protected ProjectProgressExeDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleM WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectProgressExeDeleteQuery2 extends SqlUpdate {
		protected ProjectProgressExeDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleM WHERE projectCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String workSeq) throws DataAccessException {
			return this.update(new Object[] { projectCode, workSeq });
		}
	}

	protected class ProjectProgressExeSelectQuery1 extends MappingSqlQuery {
		protected ProjectProgressExeSelectQuery1(DataSource ds) {
			super(ds, "	SELECT *  FROM projectScheduleM where projectCode=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressExeSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectProgress projectProgress = new ProjectProgress();
			projectProgress.setProjectCode(rs.getString("projectCode"));
			projectProgress.setWorkSeq(rs.getInt("workSeq"));
			projectProgress.setWorkName(rs.getString("workName"));
			projectProgress.setLevel(rs.getInt("level"));
			projectProgress.setParantWorkSeq(rs.getInt("parentWorkSeq"));
			projectProgress.setStartDate(rs.getString("startDate"));
			projectProgress.setEndDate(rs.getString("endDate"));
			projectProgress.setRealEndDate(rs.getString("realEndDate"));
			projectProgress.setOutputName(rs.getString("outputName"));
			projectProgress.setWorkType(rs.getInt("workType"));
			projectProgress.setOrderSeq(rs.getInt("orderSeq"));
			projectProgress.setContentId(rs.getString("contentId"));
			return projectProgress;
		}
	}

	protected class ProjectProgressExeSelectQuery2 extends ProjectProgressExeSelectQuery1 {
		protected ProjectProgressExeSelectQuery2(DataSource ds) {
			super(ds, "	SELECT *  FROM projectScheduleM where projectCode=? AND workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public int count(String projectCode) throws DataAccessException {
		return this.projectProgressExeSelectQuery1.execute(new Object[] { projectCode }).size();
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectProgressExeDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, String workSeq) throws DataAccessException {
		this.projectProgressExeDeleteQuery2.delete(projectCode, workSeq);
	}

	@Override
	public void insert(ProjectProgress projectProgress) throws DataAccessException {
		this.projectProgressExeInsertQuery.insert(projectProgress);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectProgress> select(String projectCode) throws DataAccessException {
		return this.projectProgressExeSelectQuery1.execute(new Object[] { projectCode });
	}

	@Override
	public ProjectProgress select(String projectCode, String workSeq) throws DataAccessException {
		return (ProjectProgress) this.projectProgressExeSelectQuery2.findObject(new Object[] { projectCode, workSeq });
	}

	protected class MaxWorkSeqQuery extends MappingSqlQuery {
		protected MaxWorkSeqQuery(DataSource ds) {
			super(ds, "	select isNull(max(workSeq), 0) as maxValue from projectScheduleM where projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected MaxWorkSeqQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Integer(rs.getInt(1));
		}
	}

	@Override
	public int getMaxWorkSeq(String projectCode) throws DataAccessException {
		return (Integer) this.maxWorkSeqQuery.findObject(new Object[] { projectCode });
	}

	protected class ProjectProgressFinishQuery extends MappingSqlQuery {
		protected ProjectProgressFinishQuery(DataSource ds) {
			super(ds, "	SELECT count(*) FROM projectScheduleM where projectCode= ? and (realEndDate is null or realEndDate = '')");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressFinishQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Integer(rs.getInt(1));
		}
	}

	@Override
	public boolean isProjectProgressFinish(String projectCode) throws DataAccessException {
		int res = (Integer) this.projectProgressFinishQuery.findObject(new Object[] { projectCode });
		return res > 0 ? false : true;
	}
	
}
