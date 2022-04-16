package kr.co.kmac.pms.project.progress.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.dao.ProjectProgressPlanDetailDao;
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
public class ProjectProgressPlanDetailDaoImpl extends JdbcDaoSupport implements ProjectProgressPlanDetailDao {

	private ProjectProgressPlanDetailInsertQuery projectProgressPlanDetailInsertQuery;
	private ProjectProgressPlanDetailSelectQuery1 projectProgressPlanDetailSelectQuery1;
	private ProjectProgressPlanDetailSelectQuery2 projectProgressPlanDetailSelectQuery2;
	private ProjectProgressPlanDetailDeleteQuery1 projectProgressPlanDetailDeleteQuery1;
	private ProjectProgressPlanDetailDeleteQuery2 projectProgressPlanDetailDeleteQuery2;
	private ProjectProgressPlanDetailDeleteQuery3 projectProgressPlanDetailDeleteQuery3;

	@Override
	protected void initDao() throws Exception {
		this.projectProgressPlanDetailInsertQuery = new ProjectProgressPlanDetailInsertQuery(getDataSource());
		this.projectProgressPlanDetailSelectQuery1 = new ProjectProgressPlanDetailSelectQuery1(getDataSource());
		this.projectProgressPlanDetailSelectQuery2 = new ProjectProgressPlanDetailSelectQuery2(getDataSource());
		this.projectProgressPlanDetailDeleteQuery1 = new ProjectProgressPlanDetailDeleteQuery1(getDataSource());
		this.projectProgressPlanDetailDeleteQuery2 = new ProjectProgressPlanDetailDeleteQuery2(getDataSource());
		this.projectProgressPlanDetailDeleteQuery3 = new ProjectProgressPlanDetailDeleteQuery3(getDataSource());
	}

	protected class ProjectProgressPlanDetailInsertQuery extends SqlUpdate {
		protected ProjectProgressPlanDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectSchedulePDetail ( projectCode, workSeq, ssn) VALUES (?,  ?,  ?)");
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

	protected class ProjectProgressPlanDetailDeleteQuery1 extends SqlUpdate {
		protected ProjectProgressPlanDetailDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM projectSchedulePDetail WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectProgressPlanDetailDeleteQuery2 extends SqlUpdate {
		protected ProjectProgressPlanDetailDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM projectSchedulePDetail WHERE projectCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String workSeq) throws DataAccessException {
			return this.update(new Object[] { projectCode, workSeq });
		}
	}

	protected class ProjectProgressPlanDetailDeleteQuery3 extends SqlUpdate {
		protected ProjectProgressPlanDetailDeleteQuery3(DataSource ds) {
			super(ds, " DELETE FROM projectSchedulePDetail WHERE projectCode=? and workSeq=? and chargeSsn=?");
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

	protected class ProjectProgressPlanDetailSelectQuery1 extends MappingSqlQuery {
		protected ProjectProgressPlanDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT * from FROM projectSchedulePDetail where projectCode=? and workSeq=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressPlanDetailSelectQuery1(DataSource ds, String query) {
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

	protected class ProjectProgressPlanDetailSelectQuery2 extends ProjectProgressPlanDetailSelectQuery1 {
		protected ProjectProgressPlanDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT * from FROM projectSchedulePDetail where projectCode=? AND workSeq=? and chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public int count(String projectCode, String workSeq) throws DataAccessException {
		return this.projectProgressPlanDetailSelectQuery1.execute(new Object[] { projectCode, workSeq }).size();
	}

	@Override
	public void delete(String projectCode, String workSeq, String ssn) throws DataAccessException {
		this.projectProgressPlanDetailDeleteQuery3.delete(projectCode, workSeq, ssn);
	}

	@Override
	public void delete(String projectCode, String workSeq) throws DataAccessException {
		this.projectProgressPlanDetailDeleteQuery2.delete(projectCode, workSeq);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectProgressPlanDetailDeleteQuery1.delete(projectCode);
	}

	@Override
	public void insert(ProjectProgressDetail projectProgressDetail) throws DataAccessException {
		this.projectProgressPlanDetailInsertQuery.insert(projectProgressDetail);
	}

	@Override
	public ProjectProgressDetail select(String projectCode, String workSeq, String ssn) throws DataAccessException {
		return (ProjectProgressDetail) this.projectProgressPlanDetailSelectQuery2.findObject(new Object[] { projectCode, workSeq, ssn });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectProgressDetail> select(String projectCode, String workSeq) throws DataAccessException {
		return this.projectProgressPlanDetailSelectQuery1.execute(new Object[] { projectCode, workSeq });
	}
}
