/*
 * $Id: ExpenseErpDataServiceDaoImpl.java,v 1.16 2019/02/28 16:41:27 cvs Exp $
 * created by    : jiwoongLee
 * creation-date : 2006. 5. 9.
 * =========================================================
 * Copyright (c) 2006 Miracom, Inc. All rights reserved.
 */
package kr.co.kmac.pms.service.scheduler.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.common.util.StringUtil;
import kr.co.kmac.pms.service.scheduler.dao.ExpenseErpDataServiceDao;
import kr.co.kmac.pms.service.scheduler.data.ExpenseErpData;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * TODO 클래스 설명
 * 
 * @author jiwoongLee
 * @version $Id: ExpenseErpDataServiceDaoImpl.java,v 1.16 2019/02/28 16:41:27 cvs Exp $
 */
public class ExpenseErpDataServiceDaoImpl extends JdbcDaoSupport implements ExpenseErpDataServiceDao {

	private DataSource erpDataSource;// 회계 테이터 베이스 접근
	private ExpenseDataFromErp expenseDataFromErp;
	// private ExpenseDataFromErpForUpdate expenseDataFromErpForUpdate;
	private ExpenseDataToPms expenseDataToPms;
	private ExpenseDataDeleteInPms expenseDataDeleteInPms;
	private UpdateProjectReportExceedState updateProjectReportExceedState;
	private UpdateProjectTeachingFeeExceedState updateProjectTeachingFeeExceedState;
	private UpdateProjectTeachingRestFeeExceedState updateProjectTeachingRestFeeExceedState;
	private UpdateProjectReportExceedStateInit updateProjectReportExceedStateInit;
	private UpdateProjectTeachingFeeExceedStateInit updateProjectTeachingFeeExceedStateInit;
	private UpdateProjectTeachingRestFeeExceedStateInit updateProjectTeachingRestFeeExceedStateInit;
	private UpdateProjectReportExceedStateSpcProjectInit updateProjectReportExceedStateSpcProjectInit;
	private UpdateProjectTeachingFeeExceedStateSpcProjectInit updateProjectTeachingFeeExceedStateSpcProjectInit;
	private UpdateProjectTeachingRestFeeExceedStateSpcProjectInit updateProjectTeachingRestFeeExceedStateSpcProjectInit;
	

	protected void initDao() throws Exception {
		this.expenseDataFromErp = new ExpenseDataFromErp(getErpDataSource());
		// this.expenseDataFromErpForUpdate = new ExpenseDataFromErpForUpdate(getErpDataSource());
		this.expenseDataToPms = new ExpenseDataToPms(getDataSource());
		this.expenseDataDeleteInPms = new ExpenseDataDeleteInPms(getDataSource());
		this.updateProjectReportExceedState = new UpdateProjectReportExceedState(getDataSource());
		this.updateProjectTeachingFeeExceedState = new UpdateProjectTeachingFeeExceedState(getDataSource());
		this.updateProjectTeachingRestFeeExceedState = new UpdateProjectTeachingRestFeeExceedState(getDataSource());
		this.updateProjectReportExceedStateInit = new UpdateProjectReportExceedStateInit(getDataSource());
		this.updateProjectTeachingFeeExceedStateInit = new UpdateProjectTeachingFeeExceedStateInit(getDataSource());
		this.updateProjectTeachingRestFeeExceedStateInit = new UpdateProjectTeachingRestFeeExceedStateInit(getDataSource());
		this.updateProjectReportExceedStateSpcProjectInit = new UpdateProjectReportExceedStateSpcProjectInit(getDataSource());
		this.updateProjectTeachingFeeExceedStateSpcProjectInit = new UpdateProjectTeachingFeeExceedStateSpcProjectInit(getDataSource());
		this.updateProjectTeachingRestFeeExceedStateSpcProjectInit = new UpdateProjectTeachingRestFeeExceedStateSpcProjectInit(getDataSource());
		
	}

	protected class ExpenseDataFromErp extends MappingSqlQuery {
		protected ExpenseDataFromErp(DataSource ds) {
			super(ds, "SELECT * FROM V_EXPENSE_PMS_RES");
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseErpData data = new ExpenseErpData();
			data.setProjectCode(rs.getString("PROJID"));
			data.setAcctCode(rs.getString("ACCT_CODE"));
			data.setPlanedAmount(rs.getString("AMTPLAN"));
			data.setExeAmount(rs.getString("AMTEXE"));
			data.setDiffAmount(rs.getString("AMT"));
			return data;
		}
	}

