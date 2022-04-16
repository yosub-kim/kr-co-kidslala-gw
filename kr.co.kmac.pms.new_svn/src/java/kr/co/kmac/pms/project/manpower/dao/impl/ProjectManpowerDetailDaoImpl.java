package kr.co.kmac.pms.project.manpower.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.manpower.dao.ProjectManpowerDetailDao;
import kr.co.kmac.pms.project.manpower.data.ProjectManpower;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectManpowerDetailDaoImpl extends JdbcDaoSupport implements ProjectManpowerDetailDao {
	
	private ProjectManpowerDetailSelectQuery1 projectManpowerDetailSelectQuery1;
	private ProjectManpowerDetailSelectQuery2 projectManpowerDetailSelectQuery2;
	private ProjectManpowerDetailSelectQuery3 projectManpowerDetailSelectQuery3;
	private ProjectManpowerDetailSelectQuery4 projectManpowerDetailSelectQuery4;
	private ProjectManpowerDetailSelectQuery5 projectManpowerDetailSelectQuery5;
	private ProjectManpowerDetailRetrieveQuery1 projectManpowerDetailRetrieveQuery1;
	private ProjectManpowerDetailRetrieveQuery2 projectManpowerDetailRetrieveQuery2;
	private ProjectManpowerDetailRetrieveQuery3 projectManpowerDetailRetrieveQuery3;
	private ProjectManpowerDetailRetrieveQuery4 projectManpowerDetailRetrieveQuery4;
	private ProjectManpowerDetailRetrieveQuery5 projectManpowerDetailRetrieveQuery5;
	private ProjectManpowerDetailInsertQuery  projectManpowerDetailInsertQuery;
	private ProjectManpowerDetailDeleteQuery1 projectManpowerDetailDeleteQuery1;
	private ProjectManpowerDetailDeleteQuery2 projectManpowerDetailDeleteQuery2;
	private ProjectManpowerDetailDeleteQuery3 projectManpowerDetailDeleteQuery3;
	private ProjectManpowerDetailDeleteQuery4 projectManpowerDetailDeleteQuery4;
	
	
	
	@Override
	protected void initDao() throws Exception {
		this.projectManpowerDetailSelectQuery1 = new ProjectManpowerDetailSelectQuery1(getDataSource());
		this.projectManpowerDetailSelectQuery2 = new ProjectManpowerDetailSelectQuery2(getDataSource());
		this.projectManpowerDetailSelectQuery3 = new ProjectManpowerDetailSelectQuery3(getDataSource());
		this.projectManpowerDetailSelectQuery4 = new ProjectManpowerDetailSelectQuery4(getDataSource());
		this.projectManpowerDetailSelectQuery5 = new ProjectManpowerDetailSelectQuery5(getDataSource());
		this.projectManpowerDetailRetrieveQuery1 = new ProjectManpowerDetailRetrieveQuery1(getDataSource());
		this.projectManpowerDetailRetrieveQuery2 = new ProjectManpowerDetailRetrieveQuery2(getDataSource());
		this.projectManpowerDetailRetrieveQuery3 = new ProjectManpowerDetailRetrieveQuery3(getDataSource());
		this.projectManpowerDetailRetrieveQuery4 = new ProjectManpowerDetailRetrieveQuery4(getDataSource());
		this.projectManpowerDetailRetrieveQuery5 = new ProjectManpowerDetailRetrieveQuery5(getDataSource());
		this.projectManpowerDetailInsertQuery  = new ProjectManpowerDetailInsertQuery(getDataSource());
		this.projectManpowerDetailDeleteQuery1 = new ProjectManpowerDetailDeleteQuery1(getDataSource());
		this.projectManpowerDetailDeleteQuery2 = new ProjectManpowerDetailDeleteQuery2(getDataSource());
		this.projectManpowerDetailDeleteQuery3 = new ProjectManpowerDetailDeleteQuery3(getDataSource());
		this.projectManpowerDetailDeleteQuery4 = new ProjectManpowerDetailDeleteQuery4(getDataSource());
	}
	
	protected class ProjectManpowerDetailSelectQuery1 extends MappingSqlQuery {
		protected ProjectManpowerDetailSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name	"
					+ "	  FROM 	ProjectManpowerDetail A, expertPool B                   "
					+ "	 WHERE 	A.chargeSsn = B.ssn					                    "
					+ "	   AND 	A.projectCode = ?                                       "
					+ "	   AND 	A.year = ?                                              "
					+ "	   AND 	A.month = ?		                                        "
					+ "	   AND 	A.day = ?		                                        "
					+ "	   AND 	A.chargeSsn = ?                                         ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectManpowerDetailSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setYear(rs.getString("year"));
			projectManpower.setMonth(rs.getString("month"));
			projectManpower.setDay(rs.getString("day"));
			projectManpower.setSsn(rs.getString("ssn"));
			projectManpower.setName(rs.getString("name"));
			return projectManpower;
		}
	}
	
	protected class ProjectManpowerDetailSelectQuery2 extends ProjectManpowerDetailSelectQuery1 {
		protected ProjectManpowerDetailSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name		"
					+ "	  FROM 	ProjectManpowerDetail A, expertPool B			            "
					+ "	 WHERE 	1=1                                   						"
					+ "	   AND	A.chargeSsn = B.ssn					                        "
					+ "	   AND 	A.projectCode = ?                                           "
					+ "	   AND 	A.year = ?                                                  "
					+ "	   AND 	A.month = ?		                                            "
					+ "	   AND 	A.day = ?		                                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectManpowerDetailSelectQuery3 extends ProjectManpowerDetailSelectQuery1 {
		protected ProjectManpowerDetailSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name		"
					+ "	  FROM 	ProjectManpowerDetail A, expertPool B						"
					+ "	 WHERE 	1=1                                   						"
					+ "	   AND	A.chargeSsn = B.ssn					                        "
					+ "	   AND 	A.projectCode = ?                                           "
					+ "	   AND 	A.year = ?                                                  "
					+ "	   AND 	A.month = ?		                                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectManpowerDetailSelectQuery4 extends ProjectManpowerDetailSelectQuery1 {
		protected ProjectManpowerDetailSelectQuery4(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name		"
					+ "	  FROM 	ProjectManpowerDetail A, expertPool B						"
					+ "	 WHERE 	1=1                                   						"
					+ "	   AND	A.chargeSsn = B.ssn					                        "
					+ "	   AND 	A.projectCode = ?                                           ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	

	protected class ProjectManpowerDetailRetrieveQuery1 extends ProjectManpowerDetailSelectQuery1 {
		protected ProjectManpowerDetailRetrieveQuery1(DataSource ds) {
			super(ds, "	SELECT 	A.projectCode, A.year, A.month, A.day, B.ssn, B.name		"
					+ "	  FROM 	ProjectManpowerDetail A, expertPool B						"
					+ "	 WHERE 	1=1                                   						"
					+ "	   AND	A.chargeSsn = B.ssn					                        "
					+ "	   AND 	A.projectCode = ?                                           "
					+ "	   and	(a.year + a.month + a.day) between ? and ?                  ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ProjectManpowerDetailRetrieveQuery2 extends MappingSqlQuery {
		protected ProjectManpowerDetailRetrieveQuery2(DataSource ds) {
			super(ds, "	select e.jobClass, p.projectCode, p.ssn, p.role, p.position, e.companyPositionName, e.name "
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
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setSsn(rs.getString("ssn"));
			projectManpower.setRole(rs.getString("role"));
			projectManpower.setPosition(rs.getString("position"));
			projectManpower.setCompanyPositionName(rs.getString("companyPositionName"));
			projectManpower.setName(rs.getString("name"));
			return projectManpower;
		}
	}
	
	protected class ProjectManpowerDetailRetrieveQuery3 extends MappingSqlQuery {
		protected ProjectManpowerDetailRetrieveQuery3(DataSource ds) {
			super(ds,"	select jobClass, projectCode, ssn, name, year, month, day, workName, "
					+ " (case when workName = '' then '' else row_number() over(partition by ssn, workname order by day) end) as rn, (case when workName = '' then '' else count(*) over(partition by ssn, workName) end) as rn2 From ("		
					+ "	select e.jobClass, p.projectCode, e.ssn, e.name, pm.year, pm.month, pm.day, isnull(pr.workName, '') as workName, position   "
					+ " from projectmember p "
					+ " left join(	select ssn, jobClass, companyPositionName, name from expertpool)e on p.ssn = e.ssn "
					+ " left join(	select * From ProjectManpower)pm on p.projectCode = pm.projectCode "
					+ " left join(	select * From ProjectManpowerDetail )pmd on p.projectCode = pmd.projectCode and pm.year = pmd.year and pm.month = pmd.month and pm.day = pmd.day and p.ssn = pmd.chargeSsn"
					+ " left join("
					+ " select p.projectCode, p.year, p.month, p.day, p.workName, pm.chargeSsn as ssn "
					+ " from ProjectManpower p  "
					+ " left join  "
					+ "( "  
					+ " select * from ProjectManpowerDetail where projectCode= ? and year= ? and month= ? " 
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
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setYear(rs.getString("year"));
			projectManpower.setMonth(rs.getString("month"));
			projectManpower.setDay(rs.getString("day"));
			projectManpower.setWorkName(rs.getString("workName"));
			projectManpower.setSsn(rs.getString("ssn"));
			projectManpower.setRn(rs.getString("rn"));
			projectManpower.setRn2(rs.getString("rn2"));
			return projectManpower;
		}
	}
	
	protected class ProjectManpowerDetailRetrieveQuery4 extends MappingSqlQuery {
		protected ProjectManpowerDetailRetrieveQuery4(DataSource ds) {
			super(ds, "	select p.projectCode, p.projectName, pm.role "
					+ "	from project p "
					+ "	left join "
					+ "	( "
					+ "		select * from projectmember "
					+ "	)pm "
					+ "	on p.projectCode = pm.projectCode "
					+ "	where pm.ssn = ? "
					+ "	and p.projectState < '6' "
					+ "	order by projectCode desc ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setProjectName(rs.getString("projectName"));
			projectManpower.setRole(rs.getString("role"));
			return projectManpower;
		}
	}
	
	protected class ProjectManpowerDetailRetrieveQuery5 extends MappingSqlQuery {
		protected ProjectManpowerDetailRetrieveQuery5(DataSource ds) {
			super(ds, "	select projectCode, workName, workSeq From projectScheduleM where projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setWorkName(rs.getString("workName"));
			projectManpower.setWorkSeq(rs.getString("workSeq"));
			return projectManpower;
		}
	}
	
	protected class ProjectManpowerDetailInsertQuery extends SqlUpdate {
		protected ProjectManpowerDetailInsertQuery(DataSource ds) {
			super(ds, "	INSERT INTO	ProjectManpowerDetail (projectCode, year, month, day, chargeSsn)  "
					+ "	VALUES	(?,   ?,   ?,   ?,   ?)	                              ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectManpower projectManpower) throws DataAccessException {
			return this.update(new Object[] { projectManpower.getProjectCode(), projectManpower.getYear(), projectManpower.getMonth(),
					projectManpower.getDay(), projectManpower.getSsn() });
		}
	}

	protected class ProjectManpowerDetailDeleteQuery1 extends SqlUpdate {
		protected ProjectManpowerDetailDeleteQuery1(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectManpowerDetail WHERE	projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectManpowerDetailDeleteQuery2 extends SqlUpdate {
		protected ProjectManpowerDetailDeleteQuery2(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectManpowerDetail WHERE	projectCode = ? AND	year = ? AND month = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String year, String month) throws DataAccessException {
			return this.update(new Object[] { projectCode, year, month });
		}
	}

	protected class ProjectManpowerDetailDeleteQuery3 extends SqlUpdate {
		protected ProjectManpowerDetailDeleteQuery3(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectManpowerDetail WHERE	projectCode = ? AND	year = ? AND year = ? AND day=?");
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

	protected class ProjectManpowerDetailDeleteQuery4 extends SqlUpdate {
		protected ProjectManpowerDetailDeleteQuery4(DataSource ds) {
			super(ds, "	DELETE FROM	ProjectManpowerDetail WHERE	projectCode = ? AND	year = ? AND month = ? AND day=? AND chargeSsn=?");
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
	
	protected class ProjectManpowerDetailSelectQuery5 extends MappingSqlQuery {
		protected ProjectManpowerDetailSelectQuery5(DataSource ds){
			super(ds, " select * From ( "
					+" select p.projectCode, p.year, p.month, p.workName, pm.chargeSsn as ssn, ROW_NUMBER() OVER (PARTITION BY p.projectCode, p.workName, pm.chargeSsn ORDER BY p.projectCode ASC) as rn " 
					+" from ProjectManpower p "
					+" left join "
					+" ( "
					+"	select * from ProjectManpowerDetail where projectCode= ? and year= ? and month= ? "
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
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectManpower projectManpower = new ProjectManpower();
			projectManpower.setProjectCode(rs.getString("projectCode"));
			projectManpower.setYear(rs.getString("year"));
			projectManpower.setMonth(rs.getString("month"));
			projectManpower.setWorkName(rs.getString("workName"));
			projectManpower.setSsn(rs.getString("ssn"));
			return projectManpower;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select(String projectCode) throws DataAccessException {
		return this.projectManpowerDetailSelectQuery4.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select(String projectCode, String year, String month) throws DataAccessException {
		return this.projectManpowerDetailSelectQuery3.execute(new Object[] { projectCode, year, month });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select(String projectCode, String year, String month, String day) throws DataAccessException {
		return this.projectManpowerDetailSelectQuery2.execute(new Object[] { projectCode, year, month, day });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> select2(String projectCode, String year, String month) throws DataAccessException {
		return this.projectManpowerDetailSelectQuery5.execute(new Object[] { projectCode, year, month, projectCode, year, month });
	}

	@Override
	public ProjectManpower select(String projectCode, String year, String month, String day, String ssn) throws DataAccessException {
		Object object = this.projectManpowerDetailSelectQuery1.findObject(new Object[] { projectCode, year, month, day, ssn });
		return (ProjectManpower) object;
	}
	
	@Override
	public void insert(ProjectManpower projectManpower) throws DataAccessException {
		this.projectManpowerDetailInsertQuery.insert(projectManpower);		
	}

	@Override
	public void update(ProjectManpower projectManpower) throws DataAccessException {
		// do nothing
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectManpowerDetailDeleteQuery1.delete(projectCode);
		
	}

	@Override
	public void delete(String projectCode, String year, String month) throws DataAccessException {
		this.projectManpowerDetailDeleteQuery2.delete(projectCode, year, month);
		
	}

	@Override
	public void delete(String projectCode, String year, String month, String day) throws DataAccessException {
		this.projectManpowerDetailDeleteQuery3.delete(projectCode, year, month, day);
	}

	@Override
	public void delete(String projectCode, String year, String month, String day, String ssn) throws DataAccessException {
		this.projectManpowerDetailDeleteQuery4.delete(projectCode, year, month, day, ssn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> retrieve(String projectCode, String startDate, String endDate) throws DataAccessException {
		return this.projectManpowerDetailRetrieveQuery1.execute(new Object[] { projectCode, startDate, endDate });
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> retrieve2(String projectCode){
		return this.projectManpowerDetailRetrieveQuery2.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> retrieve3(String projectCode, String year, String month){
		return this.projectManpowerDetailRetrieveQuery3.execute(new Object[] {projectCode, year, month, projectCode, year, month, projectCode, year, month});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> retrieve4(String projectCode){
		return this.projectManpowerDetailRetrieveQuery4.execute(new Object[] {projectCode});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectManpower> retrieve5(String projectCode){
		return this.projectManpowerDetailRetrieveQuery5.execute(new Object[] {projectCode});
	}
	
	
}