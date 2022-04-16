package kr.co.kmac.pms.project.master.dao.impl;

import helma.objectmodel.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.project.master.dao.ProjectMemberDao;
import kr.co.kmac.pms.project.master.data.Project;
import kr.co.kmac.pms.project.master.data.ProjectMember;
import kr.co.kmac.pms.project.statistics.data.ProjectMemberMonthlyMM;
import kr.co.kmac.pms.sanction.projectchange.data.RunningMemberChange;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectMemberDaoImpl extends JdbcDaoSupport implements ProjectMemberDao {
	private ProjectMemberInsertQuery1 projectMemberInsertQuery1;
	private ProjectMemberInsertQuery2 projectMemberInsertQuery2;
	private ProjectMemberInsertQueryForMMInfo projectMemberInsertQueryForMMInfo;
	private ProjectMemberInsertQueryForMMInfoByTime projectMemberInsertQueryForMMInfoByTime;
	private ProjectMemberInsertQueryForMMInfoByProject projectMemberInsertQueryForMMInfoByProject;
	private ProjectMemberInsertQueryForMMInfoByPerson projectMemberInsertQueryForMMInfoByPerson;
	private ProjectMemberInsertQueryForMMInfoModifiedLog projectMemberInsertQueryForMMInfoModifiedLog;
	private ProjectMemberUpdateQuery projectMemberUpdateQuery;
	private ProjectMemberUpdateQueryForMemberChange projectMemberUpdateQueryForMemberChange;
	private ProjectMemberUpdateQueryForMMInfo projectMemberUpdateQueryForMMInfo;  
	private ProjectMemberSelectQuery1 projectMemberSelectQuery1;
	private ProjectMemberSelectQuery2 projectMemberSelectQuery2;
	private ProjectMemberSelectQuery3 projectMemberSelectQuery3;
	private ProjectCodeSelectQuery1 projectCodeSelectQuery1;
	private ProjectMemberSelectQueryOnlyMember projectMemberSelectQueryOnlyMember;
	private ProjectMemberSelectForExpenseQuery projectMemberSelectForExpenseQuery;
	private ProjectMemberSelectForExpenseQuery2 projectMemberSelectForExpenseQuery2;
	private ProjectMemberSelectQueryForMMInfo projectMemberSelectQueryForMMInfo; 
	private ProjectMemberPUSelectForExpenseQuery projectMemberPUSelectForExpenseQuery;
	private ProjectMemberSelectAllForExpenseQuery projectMemberSelectAllForExpenseQuery;
	private ProjectMemberDeleteQuery1 projectMemberDeleteQuery1;
	private ProjectMemberDeleteQuery2 projectMemberDeleteQuery2;
	private ProjectMemberDeleteQueryForMMInfo projectMemberDeleteQueryForMMInfo;
	private ProjectMemberDeleteQueryForMMInfoByPerson projectMemberDeleteQueryForMMInfoByPerson;
	private ProjectMemberDeleteQueryForMMInfoByTime projectMemberDeleteQueryForMMInfoByTime;
	private ProjectMemberDeleteQueryForMMInfoByTimeAndPerson projectMemberDeleteQueryForMMInfoByTimeAndPerson;
	private ProjectMemberDeleteQueryForMMInfoByProject projectMemberDeleteQueryForMMInfoByProject;
	private ProjectMemberScheduleSelectForExpenseQuery projectMemberScheduleSelectForExpenseQuery;
	private ProjectScheduleReportSelectForExpenseQuery projectScheduleReportSelectForExpenseQuery;
	private ProjectMonthlyReportSelectForExpenseQuery projectMonthlyReportSelectForExpenseQuery;
	
	@Override
	protected void initDao() throws Exception {
		this.projectMemberInsertQuery1 = new ProjectMemberInsertQuery1(getDataSource());
		this.projectMemberInsertQuery2 = new ProjectMemberInsertQuery2(getDataSource());
		this.projectMemberInsertQueryForMMInfo = new ProjectMemberInsertQueryForMMInfo(getDataSource());
		this.projectMemberInsertQueryForMMInfoByTime = new ProjectMemberInsertQueryForMMInfoByTime(getDataSource());
		this.projectMemberInsertQueryForMMInfoByProject = new ProjectMemberInsertQueryForMMInfoByProject(getDataSource());
		this.projectMemberInsertQueryForMMInfoByPerson = new ProjectMemberInsertQueryForMMInfoByPerson(getDataSource());
		this.projectMemberInsertQueryForMMInfoModifiedLog = new ProjectMemberInsertQueryForMMInfoModifiedLog(getDataSource());
		this.projectMemberUpdateQuery = new ProjectMemberUpdateQuery(getDataSource());
		this.projectMemberUpdateQueryForMemberChange = new ProjectMemberUpdateQueryForMemberChange(getDataSource());
		this.projectMemberUpdateQueryForMMInfo = new ProjectMemberUpdateQueryForMMInfo(getDataSource());
		this.projectMemberSelectQuery1 = new ProjectMemberSelectQuery1(getDataSource());
		this.projectMemberSelectQuery2 = new ProjectMemberSelectQuery2(getDataSource());
		this.projectMemberSelectQuery3 = new ProjectMemberSelectQuery3(getDataSource());
		this.projectCodeSelectQuery1 = new ProjectCodeSelectQuery1(getDataSource());
		this.projectMemberSelectQueryOnlyMember = new ProjectMemberSelectQueryOnlyMember(getDataSource());
		this.projectMemberSelectQueryForMMInfo = new ProjectMemberSelectQueryForMMInfo(getDataSource());
		this.projectMemberSelectForExpenseQuery = new ProjectMemberSelectForExpenseQuery(getDataSource());
		this.projectMemberSelectForExpenseQuery2 = new ProjectMemberSelectForExpenseQuery2(getDataSource());
		this.projectMemberPUSelectForExpenseQuery = new ProjectMemberPUSelectForExpenseQuery(getDataSource());
		this.projectMemberSelectAllForExpenseQuery = new ProjectMemberSelectAllForExpenseQuery(getDataSource());
		this.projectMemberDeleteQuery1 = new ProjectMemberDeleteQuery1(getDataSource());
		this.projectMemberDeleteQuery2 = new ProjectMemberDeleteQuery2(getDataSource());
		this.projectMemberDeleteQueryForMMInfo = new ProjectMemberDeleteQueryForMMInfo(getDataSource());
		this.projectMemberDeleteQueryForMMInfoByPerson = new ProjectMemberDeleteQueryForMMInfoByPerson(getDataSource());
		this.projectMemberDeleteQueryForMMInfoByTime = new ProjectMemberDeleteQueryForMMInfoByTime(getDataSource());
		this.projectMemberDeleteQueryForMMInfoByTimeAndPerson = new ProjectMemberDeleteQueryForMMInfoByTimeAndPerson(getDataSource());
		this.projectMemberDeleteQueryForMMInfoByProject = new ProjectMemberDeleteQueryForMMInfoByProject(getDataSource());
		this.projectScheduleReportSelectForExpenseQuery = new ProjectScheduleReportSelectForExpenseQuery(getDataSource());
		this.projectMonthlyReportSelectForExpenseQuery = new ProjectMonthlyReportSelectForExpenseQuery(getDataSource());
			}

	protected class ProjectMemberInsertQuery1 extends SqlUpdate {
		protected ProjectMemberInsertQuery1(DataSource ds) {
			super(ds, "INSERT INTO ProjectMember                                                                           "
					+ "	(projectCode, ssn, role,    cost, trainingYN, contributionCost,    teachingDay, resRate, position,	startDate, endDate) "
					+ "VALUES (  ?, ?, ?,   ?, ?, ?,   ?, ?, ?,		?, ?  )                            ");
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
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectMember projectMember) throws DataAccessException {
			return this.update(new Object[] { projectMember.getProjectCode(), projectMember.getSsn(), projectMember.getRole(),
					projectMember.getCost().replace(",", "").replace(" ", ""), projectMember.getTrainingYn(), projectMember.getContributionCost(), projectMember.getTeachingDay(),
					projectMember.getResRate(), projectMember.getPosition(),
					projectMember.getStartDate(), projectMember.getEndDate()});
		}
	}

	protected class ProjectMemberInsertQuery2 extends BatchSqlUpdate{
		protected ProjectMemberInsertQuery2(DataSource ds) {
			super(ds, "INSERT INTO ProjectMember                                                                           "
					+ "	(projectCode, ssn, role,    cost, trainingYN, contributionCost,    teachingDay, resRate, position,		startDate, endDate) "
					+ "VALUES (  ?, ?, ?,   ?, ?, ?,   ?, ?, dbo.getExpertCompanyPosition(?),		?, ?    )                            ");
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
			setRequiredRowsAffected(1);
		}
		
		protected int insert(List<RunningMemberChange> projectMemberList) throws DataAccessException {
			for (RunningMemberChange projectMember : projectMemberList) {
				this.update(new Object[] { projectMember.getProjectCode(), projectMember.getRunningMemberSsn(), projectMember.getRunningMemberRole(),
						projectMember.getRunningMemberCost().replace(",", "").replace(" ", ""), projectMember.getRunningMemberTrainingYn(), projectMember.getRunningMemberContributionCost(), 
						projectMember.getRunningMemberTeachingDay(), projectMember.getRunningMemberResRate(), projectMember.getRunningMemberSsn(),
						projectMember.getRunningMemberStartDate(), projectMember.getRunningMemberEndDate() });	
			}
			return flush().length;
		}
	}
	
	protected class ProjectMemberInsertQueryForMMInfo extends SqlUpdate {
		protected ProjectMemberInsertQueryForMMInfo(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMemberMMPlan		"
					+ "	SELECT MST.PROJID AS projectCode, MM.EMPNO as ssn, LEFT(MM.YYYYMM,4) AS [year], RIGHT(MM.YYYYMM, 2) AS [month],	"
					+ "			(CASE MST.OPTYPE											"
					+ "				WHEN '03' THEN	MM.WORKMD								"
					+ "				WHEN '02' THEN	MM.WORKMD								"
					+ "				WHEN '01' THEN	MM.WORKMD								"
					+ "			ELSE 0 END) mmValue, 'Y' as trainingYN, 'N' as checkYN		"
					+ "	FROM (																"
					+ "			SELECT  ENTNO, EMPNO, YYYYMM, SUM(WORKMD) WORKMD, 			"
					+ "					SUM(INSENAMT) INSENAMT 								"
					+ "			FROM DWPM.DBO.DW_PROJECTWORKMAN_MM									"
					+ "			GROUP BY ENTNO, EMPNO, YYYYMM) MM, DWPM.DBO.DW_PROJECTMST MST		"
					+ "	WHERE MM.ENTNO = MST.ENTNO AND MM.ENTNO = ?							");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(String entno) throws DataAccessException {
			return this.update(new Object[] { entno });
		}
	}
	
	protected class ProjectMemberInsertQueryForMMInfoByTime extends SqlUpdate {
		protected ProjectMemberInsertQueryForMMInfoByTime(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMemberMMPlan		"
					+ "	SELECT MST.PROJID AS projectCode, MM.EMPNO as ssn, LEFT(MM.YYYYMM,4) AS [year], RIGHT(MM.YYYYMM, 2) AS [month],	"
					+ "			(CASE MST.OPTYPE											"
					+ "				WHEN '03' THEN	MM.WORKMD								"
					+ "				WHEN '02' THEN	MM.WORKMD								"
					+ "				WHEN '01' THEN	MM.WORKMD								"
					+ "			ELSE 0 END) mmValue, 'Y' as trainingYN, 'N' as checkYN		"
					+ "	FROM (																"
					+ "			SELECT  ENTNO, EMPNO, YYYYMM, SUM(WORKMD) WORKMD, 			"
					+ "					SUM(INSENAMT) INSENAMT 								"
					+ "			FROM DWPM.DBO.DW_PROJECTWORKMAN_MM									"
					+ "			GROUP BY ENTNO, EMPNO, YYYYMM) MM, DWPM.DBO.DW_PROJECTMST MST		"
					+ "	WHERE MM.ENTNO = MST.ENTNO											"
					+ "	  AND YYYYMM >= LEFT(CONVERT(VARCHAR, GETDATE(), 112),6)			"
					+ "	  AND MM.ENTNO = ?							");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(String entno) throws DataAccessException {
			return this.update(new Object[] { entno });
		}
	}	
	
	protected class ProjectMemberInsertQueryForMMInfoByProject extends SqlUpdate {
		protected ProjectMemberInsertQueryForMMInfoByProject(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMemberMMPlan	(projectCode, year, month, ssn, mmValue, trainingYN, checkYN)	"
					+ "	VALUES (  ?, ?,		?, ?, ?,  'Y', 'Y' )															");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
			return this.update(new Object[] { projectMemberMonthlyMM.getProjectCode(), projectMemberMonthlyMM.getYear(),
					projectMemberMonthlyMM.getMonth(), projectMemberMonthlyMM.getSsn(), projectMemberMonthlyMM.getMmValue() });
		}
	}

	protected class ProjectMemberInsertQueryForMMInfoByPerson extends SqlUpdate {
		protected ProjectMemberInsertQueryForMMInfoByPerson(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMemberMMPlan	(projectCode, year, month, ssn, mmValue, trainingYN, checkYN)	"
					+ "	VALUES (  ?, ?,		?, ?, ?,	?, ?  )															");
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

		protected int insert(ProjectMemberMonthlyMM projectMemberMonthlyMM, String trainingYN, String checkYN) throws DataAccessException {
			return this.update(new Object[] { projectMemberMonthlyMM.getProjectCode(), projectMemberMonthlyMM.getYear(),
					projectMemberMonthlyMM.getMonth(), projectMemberMonthlyMM.getSsn(), projectMemberMonthlyMM.getMmValue(),
					trainingYN, checkYN });
		}
	}	

	protected class ProjectMemberInsertQueryForMMInfoModifiedLog extends SqlUpdate {
		protected ProjectMemberInsertQueryForMMInfoModifiedLog(DataSource ds) {
			super(ds, "	INSERT INTO ProjectMemberMMplanLog VALUES( ?, ?, getDate(), ?  )	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(String projectCode, String ssn, String modifierSsn) throws DataAccessException {
			return this.update(new Object[] { projectCode, ssn, modifierSsn });
		}
	}	
	
	protected class ProjectMemberUpdateQuery extends SqlUpdate {
		protected ProjectMemberUpdateQuery(DataSource ds) {
			super(ds, "UPDATE ProjectMember                                                             "
					+ "   SET role=?,cost=?,trainingYN=?,contributionCost=?,                            "
					+ "       teachingDay=?,position=dbo.getExpertCompanyPosition(?) , resRate=? , startDate=?,  enddate=?   "
					+ " WHERE projectCode=? and ssn=?                       ");
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
			setRequiredRowsAffected(1);
		}

		protected int update(ProjectMember projectMember) throws DataAccessException {
			return this.update(new Object[] { projectMember.getRole(), projectMember.getCost().replace(",", "").replace(" ", ""), projectMember.getTrainingYn(),
					projectMember.getContributionCost(), projectMember.getTeachingDay(), projectMember.getSsn(), projectMember.getResRate(),
					projectMember.getStartDate(), projectMember.getEndDate(),
					projectMember.getProjectCode(), projectMember.getSsn() });
		}
	}
	
	protected class ProjectMemberUpdateQueryForMemberChange extends SqlUpdate {
		protected ProjectMemberUpdateQueryForMemberChange(DataSource ds) {
			super(ds, "UPDATE ProjectMember                                                  "
					+ "   SET role=?, trainingYN=?, position=dbo.getExpertCompanyPosition(?) "
					+ " WHERE projectCode=? and ssn=? and role=?                             ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
			setRequiredRowsAffected(1);
		}
		
		protected int update(ProjectMember projectMember, String newRole, String newTrainingYn) throws DataAccessException {
			return this.update(new Object[] { 
					newRole, newTrainingYn, projectMember.getSsn(), 
					projectMember.getProjectCode(), projectMember.getSsn(), projectMember.getRole() });
		}
	}
	
	protected class ProjectMemberUpdateQueryForMMInfo extends SqlUpdate {
		protected ProjectMemberUpdateQueryForMMInfo(DataSource ds) {
			super(ds, "UPDATE ProjectMemberMMPlan                       		"
					+ "   SET trainingYN = 'Y', checkYN = 'Y',  mmValue = ? 	"
					+ " WHERE projectCode=? and year=? and month=? and ssn=?	");
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			compile();
			setRequiredRowsAffected(1);
		}
		
		protected int update(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
			return this.update(new Object[] { 
					projectMemberMonthlyMM.getMmValue(), 
					projectMemberMonthlyMM.getProjectCode(), projectMemberMonthlyMM.getYear(), projectMemberMonthlyMM.getMonth(),
					projectMemberMonthlyMM.getSsn() 		});
		}
	}

	protected class ProjectMemberDeleteQuery1 extends SqlUpdate {
		protected ProjectMemberDeleteQuery1(DataSource ds) {
			super(ds, " DELETE FROM ProjectMember WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ProjectMemberDeleteQuery2 extends SqlUpdate {
		protected ProjectMemberDeleteQuery2(DataSource ds) {
			super(ds, " DELETE FROM ProjectMember WHERE projectCode=? and ssn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
			setRequiredRowsAffected(1);
		}

		protected int delete(String projectCode, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, ssn });
		}
	}
	
	protected class ProjectMemberDeleteQueryForMMInfo extends SqlUpdate {
		protected ProjectMemberDeleteQueryForMMInfo(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class ProjectMemberDeleteQueryForMMInfoByPerson extends SqlUpdate {
		protected ProjectMemberDeleteQueryForMMInfoByPerson(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE projectCode=? AND ssn=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, ssn });
		}
	}
	
	protected class ProjectMemberDeleteQueryForMMInfoByTime extends SqlUpdate {
		protected ProjectMemberDeleteQueryForMMInfoByTime(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE year >= YEAR(GETDATE())	"
					+ "	AND month >= RIGHT('0' + CONVERT(VARCHAR, MONTH(GETDATE())), 2) AND projectCode=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode) throws DataAccessException {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class ProjectMemberDeleteQueryForMMInfoByTimeAndPerson extends SqlUpdate {
		protected ProjectMemberDeleteQueryForMMInfoByTimeAndPerson(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE year >= YEAR(GETDATE())	"
					+ "	AND month >= RIGHT('0' + CONVERT(VARCHAR, MONTH(GETDATE())), 2) AND projectCode=? AND ssn=?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String projectCode, String ssn) throws DataAccessException {
			return this.update(new Object[] { projectCode, ssn });
		}
	}

	protected class ProjectMemberDeleteQueryForMMInfoByProject extends SqlUpdate {
		protected ProjectMemberDeleteQueryForMMInfoByProject(DataSource ds) {
			super(ds, " DELETE FROM ProjectMemberMMPlan WHERE projectCode=? and year=? and month=? and ssn=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
			return this.update(new Object[] { projectMemberMonthlyMM.getProjectCode(), projectMemberMonthlyMM.getYear(), 
					projectMemberMonthlyMM.getMonth(), projectMemberMonthlyMM.getSsn() });
		}
	}

	protected class ProjectMemberSelectQuery1 extends MappingSqlQuery {
		protected ProjectMemberSelectQuery1(DataSource ds) {
			super(ds, "	SELECT 	p.projectCode, p.ssn, p.role, p.cost, p.trainingYN, p.contributionCost, p.teachingDay, "
					+ "         replace(LTrim(isNULL((select description from SMRole where id = p.position), e.companyPositionName)),' 컨설턴트','') as position,	"
					+ "			p.resRate, p.startDate, p.endDate, e.name, e.jobClass, v.*  						"
					+ " FROM ProjectMember p WITH(NOLOCK) 									"
					+ " INNER JOIN expertPool e WITH(NOLOCK) ON p.ssn=e.ssn					"
					+ " LEFT OUTER JOIN DWPM.DBO.DW_view_CP_Amt v WITH(NOLOCK) ON e.rate=v.cp_code	"
					+ " WHERE p.projectCode=? and p.trainingYn='Y'							"
					+ " ORDER BY (case p.role when 'AA' then '01' when 'AG' then '02'		" 
					+ "                     when 'PM' then '03' when 'PL' then '04'			"
					+ "                     when 'MB' then '05' end), p.ssn 				");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected ProjectMemberSelectQuery1(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setName(rs.getString("name"));
			projectMember.setRole(rs.getString("role"));
			projectMember.setCost(rs.getString("cost"));
			projectMember.setTrainingYn(rs.getString("trainingYn"));
			projectMember.setContributionCost(rs.getString("contributionCost"));
			projectMember.setTeachingDay(rs.getString("teachingDay"));
			projectMember.setPosition(rs.getString("position"));
			projectMember.setResRate(rs.getString("resRate"));
			projectMember.setStartDate(rs.getString("startDate"));
			projectMember.setEndDate(rs.getString("endDate"));
			projectMember.setJobClass(rs.getString("jobClass"));
			projectMember.setMaxAmt(rs.getString("MAX_AMT"));
			projectMember.setMinAmt(rs.getString("MIN_AMT"));
			projectMember.setMaxEdu(rs.getString("EDU_MAX"));
			projectMember.setMinEdu(rs.getString("EDU_MIN"));
			projectMember.setMaxMMAmt(rs.getString("MM_MAX_AMT"));
			projectMember.setMinMMAmt(rs.getString("MM_MIN_AMT"));
			return projectMember;
		}
	}

	protected class ProjectMemberSelectQuery2 extends MappingSqlQuery {
		protected ProjectMemberSelectQuery2(DataSource ds) {
			super(ds, "	SELECT 	top 1 * , (select name from expertpool e where e.ssn = p.ssn) as name"
					+ " FROM ProjectMember p  WITH(NOLOCK) "
					+ " WHERE p.projectCode=? and p.ssn=? and p.trainingYn='Y' and p.role <> 'AG' "
					// Job Date: 2010-06-16	Author: yhyim	Description: 정렬 순서 변경
					//+ " ORDER BY (case when role='AR' then 1 when role='PM' then 2 when role='PL' then 3 when role='MB' then 4 end) asc");					
					+ " ORDER BY (case when role='PL' then 1 when role='AR' then 2 when role='PM' then 3 when role='MB' then 4 end) asc");			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected ProjectMemberSelectQuery2(DataSource ds, String query) {
			super(ds, query);
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setName(rs.getString("name"));
			projectMember.setRole(rs.getString("role"));
			projectMember.setCost(rs.getString("cost"));
			projectMember.setTrainingYn(rs.getString("trainingYn"));
			projectMember.setContributionCost(rs.getString("contributionCost"));
			projectMember.setTeachingDay(rs.getString("teachingDay"));
			projectMember.setPosition(rs.getString("position"));
			projectMember.setResRate(rs.getString("resRate"));
			projectMember.setStartDate(rs.getString("startDate"));
			projectMember.setEndDate(rs.getString("endDate"));
			return projectMember;
		}
	}

	protected class ProjectMemberSelectQuery3 extends ProjectMemberSelectQuery2 {
		protected ProjectMemberSelectQuery3(DataSource ds) {
			super(ds, "	SELECT 	p.projectCode, p.ssn, p.role, p.cost, p.trainingYN, p.contributionCost, p.startDate, p.endDate, "
					+ " 		p.teachingDay, e.companyPositionName as position, p.resRate, e.name   "
					+ " FROM ProjectMember p WITH(NOLOCK), expertPool e WITH(NOLOCK) WHERE p.ssn=e.ssn and p.projectCode=? "
					+ " order by trainingYn desc, (case p.role when 'AA' then '01' when 'AG' then '02' " 
					+ "                     				   when 'PM' then '03' when 'PL' then '04' "
					+ "                     				   when 'MB' then '05' end), p.ssn ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}
	
	protected class ProjectMemberSelectQueryOnlyMember extends ProjectMemberSelectQuery2 {
		protected ProjectMemberSelectQueryOnlyMember(DataSource ds) {
			super(ds, "	SELECT 	b.name, a.* FROM ProjectMember a, expertpool b "
					+ " WHERE a.ssn = b.ssn and projectCode=? and a.role='MB' ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	protected class ProjectMemberSelectForExpenseQuery extends MappingSqlQuery {
		protected ProjectMemberSelectForExpenseQuery(DataSource ds) {
			super(ds, " SELECT A.projectCode, (CASE WHEN b.jobclass = 'A' THEN '상근(1)' WHEN b.jobclass = 'B' THEN '상근(2)'  WHEN b.jobclass = 'H' THEN 'AA'  "
					+ " WHEN b.jobclass = 'J' THEN '상임' WHEN b.jobclass = 'C' THEN '엑스퍼트' WHEN b.jobclass = 'D' THEN '산업계강사'  "
					+ "  WHEN b.jobclass = 'E' THEN '대학교수' WHEN b.jobclass in ('F','L','M') THEN '(대기)인력' WHEN b.jobclass = 'I' THEN 'KMAC Alumni' WHEN b.jobclass = 'N' THEN 'RA' ELSE '기타' END) as job, B.name, B.ssn, C.data_1 AS role, "
					+ "  isnull(A.cost,0) AS cost , isnull(D.money, 0) AS money, b.jobclass, e.projecttypecode "
					+ "  FROM ProjectMember A WITH(NOLOCK)  " 
					+ "  LEFT JOIN   "
					+ "  ( SELECT * FROM ExpertPool WITH(NOLOCK) )B on A.ssn = B.ssn  "
					+ "  LEFT JOIN   "
					+ "  ( SELECT * FROM CmTableData c WITH(NOLOCK) )C on C.table_name = 'position_kind' AND A.role = C.key_1 AND (A.role = 'MB' OR A.role = 'PL' OR A.role = 'QM')  "
					+ "  LEFT JOIN "
					+ "  ( SELECT chargessn, sum(convert(float, amount)) as money fROM ProjectTeachingFeeMDetail where projectcode=? and isSanction = 'FINISHED' group by chargessn )D on A.ssn = D.chargeSsn "
					+ "  LEFT JOIN "
					+ "  ( SELECT * FROM Project )E on A.projectCode = E.projectCode "
					+ "  WHERE A.projectCode=? AND C.data_1 IS NOT NULL "  
					+ "  ORDER BY B.jobclass asc, B.name asc, C.data_2 ASC, ssn ASC " );
	
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setName(rs.getString("name"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setRole(rs.getString("role"));
			projectMember.setCost(rs.getString("cost"));
			projectMember.setCnt(rs.getString("money"));
			return projectMember;
		}
	}
	
	protected class ProjectMemberSelectForExpenseQuery2 extends MappingSqlQuery {
		protected ProjectMemberSelectForExpenseQuery2(DataSource ds) {
			super(ds, " SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role           "
					+ "	  FROM 	ProjectMember A WITH(NOLOCK), ExpertPool B WITH(NOLOCK), CmTableData C WITH(NOLOCK)"
					+ "	 WHERE 	A.projectCode = ?                                        "
					+ "    AND	B.jobClass not in ('A','C','J','B')    		                 "
					+ "	   AND 	A.ssn = B.ssn                                            "
					+ "	   AND 	C.table_name = 'position_kind'                           "
					+ "	   AND 	A.role = C.key_1                                         "
					+ "	   AND 	( A.role = 'MB' OR A.role = 'PL' )                       "
					+ "ORDER BY	C.data_2 ASC, B.name ASC, ssn ASC                            ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setName(rs.getString("name"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setRole(rs.getString("role"));
			return projectMember;
		}
	}

	protected class ProjectMemberSelectQueryForMMInfo extends MappingSqlQuery {
		protected ProjectMemberSelectQueryForMMInfo(DataSource ds) {
			super(ds, " SELECT	projectCode, ssn, year, month, mmValue, trainingYN      "
					+ "	  FROM 	ProjectMemberMMPlan										"
					+ "	 WHERE 	projectCode = ?     									"
					+ "    AND	year = ?    											"
					+ "	   AND 	month = ?           									"
					+ "	   AND 	ssn = ?													");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMemberMonthlyMM projectMemberMonthlyMM = new ProjectMemberMonthlyMM();
			projectMemberMonthlyMM.setProjectCode(rs.getString("projectCode"));
			projectMemberMonthlyMM.setSsn(rs.getString("ssn"));
			projectMemberMonthlyMM.setYear(rs.getString("year"));
			projectMemberMonthlyMM.setMonth(rs.getString("month"));
			projectMemberMonthlyMM.setMmValue(rs.getString("mmValue"));
			projectMemberMonthlyMM.setMmState(rs.getString("trainingYN"));
			return projectMemberMonthlyMM;
		}
	}	
	
	protected class ProjectMemberPUSelectForExpenseQuery extends MappingSqlQuery {
		protected ProjectMemberPUSelectForExpenseQuery(DataSource ds) {
			super(ds, " SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role           "
					+ "	  FROM 	ProjectMember A WITH(NOLOCK), ExpertPool B WITH(NOLOCK), CmTableData C WITH(NOLOCK)"
					+ "	 WHERE 	A.projectCode = ?                                        "
/*					+ "    AND	B.jobClass in ('A','C','J','B')    		                 "*/
					+ "	   AND 	A.ssn = B.ssn                                            "
					+ "	   AND 	C.table_name = 'position_kind'                           "
					+ "	   AND 	A.role = C.key_1                                         "
					+ "	   AND 	( A.role = 'MB' OR A.role = 'PL' OR A.role = 'QM' )                       "
					+ "ORDER BY	C.data_2 ASC, B.NAME ASC, ssn ASC                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setName(rs.getString("name"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setRole(rs.getString("role"));
			return projectMember;
		}
	}

	
	protected class ProjectMemberSelectAllForExpenseQuery extends MappingSqlQuery {
		protected ProjectMemberSelectAllForExpenseQuery(DataSource ds) {
			super(ds, " SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS role           "
					+ "	  FROM 	ProjectMember A WITH(NOLOCK), ExpertPool B WITH(NOLOCK), CmTableData C WITH(NOLOCK)"
					+ "	 WHERE 	A.projectCode = ?                                        "
					+ "	   AND 	A.ssn = B.ssn                                            "
					+ "	   AND 	C.table_name = 'position_kind'                           "
					+ "	   AND 	A.role = C.key_1                                         "
					+ "	   AND 	( A.role = 'MB' OR A.role = 'PL' )                       "
					+ "ORDER BY	C.data_2 ASC, ssn ASC                                    ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setName(rs.getString("name"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setRole(rs.getString("role"));
			return projectMember;
		}
	}
	

	protected class ProjectScheduleReportSelectForExpenseQuery extends MappingSqlQuery {
		protected ProjectScheduleReportSelectForExpenseQuery(DataSource ds) {
			super(ds, " select projectcode, count as cnt "
					+ " from ("
					+ " select projectcode, isnull(count(state), 0) as count "
					+ " From projectweeklyreport where projectcode=? "
					+ " and assignYear=? and assignMonth=? and (writeDate is null or state = 'reject') group by projectcode "
					+ " )e" ); 
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setCnt(rs.getString("cnt"));
			return projectMember;
		}
	}
	
	protected class ProjectMonthlyReportSelectForExpenseQuery extends MappingSqlQuery {
		protected ProjectMonthlyReportSelectForExpenseQuery(DataSource ds) {
			super(ds, " select projectcode, count as cnt "
					+ " from ("
					+ " select projectcode, isnull(count(state), 0) as count "
					+ " From projectMonthlyreport where projectcode=? "
					+ " and assignYear=? and assignMonth=? and (writeDate is null or state = 'reject') group by projectcode "
					+ " )e" ); 
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setCnt(rs.getString("cnt"));
			return projectMember;
		}
	}
	
	protected class ProjectMemberScheduleSelectForExpenseQuery extends MappingSqlQuery {
		protected ProjectMemberScheduleSelectForExpenseQuery(DataSource ds) {
			super(ds, " SELECT B.projectCode, B.name, B.ssn, ISNULL(A.cnt,'0')cnt FROM ( "
				+ " SELECT count(chargessn) cnt, chargessn, projectcode "
				+ " FROM ProjectManpowerDetail "
				+ " WHERE projectcode=? "
				+ " AND year=? "
				+ " AND month=? "
				+ " GROUP BY chargessn, projectcode "
				+ " )A "
				+ " RIGHT OUTER JOIN    "
				+ " ( 	SELECT	A.projectCode, B.name, B.ssn, C.data_1 AS jobClassName	 "							 
				+ " 	FROM 	ProjectMember A, ExpertPool B, CmTableData C, C.data_2           "   	           				 
				+ " 	WHERE	1=1								 "													 
				+ " 	AND		A.trainingYN = 'Y'	 "
				+ " 	AND		A.projectCode = ? 		 "													 
				+ " 	AND		B.jobClass in ('A','C','J','B')			 "												 
				+ " 	AND 	A.ssn = B.ssn                   "                          	           				 
				+ " 	AND 	C.table_name = 'EMP_WORKING_TYPE'      "                      	           			 
				+ " 	AND 	B.jobClass = C.key_1              "                            	           			 
				+ " 	AND 	( A.role = 'MB' OR A.role = 'PL' )   "
				+ " GROUP BY   a.projectcode, b.name, b.ssn, c.data_1, c.data_2 )B  "
				+ " ON a.chargessn=b.ssn "
				+ " ORDER BY B.data_2 ASC, B.ssn DESC"); 
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMember projectMember = new ProjectMember();
			projectMember.setProjectCode(rs.getString("projectCode"));
			projectMember.setName(rs.getString("name"));
			projectMember.setSsn(rs.getString("ssn"));
			projectMember.setCnt(rs.getString("cnt"));
			return projectMember;
		}
	}
	
	protected class ProjectCodeSelectQuery1 extends MappingSqlQuery {
		protected ProjectCodeSelectQuery1(DataSource ds) {
			super(ds, " select pm.projectCode "
			+ " from projectmember pm "
			+ " left join "
			+ " ( "
			+ "	select projectCode, projectState, processTypeCode from project "
			+ " )p				"
			+ " on pm.projectCode = p.projectCode "
			+ " where pm.role='PM' "
			+ " and p.projectState = '1' "
			+ " and p.processTypeCode in ('N1','N2') "
			+ " and pm.ssn = ?"                       );
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project project = new Project();
			project.setProjectCode(rs.getString("projectCode"));
			return project;
		}
	}

	@Override
	public void delete(String projectCode) throws DataAccessException {
		this.projectMemberDeleteQuery1.delete(projectCode);
	}

	@Override
	public void delete(String projectCode, String ssn) throws DataAccessException {
		this.projectMemberDeleteQuery2.delete(projectCode, ssn);
	}
	
	@Override
	public void deleteMMInfo(String projectCode) throws DataAccessException {
		this.projectMemberDeleteQueryForMMInfo.delete(projectCode);
	}
	
	@Override
	public void deleteMMInfo(String projectCode, String ssn) throws DataAccessException {
		this.projectMemberDeleteQueryForMMInfoByPerson.delete(projectCode, ssn);
	}
	
	@Override
	public void deleteMMInfoByTime(String projectCode) throws DataAccessException {
		this.projectMemberDeleteQueryForMMInfoByTime.delete(projectCode);
	}
	
	@Override
	public void deleteMMInfoByTime(String projectCode, String ssn) throws DataAccessException {
		this.projectMemberDeleteQueryForMMInfoByTimeAndPerson.delete(projectCode, ssn);
	}
	
	@Override
	public void deleteMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
		this.projectMemberDeleteQueryForMMInfoByProject.delete(projectMemberMonthlyMM);
	}

	@Override
	public void insert(ProjectMember projectMember) throws DataAccessException {
		this.projectMemberInsertQuery1.insert(projectMember);
	}

	@Override
	public void insert(List<RunningMemberChange> projectMemberlList) throws DataAccessException {
		this.projectMemberInsertQuery2.insert(projectMemberlList);
	}
	
	@Override
	public void insertMMInfo(String entno) throws DatabaseException {
		this.projectMemberInsertQueryForMMInfo.insert(entno);
	}
	
	@Override
	public void insertMMInfoByTime(String entno) throws DatabaseException {
		this.projectMemberInsertQueryForMMInfoByTime.insert(entno);
	}
	
	@Override
	public void insertMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) {
		this.projectMemberInsertQueryForMMInfoByProject.insert(projectMemberMonthlyMM);
	}
	
	@Override
	public void insertMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM, String trainingYN, String checkYN) {
		this.projectMemberInsertQueryForMMInfoByPerson.insert(projectMemberMonthlyMM, trainingYN, checkYN);
	}
	
	@Override
	public void createMMInfoModifiedLog(String projectCode, String ssn, String modifierSsn) {
		this.projectMemberInsertQueryForMMInfoModifiedLog.insert(projectCode, ssn, modifierSsn);
	}

	@Override
	public ProjectMember select(String projectCode, String ssn) throws DataAccessException {
		return (ProjectMember) this.projectMemberSelectQuery2.findObject(new Object[] { projectCode, ssn });
	}
	
	@Override
	public ProjectMemberMonthlyMM selectMMinfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
		return (ProjectMemberMonthlyMM) this.projectMemberSelectQueryForMMInfo.findObject(new Object[] {projectMemberMonthlyMM.getProjectCode(), projectMemberMonthlyMM.getYear(), projectMemberMonthlyMM.getMonth(), projectMemberMonthlyMM.getSsn()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> select(String projectCode) throws DataAccessException {
		return this.projectMemberSelectQuery1.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> selectProjectCode(String ssn) throws DataAccessException {
		return this.projectCodeSelectQuery1.execute(new Object[] {ssn});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectAll(String projectCode) throws DataAccessException {
		return this.projectMemberSelectQuery3.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectForExpense(String projectCode) throws DataAccessException {
		return this.projectMemberSelectForExpenseQuery.execute(new Object[] { projectCode, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectForExpense2(String projectCode) throws DataAccessException {
		return this.projectMemberSelectForExpenseQuery2.execute(new Object[] { projectCode});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectSchedule(String projectCode, String year, String month) throws DataAccessException {
		return this.projectMemberScheduleSelectForExpenseQuery.execute(new Object[] { projectCode, year, month, projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectScheduleReport(String projectCode, String year, String month) throws DataAccessException {
		return this.projectScheduleReportSelectForExpenseQuery.execute(new Object[] { projectCode, year, month });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectMonthlyReport(String projectCode, String year, String month) throws DataAccessException {
		return this.projectMonthlyReportSelectForExpenseQuery.execute(new Object[] { projectCode, year, month });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectForExpensePU(String projectCode) throws DataAccessException {
		return this.projectMemberPUSelectForExpenseQuery.execute(new Object[] { projectCode });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectForExpenseAll(String projectCode) throws DataAccessException {
		return this.projectMemberSelectAllForExpenseQuery.execute(new Object[] { projectCode });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMember> selectOnlyMemberAll(String projectCode) throws DataAccessException {
		return this.projectMemberSelectQueryOnlyMember.execute(new Object[] { projectCode });
	}

	@Override
	public void update(ProjectMember projectMember) throws DataAccessException {
		this.projectMemberUpdateQuery.update(projectMember);
	}

	@Override
	public void updateMemberChange(ProjectMember projectMember, String newRole, String newTraingYn) throws DataAccessException {
		this.projectMemberUpdateQueryForMemberChange.update(projectMember, newRole, newTraingYn);		
	}

	@Override
	public void updateMMInfoByProject(ProjectMemberMonthlyMM projectMemberMonthlyMM) throws DataAccessException {
		this.projectMemberUpdateQueryForMMInfo.update(projectMemberMonthlyMM);
	}
}
