package kr.co.kmac.pms.project.manpower.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.manpower.dao.ProjectManpowerDao;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectManpowerDaoImpl extends JdbcDaoSupport implements ProjectManpowerDao {
	
	private ProjectManpowerSelectQuery1 projectManpowerSelectQuery1;
	private ProjectManpowerSelectQuery2 projectManpowerSelectQuery2;
	private ProjectManpowerSelectQuery3 projectManpowerSelectQuery3;
	private ProjectManpowerInsertQuery projectManpowerInsertQuery;
	private ProjectManpowerUpdateQuery projectManpowerUpdateQuery;

	
	@Override
	protected void initDao() throws Exception {
		this.projectManpowerSelectQuery1 = new ProjectManpowerSelectQuery1(getDataSource());
		this.projectManpowerSelectQuery2 = new ProjectManpowerSelectQuery2(getDataSource());
		this.projectManpowerSelectQuery3 = new ProjectManpowerSelectQuery3(getDataSource());
		this.projectManpowerInsertQuery = new ProjectManpowerInsertQuery(getDataSource());
		this.projectManpowerUpdateQuery = new ProjectManpowerUpdateQuery(getDataSource());
		//this.projectReportDeleteQuery1 = new ProjectReportDeleteQuery1(getDataSource());
		//this.projectReportDeleteQuery2 = new ProjectReportDeleteQuery2(getDataSource());
	}
	
	protected class ProjectManpowerSelectQuery1 extends MappingSqlQuery {
		protected ProjectManpowerSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,                                  "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName		    "
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectManpower						                                "
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

		protected ProjectManpowerSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setYear(rs.getString("year"));
			projectManpower.setMonth(rs.getString("month"));
			projectManpower.setDay(rs.getString("day"));
			projectManpower.setWeek(rs.getString("week"));
			projectManpower.setWorkSeq(rs.getString("workSeq"));
			projectManpower.setWorkName(rs.getString("workName"));
			return projectManpower;
		}
	}

	protected class ProjectManpowerSelectQuery2 extends ProjectManpowerSelectQuery1 {
		protected ProjectManpowerSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,	                                "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName	    	"
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectManpower						                                "
					+ "				 WHERE 	projectCode = ?						                                "
					+ "			) A LEFT OUTER JOIN ProjectScheduleM B			                                "
					+ "	    ON 	A.projectCode = B.projectCode					                                "
					+ "	   AND 	A.workSeq = B.workSeq							                                "
					+ " ORDER BY 	A.day ASC										                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectManpowerSelectQuery3 extends ProjectManpowerSelectQuery1 {
		protected ProjectManpowerSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, A.week,	                                "
					+ "			( CASE WHEN A.workSeq IS NULL THEN '' ELSE A.workSeq END) AS workSeq,	        "
					+ "			( CASE WHEN A.workName IS NULL THEN '' ELSE A.workName END) AS workName		    "
					+ "	  FROM	                                                                                "
					+ "			(	                                                                            "
					+ "				SELECT 	*													                "
					+ "				  FROM 	ProjectManpower						                                "
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
	
	protected class ProjectManpowerInsertQuery extends SqlUpdate {
		protected ProjectManpowerInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO ProjectManpower ( projectCode, year, month, day, week, workSeq, workName)"
					+ " VALUES (?,   ?,   ?,   ?,   ?,   ?,   ?) ");
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

		protected int insert(ProjectManpower projectManpower) throws DataAccessException {
			return update(new Object[] { projectManpower.getProjectCode(), projectManpower.getYear(), projectManpower.getMonth(),
					projectManpower.getDay(), projectManpower.getWeek(), projectManpower.getWorkSeq(), projectManpower.getWorkName() });
		}
	}	
	
	protected class ProjectManpowerUpdateQuery extends SqlUpdate {
		protected ProjectManpowerUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectManpower set workSeq=?, workName=? "
					+ " WHERE projectCode=? and year=? and month=? and day=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int update(ProjectManpower projectManpower) throws DataAccessException {
			return update(new Object[] { projectManpower.getWorkSeq(), projectManpower.getWorkName(),
					projectManpower.getProjectCode(), projectManpower.getYear(), projectManpower.getMonth(), projectManpower.getDay() });
		}
	}
	
	@Override
	public String getSeqNo(String projectCode, String taskFormTypeId) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(String projectCode, String year, String month) throws DataAccessException {
		return this.getJdbcTemplate().queryForInt("SELECT count(*) AS count FROM projectManpower WHERE projectCode=? AND year=? AND month=?",
				new Object[] { projectCode, year, month });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select(String projectCode) throws DataAccessException {
		return this.projectManpowerSelectQuery2.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select(String projectCode, String year, String month) throws DataAccessException {
		return this.projectManpowerSelectQuery1.execute(new Object[] { projectCode, year, month });
	}

	@Override
	public ProjectManpower select(String projectCode, String year, String month, String day) throws DataAccessException {
		return (ProjectManpower) this.projectManpowerSelectQuery3.findObject(new Object[] { projectCode, year, month, day });
	}

	@Override
	public void insert(ProjectManpower projectManpower) throws DataAccessException {
		this.projectManpowerInsertQuery.insert(projectManpower);
		
	}

	@Override
	public void update(ProjectManpower projectManpower) throws DataAccessException {
		this.projectManpowerUpdateQuery.update(projectManpower);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}
	
}