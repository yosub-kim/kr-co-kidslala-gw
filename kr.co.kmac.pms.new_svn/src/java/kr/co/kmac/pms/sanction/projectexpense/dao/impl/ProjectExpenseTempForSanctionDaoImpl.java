package kr.co.kmac.pms.sanction.projectexpense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import kr.co.kmac.pms.sanction.projectexpense.dao.ProjectExpenseTempForSanctionDao;
import kr.co.kmac.pms.sanction.projectexpense.data.ExpenseRealTimeResultDetailForSanction;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class ProjectExpenseTempForSanctionDaoImpl extends JdbcDaoSupport implements ProjectExpenseTempForSanctionDao {

	private ProjectExpenseTempForSanctionInsertQuery projectExpenseTempForSanctionInsertQuery;
	private ProjectExpenseTempForSanctionRetrieveQuery projectExpenseTempForSanctionRetrieveQuery;
	private ProjectExpenseTempForRefSanctionRetrieveQuery projectExpenseTempForRefSanctionRetrieveQuery;
	private ProjectExpenseTempForSanctionDeleteQuery projectExpenseTempForSanctionDeleteQuery;

	protected void initDao() {
		this.projectExpenseTempForSanctionInsertQuery = new ProjectExpenseTempForSanctionInsertQuery(getDataSource());
		this.projectExpenseTempForSanctionRetrieveQuery = new ProjectExpenseTempForSanctionRetrieveQuery(getDataSource());
		this.projectExpenseTempForRefSanctionRetrieveQuery = new ProjectExpenseTempForRefSanctionRetrieveQuery(getDataSource());
		this.projectExpenseTempForSanctionDeleteQuery = new ProjectExpenseTempForSanctionDeleteQuery(getDataSource());
	}

	protected class ProjectExpenseTempForSanctionDeleteQuery extends SqlUpdate {
		protected ProjectExpenseTempForSanctionDeleteQuery(DataSource ds) {
			super(ds, " DELETE FROM ProjectExpenseTempForSanction WHERE seq=? ");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected int delete(String seq) {
			return this.update(new Object[] { seq });
		}
	}

	protected class ProjectExpenseTempForSanctionInsertQuery extends SqlUpdate {
		protected ProjectExpenseTempForSanctionInsertQuery(DataSource ds) {
			super(ds, " INSERT INTO ProjectExpenseTempForSanction                                       "
					+ "   (seq,tempYear,tempMonth,        tempName,tempSsn,tempTotalRealTimeSalary,     "
					+ "    tempProjectCode,tempProjectName,tempPreportCount,                            "
					+ "    tempRealTimeSalaryEachProject,tempExecuted,									"
					+ "    tempDept,tempSeq, tempIsHolding, tempType, tempDeptType, 					"
					+ "    tempJobclass,tempJobclassDesc, tempJobclassErp, 								"
					+ "	   tempCost, tempResRate, tempMMValueRatio, tempIsMMExceed,						"
					+ "	   tempCumulativeRealTimeSalary, tempBudgetEachProject	)						"
					+ " VALUES(?, ?, ?,     ?, ?, ?,     ?, ?, ?,    ?, ?,       ?, ?,     ?, ?, ?,     ?, ?, ?,     ?, ?, ?, ?,     ?, ?)");
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
			declareParameter(new SqlParameter(Types.VARCHAR));
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));

			compile();
			setRequiredRowsAffected(1);
		}

		protected int insert(ExpenseRealTimeResultDetailForSanction expenseTempForSanction) {
			return this.update(new Object[] { expenseTempForSanction.getProjectExpenseSeq(), expenseTempForSanction.getSalaryYear(),
					expenseTempForSanction.getSalaryMonth(), expenseTempForSanction.getSalaryName(), expenseTempForSanction.getSalarySsn(),
					expenseTempForSanction.getSalaryTotalRealTimeSalary(), expenseTempForSanction.getSalaryProjectCode(),
					expenseTempForSanction.getSalaryProjectName(), expenseTempForSanction.getSalaryPreportCount(),
					expenseTempForSanction.getSalaryRealTimeSalaryEachProject(),
					expenseTempForSanction.getSalaryExecuted(),
					expenseTempForSanction.getSalaryDept(), expenseTempForSanction.getSalarySeq(),
					expenseTempForSanction.getSalaryIsHolding(), expenseTempForSanction.getSalaryType(), expenseTempForSanction.getSalaryDeptType(),
					expenseTempForSanction.getSalaryJobClass(), expenseTempForSanction.getSalaryJobClassDesc(), expenseTempForSanction.getSalaryJobClassErp(),
					expenseTempForSanction.getSalaryCost(), expenseTempForSanction.getSalaryResRate(), expenseTempForSanction.getSalaryMMValueRatio(), expenseTempForSanction.getSalaryIsMMExceed(), 
					expenseTempForSanction.getSalaryCumulativeRealTimeSalary(), expenseTempForSanction.getSalaryBudgetEachProject()
					});
		}
	}

	protected class ProjectExpenseTempForSanctionRetrieveQuery extends MappingSqlQuery {
		protected ProjectExpenseTempForSanctionRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}

		protected ProjectExpenseTempForSanctionRetrieveQuery(DataSource ds) {
			super(ds, "	SELECT	seq,tempYear,tempMonth,tempName,tempSsn, sum(convert(float, tempRealTimeSalaryEachProject)) over (partition by tempSsn) as tempTotalRealTimeSalary,tempProjectCode "
					+ "			,tempProjectName,tempPreportCount,tempRealTimeSalaryEachProject,tempExecuted,tempDept	"
					+ "			,tempSeq,tempIsHolding,tempType,tempJobClass,tempJobClassErp,tempJobClassDesc			"
					+ "			,tempDeptType,isNull(tempCost,0) as tempCost,isNull(tempResRate,'-') as tempResRate		"
					+ "			,(substring(dbo.getExpertPoolUid(tempSsn), 1, 6)+'-'+substring(dbo.getExpertPoolUid(tempSsn), 7, 7) ) tempUid, tempMMValueRatio, tempIsMMExceed "
					+ "			,tempCumulativeRealTimeSalary, tempBudgetEachProject									"
					+ "			,(CASE WHEN (SELECT seq FROM sanctionDoc WHERE seq = s.seq and approvalType = 'expenseR') IS NULL THEN '-' ELSE '인센티브' END) tempApprovalType, count(tempSsn) over (partition by tempSsn) ssnCnt	"					
					+ "	FROM ProjectExpenseTempForSanction s WHERE seq=? ORDER BY tempJobClassErp");
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}

		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseRealTimeResultDetailForSanction expenseTempForSanction = new ExpenseRealTimeResultDetailForSanction();
			expenseTempForSanction.setProjectExpenseSeq(rs.getString("seq"));
			expenseTempForSanction.setSalaryYear(rs.getString("tempYear"));
			expenseTempForSanction.setSalaryMonth(rs.getString("tempMonth"));
			expenseTempForSanction.setSalaryName(rs.getString("tempName"));
			expenseTempForSanction.setSalarySsn(rs.getString("tempSsn"));
			expenseTempForSanction.setSalaryUid(rs.getString("tempUid"));
			expenseTempForSanction.setSalaryTotalRealTimeSalary(rs.getLong("tempTotalRealTimeSalary"));
			expenseTempForSanction.setSalaryProjectCode(rs.getString("tempProjectCode"));
			expenseTempForSanction.setSalaryProjectName(rs.getString("tempProjectName"));
			expenseTempForSanction.setSalaryPreportCount(rs.getString("tempPreportCount"));
			expenseTempForSanction.setSalaryRealTimeSalaryEachProject(rs.getLong("tempRealTimeSalaryEachProject"));
			expenseTempForSanction.setSalaryExecuted(rs.getLong("tempExecuted"));
			expenseTempForSanction.setSalaryDept(rs.getString("tempDept"));
			expenseTempForSanction.setSalarySeq(rs.getString("tempSeq"));
			expenseTempForSanction.setSalaryIsHolding(rs.getString("tempIsHolding"));
			expenseTempForSanction.setSalaryType(rs.getString("tempType"));
			expenseTempForSanction.setSalaryDeptType(rs.getString("tempDeptType"));
			expenseTempForSanction.setSalaryJobClass(rs.getString("tempJobClass"));
			expenseTempForSanction.setSalaryJobClassDesc(rs.getString("tempJobClassDesc"));
			expenseTempForSanction.setSalaryJobClassErp(rs.getString("tempJobClassErp"));
			expenseTempForSanction.setSalaryCost(Double.parseDouble(rs.getString("tempCost")));
			expenseTempForSanction.setSalaryResRate(rs.getString("tempResRate"));
			expenseTempForSanction.setSalaryIsExceed("N");	// 인건비 초과여부 값을  N 로 고정(값이 없으므로)
			expenseTempForSanction.setSalaryMMValueRatio(rs.getString("tempMMValueRatio"));
			expenseTempForSanction.setSalaryIsMMExceed(rs.getString("tempIsMMExceed"));
			expenseTempForSanction.setSalaryCumulativeRealTimeSalary(rs.getLong("tempCumulativeRealTimeSalary"));
			expenseTempForSanction.setSalaryBudgetEachProject(rs.getLong("tempBudgetEachProject"));
			expenseTempForSanction.setSalaryApprovalType(rs.getString("tempApprovalType"));
			expenseTempForSanction.setSalarySsnCnt(rs.getString("ssnCnt"));
			return expenseTempForSanction;
		}

	}
	
	protected class ProjectExpenseTempForRefSanctionRetrieveQuery extends MappingSqlQuery {
		protected ProjectExpenseTempForRefSanctionRetrieveQuery(DataSource ds, String query) {
			super(ds, query);
		}
		
		protected ProjectExpenseTempForRefSanctionRetrieveQuery(DataSource ds) {
			super(ds, "	SELECT	seq,tempYear,tempMonth,tempName,tempSsn,tempTotalRealTimeSalary,tempProjectCode									"
					+ "			,tempProjectName,tempPreportCount,tempRealTimeSalaryEachProject,tempExecuted,tempDept							"
					+ "			,tempSeq,tempIsHolding,tempType,tempJobClass,tempJobClassErp,tempJobClassDesc, tempMMValueRatio					"
					+ "			,tempDeptType,isNull(tempCost,0) as tempCost,isNull(tempResRate,'-') as tempResRate								"
					+ "			,(substring(dbo.getExpertPoolUid(tempSsn), 1, 6)+'-'+substring(dbo.getExpertPoolUid(tempSsn), 7, 7) ) tempUid	"
					+ "			,tempCumulativeRealTimeSalary, tempBudgetEachProject															"
					+ "			,(CASE WHEN (SELECT seq FROM sanctionDoc WHERE seq = s.seq and approvalType = 'expenseR') IS NULL THEN '-' ELSE '인센티브' END) tempApprovalType	"	
					+ "	FROM ProjectExpenseTempForSanction s WHERE tempYear=? and tempMonth=? ORDER BY tempJobClassErp							");
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
		
		protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			ExpenseRealTimeResultDetailForSanction expenseTempForSanction = new ExpenseRealTimeResultDetailForSanction();
			expenseTempForSanction.setProjectExpenseSeq(rs.getString("seq"));
			expenseTempForSanction.setSalaryYear(rs.getString("tempYear"));
			expenseTempForSanction.setSalaryMonth(rs.getString("tempMonth"));
			expenseTempForSanction.setSalaryName(rs.getString("tempName"));
			expenseTempForSanction.setSalarySsn(rs.getString("tempSsn"));
			expenseTempForSanction.setSalaryUid(rs.getString("tempUid"));
			expenseTempForSanction.setSalaryTotalRealTimeSalary(rs.getLong("tempTotalRealTimeSalary"));
			expenseTempForSanction.setSalaryProjectCode(rs.getString("tempProjectCode"));
			expenseTempForSanction.setSalaryProjectName(rs.getString("tempProjectName"));
			expenseTempForSanction.setSalaryPreportCount(rs.getString("tempPreportCount"));
			expenseTempForSanction.setSalaryRealTimeSalaryEachProject(rs.getLong("tempRealTimeSalaryEachProject"));
			expenseTempForSanction.setSalaryExecuted(rs.getLong("tempExecuted"));
			expenseTempForSanction.setSalaryDept(rs.getString("tempDept"));
			expenseTempForSanction.setSalarySeq(rs.getString("tempSeq"));
			expenseTempForSanction.setSalaryIsHolding(rs.getString("tempIsHolding"));
			expenseTempForSanction.setSalaryType(rs.getString("tempType"));
			expenseTempForSanction.setSalaryDeptType(rs.getString("tempDeptType"));
			expenseTempForSanction.setSalaryJobClass(rs.getString("tempJobClass"));
			expenseTempForSanction.setSalaryJobClassDesc(rs.getString("tempJobClassDesc"));
			expenseTempForSanction.setSalaryJobClassErp(rs.getString("tempJobClassErp"));
			expenseTempForSanction.setSalaryMMValueRatio(rs.getString("tempMMValueRatio"));
			expenseTempForSanction.setSalaryCost(Double.parseDouble(rs.getString("tempCost")));
			expenseTempForSanction.setSalaryResRate(rs.getString("tempResRate"));
			expenseTempForSanction.setSalaryCumulativeRealTimeSalary(rs.getLong("tempCumulativeRealTimeSalary"));
			expenseTempForSanction.setSalaryBudgetEachProject(rs.getLong("tempBudgetEachProject"));
			expenseTempForSanction.setSalaryIsExceed("N");	// 인건비 초과여부 값을  N 로 고정(값이 없으므로)
			expenseTempForSanction.setSalaryApprovalType(rs.getString("tempApprovalType"));
			return expenseTempForSanction;
		}
		
	}

	@Override
	public void insert(ExpenseRealTimeResultDetailForSanction projectExpenseTempForSanction) throws DataAccessException {
		this.projectExpenseTempForSanctionInsertQuery.insert(projectExpenseTempForSanction);
	}

	@Override
	public void delete(String seq) throws DataAccessException {
		this.projectExpenseTempForSanctionDeleteQuery.delete(seq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> select(String seq) throws DataAccessException {
		return this.projectExpenseTempForSanctionRetrieveQuery.execute(new Object[] { seq });
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseRealTimeResultDetailForSanction> select(String year, String month) throws DataAccessException {
		return this.projectExpenseTempForRefSanctionRetrieveQuery.execute(new Object[] { year, month });
	}

}
