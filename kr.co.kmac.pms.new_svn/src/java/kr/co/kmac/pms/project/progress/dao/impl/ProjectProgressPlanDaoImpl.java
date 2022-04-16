package kr.co.kmac.pms.project.progress.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.progress.dao.ProjectProgressPlanDao;
import kr.co.kmac.pms.project.progress.data.ProjectProgress;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * @deprecated
 * @author Jiwoong Lee
 */
public class ProjectProgressPlanDaoImpl extends JdbcDaoSupport implements ProjectProgressPlanDao {
	private ProjectProgressPlanInsertQuery projectProgressPlanInsertQuery;
	private ProjectProgressPlanSelectQuery1 projectProgressPlanSelectQuery1;
	private ProjectProgressPlanSelectQuery2 projectProgressPlanSelectQuery2;
	private ProjectProgressPlanDeleteQuery1 projectProgressPlanDeleteQuery1;
	private ProjectProgressPlanDeleteQuery2 projectProgressPlanDeleteQuery2;

	@Override
	protected void initDao() throws Exception {
		this.projectProgressPlanInsertQuery = new ProjectProgressPlanInsertQuery(getDataSource());
		this.projectProgressPlanSelectQuery1 = new ProjectProgressPlanSelectQuery1(getDataSource());
		this.projectProgressPlanSelectQuery2 = new ProjectProgressPlanSelectQuery2(getDataSource());
		this.projectProgressPlanDeleteQuery1 = new ProjectProgressPlanDeleteQuery1(getDataSource());
		this.projectProgressPlanDeleteQuery2 = new ProjectProgressPlanDeleteQuery2(getDataSource());
	}

	protected class ProjectProgressPlanInsertQuery extends SqlUpdate {
		protected ProjectProgressPlanInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO projectScheduleP ( projectCode, workSeq, workName, level, parentWorkSeq, startDate, endDate, outputName) "
					+ " VALUES (?,  ?,  ?,  ?,  ?,  ?,  ?,  ? )");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectProgress projectProgress) throws DataAccessException {
			return update(new Object[] { projectProgress.getProjectCode(), projectProgress.getWorkSeq(), projectProgress.getWorkName(),
					projectProgress.getLevel(), projectProgress.getParantWorkSeq(), projectProgress.getStartDate(), projectProgress.getEndDate(),
					projectProgress.getRealEndDate(), projectProgress.getOutputName() });
		}
	}

	protected class ProjectProgressPlanDeleteQuery1 extends SqlUpdate {
		protected ProjectProgressPlanDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleP WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectProgressPlanDeleteQuery2 extends SqlUpdate {
		protected ProjectProgressPlanDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM projectScheduleP WHERE projectCode=? and workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String workSeq) throws DataAccessException {
			return this.update(new Object[] { projectCode, workSeq });
		}
	}

	protected class ProjectProgressPlanSelectQuery1 extends MappingSqlQuery {
		protected ProjectProgressPlanSelectQuery1(DataSource ds) {
			super(ds, "	SELECT * from FROM projectScheduleP where projectCode=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectProgressPlanSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectProgress projectProgress = new ProjectProgress();
			projectProgress.setProjectCode(rs.getString("projectCode"));
			projectProgress.setWorkSeq(rs.getInt("workSeq"));
			projectProgress.setWorkName(rs.getString("workName"));
			projectProgress.setLevel(rs.getInt("level"));
			projectProgress.setParantWorkSeq(rs.getInt("parantWorkSeq"));
			projectProgress.setStartDate(rs.getString("startDate"));
			projectProgress.setEndDate(rs.getString("endDate"));
			projectProgress.setOutputName(rs.getString("outputName"));
			return projectProgress;
		}
	}

	protected class ProjectProgressPlanSelectQuery2 extends ProjectProgressPlanSelectQuery1 {
		protected ProjectProgressPlanSelectQuery2(DataSource ds) {
			super(ds, "	SELECT * from FROM projectScheduleP where projectCode=? AND workSeq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public int count(String projectCode) throws DataAccessException {
		return this.projectProgressPlanSelectQuery1.execute(new Object[] { projectCode }).size();
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectProgressPlanDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, String workSeq) throws DataAccessException {
		this.projectProgressPlanDeleteQuery2.delete(projectCode, workSeq);
	}

	@Override
	public void insert(ProjectProgress projectProgress) throws DataAccessException {
		this.projectProgressPlanInsertQuery.insert(projectProgress);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectProgress> select(String projectCode) throws DataAccessException {
		return this.projectProgressPlanSelectQuery1.execute(new Object[] { projectCode });
	}

	@Override
	public ProjectProgress select(String projectCode, String workSeq) throws DataAccessException {
		return (ProjectProgress) this.projectProgressPlanSelectQuery2.findObject(new Object[] { projectCode, workSeq });
	}
}