	protected class ExpenseDataFromErpForUpdate extends MappingSqlQuery {
		protected ExpenseDataFromErpForUpdate(DataSource ds) {
			super(ds, "select projectCode from (                                                                  "
					+ "  	select PROJID as projectCode, sum(to_number(amt, 0)) diffAmount from V_EXPENSE_PMS_RES"
					+ "  	group by PROJID                                                                       "
					+ ") res                                                                                      " + "where res.diffAmount < 0 ");
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("projectCode");
		}
	}

	protected class ExpenseDataDeleteInPms extends SqlUpdate {
		protected ExpenseDataDeleteInPms(DataSource ds) {
			super(ds, " DELETE FROM ProjectExpenseFromErp");
			compile();
		}

		protected int deleteData() {
			return this.update();
		}
	}

	protected class UpdateProjectReportExceedStateInit extends SqlUpdate {
		protected UpdateProjectReportExceedStateInit(DataSource ds) {
			super(ds, " update projectReportContent set isExceed = 'N' ");
			compile();
		}

		protected int updateState() {
			return this.update();
		}
	}
	
	protected class UpdateProjectTeachingFeeExceedStateInit extends SqlUpdate {
		protected UpdateProjectTeachingFeeExceedStateInit(DataSource ds) {
			super(ds, " update projectTeachingFeeMDetail set isExceed = 'N' ");
			compile();
		}
		
		protected int updateState() {
			return this.update();
		}
	}
	
	protected class UpdateProjectTeachingRestFeeExceedStateInit extends SqlUpdate {
		protected UpdateProjectTeachingRestFeeExceedStateInit(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingRestFeeDetail SET isExceed = 'N' ");
			compile();
		}
		
		protected int updateState() {
			return this.update();
		}
	}
	
