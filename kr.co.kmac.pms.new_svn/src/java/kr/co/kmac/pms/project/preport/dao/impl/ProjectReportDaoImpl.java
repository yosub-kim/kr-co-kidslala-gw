package kr.co.kmac.pms.project.preport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.preport.dao.ProjectReportDao;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectReportDaoImpl extends JdbcDaoSupport implements ProjectReportDao {
	private ProjectReportInsertQuery projectReportInsertQuery;
	private ProjectReportUpdateQuery projectReportUpdateQuery;
	private ProjectReportSelectQuery1 projectReportSelectQuery1;
	private ProjectReportSelectQuery2 projectReportSelectQuery2;
	private ProjectReportSelectQuery3 projectReportSelectQuery3;
	private ProjectReportDeleteQuery1 projectReportDeleteQuery1;
	private ProjectReportDeleteQuery2 projectReportDeleteQuery2;

	@Override
	protected void initDao() throws Exception {
		this.projectReportInsertQuery = new ProjectReportInsertQuery(getDataSource());
		this.projectReportUpdateQuery = new ProjectReportUpdateQuery(getDataSource());
		this.projectReportSelectQuery1 = new ProjectReportSelectQuery1(getDataSource());
		this.projectReportSelectQuery2 = new ProjectReportSelectQuery2(getDataSource());
		this.projectReportSelectQuery3 = new ProjectReportSelectQuery3(getDataSource());
		this.projectReportDeleteQuery1 = new ProjectReportDeleteQuery1(getDataSource());
		this.projectReportDeleteQuery2 = new ProjectReportDeleteQuery2(getDataSource());
	}

	protected class ProjectReportInsertQuery extends SqlUpdate {
		protected ProjectReportInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectReport ( projectCode, year, month, day, week, workSeq, workName, outputName)"
					+ " VALUES (?,   ?,   ?,   ?,   ?,   ?,   ?,   ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectReportPlan projectReportPlan) throws DataAccessException {
			return update(new Object[] { projectReportPlan.getProjectCode(), projectReportPlan.getYear(), projectReportPlan.getMonth(),
					projectReportPlan.getDay(), projectReportPlan.getWeek(), projectReportPlan.getWorkSeq(), projectReportPlan.getWorkName(),
					projectReportPlan.getOutputName() });
		}
	}

	protected class ProjectReportUpdateQuery extends SqlUpdate {
		protected ProjectReportUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectReport set workSeq=?, workName=?, outputName=? "
					+ " WHERE projectCode=? and year=? and month=? and day=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(ProjectReportPlan projectReportPlan) throws DataAccessException {
			return update(new Object[] { projectReportPlan.getWorkSeq(), projectReportPlan.getWorkName(), projectReportPlan.getOutputName(),
					projectReportPlan.getProjectCode(), projectReportPlan.getYear(), projectReportPlan.getMonth(), projectReportPlan.getDay() });
		}
	}

	protected class ProjectReportDeleteQuery1 extends SqlUpdate {
		protected ProjectReportDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectReport WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectReportDeleteQuery2 extends SqlUpdate {
		protected ProjectReportDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectReport WHERE projectCode=? and year=? and month=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String year, String month) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month });
		}
	}

	protected class ProjectReportSelectQuery1 extends MappingSqlQuery {
		protected ProjectReportSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,                                  "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName,	    "
					+ "			( CASE WHEN A.outputName IS NULL THEN '' ELSE A.outputName END) AS outputName	"
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectReport						                                "
					+ "				 WHERE 	projectCode = ?						                                "
					+ "				   AND 	year = ?	                                                        "
					+ "				   AND 	month = ?	                                                        "
					+ "			) A LEFT OUTER JOIN ProjectScheduleM B			                                "
					+ "	    ON 	A.projectCode = B.projectCode					                                "
					+ "	   AND 	A.workSeq = B.workSeq							                                "
					+ " ORDER BY 	A.day ASC										                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectReportSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportPlan projectReportPlan = new ProjectReportPlan();
			projectReportPlan.setProjectCode(rs.getString("projectCode"));
			projectReportPlan.setYear(rs.getString("year"));
			projectReportPlan.setMonth(rs.getString("month"));
			projectReportPlan.setDay(rs.getString("day"));
			projectReportPlan.setWeek(rs.getString("week"));
			projectReportPlan.setWorkSeq(rs.getString("workSeq"));
			projectReportPlan.setWorkName(rs.getString("workName"));
			projectReportPlan.setOutputName(rs.getString("outputName"));
			return projectReportPlan;
		}
	}

	protected class ProjectReportSelectQuery2 extends ProjectReportSelectQuery1 {
		protected ProjectReportSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,	                                "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName,	    "
					+ "			( CASE WHEN A.outputName IS NULL THEN '' ELSE A.outputName END) AS outputName	"
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectReport						                                "
					+ "				 WHERE 	projectCode = ?						                                "
					+ "			) A LEFT OUTER JOIN ProjectScheduleM B			                                "
					+ "	    ON 	A.projectCode = B.projectCode					                                "
					+ "	   AND 	A.workSeq = B.workSeq							                                "
					+ " ORDER BY 	A.day ASC										                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectReportSelectQuery3 extends ProjectReportSelectQuery1 {
		protected ProjectReportSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,	                                "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName,	    "
					+ "			( CASE WHEN A.outputName IS NULL THEN '' ELSE A.outputName END) AS outputName	"
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectReport						                                "
					+ "				 WHERE 	projectCode = ?						                                "
					+ "				   AND 	year = ?	                                                        "
					+ "				   AND 	month = ?	                                                        "
					+ "				   AND 	day = ?	                                                   		    "
					+ "			) A LEFT OUTER JOIN ProjectScheduleM B			                                "
					+ "	    ON 	A.projectCode = B.projectCode					                                "
					+ "	   AND 	A.workSeq = B.workSeq							                                "
					+ " ORDER BY 	A.day ASC										                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	@Override
	public String getSeqNo(String projectCode, String taskFormTypeId) throws DataAccessException {
		return String.valueOf(getJdbcTemplate().queryForInt("Select max(seq) from ProjectReportContent where projectCode=? and taskFormTypeId=?",
				new Object[] { projectCode, taskFormTypeId }) + 1);
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		this.projectReportDeleteQuery2.delete(projectCode, year, month);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectReportDeleteQuery1.delete(projectCode);
	}

	@Override
	public void insert(ProjectReportPlan projectReportPlan) throws DataAccessException {
		this.projectReportInsertQuery.insert(projectReportPlan);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> select(String projectCode, String year, String month) throws DataAccessException {
		return this.projectReportSelectQuery1.execute(new Object[] { projectCode, year, month });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> select(String projectCode) throws DataAccessException {
		return this.projectReportSelectQuery2.execute(new Object[] { projectCode });
	}

	@Override
	public ProjectReportPlan select(String projectCode, String year, String month, String day) throws DataAccessException {
		return (ProjectReportPlan) this.projectReportSelectQuery3.findObject(new Object[] { projectCode, year, month, day });
	}

	@Override
	public int count(String projectCode, String year, String month) throws DataAccessException {
		return this.getJdbcTemplate().queryForInt("SELECT count(*) AS count FROM projectReport WHERE projectCode=? AND year=? AND month=?",
				new Object[] { projectCode, year, month });
	}

	@Override
	public void update(ProjectReportPlan projectReportPlan) throws DataAccessException {
		this.projectReportUpdateQuery.update(projectReportPlan);
	}

	@Override
	public void updateProjectReportPayAmount(String projectCode, String assignDate, String ssn, String payAmount)
			throws DataAccessException {
		getJdbcTemplate().execute(" " 
				+ " update ProjectReportContent set payAmount='" + payAmount + "' "
				+ "  where projectCode='" + projectCode + "' and assignDate='" + assignDate+ "' "
				+ "    and writerSsn='" + ssn + "' ");
	}

}
