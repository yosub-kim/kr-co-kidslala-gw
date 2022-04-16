package kr.co.kmac.pms.project.progress.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.dao.ProjectProgressExeDetailDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgressDetail;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @deprecated
 * @author Jiwoong Lee
 */
public class ProjectProgressExeDetailDaoImpl extends JdbcDaoSupport implements ProjectProgressExeDetailDao {
	private ProjectProgressExeDetailInsertQuery projectProgressExeDetailInsertQuery;
	private ProjectProgressExeDetailSelectQuery1 projectProgressExeDetailSelectQuery1;
	private ProjectProgressExeDetailSelectQuery2 projectProgressExeDetailSelectQuery2;
	private ProjectProgressExeDetailDeleteQuery1 projectProgressExeDetailDeleteQuery1;
	private ProjectProgressExeDetailDeleteQuery2 projectProgressExeDetailDeleteQuery2;
	private ProjectProgressExeDetailDeleteQuery3 projectProgressExeDetailDeleteQuery3;

	@Override
	protected void initDao() throws Exception {
		this.projectProgressExeDetailInsertQuery = new ProjectProgressExeDetailInsertQuery(getDataSource());
		this.projectProgressExeDetailSelectQuery1 = new ProjectProgressExeDetailSelectQuery1(getDataSource());
		this.projectProgressExeDetailSelectQuery2 = new ProjectProgressExeDetailSelectQuery2(getDataSource());
		this.projectProgressExeDetailDeleteQuery1 = new ProjectProgressExeDetailDeleteQuery1(getDataSource());
		this.projectProgressExeDetailDeleteQuery2 = new ProjectProgressExeDetailDeleteQuery2(getDataSource());
		this.projectProgressExeDetailDeleteQuery3 = new ProjectProgressExeDetailDeleteQuery3(getDataSource());
	}

	protected class ProjectProgressExeDetailInsertQuery extends SqlUpdate {
		protected ProjectProgressExeDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectScheduleMDetail ( projectCode, workSeq, ssn) VALUES (?,  ?,  ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectProgressDetail projectProgressDetail) throws DataAccessException {
			return update(new Object[] { projectProgressDetail.getProjectCode(), projectProgressDetail.getWorkSeq(), projectProgressDetail.getSsn() });
		}
	}

	protected class ProjectProgressExeDetailDeleteQuery1 extends SqlUpdate {
		protected ProjectProgressExeDetailDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleMDetail WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectProgressExeDetailDeleteQuery2 extends SqlUpdate {
		protected ProjectProgressExeDetailDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleMDetail WHERE projectCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String workSeq) throws DataAccessException {
			return this.update(new Object[] { projectCode, workSeq });
		}
	}

	protected class ProjectProgressExeDetailDeleteQuery3 extends SqlUpdate {
		protected ProjectProgressExeDetailDeleteQuery3(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleMDetail WHERE projectCode=? and workSeq=? and chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String workSeq, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, workSeq, ssn });
		}
	}

	protected class ProjectProgressExeDetailSelectQuery1 extends MappingSqlQuery {
		protected ProjectProgressExeDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT * from FROM projectScheduleMDetail where projectCode=? and workSeq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressExeDetailSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectProgressDetail projectProgressDetail = new ProjectProgressDetail();
			projectProgressDetail.setProjectCode(rs.getString("projectCode"));
			projectProgressDetail.setWorkSeq(rs.getString("workSeq"));
			projectProgressDetail.setSsn(rs.getString("ssn"));
			return projectProgressDetail;
		}
	}

	protected class ProjectProgressExeDetailSelectQuery2 extends ProjectProgressExeDetailSelectQuery1 {
		protected ProjectProgressExeDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT * from FROM projectScheduleMDetail where projectCode=? AND workSeq=? and chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public int count(String projectCode, String workSeq) throws DataAccessException {
		return this.projectProgressExeDetailSelectQuery1.execute(new Object[] { projectCode, workSeq }).size();
	}

	@Override
	public void delete(String projectCode, String workSeq, String ssn) throws DataAccessException {
		this.projectProgressExeDetailDeleteQuery3.delete(projectCode, workSeq, ssn);
	}

	@Override
	public void delete(String projectCode, String workSeq) throws DataAccessException {
		this.projectProgressExeDetailDeleteQuery2.delete(projectCode, workSeq);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectProgressExeDetailDeleteQuery1.delete(projectCode);
	}

	@Override
	public void insert(ProjectProgressDetail projectProgressDetail) throws DataAccessException {
		this.projectProgressExeDetailInsertQuery.insert(projectProgressDetail);
	}

	@Override
	public ProjectProgressDetail select(String projectCode, String workSeq, String ssn) throws DataAccessException {
		return (ProjectProgressDetail) this.projectProgressExeDetailSelectQuery2.findObject(new Object[] { projectCode, workSeq, ssn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectProgressDetail> select(String projectCode, String workSeq) throws DataAccessException {
		return this.projectProgressExeDetailSelectQuery1.execute(new Object[] { projectCode, workSeq });
	}

}
