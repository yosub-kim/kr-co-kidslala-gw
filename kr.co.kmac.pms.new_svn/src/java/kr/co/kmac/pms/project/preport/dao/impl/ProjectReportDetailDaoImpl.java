package kr.co.kmac.pms.project.preport.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;
import kr.co.kmac.pms.project.preport.dao.ProjectReportDetailDao;
import kr.co.kmac.pms.project.preport.data.ProjectReportPlan;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectReportDetailDaoImpl extends JdbcDaoSupport implements ProjectReportDetailDao {
	private ProjectReportDetailInsertQuery projectReportDetailInsertQuery;
	private ProjectReportDetailUpdateQuery projectReportDetailUpdateQuery;
	private ProjectReportDetailSelectQuery1 projectReportDetailSelectQuery1;
	private ProjectReportDetailSelectQuery2 projectReportDetailSelectQuery2;
	private ProjectReportDetailSelectQuery3 projectReportDetailSelectQuery3;
	private ProjectReportDetailSelectQuery4 projectReportDetailSelectQuery4;
	private ProjectReportDetailSelectQuery5 projectReportDetailSelectQuery5;
	private ProjectReportDetailSelectQuery6 projectReportDetailSelectQuery6;
	private ProjectReportDetailSelectQuery7 projectReportDetailSelectQuery7;
	private ProjectReportDetailDeleteQuery1 projectReportDetailDeleteQuery1;
	private ProjectReportDetailDeleteQuery2 projectReportDetailDeleteQuery2;
	private ProjectReportDetailDeleteQuery3 projectReportDetailDeleteQuery3;
	private ProjectReportDetailDeleteQuery4 projectReportDetailDeleteQuery4;

	@Override
	protected void initDao() throws Exception {
		this.projectReportDetailInsertQuery = new ProjectReportDetailInsertQuery(getDataSource());
		this.projectReportDetailUpdateQuery = new ProjectReportDetailUpdateQuery(getDataSource());
		this.projectReportDetailSelectQuery1 = new ProjectReportDetailSelectQuery1(getDataSource());
		this.projectReportDetailSelectQuery2 = new ProjectReportDetailSelectQuery2(getDataSource());
		this.projectReportDetailSelectQuery3 = new ProjectReportDetailSelectQuery3(getDataSource());
		this.projectReportDetailSelectQuery4 = new ProjectReportDetailSelectQuery4(getDataSource());
		this.projectReportDetailSelectQuery5 = new ProjectReportDetailSelectQuery5(getDataSource());
		this.projectReportDetailSelectQuery6 = new ProjectReportDetailSelectQuery6(getDataSource());
		this.projectReportDetailSelectQuery7 = new ProjectReportDetailSelectQuery7(getDataSource());
		this.projectReportDetailDeleteQuery1 = new ProjectReportDetailDeleteQuery1(getDataSource());
		this.projectReportDetailDeleteQuery2 = new ProjectReportDetailDeleteQuery2(getDataSource());
		this.projectReportDetailDeleteQuery3 = new ProjectReportDetailDeleteQuery3(getDataSource());
		this.projectReportDetailDeleteQuery4 = new ProjectReportDetailDeleteQuery4(getDataSource());
	}

	protected class ProjectReportDetailInsertQuery extends SqlUpdate {
		protected ProjectReportDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO	ProjectReportDetail (projectCode, year, month, day, chargeSsn, time)  "
					+ "	VALUES	(?,   ?,   ?,   ?,   ?,   ?)	                              ");
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
			return this.update(new Object[] { projectReportPlan.getProjectCode(), projectReportPlan.getYear(), projectReportPlan.getMonth(),
					projectReportPlan.getDay(), projectReportPlan.getSsn(), projectReportPlan.getTime() });
		}
	}

	protected class ProjectReportDetailUpdateQuery extends SqlUpdate {
		protected ProjectReportDetailUpdateQuery(DataSource ds) {
			super(ds, "	UPDATE ProjectReportDetail set time=?  WHERE projectCode=? AND year=? AND month=? AND day=? AND chargeSsn=?");
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
			return this.update(new Object[] { projectReportPlan.getTime(), projectReportPlan.getProjectCode(), projectReportPlan.getYear(),
					projectReportPlan.getMonth(), projectReportPlan.getDay(), projectReportPlan.getSsn() });
		}
	}

	protected class ProjectReportDetailDeleteQuery1 extends SqlUpdate {
		protected ProjectReportDetailDeleteQuery1(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectReportDetail WHERE	projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectReportDetailDeleteQuery2 extends SqlUpdate {
		protected ProjectReportDetailDeleteQuery2(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectReportDetail WHERE	projectCode = ? AND	year = ? AND month = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month });
		}
	}

	protected class ProjectReportDetailDeleteQuery3 extends SqlUpdate {
		protected ProjectReportDetailDeleteQuery3(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectReportDetail WHERE	projectCode = ? AND	year = ? AND year = ? AND day=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String day) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, day });
		}
	}

	protected class ProjectReportDetailDeleteQuery4 extends SqlUpdate {
		protected ProjectReportDetailDeleteQuery4(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectReportDetail WHERE	projectCode = ? AND	year = ? AND month = ? AND day=? AND chargeSsn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month, String day, String chargeSsn) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month, day, chargeSsn });
		}
	}

	protected class ProjectReportDetailSelectQuery1 extends MappingSqlQuery {
		protected ProjectReportDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name, A.time "
					+ "	  FROM 	ProjectReportDetail A, expertPool B                          "
					+ "	 WHERE 	A.chargeSsn = B.ssn					                         "
					+ "	   AND 	A.projectCode = ?                                            "
					+ "	   AND 	A.year = ?                                                   "
					+ "	   AND 	A.month = ?		                                             "
					+ "	   AND 	A.day = ?		                                             "
					+ "	   AND 	A.chargeSsn = ?                                              ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectReportDetailSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportPlan projectReportPlan = new ProjectReportPlan();
			projectReportPlan.setProjectCode(rs.getString("projectCode"));
			projectReportPlan.setYear(rs.getString("year"));
			projectReportPlan.setMonth(rs.getString("month"));
			projectReportPlan.setDay(rs.getString("day"));
			projectReportPlan.setSsn(rs.getString("ssn"));
			projectReportPlan.setName(rs.getString("name"));
			projectReportPlan.setTime(StringUtil.isNull(rs.getString("time"), "0"));
			return projectReportPlan;
		}
	}

	protected class ProjectReportDetailSelectQuery2 extends ProjectReportDetailSelectQuery1 {
		protected ProjectReportDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name, A.time, C.businessTypeCode "
					+ "	  FROM 	ProjectReportDetail A, expertPool B, project C	                  "
					+ "	 WHERE 	A.projectCode = C.projectCode                                     "
					+ "	   AND	A.chargeSsn = B.ssn					                              "
					+ "	   AND 	A.projectCode = ?                                                 "
					+ "	   AND 	A.year = ?                                                        "
					+ "	   AND 	A.month = ?		                                                  "
					+ "	   AND 	A.day = ?		                                                  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectReportDetailSelectQuery3 extends ProjectReportDetailSelectQuery1 {
		protected ProjectReportDetailSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name, A.time, C.businessTypeCode "
					+ "	  FROM 	ProjectReportDetail A, expertPool B, project C	                  "
					+ "	 WHERE 	A.projectCode = C.projectCode                                     "
					+ "	   AND	A.chargeSsn = B.ssn					                              "
					+ "	   AND 	A.projectCode = ?                                                 "
					+ "	   AND 	A.year = ?                                                        "
					+ "	   AND 	A.month = ?		                                                  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectReportDetailSelectQuery4 extends ProjectReportDetailSelectQuery1 {
		protected ProjectReportDetailSelectQuery4(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name, A.time, C.businessTypeCode "
					+ "	  FROM 	ProjectReportDetail A, expertPool B, project C	                  "
					+ "	 WHERE 	A.projectCode = C.projectCode                                     "
					+ "	   AND	A.chargeSsn = B.ssn					                              "
					+ "	   AND 	A.projectCode = ?                                                 ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ProjectReportDetailSelectQuery5 extends MappingSqlQuery {
		protected ProjectReportDetailSelectQuery5(DataSource ds) {
			super(ds, " select e.jobClass, p.projectCode, p.ssn, p.role, p.position, e.companyPositionName, e.name "
					+ " from projectmember p "
					+ " left join( "
					+ " select ssn, jobClass, companyPositionName, name from expertpool "
					+ " )e on p.ssn = e.ssn"
					+ " where p.projectCode= ? and jobClass in ('A','B','J','C','N') and role not in ('AR','QM','PM')"
					+ " order by (case when jobclass='A' then '1' when jobClass='B' then '2' when jobClass='J' then '3' when jobClass='C' then '4' else '5' end)");
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportPlan projectReportPlan = new ProjectReportPlan();
			projectReportPlan.setProjectCode(rs.getString("projectCode"));
			projectReportPlan.setSsn(rs.getString("ssn"));
			projectReportPlan.setRole(rs.getString("role"));
			projectReportPlan.setPosition(rs.getString("position"));
			projectReportPlan.setCompanyPositionName(rs.getString("companyPositionName"));
			projectReportPlan.setName(rs.getString("name"));
			return projectReportPlan;
		}
	}
	
	protected class ProjectReportDetailSelectQuery6 extends MappingSqlQuery {
		protected ProjectReportDetailSelectQuery6(DataSource ds) {
			super(ds, " select * From ( "
					+" select p.projectCode, p.year, p.month, p.workName, pm.chargeSsn as ssn, ROW_NUMBER() OVER (PARTITION BY p.projectCode, p.workName, pm.chargeSsn ORDER BY p.projectCode ASC) as rn " 
					+" from ProjectReport p "
					+" left join "
					+" ( "
					+"	select * from ProjectReportDetail where projectCode= ? and year= ? and month= ? "
					+" )pm "
					+" on p.projectCode = pm.projectCode and p.year = pm.year and p.month = pm.month and p.day = pm.day "
					+" where p.projectCode = ? and p.year = ? and p.month = ? and workName != '' "
					+" )r "
					+" where rn = '1' "
					+" order by ssn, workname");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportPlan projectReportPlan = new ProjectReportPlan();
			projectReportPlan.setProjectCode(rs.getString("projectCode"));
			projectReportPlan.setYear(rs.getString("year"));
			projectReportPlan.setMonth(rs.getString("month"));
			projectReportPlan.setSsn(rs.getString("ssn"));
			projectReportPlan.setWorkName(rs.getString("workName"));
			return projectReportPlan;
		}
	}
	
	protected class ProjectReportDetailSelectQuery7 extends MappingSqlQuery {
		protected ProjectReportDetailSelectQuery7(DataSource ds) {
			super(ds,"	select jobClass, projectCode, ssn, name, year, month, day, workName, "
					+ " (case when workName = '' then '' else row_number() over(partition by ssn, workname order by day) end) as rn, (case when workName = '' then '' else count(*) over(partition by ssn, workName) end) as rn2 From ("		
					+ "	select e.jobClass, p.projectCode, e.ssn, e.name, pm.year, pm.month, pm.day, isnull(pr.workName, '') as workName, position   "
					+ " from projectmember p "
					+ " left join(	select ssn, jobClass, companyPositionName, name from expertpool)e on p.ssn = e.ssn "
					+ " left join(	select * From ProjectReport)pm on p.projectCode = pm.projectCode "
					+ " left join(	select * From ProjectReportDetail )pmd on p.projectCode = pmd.projectCode and pm.year = pmd.year and pm.month = pmd.month and pm.day = pmd.day and p.ssn = pmd.chargeSsn"
					+ " left join("
					+ " select p.projectCode, p.year, p.month, p.day, p.workName, pm.chargeSsn as ssn "
					+ " from ProjectReport p  "
					+ " left join  "
					+ "( "  
					+ " select * from ProjectReportDetail where projectCode= ? and year= ? and month= ? " 
					+ " )pm  "
					+ "on p.projectCode = pm.projectCode and p.year = pm.year and p.month = pm.month and p.day = pm.day "
					+ "where p.projectCode = ? and p.year = ? and p.month = ? and workName != '' "
					+ " )pr"
					+ " on pm.projectCode = pr.projectCode and pm.year = pr.year and  pm.month = pr.month and pm.day = pr.day and p.ssn= pr.ssn "
					+ " where p.projectCode=? and jobClass in ('A','B','J','C') and role not in ('AR','QM','PM') and pm.year=? and pm.month=? "
					+ "  )ps "
				    + "order by ssn, year, month, day, jobClass, position");
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

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReportPlan projectReportPlan = new ProjectReportPlan();
			projectReportPlan.setProjectCode(rs.getString("projectCode"));
			projectReportPlan.setYear(rs.getString("year"));
			projectReportPlan.setMonth(rs.getString("month"));
			projectReportPlan.setDay(rs.getString("day"));
			projectReportPlan.setWorkName(rs.getString("workName"));
			projectReportPlan.setSsn(rs.getString("ssn"));
			projectReportPlan.setRn(rs.getString("rn"));
			projectReportPlan.setRn2(rs.getString("rn2"));
			return projectReportPlan;
		}
	}

	@Override
	public void delete(String projectCode, String year, String month, String day, String ssn) throws DataAccessException {
		this.projectReportDetailDeleteQuery4.delete(projectCode, year, month, day, ssn);
	}

	@Override
	public void delete(String projectCode, String year, String month, String day) throws DataAccessException {
		this.projectReportDetailDeleteQuery3.delete(projectCode, year, month, day);
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		this.projectReportDetailDeleteQuery2.delete(projectCode, year, month);
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectReportDetailDeleteQuery1.delete(projectCode);
	}

	@Override
	public void insert(ProjectReportPlan projectReportPlan) throws DataAccessException {
		this.projectReportDetailInsertQuery.insert(projectReportPlan);
	}

	@Override
	public void update(ProjectReportPlan projectReportPlan) throws DataAccessException {
		this.projectReportDetailUpdateQuery.update(projectReportPlan);
	}

	@Override
	public ProjectReportPlan select(String projectCode, String year, String month, String day, String ssn) throws DataAccessException {
		Object object = this.projectReportDetailSelectQuery1.findObject(new Object[] { projectCode, year, month, day, ssn });
		return (ProjectReportPlan) object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> select(String projectCode, String year, String month, String day) throws DataAccessException {
		return this.projectReportDetailSelectQuery2.execute(new Object[] { projectCode, year, month, day });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> select(String projectCode, String year, String month) throws DataAccessException {
		return this.projectReportDetailSelectQuery3.execute(new Object[] { projectCode, year, month });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> select(String projectCode) throws DataAccessException {
		return this.projectReportDetailSelectQuery4.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> selectProjectMember(String projectCode) throws DataAccessException {
		return this.projectReportDetailSelectQuery5.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> selectProjectWorkName(String projectCode, String year, String month) throws DataAccessException {
		return this.projectReportDetailSelectQuery6.execute(new Object[] { projectCode, year, month, projectCode, year, month });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectReportPlan> selectProjectMonthWork(String projectCode, String year, String month) throws DataAccessException {
		return this.projectReportDetailSelectQuery7.execute(new Object[] { projectCode, year, month, projectCode, year, month, projectCode, year, month });
	}

}