	protected class UpdateProjectReportExceedStateSpcProjectInit extends SqlUpdate {
		protected UpdateProjectReportExceedStateSpcProjectInit(DataSource ds) {
			super(ds, " update projectReportContent set isExceed = 'N' where projectCode = ?");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class UpdateProjectReportExceedState extends SqlUpdate {
		protected UpdateProjectReportExceedState(DataSource ds) {
			super(ds, " update projectReportContent set isExceed = 'Y' where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateProjectTeachingFeeExceedStateSpcProjectInit extends SqlUpdate {
		protected UpdateProjectTeachingFeeExceedStateSpcProjectInit(DataSource ds) {
			super(ds, " update projectTeachingFeeMDetail set isExceed = 'N' where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateProjectTeachingRestFeeExceedStateSpcProjectInit extends SqlUpdate {
		protected UpdateProjectTeachingRestFeeExceedStateSpcProjectInit(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingRestFeeDetail SET isExceed = 'N' WHERE projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateProjectTeachingFeeExceedState extends SqlUpdate {
		protected UpdateProjectTeachingFeeExceedState(DataSource ds) {
			super(ds, " update projectTeachingFeeMDetail set isExceed = 'Y' where projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}
	
	protected class UpdateProjectTeachingRestFeeExceedState extends SqlUpdate {
		protected UpdateProjectTeachingRestFeeExceedState(DataSource ds) {
			super(ds, " UPDATE ProjectTeachingRestFeeDetail SET isExceed = 'Y' WHERE projectCode = ? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int updateState(String projectCode) {
			return this.update(new Object[] { projectCode });
		}
	}

	protected class ExpenseDataToPms extends BatchSqlUpdate {
		protected ExpenseDataToPms(DataSource ds) {
			super(ds, " INSERT INTO ProjectExpenseFromErp (projectCode, acctCode, planAmount, exeAmount, diffAmount) "
					+ "           VALUES (?, ?, ?, ?, ?)");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int insertData(List<ExpenseErpData> data) {
			for (int i = 0; i < data.size(); i++) {
				ExpenseErpData erpData = (ExpenseErpData) data.get(i);
				this.update(new Object[] { erpData.getProjectCode(), erpData.getAcctCode(), erpData.getPlanedAmount(), erpData.getExeAmount(),
						erpData.getDiffAmount() });
			}
			return this.flush().length;
		}
	}

	/**
	 * @return Returns the erpDataSource.
	 */
	public DataSource getErpDataSource() {
		return erpDataSource;
	}

	/**
	 * @param erpDataSource The erpDataSource to set.
	 */
	public void setErpDataSource(DataSource erpDataSource) {
		this.erpDataSource = erpDataSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseErpData> getExpenseDataFromErpFullData() throws DataAccessException {
		return this.expenseDataFromErp.execute();
	}

	@Override
	public int setExpenseDataToPms(List<ExpenseErpData> data) throws DataAccessException {
		this.expenseDataDeleteInPms.deleteData();
		return this.expenseDataToPms.insertData(data);
	}

	@Override
	public void updateProjectReportExceedState(String projectCode) throws DataAccessException {
		this.updateProjectReportExceedState.updateState(projectCode);
	}

	@Override
	public void updateProjectTeachingFeeExceedState(String projectCode) throws DataAccessException {
		this.updateProjectTeachingFeeExceedState.updateState(projectCode);
	}

	@Override
	public void updateProjectTeachingRestFeeExceedState(String projectCode) throws DataAccessException {
		this.updateProjectTeachingRestFeeExceedState.updateState(projectCode);
	}
	
	@Override
	public void updateProjectReportExceedStateInit() throws DataAccessException {
		this.updateProjectReportExceedStateInit.updateState();
	}

	@Override
	public void updateProjectTeachingFeeExceedStateInit() throws DataAccessException {
		this.updateProjectTeachingFeeExceedStateInit.updateState();
	}

	@Override
	public void updateProjectTeachingRestFeeExceedStateInit() throws DataAccessException {
		this.updateProjectTeachingRestFeeExceedStateInit.updateState();
	}
	
	@Override
	public void updateProjectReportExceedStateSpcProjectInit(String projectCode) throws DataAccessException {
		this.updateProjectReportExceedStateSpcProjectInit.updateState(projectCode);
	}
	
	@Override
	public void updateProjectTeachingFeeExceedStateSpcProjectInit(String projectCode) throws DataAccessException {
		this.updateProjectTeachingFeeExceedStateSpcProjectInit.updateState(projectCode);
	}
	
	@Override
	public void updateProjectTeachingRestFeeExceedStateSpcProjectInit(String projectCode) throws DataAccessException {
		this.updateProjectTeachingRestFeeExceedStateSpcProjectInit.updateState(projectCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getExpenseDataFromErpForFlagUpdate() throws DataAccessException {
		int day = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(6, 8));
		int month = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(4, 6));
		String sMonth = "";
		int year = Integer.parseInt(StringUtil.getCurr("yyyyMMdd").substring(0, 4));
		if (day >= 1 && day <= 5) {
			if (month == 1) {
				month = 12;
				year = year - 1;
			} else {
				month = month - 1;
			}
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
			//day = StringUtil.getMonthlyDayCount(year, month);
		} else {
			if (String.valueOf(month).length() == 1) {
				sMonth = "0" + String.valueOf(month);
			} else {
				sMonth = String.valueOf(month);
			}
		}
		// ----------------------------------------------------------------------------------------------------
		return getJdbcTemplate().query(this.getLaborSalesCostOverList(String.valueOf(year), sMonth), new RowMapper() {
			public Object mapRow(ResultSet rs, int currentRowNum) throws SQLException {
				return rs.getString("projectCode");
			}
		});
		// ----------------------------------------------------------------------------------------------------
	}
	// Job Date: 2011-10-25	Author: yhyim	Description: 인건비 초과 프로젝트 list up(for erpCronTrigger)
	private String getLaborSalesCostOverList(String year, String month) {
		StringBuffer query = new StringBuffer();
		query.append("	SELECT 	runningDivCode, runningDivName, projectCode, projectName, '' as acctCode, planAmount, totalAmount, exeAmount, realtimeSalary,			\n");
		query.append("			planAmount as grandPlanAmount, totalAmount as grandTotalAmount, exeAmount as grandExeAmount, realtimeSalary as grandRealTimeSalary,     \n");
		query.append("			isOverYN, 1 as runningDivCodeCnt, 1 as projectCodeCnt                                                                                   \n");
		query.append("	FROM                                                                                                                                            \n");
		query.append("	(                                                                                                                                               \n");
		query.append("		SELECT 	runningDivCode, (select aliasName from smgroup where id = runningDivCode) as runningDivName,                                        \n");
		query.append("				projectCode, projectName,                                                                                                           \n");
		query.append("				planAmount, totalAmount, exeAmount, realTimeSalary,                                                                                 \n");
		query.append("				(case when  planAmount < totalAmount then 'Y' else 'N' end) as isOverYn	                                                            \n");
		query.append("		FROM                                                                                                                                        \n");
		query.append("		(                                                                                                                                           \n");
		query.append("			select                                                                                                                                  \n");
		query.append("				(select d.runningDivCode from project d where d.projectCode=q.projectCode) as runningDivCode,                                       \n");
		query.append("				q.projectCode, q.projectName, q.planAmount,                                                                                         \n");
		query.append("				(isNull(q.exeAmount, 0) + isNull(w.realtimeSalary, 0)) as totalAmount,                                                              \n");
		query.append("				q.exeAmount, isNull(w.realtimeSalary, 0) realtimeSalary                                                                             \n");
		query.append("			FROM                                                                                                                                    \n");
		query.append("			(                                                                                                                                       \n");
		query.append("				select a.projectCode, b.projectName, a.planAmount, a.exeAmount, a.diffAmount                                                        \n");
		query.append("				from                                                                                                                                \n");
		query.append("				(                                                                                                                                   \n");
		query.append("					SELECT	projectCode, sum(convert(bigint,planAmount)) planAmount,                                                                \n");
		query.append("									sum(convert(bigint,exeAmount)) exeAmount, sum(convert(bigint,diffAmount)) diffAmount                            \n");
		query.append("					FROM ProjectExpenseFromErp                                                                                                      \n");
		query.append("					GROUP BY projectCode                                                                                                            \n");
		query.append("				) a,                                                                                                                                \n");
		query.append("				(select projectCode, projectName from project                                                                                       \n");
		query.append("				  where businessTypeCode = 'BTA' AND (projectState < '6' OR adminOpen = 'Y'))b                                                      \n");
		query.append("				where a.projectcode = b.projectcode                                                                                                 \n");
		query.append("			) q                                                                                                                                     \n");
		query.append("			left outer join                                                                                                                         \n");
		query.append("			(                                                                                                                                       \n");
		query.append("				select projectCode, sum(realTimeSalary) realTimeSalary                                                                              \n");
		query.append("				from (                                                                                                                              \n");
		query.append("					select projectCode, jobClass, sum(eachSalary) realTimeSalary                                                                    \n");
		query.append("					from (                                                                                                                          \n");
		query.append("						select	project.projectCode,                                                                                                \n");
		query.append("								(case when expertpool.jobClass='C' then 'J' else expertPool.jobClass end) jobClass,    								\n");
		query.append("								(case when projectReportContent.state = 'complete' then 'complete' else 'ing' end) state,                           \n");
		query.append("								convert(real, (convert(real, projectMember.cost)*convert(real, isNull(projectMember.resRate,1))                     \n");
		query.append("										* convert(real, isNull(projectReportContent.payAmount, 1)))) as eachSalary                                  \n");
		query.append("						from 	(select projectCode, businessTypeCode from project) project,                                                        \n");
		query.append("								(	                                                                                                                \n");
		query.append("									select a.* from projectMember a                                                                                 \n");
		query.append("									left outer join                                                                                                 \n");
		query.append("									(                                                                                                               \n");
		query.append("										select rs1.projectCode, rs1.ssn, rs1.role, rs1.trainingYn                                                   \n");
		query.append("										from                                                                                                        \n");
		query.append("										(                                                                                                           \n");
		query.append("											select projectCode, ssn, role, trainingYn                                                               \n");
		query.append("											from projectMember where (role = 'PL' or role = 'MB')                                                   \n");
		query.append("										) rs1                                                                                                       \n");
		query.append("										inner join                                                                                                  \n");
		query.append("										(                                                                                                           \n");
		query.append("											select	projectCode, ssn from projectMember                                                             \n");
		query.append("											 where (role = 'PL' or role = 'MB')                                                                     \n");
		query.append("											group by projectCode, ssn                                                                               \n");
		query.append("											having	count(ssn) > 1                                                                                  \n");
		query.append("										) rs2                                                                                                       \n");
		query.append("										on rs1.projectCode = rs2.projectCode                                                                        \n");
		query.append("										and rs1.ssn = rs2.ssn                                                                                       \n");
		query.append("										and trainingYn = 'N'                                                                                        \n");
		query.append("									) b                                                                                                             \n");
		query.append("									on a.projectCode = b.projectCode                                                                                \n");
		query.append("									and a.ssn = b.ssn                                                                                               \n");
		query.append("									and a.role = b.role                                                                                             \n");
		query.append("									and a.trainingYn = b.trainingYn                                                                                 \n");
		query.append("									where b.projectCode is null                                                                                     \n");
		query.append("								) projectMember,                                                                                                    \n");
		query.append("								(select projectCode, assignDate, writerSsn, payAmount, state from projectReportContent) projectReportContent,       \n");
		query.append("								(select ssn, jobclass, rate from expertPool) expertPool                                                             \n");
		query.append("						where 	project.projectCode = projectMember.projectCode                                                                     \n");
		query.append("						and		projectMember.ssn = projectReportContent.writerSsn                                                                  \n");
		query.append("						and		projectMember.projectCode = projectReportContent.projectCode                                                        \n");
		query.append("						and		projectMember.ssn = expertPool.ssn                                                                                  \n");
		query.append("						and		project.businessTypeCode = 'BTA'                                                                                    \n");
		query.append("						and		expertPool.jobClass in ('A', 'C', 'J')                                                                         		\n");
		query.append("						and		projectReportContent.assignDate like '").append(year+month).append("__'                                				\n");
		query.append("					) res                                                                                                                           \n");
		query.append("					group by projectCode, jobClass                                                                                                  \n");
		query.append("					UNION    /* MM형 프로젝트의 80% 성과급 */                                                                                               \n");
		query.append("					select projectCode, (case when jobClass in ('A','J') then jobClass when jobclass='C' then 'J' else 'D' end) jobClass , sum(amount) realTimeSalary		\n");
		query.append("					from (                                                                                                                          \n");
		query.append("						select p.projectCode, e.jobClass, convert(money, amount) amount		                                                        \n");
		query.append("						from dbo.ProjectTeachingFeeMDetail p                                                                                        \n");
		query.append("						inner join ExpertPool e                                                                                                     \n");
		query.append("						on p.chargeSsn = e.ssn                                                                                                      \n");
		query.append("						inner join Project pjt																										\n");
		query.append("						on p.projectCode = pjt.projectCode																							\n");
		query.append("						where 1=1                                                                    												\n");		
		query.append("						and pjt.projectTypeCode = 'MM'                                                                    							\n");
		query.append("						and pjt.businessTypeCode = 'BTA'																							\n");
		query.append("						and p.amount <> ''																											\n");
		query.append("						and year  = '").append(year).append("'                                                                    					\n");
		query.append("						and month = '").append(month).append("'                                                                    					\n");
		query.append("					) r                                                                                                                             \n");
		query.append("					group by projectCode, jobClass                                                                                                  \n");
		query.append("					UNION	/* MM형 프로젝트의 누적 20% 성과급 */																								\n");
		query.append("					select projectCode, (case when jobClass in ('A','J') then jobClass when jobclass='C' then 'J' else 'D' end) jobClass , sum(amount) realTimeSalary		\n");
		query.append("					from (                                                                                                                          \n");
		query.append("						select r.projectCode, e.jobClass, convert(money, amount) amount		                                                        \n");
		query.append("						from dbo.ProjectTeachingRestFeeDetail r                                                                                     \n");
		query.append("						inner join ExpertPool e                                                                                                     \n");
		query.append("						on r.chargeSsn = e.ssn                                                                                                      \n");
		query.append("						where 1=1                                                                    												\n");		
		query.append("						and year  = '").append(year).append("'                                                                    					\n");
		query.append("						and month = '").append(month).append("'                                                                    					\n");
		query.append("					) r                                                                                                                             \n");
		query.append("					group by projectCode, jobClass                                                                                                  \n");
		query.append("					UNION																															\n");
		query.append("					select projectCode, 'D' as jobClass, sum(amount) realTimeSalary																	\n");
		query.append("					from (																															\n");
		query.append("						select p.projectCode, e.jobClass, convert(money, amount) amount																\n");
		query.append("						from dbo.ProjectTeachingFeeMDetail p																						\n"); 
		query.append("						inner join ExpertPool e																										\n");
		query.append("						on p.chargeSsn = e.ssn																										\n");
		query.append("						inner join project pjt																										\n");
		query.append("						on p.projectCode = pjt.projectCode																							\n");
		query.append("						where 1=1																													\n");
		query.append("						and p.amount <> ''																											\n");
		query.append("						and e.jobClass not in ('A','C','J')																							\n");
		query.append("						and pjt.businessTypeCode = 'BTA'																							\n");
		query.append("						and year  = '").append(year).append("'                                                                    					\n");
		query.append("						and month = '").append(month).append("'                                                                    					\n");
		query.append("					) r                                                                                                                             \n");
		query.append("					group by projectCode, jobClass																									\n");
		query.append("				) G                                                                                                                                 \n");
		query.append("				group by projectCode                                                                                                                \n");
		query.append("			) w                                                                                                                                     \n");
		query.append("			on q.projectCode = w.projectCode                                                                                                        \n");
		query.append("		) T                                                                                                                                         \n");
		query.append("	) R                                                                                                                                             \n");
		query.append("	WHERE 1=1 and runningDivCode is not null                                                                                                        \n");
		query.append("	AND isOverYN = 'Y'                                                                                                                              \n");		
		System.out.println(query.toString());
		return query.toString();
	}
}
